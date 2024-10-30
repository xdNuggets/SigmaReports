package me.josh.reportsystem

import dev.triumphteam.cmd.bukkit.BukkitCommandManager
import me.josh.reportsystem.commands.ClearReportsCmd
import me.josh.reportsystem.commands.ReportCmd
import me.josh.reportsystem.commands.ReportsCmd
import me.josh.reportsystem.gui.impl.AllReportsMenu
import me.josh.reportsystem.listeners.JoinListener
import me.josh.reportsystem.listeners.MenuListener
import me.josh.reportsystem.managers.SQLManager
import me.josh.reportsystem.managers.player.PlayerManager
import me.josh.reportsystem.managers.report.ReportManager
import org.bukkit.Bukkit

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


        // Checks if PlaceholderAPI is present
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            logger.warning("Could not find PlaceholderAPI! This plugin is required.")
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        INSTANCE = this
        val commandManager = BukkitCommandManager.create(this)

        // Initialize managers
        try {
            sqlManager = SQLManager()
            sqlManager.connect()

            playerManager = PlayerManager()
            reportManager = ReportManager()

            if (sqlManager.isConnected()) {
                println("SQL is connected. Adding managers")
                // Add Managers
                sqlManager.addManager(playerManager)
                sqlManager.addManager(reportManager)

                // Create all tables
                sqlManager.createTables()
            } else {
                println("Could not connect to the MySQL database. Tables will not be created.")
            }
        } catch (e: Exception) {
            println("Failed to initialize connection to MySQL Database")
            e.printStackTrace()
        }

        commandManager.registerCommand(ReportCmd())
        commandManager.registerCommand(ClearReportsCmd())
        commandManager.registerCommand(ReportsCmd())




        // Register Listeners
        server.pluginManager.registerEvents(JoinListener(), this)
        server.pluginManager.registerEvents(MenuListener(), this)
    }



    override fun onDisable() {
        if (sqlManager.isConnected()) {
            sqlManager.disconnect()
        }
    }


}