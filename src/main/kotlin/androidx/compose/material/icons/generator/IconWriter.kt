package androidx.compose.material.icons.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import java.io.File

class IconWriter(
    private val icon: Icon,
    private val groupClass: ClassName,
    private val groupPackage: String,
) {


    /**
     * Generates icon and writer it to [outputSrcDirectory]
     *
     * @param outputSrcDirectory the directory to generate source files in
     * @return MemberName of the created icons
     */
    fun generateTo(outputSrcDirectory: File): MemberName {
        val iconName = icon.kotlinName

        val vector = IconParser(icon).parse()

        val (fileSpec, accessProperty) = VectorAssetGenerator(
            iconName,
            groupPackage,
            vector
        ).createFileSpec(groupClass)

        fileSpec.writeTo(outputSrcDirectory)

        return MemberName(fileSpec.packageName, accessProperty)
    }

}