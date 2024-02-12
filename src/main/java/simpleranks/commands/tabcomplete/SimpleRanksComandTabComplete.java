package simpleranks.commands.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simpleranks.utils.PlayerRank;

import java.util.ArrayList;
import java.util.List;

public class SimpleRanksComandTabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> complete = new ArrayList<>();

        if (strings.length == 1) {
            complete.add("rank");
            complete.add("config");
        }

        if (strings.length == 2 && strings[0].equals("config")) {
            complete.add("defaultRank");
            complete.add("chatFormat");
            complete.add("chatRank");
            complete.add("teamSeperator");
            complete.add("teamRank");
        }

        if (strings.length == 2 && strings[0].equals("rank")) {
            complete.add("create");
            complete.add("delete");
            complete.add("info");
            complete.add("modify");
            complete.add("list");
        }

        if (strings.length == 3 && strings[1].equals("modify")) {
            complete.addAll(PlayerRank.rankNames());
        }

        if (strings.length == 4 && strings[1].equals("modify")) {
            complete.add("setDisplayName");
            complete.add("setColor");
            complete.add("moveUp");
            complete.add("moveDown");
        }

        if (strings.length == 5 && strings[1].equals("modify") && strings[3].equals("setColor")) {
            complete.addAll(PlayerRank.colors());
        }

        if (strings.length == 3 && strings[1].equals("delete")) {
            complete.addAll(PlayerRank.rankNames());
        }

        if (strings.length == 3 && strings[1].equals("info")) {
            complete.addAll(PlayerRank.rankNames());
        }

        if (strings.length == 4 && strings[1].equals("create")) {
            complete.addAll(PlayerRank.colors());
        }

        return complete;
    }
}
