package me.josh.reportsystem.config

import me.clip.placeholderapi.PlaceholderAPI
import me.josh.reportsystem.PluginMain
import me.josh.reportsystem.util.ColorUtil
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player

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

        fun getMessage(message: Messages) : String {
            return ColorUtil.color(config.getString(message.path))
        }

        fun getMessage(player: Player, message: Messages) : String {
            return ColorUtil.color(PlaceholderAPI.setPlaceholders(player, config.getString(message.path)))
        }

    }
}