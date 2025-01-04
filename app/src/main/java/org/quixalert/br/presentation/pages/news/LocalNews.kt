package org.quixalert.br.presentation.pages.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.quixalert.br.domain.model.News

@Composable
fun LocalNews(newsList: List<News>) {
    if (newsList.isEmpty()) {
        // Show placeholder or a message when there are no local news
        Text(
            text = "Nenhuma notícia local disponível",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    Text(
        text = "Notícias locais",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(top = 32.dp, start = 16.dp, bottom = 12.dp)
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        newsList.forEach {
            NewsItem(news = it, isHorizontal = false)
        }
    }
}
