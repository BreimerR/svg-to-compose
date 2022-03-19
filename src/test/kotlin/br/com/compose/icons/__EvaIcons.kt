package br.com.compose.icons

import androidx.compose.ui.graphics.vector.ImageVector
import br.com.compose.icons.evaicons.Branch
import br.com.compose.icons.evaicons.IcBalanceScale
import kotlin.collections.List as ____KtList

public object EvaIcons

private var __AllAssets: ____KtList<ImageVector>? = null

public val EvaIcons.AllAssets: ____KtList<ImageVector>
  get() {
    if (__AllAssets != null) {
      return __AllAssets!!
    }
    __AllAssets= listOf(Branch, IcBalanceScale)
    return __AllAssets!!
  }
