import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.lyricist.Lyricist
import cafe.adriel.lyricist.rememberStrings
import org.quixalert.br.presentation.view.ui.location.Locales
import org.quixalert.br.presentation.view.ui.location.Strings
import org.quixalert.br.presentation.view.ui.location.translations
import org.quixalert.br.presentation.view.ui.theme.AppColorScheme
import org.quixalert.br.presentation.view.ui.theme.AppShape
import org.quixalert.br.presentation.view.ui.theme.AppSize
import org.quixalert.br.presentation.view.ui.theme.AppTypography
import org.quixalert.br.presentation.view.ui.theme.LocalAppColorScheme
import org.quixalert.br.presentation.view.ui.theme.LocalAppShape
import org.quixalert.br.presentation.view.ui.theme.LocalAppSize
import org.quixalert.br.presentation.view.ui.theme.LocalAppTypography
import org.quixalert.br.presentation.view.ui.theme.charcoalGray
import org.quixalert.br.presentation.view.ui.theme.grayLight32
import org.quixalert.br.presentation.view.ui.theme.poppinsFamily
import org.quixalert.br.presentation.view.ui.theme.primaryGreen
import org.quixalert.br.presentation.view.ui.theme.pureWhite
import org.quixalert.br.presentation.view.ui.theme.richNavyBlue
import org.quixalert.br.presentation.view.ui.theme.softSkyBlue

private val lightColorScheme = AppColorScheme(
    background = pureWhite,
    onBackground = softSkyBlue,
    primary = primaryGreen,
    onPrimary = richNavyBlue,
    secondary = grayLight32,
    onSecondary = charcoalGray
)

@Composable
private fun _appTypography(): AppTypography {
    val poppinsFamily = poppinsFamily()

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

val LocalStrings = compositionLocalOf<Lyricist<Strings>> {
    error("No Strings provided")
}

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme
//    val rippleIndication = ripple()
    val typography = _appTypography()

    val lyricist = rememberStrings(
        translations = translations,
        defaultLanguageTag = Locales.PT_BR,
        currentLanguageTag = Locale.current.toLanguageTag()
    )

    CompositionLocalProvider(
        LocalAppColorScheme provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        LocalAppSize provides size,
//        LocalIndication provides rippleIndication,
        LocalStrings provides lyricist
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
