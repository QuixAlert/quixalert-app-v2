package org.quixalert.br.view.pages.login

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.quixalert.br.R
import org.quixalert.br.presentation.ui.theme.primaryBlue
import org.quixalert.br.presentation.ui.theme.primaryGreen

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryGreen)
    ) {
        // Wave background
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

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo - adjusted spacing
            Spacer(modifier = Modifier.height(120.dp))
            Image(
                painter = painterResource(id = R.drawable.quix_alert),
                contentDescription = "QuixAlert Logo",
                modifier = Modifier
                    .size(206.dp)
                    .padding(16.dp)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Register Button (Blue)
            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(59.dp),
                colors = ButtonDefaults.buttonColors(primaryBlue),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = "Registrar",
                    fontSize = 24.sp,
                    color = Color.White,
                    letterSpacing = (-0.333333).sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Login Button (White with blue border)
            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(59.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(40.dp),
                border = BorderStroke(1.dp, primaryBlue)
            ) {
                Text(
                    text = "Entrar",
                    fontSize = 24.sp,
                    color = primaryBlue,
                    letterSpacing = (-0.333333).sp
                )
            }
            
            // Increased bottom spacing to move buttons up
            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}