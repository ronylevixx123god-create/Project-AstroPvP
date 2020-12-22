/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.World
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftCreature
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEnderman
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityTeleportEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicbosses.bosses.soulreaper;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.entities.CustomEntity;
import me.ifamasssxd.cosmicbosses.entities.SoulReaperEnderman;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.ItemBuilder;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.World;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderman;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SoulReaper
extends Boss {
    Random random = new Random();
    private long lastCloneSpawn = 0L;

    public SoulReaper(Location location, BossType bossType) {
        super(location, bossType);
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENDERMAN;
    }

    @Override
    public void onBossSpawn() {
        super.onBossSpawn();
    }

    @Override
    public LivingEntity spawnBoss() {
        CraftWorld world = (CraftWorld)this.location.getWorld();
        CustomEntity.ENDERMAN.registerEntity();
        SoulReaperEnderman ender = new SoulReaperEnderman((World)world.getHandle());
        ender.setPosition(this.location.getX(), this.location.getY(), this.location.getZ());
        world.getHandle().addEntity((Entity)ender, CreatureSpawnEvent.SpawnReason.CUSTOM);
        LivingEntity ent = (LivingEntity)ender.getBukkitEntity();
        ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
        this.setupBoss(ent);
        this.setHelmet(ItemBuilder.buildArmor(Material.DIAMOND_HELMET, Enchantment.PROTECTION_ENVIRONMENTAL));
        this.setChestplate(ItemBuilder.buildArmor(Material.DIAMOND_CHESTPLATE, Enchantment.THORNS));
        this.setLeggings(ItemBuilder.buildArmor(Material.DIAMOND_LEGGINGS, Enchantment.PROTECTION_FIRE));
        this.setBoots(ItemBuilder.buildArmor(Material.DIAMOND_BOOTS, Enchantment.PROTECTION_PROJECTILE));
        ItemStack weapon = new ItemStack(Material.DIAMOND_SWORD, 1);
        weapon.addUnsafeEnchantment(Enchantment.DURABILITY, 100);
        weapon.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
        weapon.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
        this.getEntity().getEquipment().setItemInHandDropChance(0.0f);
        this.getEntity().getEquipment().setItemInHand(weapon);
        return ent;
    }

    @Override
    public LivingEntity setupBoss(LivingEntity e) {
        LivingEntity ent = super.setupBoss(e);
        return ent;
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        super.onDeath(event);
        if (event != null) {
            int i;
            this.sendMessage((Object)ChatColor.LIGHT_PURPLE + "You can never truly defeat me, I am the reaper of souls, and I shall return.", Sound.ENDERDRAGON_DEATH, 1.3f);
            Location death = event.getEntity().getLocation();
            for (i = 0; i < 10; ++i) {
                death.getWorld().dropItemNaturally(death, EnchantmentBook.getSecretDust((ItemRarity)ItemRarity.SOUL, (int)(this.random.nextInt(70) + 10)));
            }
            for (i = 0; i < 10; ++i) {
                death.getWorld().dropItemNaturally(death, KillTrackerAPI.createSoulShard((int)(50 + this.random.nextInt(51))));
            }
        }
    }

    @Override
    public String getCustomName() {
        return (Object)ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + this.getName();
    }

    @Override
    public String getName() {
        return "Soul Reaper";
    }

    @Override
    public void cleanup() {
        this.clearMinions();
    }

    private void clearMinions() {
        for (org.bukkit.entity.Entity m : this.getMinions()) {
            m.remove();
        }
    }

    @Override
    public String getTitle() {
        return "Reaper";
    }

    public CraftEnderman getEnderman() {
        return (CraftEnderman)this.entity;
    }

    @Override
    public void onUpdate() {
        ++this.loops;
        if (this.loops % 20 == 0 && this.getMinions().size() < 3 && this.lastCloneSpawn < System.currentTimeMillis()) {
            LivingEntity ent = (LivingEntity)this.spawnMinion(EntityType.ENDERMAN, BossUtils.getRandomLocationNearby(this.entity.getLocation(), 3, 1));
            this.sendMessage((Object)ChatColor.LIGHT_PURPLE + "Rise my children of the ender, devour them!");
            if (this.random.nextBoolean()) {
                ent.setMetadata("explode_death", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
            }
            this.lastCloneSpawn = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5L);
        }
        if (this.loops % 120 == 0 || this.loops % 90 == 0) {
            this.sendMessage((Object)ChatColor.LIGHT_PURPLE + "Your end has come, behold the power of the ender.");
            for (org.bukkit.entity.Entity minion : this.getMinions()) {
                SoulReaperEnderman ender;
                if (!minion.isValid() || (ender = this.getSoulReaperEnderman(minion)) == null) continue;
                if (this.loops % 120 == 0) {
                    ender.shooting = true;
                    continue;
                }
                ender.shooting = false;
            }
        }
        if (this.loops % 4 == 0) {
            this.updateScoreboard();
        }
        super.onUpdate();
    }

    @Override
    public org.bukkit.entity.Entity spawnMinion(EntityType type, Location l) {
        CraftWorld world = (CraftWorld)l.getWorld();
        SoulReaperEnderman clone = new SoulReaperEnderman((World)world.getHandle(), true);
        clone.setPosition(l.getX(), l.getY(), l.getZ());
        world.getHandle().addEntity((Entity)clone, CreatureSpawnEvent.SpawnReason.CUSTOM);
        LivingEntity entity = (LivingEntity)clone.getBukkitEntity();
        entity.setMaxHealth(200.0);
        entity.setHealth(200.0);
        CraftEntity ent = clone.getBukkitEntity();
        ent.setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)this.getEntity().getUniqueId()));
        ent.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        this.getMinions().add((org.bukkit.entity.Entity)ent);
        if (ent instanceof LivingEntity) {
            LivingEntity li = (LivingEntity)ent;
            li.setCustomName(this.getCustomName());
            li.setCustomNameVisible(true);
            if (li.getEquipment() != null) {
                li.getEquipment().setItemInHandDropChance(0.0f);
                li.getEquipment().setHelmetDropChance(0.0f);
                li.getEquipment().setChestplateDropChance(0.0f);
                li.getEquipment().setLeggingsDropChance(0.0f);
                li.getEquipment().setBootsDropChance(0.0f);
            }
        }
        return ent;
    }

    @Override
    public void onPhaseChange(Phase newPhase) {
    }

    @Override
    public void onDamageTaken(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            event.setCancelled(true);
        }
        super.onDamageTaken(event);
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        AtomicBoolean performedAOE = new AtomicBoolean(false);
        if (this.random.nextInt(100) <= 30) {
            player.setVelocity(player.getLocation().getDirection().multiply(-1).setY(0.7));
        }
        if (this.random.nextInt(10) <= 4 && BossUtils.canAffectPlayer(player, true)) {
            BossUtils.dealPercentMaxHealthDamage(player, 5.0, 10);
        }
        AtomicInteger affected = new AtomicInteger(0);
        player.getNearbyEntities(1.0, 2.0, 1.0).stream().filter(entity -> entity != event.getEntity()).filter(entity -> entity instanceof Player && BossUtils.canAffectPlayer((Player)entity, true)).forEach(entity -> {
            if (affected.get() >= 10) {
                return;
            }
            BossUtils.dealPercentHealthDamage(player, 10.0, 5);
            ((Player)entity).damage(0.0);
            affected.incrementAndGet();
            player.setVelocity(player.getVelocity().multiply(-1).setY(1));
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.PORTAL, (Location)entity.getLocation().clone(), (float)0.5f, (float)0.5f, (float)0.5f, (float)1.0f, (int)10);
            performedAOE.set(true);
        });
        if (performedAOE.get()) {
            player.getWorld().playSound(player.getLocation(), Sound.ENDERMAN_HIT, 1.0f, 1.3f);
        }
        super.onAttackPlayer(event, player);
    }

    @Override
    public void onEntityTeleport(EntityTeleportEvent event, org.bukkit.entity.Entity boss) {
    }

    public SoulReaperEnderman getSoulReaperEnderman(org.bukkit.entity.Entity entity) {
        CraftCreature creature;
        if (entity instanceof CraftCreature && (creature = (CraftCreature)entity).getHandle() instanceof SoulReaperEnderman) {
            return (SoulReaperEnderman)creature.getHandle();
        }
        return null;
    }

    @Override
    public void onMinionUpdate(org.bukkit.entity.Entity minion, Event e) {
        super.onMinionUpdate(minion, e);
        if (e instanceof EntityDeathEvent) {
            if (minion.hasMetadata("explode_death")) {
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_EXPLODE, (Location)minion.getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.01f, (int)15);
                minion.getWorld().playSound(minion.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                for (org.bukkit.entity.Entity ent : minion.getNearbyEntities(10.0, 10.0, 10.0)) {
                    Player player;
                    if (!(ent instanceof Player) || !BossUtils.canAffectPlayer(player = (Player)ent, true)) continue;
                    BossUtils.dealPercentHealthDamage(player, 25.0, 15);
                }
            }
        } else if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
            if (event.getDamager() instanceof Projectile) {
                if (event.getEntity().getType() != EntityType.PLAYER) {
                    event.setCancelled(true);
                } else {
                    double dmg = 30.0;
                    double missingHealth = this.getEntity().getHealth() / (double)this.getMaxHealth();
                    double missing = 1.0 - missingHealth;
                    event.setDamage(dmg * Math.min(2.0, 1.0 + missing));
                }
            } else if (event.getDamager().equals((Object)minion)) {
                if (!(event.getEntity() instanceof Player)) {
                    event.setCancelled(true);
                }
            } else if (event.getEntity().hasMetadata("minion") && event.getDamager().getType() != EntityType.PLAYER) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void onMiscUpdate(Event event) {
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.POISON;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.OUTPOST;
    }

    @Override
    public int getMaxHealth() {
        return 1500;
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList((Object[])new BossMessage[]{new BossMessage((Object)ChatColor.LIGHT_PURPLE + "Your soul is mine now [NAME]!", false, 0)});
    }
}

