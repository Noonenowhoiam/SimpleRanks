package simpleranks;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import simpleranks.commands.RankCommand;
import simpleranks.commands.SimpleRanksCommand;
import simpleranks.commands.tabcomplete.RankCommandTabComplete;
import simpleranks.commands.tabcomplete.SimpleRanksComandTabComplete;
import simpleranks.listeners.PlayerChatListener;
import simpleranks.listeners.PlayerJoinListener;
import simpleranks.listeners.PlayerLeaveListener;
import simpleranks.system.ScoreboardSystem;
import simpleranks.utils.Database;
import simpleranks.utils.PlayerRank;
import simpleranks.utils.config.DefaultConfiguration;

import java.io.File;

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
        DefaultConfiguration.init();
        //Language.init();
        Database.init();
        PlayerRank.init();
        ScoreboardSystem.reloadAll();

        getLogger().info("Loaded all ranks: " + PlayerRank.rankNames());
        getLogger().info("Starting up...");

        getLogger().info("Successful started Plugin version " + getPluginMeta().getVersion() + "!");
        getLogger().info("Plugin created by - " + getPluginMeta().getAuthors());

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
        getCommand("simpleranks").setTabCompleter(new SimpleRanksComandTabComplete());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("rank").setTabCompleter(new RankCommandTabComplete());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Database.shutdown();
        Scheduler.stop();
    }

    public void initFiles() {
        if (!new File(data).exists()) {
            new File(data).mkdir();
        }
    }

}
