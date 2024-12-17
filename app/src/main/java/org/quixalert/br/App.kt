package org.quixalert.br

import AppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.quixalert.br.view.pages.emergencyNumbers.EmergencyNumbersScreen

@Composable
@Preview
fun App() {
    AppTheme{
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
        ) {
            EmergencyNumbersScreen()
        }
    }
}