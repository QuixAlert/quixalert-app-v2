package org.quixalert.br.presentation.pages.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.quixalert.br.domain.model.News

@Composable
fun GlobalNews(newsList: List<News>) {
    if (newsList.isEmpty()) {
        // Show placeholder or a message when there are no global news
        Text(
            text = "Nenhuma notícia global disponível",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    Text(
        text = "Notícias globais",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        items(newsList) { news ->
            NewsItem(news = news, isHorizontal = true)
        }
    }
}
