package me.astropvp.hotfixplugin.listeners;

import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.StackedMobDeathEvent;
import me.astropvp.cratekeys.enums.CrateType;
import me.astropvp.cratekeys.items.CrateKeyItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnersFix implements Listener {
    private final ItemStack astroKey = CrateKeyItem.getItem(CrateType.ASTRO);

    @EventHandler
    public void onStackedEntityDeath(StackedMobDeathEvent e) {
        Entity entity = e.getEntity();
        if (e.getDamager() instanceof Player) {


            if (!AstroSpawners.getInstance().getMobStackerUtils().isStackedMob(entity)) return;

            if (e.getEntity().getLastDamageCause() != null) {
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) return;
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) return;
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) return;
            }

        if (Math.random() < 0.1) {
            ((Player) e.getDamager()).getInventory().addItem(astroKey);
        }
        }


    }
}
