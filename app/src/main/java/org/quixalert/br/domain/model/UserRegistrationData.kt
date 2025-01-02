package org.quixalert.br.domain.model

import android.net.Uri

data class UserRegistrationData(
    override var id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val birthDate: String = "",
    val username: String = "",
    val password: String = "",
    val profileImage: Uri? = null
): BaseModel(id)