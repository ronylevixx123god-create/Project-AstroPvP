/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.Vector
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.blacklist.events.ItemUseBlacklistEvent;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.RegionQueryUtil;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.GlobalRegionManager;
import com.sk89q.worldguard.protection.events.DisallowedPVPEvent;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import java.util.ArrayList;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PigZapEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class WorldGuardEntityListener
implements Listener {
    private WorldGuardPlugin plugin;

    public WorldGuardEntityListener(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onEntityInteract(EntityInteractEvent event) {
        Entity entity = event.getEntity();
        Block block = event.getBlock();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(entity.getWorld());
        if (block.getTypeId() == 60 && wcfg.disableCreatureCropTrampling) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onExpBottle(ExpBottleEvent event) {
        WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(event.getEntity().getWorld());
        if (wcfg.disableExpDrops || !this.plugin.getGlobalRegionManager().allows(DefaultFlag.EXP_DROPS, event.getEntity().getLocation())) {
            event.setExperience(0);
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onEntityDeath(EntityDeathEvent event) {
        WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(event.getEntity().getWorld());
        if (wcfg.disableExpDrops || !this.plugin.getGlobalRegionManager().allows(DefaultFlag.EXP_DROPS, event.getEntity().getLocation())) {
            event.setDroppedExp(0);
        }
        if (event instanceof PlayerDeathEvent && wcfg.disableDeathMessages) {
            ((PlayerDeathEvent)event).setDeathMessage("");
        }
    }

    private void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        Entity defender = event.getEntity();
        EntityDamageEvent.DamageCause type = event.getCause();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(defender.getWorld());
        if (defender instanceof Wolf && ((Wolf)defender).isTamed()) {
            if (wcfg.antiWolfDumbness && type != EntityDamageEvent.DamageCause.VOID) {
                event.setCancelled(true);
                return;
            }
        } else if (defender instanceof Player) {
            Player player = (Player)defender;
            if (this.isInvincible(player)) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.disableLavaDamage && type == EntityDamageEvent.DamageCause.LAVA) {
                event.setCancelled(true);
                player.setFireTicks(0);
                return;
            }
            if (wcfg.disableContactDamage && type == EntityDamageEvent.DamageCause.CONTACT) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.teleportOnVoid && type == EntityDamageEvent.DamageCause.VOID) {
                BukkitUtil.findFreePosition(player);
                event.setCancelled(true);
                return;
            }
            if (wcfg.disableVoidDamage && type == EntityDamageEvent.DamageCause.VOID) {
                event.setCancelled(true);
                return;
            }
            if (type == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && (wcfg.disableExplosionDamage || wcfg.blockOtherExplosions || wcfg.explosionFlagCancellation && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.OTHER_EXPLOSION, player.getLocation()))) {
                event.setCancelled(true);
                return;
            }
        } else if (type == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && (wcfg.blockOtherExplosions || wcfg.explosionFlagCancellation && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.OTHER_EXPLOSION, defender.getLocation()))) {
            event.setCancelled(true);
            return;
        }
    }

    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Player player;
        if (event.getDamager() instanceof Projectile) {
            this.onEntityDamageByProjectile(event);
            return;
        }
        Entity attacker = event.getDamager();
        Entity defender = event.getEntity();
        if (attacker instanceof Player) {
            player = (Player)attacker;
            ConfigurationManager cfg = this.plugin.getGlobalStateManager();
            WorldConfiguration wcfg = cfg.get(player.getWorld());
            ItemStack held = player.getInventory().getItemInHand();
            if (held != null && wcfg.getBlacklist() != null && !wcfg.getBlacklist().check(new ItemUseBlacklistEvent(this.plugin.wrapPlayer(player), BukkitUtil.toVector(player.getLocation()), held.getTypeId()), false, false)) {
                event.setCancelled(true);
                return;
            }
        }
        if (defender instanceof ItemFrame && this.checkItemFrameProtection(attacker, (ItemFrame)defender)) {
            event.setCancelled(true);
            return;
        }
        if (defender instanceof Player) {
            player = (Player)defender;
            LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
            ConfigurationManager cfg = this.plugin.getGlobalStateManager();
            WorldConfiguration wcfg = cfg.get(player.getWorld());
            if (this.isInvincible(player)) {
                if (!(!wcfg.regionInvinciblityRemovesMobs || !(attacker instanceof LivingEntity) || attacker instanceof Player || attacker instanceof Tameable && ((Tameable)((Object)attacker)).isTamed())) {
                    attacker.remove();
                }
                event.setCancelled(true);
                return;
            }
            if (wcfg.disableLightningDamage && event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.disableExplosionDamage) {
                switch (event.getCause()) {
                    case BLOCK_EXPLOSION: 
                    case ENTITY_EXPLOSION: {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if (attacker != null) {
                Vector pt2;
                ApplicableRegionSet set;
                RegionManager mgr;
                RegionManager mgr2;
                Vector pt;
                if (attacker instanceof Player && wcfg.useRegions) {
                    pt = BukkitUtil.toVector(defender.getLocation());
                    Vector pt22 = BukkitUtil.toVector(attacker.getLocation());
                    mgr2 = this.plugin.getGlobalRegionManager().get(player.getWorld());
                    if (!mgr2.getApplicableRegions(pt22).allows(DefaultFlag.PVP, this.plugin.wrapPlayer((Player)attacker))) {
                        this.tryCancelPVPEvent((Player)attacker, player, event, true);
                    } else if (!mgr2.getApplicableRegions(pt).allows(DefaultFlag.PVP, localPlayer)) {
                        this.tryCancelPVPEvent((Player)attacker, player, event, false);
                    }
                }
                if (attacker instanceof TNTPrimed || attacker instanceof ExplosiveMinecart) {
                    if (wcfg.blockTNTExplosions) {
                        event.setCancelled(true);
                        return;
                    }
                    if (wcfg.useRegions && wcfg.explosionFlagCancellation) {
                        pt = BukkitUtil.toVector(defender.getLocation());
                        mgr = this.plugin.getGlobalRegionManager().get(player.getWorld());
                        set = mgr.getApplicableRegions(pt);
                        if (!set.allows(DefaultFlag.TNT, localPlayer)) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
                if (attacker instanceof Fireball) {
                    if (attacker instanceof WitherSkull) {
                        if (wcfg.blockWitherSkullExplosions) {
                            event.setCancelled(true);
                            return;
                        }
                    } else if (wcfg.blockFireballExplosions) {
                        event.setCancelled(true);
                        return;
                    }
                    if (wcfg.useRegions) {
                        Fireball fireball = (Fireball)attacker;
                        Vector pt3 = BukkitUtil.toVector(defender.getLocation());
                        mgr2 = this.plugin.getGlobalRegionManager().get(player.getWorld());
                        ApplicableRegionSet set2 = mgr2.getApplicableRegions(pt3);
                        if (fireball.getShooter() instanceof Player) {
                            pt2 = BukkitUtil.toVector(((LivingEntity)fireball.getShooter()).getLocation());
                            if (!mgr2.getApplicableRegions(pt2).allows(DefaultFlag.PVP, this.plugin.wrapPlayer((Player)fireball.getShooter()))) {
                                this.tryCancelPVPEvent((Player)fireball.getShooter(), player, event, true);
                            } else if (!set2.allows(DefaultFlag.PVP, localPlayer)) {
                                this.tryCancelPVPEvent((Player)fireball.getShooter(), player, event, false);
                            }
                        } else if (!set2.allows(DefaultFlag.GHAST_FIREBALL, localPlayer) && wcfg.explosionFlagCancellation) {
                            event.setCancelled(true);
                            return;
                        }
                    }
                }
                if (attacker instanceof LivingEntity && !(attacker instanceof Player)) {
                    if (attacker instanceof Creeper && wcfg.blockCreeperExplosions) {
                        event.setCancelled(true);
                        return;
                    }
                    if (wcfg.disableMobDamage) {
                        event.setCancelled(true);
                        return;
                    }
                    if (wcfg.useRegions) {
                        pt = BukkitUtil.toVector(defender.getLocation());
                        mgr = this.plugin.getGlobalRegionManager().get(player.getWorld());
                        set = mgr.getApplicableRegions(pt);
                        if (!set.allows(DefaultFlag.MOB_DAMAGE, localPlayer) && !(attacker instanceof Tameable)) {
                            event.setCancelled(true);
                            return;
                        }
                        if (attacker instanceof Creeper && !set.allows(DefaultFlag.CREEPER_EXPLOSION, localPlayer) && wcfg.explosionFlagCancellation) {
                            event.setCancelled(true);
                            return;
                        }
                        if (attacker instanceof Tameable) {
                            if (((Tameable)((Object)attacker)).getOwner() == null && !set.allows(DefaultFlag.MOB_DAMAGE, localPlayer)) {
                                event.setCancelled(true);
                                return;
                            }
                            if (!(((Tameable)((Object)attacker)).getOwner() instanceof Player)) {
                                return;
                            }
                            Player beastMaster = (Player)((Tameable)((Object)attacker)).getOwner();
                            pt2 = BukkitUtil.toVector(attacker.getLocation());
                            if (!mgr.getApplicableRegions(pt2).allows(DefaultFlag.PVP, this.plugin.wrapPlayer(beastMaster))) {
                                this.tryCancelPVPEvent(beastMaster, player, event, true);
                            } else if (!set.allows(DefaultFlag.PVP, localPlayer)) {
                                this.tryCancelPVPEvent(beastMaster, player, event, false);
                            }
                        }
                    }
                }
            }
        }
    }

    private void onEntityDamageByProjectile(EntityDamageByEntityEvent event) {
        Entity defender = event.getEntity();
        LivingEntity attacker = null;
        try {
            attacker = (LivingEntity)((Projectile)event.getDamager()).getShooter();
        }
        catch (ClassCastException cce) {
            Bukkit.getLogger().info("[WorldGuard] Failed to resolve attacker for EntityDamageByProjectile because attacker != entity.");
            attacker = null;
        }
        if (defender instanceof Player) {
            Vector pt;
            Player player = (Player)defender;
            LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
            ConfigurationManager cfg = this.plugin.getGlobalStateManager();
            WorldConfiguration wcfg = cfg.get(player.getWorld());
            if (this.isInvincible(player)) {
                event.setCancelled(true);
                return;
            }
            if (attacker != null && !(attacker instanceof Player)) {
                if (wcfg.disableMobDamage) {
                    event.setCancelled(true);
                    return;
                }
                if (wcfg.useRegions) {
                    pt = BukkitUtil.toVector(defender.getLocation());
                    RegionManager mgr = this.plugin.getGlobalRegionManager().get(player.getWorld());
                    if (!mgr.getApplicableRegions(pt).allows(DefaultFlag.MOB_DAMAGE, localPlayer)) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if (attacker != null && attacker instanceof Player) {
                if (event.getDamager() instanceof EnderPearl && attacker == player) {
                    return;
                }
                if (wcfg.useRegions) {
                    pt = BukkitUtil.toVector(defender.getLocation());
                    Vector pt2 = BukkitUtil.toVector(attacker.getLocation());
                    RegionManager mgr = this.plugin.getGlobalRegionManager().get(player.getWorld());
                    if (!mgr.getApplicableRegions(pt2).allows(DefaultFlag.PVP, this.plugin.wrapPlayer((Player)attacker))) {
                        this.tryCancelPVPEvent((Player)attacker, player, event, true);
                    } else if (!mgr.getApplicableRegions(pt).allows(DefaultFlag.PVP, localPlayer)) {
                        this.tryCancelPVPEvent((Player)attacker, player, event, false);
                    }
                }
            }
        } else if (defender instanceof ItemFrame && this.checkItemFrameProtection(attacker, (ItemFrame)defender)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent) {
            this.onEntityDamageByEntity((EntityDamageByEntityEvent)event);
            return;
        }
        if (event instanceof EntityDamageByBlockEvent) {
            this.onEntityDamageByBlock((EntityDamageByBlockEvent)event);
            return;
        }
        Entity defender = event.getEntity();
        EntityDamageEvent.DamageCause type = event.getCause();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(defender.getWorld());
        if (defender instanceof Wolf && ((Wolf)defender).isTamed()) {
            if (wcfg.antiWolfDumbness) {
                event.setCancelled(true);
                return;
            }
        } else if (defender instanceof Player) {
            Player player = (Player)defender;
            if (this.isInvincible(player)) {
                event.setCancelled(true);
                player.setFireTicks(0);
                return;
            }
            if (type == EntityDamageEvent.DamageCause.WITHER) {
                if (wcfg.disableMobDamage) {
                    event.setCancelled(true);
                    return;
                }
                if (wcfg.useRegions) {
                    Vector pt = BukkitUtil.toVector(defender.getLocation());
                    RegionManager mgr = this.plugin.getGlobalRegionManager().get(player.getWorld());
                    ApplicableRegionSet set = mgr.getApplicableRegions(pt);
                    if (!set.allows(DefaultFlag.MOB_DAMAGE, this.plugin.wrapPlayer(player))) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            if (type == EntityDamageEvent.DamageCause.DROWNING && cfg.hasAmphibiousMode(player)) {
                player.setRemainingAir(player.getMaximumAir());
                event.setCancelled(true);
                return;
            }
            ItemStack helmet = player.getInventory().getHelmet();
            if (type == EntityDamageEvent.DamageCause.DROWNING && wcfg.pumpkinScuba && helmet != null && (helmet.getTypeId() == 86 || helmet.getTypeId() == 91)) {
                player.setRemainingAir(player.getMaximumAir());
                event.setCancelled(true);
                return;
            }
            if (wcfg.disableFallDamage && type == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.disableFireDamage && (type == EntityDamageEvent.DamageCause.FIRE || type == EntityDamageEvent.DamageCause.FIRE_TICK)) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.disableDrowningDamage && type == EntityDamageEvent.DamageCause.DROWNING) {
                player.setRemainingAir(player.getMaximumAir());
                event.setCancelled(true);
                return;
            }
            if (wcfg.teleportOnSuffocation && type == EntityDamageEvent.DamageCause.SUFFOCATION) {
                BukkitUtil.findFreePosition(player);
                event.setCancelled(true);
                return;
            }
            if (wcfg.disableSuffocationDamage && type == EntityDamageEvent.DamageCause.SUFFOCATION) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onEntityCombust(EntityCombustEvent event) {
        Player player;
        Entity entity = event.getEntity();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(entity.getWorld());
        if (entity instanceof Player && (cfg.hasGodMode(player = (Player)entity) || wcfg.useRegions && RegionQueryUtil.isInvincible(this.plugin, player))) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onEntityExplode(EntityExplodeEvent event) {
        RegionManager mgr;
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        Location l = event.getLocation();
        World world = l.getWorld();
        WorldConfiguration wcfg = cfg.get(world);
        Entity ent = event.getEntity();
        if (cfg.activityHaltToggle) {
            if (ent != null) {
                ent.remove();
            }
            event.setCancelled(true);
            return;
        }
        if (ent instanceof Creeper) {
            if (wcfg.blockCreeperExplosions) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.blockCreeperBlockDamage) {
                event.blockList().clear();
                return;
            }
            if (wcfg.useRegions) {
                mgr = this.plugin.getGlobalRegionManager().get(world);
                for (Block block : new ArrayList<Block>(event.blockList())) {
                    if (mgr.getApplicableRegions((Vector)BukkitUtil.toVector(block)).allows(DefaultFlag.CREEPER_EXPLOSION)) continue;
                    event.blockList().remove(block);
                }
                if (wcfg.explosionFlagCancellation && event.blockList().size() == 0) {
                    event.setCancelled(true);
                }
            }
        } else if (ent instanceof EnderDragon) {
            if (wcfg.blockEnderDragonBlockDamage) {
                event.blockList().clear();
                return;
            }
            if (wcfg.useRegions) {
                mgr = this.plugin.getGlobalRegionManager().get(world);
                for (Block block : event.blockList()) {
                    if (mgr.getApplicableRegions((Vector)BukkitUtil.toVector(block)).allows(DefaultFlag.ENDERDRAGON_BLOCK_DAMAGE)) continue;
                    event.blockList().clear();
                    if (wcfg.explosionFlagCancellation) {
                        event.setCancelled(true);
                    }
                    return;
                }
            }
        } else if (ent instanceof TNTPrimed || ent instanceof ExplosiveMinecart) {
            if (wcfg.blockTNTExplosions) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.blockTNTBlockDamage) {
                event.blockList().clear();
                return;
            }
            if (wcfg.useRegions) {
                mgr = this.plugin.getGlobalRegionManager().get(world);
                for (Block block : new ArrayList<Block>(event.blockList())) {
                    ApplicableRegionSet ars = mgr.getApplicableRegions((Vector)BukkitUtil.toVector(block));
                    if (ars.size() <= 0 || ars.getFlag(DefaultFlag.TNT) != StateFlag.State.DENY) continue;
                    event.blockList().remove(block);
                }
                if (wcfg.explosionFlagCancellation && event.blockList().size() == 0) {
                    event.setCancelled(true);
                }
            }
        } else if (ent instanceof Fireball) {
            if (ent instanceof WitherSkull) {
                if (wcfg.blockWitherSkullExplosions) {
                    event.setCancelled(true);
                    return;
                }
                if (wcfg.blockWitherSkullBlockDamage) {
                    event.blockList().clear();
                    return;
                }
            } else {
                if (wcfg.blockFireballExplosions) {
                    event.setCancelled(true);
                    return;
                }
                if (wcfg.blockFireballBlockDamage) {
                    event.blockList().clear();
                    return;
                }
            }
            if (wcfg.useRegions) {
                mgr = this.plugin.getGlobalRegionManager().get(world);
                for (Block block : event.blockList()) {
                    if (mgr.getApplicableRegions((Vector)BukkitUtil.toVector(block)).allows(DefaultFlag.GHAST_FIREBALL)) continue;
                    event.blockList().clear();
                    if (wcfg.explosionFlagCancellation) {
                        event.setCancelled(true);
                    }
                    return;
                }
            }
        } else if (ent instanceof Wither) {
            if (wcfg.blockWitherExplosions) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.blockWitherBlockDamage) {
                event.blockList().clear();
                return;
            }
        } else {
            if (wcfg.blockOtherExplosions) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.useRegions) {
                mgr = this.plugin.getGlobalRegionManager().get(world);
                for (Block block : event.blockList()) {
                    if (mgr.getApplicableRegions((Vector)BukkitUtil.toVector(block)).allows(DefaultFlag.OTHER_EXPLOSION)) continue;
                    event.blockList().clear();
                    if (wcfg.explosionFlagCancellation) {
                        event.setCancelled(true);
                    }
                    return;
                }
            }
        }
        if (wcfg.signChestProtection) {
            for (Block block : event.blockList()) {
                if (!wcfg.isChestProtected(block)) continue;
                event.blockList().clear();
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getEntity().getWorld());
        Entity ent = event.getEntity();
        if (cfg.activityHaltToggle) {
            ent.remove();
            event.setCancelled(true);
            return;
        }
        if (event.getEntityType() == EntityType.WITHER) {
            if (wcfg.blockWitherExplosions) {
                event.setCancelled(true);
                return;
            }
        } else if (event.getEntityType() == EntityType.WITHER_SKULL) {
            if (wcfg.blockWitherSkullExplosions) {
                event.setCancelled(true);
                return;
            }
        } else if (event.getEntityType() == EntityType.FIREBALL) {
            if (wcfg.blockFireballExplosions) {
                event.setCancelled(true);
                return;
            }
        } else if (event.getEntityType() == EntityType.CREEPER) {
            if (wcfg.blockCreeperExplosions) {
                event.setCancelled(true);
                return;
            }
        } else if ((event.getEntityType() == EntityType.PRIMED_TNT || event.getEntityType() == EntityType.MINECART_TNT) && wcfg.blockTNTExplosions) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        if (cfg.activityHaltToggle) {
            event.setCancelled(true);
            return;
        }
        WorldConfiguration wcfg = cfg.get(event.getEntity().getWorld());
        if (!wcfg.blockPluginSpawning && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }
        if (wcfg.allowTamedSpawns && event.getEntity() instanceof Tameable && ((Tameable)((Object)event.getEntity())).isTamed()) {
            return;
        }
        EntityType entityType = event.getEntityType();
        if (wcfg.blockCreatureSpawn.contains((Object)entityType)) {
            event.setCancelled(true);
            return;
        }
        Location eventLoc = event.getLocation();
        if (wcfg.useRegions && cfg.useRegionsCreatureSpawnEvent) {
            Vector pt = BukkitUtil.toVector(eventLoc);
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(eventLoc.getWorld());
            if (mgr == null) {
                return;
            }
            ApplicableRegionSet set = mgr.getApplicableRegions(pt);
            if (!set.allows(DefaultFlag.MOB_SPAWNING)) {
                event.setCancelled(true);
                return;
            }
            Set entityTypes = (Set)set.getFlag(DefaultFlag.DENY_SPAWN);
            if (entityTypes != null && entityTypes.contains((Object)entityType)) {
                event.setCancelled(true);
                return;
            }
        }
        if (wcfg.blockGroundSlimes && entityType == EntityType.SLIME && eventLoc.getY() >= 60.0 && event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onCreatePortal(EntityCreatePortalEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getEntity().getWorld());
        switch (event.getEntityType()) {
            case ENDER_DRAGON: {
                if (!wcfg.blockEnderDragonPortalCreation) break;
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPigZap(PigZapEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getEntity().getWorld());
        if (wcfg.disablePigZap) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onCreeperPower(CreeperPowerEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getEntity().getWorld());
        if (wcfg.disableCreeperPower) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        Entity ent = event.getEntity();
        World world = ent.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.disableHealthRegain) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        Entity ent = event.getEntity();
        Block block = event.getBlock();
        Location location = block.getLocation();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(ent.getWorld());
        if (ent instanceof Enderman) {
            if (wcfg.disableEndermanGriefing) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ENDER_BUILD, location)) {
                event.setCancelled(true);
                return;
            }
        } else if (ent.getType() == EntityType.WITHER) {
            if (wcfg.blockWitherBlockDamage || wcfg.blockWitherExplosions) {
                event.setCancelled(true);
                return;
            }
        } else if (event instanceof EntityBreakDoorEvent) {
            if (wcfg.blockZombieDoorDestruction) {
                event.setCancelled(true);
                return;
            }
        } else if (ent instanceof FallingBlock && this.plugin.getGlobalRegionManager().get(block.getWorld()).getApplicableRegions(location).size() > 0) {
            event.setCancelled(true);
            ent.remove();
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (event.getFoodLevel() < player.getFoodLevel() && this.isInvincible(player)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onPotionSplash(PotionSplashEvent event) {
        ThrownPotion entity = event.getEntity();
        ThrownPotion potion = event.getPotion();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(entity.getWorld());
        if (wcfg.blockPotionsAlways && wcfg.blockPotions.size() > 0) {
            boolean blocked = false;
            for (PotionEffect effect : potion.getEffects()) {
                if (!wcfg.blockPotions.contains(effect.getType())) continue;
                blocked = true;
                break;
            }
            if (blocked) {
                event.setCancelled(true);
                return;
            }
        }
        GlobalRegionManager regionMan = this.plugin.getGlobalRegionManager();
        int blockedEntities = 0;
        for (LivingEntity e : event.getAffectedEntities()) {
            if (regionMan.allows(DefaultFlag.POTION_SPLASH, e.getLocation(), e instanceof Player ? this.plugin.wrapPlayer((Player)e) : null)) continue;
            event.setIntensity(e, 0.0);
            ++blockedEntities;
        }
        if (blockedEntities == event.getAffectedEntities().size()) {
            event.setCancelled(true);
        }
    }

    private boolean isInvincible(Player player) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(player.getWorld());
        boolean god = cfg.hasGodMode(player);
        if (wcfg.useRegions) {
            Boolean flag = RegionQueryUtil.isAllowedInvinciblity(this.plugin, player);
            boolean allowed = flag == null || flag != false;
            boolean invincible = RegionQueryUtil.isInvincible(this.plugin, player);
            if (allowed) {
                return god || invincible;
            }
            return god && this.plugin.hasPermission(player, "worldguard.god.override-regions") || invincible;
        }
        return god;
    }

    public void tryCancelPVPEvent(Player attackingPlayer, Player defendingPlayer, EntityDamageByEntityEvent event, boolean aggressorTriggered) {
        DisallowedPVPEvent disallowedPVPEvent = new DisallowedPVPEvent(attackingPlayer, defendingPlayer, event);
        this.plugin.getServer().getPluginManager().callEvent(disallowedPVPEvent);
        if (!disallowedPVPEvent.isCancelled()) {
            if (aggressorTriggered) {
                attackingPlayer.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You are in a no-PvP area.");
            } else {
                attackingPlayer.sendMessage((Object)((Object)ChatColor.DARK_RED) + "That player is in a no-PvP area.");
            }
            event.setCancelled(true);
        }
    }

    private boolean checkItemFrameProtection(Entity attacker, ItemFrame defender) {
        World world = attacker.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.useRegions) {
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
            if (attacker instanceof Player) {
                Player player = (Player)attacker;
                LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
                if (!this.plugin.getGlobalRegionManager().hasBypass(player, world) && !mgr.getApplicableRegions(defender.getLocation()).canBuild(localPlayer)) {
                    player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
                    return true;
                }
            } else if (!this.plugin.getGlobalRegionManager().allows(DefaultFlag.ENTITY_ITEM_FRAME_DESTROY, defender.getLocation())) {
                return true;
            }
        }
        return wcfg.blockEntityItemFrameDestroy && !(attacker instanceof Player);
    }
}

