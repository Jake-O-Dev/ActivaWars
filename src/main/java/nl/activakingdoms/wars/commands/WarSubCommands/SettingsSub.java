package nl.activakingdoms.wars.commands.WarSubCommands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.Setting;
import nl.activakingdoms.wars.War;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class SettingsSub extends SubCommand {
    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getDescription() {
        return "Edit the settings of the war.";
    }

    @Override
    public String getSyntax() {
        return "/war settings [setting] [value]";
    }

    @Override
    public String getPermission() {
        return "activakingdoms.war.settings";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        if (GeneralMethods.getWar() == null) {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No war is currently setup. Use /war setup to enable this feature.");
            return;
        }

        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Available settings:");
                for (Setting setting : GeneralMethods.getWar().getSettings()) {
                    player.sendMessage("  " + setting.getName() + ":");
                    StringBuilder sb = new StringBuilder();
                    sb.append(setting.getValidAnswers().get(0));
                    ArrayList<String> list = setting.getValidAnswers();
                    for (int i = 1; i < list.size(); i++) {
                        sb.append(" / ");
                        sb.append(list.get(i));
                    }
                    player.sendMessage("  - " + sb.toString());
                }
            } else {
                sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Only a player doesn't have to specify setting and value.");
            }
        }

        else if (args.length == 3) {
            for (Setting setting : GeneralMethods.getWar().getSettings()) {
                if (setting.getName().equalsIgnoreCase(args[1]) && setting.getValidAnswers().contains(args[2])) {
                    setting.setValue(args[2]);
                    sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Setting " + ChatColor.BOLD + setting.getName() + ChatColor.RESET + " updated to " + ChatColor.BOLD + setting.getValue() + ChatColor.RESET + ".");
                    return;
                }
            }
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Setting name and/or value invalid.");
        }

        else {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Invalid amount of arguments.");
        }
    }

    @Override
    public ArrayList<String> getTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> completions = new ArrayList<>();

        if (GeneralMethods.getWar() == null) {
            return completions;
        }

        ArrayList<Setting> settings = GeneralMethods.getWar().getSettings();

        if (args.length == 2) {
            // suggest [setting]
            for (Setting setting : settings) {
                if (sender.hasPermission(setting.getPermission()))
                    completions.add(setting.getName());
            }

        } else if (args.length == 3) {
            // suggest [value]
            for (Setting setting : settings) {
                if (sender.hasPermission(setting.getPermission()) && args[1].equalsIgnoreCase(setting.getName())) {
                    completions.addAll(setting.getValidAnswers());
                }
            }
        }
        return completions;
    }
}
