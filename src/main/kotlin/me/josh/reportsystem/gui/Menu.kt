package me.josh.reportsystem.gui

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.BaseGui
import dev.triumphteam.gui.guis.PaginatedGui
import me.josh.reportsystem.PluginMain
import me.josh.reportsystem.managers.report.ReportManager
import me.josh.reportsystem.util.ColorUtil
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
        setItems()
        gui.setDefaultClickAction {
            event -> event.isCancelled = true
        }
        gui.open(player)
    }

    fun open(p: Player) {
        setItems()
        gui.open(p)
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

        for (i in size - 9 until size) {
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

    fun addPaginatedItems() {
        if(gui !is PaginatedGui) return

        val previousPageItem = ItemBuilder.from(Material.WOOD_BUTTON).name(ColorUtil.component("&aPrevious Page"))
            .asGuiItem {
                _ -> (gui as PaginatedGui).previous()
            }

        val nextPageItem = ItemBuilder.from(Material.WOOD_BUTTON).name(ColorUtil.component("&aNext Page"))
            .asGuiItem {
                    _ -> (gui as PaginatedGui).next()
            }

        val closeItem = ItemBuilder.from(Material.BARRIER).name(ColorUtil.component("&cClose"))
            .asGuiItem {
                _ -> gui.close(player)
            }

        gui.setItem(48, previousPageItem)
        gui.setItem(49, closeItem)
        gui.setItem(50, nextPageItem)
    }




}