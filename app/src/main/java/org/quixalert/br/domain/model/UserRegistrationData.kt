package org.quixalert.br.domain.model

data class UserRegistrationData(
    override var id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val birthDate: String = "",
    val username: String = "",
    val password: String = "",
    val profileImage: String = ""
): BaseModel(id)