package org.quixalert.br.domain.model

import java.time.Instant

data class News(
    override var id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val iconUrl: String = "",
    val type: NewsType = NewsType.LOCAL,
    val date: String = Instant.now().toString()
): BaseModel(id)