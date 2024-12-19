package org.quixalert.br.model
import java.util.Date

data class Notification(
    val id: Int,
    val data: Date,
    val title: String,
    val message: String,
    val readCheck: Boolean,
    val image: Int,
    //val user: User
)