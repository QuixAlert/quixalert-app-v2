package org.quixalert.br.view.pages.home

import LocalStrings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(){
    val lyricist = LocalStrings.current
    val locale = lyricist.strings

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            locale.appName,
            style = AppTheme.typography.titleLarge
        )
    }
}