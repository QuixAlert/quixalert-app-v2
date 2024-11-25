package org.quixalert.br.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val lightColorScheme = AppColorScheme(
    background = pureWhite,
    onBackground = softSkyBlue,
    primary = deepOceanBlue,
    onPrimary = richNavyBlue,
    secondary = grayLight32,
    onSecondary = charcoalGray
)

@Composable
private fun _appTypography(): AppTypography {
    val poppinsFamily = PoppinsFamily()

    return AppTypography(
        titleLarge = TextStyle(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        ),
        titleMedium = TextStyle(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        ),
        titleThin = TextStyle(
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.Thin,
            fontSize = 18.sp
        )
    )
}

private val shape = AppShape(
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(50)
)

private val size = AppSize(
    large = 24.dp,
    medium = 16.dp,
    normal = 12.dp,
    small = 8.dp
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme
    val typography = _appTypography()

    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        LocalAppSize provides size,
    ) {
        content()
    }
}

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable get() = LocalAppColorScheme.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current

    val size: AppSize
        @Composable get() = LocalAppSize.current
}