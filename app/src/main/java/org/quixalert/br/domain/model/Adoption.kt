package org.quixalert.br.domain.model

data class Adoption(
    override var id: String = "",
    val petName: String = "",
    val petImage: String = "",
    val petIcon: String = "",
    val status: AdoptionStatus = AdoptionStatus.PENDING
): BaseModel(id)