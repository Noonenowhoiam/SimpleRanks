package simpleranks.system;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardSystem {

    public static Map<UUID, ScoreboardSystem> loggedScoreboards = new HashMap<>();

    private Scoreboard scoreboard;
    private final UUID uuid;
    public ScoreboardSystem(Player owner) {
        this.uuid = owner.getUniqueId();
        if (loggedScoreboards.containsKey(owner.getUniqueId())) { loggedScoreboards.remove(owner.getUniqueId()); }
        loggedScoreboards.put(owner.getUniqueId(), this);
        this.reload();
        reloadAll();
    }

    public void loadMain() {
        /*
        Rank rank = new PlugPlayer(uuid).getRank();

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        scoreboard.getTeams().forEach(team -> team.unregister());
        for (Rank this_rank : Rank.values()) {
            Team t = scoreboard.registerNewTeam(this_rank.teamName());
            t.prefix(Component.text(this_rank.color() + this_rank.rankname() + Rank.split));
            t.setColor(ChatColor.getByChar('7'));
        }

        scoreboard.getTeam(rank.teamName()).addPlayer(Bukkit.getOfflinePlayer(uuid));

        for (Player this_player : Bukkit.getOnlinePlayers()) {
            if (this_player.getUniqueId() == uuid) continue;
            Rank this_rank = new PlugPlayer(this_player.getUniqueId()).getRank();
            scoreboard.getTeam(this_rank.teamName()).addPlayer(this_player);
        }

        Bukkit.getPlayer(uuid).setScoreboard(scoreboard);
        * */
    }

    public void reload() {
        this.loadMain();
    }

    public static void reloadAll() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            ScoreboardSystem s = getScoreboardSystemForPlayer(p);
            s.reload();
        }
    }

    public static ScoreboardSystem getScoreboardSystemForPlayer(Player player) {
        if (player == null) return null;
        if (!loggedScoreboards.containsKey(player.getUniqueId())) return new ScoreboardSystem(player);
        return loggedScoreboards.get(player.getUniqueId());
    }

    public static void playerChatEvent(AsyncPlayerChatEvent e) {

    }

    public static void playerJoinEvent(PlayerJoinEvent e) {
        reloadAll();
    }

    public static void playerLeaveEvent(PlayerQuitEvent e) {
        loggedScoreboards.remove(e.getPlayer().getUniqueId());
        reloadAll();
    }

}
