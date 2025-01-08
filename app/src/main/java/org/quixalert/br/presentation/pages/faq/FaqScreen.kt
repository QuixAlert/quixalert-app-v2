package org.quixalert.br.presentation.pages.faq

import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun FaqScreen() {
    val faqs = listOf(
        "Quem é a AMMA?" to "A AMMA é uma organização do município de Quixadá que visa  promover a preservação e a gestão sustentável dos recursos naturais em Quixadá, por meio da aplicação rigorosa das leis ambientais e do engajamento ativo da comunidade, visando um ambiente saudável para as gerações presentes e futuras.",
        "O que é o QuixAlert?" to "Somos uma plataforma intuitiva e fácil de usar para que os usuários possam adotar animais, solicitar documentos e denunciar problemas ambientais em Quixadá.",
        "O que acontece depois que faço uma denúncia no QuixAlert?" to "Quando uma denúncia é realizada, os servidores da AMMA são alertados sobre sua denúncia e as medidas cabíveis serão tomadas",
        "O QuixAlert é acessível para todos os cidadãos?" to "Sim, o QuixAlert foi desenvolvido para ser acessível a todos os cidadãos, independentemente da faixa etária ou nível de habilidade com tecnologia.",
        "Existe algum custo para usar o QuixAlert?" to "O QuixAlert é totalmente gratuito para os usuários."
    )

    LazyColumn(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 82.dp, top = 32.dp)
            .fillMaxHeight()
    ) {
        item {
            Text(
                text = "Perguntas Frequentes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        item {
            faqs.forEach { faq ->
                FaqItem(question = faq.first, answer = faq.second)
            }
        }
    }
}

@Composable
fun FaqItem(question: String, answer: String) {
    val expanded = remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clickable { expanded.value = !expanded.value },
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = question, style = MaterialTheme.typography.bodyLarge)

            androidx.compose.animation.AnimatedVisibility(
                visible = expanded.value,
                enter = fadeIn() + expandVertically(),
            ) {
                Text(
                    text = answer,
                    modifier = Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
