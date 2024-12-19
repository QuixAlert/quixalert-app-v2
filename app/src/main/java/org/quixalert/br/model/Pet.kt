package org.quixalert.br.model

data class Pet(
    val id: String,
    val name: String,
    val image: String,
    val gender: Gender,
    val type: PetType
)

enum class Gender {
    MALE, FEMALE
}

enum class PetType {
    DOG, CAT
}