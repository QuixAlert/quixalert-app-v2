package org.quixalert.br.presentation.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.domain.model.AnimalGender
import org.quixalert.br.domain.model.AnimalType

@Composable
fun AnimalItem(animal: Animal) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
    ) {
        AsyncImage(
            model = animal.image,
            contentDescription = animal.name,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(
                    Color.Black.copy(alpha = 0.6f),
                    RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = animal.name,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = when (animal.type) {
                    AnimalType.DOG -> if (animal.gender == AnimalGender.MALE)
                        "Cãozinho disponível para adoção responsável"
                    else "Cãozinha disponível para adoção responsável"
                    AnimalType.CAT -> if (animal.gender == AnimalGender.MALE)
                        "Gatinho disponível para adoção responsável"
                    else "Gatinha disponível para adoção responsável"
                },
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}