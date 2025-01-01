package org.quixalert.br.domain.model

data class User(
    override var id: String = "",
    val name: String = "",
    val greeting: String = "",
    val profileImage: String = ""
): BaseModel(id)