package org.quixalert.br.view.pages.animal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class PetDetail(
    val id: String,
    val name: String,
    val image: String,
    val currentResponsible: String,
    val responsibleIcon: String,
    val status: String,
    val breed: String,
    val size: String,
    val gender: String,
    val location: String,
    val about: String,
    val gallery: List<String>,
    val videos: List<String>
)

// Mock data
val mockPetDetail = PetDetail(
    id = "1",
    name = "Max",
    image = "https://images.jota.info/wp-content/uploads/2023/04/pexels-rk-jajoria-1189673.jpg",
    currentResponsible = "Responsável atual",
    responsibleIcon = "/api/placeholder/32/32",
    status = "Disponível",
    breed = "Sem raça definida",
    size = "Médio",
    gender = "Macho",
    location = "Rua tabelião Enéas, 649 -\nCentro, Quixadá - CE, 63900-169",
    about = "Max é um cachorro muito dócil e brincalhão. Ele adora crianças e é muito protetor. Já está vacinado e vermifugado.",
    gallery = List(6) { "https://images.jota.info/wp-content/uploads/2023/04/pexels-rk-jajoria-1189673.jpg" },
    videos = List(2) { "/api/placeholder/200/200" }
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetScreenBase(
    pet: PetDetail,
    content: @Composable ColumnScope.() -> Unit
) {
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
                    model = pet.image,
                    contentDescription = pet.name,
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
                    .background(Color.White)
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
                            text = pet.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
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
                                    color = Color.Gray
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
                                    color = Color.Gray
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
fun AnimalDetailsScreen(pet: PetDetail = mockPetDetail) {
    PetScreenBase(pet = pet) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Characteristics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Breed
                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = "Raça",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = pet.breed,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                // Size
                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = "Porte",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = pet.size,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                // Gender
                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = "Sexo",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = pet.gender,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Location Section
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Local",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = pet.location,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // About Section
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Sobre o Pet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = pet.about,
                    fontSize = 14.sp
                )
            }

            // Gallery Section
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Galeria",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                // Tabs
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
                            selectedLabelColor = Color.White
                        )
                    )
                    FilterChip(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        label = { Text("Vídeos") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF269996),
                            selectedLabelColor = Color.White
                        )
                    )
                }

                if (selectedTab == 0) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth().height(250.dp)  // Removida altura fixa
                    ) {
                        items(pet.gallery) { imageUrl ->
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
                }
            }

            Button(
                onClick = { },
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