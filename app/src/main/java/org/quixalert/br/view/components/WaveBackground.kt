package org.quixalert.br.view.pages.login

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill

@Composable
fun WaveBackground() {
    var waveHeight by remember { mutableStateOf(0.45f) }
    
    val infiniteTransition = rememberInfiniteTransition()
    val animatedWaveHeight by infiniteTransition.animateFloat(
        initialValue = waveHeight,
        targetValue = waveHeight + 0.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

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