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

final class InfernalListener implements Listener {
    private boolean factions_enabled;

    public InfernalListener() {
        this.factions_enabled = false;
        this.factions_enabled = Bukkit.getPluginManager().isPluginEnabled("Factions");
    }

    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent e) throws Exception {
        if (e.getEntity().hasMetadata(Infernal.infernalEncahntmentMetadata)) {
            final Location loc = e.getEntity().getLocation();
            if (WorldGuardUtils.isPvPDisabled(loc)) {
                return;
            }
            final int radius;
            final int level = radius = e.getEntity().getMetadata(Infernal.infernalEncahntmentMetadata).get(0).asInt();
            final String enchantment_owner = e.getEntity().hasMetadata(Infernal.infernalEncahntmentOwnerMetadata) ? e.getEntity().getMetadata(Infernal.infernalEncahntmentOwnerMetadata).get(0).asString() : null;
            for (final Entity ent : e.getEntity().getNearbyEntities((double) radius, (double) radius, (double) radius)) {
                if (!ent.hasMetadata("spectator")) {
                    if (ent.hasMetadata("do_not_clear")) {
                        continue;
                    }
                    if (!(ent instanceof LivingEntity)) {
                        continue;
                    }
                    if (enchantment_owner != null && ent instanceof Player) {
                        if (((Player) ent).getName().equals(enchantment_owner)) {
                            continue;
                        }
                        if (this.isAlly((Player) ent, Bukkit.getPlayer(enchantment_owner))) {
                            continue;
                        }
                    }
                    ent.setFireTicks(radius * 20);
                }
            }
            de.inventivegames.particle.ParticleEffect.FLAME.sendToPlayers(Bukkit.getOnlinePlayers(), loc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.20f, 10);
        }
    }

    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }
}
