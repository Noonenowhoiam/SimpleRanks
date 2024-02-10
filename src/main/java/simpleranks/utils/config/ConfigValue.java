package simpleranks.utils.config;

import com.google.gson.Gson;
import simpleranks.utils.JsonManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigValue<T> {
    private final Path path;
    private final String key;
    private final Class<T> type;

    public ConfigValue(String key, Class<T> type, Path filePath) {
        this.key = key;
        this.type = type;
        this.path = filePath;
    }

    public T get() {
        JsonManager jsonManager = new JsonManager();
        String json;
        try {
            json = Files.readString(path);
            jsonManager = new JsonManager(json);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading JSON file", e);
        }

        String[] spjson = key.split("\\.");
        String tmpkey = null;

        int now = 0;
        for (String s : spjson) {
            if (spjson.length-1 == now) { tmpkey = s; break; }
            if (!jsonManager.hasKey(s)) return null;
            jsonManager = new JsonManager(new Gson().toJson(jsonManager.getObject(s)));
            now++;
        }

        if (type == Arrays.class) {
            return (T) jsonManager.getArray(tmpkey);
        } else if (type == String.class) {
            return (T) jsonManager.getString(tmpkey);
        } else  if (type == Number.class) {
            return (T) jsonManager.getNumber(tmpkey);
        } else if (type == Boolean.class) {
            return (T) jsonManager.getBoolean(tmpkey);
        }

        else {
            return null;
        }
    }

}
