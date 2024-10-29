package me.josh.reportsystem.config

import me.josh.reportsystem.PluginMain
import org.bukkit.configuration.file.FileConfiguration

class Config {

    companion object {
        private val config: FileConfiguration = PluginMain.INSTANCE.config
        fun getReasons(): List<Map<String, Map<String, String>>> {
            val reasons = config.getMapList("gui.report_reasons")
            return reasons as List<Map<String, Map<String, String>>>
        }

        fun getTimeZone(): String {
            return config.getString("reports.timezone")
        }
    }
}