package org.quixalert.br.domain.model

data class News(
    override var id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val iconUrl: String = "",
    val isLocal: Boolean = false,
    val type: NewsType = NewsType.LOCAL
): BaseModel(id)