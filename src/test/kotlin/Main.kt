package br.com.devsrsouza.svg2compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.com.compose.icons.EvaIcons
import br.com.compose.icons.evaicons.Branch
import br.com.compose.icons.evaicons.IcBalanceScale

fun main() = application {
    Window(::exitApplication, title = "Eva Icons SVG 2 Compose", state = rememberWindowState(width = 400.dp, height = 400.dp)) {
        Row {
            Icon(EvaIcons.Branch, "Brand Icon Test", modifier = Modifier.size(32.dp))
            Icon(EvaIcons.IcBalanceScale, "Balance Scale Icon Test", Modifier.size(32.dp))
        }
    }
}