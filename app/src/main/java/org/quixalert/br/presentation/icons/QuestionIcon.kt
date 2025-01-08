package org.quixalert.br.presentation.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val QuestionIcon: ImageVector
    get() {
        if (_QuestionMarkCircle != null) {
            return _QuestionMarkCircle!!
        }
        _QuestionMarkCircle = ImageVector.Builder(
            name = "QuestionMarkCircle",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF0F172A)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(9.87891f, 7.51884f)
                curveTo(11.0505f, 6.4937f, 12.95f, 6.4937f, 14.1215f, 7.5188f)
                curveTo(15.2931f, 8.544f, 15.2931f, 10.206f, 14.1215f, 11.2312f)
                curveTo(13.9176f, 11.4096f, 13.6917f, 11.5569f, 13.4513f, 11.6733f)
                curveTo(12.7056f, 12.0341f, 12.0002f, 12.6716f, 12.0002f, 13.5f)
                verticalLineTo(14.25f)
                moveTo(21f, 12f)
                curveTo(21f, 16.9706f, 16.9706f, 21f, 12f, 21f)
                curveTo(7.0294f, 21f, 3f, 16.9706f, 3f, 12f)
                curveTo(3f, 7.0294f, 7.0294f, 3f, 12f, 3f)
                curveTo(16.9706f, 3f, 21f, 7.0294f, 21f, 12f)
                close()
                moveTo(12f, 17.25f)
                horizontalLineTo(12.0075f)
                verticalLineTo(17.2575f)
                horizontalLineTo(12f)
                verticalLineTo(17.25f)
                close()
            }
        }.build()
        return _QuestionMarkCircle!!
    }

private var _QuestionMarkCircle: ImageVector? = null
