package nl.activakingdoms.wars.commands.WarSubCommands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.Team;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RemovePlayerSub extends SubCommand {
    @Override
    public String getName() {
        return "removeplayer";
    }

    @Override
    public String getDescription() {
        return "Remove a player from a team.";
    }

    @Override
    public String getSyntax() {
        return "/war removeplayer <player>";
    }

    @Override
    public String getPermission() {
        return "activawars.war.removeplayer";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (GeneralMethods.getWar() == null) {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No war is currently setup. Use /war setup to enable this feature.");
            return;
        }

        if (args.length == 1) {
            // TODO hit to remove mode ?
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No player name specified.");
        } else {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No player found.");
                return;
            }
            for (Team team : GeneralMethods.getWar().getTeams()) {
                if (team.removePlayer(target)) {
                    sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Removed player " + ChatColor.ITALIC + target.getDisplayName() + ChatColor.RESET + " from team " + team.getColor() + team.getName() + ChatColor.RESET + ".");
                    target.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " You have been removed from team " + team.getColor() + team.getName() + ChatColor.RESET + ".");
                }
            }
        }
    }

    @Override
    public ArrayList<String> getTabComplete(CommandSender sender, String[] args) {
        return null;
    }
}
