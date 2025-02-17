package org.quixalert.br.domain.model

data class Report(
    override var id: String = "",
    val title: String = "",
    val image: String = "",
    val icon: String = "",
    val date: String = "",
    val type: ReportType = ReportType.AMBIENTAL,
    val description: String = "",
    val address: String = "",
    val motivation: String = "",
    val details: String = "",
    val userId: String = "",
    val ratings: List<Rating> = emptyList()
): BaseModel(id)




