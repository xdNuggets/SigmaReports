package me.josh.reportsystem.managers.player

import me.josh.reportsystem.managers.IManager
import org.bukkit.entity.Player

class PlayerManager : IManager {
    override val tableName: String = "players"
    override val tableQuery: String = "(uuid VARCHAR(128), discord_id BIGINT, PRIMARY KEY(uuid))"

    /**
     * Creates a new player entity in the database
     * @param player The player which should be added to the database
     */
    fun createPlayer(player: Player) {
        if(exists(player)) return

        val ps = sql.connection.prepareStatement("INSERT INTO ${tableName} (uuid, discord_id) VALUES (?, NULL)")
        ps.setString(1, player.uniqueId.toString())
        ps.executeUpdate()

        println("Player ${player.name} joined for the first time. Added to database.")
    }

    /**
     * Checks whether the player exists
     * @return if the player exists
     */
    fun exists(player: Player): Boolean {
        val ps = sql.connection.prepareStatement("SELECT * FROM players WHERE uuid=?")
        ps.setString(1, player.uniqueId.toString())
        val rs = ps.executeQuery()
        return rs.next()
    }
}