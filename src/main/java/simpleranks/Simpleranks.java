package simpleranks;

import org.bukkit.plugin.java.JavaPlugin;
import simpleranks.utils.config.DefaultConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;

public final class Simpleranks extends JavaPlugin {

    public static String data;
    public static Simpleranks instance;
    @Override
    public void onEnable() {
        instance = this;
        data = this.getDataFolder().getPath();

        initFiles();
        DefaultConfiguration.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initFiles() {
        if (!new File(data).exists()) {
            new File(data).mkdir();
        }
    }

}
