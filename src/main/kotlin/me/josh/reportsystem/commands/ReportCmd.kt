package me.josh.reportsystem.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.ArgName
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import dev.triumphteam.cmd.core.annotation.Join
import dev.triumphteam.cmd.core.annotation.Optional
import me.josh.reportsystem.PluginMain
import me.josh.reportsystem.gui.impl.ReportReasonMenu
import me.josh.reportsystem.models.report.Report
import me.josh.reportsystem.util.ColorUtil
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.jetbrains.annotations.Nullable

@Command("report", alias = ["r", "rep"])
class ReportCmd : BaseCommand() {

    @Default
    fun execute(player: CommandSender, @ArgName("username") target: Player, @Optional @Join(" ") reason: String?) {
        if(player !is Player) return
        println(reason == null)
        if(reason == "") {
            println("Opening Report Menu for ${player.name}")
            ReportReasonMenu(player, target).open()
            return
        } else if(reason!!.length > 32) player.sendMessage(ColorUtil.color("&4&l(!) &r&cYour reason is too long!"))
        else {
            val reportManager = PluginMain.INSTANCE.reportManager
            val report = Report(player, target, reason)
            reportManager.createReport(report)
            player.sendMessage(ColorUtil.color(""))
        }


    }

}