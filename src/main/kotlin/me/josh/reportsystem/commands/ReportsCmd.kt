package me.josh.reportsystem.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import me.josh.reportsystem.gui.impl.AllReportsMenu
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Command("reports", alias = ["reps"])
class ReportsCmd : BaseCommand() {

    @Default
    fun execute(sender: CommandSender) {
        AllReportsMenu(sender as Player).open()
    }
}