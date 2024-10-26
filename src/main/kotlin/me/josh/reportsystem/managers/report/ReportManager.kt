package me.josh.reportsystem.managers.report

import me.josh.reportsystem.managers.IManager
import me.josh.reportsystem.models.report.Report

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

        ps.executeQuery()



    }

}