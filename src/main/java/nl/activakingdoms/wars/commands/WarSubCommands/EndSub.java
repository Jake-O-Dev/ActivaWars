package nl.activakingdoms.wars.commands.WarSubCommands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;


public class EndSub extends SubCommand {
    @Override
    public String getName() {
        return "end";
    }

    @Override
    public String getDescription() {
        return "End the war, another setup needed after this.";
    }

    @Override
    public String getSyntax() {
        return "/war end";
    }

    @Override
    public String getPermission() {
        return "activawars.war.end";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        if (GeneralMethods.getWar() == null) {
            sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " No war is currently setup. Use /war setup to enable this feature.");
            return;
        }

        GeneralMethods.setWar(null);
        sender.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET +  " You ended the war.");
    }

    @Override
    public ArrayList<String> getTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
