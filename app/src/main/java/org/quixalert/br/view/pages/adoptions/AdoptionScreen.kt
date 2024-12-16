package org.quixalert.br.view.pages.adoptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import org.quixalert.br.model.Pet

// Models
/*

os icones da top bar sao da cor que comeca com 2 a msm do botao
Em cima do card amarelo tem o titulo Animais disponiveis para a docao, em negrito

o card amarelo tem bordas arredondadas e afste ele um pouco mais da margem

tudo no card amarelo fica alinhado a esquerda e diminua um pouco o texto Nao pode adotar agora


 */

data class Filter(
    val id: String,
    val label: String
)
@Composable
fun AdoptionScreen(pets: List<Pet>) {
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        HeaderSection()

        // Title
        Text(
            text = "Animais disponíveis para adoção",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        // Donation Section
        DonationSection()

        // Filters
        FilterSection(
            selectedFilter = selectedFilter,
            onFilterSelected = { selectedFilter = it }
        )

        // Pet List
        PetList(pets)
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Voltar",
            tint = Color(0xFF269996)
        )
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            tint = Color(0xFF269996)
        )
    }
}

@Composable
fun DonationSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF8BC35))
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Não pode adotar agora?\nFaça uma doação!",
            fontSize = 20.sp,  // Reduced from 24.sp
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "Precisamos de ração e remédio!",
            fontSize = 18.sp,
            color = Color.Black
        )

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF269996)
            )
        ) {
            Text("Doar")
        }

    }
}

@Composable
fun FilterSection(
    selectedFilter: String?,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf(
        Filter("all", "Todos"),
        Filter("dogs", "Cachorros"),
        Filter("cats", "Gatos")
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter.id,
                onClick = { onFilterSelected(filter.id) },
                label = { Text(filter.label) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF269996),
                    selectedLabelColor = Color.White,
                    containerColor = Color.LightGray
                )
            )
        }
    }
}

@Composable
fun PetList(pets: List<Pet>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pets) { pet ->
            AdoptionItem(pet = pet)
        }
    }
}

@Composable
fun AdoptionItem(pet: Pet) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Box {
                AsyncImage(
                    model = pet.image,
                    contentDescription = pet.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = "Disponível",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(
                            color = Color(0xFF0B5DA2),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = pet.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = pet.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}