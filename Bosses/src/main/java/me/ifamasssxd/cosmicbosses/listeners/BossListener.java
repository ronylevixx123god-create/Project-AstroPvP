/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.entity.TNTPrimed
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.CreatureSpawnEvent
 *  org.bukkit.event.entity.CreatureSpawnEvent$SpawnReason
 *  org.bukkit.event.entity.EntityChangeBlockEvent
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent
 *  org.bukkit.event.entity.EntityRegainHealthEvent$RegainReason
 *  org.bukkit.event.entity.EntityTargetEvent
 *  org.bukkit.event.entity.EntityTeleportEvent
 *  org.bukkit.event.world.ChunkLoadEvent
 *  org.bukkit.event.world.ChunkUnloadEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicbosses.listeners;

import me.ifamasssxd.cosmicbosses.boss.rarity.BossRarity;
import me.ifamasssxd.cosmicbosses.boss.type.BlazeBoss;
import me.ifamasssxd.cosmicbosses.utils.BossEggUtil;
import me.ifamasssxd.cosmicbosses.utils.MiscUtil;
import me.ifamasssxd.cosmicbosses.utils.ParticleEffect;
import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.google.common.collect.Lists;

import java.util.*;

import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.bosses.intergalacticenforcer.Enforcer;
import me.ifamasssxd.cosmicbosses.bosses.intergalacticenforcer.Marksman;
import me.ifamasssxd.cosmicbosses.bosses.plaguebloater.PlagueBloater;
import me.ifamasssxd.cosmicbosses.bosses.yijki.Yijki;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.blockmanager.BlockChangeManager;
import me.ifamasssxd.cosmicbosses.struct.blockmanager.StoredBlock;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BossListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onCreatureSpawnReinforce(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.REINFORCEMENTS) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onEntityDamageEvent(EntityDamageByEntityEvent e) {
        if (e.getEntity().hasMetadata("boss")) {
            Entity damager;
            Boss boss = Boss.bosses.get(e.getEntity().getUniqueId());
            if (boss == null) {
                return;
            }
            e.getEntity().setMetadata("mcMMO: Spawned Entity", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
            if (e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof Player) {
                damager = (Entity)((Projectile)e.getDamager()).getShooter();
                if (boss instanceof Marksman || boss instanceof Enforcer) {
                    e.setCancelled(true);
                    e.getDamager().remove();
                    e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ZOMBIE_INFECT, 1.5f, 2.0f);
                    return;
                }
            } else {
                damager = e.getDamager();
            }
            boss.onDamageTaken(e, damager);
        } else if ((e.getDamager().hasMetadata("boss") || this.isBossProjectile(e.getDamager())) && e.getEntity() instanceof Player) {
            Entity ent = e.getDamager() instanceof Projectile ? (Entity)((Projectile)e.getDamager()).getShooter() : e.getDamager();
            Boss boss = Boss.bosses.get(ent.getUniqueId());
            if (boss == null) {
                return;
            }
            boss.onAttackPlayer(e, (Player)e.getEntity());
        }
    }

    private boolean isBossProjectile(Entity ent) {
        if (ent instanceof Projectile && ((Projectile)ent).getShooter() instanceof Entity) {
            return ((Entity)((Projectile)ent).getShooter()).hasMetadata("boss");
        }
        return false;
    }

    @EventHandler
    public void onEntityRegain(EntityRegainHealthEvent event) {
        Boss boss;
        if (!(event.getRegainReason() != EntityRegainHealthEvent.RegainReason.MAGIC && event.getRegainReason() != EntityRegainHealthEvent.RegainReason.MAGIC_REGEN || (boss = Boss.bosses.get(event.getEntity().getUniqueId())) == null || boss instanceof Yijki)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onHighest(EntityDamageByEntityEvent e) {
        if (e.getEntity().hasMetadata("boss")) {
            Boss boss = Boss.bosses.get(e.getEntity().getUniqueId());
            if (boss == null) {
                return;
            }
            if (e.getDamager() instanceof Player || e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof Player) {
                Player player = e.getDamager() instanceof Projectile ? (Player)((Projectile)e.getDamager()).getShooter() : (Player)e.getDamager();
                boss.getDamageTracker().trackDamage((Entity)player, e);
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onLowestEntityDAmage(EntityDamageByEntityEvent e) {
        if (e.getEntity().hasMetadata("boss") && CosmicBosses.isMcMMOEnabled() && (e.getDamager() instanceof Player || e.getDamager() instanceof Projectile && ((Projectile)e.getDamager()).getShooter() instanceof Player)) {
            Player player = e.getDamager() instanceof Projectile ? (Player)((Projectile)e.getDamager()).getShooter() : (Player)e.getDamager();
            player.setMetadata("no_exp_gain", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onChunkUnload(ChunkUnloadEvent unloadEvent) {
        for (Boss boss : Boss.bosses.values()) {
            if (boss != null && !boss.getEntity().isDead() && boss.getEntity().getLocation().getChunk().getX() == unloadEvent.getChunk().getX() && boss.getEntity().getLocation().getChunk().getZ() == unloadEvent.getChunk().getZ() && !unloadEvent.isCancelled()) {
                unloadEvent.setCancelled(true);
                Bukkit.getLogger().info("Cancelling chunk unload for boss at " + unloadEvent.getChunk().getX() + "X, " + unloadEvent.getChunk().getZ() + "Z!");
                return;
            }
            if (boss == null || boss.getBossType() != BossType.HOLIDAY_SPIRIT && boss.getBossType() != BossType.CHRISTMAS_SPIRIT) continue;
            BlockChangeManager change = CosmicBosses.getInstance().getBlockChangeManager();
            ArrayList toRemove = Lists.newArrayList();
            change.getStoredBlocks().entrySet().stream().filter(entry -> ((Location)entry.getKey()).getChunk().equals((Object)unloadEvent.getChunk())).forEach(entry -> {
                change.restoreBlock((Location)entry.getKey(), (StoredBlock)entry.getValue());
                toRemove.add(entry.getKey());
            });
            toRemove.forEach(loc -> change.getStoredBlocks().remove(loc));
            Bukkit.getLogger().info("Replacing " + toRemove.size() + " blocks in unloading chunk..");
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().hasMetadata("boss")) {
            Boss boss = Boss.bosses.get(e.getEntity().getUniqueId());
            if (boss == null) {
                return;
            }
            boss.onDamageTaken(e);
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getEntity().hasMetadata("boss")) {
            Boss boss = Boss.bosses.get(event.getEntity().getUniqueId());
            if (boss == null) {
                return;
            }
            boss.onMiscUpdate((Event)event);
        }

        if (event.getTarget() != null && event.getTarget().getType() != EntityType.PLAYER && event.getEntity().getType() != EntityType.SNOWMAN && event.getEntity().getType() != EntityType.WOLF && event.getEntity().getType() != EntityType.OCELOT && event.getEntity().getType() != EntityType.HORSE) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onChunkLoad(ChunkLoadEvent event) {
        String chunk = event.getChunk().getX() + ":" + event.getChunk().getZ();
        if (CosmicBosses.getInstance().getPendingBosses().containsKey(chunk)) {
            List<String[]> bosses = CosmicBosses.getInstance().getPendingBosses().remove(chunk);
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicBosses.getInstance(), () -> {
                for (String[] boss : bosses) {
                    BossUtils.spawnBoss(boss);
                }
            }, 20L);
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntity().hasMetadata("boss")) {
            Boss boss = Boss.bosses.get(event.getEntity().getUniqueId());
            if (boss == null) {
                return;
            }
            boss.onEntityChangeBlock(event, event.getEntity());
        } else if (event.getEntity().hasMetadata("chainReactionBlock")) {
            UUID id = (UUID)((MetadataValue)event.getEntity().getMetadata("chainReactionBlock").get(0)).value();
            Boss boss = Boss.bosses.get(id);
            if (boss == null) {
                return;
            }
            boss.onEntityChangeBlock(event, event.getEntity());
        }
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent event) {
        if (event.getEntity().hasMetadata("boss")) {
            Boss boss = Boss.bosses.get(event.getEntity().getUniqueId());
            if (boss == null) {
                return;
            }
            boss.onEntityTeleport(event, event.getEntity());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().hasMetadata("boss")) {
            e.getDrops().clear();
            e.setDroppedExp(0);
            Boss boss = Boss.bosses.get(e.getEntity().getUniqueId());
            boss.onDeath(e);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onTNTExplode(EntityExplodeEvent e) {
        boolean deathPlague;
        if (e.getEntity() instanceof TNTPrimed && ((deathPlague = e.getEntity().hasMetadata("death_plague")) || e.getEntity().hasMetadata("dragon_bomb"))) {
            for (Block block : Lists.newArrayList((Iterable)e.blockList())) {
                e.blockList().remove((Object)block);
            }
            if (deathPlague) {
                e.setCancelled(true);
                for (Boss boss : BossUtils.getAllBossesFromType(PlagueBloater.class)) {
                    PlagueBloater bloater = (PlagueBloater)boss;
                    if (!bloater.getPlagueBomb().getPrimedTNT().contains((Object)e.getEntity())) continue;
                    bloater.onTNTExplode(e.getEntity());
                    break;
                }
            }
        }
    }
    @EventHandler(priority=EventPriority.MONITOR)
    public void onEntityDamageEvent(EntityDamageEvent e) {
        Entity ent = e.getEntity();
        if (me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.containsKey(ent)) {
            me.ifamasssxd.cosmicbosses.boss.type.Boss b = me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.get(ent);
            b.onBossDamaged(e);
            if (e instanceof EntityDamageByEntityEvent && (((EntityDamageByEntityEvent)e).getDamager() instanceof Player || ((EntityDamageByEntityEvent)e).getDamager() instanceof Projectile && ((Projectile)((EntityDamageByEntityEvent)e).getDamager()).getShooter() instanceof Player)) {
                Player pl;
                Player player = ((EntityDamageByEntityEvent)e).getDamager() instanceof Player ? (Player)((EntityDamageByEntityEvent)e).getDamager() : (pl = ((EntityDamageByEntityEvent)e).getDamager() instanceof Projectile ? (Player)((Projectile)((EntityDamageByEntityEvent)e).getDamager()).getShooter() : null);
                if (pl != null) {
                    double dmg = (double) MiscUtil.getEnchantDamageModifier(pl) * e.getDamage() + e.getDamage();
                    b.players_who_hit_me.put(pl.getName(), b.players_who_hit_me.containsKey(pl.getName()) ? b.players_who_hit_me.get(pl.getName()) + dmg : dmg);
                }
            }
        }
        if (e instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent)e).getDamager();
            if (damager instanceof Projectile && ((Projectile)damager).getShooter() instanceof Entity) {
                damager = (Entity)((Object)((Projectile)damager).getShooter());
            }
            if (me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.containsKey(damager)) {
                e.setDamage(e.getDamage() * 1.5);
                me.ifamasssxd.cosmicbosses.boss.type.Boss b = me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.get(damager);
                b.onBossDamage(e);
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onEntityDeathEvent(EntityDeathEvent e) {
        LivingEntity ent = e.getEntity();
        if (me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.containsKey(ent)) {
            me.ifamasssxd.cosmicbosses.boss.type.Boss b = me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.get(ent);
            if (CosmicBossEggsPlugin.getPlugin().getServer().getPluginManager().isPluginEnabled("CosmicSpaceChests")) {
                ItemRarity ir = CosmicSpaceChestsAPI.getItemRarity((int)b.rarity.i);
                MiscUtil.launchFirework(ent.getLocation(), ir);
                int x = 0;
                for (Map.Entry<String, Double> data : b.players_who_hit_me.entrySet()) {
                    String pn = data.getKey();
                    double dmg = data.getValue();
                    if (dmg >= b.max_hp * 0.05 || b.players_who_hit_me.size() == 1) {
                        boolean has_room;
                        if (Bukkit.getPlayer(pn) == null) continue;
                        if (x >= 4) {
                            Bukkit.getPlayer(pn).sendMessage((Object)((Object) ChatColor.RED) + "This boss has already awared all its loot chests to other players.");
                            continue;
                        }
                        if (Bukkit.getPlayer(pn).getWorld().equals(ent.getWorld()) && Bukkit.getPlayer(pn).getLocation().distanceSquared(ent.getLocation()) > 4096.0) continue;
                        ++x;
                        ItemStack reward = CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ir);
                        if (b.rarity == BossRarity.LEGENDARY) {
                            reward = Math.random() < 0.15 ? CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.LEGENDARY) : CosmicSpaceChestsAPI.getDropPackageChest((ItemRarity)ItemRarity.ULTIMATE);
                        }
                        boolean bl = has_room = Bukkit.getPlayer(pn).getInventory().firstEmpty() != -1;
                        if (has_room) {
                            Bukkit.getPlayer(pn).getInventory().addItem(reward);
                            Bukkit.getPlayer(pn).sendMessage((Object)((Object)ChatColor.AQUA) + "You've slain the " + b.custom_name + "!");
                            Bukkit.getPlayer(pn).sendMessage((Object)((Object)ChatColor.GRAY) + "You have earned some loot!");
                            continue;
                        }
                        Bukkit.getPlayer(pn).sendMessage((Object)((Object)ChatColor.RED) + "You had no room in your inventory for loot!");
                        Bukkit.getPlayer(pn).sendMessage((Object)((Object)ChatColor.GRAY) + "Type /lootredeem to get your reward!");
                        Bukkit.getPlayer(pn).setMetadata("lootredeem", new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), reward));
                        continue;
                    }
                    if (Bukkit.getPlayer(pn) == null) continue;
                    Bukkit.getPlayer(pn).sendMessage((Object)((Object)ChatColor.RED) + "You did not deal enough damage to the boss to earn loot!");
                }
            }
            b.players_who_hit_me.clear();
            me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.remove(ent);
            b.onBossDeath(e);
        }
    }

    @EventHandler
    public void onProjectileLaunchEvent(ProjectileLaunchEvent e) {
        Entity shooter;
        if (!(e.getEntity() instanceof WitherSkull) || (shooter = (Entity)((Object)e.getEntity().getShooter())) == null || shooter.getType() != EntityType.WITHER || me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.containsKey(shooter)) {
            // empty if block
        }
        if (e.getEntity() instanceof Fireball && (shooter = (Entity)((Object)e.getEntity().getShooter())) != null && shooter.getType() == EntityType.BLAZE && me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.containsKey(shooter)) {
            BlazeBoss b = (BlazeBoss) me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.get(shooter);
            final Projectile orig_proj = e.getEntity();
            CosmicBossEggsPlugin.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask((Plugin)CosmicBosses.getInstance(), new Runnable(){

                @Override
                public void run() {
                    if (!orig_proj.hasMetadata("no_spawn")) {
                        SmallFireball FB = ((Blaze)shooter).launchProjectile(SmallFireball.class);
                        FB.setMetadata("no_spawn", new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), true));
                        FB.setVelocity(orig_proj.getVelocity());
                    }
                }
            }, 10L);
        }
    }

    @EventHandler
    public void onProjectileHitEvent(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Fireball) {
            BlazeBoss b;
            if (e.getEntity().getShooter() == null) {
                return;
            }
            Entity shooter = (Entity)((Object)e.getEntity().getShooter());
            if (shooter != null && shooter.getType() == EntityType.BLAZE && me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.containsKey(shooter) && (b = (BlazeBoss) me.ifamasssxd.cosmicbosses.boss.type.Boss.boss_map.get(shooter)) != null) {
                if (new Random().nextInt(3) == 0) {
                    MagmaCube ms = (MagmaCube)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0.0, 1.0, 0.0), EntityType.MAGMA_CUBE);
                    ms.setSize(new Random().nextInt(3) + 1);
                    ms.setCanPickupItems(false);
                    ms.setRemoveWhenFarAway(true);
                    ms.setMetadata("no_split", new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), true));
                    if (b != null && b.minions != null) {
                        b.minions.add(ms);
                    } else {
                        ms.remove();
                    }
                } else if (new Random().nextInt(5) == 0) {
                    Blaze bl = (Blaze)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation().add(0.0, 2.0, 0.0), EntityType.BLAZE);
                    bl.setCanPickupItems(false);
                    bl.setRemoveWhenFarAway(true);
                    if (b != null && b.minions != null) {
                        b.minions.add(bl);
                    } else {
                        bl.remove();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSlimeSplit(SlimeSplitEvent e) {
        if (e.getEntity().hasMetadata("no_split")) {
            e.setCancelled(true);
            e.getEntity().remove();
        }
    }

    @EventHandler
    public void onProjectileLaunchMONITOR(BlockDispenseEvent e) {
        if (e.getBlock().getType() == Material.DISPENSER && BossEggUtil.getBoss(e.getItem()) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void superWitherEffect(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.WITHER && e.getEntity().hasMetadata("undead_wither") && e.getEntity() instanceof LivingEntity) {
            LivingEntity le = (LivingEntity)e.getEntity();
            e.setDamage(0.0);
            le.damage(1.0);
        }
    }

    @EventHandler
    public void onVehicleExitEvent(VehicleExitEvent e) {
        if (e.getExited().hasMetadata("cbe_boss")) {
            if (e.getExited().getHealth() / e.getExited().getMaxHealth() > 0.5) {
                e.setCancelled(true);
            } else {
                final Location loc = e.getVehicle().getLocation();
                e.getVehicle().remove();
                Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicBosses.getInstance(), new Runnable(){

                    @Override
                    public void run() {
                        try {
                            ParticleEffect.sendToLocation(ParticleEffect.FLAME, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.25f, 50);
                        }
                        catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    @EventHandler
    public void undeadTNTEffect(EntityExplodeEvent e) {
        if (e.getEntity() != null && e.getEntity().hasMetadata("undead_tnt")) {
            int slave_count = 0;
            for (Entity ent : e.getEntity().getNearbyEntities(4.0, 4.0, 4.0)) {
                if (ent.hasMetadata("undead_slave")) {
                    ++slave_count;
                }
                if (!(ent instanceof Player)) continue;
                Player pl = (Player)ent;
                pl.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60 + new Random().nextInt(60), 1));
                pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60 + new Random().nextInt(60), 0));
                pl.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60 + new Random().nextInt(60), 1));
                pl.setFireTicks(60 + new Random().nextInt(60));
                pl.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)((Object)ChatColor.RED) + "You were hit with TNT from the Headless King!");
            }
            final Location loc = e.getEntity().getLocation();
            if (new Random().nextInt(5) == 0 && slave_count < 4) {
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicBosses.getInstance(), new Runnable(){

                    @Override
                    public void run() {
                        int spawn_count = new Random().nextInt(3) + 1;
                        while (spawn_count > 0) {
                            --spawn_count;
                            Skeleton sk = (Skeleton)loc.getWorld().spawnEntity(loc, EntityType.SKELETON);
                            if (new Random().nextBoolean()) {
                                sk.setSkeletonType(Skeleton.SkeletonType.WITHER);
                            }
                            sk.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                            sk.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
                            sk.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0));
                            sk.setCustomName(sk.getSkeletonType() == Skeleton.SkeletonType.NORMAL ? (Object)((Object)ChatColor.RED) + "Headless Undead" : (Object)((Object)ChatColor.RED) + "Headless Ghoul");
                            sk.setCustomNameVisible(true);
                            sk.setMetadata("undead_slave", new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), true));
                        }
                        Bukkit.getScheduler().runTaskAsynchronously(CosmicBosses.getInstance(), new Runnable(){

                            @Override
                            public void run() {
                                try {
                                    ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, loc, new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat(), 0.25f, 50);
                                }
                                catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                            }
                        });
                    }
                }, 25L);
            }
        }
    }
}

