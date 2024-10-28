package me.josh.reportsystem.gui.impl

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.BaseGui
import dev.triumphteam.gui.guis.Gui
import me.josh.reportsystem.config.Config
import me.josh.reportsystem.gui.Menu
import me.josh.reportsystem.models.report.Report
import me.josh.reportsystem.util.ColorUtil
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag

class ReportReasonMenu(player: Player, private val target: Player) : Menu(player) {

    override var gui: BaseGui = Gui
        .gui()
        .title(Component.text("Select a reason to report ${target.name}"))
        .rows(6)
        .create()


    override fun setItems() {
        val reportReasons = Config.getReasons()

        for(reason in reportReasons) {
            reason.forEach {
                (key, value) ->
                    val name = value["name"]!!
                    val description = value["description"]!!
                    val item = ItemBuilder.from(Material.valueOf(key))
                        .name(Component.text(ColorUtil.color("&e${name}")))
                        .lore(getItemDescription(description))
                        .flags(ItemFlag.HIDE_ATTRIBUTES)
                        .asGuiItem { event ->
                            event.isCancelled = true
                            val report = Report(player, target, name)
                            reportManager.createReport(report)
                            player.sendMessage(ColorUtil.color("&aSuccessfully reported &e${target.name}&a for &e${name}"))
                        }

                    gui.addItem(item)
            }
        }

    }

    private fun getItemDescription(description: String): List<Component> {
        return listOf<Component>(
            Component.text(
                ColorUtil.color("&8Report them for ${description}")),
            Component.text(""),
            Component.text(""),
            Component.text(ColorUtil.color("&7(Click to report)"))
        )
    }

}