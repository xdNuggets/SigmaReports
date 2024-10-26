package me.josh.reportsystem.listeners

import me.josh.reportsystem.PluginMain
import me.josh.reportsystem.managers.player.PlayerManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener : Listener {

    val playerManager: PlayerManager = PluginMain.INSTANCE.playerManager
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        playerManager.createPlayer(event.player)
    }
}