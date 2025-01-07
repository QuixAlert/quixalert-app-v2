package org.quixalert.br.presentation.pages.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import org.quixalert.br.domain.model.News

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LastNews(newsList: List<News>) {
    if (newsList.isEmpty()) {
        // Show placeholder or a message when there are no news
        Text(
            text = "Nenhuma notícia disponível",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Últimas notícias",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        HorizontalPager(
            count = newsList.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            val newsItem = newsList[page]
            AsyncImage(
                model = newsItem.imageUrl,  // Use the image URL here
                contentDescription = "Carousel Image $page",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        )
    }
}