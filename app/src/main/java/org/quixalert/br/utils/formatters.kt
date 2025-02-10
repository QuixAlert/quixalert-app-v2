package org.quixalert.br.utils

fun Long.formatMessageTime(): String {
    val messageDate = java.util.Date(this)
    val now = java.util.Date()

    val diff = now.time - messageDate.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        days > 0 -> "$days d atrás"
        hours > 0 -> "$hours h atrás"
        minutes > 0 -> "$minutes min atrás"
        else -> "Agora"
    }
}