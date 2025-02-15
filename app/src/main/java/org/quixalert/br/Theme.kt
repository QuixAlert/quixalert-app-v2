package org.quixalert.br

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.foundation.isSystemInDarkTheme
import org.quixalert.br.presentation.ui.theme.DarkColors
import org.quixalert.br.presentation.ui.theme.LightColors

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun QuixalertTheme(
    useDynamicColors: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when {
        useDynamicColors -> {
            if (darkTheme) DarkColors else LightColors
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
