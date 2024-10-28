package me.josh.reportsystem.listeners

import me.josh.reportsystem.gui.Menu
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class MenuListener : Listener {



    @EventHandler
    fun onMenuClick(e: InventoryClickEvent) {
        if(e.inventory.holder is Menu) {
            (e.inventory.holder as Menu).open(e.whoClicked as Player)
        }
    }
}