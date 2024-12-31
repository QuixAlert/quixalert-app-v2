package org.quixalert.br.domain.model

data class EmergencyNumber (
    val id: Int,
    val name: String,
    val image: Int,
    val phone: String,
    val openingHours: String
)