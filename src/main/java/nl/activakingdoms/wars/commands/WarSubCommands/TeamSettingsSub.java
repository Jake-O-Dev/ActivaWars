package nl.activakingdoms.wars.commands.WarSubCommands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
                    displaySettings(player, team.getSettings(), team.getName());
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

    private void displaySettings(Player player, ArrayList<Setting> settings, String team) {
        player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Available team settings:");
        for (Setting setting : settings) {
            if (player.hasPermission(setting.getPermission())) {
                TextComponent text = new TextComponent(" - ");
                text.setColor(net.md_5.bungee.api.ChatColor.GRAY);

                TextComponent settingName = new TextComponent(setting.getName());
                settingName.setColor(net.md_5.bungee.api.ChatColor.GRAY);
                settingName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/war teamsettings " + team + " " + setting.getName() + " "));
                text.addExtra(settingName);
                text.addExtra(": ");


                String value = setting.getValidAnswers().get(0);
                TextComponent option = new TextComponent(value);
                option.setColor(net.md_5.bungee.api.ChatColor.GRAY);
                if (value.equals(setting.getValue())) {
                    option.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                }
                if (value.equals(setting.getDefault())) {
                    option.setItalic(true);
                }
                option.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/war teamsettings " + team + " " + setting.getName() + " " + value));
                text.addExtra(option);


                for (int i = 1; i < setting.getValidAnswers().size(); i++) {

                    text.addExtra(" / ");

                    value = setting.getValidAnswers().get(i);
                    option = new TextComponent(value);
                    option.setColor(net.md_5.bungee.api.ChatColor.GRAY);
                    if (value.equals(setting.getValue())) {
                        option.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                    }
                    if (value.equals(setting.getDefault())) {
                        option.setItalic(true);
                    }
                    option.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/war teamsettings " + team + " " + setting.getName() + " " + value));
                    text.addExtra(option);
                }

                player.spigot().sendMessage(text);
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
