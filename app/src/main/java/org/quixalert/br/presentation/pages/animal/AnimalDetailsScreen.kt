package org.quixalert.br.presentation.pages.animal

import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.presentation.pages.profile.IconTint
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalScreenBase(
    animal: Animal,
    onBackClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    var selectedVideoUrl by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                onBackClick = onBackClick
            )
            // Header Image with Back Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = animal.image,
                    contentDescription = animal.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                }
            }

            // Content Section with rounded top corners
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = (-20).dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Pet Header (Name and Responsible info) - Common for both screens
                    item {
                        Text(
                            text = animal.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    item {
                        // Responsible and Status Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(2f)
                            ) {
                                Text(
                                    text = "Responsável atual",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    AsyncImage(
                                        model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3Ze3JUrTPRe5LIttbKF8ouyH-0wbUnrbjBQ&s",
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = "Prefeitura de Quixadá",
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Status",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                                Surface(
                                    color = Color(0xFF269996),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = "Disponível",
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }

                    // Custom content for each screen
                    item {
                        content()
                    }
                }
            }
        }
    }
}

@Composable
fun AnimalDetailsScreen(
    viewModel: AnimalDetailsViewModel = hiltViewModel(),
    selectedAnimal: Animal?,
    onBackClick: () -> Unit,
    onFormClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }
    var selectedVideo by remember { mutableStateOf<String?>(null) }

    LaunchedEffect("loadingPet") {
        if (selectedAnimal != null) {
            viewModel.setAnimalId(animalId = selectedAnimal.id)
        }
        viewModel.loadPet()
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            confirmButton = {
                Button(
                    onClick = { showErrorDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF269996)
                    )
                ) {
                    Text(
                        text = "Ok",
                    )
                }
            },
            text = {
                Text(
                    uiState.errorMessage ?: "Ocorreu um erro desconhecido.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        )
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF269996))
            }
        }
        uiState.errorMessage != null -> {
            showErrorDialog = true
        }
        uiState.animal != null -> {
            val animal = uiState.animal
            if (animal != null) {
                AnimalScreenBase(animal = animal, onBackClick = onBackClick) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Raça",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                                Text(
                                    text = animal.species,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Porte",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                                Text(
                                    text = animal.size.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Sexo",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                                Text(
                                    text = animal.gender.name,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Local",
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                text = animal.extraInfo.location,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Sobre o Pet",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = animal.description.ifEmpty { "Nenhuma descrição cadastrada" },
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text(
                                text = "Galeria",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            var selectedTab by remember { mutableStateOf(0) }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                FilterChip(
                                    selected = selectedTab == 0,
                                    onClick = { selectedTab = 0 },
                                    label = { Text("Fotos") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFF269996),
                                        containerColor = Color(0xFF6B6B6B),
                                        labelColor = Color.White
                                    )
                                )
                                FilterChip(
                                    selected = selectedTab == 1,
                                    onClick = { selectedTab = 1 },
                                    label = { Text("Vídeos") },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Color(0xFF269996),
                                        containerColor = Color(0xFF6B6B6B),
                                        labelColor = Color.White
                                    )
                                )
                            }

                            if (selectedTab == 0) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth().height(250.dp)
                                ) {
                                    items(animal.extraInfo.gallery) { imageUrl ->
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .shadow(
                                                    elevation = 4.dp,
                                                    shape = RoundedCornerShape(8.dp)
                                                ),
                                            shape = RoundedCornerShape(8.dp),
                                            border = BorderStroke(1.dp, Color.White)
                                        ) {
                                            AsyncImage(
                                                model = imageUrl,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(100.dp),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                ) {
                                    items(animal.extraInfo.videos) { videoUrl ->
                                        VideoThumbnail(
                                            videoUrl = videoUrl,
                                            onClick = { selectedVideo = videoUrl }
                                        )
                                    }
                                }

// Video player dialog
                                selectedVideo?.let { videoUrl ->
                                    VideoPlayer(
                                        videoUrl = videoUrl,
                                        onDismiss = { selectedVideo = null }
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = {
                                onFormClick()
                                viewModel.setAnimalId(animal.id)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, bottom = 72.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF269996)
                            )
                        ) {
                            Text(
                                text = "Quero Adotar",
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Voltar",
                tint = IconTint
            )
        }
    }
}

@Composable
fun VideoThumbnail(
    videoUrl: String,
    onClick: () -> Unit
) {
    // Extract video ID from YouTube URL
    val videoId = extractYouTubeVideoId(videoUrl)

    // Thumbnail URL format for YouTube videos
    val thumbnailUrl = "https://img.youtube.com/vi/$videoId/0.jpg"

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Box {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = "Video thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            // Play button overlay
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center),
                tint = Color.White
            )
        }
    }
}

@Composable
fun VideoPlayer(
    videoUrl: String,
    onDismiss: () -> Unit
) {
    val videoId = extractYouTubeVideoId(videoUrl)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black)
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.apply {
                            javaScriptEnabled = true
                            mediaPlaybackRequiresUserGesture = false
                            domStorageEnabled = true
                        }
                        webChromeClient = WebChromeClient()
                        loadDataWithBaseURL(
                            "https://www.youtube.com",
                            """
                            <html>
                                <head>
                                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                    <style>
                                        body { margin: 0; }
                                        .video-container {
                                            position: relative;
                                            padding-bottom: 56.25%;
                                            height: 0;
                                            overflow: hidden;
                                        }
                                        .video-container iframe {
                                            position: absolute;
                                            top: 0;
                                            left: 0;
                                            width: 100%;
                                            height: 100%;
                                        }
                                    </style>
                                </head>
                                <body>
                                    <div class="video-container">
                                        <iframe 
                                            src="https://www.youtube.com/embed/$videoId?autoplay=1&playsinline=1"
                                            frameborder="0"
                                            allowfullscreen>
                                        </iframe>
                                    </div>
                                </body>
                            </html>
                            """.trimIndent(),
                            "text/html",
                            "UTF-8",
                            null
                        )
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Fechar",
                    tint = Color.White
                )
            }
        }
    }
}

private fun extractYouTubeVideoId(url: String): String {
    val pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u201C|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"
    val compiledPattern = Pattern.compile(pattern)
    val matcher = compiledPattern.matcher(url)
    val videoId = if (matcher.find()) {
        matcher.group()
    } else {
        ""
    }
    Log.d("VideoPlayer", "Video ID: $videoId from URL: $url")

    return videoId
}