package org.quixalert.br.model

data class Adoption(
    val id: String,
    val petName: String,
    val petImage: String,
    val petIcon: String,
    val status: AdoptionStatus
)

enum class AdoptionStatus {
    PENDING,
    APPROVED,
    REJECTED
}