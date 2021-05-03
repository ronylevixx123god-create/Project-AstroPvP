package me.astropvp.astroendgrinding.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import com.google.common.collect.Lists;

public final class EntityDamageEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel;
    private LivingEntity entity;
    private Entity damager;
    private DamageCause cause;
    private double damage;
    private List<Double> divisors;
    private List<Double> multiplicators;
    private double extraDamage;

    public EntityDamageEvent(LivingEntity entity, Entity damager, DamageCause cause) {
        this.entity = entity;
        this.damager = damager;
        this.cause = cause;
        this.damage = 0;
        this.extraDamage = 0;
        this.divisors = Lists.newArrayList();
        this.multiplicators = Lists.newArrayList();
    }

    public Entity getDamager() {
        return this.damager;
    }

    public DamageCause getDamageCause() {
        return this.cause;
    }

    public void addDivisor(double divisor) {
        this.divisors.add(divisor);
    }

    public void addMultiplicator(double multiplicator) {
        this.multiplicators.add(multiplicator);
    }

    public void addDamage(double damage) {
        this.damage += damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void removeDamage(double remove) {
        this.damage -= damage;
    }

    public void setExtraDamage(double extraDamage) {
        this.extraDamage = extraDamage;
    }

    public void addExtraDamage(double extraDamage) {
        this.extraDamage += extraDamage;
    }

    public double getExtraDamage() {
        return this.extraDamage;
    }


    public Entity getRealDamager() {
        Entity damager;
        if (this.damager != null && this.damager instanceof Projectile) {
            if (((Projectile) this.damager).getShooter() instanceof LivingEntity) {
                damager = (LivingEntity) ((Projectile) this.damager).getShooter();
            } else {
                damager = this.damager;
            }
        } else {
            damager = this.damager;
        }
        return damager;
    }



    public int getPotionEffectLevel(LivingEntity entity, PotionEffectType type) {
        for (PotionEffect effect : entity.getActivePotionEffects()) {
            if (effect.getType().equals(type)) {
                return effect.getAmplifier();
            }
        }
        return -1;
    }



    public LivingEntity getEntity() {
        return this.entity;
    }

    public final HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }


    public boolean isCancelled() {
        return this.cancel;
    }
}
