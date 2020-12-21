/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldedit.Location
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldedit.blocks.BlockType
 *  com.sk89q.worldedit.bukkit.BukkitUtil
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BlockType;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.blacklist.events.BlockBreakBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockInteractBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.BlockPlaceBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemAcquireBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemDropBlacklistEvent;
import com.sk89q.worldguard.blacklist.events.ItemUseBlacklistEvent;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.ConfigurationManager;
import com.sk89q.worldguard.bukkit.FlagStateManager;
import com.sk89q.worldguard.bukkit.WorldConfiguration;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

public class WorldGuardPlayerListener
implements Listener {
    private Pattern opPattern = Pattern.compile("^/op(?:\\s.*)?$", 2);
    private WorldGuardPlugin plugin;

    public WorldGuardPlayerListener(WorldGuardPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerEvents() {
        PluginManager pm = this.plugin.getServer().getPluginManager();
        pm.registerEvents(this, this.plugin);
        if (this.plugin.getGlobalStateManager().usePlayerMove) {
            pm.registerEvents(new PlayerMoveHandler(), this.plugin);
        }
    }

    @Deprecated
    public static boolean checkMove(WorldGuardPlugin plugin, Player player, World world, Location from, Location to) {
        return WorldGuardPlayerListener.checkMove(plugin, player, from, to);
    }

    public static boolean checkMove(WorldGuardPlugin plugin, Player player, Location from, Location to) {
        FlagStateManager.PlayerFlagState state = plugin.getFlagStateManager().getState(player);
        if (state.lastWorld != null && !state.lastWorld.equals(to.getWorld())) {
            plugin.getFlagStateManager().forget(player);
            state = plugin.getFlagStateManager().getState(player);
        }
        World world = from.getWorld();
        World toWorld = to.getWorld();
        LocalPlayer localPlayer = plugin.wrapPlayer(player);
        boolean hasBypass = plugin.getGlobalRegionManager().hasBypass(player, world);
        boolean hasRemoteBypass = plugin.getGlobalRegionManager().hasBypass(player, toWorld);
        RegionManager mgr = plugin.getGlobalRegionManager().get(toWorld);
        Vector pt = new Vector(to.getBlockX(), to.getBlockY(), to.getBlockZ());
        ApplicableRegionSet set = mgr.getApplicableRegions(pt);
        boolean entryAllowed = set.allows(DefaultFlag.ENTRY, localPlayer);
        if (!hasRemoteBypass && !entryAllowed) {
            String message = "You are not permitted to enter this area.";
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + message);
            return true;
        }
        if (state.lastExitAllowed == null) {
            state.lastExitAllowed = plugin.getGlobalRegionManager().get(world).getApplicableRegions(BukkitUtil.toVector(from)).allows(DefaultFlag.EXIT, localPlayer);
        }
        boolean exitAllowed = set.allows(DefaultFlag.EXIT, localPlayer);
        if (!hasBypass && exitAllowed && !state.lastExitAllowed.booleanValue()) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You are not permitted to leave this area.");
            return true;
        }
        String greeting = (String)set.getFlag(DefaultFlag.GREET_MESSAGE);
        String farewell = (String)set.getFlag(DefaultFlag.FAREWELL_MESSAGE);
        Boolean notifyEnter = (Boolean)set.getFlag(DefaultFlag.NOTIFY_ENTER);
        Boolean notifyLeave = (Boolean)set.getFlag(DefaultFlag.NOTIFY_LEAVE);
        GameMode gameMode = (GameMode)((Object)set.getFlag(DefaultFlag.GAME_MODE));
        if (!(state.lastFarewell == null || farewell != null && state.lastFarewell.equals(farewell))) {
            String replacedFarewell = plugin.replaceMacros(player, BukkitUtil.replaceColorMacros(state.lastFarewell));
            player.sendMessage(replacedFarewell.replaceAll("\\\\n", "\n").split("\\n"));
        }
        if (!(greeting == null || state.lastGreeting != null && state.lastGreeting.equals(greeting))) {
            String replacedGreeting = plugin.replaceMacros(player, BukkitUtil.replaceColorMacros(greeting));
            player.sendMessage(replacedGreeting.replaceAll("\\\\n", "\n").split("\\n"));
        }
        if ((notifyLeave == null || !notifyLeave.booleanValue()) && state.notifiedForLeave != null && state.notifiedForLeave.booleanValue()) {
            plugin.broadcastNotification((Object)((Object)ChatColor.GRAY) + "WG: " + (Object)((Object)ChatColor.LIGHT_PURPLE) + player.getName() + (Object)((Object)ChatColor.GOLD) + " left NOTIFY region");
        }
        if (notifyEnter != null && notifyEnter.booleanValue() && (state.notifiedForEnter == null || !state.notifiedForEnter.booleanValue())) {
            StringBuilder regionList = new StringBuilder();
            for (ProtectedRegion region : set) {
                if (regionList.length() != 0) {
                    regionList.append(", ");
                }
                regionList.append(region.getId());
            }
            plugin.broadcastNotification((Object)((Object)ChatColor.GRAY) + "WG: " + (Object)((Object)ChatColor.LIGHT_PURPLE) + player.getName() + (Object)((Object)ChatColor.GOLD) + " entered NOTIFY region: " + (Object)((Object)ChatColor.WHITE) + regionList);
        }
        if (!hasBypass && gameMode != null) {
            if (player.getGameMode() != gameMode) {
                state.lastGameMode = player.getGameMode();
                player.setGameMode(gameMode);
            } else if (state.lastGameMode == null) {
                state.lastGameMode = player.getServer().getDefaultGameMode();
            }
        } else if (state.lastGameMode != null) {
            GameMode mode = state.lastGameMode;
            state.lastGameMode = null;
            player.setGameMode(mode);
        }
        state.lastGreeting = greeting;
        state.lastFarewell = farewell;
        state.notifiedForEnter = notifyEnter;
        state.notifiedForLeave = notifyLeave;
        state.lastExitAllowed = exitAllowed;
        state.lastWorld = to.getWorld();
        state.lastBlockX = to.getBlockX();
        state.lastBlockY = to.getBlockY();
        state.lastBlockZ = to.getBlockZ();
        return false;
    }

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(player.getWorld());
        if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().hasBypass(player, player.getWorld())) {
            GameMode gameMode = (GameMode)((Object)this.plugin.getGlobalRegionManager().get(player.getWorld()).getApplicableRegions(player.getLocation()).getFlag(DefaultFlag.GAME_MODE));
            if (this.plugin.getFlagStateManager().getState((Player)player).lastGameMode != null && gameMode != null && event.getNewGameMode() != gameMode) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (cfg.activityHaltToggle) {
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Intensive server activity has been HALTED.");
            int removed = 0;
            for (Entity entity : world.getEntities()) {
                if (!BukkitUtil.isIntensiveEntity(entity)) continue;
                entity.remove();
                ++removed;
            }
            if (removed > 10) {
                this.plugin.getLogger().info("Halt-Act: " + removed + " entities (>10) auto-removed from " + player.getWorld().toString());
            }
        }
        if (wcfg.fireSpreadDisableToggle) {
            player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Fire spread is currently globally disabled for this world.");
        }
        if (!cfg.hasCommandBookGodMode() && cfg.autoGodMode && (this.plugin.inGroup(player, "wg-invincible") || this.plugin.hasPermission(player, "worldguard.auto-invincible"))) {
            cfg.enableGodMode(player);
        }
        if (this.plugin.inGroup(player, "wg-amphibious")) {
            cfg.enableAmphibiousMode(player);
        }
        if (wcfg.useRegions) {
            FlagStateManager.PlayerFlagState state = this.plugin.getFlagStateManager().getState(player);
            Location loc = player.getLocation();
            state.lastWorld = loc.getWorld();
            state.lastBlockX = loc.getBlockX();
            state.lastBlockY = loc.getBlockY();
            state.lastBlockZ = loc.getBlockZ();
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(player.getWorld());
        if (wcfg.useRegions) {
            if (!this.plugin.getGlobalRegionManager().allows(DefaultFlag.SEND_CHAT, player.getLocation())) {
                player.sendMessage((Object)((Object)ChatColor.RED) + "You don't have permission to chat in this region!");
                event.setCancelled(true);
                return;
            }
            Iterator<Player> i = event.getRecipients().iterator();
            while (i.hasNext()) {
                if (this.plugin.getGlobalRegionManager().allows(DefaultFlag.RECEIVE_CHAT, i.next().getLocation())) continue;
                i.remove();
            }
            if (event.getRecipients().size() == 0) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        String hostKey = cfg.hostKeys.get(player.getName().toLowerCase());
        if (hostKey != null) {
            String hostname = event.getHostname();
            int colonIndex = hostname.indexOf(58);
            if (colonIndex != -1) {
                hostname = hostname.substring(0, colonIndex);
            }
            if (!hostname.equals(hostKey)) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "You did not join with the valid host key!");
                this.plugin.getLogger().warning("WorldGuard host key check: " + player.getName() + " joined with '" + hostname + "' but '" + hostKey + "' was expected. Kicked!");
                return;
            }
        }
        if (cfg.deopOnJoin) {
            player.setOp(false);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.useRegions) {
            boolean hasBypass = this.plugin.getGlobalRegionManager().hasBypass(player, world);
            FlagStateManager.PlayerFlagState state = this.plugin.getFlagStateManager().getState(player);
            if (state.lastWorld != null && !hasBypass) {
                LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
                RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
                Location loc = player.getLocation();
                Vector pt = new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                ApplicableRegionSet set = mgr.getApplicableRegions(pt);
                if (state.lastExitAllowed == null) {
                    state.lastExitAllowed = set.allows(DefaultFlag.EXIT, localPlayer);
                }
                if (!(state.lastExitAllowed.booleanValue() && set.allows(DefaultFlag.ENTRY, localPlayer) || !state.lastWorld.equals(world))) {
                    Location newLoc = new Location(world, (double)state.lastBlockX + 0.5, state.lastBlockY, (double)state.lastBlockZ + 0.5);
                    player.teleport(newLoc);
                }
            }
        }
        cfg.forgetPlayer(this.plugin.wrapPlayer(player));
        this.plugin.forgetPlayer(player);
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        ItemStack item = player.getItemInHand();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.getBlacklist() != null && !wcfg.getBlacklist().check(new ItemUseBlacklistEvent(this.plugin.wrapPlayer(player), BukkitUtil.toVector(player.getLocation()), item.getTypeId()), false, false)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item;
        Player player = event.getPlayer();
        World world = player.getWorld();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            this.handleBlockRightClick(event);
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            this.handleAirRightClick(event);
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            this.handleBlockLeftClick(event);
        } else if (event.getAction() == Action.LEFT_CLICK_AIR) {
            this.handleAirLeftClick(event);
        } else if (event.getAction() == Action.PHYSICAL) {
            this.handlePhysicalInteract(event);
        }
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.removeInfiniteStacks && !this.plugin.hasPermission(player, "worldguard.override.infinite-stack")) {
            int slot = player.getInventory().getHeldItemSlot();
            ItemStack heldItem = player.getInventory().getItem(slot);
            if (heldItem != null && heldItem.getAmount() < 0) {
                player.getInventory().setItem(slot, null);
                player.sendMessage((Object)((Object)ChatColor.RED) + "Infinite stack removed.");
            }
        }
        if (wcfg.blockPotions.size() > 0 && (item = event.getItem()) != null && item.getType() == Material.POTION && !BukkitUtil.isWaterPotion(item)) {
            PotionEffect blockedEffect = null;
            Potion potion = Potion.fromDamage(BukkitUtil.getPotionEffectBits(item));
            for (PotionEffect effect : potion.getEffects()) {
                if (!wcfg.blockPotions.contains(effect.getType())) continue;
                blockedEffect = effect;
                break;
            }
            if (blockedEffect != null) {
                if (this.plugin.hasPermission(player, "worldguard.override.potions")) {
                    if (potion.isSplash() && wcfg.blockPotionsAlways) {
                        player.sendMessage((Object)((Object)ChatColor.RED) + "Sorry, potions with " + blockedEffect.getType().getName() + " can't be thrown, even if you have a permission to bypass it, due to limitations (and because overly-reliable potion blocking is on).");
                        event.setUseItemInHand(Event.Result.DENY);
                        return;
                    }
                } else {
                    player.sendMessage((Object)((Object)ChatColor.RED) + "Sorry, potions with " + blockedEffect.getType().getName() + " are presently disabled.");
                    event.setUseItemInHand(Event.Result.DENY);
                    return;
                }
            }
        }
    }

    private void handleAirLeftClick(PlayerInteractEvent event) {
    }

    private void handleBlockLeftClick(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        int type = block.getTypeId();
        World world = player.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.useRegions) {
            BlockVector pt = BukkitUtil.toVector(block);
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
            ApplicableRegionSet set = mgr.getApplicableRegions((Vector)pt);
            LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
            if (type == 122 && !this.plugin.getGlobalRegionManager().hasBypass(player, world) && !set.canBuild(localPlayer)) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not allowed to move dragon eggs here!");
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (block.getRelative(event.getBlockFace()).getTypeId() == 51 && !this.plugin.getGlobalRegionManager().hasBypass(player, world) && !mgr.getApplicableRegions(block.getRelative(event.getBlockFace()).getLocation()).canBuild(localPlayer)) {
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
        }
        if (type == 46 && player.getItemInHand().getTypeId() == 259 && wcfg.getBlacklist() != null && !wcfg.getBlacklist().check(new BlockBreakBlacklistEvent(this.plugin.wrapPlayer(player), (Vector)BukkitUtil.toVector(event.getClickedBlock()), event.getClickedBlock().getTypeId()), false, false)) {
            event.setUseInteractedBlock(Event.Result.DENY);
            event.setUseItemInHand(Event.Result.DENY);
            event.setCancelled(true);
            return;
        }
    }

    private void handleAirRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        ItemStack item = player.getItemInHand();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.getBlacklist() != null && !wcfg.getBlacklist().check(new ItemUseBlacklistEvent(this.plugin.wrapPlayer(player), BukkitUtil.toVector(player.getLocation()), item.getTypeId()), false, false)) {
            event.setCancelled(true);
            event.setUseItemInHand(Event.Result.DENY);
            return;
        }
    }

    private void handleBlockRightClick(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Block block = event.getClickedBlock();
        World world = block.getWorld();
        int type = block.getTypeId();
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if ((type == 54 || type == 84 || type == 23 || type == 61 || type == 62 || type == 117 || type == 116) && wcfg.removeInfiniteStacks && !this.plugin.hasPermission(player, "worldguard.override.infinite-stack")) {
            for (int slot = 0; slot < 40; ++slot) {
                ItemStack heldItem = player.getInventory().getItem(slot);
                if (heldItem == null || heldItem.getAmount() >= 0) continue;
                player.getInventory().setItem(slot, null);
                player.sendMessage((Object)((Object)ChatColor.RED) + "Infinite stack in slot #" + slot + " removed.");
            }
        }
        if (wcfg.useRegions) {
            BlockVector pt = BukkitUtil.toVector(block);
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
            Block placedIn = block.getRelative(event.getBlockFace());
            ApplicableRegionSet set = mgr.getApplicableRegions((Vector)pt);
            ApplicableRegionSet placedInSet = mgr.getApplicableRegions(placedIn.getLocation());
            LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
            if (item.getTypeId() == wcfg.regionWand && this.plugin.hasPermission(player, "worldguard.region.wand")) {
                if (set.size() > 0) {
                    player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Can you build? " + (set.canBuild(localPlayer) ? "Yes" : "No"));
                    StringBuilder str = new StringBuilder();
                    Iterator<ProtectedRegion> it = set.iterator();
                    while (it.hasNext()) {
                        str.append(it.next().getId());
                        if (!it.hasNext()) continue;
                        str.append(", ");
                    }
                    player.sendMessage((Object)((Object)ChatColor.YELLOW) + "Applicable regions: " + str.toString());
                } else {
                    player.sendMessage((Object)((Object)ChatColor.YELLOW) + "WorldGuard: No defined regions here!");
                }
                event.setCancelled(true);
                return;
            }
            if (item.getTypeId() == 46 && !this.plugin.getGlobalRegionManager().hasBypass(player, world) && !placedInSet.allows(DefaultFlag.TNT, localPlayer)) {
                event.setUseItemInHand(Event.Result.DENY);
                event.setCancelled(true);
            }
            if (!(item.getTypeId() != 44 && item.getTypeId() != 126 || this.plugin.getGlobalRegionManager().hasBypass(player, world))) {
                boolean cancel = false;
                if (block.getTypeId() == item.getTypeId() && !set.canBuild(localPlayer)) {
                    cancel = true;
                } else if (!placedInSet.canBuild(localPlayer)) {
                    cancel = true;
                }
                if (cancel) {
                    player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
                    event.setCancelled(true);
                    return;
                }
            }
            if (item.getTypeId() == 355) {
                int i;
                double yaw = (double)(player.getLocation().getYaw() * 4.0f / 360.0f) + 0.5;
                int i1 = (yaw < (double)(i = (int)yaw) ? i - 1 : i) & 3;
                int b0 = 0;
                int b1 = 0;
                if (i1 == 0) {
                    b1 = 1;
                }
                if (i1 == 1) {
                    b0 = -1;
                }
                if (i1 == 2) {
                    b1 = -1;
                }
                if (i1 == 3) {
                    b0 = 1;
                }
                Location headLoc = placedIn.getRelative(b0, 0, b1).getLocation();
                if (!this.plugin.getGlobalRegionManager().hasBypass(player, world) && !mgr.getApplicableRegions(headLoc).canBuild(localPlayer)) {
                    player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
                    event.setCancelled(true);
                    return;
                }
            }
            if (block.getTypeId() == 36 && !this.plugin.getGlobalRegionManager().hasBypass(player, world) && !set.canBuild(localPlayer)) {
                event.setUseInteractedBlock(Event.Result.DENY);
            }
            if (!(item.getTypeId() != 324 && item.getTypeId() != 330 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || placedInSet.canBuild(localPlayer))) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
                event.setCancelled(true);
                return;
            }
            if (!(item.getTypeId() != 385 && item.getTypeId() != 259 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || this.plugin.canBuild(player, placedIn) || placedInSet.allows(DefaultFlag.LIGHTER))) {
                event.setCancelled(true);
                event.setUseItemInHand(Event.Result.DENY);
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not allowed to use that here.");
                return;
            }
            if (item.getTypeId() == 381 && block.getTypeId() == 120 && !this.plugin.getGlobalRegionManager().hasBypass(player, world) && !set.canBuild(localPlayer)) {
                event.setCancelled(true);
                event.setUseItemInHand(Event.Result.DENY);
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not allowed to use that here.");
                return;
            }
            if (item.getTypeId() == 351 && item.getData() != null) {
                if (item.getData().getData() == 15 && (type == 2 || type == 6 || type == 59 || type == 39 || type == 40 || type == 104 || type == 105 || type == 142 || type == 141 || type == 127 || type == 31)) {
                    if (!this.plugin.getGlobalRegionManager().hasBypass(player, world) && !set.canBuild(localPlayer)) {
                        event.setCancelled(true);
                        event.setUseItemInHand(Event.Result.DENY);
                        player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not allowed to use that here.");
                        return;
                    }
                } else if (item.getData().getData() == 3 && !this.plugin.getGlobalRegionManager().hasBypass(player, world) && !set.canBuild(localPlayer) && event.getBlockFace() != BlockFace.DOWN && event.getBlockFace() != BlockFace.UP) {
                    event.setCancelled(true);
                    event.setUseItemInHand(Event.Result.DENY);
                    player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not allowed to plant that here.");
                    return;
                }
            }
            if (!(type != 140 || item.getTypeId() != 38 && item.getTypeId() != 37 && item.getTypeId() != 6 && item.getTypeId() != 40 && item.getTypeId() != 39 && item.getTypeId() != 81 && item.getTypeId() != 31 && item.getTypeId() != 32 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || set.canBuild(localPlayer))) {
                event.setUseItemInHand(Event.Result.DENY);
                event.setCancelled(true);
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not allowed to plant that here.");
                return;
            }
            if (type == 26 && !this.plugin.getGlobalRegionManager().hasBypass(player, world) && !set.allows(DefaultFlag.SLEEP, localPlayer)) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not allowed to use that bed.");
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (!(type != 54 && type != 84 && type != 23 && type != 61 && type != 62 && type != 117 && type != 146 && type != 154 && type != 158 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || set.canBuild(localPlayer) || set.allows(DefaultFlag.CHEST_ACCESS, localPlayer))) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission to open that in this area.");
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (type == 122 && !this.plugin.getGlobalRegionManager().hasBypass(player, world) && !set.canBuild(localPlayer)) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not allowed to move dragon eggs here!");
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (!(type != 69 && type != 77 && type != 143 && type != 25 && type != 93 && type != 94 && type != 64 && type != 107 && type != 84 && type != 23 && type != 61 && type != 62 && type != 58 && type != 117 && type != 116 && type != 118 && type != 130 && type != 138 && type != 145 && type != 154 && type != 158 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || set.canBuild(localPlayer) || set.allows(DefaultFlag.USE, localPlayer))) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission to use that in this area.");
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (!(type != 96 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || set.canBuild(localPlayer) || set.allows(DefaultFlag.USE_TRAPDOOR, localPlayer))) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission to use that in this area.");
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (!(type != 93 && type != 94 && type != 149 && type != 150 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || set.canBuild(localPlayer))) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission to use that in this area.");
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (!(type != 92 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || set.canBuild(localPlayer) || set.allows(DefaultFlag.USE, localPlayer))) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not invited to this tea party!");
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (!(!BlockType.isRailBlock((int)type) || item.getTypeId() != 328 && item.getTypeId() != 343 && item.getTypeId() != 342 && item.getTypeId() != 407 && item.getTypeId() != 408 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || placedInSet.canBuild(localPlayer) || placedInSet.allows(DefaultFlag.PLACE_VEHICLE, localPlayer))) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission to place vehicles here.");
                event.setUseItemInHand(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (!(item.getTypeId() != 333 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || placedInSet.canBuild(localPlayer) || placedInSet.allows(DefaultFlag.PLACE_VEHICLE, localPlayer))) {
                player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission to place vehicles here.");
                event.setUseItemInHand(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
        }
        if (wcfg.getBlacklist() != null) {
            if ((player.isSneaking() || type != 54 && type != 23 && type != 61 && type != 62 && type != 117 && type != 116 && type != 145 && type != 130 && type != 146 && type != 154 && type != 158) && !wcfg.getBlacklist().check(new ItemUseBlacklistEvent(this.plugin.wrapPlayer(player), (Vector)BukkitUtil.toVector(block), item.getTypeId()), false, false)) {
                event.setUseItemInHand(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (!wcfg.getBlacklist().check(new BlockInteractBlacklistEvent(this.plugin.wrapPlayer(player), (Vector)BukkitUtil.toVector(block), block.getTypeId()), false, false)) {
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
            if (item.getTypeId() == 46) {
                Block placedOn = block.getRelative(event.getBlockFace());
                if (!wcfg.getBlacklist().check(new BlockPlaceBlacklistEvent(this.plugin.wrapPlayer(player), (Vector)BukkitUtil.toVector(placedOn), item.getTypeId()), false, false)) {
                    event.setUseItemInHand(Event.Result.DENY);
                    event.setCancelled(true);
                    return;
                }
            }
        }
        if ((type == 54 || type == 23 || type == 61 || type == 62 || type == 116 || type == 117 || type == 146 || type == 154 || type == 158) && wcfg.isChestProtected(block, player)) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "The chest is protected.");
            event.setUseInteractedBlock(Event.Result.DENY);
            event.setCancelled(true);
            return;
        }
    }

    private void handlePhysicalInteract(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        int type = block.getTypeId();
        World world = player.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (block.getTypeId() == 60 && wcfg.disablePlayerCropTrampling) {
            event.setCancelled(true);
            return;
        }
        if (wcfg.useRegions) {
            BlockVector pt = BukkitUtil.toVector(block);
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
            ApplicableRegionSet set = mgr.getApplicableRegions((Vector)pt);
            LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
            if (!(type != 70 && type != 72 && type != 132 && type != 147 && type != 148 || this.plugin.getGlobalRegionManager().hasBypass(player, world) || set.canBuild(localPlayer) || set.allows(DefaultFlag.USE, localPlayer))) {
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getPlayer().getWorld());
        Player player = event.getPlayer();
        if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().hasBypass(player, player.getWorld()) && !this.plugin.getGlobalRegionManager().allows(DefaultFlag.ITEM_DROP, player.getLocation())) {
            event.setCancelled(true);
            player.sendMessage((Object)((Object)ChatColor.RED) + "You don't have permission to do that in this area.");
        }
        if (wcfg.getBlacklist() != null) {
            Item ci = event.getItemDrop();
            if (!wcfg.getBlacklist().check(new ItemDropBlacklistEvent(this.plugin.wrapPlayer(event.getPlayer()), BukkitUtil.toVector(ci.getLocation()), ci.getItemStack().getTypeId()), false, false)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(event.getPlayer().getWorld());
        if (wcfg.getBlacklist() != null) {
            Item ci = event.getItem();
            if (!wcfg.getBlacklist().check(new ItemAcquireBlacklistEvent(this.plugin.wrapPlayer(event.getPlayer()), BukkitUtil.toVector(ci.getLocation()), ci.getItemStack().getTypeId()), false, true)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (!this.plugin.getGlobalRegionManager().canBuild(player, event.getBlockClicked().getRelative(event.getBlockFace())) && event.getItemStack().getTypeId() != 335) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
            event.setCancelled(true);
            return;
        }
        if (wcfg.getBlacklist() != null && !wcfg.getBlacklist().check(new ItemUseBlacklistEvent(this.plugin.wrapPlayer(player), BukkitUtil.toVector(player.getLocation()), event.getBucket().getId()), false, false)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerFish(PlayerFishEvent event) {
        WorldConfiguration wcfg = this.plugin.getGlobalStateManager().get(event.getPlayer().getWorld());
        if (wcfg.disableExpDrops || !this.plugin.getGlobalRegionManager().allows(DefaultFlag.EXP_DROPS, event.getPlayer().getLocation())) {
            event.setExpToDrop(0);
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (!this.plugin.getGlobalRegionManager().canBuild(player, event.getBlockClicked().getRelative(event.getBlockFace()))) {
            player.sendMessage((Object)((Object)ChatColor.DARK_RED) + "You don't have permission for this area.");
            event.setCancelled(true);
            return;
        }
        if (wcfg.getBlacklist() != null && !wcfg.getBlacklist().check(new ItemUseBlacklistEvent(this.plugin.wrapPlayer(player), BukkitUtil.toVector(player.getLocation()), event.getBucket().getId()), false, false)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(player.getWorld());
        if (wcfg.useRegions) {
            LocalPlayer localPlayer;
            Vector pt = BukkitUtil.toVector(location);
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(player.getWorld());
            ApplicableRegionSet set = mgr.getApplicableRegions(pt);
            com.sk89q.worldedit.Location spawn = (com.sk89q.worldedit.Location)set.getFlag(DefaultFlag.SPAWN_LOC, localPlayer = this.plugin.wrapPlayer(player));
            if (spawn != null) {
                event.setRespawnLocation(com.sk89q.worldedit.bukkit.BukkitUtil.toLocation((com.sk89q.worldedit.Location)spawn));
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onItemHeldChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(player.getWorld());
        if (wcfg.removeInfiniteStacks && !this.plugin.hasPermission(player, "worldguard.override.infinite-stack")) {
            int newSlot = event.getNewSlot();
            ItemStack heldItem = player.getInventory().getItem(newSlot);
            if (heldItem != null && heldItem.getAmount() < 0) {
                player.getInventory().setItem(newSlot, null);
                player.sendMessage((Object)((Object)ChatColor.RED) + "Infinite stack removed.");
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(player.getWorld());
        if (wcfg.useRegions) {
            Vector pt = BukkitUtil.toVector(location);
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(player.getWorld());
            ApplicableRegionSet set = mgr.getApplicableRegions(pt);
            if (!this.plugin.getGlobalRegionManager().hasBypass(player, player.getWorld()) && !set.allows(DefaultFlag.SLEEP, this.plugin.wrapPlayer(player))) {
                event.setCancelled(true);
                player.sendMessage("This bed doesn't belong to you!");
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        World world = event.getFrom().getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.useRegions) {
            boolean result;
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(event.getFrom().getWorld());
            Vector pt = new Vector(event.getTo().getBlockX(), event.getTo().getBlockY(), event.getTo().getBlockZ());
            Vector ptFrom = new Vector(event.getFrom().getBlockX(), event.getFrom().getBlockY(), event.getFrom().getBlockZ());
            ApplicableRegionSet set = mgr.getApplicableRegions(pt);
            ApplicableRegionSet setFrom = mgr.getApplicableRegions(ptFrom);
            LocalPlayer localPlayer = this.plugin.wrapPlayer(event.getPlayer());
            if (cfg.usePlayerTeleports && (result = WorldGuardPlayerListener.checkMove(this.plugin, event.getPlayer(), event.getFrom(), event.getTo()))) {
                event.setCancelled(true);
                return;
            }
            if (!(event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL || this.plugin.getGlobalRegionManager().hasBypass(event.getPlayer(), world) || set.allows(DefaultFlag.ENDERPEARL, localPlayer) && setFrom.allows(DefaultFlag.ENDERPEARL, localPlayer))) {
                event.getPlayer().sendMessage((Object)((Object)ChatColor.DARK_RED) + "You're not allowed to go there.");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        LocalPlayer localPlayer = this.plugin.wrapPlayer(player);
        World world = player.getWorld();
        ConfigurationManager cfg = this.plugin.getGlobalStateManager();
        WorldConfiguration wcfg = cfg.get(world);
        if (wcfg.useRegions && !this.plugin.getGlobalRegionManager().hasBypass(player, world)) {
            int i;
            Vector pt = BukkitUtil.toVector(player.getLocation());
            RegionManager mgr = this.plugin.getGlobalRegionManager().get(world);
            ApplicableRegionSet set = mgr.getApplicableRegions(pt);
            String usedCommand = event.getMessage().toLowerCase();
            Set allowedCommands = (Set)set.getFlag(DefaultFlag.ALLOWED_CMDS, localPlayer);
            Set blockedCommands = (Set)set.getFlag(DefaultFlag.BLOCKED_CMDS, localPlayer);
            String result = "";
            String[] usedParts = usedCommand.split(" ");
            if (blockedCommands != null) {
                block0: for (String blockedCommand : blockedCommands) {
                    String[] blockedParts = blockedCommand.split(" ");
                    for (i = 0; i < blockedParts.length && i < usedParts.length; ++i) {
                        if (!blockedParts[i].equalsIgnoreCase(usedParts[i])) continue;
                        if (i + 1 == blockedParts.length) {
                            result = blockedCommand;
                            break block0;
                        }
                        if (i + 1 == usedParts.length) continue block0;
                    }
                }
            }
            if (allowedCommands != null) {
                block2: for (String allowedCommand : allowedCommands) {
                    String[] allowedParts = allowedCommand.split(" ");
                    for (i = 0; i < allowedParts.length && i < usedParts.length; ++i) {
                        if (allowedParts[i].equalsIgnoreCase(usedParts[i])) {
                            if (i + 1 == allowedParts.length) {
                                result = "";
                                break block2;
                            }
                            if (i + 1 != usedParts.length) continue;
                            result = usedCommand;
                            continue block2;
                        }
                        result = usedCommand;
                        continue block2;
                    }
                }
            }
            if (!result.isEmpty()) {
                player.sendMessage((Object)((Object)ChatColor.RED) + result + " is not allowed in this area.");
                event.setCancelled(true);
                return;
            }
        }
        if (cfg.blockInGameOp && this.opPattern.matcher(event.getMessage()).matches()) {
            player.sendMessage((Object)((Object)ChatColor.RED) + "/op can only be used in console (as set by a WG setting).");
            event.setCancelled(true);
            return;
        }
    }

    class PlayerMoveHandler
    implements Listener {
        PlayerMoveHandler() {
        }

        @EventHandler(priority=EventPriority.HIGH)
        public void onPlayerMove(PlayerMoveEvent event) {
            boolean result;
            Player player = event.getPlayer();
            World world = player.getWorld();
            ConfigurationManager cfg = WorldGuardPlayerListener.this.plugin.getGlobalStateManager();
            WorldConfiguration wcfg = cfg.get(world);
            if (player.getVehicle() != null) {
                return;
            }
            if (wcfg.useRegions && (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockY() != event.getTo().getBlockY() || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) && (result = WorldGuardPlayerListener.checkMove(WorldGuardPlayerListener.this.plugin, player, event.getFrom(), event.getTo()))) {
                Location newLoc = event.getFrom();
                newLoc.setX((double)newLoc.getBlockX() + 0.5);
                newLoc.setY(newLoc.getBlockY());
                newLoc.setZ((double)newLoc.getBlockZ() + 0.5);
                event.setTo(newLoc);
            }
        }
    }
}

