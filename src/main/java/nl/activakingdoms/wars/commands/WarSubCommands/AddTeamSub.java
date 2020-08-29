package nl.activakingdoms.wars.commands.WarSubCommands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.Team;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;


public class AddTeamSub extends SubCommand {
    @Override
    public String getName() {
        return "addteam";
    }

    @Override
    public String getDescription() {
        return "Add a team to the war";
    }

    @Override
    public String getSyntax() {
        return "/war addteam <teamname>";
    }

    @Override
    public String getPermission() {
        return "activawars.war.addteam";
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
            GeneralMethods.getWar().addTeam(new Team(args[1]));
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Team " + ChatColor.ITALIC + args[1] + ChatColor.RESET + " made.");
        }
    }

    @Override
    public ArrayList<String> getTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
