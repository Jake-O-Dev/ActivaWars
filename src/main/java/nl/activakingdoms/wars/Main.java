package nl.activakingdoms.wars;

import nl.activakingdoms.wars.commands.WarCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        new GeneralMethods(this);

        // setup config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // register commands
        getCommand("war").setExecutor(new WarCommand());

        getServer().getPluginManager().registerEvents(new Handler(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
