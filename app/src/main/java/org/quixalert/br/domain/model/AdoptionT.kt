package org.quixalert.br.domain.model

import java.time.LocalDate

data class AdoptionT(
    override var id: String = "",
    val animalId: String = "",
    val status: AdoptionStatus = AdoptionStatus.PENDING,
    val address: String = "",
    val livingDescription: String = "",
    val otherAnimals: String = "",
    val monthlyIncome: String = "",
    val householdDescription: String = "",
    val adoptionReason: String = "",
    val visitDate: String = LocalDate.now().toString(),
    val userId: String = "",
    val animal: Animal? = null
) : BaseModel(id)