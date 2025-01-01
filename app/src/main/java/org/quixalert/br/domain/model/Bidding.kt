package org.quixalert.br.domain.model

data class Bidding(
    override var id: String = "",
    val title: String = "",
    val pdfUrl: String = "",
    val image: String = "",
    val date: String = ""
): BaseModel(id)
