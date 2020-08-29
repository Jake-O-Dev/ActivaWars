package nl.activakingdoms.wars;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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


}
