package br.com.compose.icons.evaicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import br.com.compose.icons.EvaIcons

public val EvaIcons.Branch: ImageVector
    get() {
        if (_branch != null) {
            return _branch!!
        }
        _branch = Builder(name = "Branch", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp,
                viewportWidth = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(439.5f, 145.9f)
                curveToRelative(0.0f, -40.0f, -32.5f, -73.4f, -73.4f, -73.4f)
                reflectiveCurveToRelative(-73.4f, 32.5f, -73.4f, 73.4f)
                curveToRelative(0.0f, 26.9f, 14.9f, 50.2f, 36.2f, 63.2f)
                verticalLineToRelative(10.2f)
                curveToRelative(0.0f, 22.3f, -9.3f, 38.1f, -28.8f, 51.1f)
                curveToRelative(-17.7f, 11.2f, -43.7f, 19.5f, -77.1f, 24.2f)
                curveToRelative(-15.8f, 2.8f, -29.7f, 6.5f, -40.0f, 12.1f)
                verticalLineToRelative(-170.0f)
                curveToRelative(21.4f, -13.0f, 36.2f, -36.2f, 36.2f, -63.2f)
                curveToRelative(0.0f, -40.0f, -32.5f, -73.4f, -73.4f, -73.4f)
                reflectiveCurveTo(72.5f, 32.5f, 72.5f, 73.4f)
                curveToRelative(0.0f, 26.9f, 14.9f, 50.2f, 36.2f, 63.2f)
                verticalLineToRelative(238.8f)
                curveToRelative(-21.4f, 13.0f, -36.2f, 36.2f, -36.2f, 63.2f)
                curveToRelative(0.0f, 40.0f, 32.5f, 73.4f, 73.4f, 73.4f)
                reflectiveCurveToRelative(73.4f, -32.5f, 73.4f, -73.4f)
                curveToRelative(0.0f, -18.6f, -7.4f, -36.2f, -19.5f, -49.2f)
                curveToRelative(1.9f, -5.6f, 11.2f, -18.6f, 33.5f, -22.3f)
                curveToRelative(44.6f, -6.5f, 79.0f, -18.6f, 105.0f, -35.3f)
                curveToRelative(40.9f, -26.0f, 63.2f, -65.0f, 63.2f, -112.4f)
                verticalLineToRelative(-10.2f)
                curveTo(425.6f, 197.0f, 439.5f, 172.8f, 439.5f, 145.9f)
                lineTo(439.5f, 145.9f)
                close()
                moveTo(147.7f, 26.9f)
                curveToRelative(25.1f, 0.0f, 45.5f, 20.4f, 45.5f, 45.5f)
                reflectiveCurveTo(172.8f, 118.0f, 147.7f, 118.0f)
                reflectiveCurveToRelative(-45.5f, -20.4f, -45.5f, -45.5f)
                reflectiveCurveTo(122.7f, 26.9f, 147.7f, 26.9f)
                close()
                moveTo(147.7f, 484.1f)
                curveToRelative(-25.1f, 0.0f, -45.5f, -20.4f, -45.5f, -45.5f)
                curveToRelative(0.0f, -25.1f, 20.4f, -45.5f, 45.5f, -45.5f)
                reflectiveCurveToRelative(45.5f, 20.4f, 45.5f, 45.5f)
                curveTo(193.3f, 463.7f, 172.8f, 484.1f, 147.7f, 484.1f)
                close()
                moveTo(367.0f, 191.4f)
                curveToRelative(-25.1f, 0.0f, -45.5f, -20.4f, -45.5f, -45.5f)
                reflectiveCurveToRelative(20.4f, -45.5f, 45.5f, -45.5f)
                curveToRelative(25.1f, 0.0f, 45.5f, 20.4f, 45.5f, 45.5f)
                reflectiveCurveTo(392.1f, 191.4f, 367.0f, 191.4f)
                close()
            }
        }
        .build()
        return _branch!!
    }

private var _branch: ImageVector? = null
