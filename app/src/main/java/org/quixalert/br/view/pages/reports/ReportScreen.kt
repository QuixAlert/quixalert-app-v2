package org.quixalert.br.view.pages.reports

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class ReportDetail(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val responsible: String,
    val responsibleIcon: String,
    val status: String,
    val answer: String,
    val gallery: List<String>
)

val mockReportDetail = ReportDetail(
    id = "1",
    title = "Animal abandonado",
    category = "Maus tratos",
    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    responsible = "Prefeitura de Quixadá",
    responsibleIcon = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3Ze3JUrTPRe5LIttbKF8ouyH-0wbUnrbjBQ&s",
    status = "Resolvido",
    answer = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    gallery = List(4) { "https://images.jota.info/wp-content/uploads/2023/04/pexels-rk-jajoria-1189673.jpg" }
)

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(report: ReportDetail = mockReportDetail) {
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Image with Back Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = report.gallery.firstOrNull(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
            }

            // Content Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = (-20).dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color.White)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Parte superior (fundo branco)
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    color = Color.White
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Title and Category Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(2f)) {
                                Text(
                                    text = "Título",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Text(
                                    text = report.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    text = "Categoria",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Surface(
                                    color = Color(0xFF269996),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        text = report.category,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }

                        // Description Section
                        Column {
                            Text(
                                text = "Descrição:",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = report.description,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                // Parte inferior (fundo cinza claro)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFF5F5F5)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Responsible and Status Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(2f)) {
                                Text(
                                    text = "Responsável",
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    AsyncImage(
                                        model = report.responsibleIcon,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(
                                        text = report.responsible,
                                        fontSize = 16.sp
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
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Surface(
                                    color = Color(0xFF269996),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        text = report.status,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }

                        // Answer Section
                        Column {
                            Text(
                                text = "Resposta:",
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                                color = Color(0xFFE0E0E0),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, Color.White)
                            ) {
                                Text(
                                    text = report.answer,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }

                        // Gallery Section
                        Column {
                            Text(
                                text = "Galeria:",
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.height(250.dp)
                            ) {
                                items(report.gallery) { imageUrl ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .shadow(4.dp, RoundedCornerShape(16.dp)),
                                        shape = RoundedCornerShape(16.dp),
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
                        }

                        // Rating Section
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Dê sua nota para a resolução:",
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                repeat(5) { index ->
                                    IconButton(onClick = { rating = index + 1 }) {
                                        Icon(
                                            imageVector = if (index < rating) Icons.Default.Star else Icons.Outlined.Star,
                                            contentDescription = null,
                                            tint = if (index < rating) Color(0xFF269996) else Color.Gray
                                        )
                                    }
                                }
                            }
                        }

                        // Comment Section
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Comentários (opcional):",
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, Color.White)
                            ) {
                                TextField(
                                    value = comment,
                                    onValueChange = { comment = it },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color(0xFFE0E0E0),
                                        unfocusedContainerColor = Color(0xFFE0E0E0),
                                        disabledContainerColor = Color(0xFFE0E0E0),
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    )
                                )
                            }
                        }

                        // Submit Button
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF269996)
                            )
                        ) {
                            Text(
                                text = "Enviar validação",
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}