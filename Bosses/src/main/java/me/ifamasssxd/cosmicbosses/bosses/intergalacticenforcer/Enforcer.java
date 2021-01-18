/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntityCreature
 *  net.minecraft.server.v1_7_R4.EntityHuman
 *  net.minecraft.server.v1_7_R4.EntityInsentient
 *  net.minecraft.server.v1_7_R4.GenericAttributes
 *  net.minecraft.server.v1_7_R4.PathfinderGoal
 *  net.minecraft.server.v1_7_R4.PathfinderGoalFloat
 *  net.minecraft.server.v1_7_R4.PathfinderGoalHurtByTarget
 *  net.minecraft.server.v1_7_R4.PathfinderGoalLookAtPlayer
 *  net.minecraft.server.v1_7_R4.PathfinderGoalMeleeAttack
 *  net.minecraft.server.v1_7_R4.PathfinderGoalMoveTowardsRestriction
 *  net.minecraft.server.v1_7_R4.PathfinderGoalNearestAttackableTarget
 *  net.minecraft.server.v1_7_R4.PathfinderGoalRandomLookaround
 *  net.minecraft.server.v1_7_R4.PathfinderGoalSelector
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftCreature
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Zombie
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityTargetEvent
 *  org.bukkit.event.entity.EntityTargetEvent$TargetReason
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicbosses.bosses.intergalacticenforcer;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicbosses.bosses.GuardBoss;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.CustomItem;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.ItemStack;

public class Enforcer
extends Boss
implements GuardBoss {
    private long lastTargetChange = 0L;
    private long lastCheck = 0L;
    private int outsideTicks = 0;

    public Enforcer(Location location, BossType bossType) {
        super(location, bossType);
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public String getCustomName() {
        return ChatColor.YELLOW + ChatColor.BOLD.toString() + "Cosmic Enforcer";
    }

    @Override
    public String getName() {
        return "Cosmic Enforcer";
    }

    @Override
    public LivingEntity setupBoss(LivingEntity e) {
        LivingEntity entity = super.setupBoss(e);
        EntityCreature creature = ((CraftCreature)entity).getHandle();
        creature.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(24.0);
        creature.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue((double)0.55f);
        creature.getAttributeInstance(GenericAttributes.c).setValue((double)0.85f);
        creature.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(3.0);
        Zombie zombie = this.getPigZombie();
        zombie.setBaby(false);
        zombie.setVillager(false);
        return entity;
    }

    @Override
    public void onBossSpawn() {
        LivingEntity ent = this.spawnBoss();
        EntityCreature insent = ((CraftCreature)ent).getHandle();
        EntityCreature creature = ((CraftCreature)ent).getHandle();
        BossUtils.clearGoalSelectors(insent);
        PathfinderGoalSelector targetSelector = BossUtils.getSelector(insent, "targetSelector");
        PathfinderGoalSelector goalSelector = BossUtils.getSelector(insent, "goalSelector");
        goalSelector.a(0, new PathfinderGoalFloat(insent));
        goalSelector.a(2, new PathfinderGoalMeleeAttack(creature, EntityHuman.class, 1.2, false));
        goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(creature, 1.3));
        goalSelector.a(8, new PathfinderGoalLookAtPlayer(insent, EntityHuman.class, 8.0f));
        goalSelector.a(8, new PathfinderGoalRandomLookaround(insent));
        targetSelector.a(1, new PathfinderGoalHurtByTarget(creature, true));
        targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(creature, EntityHuman.class, 0, true));
        this.setHelmet(new CustomItem(new ItemStack(Material.DIAMOND_HELMET)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.PROTECTION_FIRE, 5).build());
        this.setChestplate(new CustomItem(new ItemStack(Material.DIAMOND_CHESTPLATE)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.setLeggings(new CustomItem(new ItemStack(Material.DIAMOND_LEGGINGS)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchant(Enchantment.THORNS, 1).build());
        this.setBoots(new CustomItem(new ItemStack(Material.DIAMOND_BOOTS)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        ent.getEquipment().setItemInHand(new CustomItem(new ItemStack(Material.DIAMOND_SWORD)).setUnbreakable(true).addEnchant(Enchantment.KNOCKBACK, 1).addEnchant(Enchantment.DAMAGE_ALL, 5).build());
        ent.getEquipment().setItemInHandDropChance(0.0f);
    }

    @Override
    public void cleanup() {
        this.clearScoreboard();
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
        if (event instanceof EntityTargetEvent) {
            EntityTargetEvent eve = (EntityTargetEvent)event;
            if (!(eve.getTarget() instanceof Player)) {
                eve.setCancelled(true);
                return;
            }
            if (eve.getReason() == EntityTargetEvent.TargetReason.CUSTOM || eve.getReason() == EntityTargetEvent.TargetReason.CLOSEST_PLAYER) {
                return;
            }
            eve.setCancelled(true);
        }
    }

    public Zombie getPigZombie() {
        return (Zombie)this.getEntity();
    }

    @Override
    public void onUpdate() {
        ++this.loops;
        if (this.loops % 16 == 0) {
            this.updateScoreboard();
        }
        if (this.loops % 10 == 0 && this.getAntiFarm().checkStuck()) {
            this.getAntiFarm().unstickBoss();
        }
        this.checkGuardLocation(this, this.location);
        if (!(this.lastTargetChange != 0L && System.currentTimeMillis() - this.lastTargetChange <= 600L && this.getPigZombie().getTarget() != null && this.getPigZombie().getTarget().isValid() || this.lastCheck != 0L && System.currentTimeMillis() - this.lastCheck <= 500L || !this.getEntity().getWorld().equals((Object)this.location.getWorld()))) {
            Player closest;
            double distanceFromChest = this.location.distanceSquared(this.getEntity().getLocation());
            if (distanceFromChest >= 325.0) {
                this.getEntity().teleport(this.location.clone().add(0.0, 1.0, 0.0));
                closest = this.getClosestTarget(64.0);
                if (closest != null) {
                    this.setTarget((LivingEntity)closest);
                    return;
                }
                this.setTarget(null);
            }
            closest = this.getClosestTarget(64.0);
            this.outsideTicks = this.checkOutsideTicks(this, this.outsideTicks, this.location);
            this.lastCheck = System.currentTimeMillis();
            if (closest != null) {
                this.lastTargetChange = System.currentTimeMillis();
                this.setTarget(null);
                this.setTarget((LivingEntity)closest);
                this.outsideTicks = 0;
            }
        }
        super.onUpdate();
    }

    public Player getClosestTarget(double range) {
        double closest = -1.0;
        Player closestPlayer = null;
        double radius = 30.0;
        radius *= radius;
        for (org.bukkit.entity.Entity ent : this.getEntity().getNearbyEntities(range, range / 2.0, range)) {
            Player pl;
            if (!(ent instanceof Player) || !(pl = (Player)ent).getWorld().equals((Object)this.location.getWorld()) || pl.getGameMode() != GameMode.SURVIVAL) continue;
            double distance = pl.getLocation().distanceSquared(this.location);
            if (!(closest == -1.0 && distance <= radius) && !(distance <= closest)) continue;
            closestPlayer = pl;
            closest = distance;
        }
        if (closestPlayer != null && closest <= radius) {
            return closestPlayer;
        }
        return null;
    }

    public void setTarget(LivingEntity target) {
        EntityCreature entity = ((CraftCreature)this.getPigZombie()).getHandle();
        if (target == null) {
            entity.target = null;
            entity.setGoalTarget(null);
        } else if (target instanceof CraftLivingEntity) {
            entity.target = ((CraftLivingEntity)target).getHandle();
            entity.pathEntity = entity.world.findPath((Entity)entity, entity.target, 45.0f, true, false, false, true);
            entity.setGoalTarget(((CraftLivingEntity)target).getHandle());
        }
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        int percentDamage = ThreadLocalRandom.current().nextInt(11) + 7;
        if (this.random.nextInt(100) < 30) {
            percentDamage += 5;
            player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 3.0f, 1.3f);
        }
        BossUtils.dealPercentMaxHealthDamage(player, percentDamage, 25, false);
        if (player.isSprinting() && this.random.nextInt(100) < 20) {
            player.setSprinting(false);
        }
        super.onAttackPlayer(event, player);
    }

    @Override
    public void onDamageTaken(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            event.setCancelled(true);
            if (this.getAntiFarm().checkStuck()) {
                this.getAntiFarm().unstickBoss();
            }
        }
        event.setDamage(event.getDamage() / 2.0);
        super.onDamageTaken(event);
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.POISON;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.LEGENDARY_PLUS;
    }

    @Override
    public int getMaxHealth() {
        return 1500;
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList(new BossMessage("This treasure doesnt belong to you!", false, -1));
    }
}

