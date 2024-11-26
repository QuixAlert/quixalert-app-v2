package org.quixalert.br

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.quixalert.br.ui.theme.AppTheme
import org.quixalert.br.ui.theme.LocalStrings
import org.quixalert.br.ui.theme.primaryGreen

@Composable
@Preview
fun App() {
    AppTheme {
        val lyricist = LocalStrings.current
        val locale = lyricist.strings

        MaterialTheme {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = locale.appName,
                    style = AppTheme.typography.titleLarge,
                    color = primaryGreen
                )
            }
        }
    }

}