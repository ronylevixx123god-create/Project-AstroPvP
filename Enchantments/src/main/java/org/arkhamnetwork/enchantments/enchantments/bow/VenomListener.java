package org.arkhamnetwork.enchantments.enchantments.bow;

import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

final class VenomListener implements Listener {
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
                    if (this.isAlly((Player) ent, Bukkit.getPlayer(enchantment_owner))) {
                        continue;
                    }
                }
                if (!(ent instanceof Player)) {
                    continue;
                }
                ((Player) ent).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 25 * level, 1));
            }
        }
    }

    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
