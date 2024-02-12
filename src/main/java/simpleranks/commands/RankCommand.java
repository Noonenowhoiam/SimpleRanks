package simpleranks.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import simpleranks.system.ScoreboardSystem;
import simpleranks.utils.Permissions;
import simpleranks.utils.PlayerRank;
import simpleranks.utils.Prefix;
import simpleranks.utils.config.PlayerConfiguration;

import java.lang.ref.PhantomReference;

public class RankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender.hasPermission(Permissions.GET_RANK.perm()) || commandSender.hasPermission(Permissions.SET_RANK.perm()))) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Du hast keine Rechte diesen Command zu nutzen!"); return true; }
        if (strings.length < 1) { sendHelp(commandSender); return true; }
        String player = strings[0];
        OfflinePlayer updateP = Bukkit.getOfflinePlayer(player);
        if (updateP == null) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Der angegebene Spieler existiert §cnicht§7!"); return true; }

        if (strings.length < 2) { sendHelp(commandSender); return true; }
        String option = strings[1];

        if (option.equals("set")) {
            if (!commandSender.hasPermission(Permissions.SET_RANK.perm())) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Du hast keine Rechte den Rang zu aktualisieren!"); return true; }
            if (strings.length < 3) { sendHelp(commandSender); return true; }
            String rankName = strings[2];
            if (!PlayerRank.isRankExistent(rankName)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Der angegebene Rang existiert §cnicht§7!"); return true; }
            PlayerRank rank = PlayerRank.get(rankName);
            PlayerConfiguration conf = PlayerConfiguration.getFor(updateP.getUniqueId());
            conf.setRank(rank);
            ScoreboardSystem.reloadAll();
            commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast dem Spieler §a" + updateP.getName() + "§7 den Rang " + rank.color() + rank.displayName() + "§7 gegeben!");
            return true;
        }

        if (option.equals("get")) {
            if (!commandSender.hasPermission(Permissions.GET_RANK.perm())) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Du hast keine Rechte den Rang abzurufen!"); return true; }
            PlayerConfiguration conf = PlayerConfiguration.getFor(updateP.getUniqueId());
            commandSender.sendMessage(Prefix.SYSTEM.def() + "Der Spieler §a" + updateP.getName() + "§7 hat den Rang " + conf.getRank().color() + conf.getRank().displayName() + "§7!");
            return true;
        }

        commandSender.sendMessage(Prefix.SYSTEM.err() + "Die Option §c" + option + "§7 wurde nicht gefunden!");
        return true;
    }

    public void sendHelp(CommandSender c) {
        c.sendMessage("Help");
    }
}
