package com.example.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.quixalert.br.presentation.pages.news.GlobalNews
import org.quixalert.br.presentation.pages.news.LastNews
import org.quixalert.br.presentation.pages.news.LocalNews
import org.quixalert.br.presentation.pages.news.NewsViewModel

@Composable
fun NewsScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect("news") {
        viewModel.loadNews()
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 100.dp)
    ) {
        LastNews(uiState.latestNews)
        GlobalNews(uiState.globalNews)
        LocalNews(uiState.localNews)
    }
}
