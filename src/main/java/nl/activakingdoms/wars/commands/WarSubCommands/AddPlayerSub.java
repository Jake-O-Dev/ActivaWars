package nl.activakingdoms.wars.commands.WarSubCommands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.Team;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;



public class AddPlayerSub extends SubCommand {
    @Override
    public String getName() {
        return "addplayer";
    }

    @Override
    public String getDescription() {
        return "Add a player to a team";
    }

    @Override
    public String getSyntax() {
        return "/war addplayer <team> <player>";
    }

    @Override
    public String getPermission() {
        return "activawars.war.addplayer";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        if (GeneralMethods.getWar() == null) {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No war is currently setup. Use /war setup to enable this feature.");
            return;
        }

        if (args.length == 1) {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No team-name specified.");
        } else {
            Team team = GeneralMethods.getWar().getTeam(args[1]);
            if (team == null) {
                sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No team found with that name.");
            } else {
                if (args.length == 2) {
                    // TODO hit-add detection
                    sender.sendMessage("TODO hit-add detection");
                } else {
                    Player target = Bukkit.getPlayer(args[2]);
                    if (target == null) {
                        sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No player found.");
                        return;
                    }
                    for (Team t : GeneralMethods.getWar().getTeams()) {
                        if (t.getPlayers().contains(target)) {
                            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " This player is already in a team, remove them first using /war removeplayer");
                            return;
                        }
                    }
                    team.addPlayer(target);
                    sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Added " + ChatColor.ITALIC + target.getDisplayName() + ChatColor.RESET + " to team " + team.getColor() + team.getName());
                    target.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " You have been added to team " + team.getColor() + team.getName());
                }
            }
        }
    }

    @Override
    public ArrayList<String> getTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if (GeneralMethods.getWar() == null) {
            return completions;
        }
        if (args.length == 2) {
            for (Team team : GeneralMethods.getWar().getTeams()) {
                completions.add(team.getName());
            }
        }
        else if (args.length == 3){
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!GeneralMethods.getWar().containsPlayer(player))
                    completions.add(player.getName());
            }
        }

        return completions;
    }
}
