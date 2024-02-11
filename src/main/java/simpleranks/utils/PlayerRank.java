package simpleranks.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import simpleranks.utils.config.DefaultConfiguration;
import simpleranks.utils.config.PlayerConfiguration;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerRank extends Database {

    private long id;

    public PlayerRank(long id) {
        this.id = id;
    }

    public static PlayerRank newRank(String dpn, String color) {
        long id = new Random().nextLong(); id = Math.abs(id) % 10000000000000000L;
        List<Long> ranks = PlayerRank.rankIds();
        while (ranks.contains(id)) { id = new Random().nextLong(); id = Math.abs(id) % 10000000000000000L; }

        StringBuilder replacedStringBuilder = new StringBuilder();
        for (int i = 0; i < PlayerRank.ranks().size(); i++) {
            replacedStringBuilder.append('9');
        }

        try {
            database.executeUpdate("INSERT INTO " + ranksDataTable + " (`id`, `displayName`, `color`, `position`) VALUES ('" + id + "', '" + dpn + "', '" + color + "', '" + replacedStringBuilder.toString() + "')");
        } catch (Exception e) { e.printStackTrace(); }
        return PlayerRank.get(id);
    }

    public static void deleteRank(long id) {
        if (!isRankExistent(id)) return;
        try {
            database.executeUpdate("DELETE FROM " + ranksDataTable + " WHERE id = '" + id + "';");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static PlayerRank get(long id) {
        return new PlayerRank(id);
    }
    public static PlayerRank get(String name) {
        try {
            ResultSet rs = database.executeQuery("SELECT * FROM " + ranksDataTable + " WHERE displayName = '" + name + "';");
            String temp_id = null; if (rs.next()) { temp_id = rs.getString("id"); }
            rs.close();
            if (temp_id == null) return PlayerRank.getDefaultRank();
            if (!JavaTools.isLong(temp_id)) return PlayerRank.getDefaultRank();
            return PlayerRank.get(Long.valueOf(temp_id));
        } catch (Exception e) { e.printStackTrace(); }
        return PlayerRank.getDefaultRank();
    }


    public long id() { return id; }
    public String displayName() {
        if (!isRankExistent(id)) return null;
        try {
            ResultSet rs = database.executeQuery("SELECT * FROM " + ranksDataTable + " WHERE id = '" + id + "';");
            String s = null; if (rs.next()) { s = rs.getString("displayName"); }
            rs.close();
            return s;
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void setDisplayName(String name) {
        try {
            database.executeUpdate("UPDATE " + ranksDataTable + " SET displayName = '" + name + "' WHERE id = '" + id + "';");
        } catch (Exception e) { e.printStackTrace(); }
    }
    public String teamName() {
        return position() + "-" + displayName();
    }
    public int position() {
        if (!isRankExistent(id)) return 99999;
        try {
            ResultSet rs = database.executeQuery("SELECT * FROM " + ranksDataTable + " WHERE id = '" + id + "';");
            int i = 99999; if (rs.next()) { i = rs.getInt("position"); }
            rs.close();
            return i;
        } catch (Exception e) { e.printStackTrace(); }
        return 99999;
    }
    public void setPosition(int pos) {
        try {
            database.executeUpdate("UPDATE " + ranksDataTable + " SET position = '" + pos + "' WHERE id = '" + id + "';");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public String color() {
        if (!isRankExistent(id)) return null;
        try {
            ResultSet rs = database.executeQuery("SELECT * FROM " + ranksDataTable + " WHERE id = '" + id + "';");
            String s = null; if (rs.next()) { s = rs.getString("color"); }
            rs.close();
            return "§" + s;
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
    public void setColor(String color) {
        try {
            database.executeUpdate("UPDATE " + ranksDataTable + " SET color = '" + color + "' WHERE id = '" + id + "';");
        } catch (Exception e) { e.printStackTrace(); }
    }


    public static List<PlayerRank> ranks() {
        List<PlayerRank> re = new ArrayList<>();
        try {
            ResultSet rs = database.executeQuery("SELECT * FROM " + ranksDataTable + ";");
            while (rs.next()) {
                String tmp_id = rs.getString("id");
                if (tmp_id == null) continue;
                if (!JavaTools.isLong(tmp_id)) continue;
                re.add(PlayerRank.get(Long.valueOf(tmp_id)));
            }
            rs.close();
        } catch (Exception e) { e.printStackTrace(); }
        return re;
    }
    public static List<Long> rankIds() {
        List<Long> re = new ArrayList<>();
        try {
            ResultSet rs = database.executeQuery("SELECT * FROM " + ranksDataTable + ";");
            while (rs.next()) {
                String tmp_id = rs.getString("id");
                if (tmp_id == null) continue;
                if (!JavaTools.isLong(tmp_id)) continue;
                re.add(Long.valueOf(tmp_id));
            }
            rs.close();
        } catch (Exception e) { e.printStackTrace(); }
        return re;
    }
    public static List<String> rankNames() {
        List<String> re = new ArrayList<>();
        try {
            ResultSet rs = database.executeQuery("SELECT * FROM " + ranksDataTable + ";");
            while (rs.next()) {
                String dpN = rs.getString("displayName");
                if (dpN == null) continue;
                re.add(dpN);
            }
            rs.close();
        } catch (Exception e) { e.printStackTrace(); }
        return re;
    }

    public static PlayerRank getDefaultRank() {
        return get(DefaultConfiguration.defaultRank.get());
    }



    public static boolean isRankExistent(long id) {
        try {
            ResultSet rs = database.executeQuery("SELECT * FROM " + ranksDataTable + " WHERE id = '" + id + "';");
            boolean b = rs.next();
            rs.close();
            return b;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public static boolean isRankExistent(String dpN) {
        try {
            ResultSet rs = database.executeQuery("SELECT * FROM " + ranksDataTable + " WHERE displayName = '" + dpN + "';");
            boolean b = rs.next();
            rs.close();
            return b;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public static void init() {
        try {
            if (!isRankExistent(1)) { database.executeUpdate("INSERT INTO " + ranksDataTable + " (`id`, `displayName`, `shortName`, `color`, `position`) VALUES ('1', 'default', 'def', 'f', '1')"); }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
