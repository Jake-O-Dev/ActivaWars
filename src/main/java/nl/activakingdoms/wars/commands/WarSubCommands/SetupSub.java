package nl.activakingdoms.wars.commands.WarSubCommands;

import nl.activakingdoms.wars.GeneralMethods;
import nl.activakingdoms.wars.War;
import nl.activakingdoms.wars.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class SetupSub extends SubCommand {
    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getDescription() {
        return "Start setting up a new war.";
    }

    @Override
    public String getSyntax() {
        return "/war setup";
    }

    @Override
    public String getPermission() {
        return "activawars.setup";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        GeneralMethods.setWar(new War());
        String prefix = GeneralMethods.getPrefix();
        sender.sendMessage(prefix + ChatColor.RESET + " A new war has been setup, do '/war settings' to edit the settings.");
    }

    @Override
    public ArrayList<String> getTabComplete(CommandSender sender, String[] args) {
        // geen extra line
        return new ArrayList<>();
    }
}
