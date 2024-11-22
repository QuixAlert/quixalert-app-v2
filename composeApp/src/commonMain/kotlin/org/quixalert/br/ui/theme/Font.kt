package org.quixalert.br.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight


import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import quixalert.composeapp.generated.resources.Res
import quixalert.composeapp.generated.resources.poppins_bold
import quixalert.composeapp.generated.resources.poppins_extrabold
import quixalert.composeapp.generated.resources.poppins_medium
import quixalert.composeapp.generated.resources.poppins_thin

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PoppinsFamily() = FontFamily(
    Font(resource = Res.font.poppins_thin, weight = FontWeight.Thin),
    Font(resource = Res.font.poppins_medium, weight = FontWeight.Medium),
    Font(resource = Res.font.poppins_bold, weight = FontWeight.Bold),
    Font(resource = Res.font.poppins_extrabold, weight = FontWeight.ExtraBold)
)