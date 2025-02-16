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

    // New version that accepts a date string (ISO-8601 format)
    fun formatDate(dateString: String?): String {
        return try {
            dateString?.let {
                LocalDate.parse(it).format(DISPLAY_FORMAT)
            } ?: "N/A"
        } catch (e: Exception) {
            "N/A"
        }
    }

    fun calculateDaysOpen(dateString: String?): String {
        return try {
            dateString?.let {
                val date = LocalDate.parse(it)
                val diff = ChronoUnit.DAYS.between(date, LocalDate.now())
                "${kotlin.math.abs(diff)} dias"
            } ?: "N/A"
        } catch (e: Exception) {
            "N/A"
        }
    }

    fun calculateDeadline(dateString: String?): String {
        return try {
            dateString?.let {
                val date = LocalDate.parse(it)
                date.plusDays(30).format(DISPLAY_FORMAT)
            } ?: "N/A"
        } catch (e: Exception) {
            "N/A"
        }
    }

    fun isValidFutureDate(date: LocalDate): Boolean {
        return !date.isBefore(LocalDate.now())
    }
}