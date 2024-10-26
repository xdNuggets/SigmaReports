package me.josh.reportsystem.gui

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.BaseGui
import me.josh.reportsystem.PluginMain
import me.josh.reportsystem.managers.report.ReportManager
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


abstract class Menu(val player: Player) {




    open lateinit var gui: BaseGui
    val reportManager: ReportManager = PluginMain.INSTANCE.reportManager
    val FILLER_GLASS = ItemBuilder.from(ItemStack(Material.STAINED_GLASS_PANE, 1, 7.toShort())).color(org.bukkit.Color.BLACK).name(
        Component.text(" "))
        .asGuiItem { event ->
        event.isCancelled = true
    }


    fun open() {
        gui.open(player)
    }

    open fun setItems() {}
    open fun getInventory() : Inventory {return gui.inventory}

    fun setBorder(size: Int) {
        if (size % 9 != 0 || size > 54) return

        for (i in 0..9) {
            if (gui.inventory.getItem(i) == null) {
                gui.setItem(i, FILLER_GLASS)
            }
        }

        for (i in size - 9 .. size) {
            if (gui.inventory.getItem(i) == null) {
                gui.setItem(i, FILLER_GLASS)
            }
        }
        for (i in 9 .. size - 9 step 9) {
            if (gui.inventory.getItem(i) == null) {
                gui.setItem(i, FILLER_GLASS)
            }
            if (gui.inventory.getItem(i + 8) == null) {
                gui.setItem(i + 8, FILLER_GLASS)
            }
        }
    }




}