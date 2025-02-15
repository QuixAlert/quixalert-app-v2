package org.quixalert.br.domain.model

data class Donation(
    override var id: String = "",
    val amount: Double = 0.0,
    val deliveryMethod: String? = null
): BaseModel(id)
