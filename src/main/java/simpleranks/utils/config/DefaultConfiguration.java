package simpleranks.utils.config;

import simpleranks.Simpleranks;
import simpleranks.utils.JsonManager;
import simpleranks.utils.config.ConfigValue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class DefaultConfiguration {
    private static File configFile = new File(Simpleranks.data + File.separator + "config.json");

    public static ConfigValue<String> defaultRank = new ConfigValue<>("defaultRank", String.class, configFile.toPath());

    public static ConfigValue<String> chatRankSeparator = new ConfigValue<>("rank.chatSeparator", String.class, configFile.toPath());
    public static ConfigValue<Boolean> chatRankEnabled = new ConfigValue<>("rank.chatRank", Boolean.class, configFile.toPath());

    public static ConfigValue<String> teamRankSeparator = new ConfigValue<>("rank.teamSeparator", String.class, configFile.toPath());
    public static ConfigValue<Boolean> teamRankEnabled = new ConfigValue<>("rank.teamRank", Boolean.class, configFile.toPath());


    public static void init() {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();

                JsonManager defaultJson = new JsonManager();

                defaultJson.addProperty("defaultRank", "default");
                defaultJson.addProperty("rank", new JsonManager()
                        .addProperty("chatSeparator", ">>")
                        .addProperty("chatRank", true)
                        .addProperty("teamSeparator", ">>")
                        .addProperty("teamRank", true)
                );

                try {
                    Files.write(configFile.toPath(), JsonManager.makePrettier(defaultJson.toJsonString()).getBytes(), StandardOpenOption.CREATE);
                } catch (IOException e) { throw new RuntimeException(e); }
            } catch (IOException e) { throw new RuntimeException(e); }
        }
    }

}
