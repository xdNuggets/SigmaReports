package me.josh.reportsystem

import dev.triumphteam.cmd.bukkit.BukkitCommandManager
import me.josh.reportsystem.listeners.JoinListener
import me.josh.reportsystem.managers.SQLManager
import me.josh.reportsystem.managers.player.PlayerManager
import me.josh.reportsystem.managers.report.ReportManager
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class PluginMain : JavaPlugin() {

    companion object {
        @JvmStatic
        lateinit var INSTANCE: PluginMain


    }

    lateinit var sqlManager: SQLManager
    lateinit var playerManager: PlayerManager
    lateinit var reportManager: ReportManager

    override fun onEnable() {
        config.options().copyDefaults(true)
        saveDefaultConfig()

        INSTANCE = this
        BukkitCommandManager.create(this)


        // Initialize managers
        try {
            sqlManager = SQLManager()
            sqlManager.connect()
        } catch (e: Exception) {
            println("Failed to intialize connection to MySQL Database")
            e.printStackTrace()
        }

        playerManager = PlayerManager()
        reportManager = ReportManager()
        // Add Managers
        sqlManager.addManager(playerManager)
        sqlManager.addManager(reportManager)

        // Create all tables
        sqlManager.createTables()


        // Register Listeners
        server.pluginManager.registerEvents(JoinListener(), this)
    }



    override fun onDisable() {
        sqlManager.disconnect();
    }


}