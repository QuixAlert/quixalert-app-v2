package org.quixalert.br.domain.model

data class EmergencyNumber (
    override var id: String = "",
    val name: String = "",
    val image: Int = 0,
    val phone: String = "",
    val openingHours: String = ""
): BaseModel(id)