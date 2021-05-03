package me.astropvp.astroendgrinding.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;


public final class EntityDeathEvent extends Event implements Cancellable {
    private static final HandlerList handlers;
    private boolean cancel;
    private LivingEntity entity;
    private Entity damager;
    private int xp;
    private Location spawnLocation;
    private DamageCause cause;
    private List<ItemStack> drops;

    public EntityDeathEvent(LivingEntity entity, Entity damager, DamageCause cause) {
        this.entity = entity;
        this.damager = damager;
        this.cause = cause;
        this.xp = 0;
        this.spawnLocation = Bukkit.getWorld("world").getSpawnLocation();
        this.drops = Lists.newArrayList();
    }

    public static final HandlerList getHandlerList() {
        return EntityDeathEvent.handlers;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

    public List<ItemStack> getDrops() {
        return this.drops;
    }

    public void setDrops(List<ItemStack> drops) {
        this.drops = drops;
    }

    public void addDrop(ItemStack item) {
        this.drops.add(item);
    }

    public Entity getDamager() {
        return this.damager;
    }

    public void setDroppedExp(int xp) {
        this.xp = xp;
    }

    public DamageCause getDamageCause() {
        return this.cause;
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

    public int getDroppedExp() {
        return this.xp;
    }

    @Override
    public final HandlerList getHandlers() {
        return EntityDeathEvent.handlers;
    }

    @Override
    public final void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public final boolean isCancelled() {
        return this.cancel;
    }

    static {
        handlers = new HandlerList();
    }
}
