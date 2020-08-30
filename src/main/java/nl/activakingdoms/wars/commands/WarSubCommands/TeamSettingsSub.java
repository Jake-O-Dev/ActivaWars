package nl.activakingdoms.wars.commands.WarSubCommands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.Setting;
import nl.activakingdoms.wars.Team;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TeamSettingsSub extends SubCommand {
    @Override
    public String getName() {
        return "teamsettings";
    }

    @Override
    public String getDescription() {
        return "Edit the settings of a team";
    }

    @Override
    public String getSyntax() {
        return "/war teamsettings <team> <setting> <value>";
    }

    @Override
    public String getPermission() {
        return "activawars.war.teamsettings";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (GeneralMethods.getWar() == null) {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No war is currently setup. Use /war setup to enable this feature.");
            return;
        }

        if (args.length == 1) {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No team specified.");
            return;
        } else if (args.length == 2) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                Team team = GeneralMethods.getWar().getTeam(args[1]);
                if (team != null) {
                    player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Available team settings:");
                    for (Setting setting : team.getSettings()) {
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
                }
            } else {
                sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Only a player doesn't have to specify setting and value.");
            }
        } else if (args.length == 4) {
            Team team = GeneralMethods.getWar().getTeam(args[1]);
            if (team != null) {
                for (Setting setting : team.getSettings()) {
                    if (setting.getName().equalsIgnoreCase(args[2]) && setting.getValidAnswers().contains(args[3])) {
                        setting.setValue(args[3]);
                        sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Setting " + ChatColor.BOLD + setting.getName() + ChatColor.RESET + " updated to " + ChatColor.BOLD + setting.getValue() + ChatColor.RESET + ".");
                        return;
                    }
                }
                sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Setting name and/or value invalid.");
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
        } else {

            Team team = GeneralMethods.getWar().getTeam(args[1]);
            if (team == null) {
                return completions;
            }

            ArrayList<Setting> settings = team.getSettings();

            if (args.length == 3) {
                // suggest [setting]
                for (Setting setting : settings) {
                    if (sender.hasPermission(setting.getPermission()))
                        completions.add(setting.getName());
                }

            } else if (args.length == 4) {
                // suggest [value]
                for (Setting setting : settings) {
                    if (sender.hasPermission(setting.getPermission()) && args[1].equalsIgnoreCase(setting.getName())) {
                        completions.addAll(setting.getValidAnswers());
                    }
                }
            }
        }
        return completions;
    }
}
