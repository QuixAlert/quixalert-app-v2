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
import org.quixalert.br.utils.populateNews
import java.time.Instant
import javax.inject.Inject

data class NewsUiState(
    val globalNews: List<News> = emptyList(),
    val localNews: List<News> = emptyList(),
    val latestNews: List<News> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val currentFilterType: NewsType = NewsType.LOCAL,
    val searchQuery: String = "" // Add search query to state
)

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsService: NewsService
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> get() = _uiState
    private var allNewsCache: List<News> = emptyList()

    init {
        loadNews()
    }

    fun loadNews() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                allNewsCache = newsService.getAll().await()
                filterNews(_uiState.value.searchQuery)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Falha ao carregar notícias: ${e.message}"
                )
            }
        }
    }

    fun registerNews(){
        populateNews().forEach { new -> newsService.add(new) }
    }


    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        filterNews(query)
    }

    private fun filterNews(query: String) {
        val filteredNews = if (query.isBlank()) {
            allNewsCache

        } else {
            allNewsCache.filter { news ->
                news.title.contains(query, ignoreCase = true) ||
                        news.title.contains(query, ignoreCase = true)
            }
        }

        val latestNews = filteredNews
            .sortedByDescending { Instant.parse(it.date) }
            .take(3)
        val globalNews = filteredNews.filter { it.type == NewsType.GLOBAL }
        val localNews = filteredNews.filter { it.type == NewsType.LOCAL }

        _uiState.value = _uiState.value.copy(
            globalNews = globalNews,
            localNews = localNews,
            latestNews = latestNews,
            isLoading = false
        )
    }
}
