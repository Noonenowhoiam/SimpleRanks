package simpleranks.commands.tabcomplete;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simpleranks.utils.Permissions;
import simpleranks.utils.PlayerRank;

import java.util.ArrayList;
import java.util.List;

public class RankCommandTabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> complete = new ArrayList<>();

        if (strings.length == 1) {
            if (commandSender.hasPermission(Permissions.GET_RANK.perm()) || commandSender.hasPermission(Permissions.SET_RANK.perm())) {
                List<String> playerNames =  new ArrayList<>();
                Bukkit.getOnlinePlayers().forEach(player -> { playerNames.add(player.getName()); });
                complete.addAll(playerNames);
            }
        }

        if (strings.length == 2) {
            if (commandSender.hasPermission(Permissions.GET_RANK.perm())) {
                complete.add("get");
            }

            if (commandSender.hasPermission(Permissions.SET_RANK.perm())) {
                complete.add("set");
            }
        }

        if (strings.length == 3 && strings[1].equals("set")) {
            if (commandSender.hasPermission(Permissions.SET_RANK.perm())) {
                complete.addAll(PlayerRank.rankNames());
            }
        }

        return complete;
    }
}
