package org.quixalert.br.presentation.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val primaryGreen = Color(0xFF269996)
val primaryBlue = Color(0xFF0B5DA2)
val primaryGray = Color(0xFFD0D3D9)
val primaryRed = Color(0xFFFF4246)

val sunlitOrange = Color(0xFFA912)
val vibrantOrange = Color(0xE69810)

val softSkyBlue = Color(0xCFF1F6)
val deepOceanBlue = Color(0x348FD9)
val richNavyBlue = Color(0x2A72AE)
val principalBlue = Color(0x269996)

val pureWhite = Color(0xFFFFFF)

val grayLight32 = Color(0x20D9D9D9) // 32 transparency
val charcoalGray = Color(0x727272)

// Paletas de cores personalizadas
val LightColors = lightColorScheme(
    
    primary = Color(0xFFE1E2E8),
    onPrimary = Color.White,
    secondary = Color(0xFFC9C9CB),
    onSecondary = Color.Black,
    background = Color.White,

    surface = Color(0xFFF5F5F5),
    onSurface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFFF0F0F0),
    onSurfaceVariant = Color(0xFF666666),
    tertiary = Color(0xFF666666),
    onTertiary = Color(0xFF424242),
    
    onBackground = Color.Black,
    onTertiaryContainer = Color(0xFFEEEEEE)
)

val DarkColors = darkColorScheme(
    primary = Color(0xFF7E7E7E),
    onPrimary = Color.White,
    secondary = Color(0xFFE24767),
    onSecondary = Color.White,
    background = Color.Black,
    surface = Color(0xFF9F9F9F),
    onBackground = Color.White,
    onSurface = Color.White,
    onTertiary = Color.White,
    onSurfaceVariant = Color.Black,
    onTertiaryContainer = Color.Black
)
