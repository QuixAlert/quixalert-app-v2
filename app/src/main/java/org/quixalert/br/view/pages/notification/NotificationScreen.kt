package org.quixalert.br.view.pages.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.quixalert.br.R
import org.quixalert.br.model.Notification
import java.text.SimpleDateFormat
import java.util.*


val notifications = listOf(
    Notification(
        id = 1,
        data = SimpleDateFormat("dd/MM/yyyy").parse("15/12/2024"),
        title = "Dicas de sustentabilidade",
        message = "Todas as vezes que usamos uma folha de papel indevidamente, matamos praticamente uma árvore. Use papel de forma consciente.",
        readCheck = false,
        image = R.drawable.notification_icon1
    ),
    Notification(
        id = 2,
        data = SimpleDateFormat("dd/MM/yyyy").parse("12/12/2024"),
        title = "Vacinação de animais",
        message = "A partir do dia 30 de junho, teremos vacinação gratuita para gatos e cachorros. Você pode ir até qualquer posto de vacinação com o seu pet.",
        readCheck = true,
        image = R.drawable.notification_icon1
    ),
    Notification(
        id = 3,
        data = SimpleDateFormat("dd/MM/yyyy").parse("12/12/2024"),
        title = "Ibama está na cidade",
        message = "O Ibama está na cidade para promover o quinto ciclo de palestras sobre preservação ambiental. Será no auditório Raquel de Queiroz às 10h.",
        readCheck = true,
        image = R.drawable.notification_icon2
    ),
    Notification(
        id = 4,
        data =SimpleDateFormat("dd/MM/yyyy").parse("11/12/2024"),
        title = "Castração de gatos gratuita",
        message = "A AMMA estará fornecendo castrações para felinos de forma gratuita para cidadãos que possuem qualquer tipo de vulnerabilidade social.",
        readCheck = true,
        image = R.drawable.notification_icon4
    ),
    Notification(
        id = 5,
        data =SimpleDateFormat("dd/MM/yyyy").parse("11/12/2024"),
        title = "Castração de gatos gratuita",
        message = "A AMMA estará fornecendo castrações para felinos de forma gratuita para cidadãos que possuem qualquer tipo de vulnerabilidade social.",
        readCheck = true,
        image = R.drawable.notification_icon4
    ),
    Notification(
        id = 6,
        data =SimpleDateFormat("dd/MM/yyyy").parse("11/12/2024"),
        title = "Castração de gatos gratuita",
        message = "A AMMA estará fornecendo castrações para felinos de forma gratuita para cidadãos que possuem qualquer tipo de vulnerabilidade social.",
        readCheck = true,
        image = R.drawable.notification_icon4
    ),
)

@Composable
fun NotificationScreen() {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, top = 32.dp)
            ) {
                IconButton(onClick = { /* ajustar a navigatiton futuramente */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        modifier = Modifier.size(48.dp)
                    )
                }

                Text(
                    text = "Notificações",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        },

        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NotificationList(notifications)
            }
        }
    )
}

@Composable
fun NotificationList(notifications: List<Notification>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(notifications) { notification ->
            NotificationItem(notification)
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 100.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = notification.image),
                contentDescription = "Ícone da Notificação",
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.LightGray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = notification.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = notification.message,
                    fontWeight = FontWeight.Light,
                    fontSize = 13.sp,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = dateFormatter.format(notification.data),
                    color = Color.Gray,
                    textAlign = TextAlign.End,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.End),
                )
            }
        }
    }
}
