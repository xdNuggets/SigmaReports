package me.josh.reportsystem.managers

import me.josh.reportsystem.PluginMain
import org.bukkit.configuration.file.FileConfiguration

interface IManager {

    val config: FileConfiguration
        get() = PluginMain.INSTANCE.config

    val tableName: String
    val tableQuery: String
    val sql: SQLManager
        get() = PluginMain.INSTANCE.sqlManager
}