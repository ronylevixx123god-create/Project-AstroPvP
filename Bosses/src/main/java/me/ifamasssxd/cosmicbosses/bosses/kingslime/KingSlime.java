/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_7_R4.EntityInsentient
 *  net.minecraft.server.v1_7_R4.EntitySlime
 *  net.minecraft.server.v1_7_R4.PathfinderGoalSelector
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftSlime
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.MagmaCube
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Slime
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityTargetEvent
 *  org.bukkit.event.entity.EntityTargetLivingEntityEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicbosses.bosses.kingslime;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.kingslime.WaterTrap;
import me.ifamasssxd.cosmicbosses.struct.BlockModifier;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.ReflectionUtils;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.PathfinderGoalSelector;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSlime;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class KingSlime
extends Boss {
    WaterTrap waterTrap;
    boolean inAir = false;
    double health = 0.0;

    public KingSlime(Location location, BossType bossType) {
        super(location, bossType);
        this.modifier = new BlockModifier();
        this.waterTrap = new WaterTrap(this, this.modifier);
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
    }

    @Override
    public String getTitle() {
        return "Slimey";
    }

    @Override
    public EntityType getEntityType() {
        return this.getPhase() == Phase.TWO ? EntityType.MAGMA_CUBE : EntityType.SLIME;
    }

    @Override
    public void onBossSpawn() {
        LivingEntity ent = this.spawnBoss();
        this.getSlime().setSize(15);
        ent.setMaxHealth((double)this.getMaxHealth());
        ent.setHealth((double)this.getMaxHealth());
        this.alert();
        BossMessage msg = this.getStartingMessage();
        if (msg != null) {
            this.sendMessage(ChatColor.ITALIC.toString() + msg.getMessage());
        }
    }

    @Override
    public int getMaxHealth() {
        return 1500;
    }

    @Override
    public String getCustomName() {
        return "King Slime";
    }

    @Override
    public String getName() {
        return "King Slime";
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.ULTIMATE_PLUS;
    }

    @Override
    public void onUpdate() {
        ++this.loops;
        if (this.loops % 80 == 0) {
            this.onPhaseChange(this.getPhase() == Phase.ONE ? Phase.TWO : (this.getPhase() == Phase.TWO ? Phase.ONE : Phase.ONE));
        }
        if (this.loops % 4 == 0 && this.getAntiFarm().checkStuck()) {
            this.getAntiFarm().unstickBoss();
        }
        if (this.waterTrap.canUse() && this.random.nextBoolean() && this.loops >= 20) {
            this.waterTrap.onAbility();
            this.sendMessage((Object)ChatColor.ITALIC + "Breath it in!");
        }
        Location entityLocation = this.getEntity().getLocation();
        if (this.getPhase() == Phase.ONE && this.loops % 4 == 0 && this.getMinions().size() < 6 && this.random.nextInt(3) <= 1) {
            for (Entity ent : this.getNearbyEntities(20.0, EntityType.PLAYER)) {
                if (this.getMinions().size() >= 8) break;
                Player p = (Player)ent;
                if (!BossUtils.canAffectPlayer(p, false)) continue;
                Location loc = BossUtils.getRandomLocationNearby(p.getLocation(), 10, 3, 0);
                Slime minion = (Slime)this.spawnMinion(EntityType.SLIME, loc);
                this.applyNMS(minion);
                minion.setCustomName(this.getColor(true) + this.getName() + "'s Minion");
                minion.setCustomNameVisible(true);
                minion.setMetadata("no_slime_split", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
                minion.setSize(2);
                minion.setMaxHealth(100.0);
                minion.setHealth(minion.getMaxHealth());
            }
        }
        this.updateMinionPath();
        this.updateSize();
        if (this.loops % 16 == 0) {
            this.updateScoreboard();
        }
        if (this.getEntity().isOnGround() && this.inAir) {
            this.inAir = false;
            Location loc = entityLocation.clone();
            Block b = loc.getBlock();
            Location under = this.getModifier().containsLocation(this.waterTrap, b.getLocation()) ? b.getLocation() : b.getRelative(BlockFace.DOWN).getLocation();
            Location resuable = under.clone();
            if (this.getModifier().containsLocation(this.waterTrap, under)) {
                for (int x = under.getBlockX() - 2; x < under.getBlockX() + 2; ++x) {
                    for (int z = under.getBlockZ() - 2; z < under.getBlockZ() + 2; ++z) {
                        resuable.setX((double)x);
                        resuable.setZ((double)z);
                        if (!this.getModifier().containsLocation(this.waterTrap, resuable)) continue;
                        this.getModifier().removeBlock(this.waterTrap, resuable);
                        b.getWorld().playSound(b.getLocation(), Sound.ZOMBIE_WOODBREAK, 5.0f, 0.7f);
                    }
                }
            }
        } else {
            this.inAir = true;
        }
        super.onUpdate();
    }

    private void updateMinionPath() {
        Iterator<Entity> iter = this.getMinions().iterator();
        while (iter.hasNext()) {
            LivingEntity e = (LivingEntity)iter.next();
            if (e == null || !e.isValid()) {
                iter.remove();
                continue;
            }
            if (!(e instanceof Slime)) continue;
            if (this.getPhase() == Phase.TWO) {
                if (this.random.nextInt(10) != 0) continue;
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LARGE_SMOKE, (Location)e.getLocation(), (float)0.5f, (float)0.5f, (float)0.5f, (float)0.01f, (int)5);
                continue;
            }
            Location loc = this.getEntity().getLocation();
            double distance = e.getLocation().distanceSquared(loc);
            if (distance <= 45.0 && e.getTicksLived() >= 60) {
                int heal = (int)Math.min(50.0, e.getHealth() * 2.0);
                this.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "+ " + heal + " HP (SOURCE: Minion)");
                this.onHeal(heal);
                this.getEntity().getWorld().playSound(loc, Sound.SLIME_ATTACK, 15.0f, 0.8f);
                if (this.random.nextInt(10) == 0) {
                    ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.SLIME, (Location)e.getLocation(), (float)0.5f, (float)0.5f, (float)0.5f, (float)0.01f, (int)20);
                }
                e.remove();
                iter.remove();
                continue;
            }
            if (distance > 300.0) {
                e.teleport(BossUtils.getRandomLocationNearby(loc.clone(), 10, 5, -2));
            }
            EntitySlime insent = ((CraftSlime)e).getHandle();
            insent.getControllerLook().a(((CraftEntity)this.getEntity()).getHandle(), 30.0f, 30.0f);
            insent.getControllerJump().a();
            insent.getNavigation().a(loc.getX(), loc.getY(), loc.getZ(), 1.5);
        }
    }

    private void updateSize() {
        double health;
        int maxSize = 15;
        int currentSize = this.getSlime().getSize();
        this.health = health = this.getEntity().getHealth();
        double percentageHealth = health / this.getEntity().getMaxHealth();
        int size = (int)Math.max(5.0, Math.ceil(Double.valueOf(maxSize) * percentageHealth));
        if (size != currentSize) {
            Slime slime = this.getSlime();
            slime.setSize(size);
            slime.setMaxHealth((double)this.getMaxHealth());
            slime.setHealth(health);
            slime.getWorld().playSound(slime.getLocation(), Sound.SLIME_ATTACK, 15.0f, 0.7f);
        }
    }

    @Override
    public void onMinionUpdate(Entity minion, Event e) {
        EntityDamageEvent event;
        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event2 = (EntityDamageByEntityEvent)e;
            if (event2.getDamager().equals((Object)minion) && event2.getEntity() instanceof Player) {
                event2.setDamage(event2.getDamage() * 5.0);
            }
        } else if (e instanceof EntityTargetEvent || e instanceof EntityTargetLivingEntityEvent) {
            if (this.getPhase() == Phase.ONE) {
                EntityTargetEvent event3 = (EntityTargetEvent)e;
                event3.setTarget(null);
            }
        } else if (e instanceof EntityDeathEvent) {
            ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.EXPLODE, (Location)minion.getLocation(), (float)0.5f, (float)0.5f, (float)0.5f, (float)0.01f, (int)10);
            minion.getWorld().playSound(minion.getLocation(), Sound.EXPLODE, 4.0f, 1.0f);
            for (Entity ent : minion.getNearbyEntities(5.0, 5.0, 5.0)) {
                if (!(ent instanceof Player)) continue;
                Player pl = (Player)ent;
                BossUtils.dealPercentMaxHealthDamage(pl, 10.0, 10);
            }
        } else if (e instanceof EntityDamageEvent && ((event = (EntityDamageEvent)e).getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.FALL)) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onDamageTaken(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        if (cause == EntityDamageEvent.DamageCause.DROWNING) {
            event.setCancelled(true);
            if (this.getAntiFarm().checkStuck(this)) {
                this.getAntiFarm().unstickBoss();
            }
        }
        super.onDamageTaken(event);
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, Player player) {
        if (!event.isCancelled()) {
            if (!BossUtils.canAffectPlayer(player, false)) {
                return;
            }
            if (this.getPhase() == Phase.TWO) {
                BossUtils.dealPercentMaxHealthDamage(player, 7.0, 7);
                player.setFireTicks(20);
            } else {
                BossUtils.dealPercentHealthDamage(player, 3.0, 2, 2);
            }
        }
        super.onAttackPlayer(event, player);
    }

    @Override
    public void onDamageTaken(EntityDamageByEntityEvent event, Entity damager) {
        super.onDamageTaken(event, damager);
    }

    @Override
    public void onPhaseChange(Phase newPhase) {
        this.setPhase(newPhase);
        Slime normal = null;
        if (newPhase == Phase.ONE || newPhase == Phase.TWO) {
            this.location = this.getEntity().getLocation();
            Slime currentSlime = this.getSlime();
            Boss.bosses.remove(currentSlime.getUniqueId());
            normal = (Slime)this.spawnBoss();
            normal.setSize(currentSlime.getSize());
            normal.setMaxHealth((double)this.getMaxHealth());
            normal.setHealth(currentSlime.getHealth());
            currentSlime.remove();
            String msg = (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + this.getName() + " has entered Phase " + newPhase.getPhase();
            msg = newPhase == Phase.TWO ? "Behold the power of the Slime King! Explosive slimes, attack these cosmonaut scum!" : "Come to me my children, heal me so we can crush these Cosmonauts!";
            for (Entity minion : Lists.newArrayList(this.getMinions())) {
                Slime slime;
                MagmaCube cube;
                if (newPhase == Phase.TWO && minion instanceof Slime) {
                    cube = (MagmaCube)this.spawnMinion(EntityType.MAGMA_CUBE, minion.getLocation());
                    slime = (Slime)minion;
                    cube.setSize(slime.getSize());
                    cube.setMaxHealth(slime.getMaxHealth());
                    cube.setHealth(slime.getHealth());
                    minion.remove();
                    this.getMinions().remove((Object)minion);
                    continue;
                }
                if (newPhase != Phase.ONE || !(minion instanceof MagmaCube)) continue;
                cube = (Slime)this.spawnMinion(EntityType.SLIME, minion.getLocation());
                slime = (MagmaCube)minion;
                cube.setSize(slime.getSize());
                cube.setMaxHealth(slime.getMaxHealth());
                cube.setHealth(slime.getHealth());
                this.applyNMS((Slime)cube);
                minion.remove();
                this.getMinions().remove((Object)minion);
            }
            this.sendMessage((Object)ChatColor.ITALIC + msg, Sound.ENDERDRAGON_GROWL, 1.4f);
        }
    }

    private void applyNMS(Slime slime) {
        EntitySlime ent = ((CraftSlime)slime).getHandle();
        Object goalSelector = ReflectionUtils.getObject(EntityInsentient.class, (Object)ent, "goalSelector");
        List c = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, goalSelector, "c");
        c.clear();
        List b = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, goalSelector, "b");
        b.clear();
        Object targetSelector = ReflectionUtils.getObject(EntityInsentient.class, (Object)ent, "targetSelector");
        List targC = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, targetSelector, "c");
        targC.clear();
        List targB = (List)ReflectionUtils.getObject(PathfinderGoalSelector.class, targetSelector, "b");
        targB.clear();
    }

    @Override
    public boolean checkStuck() {
        Material type = this.getEntity().getLocation().getBlock().getType();
        if (type == Material.WATER || type == Material.STATIONARY_WATER || (type == Material.STATIONARY_LAVA || type == Material.LAVA) && this.getPhase() == Phase.ONE) {
            this.getAntiFarm().setStuckTicks(this.getAntiFarm().getStuckTicks() + 1);
            return this.getAntiFarm().isStuck();
        }
        return super.checkStuck();
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.WATER;
    }

    @Override
    public void cleanup() {
        this.removeMinions();
        this.modifier.replaceAll();
        this.modifier.removeAllBlocks(true);
        this.clearScoreboard();
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        super.onDeath(event);
        if (event != null) {
            this.sendMessage((Object)ChatColor.ITALIC + "You... have defeated me.");
        }
    }

    private Slime getSlime() {
        return (Slime)this.getEntity();
    }

    @Override
    public void onMiscUpdate(Event event) {
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList((Object[])new BossMessage[]{new BossMessage("Are you challenging my rule?", false, 0), new BossMessage("You have slayed your last slime.", false, 0), new BossMessage("Ah, so you are [NAME], the infamous slime killer. Prepare to meet your doom!", false, -1), new BossMessage("You're making me angry die already!", true, 2)});
    }
}

