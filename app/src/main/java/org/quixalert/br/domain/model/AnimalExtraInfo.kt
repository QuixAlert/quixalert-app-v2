package org.quixalert.br.domain.model

data class AnimalExtraInfo(
    val gallery: List<String> = emptyList(),
    val videos: List<String> = emptyList(),
    val location: String = ""
)