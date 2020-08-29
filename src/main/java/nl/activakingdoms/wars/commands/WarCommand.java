package nl.activakingdoms.wars.commands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.commands.WarSubCommands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class WarCommand implements TabExecutor {

    private ArrayList<SubCommand> subCommands;

    public WarCommand() {
        subCommands = new ArrayList<>();

        subCommands.add(new SetupSub());
        subCommands.add(new EndSub());

        subCommands.add(new SettingsSub());
        subCommands.add(new SetColorSub());

        subCommands.add(new AddTeamSub());
        subCommands.add(new RemoveTeamSub());

        subCommands.add(new AddPlayerSub());
        subCommands.add(new RemovePlayerSub());

    }

    private ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {



        if (sender.hasPermission("activawars.war")) {

            if (args.length > 0) {
                // has subcommand logic
                for (SubCommand sub : getSubCommands()) {
                    if (sub.getName().equalsIgnoreCase(args[0])) {
                        sub.perform(sender, args);
                    }
                }
                if (args[0].equalsIgnoreCase("help")) {
                    displayHelp(sender);
                }
            } else {
                displayHelp(sender);
            }

        } else {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " You do not have the permission to use this command.");
        }

        return true;
    }

    private void displayHelp(CommandSender sender) {
        sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.GOLD + " Help:");
        for (SubCommand sub : getSubCommands()) {
            if (sender.hasPermission(sub.getPermission()))
                sender.sendMessage(ChatColor.YELLOW + "  " + sub.getSyntax() + " - " + sub.getDescription());
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        ArrayList<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // suggest subcommands
            for (SubCommand sub : getSubCommands()) {
                if (sender.hasPermission(sub.getPermission()))
                    completions.add(sub.getName());
            }

            return completions;
        } else {
            // suggest arguments for subcommand
            for (SubCommand sub : getSubCommands()) {
                if (args[0].equalsIgnoreCase(sub.getName())) {

                    completions = sub.getTabComplete(sender, args);

                    if (completions == null) {
                        return null;
                    }

                    if (args[args.length -1].length() > 0) {
                        ArrayList<String> corrected = new ArrayList<>();
                        for (String string : completions) {
                            if (string.startsWith(args[args.length - 1])) {
                                corrected.add(string);
                            }
                        }
                        return corrected;
                    }

                    return completions;
                }
            }
        }

        return completions;
    }
}
