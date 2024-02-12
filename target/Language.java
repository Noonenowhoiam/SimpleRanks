package simpleranks.utils;

import simpleranks.utils.config.ConfigValue;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Language {

    public static Map<String, String> languages = new HashMap<>();

    public static void init() {
        languages.put("en", ClassLoader.getSystemResourceAsStream("languages/en-EN.json").toString());

    }

    public static String get(String key, Map<String, String> replace) {
        String selected_language = "en";

        ConfigValue<String> needed = new ConfigValue<>(key, String.class, Path.of(languages.get(selected_language)));
        String re = needed.get();

        for (Map.Entry<String, String> e : replace.entrySet()) {
            re = re.replace(e.getKey(), e.getValue());
        }

        return re;
    }

    public static String get(String key) {
        String selected_language = "en";

        ConfigValue<String> needed = new ConfigValue<>(key, String.class, Path.of(languages.get(selected_language)));
        String re = needed.get();
        return re;
    }

    public static String get(Content c, Map<String, String> replace) {
        String selected_language = "en";

        ConfigValue<String> needed = new ConfigValue<>(c.key(), String.class, Path.of(languages.get(selected_language)));
        String re = needed.get();

        for (Map.Entry<String, String> e : replace.entrySet()) {
            re = re.replace(e.getKey(), e.getValue());
        }

        return re;
    }

    public static String get(Content c) {
        String selected_language = "en";

        ConfigValue<String> needed = new ConfigValue<>(c.key(), String.class, Path.of(languages.get(selected_language)));
        String re = needed.get();
        return re;
    }

    public static enum Content {

        COMMAND_NO_RIGHTS_TO_USE("command.no_rights_to_use"), COMMAND_SPECIFIED_PLAYER_NOT_EXISTS("command.specified_player_not_exists"), COMMAND_OPTION_NOT_FOUND("command.option_not_found"), COMMAND_SPECIFIED_OPTION_NOT_FOUND("command.specified_option_not_found"),
        COMMAND_RANK_NO_RIGHTS_UPDATE_RANK("command.rank.no_rights_update_rank"), COMMAND_RANK_NO_HIGHER_RANK_RIGHTS("command.rank.no_rights_assign_higher_rank"), COMMAND_RANK_SPECIFIED_RANK_NOT_EXISTS("command.rank.specified_rank_not_exists"),
        COMMAND_RANK_UPDATE_SUCCESS("command.rank.rank_successful_assigned"), COMMAND_RANK_PLAYER_RANK("command.rank.rank_of_player")
        ;

        Content(String key) { this.key = key; }
        private final String key;
        public String key() { return key; }
    }
}
