package nl.activakingdoms.wars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class Handler implements Listener {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent e) {

        War war = GeneralMethods.getWar();

        if (war == null) return;

        if (war.checkSetting("friendly-fire", "False")) {
            if (e.getEntity() instanceof Player) {
                Player target = (Player) e.getEntity();
                if (e.getDamager() instanceof Player) {
                    Player damager = (Player) e.getDamager();
                    if (war.getTeam(damager).equals(war.getTeam(target)) && war.getTeam(damager) != null) {
                        e.setCancelled(true);
                    }
                }
            }
        }

        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (e.getEntity() instanceof Player) {
                Player target = (Player) e.getEntity();
                if (war.getHitToAdd().containsKey(player)) {
                    Team team = war.getHitToAdd().get(player);
                    if (war.containsPlayer(target)) {
                        player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Can't add " + ChatColor.ITALIC + target.getDisplayName() + ChatColor.RESET + ChatColor.RED + " to a team because they are already in one.");
                    } else {
                        team.addPlayer(target);
                        player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Added " + ChatColor.ITALIC + target.getDisplayName() + ChatColor.RESET + " to team " + team.getColor() + team.getName());
                        target.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " You have been added to team " + team.getColor() + team.getName());
                    }
                    e.setCancelled(true);
                } else if (war.getHitToRemove().contains(player)) {
                    if (war.containsPlayer(target)) {
                        Team team = war.getTeam(target);
                        team.removePlayer(target);
                        player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " Removed player " + ChatColor.ITALIC + target.getDisplayName() + ChatColor.RESET + " from team " + team.getColor() + team.getName() + ChatColor.RESET + ".");
                        target.sendMessage(GeneralMethods.getPrefix() + ChatColor.RESET + " You have been removed from team " + team.getColor() + team.getName() + ChatColor.RESET + ".");
                    } else {
                        player.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Can't remove " + ChatColor.ITALIC + target.getDisplayName() + ChatColor.RESET + ChatColor.RED + " from a team because they aren't in one.");
                    }
                    e.setCancelled(true);
                }
            }
        }


    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        War war = GeneralMethods.getWar();

        if (war == null) return;

        Player player = e.getPlayer();
        if (war.containsPlayer(player)) {
            if (war.getTeam(player).getValue("cause-alerts").equalsIgnoreCase("True")) {
                ArrayList<Player> notificationList = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPermission("activawars.war.notifications")) {
                        notificationList.add(p);
                    }
                }
                notificationList.removeAll(war.getDontNotify());
                for (Player p : notificationList) {
                    p.sendMessage(GeneralMethods.getPrefix() + ChatColor.RED + " Player " + player.getName() + " broke " + e.getBlock().getType().name() + " at " + e.getBlock().getX() + "," + e.getBlock().getY() + "," + e.getBlock().getZ());
                }
            }
        }
    }
}
