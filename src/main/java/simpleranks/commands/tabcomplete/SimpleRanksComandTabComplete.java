package simpleranks.commands.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import simpleranks.utils.Permissions;
import simpleranks.utils.PlayerRank;

import java.util.ArrayList;
import java.util.List;

public class SimpleRanksComandTabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> complete = new ArrayList<>();

        if (strings.length == 1) {
            if (commandSender.hasPermission(Permissions.SETUP_RANK_LIST.perm()) || commandSender.hasPermission(Permissions.SETUP_RANK_INFO.perm()) || commandSender.hasPermission(Permissions.SETUP_RANK_DELETE.perm()) ||
            commandSender.hasPermission(Permissions.SETUP_RANK_MODIFY.perm()) || commandSender.hasPermission(Permissions.SETUP_RANK_CREATE.name())) complete.add("rank");
            if (commandSender.hasPermission(Permissions.CONFIG.perm())) complete.add("config");
        }

        if (strings.length == 2 && strings[0].equals("config") && commandSender.hasPermission(Permissions.CONFIG.perm())) {
            complete.add("defaultRank");
            complete.add("chatFormat");
            complete.add("chatRank");
            complete.add("teamSeperator");
            complete.add("teamRank");
        }

        if (strings.length == 3 && strings[1].equals("defaultRank") && commandSender.hasPermission(Permissions.CONFIG.perm())) {
            complete.addAll(PlayerRank.rankNames());
        }

        if (strings.length == 2 && strings[0].equals("rank")) {
            if (commandSender.hasPermission(Permissions.SETUP_RANK_CREATE.perm())) complete.add("create");
            if (commandSender.hasPermission(Permissions.SETUP_RANK_DELETE.perm())) complete.add("delete");
            if (commandSender.hasPermission(Permissions.SETUP_RANK_INFO.perm())) complete.add("info");
            if (commandSender.hasPermission(Permissions.SETUP_RANK_MODIFY.perm())) complete.add("modify");
            if (commandSender.hasPermission(Permissions.SETUP_RANK_LIST.perm())) complete.add("list");
        }

        if (strings.length == 3 && strings[1].equals("modify") && commandSender.hasPermission(Permissions.SETUP_RANK_MODIFY.perm())) {
            complete.addAll(PlayerRank.rankNames());
        }

        if (strings.length == 4 && strings[1].equals("modify") && commandSender.hasPermission(Permissions.SETUP_RANK_MODIFY.perm())) {
            complete.add("setDisplayName");
            complete.add("setColor");
            complete.add("moveUp");
            complete.add("moveDown");
        }

        if (strings.length == 5 && strings[1].equals("modify") && strings[3].equals("setColor") && commandSender.hasPermission(Permissions.SETUP_RANK_MODIFY.perm())) {
            complete.addAll(PlayerRank.colors());
        }

        if (strings.length == 3 && strings[1].equals("delete") && commandSender.hasPermission(Permissions.SETUP_RANK_DELETE.perm())) {
            complete.addAll(PlayerRank.rankNames());
        }

        if (strings.length == 3 && strings[1].equals("info") && commandSender.hasPermission(Permissions.SETUP_RANK_INFO.perm())) {
            complete.addAll(PlayerRank.rankNames());
        }

        if (strings.length == 4 && strings[1].equals("create") && commandSender.hasPermission(Permissions.SETUP_RANK_CREATE.perm())) {
            complete.addAll(PlayerRank.colors());
        }

        return complete;
    }
}
