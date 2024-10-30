package me.josh.reportsystem.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.ArgName
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import dev.triumphteam.cmd.core.annotation.Join
import dev.triumphteam.cmd.core.annotation.Optional
import me.josh.reportsystem.PluginMain
import me.josh.reportsystem.config.Config
import me.josh.reportsystem.config.Messages
import me.josh.reportsystem.gui.impl.ReportReasonMenu
import me.josh.reportsystem.models.report.Report
import me.josh.reportsystem.util.ColorUtil
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import org.jetbrains.annotations.Nullable

@Command("report", alias = ["r", "rep"])
class ReportCmd : BaseCommand() {

    private val config: FileConfiguration = PluginMain.INSTANCE.config

    @Default
    fun execute(player: CommandSender, @ArgName("username") target: Player, @Optional @Join(" ") reason: String?) {
        if(player !is Player) return

        if(reason == "") {
            println("Opening Report Menu for ${player.name}")
            ReportReasonMenu(player, target).open()
            return
        } else if(reason!!.length > config.getInt("reports.reason.max_length")) player.sendMessage(Config.getMessage(Messages.REASON_TOO_LONG))

        else if(reason.length < config.getInt("reports.reason.min_length")) player.sendMessage(Config.getMessage(Messages.REASON_NOT_LONG_ENOUGH))

        else {
            val reportManager = PluginMain.INSTANCE.reportManager
            val report = Report(player, target, reason)
            reportManager.createReport(report)
            player.sendMessage(ColorUtil.color("&aSuccessfully reported &e${target.name}&a for &e$reason"))
        }


    }

}