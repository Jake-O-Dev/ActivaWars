package nl.activakingdoms.wars;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;

public class Handler implements Listener {

    @EventHandler
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
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        War war = GeneralMethods.getWar();

        Player player = e.getPlayer();
        if (war.containsPlayer(player)) {
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
