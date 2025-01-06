package org.quixalert.br.domain.model
import java.time.Instant
import java.util.Date

data class Notification(
    override var id: String = "",
    val data: Date = Date.from(Instant.now()),
    val title: String = "",
    val message: String = "",
    val readCheck: Boolean = false,
    val imageUrl: String = ""
): BaseModel(id)