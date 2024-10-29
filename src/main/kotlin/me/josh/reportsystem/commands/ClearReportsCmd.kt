package me.josh.reportsystem.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import me.josh.reportsystem.PluginMain
import org.bukkit.command.CommandSender

/**
 * A command only meant to be used for debug/admin purposes.
 */

@Command("clearrep")
class ClearReportsCmd : BaseCommand() {

    @Default
    fun execute(sender: CommandSender) {
        PluginMain.INSTANCE.reportManager.deleteAllReports()
        sender.sendMessage("Deleted all reports")
    }
}