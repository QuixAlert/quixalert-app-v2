package org.quixalert.br.presentation.pages.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.quixalert.br.domain.model.News

@Composable
fun LocalNews(newsList: List<News>, hasHeader: Boolean = true, hasPadding: Boolean = true, isHorizontal: Boolean = false) {
    if (newsList.isEmpty()) {
        Text(
            text = "Nenhuma notícia local disponível",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    if (hasHeader) {
        Text(
            text = "Notícias locais",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 32.dp, start = 16.dp, bottom = 12.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }

    if (isHorizontal) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .apply {
                    if (hasPadding) {
                        padding(horizontal = 16.dp)
                    }
                }
        ) {
            items(newsList) { news ->
                NewsItem(news = news, isHorizontal = true)
            }
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .apply {
                    if (hasPadding) {
                        padding(horizontal = 16.dp)
                    }
                }
                .fillMaxWidth()
        ) {
            newsList.forEach { news ->
                NewsItem(news = news, isHorizontal = false)
            }
        }
    }
}
