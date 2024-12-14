package org.quixalert.br.view.pages.login

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.Fill
import org.quixalert.br.view.ui.theme.primaryGreen

@Composable
fun LoginScreen() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    var waveHeight by remember { mutableStateOf(0.35f) }
    
    val infiniteTransition = rememberInfiniteTransition()
    val animatedWaveHeight by infiniteTransition.animateFloat(
        initialValue = waveHeight,
        targetValue = waveHeight + 0.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryGreen)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            
            val wavePath = Path().apply {
                moveTo(0f, 0f)
                
                lineTo(width, 0f)
                
                lineTo(width, height * animatedWaveHeight)
                
                cubicTo(
                    width * 0.8f, height * (animatedWaveHeight + 0.05f),
                    width * 0.2f, height * (animatedWaveHeight - 0.05f),
                    0f, height * animatedWaveHeight
                )
                
                close()
            }

            drawPath(
                path = wavePath,
                color = Color.White,
                style = Fill
            )
        }
    }
}