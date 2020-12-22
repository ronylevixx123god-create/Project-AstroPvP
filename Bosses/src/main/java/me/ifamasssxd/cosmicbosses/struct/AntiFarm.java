/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Creature
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmicbosses.struct;

import java.util.List;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.GuardBoss;
import me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost.MotherOfYijki;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.MiniBoss;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AntiFarm {
    private final int THRESHHOLD = 20;
    Boss boss;
    private Location lastLocation;
    int stuckTicks = 0;
    private int unstickAttempts = 0;

    public AntiFarm(Boss boss) {
        this.boss = boss;
    }

    public boolean isStuck() {
        return this.stuckTicks >= 20;
    }

    public boolean checkStuck() {
        if (this.lastLocation != null && this.lastLocation.getWorld() == this.getBoss().getEntity().getWorld()) {
            double distance = this.lastLocation.distanceSquared(this.getBoss().getEntity().getLocation());
            this.stuckTicks = distance <= 10.0 ? ++this.stuckTicks : 0;
        }
        this.lastLocation = this.getEntity().getLocation().clone();
        if (!(BossUtils.isWarzone(this.lastLocation) || this.lastLocation.getWorld().getName().equals(CosmicBosses.battlefieldWorld) || this.boss instanceof GuardBoss || this.boss instanceof MiniBoss)) {
            if (this.unstickAttempts >= 5) {
                this.getBoss().alertNearby(30, "Removing boss due to luring out of warzone > 5 times.", Sound.ENDERDRAGON_GROWL, 1.4f);
                this.getBoss().onDeath(null);
                this.getBoss().getEntity().remove();
                return false;
            }
            ++this.unstickAttempts;
            return true;
        }
        return this.isStuck();
    }

    public boolean checkStuck(Boss boss) {
        return boss.checkStuck();
    }

    public void unstickBoss() {
        Location targetTeleport;
        Entity target = this.getTarget();
        double distance = target != null && target.getWorld().equals((Object)this.getEntity().getWorld()) ? target.getLocation().distanceSquared(this.getEntity().getLocation()) : -1.0;
        List nearby = this.getBoss().getEntity().getNearbyEntities(15.0, 15.0, 15.0);
        if (target != null && target instanceof Player && target.getWorld().equals((Object)this.getEntity().getWorld()) && distance <= 1000.0 && BossUtils.canAffectPlayer((Player)target, false)) {
            Location newLocation = target.getLocation().clone();
            if (this.getBoss().getEntity().isInsideVehicle()) {
                this.getBoss().getEntity().getVehicle().teleport(newLocation);
            }
            targetTeleport = newLocation;
        } else {
            if (this.getBoss().getEntity().isInsideVehicle()) {
                this.getBoss().getEntity().getVehicle().teleport(this.getBoss().getLocation());
            }
            targetTeleport = this.getBoss().getLocation();
        }
        if (this.getBoss().getEntity().getLocation().distanceSquared(targetTeleport) > 10.0 && !(this.getBoss() instanceof GuardBoss) && !(this.getBoss() instanceof MotherOfYijki)) {
            for (Entity entity : nearby) {
                if (!(entity instanceof Player)) continue;
                Player pl = (Player)entity;
                pl.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "The " + this.getBoss().getCustomName() + (Object)ChatColor.YELLOW + " has teleported back to their spawn location!");
            }
        }
        this.getBoss().getEntity().teleport(targetTeleport);
        this.stuckTicks = 0;
        System.out.println("Unsticking boss " + this.boss.getName());
    }

    public Entity getTarget() {
        Creature creature;
        if (this.getLivingEntity() instanceof Creature && (creature = (Creature)this.getLivingEntity()).getTarget() != null) {
            return creature.getTarget();
        }
        return null;
    }

    public Entity getEntity() {
        return this.getBoss().getEntity();
    }

    public LivingEntity getLivingEntity() {
        return (LivingEntity)this.getEntity();
    }

    public Boss getBoss() {
        return this.boss;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public void setStuckTicks(int stuckTicks) {
        this.stuckTicks = stuckTicks;
    }

    public int getStuckTicks() {
        return this.stuckTicks;
    }
}

