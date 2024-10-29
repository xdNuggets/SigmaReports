package me.josh.reportsystem.managers.report

import me.josh.reportsystem.gui.util.ReportsFilterType
import me.josh.reportsystem.managers.IManager
import me.josh.reportsystem.models.report.Report
import me.josh.reportsystem.models.report.ReportType
import org.bukkit.Bukkit
import java.util.*
import kotlin.collections.ArrayList

class ReportManager: IManager {

    override val tableName: String = "reports"
    override val tableQuery: String = "(type VARCHAR(10), reporter VARCHAR(128), reportedPlayer VARCHAR(128), reason VARCHAR(255), id VARCHAR(8), date BIGINT, PRIMARY KEY(id))"

    fun createReport(report: Report) {
        val ps = sql.connection.prepareStatement("INSERT INTO ${tableName} (id, reporter, reportedPlayer, reason, date, type) VALUES (?,?,?,?,?,?)")

        ps.setString(1, report.id)
        ps.setString(2, report.reporter.uniqueId.toString())
        ps.setString(3, report.reportedPlayer.uniqueId.toString())
        ps.setString(4, report.reason)
        ps.setLong(5, report.date)
        ps.setString(6, report.type.toString())

        ps.executeUpdate()
    }


    fun deleteAllReports() {
        val ps = sql.connection.prepareStatement("DELETE * FROM $tableName")
        ps.executeUpdate()
    }


    fun getReports(filter: ReportsFilterType) : List<Report> {
        val query: String = when(filter) {
            ReportsFilterType.ALL -> "SELECT * FROM reports"
            ReportsFilterType.NEWEST -> "SELECT * FROM reports ORDER BY date DESC"
            ReportsFilterType.PENDING -> "SELECT * FROM reports WHERE type='PENDING'"
            ReportsFilterType.ACCEPTED -> "SELECT * FROM reports WHERE type='ACCEPTED'"
            ReportsFilterType.DENIED -> "SELECT * FROM reports WHERE type='DENIED'"
            ReportsFilterType.USER -> "SELECT * FROM reports"
        }

        val ps = sql.connection.prepareStatement(query)
        val rs = ps.executeQuery()
        val reports = ArrayList<Report>()
        while(rs.next()) {
            val report = Report(
                ReportType.valueOf(rs.getString("type")),
                Bukkit.getPlayer(UUID.fromString(rs.getString("reporter"))),
                Bukkit.getPlayer(UUID.fromString(rs.getString("reportedPlayer"))),
                rs.getString("reason"),
                rs.getLong("date"),
                rs.getString("id")
            )
            reports.add(report)
        }

        return reports
    }

}