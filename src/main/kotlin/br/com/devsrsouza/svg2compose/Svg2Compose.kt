package br.com.devsrsouza.svg2compose

import androidx.compose.material.icons.generator.*
import com.android.ide.common.vectordrawable.Svg2Vector
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.MemberName
import java.io.File
import java.util.*
import kotlin.io.path.createTempDirectory
import kotlin.math.max

typealias IconNameTransformer = (iconName: String, group: String) -> String

data class Size(val height: Float, val width: Float) {

    constructor(size: Number) : this(size.toFloat(), size.toFloat())

    val maxValue
        get() = max(height, width).toInt()

}

object Svg2Compose {

    private val svgComposeTempDir: File by lazy {
        drawableTempDirectory()
    }

    /**
     * Generates source code for the [vectors] files.
     *
     * Supported types: SVG, Android Vector Drawable XML
     *
     * @param applicationIconPackage Represents what will be the final package of the generated Vector Source. ex com.yourcompany.yourapplication.icons
     * @param accessorName will be usage to access the Vector in the code like `MyIconPack.IconName` or `MyIconPack.IconGroupDir.IconName`
     */
    fun parse(
        applicationIconPackage: String,
        accessorName: String,
        outputSourceDirectory: File,
        vectorsDirectory: File,
        type: VectorType = VectorType.SVG,
        iconNameTransformer: IconNameTransformer = { it, _ -> it },
        allAssetsPropertyName: String = "AllAssets",
        size: Size? = null,
    ): ParsingResult {

        val groupStack = Stack<GeneratedGroup>()

        vectorsDirectory.walkTopDown()
            .maxDepth(10)
            .onEnter { file ->
                val dirIcons = (file.listFiles() ?: arrayOf())
                    .filterNotNull()
                    .filter { it.isDirectory.not() }
                    .filter { it.extension.equals(type.extension, ignoreCase = true) }

                val previousGroup = groupStack.peekOrNull()

                // if there is no previous group, this is the root dir, and the group name should be the accessorName
                val groupName = if (previousGroup == null) accessorName else file.name.toKotlinPropertyName()
                val groupPackage = previousGroup?.let { group -> "${group.groupPackage}.${group.groupName.second.lowercase()}" }
                    ?: applicationIconPackage
                val iconsPackage = "$groupPackage.${groupName.lowercase()}"

                val (groupFileSpec, groupClassName) = IconGroupGenerator(
                    groupPackage,
                    groupName
                ).createFileSpec(previousGroup?.groupClass)


                val generatedIconsMemberNames: Map<VectorFile, MemberName> =
                    if (dirIcons.isNotEmpty()) {
                        val drawables: List<Pair<File, File>> = when (type) {
                            VectorType.SVG -> dirIcons.map {
                                val iconName = it.nameRelative(vectorsDirectory).withoutExtension

                                val parsedFile = File(svgComposeTempDir, "${iconName}.xml")
                                parsedFile.parentFile.mkdirs()

                                Svg2Vector.parseSvgToXml(it, parsedFile.outputStream())

                                it to parsedFile
                            }.toList()

                            VectorType.DRAWABLE -> dirIcons.toList().map { it to it }
                        }

                        val icons: Map<VectorFile, Icon> = drawables.associate { (vectorFile, drawableFile) ->
                            vectorFile to Icon(
                                iconNameTransformer(
                                    drawableFile.nameWithoutExtension.toKotlinPropertyName().trim(),
                                    groupName
                                ),
                                drawableFile.name,
                                drawableFile.readText()
                            )
                        }

                        val memberNames = icons.values.writer(
                            groupClassName,
                            iconsPackage,
                            size
                        ).write(outputSourceDirectory) { true }

                        icons.mapValues { entry ->
                            memberNames.first {
                                val name = (size?.let { setSize -> "${entry.value.kotlinName}${setSize.maxValue}" }
                                    ?: entry.value.kotlinName)
                                it.simpleName == name
                            }
                        }
                    } else {
                        emptyMap()
                    }

                val result = GeneratedGroup(
                    groupPackage,
                    file to groupName,
                    generatedIconsMemberNames,
                    groupClassName,
                    groupFileSpec,
                    childGroups = emptyList()
                )

                if (previousGroup != null) {
                    groupStack.pop()
                    groupStack.push(previousGroup.copy(childGroups = previousGroup.childGroups + result))
                }

                groupStack.push(result)

                true
            }
            .onLeave {
                val group = if (groupStack.size > 1)
                    groupStack.pop()
                else
                    groupStack.peek()

                val allAssetsGenerator = AllIconAccessorGenerator(
                    group.generatedIconsMemberNames.values,
                    group.groupClass,
                    allAssetsPropertyName,
                    group.childGroups
                )

                for (propertySpec in allAssetsGenerator.createPropertySpec(group.groupFileSpec)) {
                    group.groupFileSpec.addProperty(propertySpec)
                }

                group.groupFileSpec.build().writeTo(outputSourceDirectory)
            }
            .toList() // consume, to onEnter and onLeave be triggered

        return groupStack.pop().asParsingResult()
    }

    private fun drawableTempDirectory() = createTempDirectory(prefix = "svg2compose").toFile()

    private val String.withoutExtension get() = substringBeforeLast(".")

    fun parse(
        path: IconPath,
        outputSourceDirectory: File,
        type: VectorType,
        packageName: String = "com.example",
        groupName: String = "EvaIcons",
        iconNameTransformer: IconNameTransformer = { it, _ -> it },
        size: Size? = null,
    ): MemberName {
        val iconFile = File(path)

        val iconFileName = iconFile.nameWithoutExtension

        val drawableFile = when (type) {
            VectorType.SVG -> {
                val parsedFile = File(svgComposeTempDir, "drawables/${iconFileName}.xml")
                parsedFile.parentFile.mkdirs()

                Svg2Vector.parseSvgToXml(iconFile, parsedFile.outputStream())

                parsedFile
            }

            VectorType.DRAWABLE -> {
                iconFile
            }
        }

        val icon = Icon(
            iconNameTransformer(
                drawableFile.nameWithoutExtension.toKotlinPropertyName().trim(),
                groupName
            ),
            drawableFile.name,
            drawableFile.readText()
        )

        val (_, groupClassName) = IconGroupGenerator(
            packageName,
            groupName
        ).createFileSpec(null)

        return icon.writer(
            groupClassName,
            packageName,
            size
        ).write(outputSourceDirectory)

    }

    private fun File.nameRelative(vectorsDirectory: File) = relativeTo(vectorsDirectory).path

}

typealias IconPath = String
typealias GroupFolder = File
typealias VectorFile = File

data class GeneratedGroup(
    val groupPackage: String,
    val groupName: Pair<GroupFolder, String>,
    val generatedIconsMemberNames: Map<VectorFile, MemberName>,
    val groupClass: ClassName,
    val groupFileSpec: FileSpec.Builder,
    val childGroups: List<GeneratedGroup>
)

data class ParsingResult(
    val groupName: Pair<GroupFolder, String>,
    val generatedIconsMemberNames: Map<VectorFile, MemberName>,
    val generatedGroups: List<ParsingResult>
)

private fun GeneratedGroup.asParsingResult(): ParsingResult = ParsingResult(
    groupName = groupName,
    generatedIconsMemberNames = generatedIconsMemberNames,
    generatedGroups = childGroups.map { it.asParsingResult() },
)