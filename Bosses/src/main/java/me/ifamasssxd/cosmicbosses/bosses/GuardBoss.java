/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.ParticleEffect
 *  me.ifamasssxd.factionupgrades.FactionUpgradeAPI
 *  me.ifamasssxd.factionupgrades.struct.FactionUpgrade
 *  org.bukkit.Location
 *  org.bukkit.entity.Creature
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package me.ifamasssxd.cosmicbosses.bosses;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.intergalacticenforcer.Enforcer;
import me.ifamasssxd.cosmicbosses.evilabomination.EvilAbomination;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface GuardBoss {
    default public void checkGuardLocation(Boss boss, Location chest) {
        double distance;
        LivingEntity ent = boss.getEntity();
        if (ent.getLocation().getWorld() != chest.getWorld()) {
            this.teleportToChest(boss, chest);
            return;
        }
        Creature creature = (Creature)ent;
        if (creature.getTarget() != null && creature.getTarget().getWorld() != chest.getWorld()) {
            creature.setTarget(null);
        }
        if (creature.getTarget() == null && (distance = creature.getLocation().distanceSquared(chest)) > (double)this.getDistance()) {
            this.teleportToChest(boss, chest);
        }
    }

    default public void onDamage(Boss boss, EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            int level;
            if (CosmicBosses.getInstance().isFactionUpgradesEnabled() && (level = FactionUpgradeAPI.getPerkLevel((Player)((Player)event.getDamager()), (FactionUpgrade)FactionUpgrade.CONQUEST_MASTERY)) > 0) {
                double mult = level == 1 ? 1.5 : (level == 2 ? 2.0 : (level == 3 ? 2.5 : (level == 4 ? 3.0 : 1.0)));
                event.setDamage(event.getDamage() * mult);
            }
            return;
        }
        if (event.getDamager() instanceof Projectile && ((Projectile)event.getDamager()).getShooter() instanceof Player) {
            return;
        }
        event.setCancelled(true);
    }

    default public int checkOutsideTicks(Boss boss, int currentTicks, Location chest) {
        Creature creature = (Creature)boss.getEntity();
        if (creature.getTarget() != null && (creature.getTarget().getWorld() != chest.getWorld() || creature.getTarget().getLocation().distanceSquared(chest) > 200.0 && creature.getLocation().distanceSquared(chest) > 150.0)) {
            int newTicks = currentTicks + 1;
            if (newTicks >= 5) {
                creature.setTarget(null);
                this.teleportToChest(boss, chest);
                return 0;
            }
            return newTicks;
        }
        return 0;
    }

    default public int getDistance() {
        return this instanceof Enforcer ? 70 : 35;
    }

    default public void teleportToChest(Boss boss, Location chestLocation) {
        boss.getEntity().teleport(chestLocation.clone().add(0.0, 1.0, 0.0));
        if (boss instanceof EvilAbomination) {
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.CLOUD, (Location)boss.getEntity().getLocation(), (float)0.5f, (float)0.5f, (float)0.5f, (float)0.01f, (int)5);
        }
    }
}

