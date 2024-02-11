package simpleranks.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import simpleranks.system.ScoreboardSystem;
import simpleranks.utils.JavaTools;
import simpleranks.utils.Permissions;
import simpleranks.utils.PlayerRank;
import simpleranks.utils.Prefix;
import simpleranks.utils.config.DefaultConfiguration;
import simpleranks.utils.config.PlayerConfiguration;

public class SimpleRanksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length < 1) { sendHelp(commandSender); return true; }
        String option = strings[0];

        if (option.equals("config")) {
            if (!commandSender.hasPermission(Permissions.CONFIG.perm())) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Du hast §ckeine Rechte§7 diesen Command zu nutzen!"); }
            if (strings.length < 3) { sendHelp(commandSender); return true; }
            String config_key = strings[1];
            String config_value = strings[2];

            if (config_key.equals("defaultRank")) {
                if (!PlayerRank.isRankExistent(config_value)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Der von dir angegebene Rang existiert §cnicht§7!"); return true; }
                DefaultConfiguration.defaultRank.set(config_value);
                PlayerRank rank = PlayerRank.get(config_value);
                commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast den Rang " + rank.color() + rank.displayName() + " §7 erfolgreich als §aStandart Rang§7 hinterlegt!");
                return true;
            }

            if (config_key.equals("chatFormat")) {
                StringBuilder chatFormat = new StringBuilder();
                for (int i = 0; i < strings.length; i++) {
                    if (i < 2) continue;
                    chatFormat.append(strings[i]).append(" ");
                }
                DefaultConfiguration.chatRankFormat.set(chatFormat.toString());
                commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast das Format des Chats §aerfolgreich§7 geändert!");
                return true;
            }

            if (config_key.equals("chatRank")) {
                if (!(config_value.equalsIgnoreCase("true") || config_value.equalsIgnoreCase("false"))) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Du musst §atrue§7 oder §cfalse§7 angeben!"); return true; }
                boolean b = false;
                if (config_value.equalsIgnoreCase("true")) b = true;
                if (config_value.equalsIgnoreCase("false")) b = false;
                DefaultConfiguration.chatRankEnabled.set(b);
                if (b) {
                    commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast den chat Rank erfolgreich §aeingeschalten§7!");
                } else {
                    commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast den chat Rank erfolgreich §causgeschalten§7!");
                }
                return true;
            }

            if (config_key.equals("teamSeperator")) {
                if (config_value.length() > 4) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Der Seperator darf §cmaximal 4 zeichen§7 lang sein!"); return true; }
                DefaultConfiguration.teamRankSeparator.set(config_value);
                commandSender.sendMessage(Prefix.SYSTEM.def() + "Der Team Seperator ist nun §a" + config_value + "§7!");
                ScoreboardSystem.reloadAll();
                return true;
            }

            if (config_key.equals("teamRank")) {
                if (!(config_value.equalsIgnoreCase("true") || config_value.equalsIgnoreCase("false"))) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Du musst §atrue§7 oder §cfalse§7 angeben!"); return true; }
                boolean b = false;
                if (config_value.equalsIgnoreCase("true")) b = true;
                if (config_value.equalsIgnoreCase("false")) b = false;
                DefaultConfiguration.teamRankEnabled.set(b);
                if (b) {
                    commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast den team Rank erfolgreich §aeingeschalten§7!");
                } else {
                    commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast den team Rank erfolgreich §causgeschalten§7!");
                }
                ScoreboardSystem.reloadAll();
                return true;
            }

            commandSender.sendMessage(Prefix.SYSTEM.err() + "Die von dir angegebene Config value wurde §cnicht§7 gefunden!");
            return true;
        }

        if (option.equals("rank")) {
            if (strings.length < 2) { sendHelp(commandSender); return true; }
            String option2 = strings[1];

            if (option2.equals("create")) {
                if (strings.length < 4) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Bitte gib einen §anamen§7 und eine §afarbe§7 an! Usage: /sr rank create <displayName> <farbe>"); return true; }
                String dpName = strings[2];
                String color = strings[3];

                if (dpName.length() > 15) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Der angegebene Name ist zu lang! Bitte nutze §cmaximal 15 zeichen§7!"); return true; }
                if (color.length() > 1) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Die farbe muss ein §ceinzelner Buchstabe§7 sein!"); return true; }
                if (!"4c6e2ab319d5f780".contains(color)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Die angegebene Farbe §cexistiert nicht§7!"); return true; }
                if (PlayerRank.isRankExistent(dpName)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Ein Rang mir den Namen §c" + dpName + "§7 existiert bereits!"); return true; }

                PlayerRank.newRank(dpName, color);
                commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast den Rang §" + color + dpName + "§7 erstellt!");
                return true;
            }

            if (option2.equals("delete")) {
                if (strings.length < 3) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Bitte gib den §cNamen§7 des Ranks an den du löschen möchtest!"); return true; }
                String dpName = strings[2];

                if (!PlayerRank.isRankExistent(dpName)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Es existiert §ckein Rang§7 mit dem angegebenen Namen!"); return true; }
                if (DefaultConfiguration.defaultRank.get().equals(dpName)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Du kannst den §cDefault Rank§7 nicht löschen! Ändere ihn in den Configs!"); return true; }

                PlayerRank.deleteRank(PlayerRank.get(dpName).id());
                ScoreboardSystem.reloadAll();
                commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast den Rang §a" + dpName + "§7 erfolgreich §cgelöscht§7!");
                return true;
            }

            if (option2.equals("modify")) {
                if (strings.length < 3) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Bitte gib einen §aRang§7 an den du verändern möchtest!"); return true; }
                String rankName = strings[2];

                if (!PlayerRank.isRankExistent(rankName)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Der angegebene §cRang§7 existiert nicht!"); return true; }
                if (strings.length < 4) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Bitte gib eine §aOption§7 an die du modifizieren möchtest!"); return true; }
                String option3_key = strings[3];
                if (strings.length < 5) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Bitte gib eine §aValue§7 an!"); return true; }
                String option3_value = strings[4];

                if (DefaultConfiguration.defaultRank.get().equals(rankName)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Du kannst den §cDefault Rank§7 nicht modifizieren! Ändere ihn in den Configs!"); return true; }

                if (option3_key.equals("setDisplayName")) {
                    if (PlayerRank.isRankExistent(option3_value)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Es existiert bereits ein Rang mit dem Namen §c" + option3_value + "§7!"); return true; }
                    PlayerRank.get(rankName).setDisplayName(option3_value);
                    commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast den Namen des Ranges §c" + rankName + "§7 zu §a" + option3_value + "§7 geändert!");
                    ScoreboardSystem.reloadAll();
                    return true;
                }

                if (option3_key.equals("setColor")) {
                    if (option3_value.length() > 1) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Die farbe muss ein §ceinzelner Buchstabe§7 sein!"); return true; }
                    if (!"4c6e2ab319d5f780".contains(option3_value)) { commandSender.sendMessage(Prefix.SYSTEM.err() + "Die angegebene Farbe §cexistiert nicht§7!"); return true; }
                    String oldC = PlayerRank.get(rankName).color();
                    PlayerRank.get(rankName).setColor(option3_value);
                    commandSender.sendMessage(Prefix.SYSTEM.def() + "Du hast die farbe des Rangs von §" + oldC + "altefarbe§7 zu §" + option3_value + "neuefarbe§7 geändert!");
                    ScoreboardSystem.reloadAll();
                    return true;
                }
            }

            commandSender.sendMessage(Prefix.SYSTEM.err() + "Die von dir angegebene Rank option §cexistiert nicht§7!");
            return true;
        }

        commandSender.sendMessage(Prefix.SYSTEM.err() + "Die option §c" + option + "§7 existiert nicht!");
        return true;
    }

    public void sendHelp(CommandSender s) {
        s.sendMessage("Help");
    }
}
