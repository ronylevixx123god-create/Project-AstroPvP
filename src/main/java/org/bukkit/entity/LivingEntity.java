/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public interface LivingEntity
extends Entity,
Damageable,
ProjectileSource {
    public double getEyeHeight();

    public double getEyeHeight(boolean var1);

    public Location getEyeLocation();

    @Deprecated
    public List<Block> getLineOfSight(HashSet<Byte> var1, int var2);

    @Deprecated
    public Block getTargetBlock(HashSet<Byte> var1, int var2);

    @Deprecated
    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> var1, int var2);

    @Deprecated
    public Egg throwEgg();

    @Deprecated
    public Snowball throwSnowball();

    @Deprecated
    public Arrow shootArrow();

    public int getRemainingAir();

    public void setRemainingAir(int var1);

    public int getMaximumAir();

    public void setMaximumAir(int var1);

    public int getMaximumNoDamageTicks();

    public void setMaximumNoDamageTicks(int var1);

    public double getLastDamage();

    @Deprecated
    public int _INVALID_getLastDamage();

    public void setLastDamage(double var1);

    @Deprecated
    public void _INVALID_setLastDamage(int var1);

    public int getNoDamageTicks();

    public void setNoDamageTicks(int var1);

    public Player getKiller();

    public boolean addPotionEffect(PotionEffect var1);

    public boolean addPotionEffect(PotionEffect var1, boolean var2);

    public boolean addPotionEffects(Collection<PotionEffect> var1);

    public boolean hasPotionEffect(PotionEffectType var1);

    public void removePotionEffect(PotionEffectType var1);

    public Collection<PotionEffect> getActivePotionEffects();

    public boolean hasLineOfSight(Entity var1);

    public boolean getRemoveWhenFarAway();

    public void setRemoveWhenFarAway(boolean var1);

    public EntityEquipment getEquipment();

    public void setCanPickupItems(boolean var1);

    public boolean getCanPickupItems();

    public void setCustomName(String var1);

    public String getCustomName();

    public void setCustomNameVisible(boolean var1);

    public boolean isCustomNameVisible();

    public boolean isLeashed();

    public Entity getLeashHolder() throws IllegalStateException;

    public boolean setLeashHolder(Entity var1);
}

