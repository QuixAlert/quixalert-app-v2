package org.quixalert.br.domain.model

import java.time.Instant

data class Message(
    override var id: String = "",
    val description: String = "",
    val timestamp: Long = Instant.now().toEpochMilli(),
    val userId: String = "",
    val adoptionId: String = "",
    val isFromAttendant: Boolean = false
) : BaseModel(id)