package org.quixalert.br.utils

import java.util.Calendar

fun isDarkModeActive(): Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return currentHour >= 18 || currentHour < 6
}