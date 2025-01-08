package org.quixalert.br.presentation.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.quixalert.br.domain.model.NewsType
import org.quixalert.br.domain.model.User
import org.quixalert.br.presentation.components.ErrorSection
import org.quixalert.br.presentation.components.HorizontalDividerSection
import org.quixalert.br.presentation.components.LoadingSection
import org.quixalert.br.presentation.components.SectionTitle
import org.quixalert.br.presentation.pages.adoptions.AdoptionViewModel
import org.quixalert.br.presentation.pages.news.NewsViewModel

val IconTint = Color(0xFF269996)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    user: User,
    newsViewModel: NewsViewModel = hiltViewModel(),
    adoptionViewModel: AdoptionViewModel = hiltViewModel(),
    onNotificationClick: () -> Unit
) {
    val newsUiState by newsViewModel.uiState.collectAsState()
    val adoptionUiState by adoptionViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        newsViewModel.loadNews()
        adoptionViewModel.loadPets()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 52.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopBar(user = user, onNotificationClick)
        }

        item {
            HorizontalDividerSection()
        }


        item {
            SearchBar(
                searchQuery = newsUiState.searchQuery,
                onSearchQueryChange = { query ->
                    newsViewModel.onSearchQueryChanged(query)
                }
            )
        }

        item { SectionTitle("Notícias Locais") }
        item {
            NewsSection(
                isLoading = newsUiState.isLoading,
                errorMessage = newsUiState.errorMessage,
                newsList = newsUiState.localNews,
                newsType = NewsType.LOCAL
            )
        }

        item { SectionTitle("Notícias Globais") }
        item {
            NewsSection(
                isLoading = newsUiState.isLoading,
                errorMessage = newsUiState.errorMessage,
                newsList = newsUiState.globalNews,
                newsType = NewsType.GLOBAL
            )
        }

        item { SectionTitle("Animais para Adoção") }

        if (adoptionUiState.isLoading) {
            item { LoadingSection() }
        } else if (!adoptionUiState.errorMessage.isNullOrEmpty()) {
            item { ErrorSection(adoptionUiState.errorMessage!!) }
        } else {
            items(adoptionUiState.allAnimals.chunked(2)) { rowAnimals ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowAnimals.forEach { animal ->
                        Box(modifier = Modifier.weight(1f)) {
                            AnimalItem(animal = animal)
                        }
                    }
                    if (rowAnimals.size == 1) {
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}


@Composable
fun TopBar(user: User, onNotificationClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = user.profileImage,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .shadow(4.dp, CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = user.greeting,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        IconButton (onNotificationClick) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                modifier = Modifier
                    .size(24.dp)
                    .shadow(2.dp, CircleShape),
                tint = IconTint
            )
        }
    }
}

// HomeScreen.kt - SearchBar Component
@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .shadow(4.dp, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        BasicTextField(

            value = searchQuery,
            onValueChange = onSearchQueryChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Pesquisar notícias...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        innerTextField()
                    }
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = IconTint
                    )
                }
            }
        )
    }
}
