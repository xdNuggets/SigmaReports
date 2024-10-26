package me.josh.reportsystem.managers

import me.josh.reportsystem.PluginMain
import org.bukkit.configuration.file.FileConfiguration
import java.sql.Connection
import java.sql.DriverManager
import java.util.logging.Logger
import kotlin.properties.Delegates

class SQLManager {

    private var managers = mutableListOf<IManager>()
    lateinit var connection: Connection
    private val config: FileConfiguration = PluginMain.INSTANCE.config

    private var host: String
    private var port: Int
    private var database: String
    private var username: String
    private var password: String



    init {
        println("Intializing SQL Manager")
        host = config.getString("database.host")
        port = config.getInt("database.port")
        database = config.getString("database.name")
        username = config.getString("database.username")
        password = ""
        println("Connecting to MySQL")
        println("$host, $port, $database, $username, $password")
    }


    fun addManager(manager: IManager) {
        managers.add(manager)
    }

    fun connect() {
        if(!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                    host + ":" + port + "/" + database + "?useSSL=false",
                username, password)
        } else {
            println("MySQL is already connected!")
        }
    }

    fun disconnect() {
        if(isConnected()) {
            connection.close()
            println("Successfully closed connection to MySQL database!")
        } else {
            println("MySQL is not connected!")
        }
    }

    private fun isConnected(): Boolean {
        return connection.isClosed
    }


    fun createTables() {
        managers.forEach { manager ->
            val ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ${manager.tableName} {$manager.tableQuery}")
            ps.executeUpdate();
        }
    }
}