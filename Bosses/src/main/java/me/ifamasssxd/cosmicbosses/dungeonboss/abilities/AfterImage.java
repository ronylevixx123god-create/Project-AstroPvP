/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.abilities;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class AfterImage
extends DungeonAbility {
    private Set<Entity> imagesAlive = Collections.newSetFromMap(new WeakHashMap());
    private long start;

    public AfterImage(Boss boss) {
        super(boss);
    }

    @Override
    public void onTick() {
        if (this.start != -1L && System.currentTimeMillis() - this.start >= 3000L) {
            this.start = -1L;
            for (Player pl : this.boss.getEntity().getWorld().getPlayers()) {
                pl.sendMessage((Object)ChatColor.RED + "Yijki's After Images are preparing to charge!");
            }
        }
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(8L);
    }

    public long getSoftCooldown() {
        return TimeUnit.SECONDS.toMillis(10L);
    }

    @Override
    public String getAbilityName() {
        return "After Image";
    }

    @Override
    public boolean shouldStopTicking() {
        long timeSinceStarted = System.currentTimeMillis() - this.initTime;
        return !this.isAnyImagesAlive() && timeSinceStarted >= this.getCooldown() || timeSinceStarted >= this.getSoftCooldown();
    }

    @Override
    public void onAbilityEnable() {
        this.imagesAlive.stream().filter(ent -> !ent.isDead()).forEach(Entity::remove);
        this.imagesAlive.clear();
        this.start = System.currentTimeMillis();
        this.initAbility();
        for (int i = 0; i < 3; ++i) {
            this.imagesAlive.add(this.boss.spawnMinion(EntityType.WITHER, this.getBoss().getEntity().getLocation()));
        }
        this.boss.sendMessage((Object)ChatColor.RED + "Lets make this more interesting!", Sound.WITHER_SPAWN, 1.0f);
    }

    public boolean isAnyImagesAlive() {
        return this.imagesAlive.stream().anyMatch(ent -> !ent.isDead());
    }

    @Override
    public void onAbilityDisable() {
        for (Entity ent : this.boss.getMinions()) {
            if (ent == null || ent.isDead()) continue;
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.CLOUD, (Location)ent.getLocation().add(0.0, 1.0, 0.0), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.1f, (int)30);
        }
        this.boss.removeMinions();
        this.imagesAlive.clear();
    }

    @Override
    public void onAbilityEvent(Event event) {
    }
}

