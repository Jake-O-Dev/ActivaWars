package nl.activakingdoms.wars.commands.WarSubCommands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class NotificationsSub extends SubCommand {
    @Override
    public String getName() {
        return "notifications";
    }

    @Override
    public String getDescription() {
        return "Toggle the notifications from this plugin";
    }

    @Override
    public String getSyntax() {
        return "/war notifications";
    }

    @Override
    public String getPermission() {
        return "activawars.war.notifications";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (GeneralMethods.getWar() == null) {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No war is currently setup. Use /war setup to enable this feature.");
            return;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if (toggle(player)) {
                    player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Turned notifications " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
                } else {
                    player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Turned notifications " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
                }
            } else {
                switch (args[1].toLowerCase()) {
                    case "on":
                        GeneralMethods.getWar().removeDontNotify(player);
                        player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Turned notifications " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
                        break;
                    case "off":
                        if (!GeneralMethods.getWar().getDontNotify().contains(player)) {
                            GeneralMethods.getWar().addDontNotify(player);
                        }
                        player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Turned notifications " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
                        break;
                    default:
                        if (toggle(player)) {
                            player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Turned notifications " + ChatColor.GREEN + "ON" + ChatColor.RESET + ".");
                        } else {
                            player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Turned notifications " + ChatColor.RED + "OFF" + ChatColor.RESET + ".");
                        }
                }
            }
        } else {
            sender.sendMessage("Only players can use this command.");
        }
    }

    private boolean toggle(Player player) {
        if (!GeneralMethods.getWar().removeDontNotify(player)) {
            GeneralMethods.getWar().addDontNotify(player);
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<String> getTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        completions.add("on");
        completions.add("off");
        completions.add("toggle");
        return completions;
    }
}
