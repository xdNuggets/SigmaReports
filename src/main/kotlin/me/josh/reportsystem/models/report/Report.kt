package me.josh.reportsystem.models.report

import jdk.jfr.internal.handlers.EventHandler.timestamp
import me.josh.reportsystem.PluginMain
import me.josh.reportsystem.managers.report.ReportManager
import org.bukkit.entity.Player
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.UUID


class Report(val type: ReportType, val reporter: Player, val reportedPlayer: Player, val reason: String, val date: Long, val id: String) {

    constructor(
        reporter: Player, reportedPlayer: Player, reason: String
    ) : this(ReportType.PENDING, reporter, reportedPlayer, reason, System.currentTimeMillis(), UUID.randomUUID().toString())

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
