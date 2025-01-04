package org.quixalert.br.presentation.pages.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.quixalert.br.domain.model.News
import org.quixalert.br.domain.model.NewsType
import org.quixalert.br.services.NewsService
import javax.inject.Inject

data class NewsUiState(
    val globalNews: List<News> = emptyList(),
    val localNews: List<News> = emptyList(),
    val latestNews: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val currentFilterType: NewsType = NewsType.LOCAL
)

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsService: NewsService
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> get() = _uiState

    init {
        loadNews()
    }

    fun loadNews() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val allNews = newsService.getAll().await()
                val latestNews = allNews.filterIndexed { index, _ -> index < 3 }
                val globalNews = allNews.filter { news -> news.type == NewsType.GLOBAL }
                val localNews = allNews.filter { news -> news.type == NewsType.LOCAL }

                _uiState.value = _uiState.value.copy(
                    globalNews = globalNews,
                    localNews = localNews,
                    latestNews = latestNews,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to load news: ${e.message}"
                )
            }
        }
    }

//    fun updateFilterAndReloadNews(filterType: NewsType) {
//        val filteredNewsList = when (filterType) {
//            NewsType.LOCAL -> _uiState.value.newsList.filter { it.type == NewsType.LOCAL }
//            NewsType.GLOBAL -> _uiState.value.newsList.filter { it.type == NewsType.GLOBAL }
//            else -> _uiState.value.newsList
//        }
//
//        _uiState.value = _uiState.value.copy(
//            newsList = filteredNewsList,
//            currentFilterType = filterType
//        )
//    }
}