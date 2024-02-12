package simpleranks.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import simpleranks.system.ScoreboardSystem;
import simpleranks.utils.Permissions;
import simpleranks.utils.PlayerRank;
import simpleranks.utils.Prefix;
import simpleranks.utils.config.PlayerConfiguration;

public class RankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender.hasPermission(Permissions.GET_RANK.perm()) && commandSender.hasPermission(Permissions.SET_RANK.perm()))) { commandSender.sendMessage(Prefix.SYSTEM.err() + "You are §cnot allowed§7 to execute this command!"); return true; }
        if (strings.length < 1) { sendHelp(commandSender); return true; }
        String player = strings[0];
        OfflinePlayer updateP = Bukkit.getOfflinePlayer(player);
        if (updateP == null) { commandSender.sendMessage(Prefix.SYSTEM.err() + "The player you specified §cdoes not exist§7!"); return true; }

        if (strings.length < 2) { sendHelp(commandSender); return true; }
        String option = strings[1];

        if (option.equals("set")) {
            if (!commandSender.hasPermission(Permissions.SET_RANK.perm())) { commandSender.sendMessage(Prefix.SYSTEM.err() + "You are §cnot allowed§7 to update the rank!"); return true; }
            if (strings.length < 3) { sendHelp(commandSender); return true; }
            String rankName = strings[2];
            if (!PlayerRank.isRankExistent(rankName)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "The rank you specified does §cnot exist§7!"); return true; }
            PlayerRank rank = PlayerRank.get(rankName);

            PlayerConfiguration conf = PlayerConfiguration.getFor(updateP.getUniqueId());
            if (!commandSender.hasPermission(Permissions.SET_RANK_ALL.perm())) {
                if (commandSender instanceof Player p) {
                    PlayerRank setterrank = PlayerConfiguration.getFor(p).getRank();
                    if (setterrank.position() > rank.position()) { commandSender.sendMessage(Prefix.SYSTEM.err() + "You are not allowed to assign a §chigher rank§7!"); return true; }
                }
            }
            conf.setRank(rank);
            ScoreboardSystem.reloadAll();
            commandSender.sendMessage(Prefix.SYSTEM.def() + "You gave the player §a" + updateP.getName() + "§7 the rank " + rank.color() + rank.displayName() + "§7!");
            return true;
        }

        if (option.equals("get")) {
            if (!commandSender.hasPermission(Permissions.GET_RANK.perm())) { commandSender.sendMessage(Prefix.SYSTEM.err() + "You are §cnot allowed§7 to retrieve the rank!"); return true; }
            PlayerConfiguration conf = PlayerConfiguration.getFor(updateP.getUniqueId());

            commandSender.sendMessage(Prefix.SYSTEM.def() + "The player §a" + updateP.getName() + "§7 has the rank " + conf.getRank().color() + conf.getRank().displayName() + "§7!");
            return true;
        }

        commandSender.sendMessage(Prefix.SYSTEM.err() + "The Option §c" + option + "§7 was not found!");
        return true;
    }

    public void sendHelp(CommandSender c) {
        c.sendMessage("Help");
    }
}
