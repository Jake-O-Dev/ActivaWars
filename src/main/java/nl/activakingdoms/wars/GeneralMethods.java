package nl.activakingdoms.wars;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

public class GeneralMethods {

    private static Main plugin;
    private static War war = null;
    private static LuckPerms api;

    public GeneralMethods(final Main plugin) {
        GeneralMethods.plugin = plugin;
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            GeneralMethods.api = provider.getProvider();

        }
    }

    public static War getWar() {
        return war;
    }

    public static void setWar(War war) {
        GeneralMethods.war = war;
    }

    public static Main getPlugin() {
        return GeneralMethods.plugin;
    }

    public static String getPrefix() {
        // load config
        if (getPlugin().getConfig().isSet("prefix")) {
            return ChatColor.translateAlternateColorCodes('&', getPlugin().getConfig().getString("prefix"));
        } else {
            getPlugin().getLogger().warning("No prefix found in config.");
            return ChatColor.translateAlternateColorCodes('&', "&7[&6AK&7]");
        }
    }

    public static LuckPerms getApi() {
        return GeneralMethods.api;
    }



}
