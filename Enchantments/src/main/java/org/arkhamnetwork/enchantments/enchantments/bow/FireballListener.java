package org.arkhamnetwork.enchantments.enchantments.bow;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.earth2me.essentials.User;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

final class FireballListener implements Listener {
    @EventHandler
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("hellfire")) {
            e.setCancelled(true);
            e.setDamage(0.0);
            if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) e).getDamager() instanceof Player) {
                ((Player) ((EntityDamageByEntityEvent) e).getDamager()).playSound(e.getEntity().getLocation(), Sound.FIRE, 1.0f, 0.7f);
                e.getEntity().remove();
            }
        }
    }

    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent e) throws Exception {
        if (e.getEntity().hasMetadata(Hellfire.hellfireEncahntmentMetadata)) {
            final Location loc = e.getEntity().getLocation();
            if (e.getEntity().getPassenger() != null) {
                e.getEntity().getPassenger().remove();
            }
            if (WorldGuardUtils.isPvPDisabled(loc)) {
                return;
            }
            loc.getWorld().playSound(loc, Sound.FIRE, 1.0f, 0.85f);
            final int level = e.getEntity().getMetadata(Hellfire.hellfireEncahntmentMetadata).get(0).asInt();
            final boolean duelWorld = loc.getWorld().getName().equals("world_duels2");
            int radius = level * 2;
            if (duelWorld) {
                radius /= 2;
            }
            final String enchantment_owner = e.getEntity().hasMetadata(Hellfire.hellfireEncahntmentOwnerMetadata) ? e.getEntity().getMetadata(Hellfire.hellfireEncahntmentOwnerMetadata).get(0).asString() : null;
            final Player owner = Bukkit.getPlayer(enchantment_owner);
            for (final Entity ent : e.getEntity().getNearbyEntities((double) radius, (double) radius, (double) radius)) {
                if (ent.hasMetadata("spectator")) {
                    continue;
                }
                if (!(ent instanceof LivingEntity) || ent.hasMetadata("do_not_clear")) {
                    continue;
                }
                if (enchantment_owner != null && ent instanceof Player) {
                    if (((Player) ent).getName().equals(enchantment_owner) || this.isAlly((Player) ent, owner)) {
                        continue;
                    }
                    if (!this.canHurt((Player) ent, owner)) {
                        continue;
                    }
                }
                if (!(ent instanceof Player)) {
                    continue;
                }
                final Player pEnt = (Player) ent;
                final User u = Hellfire.ess3.getUser(pEnt);
                if (u.isGodModeEnabled() || u.isVanished()) {
                    continue;
                }
                if (WorldGuardUtils.isPvPDisabled(ent.getLocation())) {
                    continue;
                }
                if (ent instanceof EnderDragon) {
                    continue;
                }
                ent.setFireTicks(ent.getFireTicks() + level * 20);
                final LivingEntity le = (LivingEntity) ent;
                if (Bukkit.getPlayer(enchantment_owner) != null) {
                    Bukkit.getScheduler().runTaskLater(Enchantments.getInstance(), () -> {
                        if (!le.isDead()) {
                            final int dmg = 1 + level;
                            le.damage(duelWorld ? (dmg / 1.5) : ((double) dmg));
                        }
                    }, 1L);
                }
                final Location eloc = ent.getLocation().add(0.0, 1.0, 0.0);
                de.inventivegames.particle.ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(),eloc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f, 16);

            }
            de.inventivegames.particle.ParticleEffect.LARGE_EXPLODE.sendToPlayers(Bukkit.getOnlinePlayers(),loc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f, 8);
            de.inventivegames.particle.ParticleEffect.FLAME.sendToPlayers(Bukkit.getOnlinePlayers(),loc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.25f, 45);
            e.getEntity().remove();
        }
    }

    private boolean isAlly(final Player p1, final Player p2) {
        return GeneralUtil.isAlly(p1, p2);
    }

    private boolean canHurt(final Player p1, final Player p2) {
        return GeneralUtil.isEnemy(p1, p2);
    }
}
