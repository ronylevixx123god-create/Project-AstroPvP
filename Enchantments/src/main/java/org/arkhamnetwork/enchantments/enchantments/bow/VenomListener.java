package org.arkhamnetwork.enchantments.enchantments.bow;

import org.bukkit.event.entity.*;
import org.bukkit.metadata.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.event.*;
import org.arkhamnetwork.enchantments.util.*;

final class VenomListener implements Listener
{
    private boolean factions_enabled;
    
    public VenomListener() {
        this.factions_enabled = false;
        this.factions_enabled = Bukkit.getPluginManager().isPluginEnabled("Factions");
    }
    
    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent e) {
        if (e.getEntity().hasMetadata(Venom.venomEncahntmentMetadata)) {
            final Location loc = e.getEntity().getLocation();
            if (e.getEntity().getPassenger() != null) {
                e.getEntity().getPassenger().remove();
            }
            if (WorldGuardUtils.isPvPDisabled(loc)) {
                return;
            }
            final int radius;
            final int level = radius = e.getEntity().getMetadata(Venom.venomEncahntmentMetadata).get(0).asInt();
            final String enchantment_owner = e.getEntity().hasMetadata(Venom.venomEncahntmentOwnerMetadata) ? e.getEntity().getMetadata(Venom.venomEncahntmentOwnerMetadata).get(0).asString() : null;
            for (final Entity ent : e.getEntity().getNearbyEntities(radius, radius, radius)) {
                if (ent.hasMetadata("spectator")) {
                    continue;
                }
                if (!(e.getEntity() instanceof Player)) {
                    continue;
                }
                if (!(ent instanceof LivingEntity)) {
                    continue;
                }
                if (enchantment_owner != null && ent instanceof Player) {
                    if (ent.getName().equals(enchantment_owner)) {
                        continue;
                    }
                    if (this.isAlly((Player)ent, Bukkit.getPlayer(enchantment_owner))) {
                        continue;
                    }
                }
                if (!(ent instanceof Player)) {
                    continue;
                }
                ((Player)ent).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 25 * level, 1));
            }
        }
    }
    
    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
