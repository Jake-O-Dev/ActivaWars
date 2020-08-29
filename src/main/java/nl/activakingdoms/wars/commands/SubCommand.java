package nl.activakingdoms.wars.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;


public abstract class SubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract String getPermission();

    public abstract void perform(CommandSender sender, String[] args);

    public abstract ArrayList<String> getTabComplete(CommandSender sender, String[] args);
}
