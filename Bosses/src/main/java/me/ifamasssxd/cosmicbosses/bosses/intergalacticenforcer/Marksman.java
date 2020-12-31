/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_7_R4.EntityCreature
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityInsentient
 *  net.minecraft.server.v1_7_R4.EntitySkeleton
 *  net.minecraft.server.v1_7_R4.GenericAttributes
 *  net.minecraft.server.v1_7_R4.IRangedEntity
 *  net.minecraft.server.v1_7_R4.PathfinderGoal
 *  net.minecraft.server.v1_7_R4.PathfinderGoalFloat
 *  net.minecraft.server.v1_7_R4.PathfinderGoalHurtByTarget
 *  net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer
 *  net.minecraft.server.v1_7_R4.PathfinderGoalMoveTowardsRestriction
 *  net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget
 *  net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround
 *  net.minecraft.server.v1_7_R4.PathfinderGoalSelector
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftCreature
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftSkeleton
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Skeleton
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicbosses.bosses.intergalacticenforcer;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicbosses.bosses.GuardBoss;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.PathfinderGoalBossArrowAttack;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.CustomItem;
import me.ifamasssxd.cosmicbosses.utils.ReflectionUtils;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSkeleton;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Marksman
extends Boss
implements GuardBoss {
    private int outsideTicks = 0;

    public Marksman(Location location, BossType bossType) {
        super(location, bossType);
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SKELETON;
    }

    @Override
    public LivingEntity setupBoss(LivingEntity e) {
        LivingEntity entity = super.setupBoss(e);
        EntityCreature creature = ((CraftCreature)entity).getHandle();
        creature.getAttributeInstance(GenericAttributes.b).setValue(16.0);
        creature.getAttributeInstance(GenericAttributes.d).setValue((double)0.4f);
        creature.getAttributeInstance(GenericAttributes.c).setValue((double)0.65f);
        creature.getAttributeInstance(GenericAttributes.e).setValue(2.0);
        Skeleton skeleton = (Skeleton)entity;
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        return entity;
    }

    @Override
    public void onBossSpawn() {
        Skeleton skeleton = (Skeleton)this.spawnBoss();
        EntitySkeleton entity = ((CraftSkeleton)skeleton).getHandle();
        ReflectionUtils.setObject(EntitySkeleton.class,entity,new PathfinderGoalBossArrowAttack(entity, 1.0, 5, 20, 15.0f), "bp");
        BossUtils.clearGoalSelectors(entity);
        PathfinderGoalSelector targetSelector = BossUtils.getSelector(entity, "targetSelector");
        PathfinderGoalSelector goalSelector = BossUtils.getSelector(entity, "goalSelector");
        goalSelector.a(1, new PathfinderGoalFloat(entity));
        goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(entity, 1.3));
        goalSelector.a(6, new PathfinderGoalLookAtPlayer(entity, EntityHuman.class, 8.0f));
        goalSelector.a(6, new PathfinderGoalRandomLookaround(entity));
        targetSelector.a(1, new PathfinderGoalHurtByTarget(entity, false));
        targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(entity, EntityHuman.class, 0, true));
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        this.setHelmet(new CustomItem(new ItemStack(Material.GOLD_HELMET)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10).addEnchant(Enchantment.PROTECTION_FIRE, 5).build());
        this.setChestplate(new CustomItem(new ItemStack(Material.GOLD_CHESTPLATE)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10).build());
        this.setLeggings(new CustomItem(new ItemStack(Material.GOLD_LEGGINGS)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10).addEnchant(Enchantment.THORNS, 1).build());
        this.setBoots(new CustomItem(new ItemStack(Material.GOLD_BOOTS)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10).build());
        skeleton.getEquipment().setItemInHand(new CustomItem(new ItemStack(Material.BOW)).setUnbreakable(true).addEnchant(Enchantment.ARROW_KNOCKBACK, 5).addEnchant(Enchantment.ARROW_FIRE, 5).addEnchant(Enchantment.ARROW_DAMAGE, 10).build());
        skeleton.getEquipment().setItemInHandDropChance(0.0f);
        BossMessage start = this.getStartingMessage();
        if (start != null) {
            this.sendMessage((Object)ChatColor.ITALIC + start.getMessage());
        }
    }

    @Override
    public String getCustomName() {
        return ChatColor.DARK_PURPLE.toString() +ChatColor.BOLD + "Intergalactic Marksman";
    }

    @Override
    public String getName() {
        return "Intergalactic Marksman";
    }

    @Override
    public void cleanup() {
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onPhaseChange(Phase newPhase) {
    }

    @Override
    public void onMiscUpdate(Event event) {
    }

    @Override
    public void onDamageTaken(EntityDamageEvent event) {
        event.setDamage(event.getDamage() / 2.0);
        super.onDamageTaken(event);
    }

    @Override
    public void onUpdate() {
        ++this.loops;
        if (this.loops % 20 == 0 && this.getAntiFarm().checkStuck()) {
            this.getAntiFarm().unstickBoss();
        }
        this.checkGuardLocation(this, this.location);
        if (this.loops % 3 == 0) {
            this.outsideTicks = this.checkOutsideTicks(this, this.outsideTicks, this.location);
        }
        super.onUpdate();
    }

    @Override
    public void onDamageTaken(EntityDamageByEntityEvent event, Entity damager) {
        if (damager instanceof Projectile) {
            event.setCancelled(true);
            this.getEntity().getWorld().playSound(this.getEntity().getLocation(), Sound.ZOMBIE_INFECT, 1.5f, 2.0f);
            damager.remove();
            return;
        }
        super.onDamageTaken(event, damager);
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        int percentDamage = ThreadLocalRandom.current().nextInt(15) + 10;
        if (this.random.nextInt(100) < 30) {
            percentDamage += 5;
        }
        BossUtils.dealPercentMaxHealthDamage(player, percentDamage, 25, false);
        player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 3.0f, 1.3f);
        if (player.isSprinting() && this.random.nextInt(100) < 20) {
            player.setSprinting(false);
        }
        super.onAttackPlayer(event, player);
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.FIRE;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.LEGENDARY;
    }

    @Override
    public int getMaxHealth() {
        return 600;
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList(new BossMessage("You will crumble under my arrow!", false, -1));
    }
}

