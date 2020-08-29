package nl.activakingdoms.wars.commands.WarSubCommands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.Team;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;


public class SetColorSub extends SubCommand {
    @Override
    public String getName() {
        return "setcolor";
    }

    @Override
    public String getDescription() {
        return "Set the color of team";
    }

    @Override
    public String getSyntax() {
        return "/war setcolor <team> <color>";
    }

    @Override
    public String getPermission() {
        return "activawars.war.setcolor";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (GeneralMethods.getWar() == null) {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No war is currently setup. Use /war setup to enable this feature.");
            return;
        }


        if (args.length == 1) {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No player name specified.");
        } else {

            Team team = GeneralMethods.getWar().getTeam(args[1]);
            if (team == null) {
                sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No team found with that name.");
            } else {
                ChatColor color;
                if (args.length == 2) {
                   color = ChatColor.WHITE;
                   sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Color set to red");
                } else {
                    if (values.contains(args[2].toUpperCase()))
                        color = ChatColor.valueOf(args[2].toUpperCase());
                    else {
                        sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No valid color with that name.");
                        return;
                    }
                }
                team.setColor(color);
            }

        }
    }

    @Override
    public ArrayList<String> getTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> completions = new ArrayList<>();
        if (GeneralMethods.getWar() == null)
            return completions;

        if (args.length == 2)
            for (Team team : GeneralMethods.getWar().getTeams())
                completions.add(team.getName());

        else
            completions.addAll(values);

        return completions;
    }

    private ArrayList<String> values = new ArrayList<>();

    public SetColorSub() {
        values.add("AQUA");
        values.add("BLACK");
        values.add("BLUE");
        values.add("DARK_AQUA");
        values.add("DARK_BLUE");
        values.add("DARK_GRAY");
        values.add("DARK_GREEN");
        values.add("DARK_PURPLE");
        values.add("DARK_RED");
        values.add("GOLD");
        values.add("GRAY");
        values.add("GREEN");
        values.add("LIGHT_PURPLE");
        values.add("RED");
        values.add("WHITE");
        values.add("YELLOW");
    }
}
