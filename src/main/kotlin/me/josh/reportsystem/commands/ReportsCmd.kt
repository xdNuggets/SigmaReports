package me.josh.reportsystem.commands

import dev.triumphteam.cmd.core.BaseCommand
import dev.triumphteam.cmd.core.annotation.Command
import dev.triumphteam.cmd.core.annotation.Default
import org.bukkit.command.CommandSender

@Command("reports", alias = ["reps"])
class ReportsCmd : BaseCommand() {

    @Default
    fun execute(sender: CommandSender) {

    }
}