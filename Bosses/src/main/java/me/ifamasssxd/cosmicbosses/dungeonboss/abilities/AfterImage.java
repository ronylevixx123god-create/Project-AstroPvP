package me.ifamasssxd.cosmicbosses.dungeonboss.abilities;

import me.ifamasssxd.cosmicbosses.dungeonboss.*;
import me.ifamasssxd.cosmicbosses.struct.*;
import java.util.*;
import java.util.concurrent.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.event.*;

public class AfterImage extends DungeonAbility
{
    private Set<Entity> imagesAlive;
    private long start;
    
    public AfterImage(final Boss boss) {
        super(boss);
        this.imagesAlive = Collections.newSetFromMap(new WeakHashMap<Entity, Boolean>());
    }
    
    @Override
    public void onTick() {
        if (this.start != -1L && System.currentTimeMillis() - this.start >= 3000L) {
            this.start = -1L;
            for (final Player pl : this.boss.getEntity().getWorld().getPlayers()) {
                pl.sendMessage(ChatColor.RED + "Yijki's After Images are preparing to charge!");
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
        final long timeSinceStarted = System.currentTimeMillis() - this.initTime;
        return (!this.isAnyImagesAlive() && timeSinceStarted >= this.getCooldown()) || timeSinceStarted >= this.getSoftCooldown();
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
        this.boss.sendMessage(ChatColor.RED + "Lets make this more interesting!", Sound.WITHER_SPAWN, 1.0f);
    }
    
    public boolean isAnyImagesAlive() {
        return this.imagesAlive.stream().anyMatch(ent -> !ent.isDead());
    }
    
    @Override
    public void onAbilityDisable() {
        for (final Entity ent : this.boss.getMinions()) {
            if (ent != null && !ent.isDead()) {
                ParticleEffect.sendToLocation(ParticleEffect.CLOUD, ent.getLocation().add(0.0, 1.0, 0.0), 1.0f, 1.0f, 1.0f, 0.1f, 30);
            }
        }
        this.boss.removeMinions();
        this.imagesAlive.clear();
    }
    
    @Override
    public void onAbilityEvent(final Event event) {
    }
}
