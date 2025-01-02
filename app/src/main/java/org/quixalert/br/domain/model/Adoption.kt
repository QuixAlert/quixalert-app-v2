package org.quixalert.br.domain.model

import java.time.LocalDate

data class Adoption(
    override var id: String = "",
    val petName: String = "",
    val petImage: String = "",
    val petIcon: String = "",
    val status: AdoptionStatus = AdoptionStatus.PENDING,
    val address: String = "",
    val livingDescription: String = "",
    val otherAnimals: String = "",
    val monthlyIncome: String = "",
    val householdDescription: String = "",
    val adoptionReason: String = "",
    val visitDate: LocalDate? = null
) : BaseModel(id)
