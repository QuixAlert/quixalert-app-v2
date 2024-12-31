package org.quixalert.br.presentation.view.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import org.quixalert.br.R

@Composable
fun poppinsFamily() = FontFamily(
    Font(resId = R.font.poppins_thin, weight = FontWeight.Thin),
    Font(resId = R.font.poppins_medium, weight = FontWeight.Medium),
    Font(resId = R.font.poppins_bold, weight = FontWeight.Bold),
    Font(resId = R.font.poppins_extrabold, weight = FontWeight.ExtraBold)
)