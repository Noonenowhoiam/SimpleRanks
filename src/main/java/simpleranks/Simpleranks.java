package simpleranks;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import simpleranks.commands.RankCommand;
import simpleranks.commands.SimpleRanksCommand;
import simpleranks.listeners.PlayerChatListener;
import simpleranks.listeners.PlayerJoinListener;
import simpleranks.listeners.PlayerLeaveListener;
import simpleranks.system.ScoreboardSystem;
import simpleranks.utils.Database;
import simpleranks.utils.JsonManager;
import simpleranks.utils.PlayerRank;
import simpleranks.utils.config.ConfigValue;
import simpleranks.utils.config.DefaultConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Map;

public final class Simpleranks extends JavaPlugin {

    public static String data;
    public static Simpleranks instance;
    @Override
    public void onEnable() {
        instance = this;
        data = this.getDataFolder().getPath();

        initListeners();
        initCommands();
        initFiles();
        Database.init();
        PlayerRank.init();
        DefaultConfiguration.init();
        ScoreboardSystem.reloadAll();

        Scheduler.start();
    }

    public void initListeners() {
        PluginManager man = this.getServer().getPluginManager();
        man.registerEvents(new PlayerJoinListener(), this);
        man.registerEvents(new PlayerLeaveListener(), this);
        man.registerEvents(new PlayerChatListener(), this);
    }

    public void initCommands() {
        getCommand("simpleranks").setExecutor(new SimpleRanksCommand());
        getCommand("rank").setExecutor(new RankCommand());
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
