package org.quixalert.br.presentation.pages.emergencyNumbers

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.quixalert.br.R
import org.quixalert.br.domain.model.EmergencyNumber
import org.quixalert.br.presentation.pages.profile.IconTint

val animalEmergencyNumbersList = listOf(
    EmergencyNumber(
        name = "Prefeitura de Quixadá",
        image = R.drawable.emergency_icon1,
        phone = "(88) 99920-4550",
        openingHours = "Disponível de 8h até 17h",
    ),
    EmergencyNumber(
        name = "AMMA",
        image = R.drawable.emergency_icon2,
        phone = "(88) 99920-4550",
        openingHours = "Disponível de 8h até 17h",
    ),
    EmergencyNumber(
        name = "Polícia Municipal",
        image = R.drawable.emergency_icon3,
        phone = "(88) 3445-1047",
        openingHours = "Disponível 24h",
    ),
)

val ambientalEmergencyNumbersList = listOf(
    EmergencyNumber(
        name = "Prefeitura de Quixadá",
        image = R.drawable.emergency_icon1,
        phone = "(88) 99920-4550",
        openingHours = "Disponível de 8h até 17h",
    ),
    EmergencyNumber(
        name = "Corpo de Bombeiros",
        image = R.drawable.emergency_icon4,
        phone = "(85) 98878-7260",
        openingHours = "Disponível de 8h até 17h",
    ),
    EmergencyNumber(
        name = "AMMA",
        image = R.drawable.emergency_icon2,
        phone = "(88) 99920-4550",
        openingHours = "Disponível de 8h até 17h",
    ),
    EmergencyNumber(
        name = "IBAMA",
        image = R.drawable.emergency_icon5,
        phone = "0800 061 8080",
        openingHours = "Disponível de 7h até 19h",
    ),
)

@Composable
fun EmergencyNumbersScreen(onBackClick: () -> Unit = {}, onMenuClick: () -> Unit = {}) {
    Column() {
        TopBar(
            onBackClick = onBackClick,
            onMenuClick = onMenuClick
        )
        Text(
            text = "Números em caso de emergência",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(start = 16.dp)
        )
        EmergencyNumbersList(animalEmergencyNumbersList, ambientalEmergencyNumbersList)
    }
}

@Composable
fun EmergencyNumbersList(
    animalEmergencyNumbersList: List<EmergencyNumber>,
    ambientalEmergencyNumbersList: List<EmergencyNumber>
) {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Text(
            text = "Maus tratos animais",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF50555C)
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(animalEmergencyNumbersList) { emergencyNumber ->
                EmergencyNumberItem(emergencyNumber)
            }
        }


        Text(
            text = "Causas ambientais",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF50555C)
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(ambientalEmergencyNumbersList) { emergencyNumber ->
                EmergencyNumberItem(emergencyNumber)
            }
        }
    }
}

@Composable
fun EmergencyNumberItem(emergencyNumber: EmergencyNumber) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = emergencyNumber.image),
                contentDescription = "Ícone da Notificação",
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.LightGray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = emergencyNumber.name,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                )
                Text(
                    text = emergencyNumber.openingHours,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            }

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF269996)
                ),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable {
                        dialPhoneNumber(context, emergencyNumber.phone)
                    }
                    .width(130.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Telefone",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = emergencyNumber.phone,
                        style = TextStyle(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun TopBar(
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit
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

        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = IconTint
            )
        }
    }
}

fun dialPhoneNumber(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phoneNumber")
    context.startActivity(intent)
}