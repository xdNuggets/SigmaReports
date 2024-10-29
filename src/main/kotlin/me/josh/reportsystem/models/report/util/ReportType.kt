package me.josh.reportsystem.models.report.util


/**
 * The states a report can have
 * @param PENDING the default state of a report
 * @param ACCEPTED This report has been accepted by a moderator
 * @param DENIED This report has been denied by a moderator
 */
enum class ReportType {

    PENDING,
    ACCEPTED,
    DENIED
}