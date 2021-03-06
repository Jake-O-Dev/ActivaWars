package nl.activakingdoms.wars;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class War {

    private ArrayList<Setting> settings;
    private ArrayList<Team> teams;
    private ArrayList<Player> dontNotify;

    private HashMap<Player, Team> hitToAdd;
    private ArrayList<Player> hitToRemove;

    public War() {
        settings = new ArrayList<>();
        teams = new ArrayList<>();
        dontNotify = new ArrayList<>();


        hitToAdd = new HashMap<>();
        hitToRemove = new ArrayList<>();

        ArrayList<String> validAnswers = new ArrayList<>();
        validAnswers.add("Yes");
        validAnswers.add("No");
        settings.add(new Setting("allow-respawn", validAnswers, "Yes", "activawars.war.settings.allowrespawn"));

        validAnswers = new ArrayList<>();
        validAnswers.add("True");
        validAnswers.add("False");
        settings.add(new Setting("friendly-fire", validAnswers, "False", "activawars.war.settings.friendlyfire"));
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public boolean removeTeam(Team team) {
        return teams.remove(team);
    }

    public Team getTeam(String name) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name))
                return team;
        }
        return null;
    }

    public Team getTeam(Player player) {
        for (Team team : getTeams()) {
            if (team.getPlayers().contains(player))
                return team;
        }
        return null;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public boolean containsPlayer(Player player) {
        for (Team team : getTeams()) {
            if (team.getPlayers().contains(player))
                return true;
        }
        return false;
    }

    public boolean checkSetting(String name, String value) {
        for (Setting setting : getSettings()) {
            if (setting.getName().equalsIgnoreCase(name)) {
                return value.equalsIgnoreCase(setting.getValue());
            }
        }
        return false;
    }

    public Setting getSetting(String name) {
        for (Setting setting : getSettings()) {
            if (setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }

    public ArrayList<Player> getDontNotify() {
        return dontNotify;
    }

    public void setDontNotify(ArrayList<Player> dontNotify) {
        this.dontNotify = dontNotify;
    }

    //
    // HIT-TO-ADD / HIT-TO-REMOVE
    //

    public HashMap<Player, Team> getHitToAdd() {
        return hitToAdd;
    }

    public Team removeFromAdd(Player player) {
        return hitToAdd.remove(player);
    }

    public void addToAdd(Player player, Team team) {
        hitToAdd.put(player, team);
    }

    public ArrayList<Player> getHitToRemove() {
        return hitToRemove;
    }

    public boolean removeFromRemove(Player player) {
        return hitToRemove.remove(player);
    }

    public void addToRemove(Player player) {
        hitToRemove.add(player);
    }
  
  
    // notifications
    public void addDontNotify(Player player) {
        dontNotify.add(player);
    }

    public boolean removeDontNotify(Player player) {
        return dontNotify.remove(player);
    }
}
