package nl.activakingdoms.wars;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Team {

    private String name;
    private ChatColor color = ChatColor.WHITE;
    private ArrayList<Player> players;
    private ArrayList<Setting> settings;

    public Team (String name) {
        this.name = name;
        players = new ArrayList<>();
        settings = new ArrayList<>();

        ArrayList<String> validAnswers = new ArrayList<>();
        validAnswers.add("True");
        validAnswers.add("False");
        settings.add(new Setting("cause-alerts", validAnswers, "True", "activawars.war.teamsettings.blockbreakalerts"));
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
        updatePlayerColors();
    }


    //
    // PLAYERS
    //

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        players.add(player);
        updatePlayerColors();
    }

    public boolean removePlayer(Player player) {
        player.setDisplayName(ChatColor.RESET + player.getName() + ChatColor.RESET);
        player.setPlayerListName(ChatColor.RESET + player.getName() + ChatColor.RESET);
        return players.remove(player);
    }

    public void updatePlayerColors() {
        for (Player player : getPlayers()) {
            player.setDisplayName(getColor() + player.getName() + ChatColor.RESET);
            player.setPlayerListName(getColor() + player.getName() + ChatColor.RESET);
        }

    }

    //
    // Settings
    //


    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public Setting getSetting(String name) {
        for (Setting setting : getSettings()) {
            if (setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    public void setValue(String name, String value) {
        Setting setting = getSetting(name);
        if (setting != null) {
            setting.setValue(value);
        }
    }

    public String getValue(String name) {
        Setting setting = getSetting(name);
        if (setting != null) {
            return setting.getValue();
        }
        return null;
    }
}
