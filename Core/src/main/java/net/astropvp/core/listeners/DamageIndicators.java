package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.damageindicators.utils.DamageIndicatorUtils;
import net.astropvp.core.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Set;

public class DamageIndicators implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamaged(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            double dmg = event.getFinalDamage();
            if (dmg<=0) {
                return;
            }

            if (event.getEntity().getLastDamageCause().getCause() == null) {
                return;
            }
            if (event.getEntity().getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                return;
            }

            if (dmg <= 4) {
                return;
            }

            Set<Player> players = LocationUtils.getNearbyPlayers(event.getEntity().getLocation(), 16);
            String color = (dmg <= 4 ? CC.YellowB : dmg <= 6 ? CC.GoldB : dmg <= 8 ? CC.RedB : CC.DarkRedB);

            String text = color + "-" + (int) Math.ceil(dmg) + " DMG";
            int x = (int) event.getEntity().getLocation().getX()+1;
            int y = (int) event.getEntity().getLocation().getY()+2;
            int z = (int) event.getEntity().getLocation().getZ()+1;
            players.stream().forEach(player -> DamageIndicatorUtils.createDamageIndicator(player, new Location(Bukkit.getWorld("world"), x, y, z), new String[] {text}));
        }
    }
}
