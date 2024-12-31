package org.quixalert.br.domain.model

data class News(
    val id: String,
    val title: String,
    val image: String,
    val icon: String,
    val isLocal: Boolean = false
)