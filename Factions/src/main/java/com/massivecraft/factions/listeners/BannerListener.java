/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.event.FPlayerLeaveEvent
 *  com.massivecraft.factions.event.FactionDisbandEvent
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  com.wimbli.WorldBorder.BorderData
 *  com.wimbli.WorldBorder.WorldBorder
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.bukkit.DyeColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.block.banner.Pattern
 *  org.bukkit.block.banner.PatternType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.inventory.CraftItemEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerTeleportEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.BannerMeta
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions.listeners;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.manager.BannerManager;
import com.massivecraft.factions.struct.FactionBanner;
import com.massivecraft.factions.struct.Relation;
import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.WorldBorder;
import java.util.List;

import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.plugin.Plugin;

public class BannerListener
implements Listener {
    private int BANNER_SOUL_COST;
    private BlockPlaceEvent waitingForMonitorCall = null;
    private List<String> bannerAllowedWorlds = Lists.newArrayList("world", "world_nether", "world_end", "world_arena", "world_event");

    public BannerListener() {
        this.BANNER_SOUL_COST = P.getP().getConfig().getInt("soulCost");
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onFactionDisband(FactionDisbandEvent event) {
        P.getP().getBannerManager().getBannerDesignMap().remove(event.getFaction().getId());
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onFactionDisband(FPlayerLeaveEvent event) {
        if (event.getFaction().getFPlayers().size() <= 1 && event.getFaction().getFPlayers().contains((Object)event.getFPlayer())) {
            P.getP().getBannerManager().getBannerDesignMap().remove(event.getFaction().getId());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=false)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material type = block.getType();
        if (type == Material.STANDING_BANNER || type == Material.WALL_BANNER) {
            for (FactionBanner banner : P.getP().getBannerManager().getFactionBannerMap().values()) {
                Location l = banner.getActiveLocation();
                if (l == null || l.getBlockX() != block.getX() || l.getBlockY() != block.getY() || l.getBlockZ() != block.getZ()) continue;
                FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)event.getPlayer());
                if (fp.getFaction().getRelationTo((RelationParticipator)banner.getFaction()).isAtLeast(Relation.ALLY)) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot destroy " + banner.getWhoPlacedUsername() + "'s Faction Banner!");
                    event.getPlayer().sendMessage(CC.Gray + "It will despawn in: " + banner.getSecondsLeft() + "s!");
                    return;
                }
                event.setCancelled(true);
                banner.removeBanner();
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getPlayer().hasMetadata("bannerTp") && e.getCause() == PlayerTeleportEvent.TeleportCause.PLUGIN) {
            e.getPlayer().removeMetadata("bannerTp", P.getP());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=false)
    public void onPlayerPlace(BlockPlaceEvent event) {
        NBTWrapper wrapper;
        ItemStack item = event.getItemInHand();
        if (item != null && item.getType() == BannerManager.bannerMaterial && (wrapper = new NBTWrapper(item)).getString("cosmicType").equals("FACTION_BANNER")) {
            event.setCancelled(true);
            this.waitingForMonitorCall = event;
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item != null && item.getType() == BannerManager.bannerMaterial && new NBTWrapper(item).getString("cosmicType").equals("FACTION_BANNER")) {
            Block placingOn;
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK && (placingOn = event.getClickedBlock().getRelative(event.getBlockFace())).getType() != Material.AIR) {
                event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You must place a Faction Banner in an valid location outside of spawn!");
                event.setCancelled(true);
                return;
            }
            event.setUseItemInHand(Event.Result.ALLOW);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=false)
    public void onPlayerPlaceMonitor(BlockPlaceEvent event) {
        if (!event.isCancelled() || !event.equals((Object)this.waitingForMonitorCall)) {
            return;
        }
        this.waitingForMonitorCall = null;
        Faction fac = FactionUtils.getFaction((Player)event.getPlayer(), (boolean)false);
        if (!FactionUtils.isNormal((Faction)fac)) {
            event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You must be in a faction to place a Faction Banner!");
            return;
        }
        Block placedOn = event.getBlockPlaced();
        if (!this.bannerAllowedWorlds.contains(placedOn.getWorld().getName())) {
            event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot place Faction Banners in this world!");
            event.getPlayer().sendMessage(CC.Gray + "Allowed Worlds: [overworld, nether, end, battlefield]");
            return;
        }
        if (!WorldGuardUtils.isPvPEnabled((Location)placedOn.getLocation())) {
            event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You can only place Faction Banners in PvP-enabled areas!");
            return;
        }
        Location playerLoc = event.getPlayer().getLocation();
        if (playerLoc.getBlockX() != placedOn.getX() || playerLoc.getBlockZ() != placedOn.getZ() || Math.abs(playerLoc.getBlockY() - placedOn.getY()) > 1) {
            event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You must place faction banners directly underneath you.");
            return;
        }
        BannerManager manager = P.getP().getBannerManager();
        FactionBanner banner = manager.getFactionBannerMap().get(fac.getId());
        if (banner != null && !banner.hasExpired()) {
            event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "Your faction already has an active /f banner placed!");
            event.getPlayer().sendMessage(CC.Gray + "You can place a new /f banner in: " + banner.getSecondsLeft() + "s");
            return;
        }
        BorderData worldBorder = WorldBorder.plugin.getWorldBorder(event.getPlayer().getWorld().getName());
        if (worldBorder != null && !worldBorder.insideBorder(placedOn.getLocation())) {
            event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot place Faction Banners outside the world border!");
            return;
        }
        boolean freePlace = false;
        NBTWrapper item = new NBTWrapper(event.getItemInHand());
        NBTTagCompoundBuilder cosmicData = item.getconaxData();
        if (cosmicData != null && cosmicData.hasKey("noSoulCost")) {
            freePlace = true;
        }
        if (this.BANNER_SOUL_COST > 0 && !freePlace && !KillTrackerAPI.hasSoulAmount((Player)event.getPlayer(), (int)this.BANNER_SOUL_COST)) {
            event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You do not have enough souls to place this /f banner!");
            event.getPlayer().sendMessage(CC.RedB + "COST: " + CC.Gray + "500 souls");
            return;
        }
        Material type = placedOn.getType();
        if (!(type != Material.AIR && type != Material.STANDING_BANNER && type != Material.WALL_BANNER || placedOn.getRelative(BlockFace.UP).getType() != Material.AIR && placedOn.getY() != 255)) {
            if (this.BANNER_SOUL_COST > 0 && !freePlace) {
                KillTrackerAPI.removeSoulsFromGems((Player)event.getPlayer(), (int)this.BANNER_SOUL_COST);
                event.getPlayer().sendMessage(CC.RedB + " -" + this.BANNER_SOUL_COST + " souls");
            }
            event.getPlayer().sendMessage(CC.DarkPurpleB + "(!) " + CC.DarkPurple + "You have placed a Faction Banner!");
            event.getPlayer().sendMessage(CC.Gray + "Faction Members have " + FactionBanner.secondCooldown + "s to teleport to it using " + CC.LightPurple + "/f assist");
            banner = P.getP().getBannerManager().getFactionBannerMap().computeIfAbsent(fac.getId(), e -> new FactionBanner());
            banner.removeBanner();
            banner.placeBanner(fac, event.getPlayer(), placedOn.getLocation());
            event.setCancelled(false);
            return;
        }
        event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You must place your /f banner in an valid location!");
        event.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCraft(CraftItemEvent event) {
        ItemStack banner;
        BannerMeta bannerMeta;
        if (event.getRecipe().getResult().getType() == Material.BANNER && !event.getWhoClicked().isOp() && (this.isBlacklistedBanner(bannerMeta = (BannerMeta)(banner = event.getCurrentItem()).getItemMeta()) || banner.getDurability() == 0 || banner.getDurability() == 7 || banner.getDurability() == 8)) {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot craft this style/color of banner!");
            ((Player)event.getWhoClicked()).sendMessage(CC.Gray + "Some banner styles/colors are limited edition and cannot be crafted.");
        }
    }

    private boolean isBlacklistedBanner(BannerMeta bannerMeta) {
        if (bannerMeta.getBaseColor() == DyeColor.BLACK || bannerMeta.getBaseColor() == DyeColor.GRAY || bannerMeta.getBaseColor() == DyeColor.SILVER) {
            return true;
        }
        for (Pattern p : bannerMeta.getPatterns()) {
            if (p.getColor() != DyeColor.BLACK && p.getColor() != DyeColor.GRAY && p.getColor() != DyeColor.SILVER && p.getPattern() != PatternType.SKULL) continue;
            return true;
        }
        return false;
    }

    public List<String> getBannerAllowedWorlds() {
        return this.bannerAllowedWorlds;
    }
}

