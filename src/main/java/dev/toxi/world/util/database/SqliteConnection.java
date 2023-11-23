package dev.toxi.world.util.database;

import dev.toxi.world.config.PluginConfig;
import dev.toxi.world.util.VersionUtil;
import org.bukkit.Bukkit;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SqliteConnection extends DatabaseConnection {

    private void connect(String file) {
        if (VersionUtil.getVersion() <= 8) {
            Bukkit.getLogger().log(Level.SEVERE, "[MyWorld | SQLite] ========================================================");
            Bukkit.getLogger().log(Level.SEVERE, "[MyWorld | SQLite] SQLite is not available in 1.8.");
            Bukkit.getLogger().log(Level.SEVERE, "[MyWorld | SQLite] Please consider using MySQL or disable the use_last_location option");
            Bukkit.getLogger().log(Level.SEVERE, "[MyWorld | SQLite] ========================================================");
            return;
        }

        synchronized (lock) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                Bukkit.getLogger().log(Level.SEVERE, "[MyWorld | SQLite] Drivers are not working properly");
                return;
            }
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + file);
                Bukkit.getLogger().log(Level.INFO, "[MyWorld | SQLite] Connected to local file database");
            } catch (SQLException e) {
                Bukkit.getLogger().log(Level.SEVERE, "[MyWorld | SQLite] Failed to connect with given server:");
                e.printStackTrace();
            }
        }
    }

    public void connect() {
        connect(PluginConfig.getSqliteFile());
    }
}
