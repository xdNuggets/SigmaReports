package me.josh.reportsystem.models.report


import me.josh.reportsystem.PluginMain
import me.josh.reportsystem.managers.report.ReportManager
import me.josh.reportsystem.models.report.util.ReportType
import org.bukkit.entity.Player
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID

/**
 * Represents a report submitted by a player against another player in the system.
 * A report contains details such as the type, reason, reporter, reported player, timestamp, and unique ID.
 * Additionally, the class provides methods for handling the report's state and formatting the date and time details.
 *
 * @param type The type of report (default is PENDING) (see [ReportType])
 * @param reporter The player who created the report
 * @param reportedPlayer The player who was reported by `reporter`
 * @param reason The reason for the report
 * @param date The timestamp (in milliseconds) when the report was created
 * @param id The unique identifier for the report
 */
data class Report(val type: ReportType, val reporter: Player, val reportedPlayer: Player, val reason: String, val date: Long, val id: String) {

    constructor(
        reporter: Player, reportedPlayer: Player, reason: String
    ) : this(ReportType.PENDING, reporter, reportedPlayer, reason, System.currentTimeMillis(), UUID.randomUUID().toString().substring(0,7))

    val reportManager: ReportManager = PluginMain.INSTANCE.reportManager


    fun accept() {

    }

    fun deny() {

    }

    private fun getTimeElapsed(): Long {
        return System.currentTimeMillis() - date
    }

    fun getFormattedTime(): String {
        val instant: Instant = Instant.ofEpochMilli(date)
        return DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm")
            .format(LocalDateTime.ofInstant(instant, ZoneId.of("UTC")))
    }

    /**
     * Gets the formatted time since the report was created
     * @return A formatted string of the time since the report was created
     */
    fun getFormattedTimeElapsed(): String {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault())
        val now = LocalDateTime.now()

        val duration: Duration = Duration.between(dateTime, now)
        val days: Long = duration.toDays()
        val hours: Long = duration.toHours() % 24
        val minutes: Long = duration.toMinutes() % 60

        val result = StringBuilder()
        if (days > 0) result.append(days).append(" days, ")
        if (hours > 0 || days > 0) result.append(hours).append(" hours, ")
        result.append(minutes).append(" minutes ago")

        return result.toString()
    }
}
