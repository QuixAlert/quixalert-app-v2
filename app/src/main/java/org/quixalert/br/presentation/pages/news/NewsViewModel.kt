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
//                registerNews()
                val allNews = newsService.getAll().await()
                val latestNews = allNews
                    .sortedByDescending { Instant.parse(it.date) }
                    .take(3)
                val globalNews = allNews.filter { it.type == NewsType.GLOBAL }
                val localNews = allNews.filter { it.type == NewsType.LOCAL }

                _uiState.value = _uiState.value.copy(
                    globalNews = globalNews,
                    localNews = localNews,
                    latestNews = latestNews,
                    isLoading = false
                )
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
}
