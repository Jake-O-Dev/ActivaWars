package nl.activakingdoms.wars.commands.WarSubCommands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.Team;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;


public class RemoveTeamSub extends SubCommand {
    @Override
    public String getName() {
        return "removeteam";
    }

    @Override
    public String getDescription() {
        return "Remove a team";
    }

    @Override
    public String getSyntax() {
        return "/war removeteam <teamname>";
    }

    @Override
    public String getPermission() {
        return "activawars.war.removeteam";
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
                GeneralMethods.getWar().removeTeam(team);
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

        return completions;
    }
}
