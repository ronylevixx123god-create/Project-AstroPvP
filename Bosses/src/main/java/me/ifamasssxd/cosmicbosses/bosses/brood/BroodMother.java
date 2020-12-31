/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.google.common.collect.Lists
 *  net.minecraft.server.v1_7_R4.Entity
 *  net.minecraft.server.v1_7_R4.EntitySpider
 *  net.minecraft.server.v1_7_R4.MathHelper
 *  net.minecraft.server.v1_7_R4.World
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftSpider
 *  org.bukkit.entity.Creature
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.Spider
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.util.Vector
 */
package me.ifamasssxd.cosmicbosses.bosses.brood;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.brood.SpiderSpawn;
import me.ifamasssxd.cosmicbosses.bosses.brood.WebAttack;
import me.ifamasssxd.cosmicbosses.entities.SpiderBoss;
import me.ifamasssxd.cosmicbosses.struct.BlockModifier;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossMessage;
import me.ifamasssxd.cosmicbosses.struct.Phase;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.struct.enums.DamageType;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntitySpider;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSpider;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Spider;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BroodMother
extends Boss {
    WebAttack attack;
    SpiderSpawn spawn = new SpiderSpawn(this);
    int walkFails = 0;

    public BroodMother(Location location, BossType bossType) {
        super(location, bossType);
        this.modifier = new BlockModifier();
        this.attack = new WebAttack(this, this.modifier);
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE));
    }

    @Override
    public String getTitle() {
        return "Brood";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SPIDER;
    }

    @Override
    public void onBossSpawn() {
        super.onBossSpawn();
        BossMessage start = this.getStartingMessage();
        if (start != null) {
            this.sendMessage((Object)ChatColor.ITALIC + start.getMessage());
        }
    }

    @Override
    public LivingEntity spawnBoss() {
        CraftWorld world = (CraftWorld)this.location.getWorld();
        SpiderBoss boss = new SpiderBoss((World) world.getHandle());
        boss.setPosition(this.location.getX(), this.location.getY(), this.location.getZ());
        world.getHandle().addEntity((Entity)boss, CreatureSpawnEvent.SpawnReason.CUSTOM);
        LivingEntity e = (LivingEntity)boss.getBukkitEntity();
        this.uuid = e.getUniqueId();
        e.setMetadata("do_not_clear", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        e.setMetadata("boss", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        e.setMaxHealth((double)this.getMaxHealth());
        e.setHealth(e.getMaxHealth());
        this.setEnt(e);
        e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));
        e.setCustomName((Object)this.getDifficulty().getColor() + ChatColor.BOLD.toString() + this.getCustomName());
        e.setCustomNameVisible(true);
        this.entity = e;
        bosses.put(e.getUniqueId(), this);
        return e;
    }

    @Override
    public String getCustomName() {
        return "Brood Mother";
    }

    @Override
    public String getName() {
        return "Brood Mother";
    }

    @Override
    public void onUpdate() {
        ++this.loops;
        if (this.attack.canUse() && this.random.nextBoolean() && this.loops >= 20) {
            this.attack.onAbility();
        } else if (this.spawn.canUse() && this.random.nextInt(10) <= 7) {
            this.spawn.onAbility();
        }
        if (this.getAntiFarm().checkStuck()) {
            this.getAntiFarm().unstickBoss();
        }
        if (this.loops % 16 == 0) {
            this.updateScoreboard();
        }
        Iterator<org.bukkit.entity.Entity> ent = this.getMinions().iterator();
        while (ent.hasNext()) {
            org.bukkit.entity.Entity e = ent.next();
            if (e == null || !e.isValid()) {
                ent.remove();
                continue;
            }
            if (e.getTicksLived() >= 140 && this.getMinions().size() > 7 && this.random.nextInt(100) < 25) {
                e.setVelocity(e.getVelocity().add(new Vector(0, 5, 0)));
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicBosses.getInstance(), () -> {
                    if (e != null && e.isValid()) {
                        e.remove();
                    }
                }, 1L);
                continue;
            }
            if (e.getLocation().distanceSquared(this.getEntity().getLocation()) >= 750.0) {
                e.teleport(this.getEntity().getLocation().clone());
            }
            if (this.loops % 10 != 0) continue;
            this.spiderJump(e, 5.0f);
        }
        super.onUpdate();
    }

    @Override
    public void cleanup() {
        this.getModifier().removeAllBlocks(true);
        this.removeMinions();
        this.clearScoreboard();
    }

    @Override
    public void onAttackPlayer(EntityDamageByEntityEvent event, final Player player) {
        if (event.isCancelled()) {
            return;
        }
        boolean canAffect = BossUtils.canAffectPlayer(player, false);
        if (this.random.nextInt(10) <= 2) {
            float speed;
            this.getEntity().getWorld().playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0f, 0.9f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 5), true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 1), true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1), true);
            player.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "* BITTEN *");
            this.sendMessage(player, "You have been poisoned!");
            if (canAffect) {
                BossUtils.dealPercentMaxHealthDamage(player, 10.0, 10);
            }
            if ((speed = player.getWalkSpeed() - 0.05f) <= 0.0f) {
                speed = 0.02f;
            }
            player.setWalkSpeed(speed);
            new BukkitRunnable(){

                public void run() {
                    float speed = player.getWalkSpeed() + 0.02f;
                    if ((double)speed > 0.2) {
                        speed = 0.2f;
                    }
                    player.setWalkSpeed(speed);
                }
            }.runTaskLater((Plugin)CosmicBosses.getInstance(), 60L);
        }
        if (canAffect) {
            BossUtils.dealPercentHealthDamage(player, 5.0, 4, 5);
        }
        super.onAttackPlayer(event, player);
    }

    @Override
    public void onMinionUpdate(org.bukkit.entity.Entity minion, Event e) {
        EntityDamageEvent event;
        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent event2 = (EntityDamageByEntityEvent)e;
            if (event2.getEntity().hasMetadata("minion")) {
                Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicBosses.getInstance(), () -> minion.setVelocity(new Vector()));
            }
            if (event2.getEntity() instanceof Player) {
                Player pl = (Player)event2.getEntity();
                if (!event2.isCancelled() && event2.getFinalDamage() < pl.getHealth()) {
                    pl.playSound(pl.getLocation(), Sound.ZOMBIE_METAL, 1.0f, 1.4f);
                    BossUtils.dealPercentMaxHealthDamage(pl, 5.0, 8);
                    pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 4));
                }
            } else if (event2.getEntity().hasMetadata("minion") && event2.getDamager() instanceof Projectile) {
                event2.setCancelled(true);
                minion.getWorld().playSound(minion.getLocation(), Sound.ITEM_BREAK, 4.0f, 1.4f);
            }
        } else if (e instanceof EntityDamageEvent && ((event = (EntityDamageEvent)e).getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA)) {
            event.setCancelled(true);
        }
    }

    private void spiderJump(org.bukkit.entity.Entity ent, float strength) {
        Spider spider = (Spider)ent;
        LivingEntity target = spider.getTarget();
        Location spiderLoc = spider.getLocation();
        if (target != null && strength > 2.0f && strength < 6.0f && this.random.nextInt(10) == 0 && spider.isOnGround()) {
            Location targetLoc = target.getLocation();
            double distX = targetLoc.getX() - spiderLoc.getX();
            double distZ = targetLoc.getZ() - spiderLoc.getZ();
            float distanceSquared = MathHelper.sqrt((double)(distX * distX + distZ * distZ));
            Vector velocity = spider.getVelocity();
            double motX = distX / (double)distanceSquared * 0.5 * (double)0.8f + velocity.getX() * (double)0.2f;
            double motZ = distZ / (double)distanceSquared * 0.5 * (double)0.8f + velocity.getZ() * (double)0.2f;
            double motY = 0.4f;
            velocity.setX(motX);
            velocity.setZ(motZ);
            velocity.setY(motY);
            spider.setVelocity(velocity);
            System.out.println("Leaping");
        }
    }

    @Override
    public void onDamageTaken(EntityDamageByEntityEvent event, org.bukkit.entity.Entity damager) {
        if (!event.isCancelled()) {
            Creature ent = (Creature)this.getEntity();
            event.setDamage(event.getDamage() / 2.0);
            if (damager instanceof Player) {
                double distance;
                Player pl = (Player)damager;
                double d = distance = ent.getTarget() != null ? ent.getTarget().getLocation().distanceSquared(ent.getLocation()) : -1.0;
                if (ent.getTarget() == null || distance > 100.0) {
                    ent.setTarget((LivingEntity)pl);
                    EntitySpider spider = ((CraftSpider)this.getEntity()).getHandle();
                    boolean walked = spider.getNavigation().a(pl.getLocation().getX(), pl.getLocation().getY(), pl.getLocation().getZ(), 1.4);
                    this.walkFails = walked ? 0 : ++this.walkFails;
                    if (this.walkFails >= 5) {
                        Location loc = BossUtils.getRandomLocationNearby(pl.getLocation(), 5, 3);
                        ent.teleport(loc);
                        this.sendMessage((Object)ChatColor.RED + "Get over here!");
                    }
                }
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
        super.onDamageTaken(event);
    }

    @Override
    public void onPhaseChange(Phase newPhase) {
    }

    @Override
    public void onDeath(EntityDeathEvent event) {
        super.onDeath(event);
        if (event != null) {
            this.sendMessage((Object)ChatColor.ITALIC + "HOW IS THIS POSSIBLE? The Brood Mother, ME, defeated by infidels such as yourselves?!", Sound.SPIDER_DEATH, 0.7f);
            if (event.getEntity() != null) {
                for (org.bukkit.entity.Entity near : event.getEntity().getNearbyEntities(20.0, 20.0, 20.0)) {
                    Player player;
                    if (!(near instanceof Player) || !(player = (Player)near).hasPotionEffect(PotionEffectType.BLINDNESS)) continue;
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                }
            }
        }
    }

    @Override
    public DamageType getDamageType() {
        return DamageType.POISON;
    }

    @Override
    public Difficulty getDifficulty() {
        return Difficulty.ULTIMATE;
    }

    @Override
    public int getMaxHealth() {
        return 2000;
    }

    @Override
    public void onMiscUpdate(Event event) {
    }

    @Override
    public List<BossMessage> getGeneralMessages() {
        return Lists.newArrayList(new BossMessage("Have you ever wondered why you're afraid of spiders?", false, -1), new BossMessage("One bite, and you'll be mine.", false, 0), new BossMessage("My babies.. they hunger for Cosmonauts!", true, 0));
    }
}

