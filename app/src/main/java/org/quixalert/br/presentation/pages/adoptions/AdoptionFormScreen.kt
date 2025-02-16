package org.quixalert.br.presentation.pages.adoptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.quixalert.br.R
import org.quixalert.br.domain.model.AdoptionStatus
import org.quixalert.br.domain.model.AdoptionT
import org.quixalert.br.domain.model.Animal
import org.quixalert.br.presentation.pages.animal.AnimalDetailsViewModel
import org.quixalert.br.presentation.pages.home.IconTint
import java.time.Instant
import org.quixalert.br.services.FirebaseAuthService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.material3.SelectableDates
import org.quixalert.br.domain.model.User
import java.util.Calendar
import org.quixalert.br.utils.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdoptionFormScreen(
    onBackClick: (Animal?) -> Unit,
    onFormClick: () -> Unit,
    adoptionViewModel: AdoptionViewModel = hiltViewModel(),
    animalDetailsViewModel: AnimalDetailsViewModel = hiltViewModel(),
    firebaseAuthService: FirebaseAuthService,
    ) {
    val user = firebaseAuthService.getCurrentUser()
    val userId = user?.uid ?: ""
    var address by remember { mutableStateOf("") }
    var livingDescription by remember { mutableStateOf("") }
    var otherAnimals by remember { mutableStateOf("") }
    var monthlyIncome by remember { mutableStateOf("") }
    var householdDescription by remember { mutableStateOf("") }
    var adoptionReason by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )

    val adoptionUiState by adoptionViewModel.uiState.collectAsState()
    val animalUiState by animalDetailsViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        animalDetailsViewModel.loadPet()
    }

    if (adoptionUiState.submissionSuccess && !showSuccessDialog) {
        showSuccessDialog = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val animal = animalUiState.animal
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(onBackClick = { onBackClick(animal) })

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                if (animal != null) {
                    AsyncImage(
                        model = animal.image,
                        contentDescription = animal.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(y = (-20).dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (animal != null) {
                    Text(
                        text = animal.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(2f)) {
                        Text(
                            text = "Responsável atual",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.emergency_icon1),
                                contentDescription = "Prefeitura de Quixadá",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Text(text = "Prefeitura de Quixadá", fontSize = 16.sp, color = MaterialTheme.colorScheme.onBackground)
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
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
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Text(
                    text = "Formulário de Adoção",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.onSurfaceVariant, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Endereço", color = Color.Black) },
                        placeholder = { Text("Endereço completo", color = Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    OutlinedTextField(
                        value = livingDescription,
                        onValueChange = { livingDescription = it },
                        label = { Text("Descreva onde você mora", color = Color.Black) },
                        placeholder = { Text("Ex: moro em apartamento...", color = Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    OutlinedTextField(
                        value = otherAnimals,
                        onValueChange = { otherAnimals = it },
                        label = { Text("Já há outros animais onde você mora?", color = Color.Black) },
                        placeholder = { Text("Ex: Sim, tenho dois cachorros...", color = Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    OutlinedTextField(
                        value = monthlyIncome,
                        onValueChange = { monthlyIncome = it },
                        label = { Text("Quanto você ganha mensalmente?", color = Color.Black) },
                        placeholder = { Text("R$ 0,00", color = Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    OutlinedTextField(
                        value = householdDescription,
                        onValueChange = { householdDescription = it },
                        label = { Text("Descreva quem mora com você", color = Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    OutlinedTextField(
                        value = adoptionReason,
                        onValueChange = { adoptionReason = it },
                        label = { Text("Por que você quer adotar?", color = Color.Black) },
                        placeholder = { Text("Ex: quero adotar porque...", color = Color.Black) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    OutlinedTextField(
                        value = selectedDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "",
                        onValueChange = { },
                        label = { Text("Agendar visita presencial", color = Color.Black) },
                        placeholder = { Text("Selecione uma data para a visita", color = Color.Gray) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDatePicker = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                        readOnly = true,
                        enabled = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Selecionar data",
                                modifier = Modifier.clickable { showDatePicker = true }
                            )
                        }
                    )

                    if (showDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    val epochMillis = datePickerState.selectedDateMillis
                                    if (epochMillis != null) {
                                        val selectedLocalDate = DateUtils.fromEpochMillis(epochMillis)
                                        if (DateUtils.isValidFutureDate(selectedLocalDate)) {
                                            selectedDate = selectedLocalDate
                                            showDatePicker = false
                                        }
                                    }
                                }) {
                                    Text("OK", color = Color(0xFF269996))
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDatePicker = false }) {
                                    Text("Cancelar", color = Color(0xFF269996))
                                }
                            }
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }
                }

                Button(
                    onClick = {
                        if (address.isBlank() || adoptionReason.isBlank()) {
                            return@Button
                        }

                        val adoption = animal?.let {
                            AdoptionT(
                                animalId = animal.id,
                                status = AdoptionStatus.PENDING,
                                address = address,
                                livingDescription = livingDescription,
                                otherAnimals = otherAnimals,
                                monthlyIncome = monthlyIncome,
                                householdDescription = householdDescription,
                                adoptionReason = adoptionReason,
                                visitDate = selectedDate,
                                userId = userId
                            )
                        }
                        if (adoption != null) {
                            adoptionViewModel.submitAdoption(adoption)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 54.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (adoptionUiState.isSubmitting) Color.Gray else Color(0xFF269996)
                    ),
                    enabled = !adoptionUiState.isSubmitting
                ) {
                    if (adoptionUiState.isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Enviar Candidatura",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                adoptionViewModel.resetSubmissionSuccess()
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        onFormClick()
                        adoptionViewModel.resetSubmissionSuccess()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF269996)
                    )
                ) {
                    Text(text = "Ok")
                }
            },
            text = {
                Text(
                    text = "Solicitação enviada com sucesso!",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        )
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