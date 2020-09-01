package nl.activakingdoms.wars.commands.WarSubCommands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.Setting;
import nl.activakingdoms.wars.War;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class SettingsSub extends SubCommand {
    @Override
    public String getName() {
        return "settings";
    }

    @Override
    public String getDescription() {
        return "Edit the settings of the war.";
    }

    @Override
    public String getSyntax() {
        return "/war settings [setting] [value]";
    }

    @Override
    public String getPermission() {
        return "activakingdoms.war.settings";
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
                displaySettings(player, GeneralMethods.getWar().getSettings());
            } else {
                sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Only a player doesn't have to specify setting and value.");
            }
        }

        else if (args.length == 3) {
            for (Setting setting : GeneralMethods.getWar().getSettings()) {
                if (setting.getName().equalsIgnoreCase(args[1]) && setting.getValidAnswers().contains(args[2])) {
                    setting.setValue(args[2]);
                    sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Setting " + ChatColor.BOLD + setting.getName() + ChatColor.RESET + " updated to " + ChatColor.BOLD + setting.getValue() + ChatColor.RESET + ".");
                    return;
                }
            }
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Setting name and/or value invalid.");
        }

        else {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Invalid amount of arguments.");
        }
    }

    private void displaySettings(Player player, ArrayList<Setting> settings) {
        player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Available settings:");
        for (Setting setting : settings) {
            if (player.hasPermission(setting.getPermission())) {
                TextComponent text = new TextComponent(" - ");
                text.setColor(net.md_5.bungee.api.ChatColor.GRAY);

                TextComponent settingName = new TextComponent(setting.getName());
                settingName.setColor(net.md_5.bungee.api.ChatColor.GRAY);
                settingName.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/war settings " + setting.getName() + " "));
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
                option.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/war settings " + setting.getName() + " " + value));
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
                    option.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/war settings " + setting.getName() + " " + value));
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

        ArrayList<Setting> settings = GeneralMethods.getWar().getSettings();

        if (args.length == 2) {
            // suggest [setting]
            for (Setting setting : settings) {
                if (sender.hasPermission(setting.getPermission()))
                    completions.add(setting.getName());
            }

        } else if (args.length == 3) {
            // suggest [value]
            for (Setting setting : settings) {
                if (sender.hasPermission(setting.getPermission()) && args[1].equalsIgnoreCase(setting.getName())) {
                    completions.addAll(setting.getValidAnswers());
                }
            }
        }
        return completions;
    }
}
