package simpleranks.utils.config;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerConfiguration {

    private UUID playerUUID;
    public PlayerConfiguration(UUID uuid) {
        this.playerUUID = uuid;
    }


    public static PlayerConfiguration getFor(Player p) {
        return new PlayerConfiguration(p.getUniqueId());
    }

    public static PlayerConfiguration getFor(UUID uuid) {
        return new PlayerConfiguration(uuid);
    }




}
