package me.josh.reportsystem.managers.player

import me.josh.reportsystem.managers.IManager
import org.bukkit.entity.Player

class PlayerManager : IManager {
    override val tableName: String = "players"
    override val tableQuery: String = "(uuid VARCHAR(128), PRIMARY KEY(uuid))"


    fun createPlayer(player: Player) {
        if(exists(player)) return

        val ps = sql.connection.prepareStatement("INSERT INTO ${tableName} (uuid) VALUES (${player.uniqueId})")
        ps.executeQuery()
    }

    fun exists(player: Player): Boolean {
        val ps = sql.connection.prepareStatement("SELECT * FROM users WHERE uuid=?")
        ps.setString(1, player.uniqueId.toString())
        val rs = ps.executeQuery()
        return rs.next()
    }
}