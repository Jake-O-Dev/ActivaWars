package nl.activakingdoms.wars.commands.WarSubCommands;

import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.Team;
import nl.activakingdoms.wars.War;
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
            if (sender instanceof Player) {
                Player player = (Player) sender;
                War war = GeneralMethods.getWar();
                if (war.removeFromRemove(player)) {
                    player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Disabled " + ChatColor.RESET + "'hit-to-remove' function.");
                } else if (war.removeFromAdd(player) != null) {
                    war.addToRemove(player);
                    player.sendMessage(GeneralMethods.getPrefix() + ChatColor.GREEN + " Enabled " + ChatColor.RESET + "'hit-to-remove' function and" + ChatColor.RED + " disabled " + ChatColor.RESET + "'hit-to-add' function.");
                } else {
                    war.addToRemove(player);
                    player.sendMessage(GeneralMethods.getPrefix() + ChatColor.GREEN + " Enabled " + ChatColor.RESET + "'hit-to-remove' function.");
                }
            } else {
                sender.sendMessage("Only player can enter hit-to-remove mode.");
            }
        } else {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No player found.");
                return;
            }
            if (GeneralMethods.getWar().containsPlayer(target)) {
                Team team = GeneralMethods.getWar().getTeam(target);
                team.removePlayer(target);
                sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Removed player " + ChatColor.ITALIC + target.getDisplayName() + ChatColor.RESET + " from team " + team.getColor() + team.getName() + ChatColor.RESET + ".");
                target.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " You have been removed from team " + team.getColor() + team.getName() + ChatColor.RESET + ".");
            } else {
                sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Player is not in a team.");
            }
        }
    }

    @Override
    public ArrayList<String> getTabComplete(CommandSender sender, String[] args) {
        if (GeneralMethods.getWar() == null) {
            return new ArrayList<>();
        }
        War war = GeneralMethods.getWar();
        ArrayList<String> completions = new ArrayList<>();
        if (args.length == 2)
            for (Player player : Bukkit.getOnlinePlayers())
                if (war.containsPlayer(player)) completions.add(player.getName());

        return completions;
    }
}
