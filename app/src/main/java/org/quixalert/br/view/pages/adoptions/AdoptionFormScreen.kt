package org.quixalert.br.view.pages.adoptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.quixalert.br.view.pages.animal.PetDetail
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdoptionFormScreen(pet: PetDetail, onBackClick: () -> Unit) {
    var address by remember { mutableStateOf("") }
    var livingDescription by remember { mutableStateOf("") }
    var otherAnimals by remember { mutableStateOf("") }
    var monthlyIncome by remember { mutableStateOf("") }
    var householdDescription by remember { mutableStateOf("") }
    var adoptionReason by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

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
                    onClick = onBackClick,
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
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Pet Name
                Text(
                    text = pet.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                // Responsible and Status Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(2f)) {
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
                                model = pet.responsibleIcon,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                            Text(text = "Prefeitura de Quixadá", fontSize = 16.sp)
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
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
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Form Title
                Text(
                    text = "Formulário de Adoção",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )

                // Form Fields
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Address
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Endereço") },
                        placeholder = { Text("Endereço completo") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFEEEEEE)
                        )
                    )

                    // Living Description
                    OutlinedTextField(
                        value = livingDescription,
                        onValueChange = { livingDescription = it },
                        label = { Text("Descreva onde você mora") },
                        placeholder = { Text("Ex: moro em apartamento...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFEEEEEE)
                        )
                    )

                    // Other Animals
                    OutlinedTextField(
                        value = otherAnimals,
                        onValueChange = { otherAnimals = it },
                        label = { Text("Já há outros animais onde você mora?") },
                        placeholder = { Text("Ex: Sim, tenho dois cachorros...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFEEEEEE)
                        )
                    )

                    // Monthly Income
                    OutlinedTextField(
                        value = monthlyIncome,
                        onValueChange = { monthlyIncome = it },
                        label = { Text("Quanto você ganha mensalmente?") },
                        placeholder = { Text("R$ 0,00") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFEEEEEE)
                        )
                    )

                    // Household Description
                    OutlinedTextField(
                        value = householdDescription,
                        onValueChange = { householdDescription = it },
                        label = { Text("Descreva quem mora com você") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFEEEEEE)
                        )
                    )

                    // Adoption Reason
                    OutlinedTextField(
                        value = adoptionReason,
                        onValueChange = { adoptionReason = it },
                        label = { Text("Por que você quer adotar?") },
                        placeholder = { Text("Ex: quero adotar porque...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFEEEEEE)
                        )
                    )

                    // Visit Date
                    OutlinedTextField(
                        value = selectedDate?.toString() ?: "",
                        onValueChange = { },
                        label = { Text("Agendar visita presencial") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFEEEEEE)
                        ),
                        readOnly = true,
                        enabled = false
                    )

                    // Date Picker Dialog
                    if (showDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    showDatePicker = false
                                    // Handle date selection
                                }) {
                                    Text("OK")
                                }
                            }
                        ) {
                            DatePicker(
                                state = rememberDatePickerState()
                            )
                        }
                    }
                }

                // Submit Button
                Button(
                    onClick = { /* Handle form submission */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF269996)
                    )
                ) {
                    Text(
                        text = "Enviar Candidatura",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}