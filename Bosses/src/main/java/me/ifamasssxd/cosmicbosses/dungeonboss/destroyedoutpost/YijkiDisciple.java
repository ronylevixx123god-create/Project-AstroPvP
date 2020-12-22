/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_8_R3.Entity
 *  net.minecraft.server.v1_8_R3.EntitySkeleton
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftSkeleton
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.Creature
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package me.ifamasssxd.cosmicbosses.dungeonboss.destroyedoutpost;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.EmpoweredArrow;
import me.ifamasssxd.cosmicbosses.bosses.skeletonhorse.Summoning;
import me.ifamasssxd.cosmicbosses.entities.CustomEntity;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.MiniBoss;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.CustomItem;
import me.ifamasssxd.cosmicbosses.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSkeleton;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class YijkiDisciple
extends Boss
implements MiniBoss {
    EmpoweredArrow empoweredArrow = new EmpoweredArrow(this);
    Summoning summoning = new Summoning(this);
    public boolean invincible = false;
    int hits = 0;
    long lastHit = System.currentTimeMillis();
    long timeSinceInvincible = System.currentTimeMillis();
    org.bukkit.entity.Entity lastTarget;
    int storedHealth = this.getMaxHealth();

    public YijkiDisciple(Location location, BossType bossType) {
        super(location, bossType);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SKELETON;
    }

    @Override
    public LivingEntity spawnBoss() {
        LivingEntity e = (LivingEntity)BossUtils.createCustomEntity(CustomEntity.DUNGEON_DISCIPEL, this.location);
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
        skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        this.setHelmet(new CustomItem(new ItemStack(Material.IRON_HELMET)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        this.setChestplate(new CustomItem(new ItemStack(Material.IRON_CHESTPLATE)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.setLeggings(new CustomItem(new ItemStack(Material.IRON_LEGGINGS)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).addEnchant(Enchantment.THORNS, 1).build());
        this.setBoots(new CustomItem(new ItemStack(Material.IRON_BOOTS)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        this.onPhaseChange(Phase.TWO);
        this.alert();
    }

    @Override
    public String getCustomName() {
        return "Disciple of Yijki";
    }

    @Override
    public String getName() {
        return "Disciple of Yijki";
    }

    @Override
    public void onUpdate() {
        Creature boss;
        ++this.loops;
        LivingEntity entity = this.getEntity();
        if (this.invincible && this.random.nextInt(10) <= 2) {
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LAVA, (Location)entity.getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.05f, (int)50);
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.PORTAL, (Location)entity.getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.1f, (int)30);
        }
        if (this.loops % 2 == 0 && (boss = (Creature)this.getEntity()) != null && boss.getTarget() != null && boss.getTarget() instanceof Player && boss.getTarget().getLocation().getWorld().equals((Object)boss.getWorld()) && boss.getTarget().getLocation().distanceSquared(boss.getLocation()) >= 100.0 && boss.getTarget().getWorld().getName().equals(boss.getWorld().getName())) {
            boss.teleport(boss.getTarget().getLocation());
        }
        if (this.loops % 12 == 0) {
            if (!this.invincible && this.getAntiFarm().checkStuck()) {
                this.getAntiFarm().unstickBoss();
            }
            if (!entity.hasPotionEffect(PotionEffectType.SPEED)) {
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
            }
        }
        if (this.loops % 4 == 0 && this.invincible && entity.getHealth() < (double)this.storedHealth) {
            entity.setHealth((double)this.storedHealth);
        }
        if (!this.invincible && this.getPhase() == Phase.TWO && this.summoning.canUse() && (this.hits >= 10 || (double)(System.currentTimeMillis() - this.timeSinceInvincible) >= (double)this.summoning.getCooldown() * 0.45)) {
            this.hits = 0;
            this.timeSinceInvincible = System.currentTimeMillis();
            this.storedHealth = (int)entity.getHealth();
            this.summoning.onAbility();
        }
        if (this.getPhase() == Phase.ONE && this.empoweredArrow.canUse()) {
            this.empoweredArrow.onAbility();
        }
        Iterator<org.bukkit.entity.Entity> ent = this.getMinions().iterator();
        while (ent.hasNext()) {
            org.bukkit.entity.Entity e = ent.next();
            if (e == null || !e.isValid()) {
                ent.remove();
                continue;
            }
            if (!(e.getLocation().distanceSquared(this.getEntity().getLocation()) >= 1200.0)) continue;
            e.teleport(this.getEntity().getLocation().clone().add(0.0, 15.0, 0.0));
        }
        if (this.invincible && this.getMinions().size() <= 0) {
            this.removeInvincibility();
        }
        if (this.loops % 16 == 0) {
            this.updateScoreboard();
        }
        if (!this.invincible && this.loops % 2 * 20 == 0) {
            boss = (Creature)this.getEntity();
            if (!boss.isInsideVehicle() && boss.getTarget() != null && boss.getTarget().isValid() && boss.getTarget() instanceof Player && boss.getTarget().getWorld().equals((Object)this.getEntity().getWorld())) {
                this.lastTarget = boss.getTarget();
                double distance = this.lastTarget.getLocation().distanceSquared(this.getEntity().getLocation());
                if (this.lastTarget.isValid() && distance <= 200.0 && distance > 60.0) {
                    EntitySkeleton insent = ((CraftSkeleton)this.getEntity()).getHandle();
                    Entity ent2 = ((CraftEntity)this.lastTarget).getHandle();
                    insent.getNavigation().a(ent2, 2.0);
                }
            } else if (this.lastTarget != null) {
                double distance = this.getLocation().distanceSquared(this.getEntity().getLocation());
                if (this.lastTarget.getWorld().equals((Object)this.getEntity().getWorld()) && !this.lastTarget.isDead() && this.getDamageTracker().containsKey(this.lastTarget.getUniqueId()) && distance <= 200.0) {
                    boss.setTarget((LivingEntity)this.lastTarget);
                }
            }
        }
        super.onUpdate();
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        if (this.getPhase() == Phase.TWO) {
            event.setDamage(event.getDamage() * 3.0);
            player.setFireTicks(600);
            if (BossUtils.canAffectPlayer(player, true)) {
                BossUtils.dealPercentMaxHealthDamage(player, 10.0, 10);
            }
        }
        if (event.getDamager().hasMetadata("boss_empowered_arrow")) {
            org.bukkit.entity.Entity arrow = event.getDamager();
            event.getDamager().removeMetadata("boss_empowered_arrow", (Plugin)CosmicBosses.getInstance());
            arrow.getWorld().playSound(arrow.getLocation(), Sound.EXPLODE, 10.0f, 1.1f);
            if (arrow.getPassenger() != null) {
                arrow.getPassenger().remove();
            }
            player.setFireTicks(200);
            if (BossUtils.canAffectPlayer(player, true)) {
                BossUtils.dealPercentMaxHealthDamage(player, 40.0, 10);
            }
            if (this.random.nextInt(10) == 0) {
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_SMOKE, (Location)arrow.getLocation(), (float)3.0f, (float)3.0f, (float)3.0f, (float)0.1f, (int)50);
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.FLAME, (Location)arrow.getLocation(), (float)3.0f, (float)3.0f, (float)3.0f, (float)0.1f, (int)15);
            }
            for (org.bukkit.entity.Entity ent : player.getNearbyEntities(4.0, 4.0, 4.0)) {
                Player pl;
                if (!(ent instanceof Player) || !BossUtils.canAffectPlayer(pl = (Player)ent, true)) continue;
                BossUtils.dealPercentHealthDamage(pl, 10.0, 10);
                pl.setFireTicks(100);
            }
        } else if (event.getDamager() instanceof Arrow) {
            event.setDamage(event.getDamage() * 5.0);
        }
        super.onAttackPlayer(event, player);
    }

    @Override
    public void onDamageTaken(EntityDamageByEntityEvent event, org.bukkit.entity.Entity damager) {
        if (event.getDamager() instanceof Projectile) {
            event.setCancelled(true);
            return;
        }
        if (this.invincible) {
            event.setCancelled(true);
            if (damager instanceof Player) {
                Player pl = (Player)damager;
                BossUtils.pushAwayPlayer((org.bukkit.entity.Entity)this.getEntity(), pl, (double)2.3f);
            }
            this.getEntity().getWorld().playSound(this.getEntity().getLocation(), Sound.WITHER_IDLE, 3.0f, 1.8f);
            if (this.random.nextInt(10) <= 4) {
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.EXPLODE, (Location)this.getEntity().getLocation(), (float)0.5f, (float)0.3f, (float)0.3f, (float)0.1f, (int)30);
            }
            return;
        }
        this.hits = System.currentTimeMillis() - this.lastHit <= 500L ? ++this.hits : 1;
        this.lastHit = System.currentTimeMillis();
        if (damager instanceof Player && event.getDamager() instanceof Player) {
            Player pl = (Player)damager;
            if (this.random.nextInt(100) <= 10) {
                if (BossUtils.canAffectPlayer(pl, true)) {
                    pl.setFireTicks(30);
                    BossUtils.pushAwayPlayer((org.bukkit.entity.Entity)this.getEntity(), pl, 10.0);
                    if (this.random.nextInt(3) == 0) {
                        pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 3));
                    }
                    this.getEntity().getWorld().playSound(this.getEntity().getLocation(), Sound.WITHER_SHOOT, 4.0f, 0.8f);
                }
            } else if (this.getPhase() == Phase.TWO && this.random.nextInt(10) <= 4) {
                this.getEntity().teleport(BossUtils.getRandomLocationNearby(damager.getLocation(), 2, 1, 1));
                this.getEntity().getWorld().playSound(this.getEntity().getLocation(), Sound.ZOMBIE_DEATH, 10.0f, 1.4f);
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.PORTAL, (Location)this.getEntity().getLocation(), (float)1.0f, (float)1.0f, (float)1.0f, (float)0.7f, (int)20);
            }
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
        if (this.invincible) {
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
            if (this.invincible && this.getMinions().size() == 0) {
                this.removeInvincibility();
            }
        } else if (e instanceof EntityDamageEvent && ((event = (EntityDamageEvent)e).getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA)) {
            event.setCancelled(true);
            event.getEntity().setFireTicks(0);
        }
    }

    @Override
    public void removeMinion(org.bukkit.entity.Entity ent) {
    }

    public void removeInvincibility() {
        this.invincible = false;
        this.timeSinceInvincible = System.currentTimeMillis();
        this.summoning.activateCooldown();
        this.getEntity().removePotionEffect(PotionEffectType.INVISIBILITY);
    }

    @Override
    public void onPhaseChange(Phase newPhase) {
        this.setPhase(newPhase);
        if (newPhase == Phase.TWO) {
            this.getEntity().removeMetadata("empower_arrow", (Plugin)CosmicBosses.getInstance());
            this.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
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
        }
    }

    @Override
    public void cleanup() {
        this.removeMinions();
        this.clearScoreboard();
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        super.onDeath(event);
        if (event != null) {
            this.sendMessage((Object)ChatColor.ITALIC + "You should not have come here... you will never leave alive!");
        }
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
        return 1000;
    }

    @Override
    public void onMiscUpdate(Event event) {
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList((Object[])new BossMessage[]{new BossMessage("Die, heretic.", false, -1)});
    }
}

