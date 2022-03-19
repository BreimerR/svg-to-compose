package br.com.devsrsouza.svg2compose

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

class TestMain {

    @Test
    fun traverseGeneratorTest() {
        val icons = File("src/test/resources/icons")
        val src = File("src/test/kotlin").apply { mkdirs() }

        assert(icons.exists()) {
            "Make sure to add icons into res dir. Default test icons have been provided"
        }

        Svg2Compose.parse(
            applicationIconPackage = "br.com.compose.icons",
            accessorName = "EvaIcons",
            outputSourceDirectory = src,
            vectorsDirectory = icons,
            iconNameTransformer = { name, group ->
                name.removeSuffix(group, ignoreCase = true)
            }
        )

    }

    private fun String.removeSuffix(suffix: String, ignoreCase: Boolean): String {
        return if (ignoreCase) {
            val index = lastIndexOf(suffix, ignoreCase = true)

            if (index > -1) substring(0, index) else this
        } else {
            removeSuffix(suffix)
        }
    }

    @Test
    fun testCamelCase() {
        val iconName = "IconName"

        assertEquals(iconName, "icon-name".camelCase)
        assertEquals(iconName, "icon name".camelCase)
        assertEquals(iconName, "icon_name".camelCase)
    }

    val String.camelCase: String
        get() = trim().split(" ", "-", "_").joinToString("") {
            it[0].uppercase() + it.substring(1, it.length)
        }
}

