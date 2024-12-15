package org.quixalert.br.model

import android.net.Uri

data class UserRegistrationData(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val birthDate: String = "",
    val username: String = "",
    val password: String = "",
    val profileImage: Uri? = null
) 