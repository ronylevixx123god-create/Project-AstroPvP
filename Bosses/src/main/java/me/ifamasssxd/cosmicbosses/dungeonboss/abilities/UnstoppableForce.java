/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.util.BlockIterator
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.abilities;

import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijki;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonYijkiEntity;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class UnstoppableForce
extends DungeonAbility {
    private Location chargeLocation;
    private Vector direction;
    int ticks = 0;
    long lastSent;

    public UnstoppableForce(Boss boss) {
        super(boss);
        this.chargeLocation = boss.getLocation();
    }

    @Override
    public void onTick() {
        ++this.ticks;
        long time = System.currentTimeMillis() - this.initTime;
        if (time >= 5000L && this.direction == null) {
            DungeonYijkiEntity dungeonYijkiEntity = ((DungeonYijki)this.getBoss()).getBossEntity();
            if (dungeonYijkiEntity.target != null) {
                this.direction = dungeonYijkiEntity.target.getBukkitEntity().getLocation().toVector().subtract(this.getBoss().getEntity().getLocation().toVector());
                if (this.direction.length() != 0.0) {
                    this.direction = this.direction.normalize();
                    this.direction = this.direction.multiply(1.26);
                }
            } else {
                this.direction = this.getBoss().getEntity().getLocation().getDirection();
            }
        }
        if (!this.shouldCharge()) {
            if (System.currentTimeMillis() - this.lastSent >= 1000L || this.lastSent == 0L) {
                this.lastSent = System.currentTimeMillis();
                for (Player player : this.getBoss().getEntity().getWorld().getPlayers()) {
                    player.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "Yijki >> " + (Object)ChatColor.GRAY + "Charging in " + (Object)ChatColor.RED + (Object)ChatColor.BOLD + (int)Math.max(0.0, Math.ceil(5L - (System.currentTimeMillis() - this.initTime) / 1000L)) + "s");
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 3.0f, 1.1f);
                }
            }
            if (this.ticks % 4 == 0) {
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.WITCH_MAGIC, (Location)this.getBoss().getEntity().getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.1f, (int)20);
            }
            if (this.ticks % 3 == 0) {
                UnstoppableForce.drawParticleLineInDirection(this.getBoss().getEntity());
            }
        }
    }

    public static void drawParticleLineInDirection(LivingEntity entity) {
        try {
            BlockIterator iterator = new BlockIterator(entity, 8);
            iterator.forEachRemaining(bl -> ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.MAGIC_CRIT, (Location)bl.getLocation().add(0.5, 0.5, 0.5), (float)0.0f, (float)0.0f, (float)0.0f, (float)0.01f, (int)3));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(30L);
    }

    @Override
    public boolean shouldStopTicking() {
        long timeSinceStarted = System.currentTimeMillis() - this.initTime;
        return timeSinceStarted >= 6400L;
    }

    public boolean shouldCharge() {
        return System.currentTimeMillis() - this.initTime >= 5000L && this.direction != null;
    }

    @Override
    public void onAbilityEnable() {
        this.initAbility();
        this.boss.sendMessage("I cannot be stopped!");
    }

    @Override
    public void onAbilityDisable() {
        if (this.boss.getEntity() != null) {
            ((DungeonYijki)this.boss).getBossEntity().getNavigation().h();
            this.boss.getEntity().teleport(((DungeonYijki)this.boss).getRandomSpawnLocation());
            ((DungeonYijki)this.boss).getBossEntity().getNavigation().h();
            Bukkit.getLogger().info("Teleporting Yijki back to " + (Object)this.boss.getLocation());
        }
        ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.WITCH_MAGIC, (Location)this.boss.getEntity().getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.1f, (int)50);
    }

    @Override
    public void onAbilityEvent(Event event) {
    }

    @Override
    public String getAbilityName() {
        return "Unstoppable Force";
    }

    public Location getChargeLocation() {
        return this.chargeLocation;
    }

    public Vector getDirection() {
        return this.direction;
    }
}

