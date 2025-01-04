package org.quixalert.br.presentation.pages.home

import androidx.compose.runtime.Composable
import org.quixalert.br.domain.model.News
import org.quixalert.br.domain.model.NewsType
import org.quixalert.br.presentation.components.ErrorSection
import org.quixalert.br.presentation.components.LoadingSection
import org.quixalert.br.presentation.pages.news.GlobalNews
import org.quixalert.br.presentation.pages.news.LocalNews

@Composable
fun NewsSection(
    isLoading: Boolean,
    errorMessage: String?,
    newsList: List<News>,
    newsType: NewsType
) {
    if (isLoading) {
        LoadingSection()
    } else if (!errorMessage.isNullOrEmpty()) {
        ErrorSection(errorMessage)
    } else {
        if(newsType == NewsType.LOCAL){
            LocalNews(newsList, hasHeader = false, hasPadding = false, isHorizontal = true)
        } else {
            GlobalNews(newsList, hasHeader = false, hasPadding = false)
        }
    }
}
