/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldedit.blocks.BlockType
 *  com.sk89q.worldedit.blocks.ItemType
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BlockType;
import com.sk89q.worldedit.blocks.ItemType;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.blacklist.events.BlockBreakBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockPlaceBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.DestroyWithBlacklistEvent;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.SpongeUtil;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

public class WorldGuardBlockListener
implements Listener {
    private WorldGuardPlugin plugin;

    public WorldGuardBlockListener(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    protected WorldConfiguration getWorldConfig(World world) {
        return this.plugin.getGlobalStateManager().get(world);
    }

    protected WorldConfiguration getWorldConfig(Player player) {
        return this.getWorldConfig(player.getWorld());
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        Block blockDamaged = event.getBlock();
        if (blockDamaged.getTypeId() == 92 && !this.plugin.getGlobalRegionManager().canBuild(player, blockDamaged)) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not invited to this tea party!");
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack held;
        Player player = event.getPlayer();
        WorldConfiguration wcfg = this.getWorldConfig(player);
        if (!(wcfg.itemDurability || (held = player.getItemInHand()).getTypeId() <= 0 || ItemType.usesDamageValue((int)held.getTypeId()) || BlockType.usesData((int)held.getTypeId()))) {
            held.setDurability((short)0);
            player.setItemInHand(held);
        }
        if (!this.plugin.getGlobalRegionManager().canBuild(player, event.getBlock())) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
            event.setCancelled(true);
            return;
        }
        if (wcfg.getBlacklist() != null) {
            if (!wcfg.getBlacklist().check(new BlockBreakBlacklistEvent(this.plugin.wrapPlayer(player), (Vector)BukkitUtil.toVector(event.getBlock()), event.getBlock().getTypeId()), false, false)) {
                event.setCancelled(true);
                return;
            }
            if (!wcfg.getBlacklist().check(new DestroyWithBlacklistEvent(this.plugin.wrapPlayer(player), (Vector)BukkitUtil.toVector(event.getBlock()), player.getItemInHand().getTypeId()), false, false)) {
                event.setCancelled(true);
                return;
            }
        }
        if (wcfg.isChestProtected(event.getBlock(), player)) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "The chest is protected.");
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onBlockFromTo(BlockFromToEvent event) {
        Block blockTo;
        Block blockFrom = event.getBlock();
        boolean isLava = blockFrom.getTypeId() == 10 || blockFrom.getTypeId() == 11;
        boolean isAir = blockFrom.getTypeId() == 0;
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        if (cfg.activityHaltToggle) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.disableObsidianGenerators && (isAir || isLava) && (blockTo = event.getToBlock()).getTypeId() == 55) {
            blockTo.setTypeId(0);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockIgnite(BlockIgniteEvent event) {
        int z;
        int y;
        int x;
        boolean isFireSpread;
        BlockIgniteEvent.IgniteCause cause = event.getCause();
        Block block = event.getBlock();
        World world = block.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (cfg.activityHaltToggle) {
            event.setCancelled(true);
            return;
        }
        boolean bl = isFireSpread = cause == BlockIgniteEvent.IgniteCause.SPREAD;
        if (wcfg.preventLightningFire && cause == BlockIgniteEvent.IgniteCause.LIGHTNING) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.preventLavaFire && cause == BlockIgniteEvent.IgniteCause.LAVA) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.disableFireSpread && isFireSpread) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.blockLighter && (cause == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL || cause == BlockIgniteEvent.IgniteCause.FIREBALL) && event.getPlayer() != null && !this.plugin.hasPermission(event.getPlayer(), "worldguard.override.lighter")) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.fireSpreadDisableToggle && isFireSpread) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.disableFireSpreadBlocks.size() > 0 && isFireSpread && (wcfg.disableFireSpreadBlocks.contains(world.getBlockTypeIdAt(x = block.getX(), (y = block.getY()) - 1, z = block.getZ())) || wcfg.disableFireSpreadBlocks.contains(world.getBlockTypeIdAt(x + 1, y, z)) || wcfg.disableFireSpreadBlocks.contains(world.getBlockTypeIdAt(x - 1, y, z)) || wcfg.disableFireSpreadBlocks.contains(world.getBlockTypeIdAt(x, y, z - 1)) || wcfg.disableFireSpreadBlocks.contains(world.getBlockTypeIdAt(x, y, z + 1)))) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.useRegions) {
            BlockVector pt = BukkitUtil.toVector(block);
            Player player = event.getPlayer();
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
            ApplicableRegionSet set = mgr.getApplicableRegions((Vector)pt);
            if (player != null && !this.plugin.getGlobalRegionManager().hasBypass(player, world)) {
                LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
                if (!(cause != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL && cause != BlockIgniteEvent.IgniteCause.FIREBALL || set.allows(DefaultFlag.LIGHTER) || set.canBuild(localPlayer) || this.plugin.hasPermission(player, "worldguard.override.lighter"))) {
                    event.setCancelled(true);
                    return;
                }
            }
            if (wcfg.highFreqFlags && isFireSpread && !set.allows(DefaultFlag.FIRE_SPREAD)) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.highFreqFlags && cause == BlockIgniteEvent.IgniteCause.LAVA && !set.allows(DefaultFlag.LAVA_FIRE)) {
                event.setCancelled(true);
                return;
            }
            if (cause == BlockIgniteEvent.IgniteCause.FIREBALL && event.getPlayer() == null && !set.allows(DefaultFlag.GHAST_FIREBALL)) {
                event.setCancelled(true);
                return;
            }
            if (cause == BlockIgniteEvent.IgniteCause.LIGHTNING && !set.allows(DefaultFlag.LIGHTNING)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockBurn(BlockBurnEvent event) {
        Block block;
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        if (cfg.activityHaltToggle) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.disableFireSpread) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.fireSpreadDisableToggle) {
            Block block2 = event.getBlock();
            event.setCancelled(true);
            this.checkAndDestroyAround(block2.getWorld(), block2.getX(), block2.getY(), block2.getZ(), 51);
            return;
        }
        if (wcfg.disableFireSpreadBlocks.size() > 0 && wcfg.disableFireSpreadBlocks.contains((block = event.getBlock()).getTypeId())) {
            event.setCancelled(true);
            this.checkAndDestroyAround(block.getWorld(), block.getX(), block.getY(), block.getZ(), 51);
            return;
        }
        if (wcfg.isChestProtected(event.getBlock())) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.useRegions) {
            block = event.getBlock();
            int x = block.getX();
            int y = block.getY();
            int z = block.getZ();
            BlockVector pt = BukkitUtil.toVector(block);
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(block.getWorld());
            ApplicableRegionSet set = mgr.getApplicableRegions((Vector)pt);
            if (!set.allows(DefaultFlag.FIRE_SPREAD)) {
                this.checkAndDestroyAround(block.getWorld(), x, y, z, 51);
                event.setCancelled(true);
                return;
            }
        }
    }

    private void checkAndDestroyAround(World world, int x, int y, int z, int required) {
        this.checkAndDestroy(world, x, y, z + 1, required);
        this.checkAndDestroy(world, x, y, z - 1, required);
        this.checkAndDestroy(world, x, y + 1, z, required);
        this.checkAndDestroy(world, x, y - 1, z, required);
        this.checkAndDestroy(world, x + 1, y, z, required);
        this.checkAndDestroy(world, x - 1, y, z, required);
    }

    private void checkAndDestroy(World world, int x, int y, int z, int required) {
        if (world.getBlockTypeIdAt(x, y, z) == required) {
            world.getBlockAt(x, y, z).setTypeId(0);
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onBlockPhysics(BlockPhysicsEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        if (cfg.activityHaltToggle) {
            event.setCancelled(true);
            return;
        }
        int id = event.getChangedTypeId();
        if (id == 13 && wcfg.noPhysicsGravel) {
            event.setCancelled(true);
            return;
        }
        if (id == 12 && wcfg.noPhysicsSand) {
            event.setCancelled(true);
            return;
        }
        if (id == 90 && wcfg.allowPortalAnywhere) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.ropeLadders && event.getBlock().getType() == Material.LADDER && event.getBlock().getRelative(0, 1, 0).getType() == Material.LADDER) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block blockPlaced = event.getBlock();
        Player player = event.getPlayer();
        World world = blockPlaced.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.useRegions) {
            Location location = blockPlaced.getLocation();
            if (!this.plugin.getGlobalRegionManager().canBuild(player, location)) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
                event.setCancelled(true);
                return;
            }
        }
        if (wcfg.getBlacklist() != null && !wcfg.getBlacklist().check(new BlockPlaceBlacklistEvent(this.plugin.wrapPlayer(player), (Vector)BukkitUtil.toVector(blockPlaced), blockPlaced.getTypeId()), false, false)) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.signChestProtection && wcfg.getChestProtection().isChest(blockPlaced.getTypeId()) && wcfg.isAdjacentChestProtected(event.getBlock(), player)) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "This spot is for a chest that you don't have permission for.");
            event.setCancelled(true);
            return;
        }
        if (wcfg.simulateSponge && blockPlaced.getTypeId() == 19) {
            if (wcfg.redstoneSponges && blockPlaced.isBlockIndirectlyPowered()) {
                return;
            }
            int ox = blockPlaced.getX();
            int oy = blockPlaced.getY();
            int oz = blockPlaced.getZ();
            SpongeUtil.clearSpongeWater(this.plugin, world, ox, oy, oz);
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {
        Block blockTo = event.getBlock();
        World world = blockTo.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.simulateSponge && wcfg.redstoneSponges) {
            int ox = blockTo.getX();
            int oy = blockTo.getY();
            int oz = blockTo.getZ();
            for (int cx = -1; cx <= 1; ++cx) {
                for (int cy = -1; cy <= 1; ++cy) {
                    for (int cz = -1; cz <= 1; ++cz) {
                        Block sponge = world.getBlockAt(ox + cx, oy + cy, oz + cz);
                        if (sponge.getTypeId() == 19 && sponge.isBlockIndirectlyPowered()) {
                            SpongeUtil.clearSpongeWater(this.plugin, world, ox + cx, oy + cy, oz + cz);
                            continue;
                        }
                        if (sponge.getTypeId() != 19 || sponge.isBlockIndirectlyPowered()) continue;
                        SpongeUtil.addSpongeWater(this.plugin, world, ox + cx, oy + cy, oz + cz);
                    }
                }
            }
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        WorldConfiguration wcfg = this.getWorldConfig(player);
        if (wcfg.signChestProtection) {
            if (event.getLine(0).equalsIgnoreCase("[Lock]")) {
                if (wcfg.isChestProtectedPlacement(event.getBlock(), player)) {
                    player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You do not own the adjacent chest.");
                    event.getBlock().breakNaturally();
                    event.setCancelled(true);
                    return;
                }
                if (event.getBlock().getTypeId() != 63) {
                    player.sendMessage((Object)((Object)ChatColor.RED) + "The [Lock] sign must be a sign post, not a wall sign.");
                    event.getBlock().breakNaturally();
                    event.setCancelled(true);
                    return;
                }
                if (!event.getLine(1).equalsIgnoreCase(player.getName())) {
                    player.sendMessage((Object)((Object)ChatColor.RED) + "The first owner line must be your name.");
                    event.getBlock().breakNaturally();
                    event.setCancelled(true);
                    return;
                }
                int below = event.getBlock().getRelative(0, -1, 0).getTypeId();
                if (below == 46 || below == 12 || below == 13 || below == 63) {
                    player.sendMessage((Object)((Object)ChatColor.RED) + "That is not a safe block that you're putting this sign on.");
                    event.getBlock().breakNaturally();
                    event.setCancelled(true);
                    return;
                }
                event.setLine(0, "[Lock]");
                player.sendMessage((Object)((Object)ChatColor.YELLOW) + "A chest or double chest above is now protected.");
            }
        } else if (!wcfg.disableSignChestProtectionCheck && event.getLine(0).equalsIgnoreCase("[Lock]")) {
            player.sendMessage((Object)((Object)ChatColor.RED) + "WorldGuard's sign chest protection is disabled.");
            event.getBlock().breakNaturally();
            event.setCancelled(true);
            return;
        }
        if (!this.plugin.getGlobalRegionManager().canBuild(player, event.getBlock())) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onLeavesDecay(LeavesDecayEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        if (cfg.activityHaltToggle) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.disableLeafDecay) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.LEAF_DECAY, event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockForm(BlockFormEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        if (cfg.activityHaltToggle) {
            event.setCancelled(true);
            return;
        }
        int type = event.getNewState().getTypeId();
        if (event instanceof EntityBlockFormEvent) {
            if (((EntityBlockFormEvent)event).getEntity() instanceof Snowman && wcfg.disableSnowmanTrails) {
                event.setCancelled(true);
                return;
            }
            return;
        }
        if (type == 79) {
            if (wcfg.disableIceFormation) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ICE_FORM, event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
        if (type == 78) {
            int targetId;
            if (wcfg.disableSnowFormation) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.allowedSnowFallOver.size() > 0 && !wcfg.allowedSnowFallOver.contains(targetId = event.getBlock().getRelative(0, -1, 0).getTypeId())) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.SNOW_FALL, event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockSpread(BlockSpreadEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        if (cfg.activityHaltToggle) {
            event.setCancelled(true);
            return;
        }
        int fromType = event.getSource().getTypeId();
        if (fromType == 40 || fromType == 39) {
            if (wcfg.disableMushroomSpread) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.MUSHROOMS, event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
        if (fromType == 2) {
            if (wcfg.disableGrassGrowth) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.GRASS_SPREAD, event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
        if (fromType == 110) {
            if (wcfg.disableMyceliumSpread) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.MYCELIUM_SPREAD, event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
        if (fromType == 106) {
            if (wcfg.disableVineGrowth) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.VINE_GROWTH, event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockFade(BlockFadeEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        switch (event.getBlock().getTypeId()) {
            case 79: {
                if (wcfg.disableIceMelting) {
                    event.setCancelled(true);
                    return;
                }
                if (!wcfg.useRegions || this.plugin.getGlobalRegionManager().allows(DefaultFlag.ICE_MELT, event.getBlock().getLocation())) break;
                event.setCancelled(true);
                return;
            }
            case 78: {
                if (wcfg.disableSnowMelting) {
                    event.setCancelled(true);
                    return;
                }
                if (!wcfg.useRegions || this.plugin.getGlobalRegionManager().allows(DefaultFlag.SNOW_MELT, event.getBlock().getLocation())) break;
                event.setCancelled(true);
                return;
            }
            case 60: {
                if (wcfg.disableSoilDehydration) {
                    event.setCancelled(true);
                    return;
                }
                if (!wcfg.useRegions || this.plugin.getGlobalRegionManager().allows(DefaultFlag.SOIL_DRY, event.getBlock().getLocation())) break;
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        if (wcfg.useRegions) {
            if (!this.plugin.getGlobalRegionManager().allows(DefaultFlag.PISTONS, event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
            for (Block block : event.getBlocks()) {
                if (this.plugin.getGlobalRegionManager().allows(DefaultFlag.PISTONS, block.getLocation())) continue;
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        if (wcfg.useRegions && event.isSticky() && (!this.plugin.getGlobalRegionManager().allows(DefaultFlag.PISTONS, event.getRetractLocation()) || !this.plugin.getGlobalRegionManager().allows(DefaultFlag.PISTONS, event.getBlock().getLocation()))) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockDispense(BlockDispenseEvent event) {
        ItemStack item;
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        if (wcfg.blockPotions.size() > 0 && (item = event.getItem()).getType() == Material.POTION && !BukkitUtil.isWaterPotion(item)) {
            Potion potion = Potion.fromDamage(BukkitUtil.getPotionEffectBits(item));
            for (PotionEffect effect : potion.getEffects()) {
                if (!potion.isSplash() || !wcfg.blockPotions.contains(effect.getType())) continue;
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBlockGrow(BlockGrowEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getBlock().getWorld());
        int type = event.getNewState().getTypeId();
        if (type == 59 || type == 141 || type == 142 || event.getNewState().getType() == Material.SUGAR_CANE_BLOCK || event.getNewState().getType() == Material.CACTUS) {
            if (wcfg.disableCropGrowth) {
                event.setCancelled(true);
                return;
            }
            if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.CROP_GROWTH, event.getBlock().getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
    }
}

