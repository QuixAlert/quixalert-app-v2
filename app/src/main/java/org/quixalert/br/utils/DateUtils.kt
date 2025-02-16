package org.quixalert.br.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DateUtils {
    private val BRAZIL_ZONE_ID = ZoneId.of("America/Sao_Paulo")
    private val DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun fromEpochMillis(epochMillis: Long): LocalDate {
        return Instant.ofEpochMilli(epochMillis)
            .atZone(BRAZIL_ZONE_ID)
            .toLocalDate()
            .plusDays(1)
    }

    fun formatDate(date: LocalDate?): String {
        return date?.format(DISPLAY_FORMAT) ?: "N/A"
    }

    fun calculateDaysOpen(date: LocalDate?): String {
        return date?.let { 
            val diff = ChronoUnit.DAYS.between(date, LocalDate.now())
            "$diff dias"
        } ?: "N/A"
    }

    fun calculateDeadline(date: LocalDate?): String {
        return date?.plusDays(30)?.format(DISPLAY_FORMAT) ?: "N/A"
    }

    fun isValidFutureDate(date: LocalDate): Boolean {
        return !date.isBefore(LocalDate.now())
    }
} 