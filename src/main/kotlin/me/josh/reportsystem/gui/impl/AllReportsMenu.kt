package me.josh.reportsystem.gui.impl

import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.components.GuiAction
import dev.triumphteam.gui.guis.BaseGui
import dev.triumphteam.gui.guis.Gui
import dev.triumphteam.gui.guis.GuiItem
import dev.triumphteam.gui.guis.PaginatedGui
import me.josh.reportsystem.gui.Menu
import me.josh.reportsystem.models.report.util.ReportsFilterType
import me.josh.reportsystem.models.report.Report
import me.josh.reportsystem.util.ColorUtil
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class AllReportsMenu(player: Player) : Menu(player) {

    private val filters = listOf(ReportsFilterType.PENDING, ReportsFilterType.ALL, ReportsFilterType.ACCEPTED, ReportsFilterType.DENIED, ReportsFilterType.NEWEST)
    private var filterIndex = 0

    override var gui: BaseGui = Gui.paginated().title(ColorUtil.component("&fReports (&7${filters[filterIndex].name}&f)"))
        .rows(6)
        .create()


    override fun setItems() {
        gui.setDefaultClickAction {
            event -> event.isCancelled = true
            gui.updateTitle("Reports (${filters[filterIndex].name})")
            gui.update()
        }
        setBorderItems()
        val reports = reportManager.getReports(filters[filterIndex])
        for(report in reports) {
            val reportItem = createReportBook(report)
            gui.addItem(reportItem)
        }
    }




    private fun setBorderItems() {
        setBorder(gui.rows * 9)
        addPaginatedItems()
        gui.setItem(53, filterBookItem)
    }


    private val filterBookAction = GuiAction<InventoryClickEvent> { _ ->
        run {
            if(filterIndex == filters.size - 1) filterIndex = 0 else filterIndex++
            updateFilterBook()
            updateGUI()
            player.sendMessage(ColorUtil.color("&aSet filter to &e${filters[filterIndex]}"))
        }
    }

    private var filterItemName = ColorUtil.component("&7Current Filter: &e${filters[filterIndex]}")
    private var filterBookItem = ItemBuilder.from(Material.BOOK_AND_QUILL)
        .name(filterItemName)
        .lore(getFilterBookLore())
        .asGuiItem(filterBookAction)


    private fun getFilterBookLore(): List<Component> {
        val lore = ArrayList<Component>()
        for(filter in filters) {
            if(filter == filters[filterIndex]) {
                lore.add(ColorUtil.component("&f○ &a&l$filter"))
            } else{
                lore.add(ColorUtil.component("&f● &c$filter"))
            }
        }

        lore.add(ColorUtil.component(""))
        lore.add(ColorUtil.component("&7Click to change the filter"))

        return lore
    }

    // Rewrite to work with USER filter type
    fun createReportBook(report: Report) : GuiItem {

        val item = ItemBuilder.from(Material.BOOK)
            .name(ColorUtil.component("&e${report.id}"))
            .lore(listOf(
                ColorUtil.component("&7Reported User: &e${report.reportedPlayer.name}"),
                ColorUtil.component("&7Reporter: &e${report.reporter.name}"),
                ColorUtil.component("&7Reason: &e${report.reason}"),
                ColorUtil.component("&7Status: &e${report.type.name}"),
                ColorUtil.component(""),
                ColorUtil.component("&7Reported on ${report.getFormattedTime()} &8${report.getFormattedTimeElapsed()}")
            ))
            .asGuiItem {
                _ ->
                run {
                    player.sendMessage("pressed")
                }
            }
        return item
    }

    private fun updateFilterBook() {
        filterItemName = ColorUtil.component("&7Current Filter: &e${filters[filterIndex]}")
        filterBookItem = ItemBuilder.from(Material.BOOK_AND_QUILL)
            .name(filterItemName)
            .lore(getFilterBookLore())
            .asGuiItem(filterBookAction)
        gui.updateItem(53, filterBookItem)
        gui.update()
    }

    private fun updateGUI() {

        (gui as PaginatedGui).clearPageItems()
        val reports = reportManager.getReports(filters[filterIndex])
        for(report in reports) {
            gui.addItem(createReportBook(report))
        }
        gui.updateTitle(ColorUtil.color("&fReports (&7${filters[filterIndex].name}&f)"))
        gui.update()

    }
}