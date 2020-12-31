/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.EssentialsUtils
 *  com.google.common.collect.Lists
 *  com.google.common.collect.MapMaker
 *  net.minecraft.server.v1_8_R3.AttributeInstance
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntityHorse
 *  net.minecraft.server.v1_8_R3.EntityInsentient
 *  net.minecraft.server.v1_8_R3.EntityZombie
 *  net.minecraft.server.v1_8_R3.GenericAttributes
 *  net.minecraft.server.v1_8_R3.IAttribute
 *  net.minecraft.server.v1_8_R3.World
 *  net.minecraft.server.v1_8_R3.WorldServer
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.craftbukkit.v1_8_R3.CraftWorld
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Creature
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Horse
 *  org.bukkit.entity.Horse$Variant
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.WitherSkull
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.ProjectileHitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicbosses.evilabomination;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EssentialsUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.GuardBoss;
import me.ifamasssxd.cosmicbosses.evilabomination.EvilAbominationEntity;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.CustomItem;
import me.ifamasssxd.cosmicbosses.utils.ItemBuilder;
import me.ifamasssxd.cosmicbosses.utils.ReflectionUtils;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IAttribute;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EvilAbomination
extends Boss
implements GuardBoss {
    private WeakReference<org.bukkit.entity.Entity> horseEntity;
    private int outsideTicks = 0;
    private Set<UUID> lastDamaged = Collections.newSetFromMap(new MapMaker().expireAfterWrite(400L, TimeUnit.MILLISECONDS).makeMap());

    public EvilAbomination(Location location, BossType bossType) {
        super(location, bossType);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public String getCustomName() {
        return CC.DarkGreenB + "Evil Abomination";
    }

    @Override
    public BossType getBossType() {
        return BossType.EVIL_ABOMINATION;
    }

    @Override
    public String getName() {
        return "Evil Abomination";
    }

    @Override
    public LivingEntity spawnBoss() {
        WorldServer world = ((CraftWorld)this.location.getWorld()).getHandle();
        EvilAbominationEntity entity = new EvilAbominationEntity(this, (World)world);
        entity.setPosition(this.location.getX(), this.location.getY(), this.location.getZ());
        world.addEntity((Entity)entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        LivingEntity e = (LivingEntity)entity.getBukkitEntity();
        this.uuid = e.getUniqueId();
        e.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        e.setMetadata("boss", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        e.setMaxHealth((double)this.getMaxHealth());
        e.setHealth(e.getMaxHealth());
        this.setEnt(e);
        e.setCustomName((Object)this.getDifficulty().getColor() + ChatColor.BOLD.toString() + this.getCustomName());
        e.setCustomNameVisible(true);
        e.setRemoveWhenFarAway(false);
        this.entity = e;
        bosses.put(e.getUniqueId(), this);
        BossMessage msg = this.getStartingMessage();
        if (msg != null) {
            this.sendMessage(ChatColor.ITALIC.toString() + msg.getMessage());
        }
        return e;
    }

    @Override
    public void onBossSpawn() {
        LivingEntity skeleton = this.spawnBoss();
        skeleton.getEquipment().setItemInHand(ItemBuilder.buildArmor(Material.BOW, Enchantment.ARROW_DAMAGE, Enchantment.ARROW_FIRE));
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        this.setHelmet(new CustomItem(new ItemStack(Material.DIAMOND_HELMET)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        this.setChestplate(new CustomItem(new ItemStack(Material.DIAMOND_CHESTPLATE)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.setLeggings(new CustomItem(new ItemStack(Material.DIAMOND_LEGGINGS)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).addEnchant(Enchantment.THORNS, 1).build());
        this.setBoots(new CustomItem(new ItemStack(Material.DIAMOND_BOOTS)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        ((CraftLivingEntity)skeleton).getHandle().getAttributeInstance(GenericAttributes.d).setValue((double)0.43f);
        Horse horse = (Horse)this.spawnMinion(EntityType.HORSE, skeleton.getLocation());
        this.horseEntity = new WeakReference<>(horse);
        EntityHorse entHorse = ((CraftHorse)horse).getHandle();
        entHorse.getAttributeInstance(GenericAttributes.d).setValue(entHorse.getAttributeInstance(GenericAttributes.d).getValue() * (double)1.19f);
        horse.setMetadata("horse", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        horse.setMaxHealth((double)this.getMaxHealth());
        horse.setCustomName(CC.DarkGreenB + "Abomination");
        horse.setCustomNameVisible(true);
        horse.setHealth(horse.getMaxHealth());
        horse.setAdult();
        horse.setVariant(Horse.Variant.UNDEAD_HORSE);
        horse.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        horse.setDomestication(horse.getMaxDomestication());
        horse.setPassenger((org.bukkit.entity.Entity)skeleton);
        this.alert();
    }

    @Override
    public void cleanup() {
        org.bukkit.entity.Entity horse = (org.bukkit.entity.Entity)this.horseEntity.get();
        if (horse != null) {
            horse.remove();
            this.horseEntity.clear();
        }
        this.removeMinions();
        this.clearScoreboard();
    }

    @Override
    public void onUpdate() {
        if (this.loops % 12 == 0) {
            if (this.getAntiFarm().checkStuck()) {
                this.getAntiFarm().unstickBoss();
            }
            if (!this.entity.hasPotionEffect(PotionEffectType.SPEED)) {
                this.entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
            }
        }
        Iterator<org.bukkit.entity.Entity> ent = this.getMinions().iterator();
        while (ent.hasNext()) {
            org.bukkit.entity.Entity e = ent.next();
            if (e == null || !e.isValid()) {
                ent.remove();
                continue;
            }
            if (!(e.getLocation().distanceSquared(this.getEntity().getLocation()) >= 1000.0)) continue;
            e.teleport(this.getEntity().getLocation().clone());
        }
        Horse horse = (Horse)this.horseEntity.get();
        if (horse != null && horse.isValid()) {
            if (this.getEntity().getHealth() < this.getEntity().getMaxHealth() * 0.75) {
                horse.damage(2.147483647E9);
            } else {
                Creature boss;
                if (horse.getPassenger() == null) {
                    horse.setPassenger((org.bukkit.entity.Entity)this.getEntity());
                }
                if ((boss = (Creature)this.getEntity()).getTarget() != null && boss.getTarget().getWorld() == boss.getWorld()) {
                    double distanceSquared = horse.getLocation().distanceSquared(boss.getTarget().getLocation());
                    if (distanceSquared > 100.0) {
                        EntityHorse insent = ((CraftHorse)horse).getHandle();
                        Location targetLoc = boss.getTarget().getLocation();
                        insent.getNavigation().a(targetLoc.getX(), targetLoc.getY(), targetLoc.getZ(), 1.8);
                    } else if (distanceSquared < 5.0) {
                        EntityHorse insent = ((CraftHorse)horse).getHandle();
                        Location targetLoc = BossUtils.getRandomLocationNearby(boss.getTarget().getLocation(), 7, 4, 0);
                        insent.getNavigation().a(targetLoc.getX(), targetLoc.getY(), targetLoc.getZ(), 1.65);
                    }
                }
            }
        }
        if (horse != null && horse.isDead() && this.getPhase() != Phase.TWO) {
            this.onPhaseChange(Phase.TWO);
            this.horseEntity.clear();
        }
        this.checkGuardLocation(this, this.location);
        this.outsideTicks = this.checkOutsideTicks(this, this.outsideTicks, this.location);
        if (this.loops % 4 == 0) {
            this.updateScoreboard();
        }
        super.onUpdate();
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        super.onAttackPlayer(event, player);
        if (event.getDamager() instanceof Projectile) {
            event.setDamage(event.getDamage() * 3.0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 3), true);
            if (this.random.nextDouble() <= 0.25) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0), false);
            }
            BossUtils.pushAwayPlayer(event.getDamager(), player, (double)1.27f);
        }
    }

    @Override
    public void onDamageTaken(EntityDamageByEntityEvent event, org.bukkit.entity.Entity damager) {
        if (damager instanceof Projectile) {
            event.setCancelled(true);
            return;
        }
        if (event.getEntity() instanceof Horse) {
            return;
        }
        super.onDamageTaken(event, damager);
    }

    @Override
    public void onDamageTaken(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            event.setCancelled(true);
            if (this.getAntiFarm().checkStuck()) {
                this.getAntiFarm().unstickBoss();
            }
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.POISON) {
            event.setCancelled(true);
        }
        super.onDamageTaken(event);
    }

    @Override
    public void onMinionUpdate(org.bukkit.entity.Entity minion, Event e) {
        EntityDamageEvent event;
        if (minion.hasMetadata("horse")) {
            if (e instanceof EntityDeathEvent) {
                this.getEntity().leaveVehicle();
                this.getMinions().remove((Object)minion);
                this.onPhaseChange(Phase.TWO);
            } else if (e instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent event2 = (EntityDamageByEntityEvent)e;
                if (event2.getDamager() instanceof Player) {
                    this.onDamageTaken(event2, event2.getDamager());
                }
            } else if (e instanceof EntityDamageEvent) {
                EntityDamageEvent event3 = (EntityDamageEvent)e;
                this.onDamageTaken(event3);
            }
        } else if (e instanceof EntityDeathEvent) {
            this.getMinions().remove((Object)minion);
        } else if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event4 = (EntityDamageByEntityEvent)e;
            boolean isMinion = this.getMinions().contains((Object)event4.getEntity());
            if (isMinion && event4.getDamager() instanceof Projectile && !(((Projectile)event4.getDamager()).getShooter() instanceof Player)) {
                event4.setCancelled(true);
            } else if (event4.getEntity() instanceof Player && this.getMinions().contains((Object)event4.getDamager()) && !event4.isCancelled()) {
                Player damaged = (Player)event4.getEntity();
                if (this.lastDamaged.contains(damaged.getUniqueId()) || EssentialsUtils.isInGodMode((Player)damaged)) {
                    return;
                }
                double damage = 4.0;
                if (damaged.getHealth() < damage && !damaged.isDead()) {
                    damaged.setHealth(0.0);
                } else if (damaged.getHealth() > damage) {
                    damaged.setHealth(damaged.getHealth() - damage);
                }
                this.lastDamaged.add(damaged.getUniqueId());
                event4.setDamage(0.1);
            }
        } else if (e instanceof EntityDamageEvent && ((event = (EntityDamageEvent)e).getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA || event.getCause() == EntityDamageEvent.DamageCause.WITHER)) {
            event.setCancelled(true);
            event.getEntity().setFireTicks(0);
        }
    }

    @Override
    public void onPhaseChange(Phase newPhase) {
        if (newPhase == Phase.TWO) {
            this.setPhase(newPhase);
            this.getEntity().removeMetadata("empower_arrow", (Plugin)CosmicBosses.getInstance());
            this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
            Location loc = this.getEntity().getLocation();
            loc.getWorld().playSound(loc, Sound.WITHER_SPAWN, 10.0f, 1.3f);
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicBosses.getInstance(), () -> {
                loc.getWorld().playSound(loc, Sound.WITHER_SPAWN, 10.0f, 1.8f);
                loc.getWorld().playSound(loc, Sound.ZOMBIE_INFECT, 5.0f, 1.8f);
            }, 5L);
            this.sendMessage((Object)ChatColor.ITALIC + "You killed something that was already dead. Am I supposed to be impressed? Feel the power of the ROT!", Sound.GHAST_MOAN, 1.3f);
        }
    }

    @Override
    public void teleportToChest(Boss boss, Location chestLocation) {
        Location spawn = chestLocation.clone().add(0.0, 1.0, 0.0);
        org.bukkit.entity.Entity vehicle = this.getEntity().getVehicle();
        if (vehicle != null && vehicle.isValid()) {
            vehicle.eject();
            vehicle.teleport(spawn);
        }
        this.getEntity().teleport(spawn);
        if (vehicle != null && vehicle.isValid()) {
            vehicle.setPassenger((org.bukkit.entity.Entity)this.getEntity());
        }
    }

    @Override
    public int getDistance() {
        return 225;
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        org.bukkit.entity.Entity horse = (org.bukkit.entity.Entity)this.horseEntity.get();
        if (horse != null && horse.isValid()) {
            horse.remove();
            this.horseEntity.clear();
        }
        super.onDeath(event);
        if (event != null) {
            this.sendMessage((Object)ChatColor.ITALIC + "Arghh... You may have defeated me, but you cannot defeat The Plague. This world is doomed.");
        }
    }

    @Override
    public void onMiscUpdate(Event event) {
        ProjectileHitEvent e;
        if (event instanceof ProjectileHitEvent && (e = (ProjectileHitEvent)event).getEntity() instanceof WitherSkull && ((WitherSkull)e.getEntity()).isCharged()) {
            Location landed = e.getEntity().getLocation();
            Block block = landed.getBlock();
            if (block.getType().isSolid() && (block = block.getRelative(BlockFace.UP)).getType().isSolid()) {
                block = block.getRelative(BlockFace.UP);
            }
            this.createMinion(block.getLocation());
            landed.getWorld().playSound(landed, Sound.ZOMBIE_IDLE, 1.1f, 1.1f);
        }
    }

    private LivingEntity createMinion(Location spawn) {
        LivingEntity entity = (LivingEntity)spawn.getWorld().spawnEntity(spawn, EntityType.ZOMBIE);
        entity.setMaxHealth(15.0);
        entity.setHealth(entity.getMaxHealth());
        entity.getEquipment().setItemInHand(new CustomItem(new ItemStack(Material.DIAMOND_SWORD)).addEnchant(Enchantment.DURABILITY, 5).addEnchant(Enchantment.FIRE_ASPECT, 1).build());
        entity.getEquipment().setItemInHandDropChance(0.0f);
        entity.getEquipment().setChestplate(new CustomItem(new ItemStack(Material.IRON_CHESTPLATE)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchant(Enchantment.THORNS, 5).build());
        entity.getEquipment().setChestplateDropChance(0.0f);
        if (this.random.nextBoolean()) {
            entity.getEquipment().setLeggings(new CustomItem(new ItemStack(Material.IRON_LEGGINGS)).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).build());
            entity.getEquipment().setLeggingsDropChance(0.0f);
        }
        entity.setCustomName(CC.DarkGreen + "Plagued Minion");
        entity.setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)this.getEntity().getUniqueId()));
        entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 4), true);
        EntityInsentient ent = (EntityInsentient)((CraftLivingEntity)entity).getHandle();
        ent.getAttributeInstance(GenericAttributes.d).setValue((double)0.42f);
        AttributeInstance instance = ent.getAttributeInstance((IAttribute)ReflectionUtils.getObject(EntityZombie.class, null, "bp"));
        if (instance != null) {
            instance.setValue(0.0);
        }
        this.getMinions().add((org.bukkit.entity.Entity)entity);
        return entity;
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.POISON;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.HEROIC;
    }

    @Override
    public int getMaxHealth() {
        return 1500;
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList((Object[])new BossMessage[]{new BossMessage("Disease. Rot. Death. These are the pillars I exist by, and these are the pillars you will die animTime.", false, -1), new BossMessage("Feel the decay of the plague consume you!", false, 0), new BossMessage("You cannot escape my blight, this place was doomed long before you set foot here!", false, 0)});
    }
}

