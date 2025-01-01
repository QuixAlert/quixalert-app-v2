package org.quixalert.br.domain.model

data class Report(
    override var id: String = "",
    val title: String = "",
    val image: String = "",
    val icon: String = "",
    val date: String = ""
): BaseModel(id)
