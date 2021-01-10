/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.MaskUtils
 *  com.cosmicpvp.cosmicutils.utils.ParticleEffect
 *  com.cosmicpvp.cosmicutils.utils.TimeUtils
 *  com.cosmicpvp.cosmicutils.utils.UpgradeUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  com.gmail.filoghost.holograms.api.Hologram
 *  com.gmail.filoghost.holograms.api.HolographicDisplaysAPI
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  me.astropvp.astropvpskins.AstroItemSkinsAPI
 *  me.astropvp.astropvpskins.enums.SkinType
 *  me.ifamasssxd.cosmichcf.api.HCFOutpostAPI
 *  me.ifamasssxd.cosmichcf.struct.OutpostType
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.block.BlockDamageEvent
 *  org.bukkit.event.block.BlockPhysicsEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerPortalEvent
 *  org.bukkit.event.player.PlayerTeleportEvent$TeleportCause
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package com.compilex.CosmicDungeons.listeners;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonState;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.compilex.CosmicDungeons.menus.DungeonMasterKeyMenu;
import com.compilex.CosmicDungeons.utils.PortalTrinketUtils;
import com.cosmicpvp.cosmicutils.utils.*;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import net.astropvp.core.utils.TitleUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;

public class DungeonPortalListener
implements Listener {
    public HashSet<Location> portalBlocks = new HashSet();
    public static List<Integer> debuffs = Lists.newArrayList();
    public boolean isHeroicDungeon = false;

    public static List<Integer> getDebuffs() {
        return debuffs;
    }

    @EventHandler(priority=EventPriority.LOW, ignoreCancelled=true)
    public void onPlayerEnterDungeonPortal(PlayerPortalEvent e) {
        if (e.getPlayer().getLocation().add(0.0, 1.0, 0.0).getBlock().hasMetadata("dungeonPortalBlock")) {
            int dungeonID = ((MetadataValue)e.getPlayer().getLocation().add(0.0, 1.0, 0.0).getBlock().getMetadata("dungeonPortalBlock").get(0)).asInt();
            Dungeon dungeon = DungeonsAPI.get().getDungeon(dungeonID);
            if (dungeon.getDungeonStatus() == DungeonState.LOADING) {
                DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(e.getPlayer());
                e.setCancelled(true);
                if (dungeon.isKeyRequired() && !dungeon.playerHasKey(e.getPlayer())) {
                    e.getPlayer().sendMessage("");
                    e.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You do not have the required key in your inventory to the " + dungeon.getDungeonName() + CC.Red + " dungeon. Purchase it from the Dungeon Master at /spawn!");
                    e.getPlayer().sendMessage("");
                    return;
                }
                if (!DungeonMasterKeyMenu.canBuyKey(dungeon.dungeonType) && !e.getPlayer().isOp()) {
                    Bukkit.getLogger().info("[CosmicDungeons (Exploit)] Player " + (Object)e.getPlayer() + " attempted to enter a dungeon portal at " + (Object)e.getFrom() + " to dungeon " + dungeon + " without keys being unlocked, playerInventory: " + (Object)e.getPlayer().getInventory());
                    e.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "Hmm... well that doesn't seem quite right. Please contact a staff member for further assistance.");
                    return;
                }
                if (dp != null && !dungeon.isGlobalPortal() && dungeon.isDestroyedOutpost() && !DungeonType.ABANDONED_SHIP.getDungeonInstance().hasCompletedRecently(dp, 24L)) {
                    e.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You must complete the " + DungeonType.ABANDONED_SHIP.getDungeonName() + CC.Red + " Dungeon within the last 24h to enter this dungeon!");
                    return;
                }
                if (e.getPlayer().hasMetadata("lastDungeonDeath") && System.currentTimeMillis() - ((MetadataValue)e.getPlayer().getMetadata("lastDungeonDeath").get(0)).asLong() <= 100000L) {
                    e.getPlayer().sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You have died in this dungeon instance, you are locked out of this portal.");
                    return;
                }
                if (dungeon.getProtectedFor() != null && !dungeon.getProtectedFor().equals("-1")) {
                    Faction facProtectedFor = FactionUtils.getFactionById((String)dungeon.getProtectedFor());
                    Relation relation = FactionUtils.getFaction((Player)e.getPlayer()).getRelationTo((RelationParticipator)facProtectedFor);
                    if (FactionUtils.getFaction((Player)e.getPlayer()).getId().equals("0") || relation != Relation.ALLY && relation != Relation.MEMBER) {
                        e.getPlayer().sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "This portal is protected. You must be an ally or member of " + facProtectedFor.getTag() + " to enter.");
                        return;
                    }
                }
                if (!dp.getCooldowns().canPlayerDoDungeon(dungeon)) {
                    dp.getBukkitPlayer().sendMessage((Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.YELLOW + "You cannot receive rewards for clearing the " + dungeon.getDungeonName() + (Object)ChatColor.YELLOW + " dungeon for another " + com.compilex.CosmicDungeons.utils.TimeUtils.formatDifference(dp.getCooldowns().getCooldown(dungeon)) + ".");
                }
                if (dungeon.isKeyRequired()) {
                    if (Math.random() <= 0.25 && HCFOutpostAPI.isControllingOutpost((Faction)FactionUtils.getFaction((Player)e.getPlayer()), (OutpostType)OutpostType.COURTYARD)) {
                        e.getPlayer().sendMessage(CC.GreenB + "(!) " + CC.Green + "Dungeon Portal Key saved! " + CC.GreenB + "(SRC: " + CC.Red + "Infernal Stronghold" + CC.GreenB + ")");
                    } else if (Math.random() < 0.02 && e.getPlayer().hasPermission("cosmicpvp.heroic")) {
                        e.getPlayer().sendMessage(CC.GreenB + "(!) " + CC.Green + "Dungeon Portal Key saved! " + CC.GreenB + "(SRC: " + CC.Red + "Top+ Rank" + CC.GreenB + ")");
                    } else if (Math.random() < 0.1 && MaskUtils.hasMaskEquipped((Player)e.getPlayer(), (String)"DUNGEON")) {
                        e.getPlayer().sendMessage(CC.GreenB + "(!) " + CC.Green + "Dungeon Portal Key saved! " + CC.GreenB + "(SRC: " + CC.Red + "Dungeon Mask" + CC.GreenB + ")");
                    } else if (Math.random() < 0.05 && e.getPlayer().getInventory().getBoots() != null && AstroItemSkinsAPI.hasSkinEquipped((Player)e.getPlayer(), (SkinType)SkinType.SPRING_2020_BOOTS)) {
                        e.getPlayer().sendMessage(CC.GreenB + "(!) " + CC.Green + "Dungeon Portal Key saved! " + CC.GreenB + "(SRC: " + CC.Red + "Rainbow Slides" + CC.GreenB + ")");
                    } else {
                        if (!dungeon.removePlayerKey(e.getPlayer())) {
                            e.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "Unable to find and remove dungeon key from your inventory!");
                            return;
                        }
                        e.getPlayer().setMetadata("dungeonKeyUsed_" + dungeon.getDungeonType().getInternalName(), (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get(), (Object)System.currentTimeMillis()));
                        e.getPlayer().sendMessage((Object)dungeon.getDungeonType().getColor() + "- 1 " + dungeon.getDungeonType().getDungeonName() + " Dungeon Portal Key");
                    }
                }
                if (e.getPlayer().getWorld().equals((Object)dungeon.getWorld())) {
                    dp.leaveDungeon();
                } else if (e.getPlayer().teleport(dungeon.getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN)) {
                    dp.setCurrentDungeon(dungeon);
                    dp.getBukkitPlayer().removeMetadata("lastRegionName", (Plugin)CosmicDungeonsPlugin.get_());
                    e.getPlayer().sendMessage((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.GOLD + "You have entered the " + dungeon.getDungeonName() + (Object)ChatColor.GOLD + " /dungeon! You will be assigned your first /objective once the portal closes.");

                    TitleUtil.sendTitle(e.getPlayer(), ChatColor.GREEN + ChatColor.BOLD.toString() + dungeon.getDungeonName());
                    TitleUtil.sendSubTitle(e.getPlayer(), ChatColor.GRAY + "The /dungeon will begin once the entry portal closes.");

                    if (this.isHeroicDungeon) {
                        for (int i = 0; i < debuffs.size(); ++i) {
                            e.getPlayer().setMetadata("heroicDungeon_" + debuffs.get(i), (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get(), (Object)System.currentTimeMillis()));
                        }
                    }
                }
            }
        } else if (e.getFrom().clone().add(0.0, 1.0, 0.0).getBlock().hasMetadata("dungeonExitPortalBlock")) {
            int dungeonID = ((MetadataValue)e.getFrom().clone().add(0.0, 1.0, 0.0).getBlock().getMetadata("dungeonExitPortalBlock").get(0)).asInt();
            Dungeon dungeon = DungeonsAPI.get().getDungeon(dungeonID);
            if (dungeon.getDungeonStatus() == DungeonState.COMPLETE) {
                e.getPlayer().teleport(((World)Bukkit.getWorlds().get(0)).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            }
        } else if (WorldGuardUtils.isPvPDisabled((Location)e.getPlayer().getLocation()) || e.getPlayer().getWorld().getName().startsWith("dungeon_")) {
            e.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You must stand directly in the dungeon portal to enter.");
            e.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onBlockPhysics(BlockPhysicsEvent e) {
        if (e.getBlock().hasMetadata("dungeonPortalBlock") || e.getBlock().hasMetadata("dungeonExitPortalBlock") || e.getBlock().getType() == Material.ENDER_PORTAL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e) {
        if (e.getBlock().hasMetadata("dungeonPortalBlock") || e.getBlock().hasMetadata("dungeonExitPortalBlock")) {
            Location l = e.getBlock().getLocation();
            WorldUtil.sendWorldEffect((Block)l.getBlock(), (int)90, (int)2001);
        }
    }

    @EventHandler
    public void onPlayerSpawnDungeonPortal(PlayerInteractEvent e) {
        Dungeon dungeon;
        ItemStack portalItem = e.getItem();
        if (e.hasItem() && e.getItem().getType() == Material.EYE_OF_ENDER && (dungeon = DungeonsAPI.get().getDungeonFromPortalItem(e.getItem())) != null) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.hasBlock() && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Player p = e.getPlayer();
                if (CosmicDungeonsPlugin.get_().isDisableDungeons()) {
                    p.sendMessage((Object)ChatColor.RED + "Dungeon portal placement is currently disabled by staff.");
                    return;
                }
                if (CosmicDungeonsPlugin.get().getDisabledDungeons().contains((Object)dungeon.getDungeonType()) && !p.isOp()) {
                    p.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot place this dungeon portal at this time.");
                    return;
                }
                if (dungeon.getPortalPlaceCooldown() > System.currentTimeMillis()) {
                    p.sendMessage(CC.RedB + "(!) " + CC.Red + "Please wait " + TimeUtils.formatFutureTime((long)dungeon.getPortalPlaceCooldown()) + " until the dungeon is finished resetting.");
                    return;
                }
                if (dungeon.getDungeonStatus() == DungeonState.IN_PROGRESS) {
                    if (dungeon.getPlayers().size() == 0) {
                        dungeon.reset();
                        return;
                    }
                    p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "Another party is already running that dungeon. You will have to wait until they are done.");
                } else {
                    if (dungeon.getDungeonStatus() == DungeonState.LOADING) {
                        p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "There is already an active portal to that dungeon.");
                        if (dungeon.getPortalLocation() != null) {
                            p.sendMessage((Object)ChatColor.RED + "Portal: " + (Object)ChatColor.GRAY + dungeon.getPortalLocation().getBlockX() + "x, " + dungeon.getPortalLocation().getBlockY() + "y, " + dungeon.getPortalLocation().getBlockZ() + "z");
                        }
                        return;
                    }
                    if (!WorldGuardUtils.getRegionName((Location)e.getClickedBlock().getLocation()).equalsIgnoreCase("spawn_region")) {
                        p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot place /dungeon portals in PvP-enabled zones.");
                        return;
                    }
                    DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(p);
                    if (dungeon.isDestroyedOutpost() && !DungeonType.ABANDONED_SHIP.getDungeonInstance().hasCompletedRecently(dp, 24L)) {
                        p.sendMessage(CC.RedB + "ERROR: " + CC.Red + "Teleportation Coordinates Unknown");
                        p.sendMessage(CC.Red + "You must have defeated the " + CC.LightPurpleB + DungeonType.ABANDONED_SHIP.getDungeonName() + CC.Red + " dungeon within the last 24h to place this portal!");
                        return;
                    }
                    if (!dp.getCooldowns().canPlayerDoDungeon(dungeon)) {
                        p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You cannot place a portal to the " + dungeon.getDungeonName() + (Object)ChatColor.RED + " dungeon for " + com.compilex.CosmicDungeons.utils.TimeUtils.formatDifference(dp.getCooldowns().getCooldown(dungeon)) + ".");
                        return;
                    }
                    if (!e.getClickedBlock().getWorld().getName().equals(((World)Bukkit.getWorlds().get(0)).getName())) {
                        p.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You can only place Dungeon Portals in the Overworld!");
                        return;
                    }
                    if (!WorldGuardUtils.isInRegionsFast((Location)e.getClickedBlock().getLocation(), (String[])new String[]{"spawn_region"})) {
                        p.sendMessage(CC.RedB + "(!) " + CC.Red + "You can only place Dungeon Portals at spawn!");
                        return;
                    }
                    final Block portalBlockA = e.getClickedBlock().getLocation().add(0.0, 1.0, 0.0).getBlock();
                    final Block portalBlockB = e.getClickedBlock().getLocation().add(0.0, 2.0, 0.0).getBlock();
                    if (portalBlockA.getType() != Material.AIR || portalBlockB.getType() != Material.AIR) {
                        p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "Portals need a 1x1x2 area to be spawned.");
                        return;
                    }
                    boolean hasProtectionTrinket = PortalTrinketUtils.portalHasTrinket(portalItem, "PORTAL_PROTECTION");
                    boolean hasTimeReductionTrinket = PortalTrinketUtils.portalHasTrinket(portalItem, "TIME_REDUCTION_1") || PortalTrinketUtils.portalHasTrinket(portalItem, "TIME_REDUCTION_2") || PortalTrinketUtils.portalHasTrinket(portalItem, "TIME_REDUCTION_3");
                    boolean hasBossSlayerTrinket = PortalTrinketUtils.portalHasTrinket(portalItem, "BOSS_SLAYER");
                    if (p.getItemInHand().getAmount() > 1) {
                        ItemStack i = p.getItemInHand();
                        i.setAmount(i.getAmount() - 1);
                        p.setItemInHand((ItemStack)i);
                    } else {
                        p.setItemInHand((ItemStack)null);
                    }
                    dungeon.reset();
                    if (hasProtectionTrinket && !FactionUtils.getFaction((Player)p).getId().equals("0")) {
                        dungeon.setProtectedFor(FactionUtils.getFaction((Player)p).getId());
                    }
                    for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                        pl.removeMetadata("lastDungeonDeath", (Plugin)CosmicDungeonsPlugin.get_());
                    }
                    portalBlockA.setMetadata("dungeonPortalBlock", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)dungeon.getDungeonID()));
                    portalBlockB.setMetadata("dungeonPortalBlock", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)dungeon.getDungeonID()));
                    portalBlockA.setTypeIdAndData(90, (byte)0, false);
                    portalBlockB.setTypeIdAndData(90, (byte)0, false);
                    this.portalBlocks.add(portalBlockA.getLocation());
                    this.portalBlocks.add(portalBlockB.getLocation());
                    final Block dungeon_portalBlockA = dungeon.getSpawnLocation().getBlock();
                    final Block dungeon_portalBlockB = dungeon.getSpawnLocation().clone().add(0.0, 1.0, 0.0).getBlock();
                    dungeon_portalBlockA.setMetadata("dungeonPortalBlock", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)dungeon.getDungeonID()));
                    dungeon_portalBlockB.setMetadata("dungeonPortalBlock", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDungeonsPlugin.get_(), (Object)dungeon.getDungeonID()));
                    dungeon_portalBlockA.setTypeIdAndData(90, (byte)0, false);
                    dungeon_portalBlockB.setTypeIdAndData(90, (byte)0, false);
                    this.portalBlocks.add(dungeon_portalBlockA.getLocation());
                    this.portalBlocks.add(dungeon_portalBlockB.getLocation());
                    ParticleEffect.playEffect((ParticleEffect)ParticleEffect.WITCH_MAGIC, (Location)portalBlockA.getLocation(), (float)0.45f, (int)50);
                    ParticleEffect.playEffect((ParticleEffect)ParticleEffect.PORTAL, (Location)portalBlockA.getLocation(), (float)0.6f, (int)50);
                    ParticleEffect.playEffect((ParticleEffect)ParticleEffect.PORTAL, (Location)portalBlockB.getLocation(), (float)0.6f, (int)50);
                    p.getWorld().playSound(portalBlockA.getLocation(), Sound.AMBIENCE_THUNDER, 2.0f, 0.35f);
                    dungeon.setDungeonStatus(DungeonState.LOADING);
                    dungeon.setPortalLocation(portalBlockA.getLocation());
                    //final Hologram holo_a = HolographicDisplaysAPI.createHologram((Plugin)CosmicDungeonsPlugin.get_(), (Location)portalBlockB.getLocation().add(0.5, 1.25, 0.5), (String[])new String[]{dungeon.getDungeonName()});
                    //final Hologram holo_b = HolographicDisplaysAPI.createHologram((Plugin)CosmicDungeonsPlugin.get_(), (Location)dungeon_portalBlockB.getLocation().add(0.5, 1.25, 0.5), (String[])new String[]{(Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "Exit Portal"});
                    //if (dungeon.isAbandondedSpaceship()) {
                    //    dungeon.setObjectiveHologram(HolographicDisplaysAPI.createHologram((Plugin)CosmicDungeonsPlugin.get_(), (Location)new Location(dungeon.getWorld(), -2.0, 82.0, 90.0), (String[])new String[]{(Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "LOCKED", (Object)ChatColor.GRAY + "The airlock will automatically", (Object)ChatColor.GRAY + "unlock once the dungeon portal closes."}));
                    //} else if (dungeon.isDestroyedOutpost()) {
                    //    dungeon.setObjectiveHologram(HolographicDisplaysAPI.createHologram((Plugin)CosmicDungeonsPlugin.get_(), (Location)new Location(dungeon.getWorld(), 287.0, 208.0, -12.0), (String[])new String[]{(Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "LOCKED", (Object)ChatColor.GRAY + "The airlock will automatically", (Object)ChatColor.GRAY + "unlock once the dungeon portal closes."}));
                    //}
                    int closeTimer = 100;
                    int level = UpgradeUtils.getUpgradeLevel((Player)p, (String)"HEROIC_DUNGEON_PORTAL");
                    if (level == 1) {
                        closeTimer = 90;
                    } else if (level == 2) {
                        closeTimer = 80;
                    } else if (level == 3) {
                        closeTimer = 45;
                    }
                    if (p.isOp() && p.getName().equalsIgnoreCase("matthawkk") && p.isSneaking()) {
                        closeTimer = 10;
                    }
                    if (hasBossSlayerTrinket) {
                        p.sendMessage(CC.GoldB + "Boss Slayer Trinket: " + CC.Red + "+ 1.5x Outgoing Boss Damage");
                        dungeon.setBossSlayerTrinket(true);
                    }
                    Bukkit.broadcastMessage((String)"");
                    Bukkit.broadcastMessage((String)((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "*** DUNGEON PORTAL ***"));
                    Bukkit.broadcastMessage((String)((Object)ChatColor.YELLOW + p.getDisplayName() + (Object)ChatColor.GOLD + " has placed a dungeon portal to "));
                    Bukkit.broadcastMessage((String)(dungeon.getDungeonName() + (Object)ChatColor.GOLD + " at: " + (Object)ChatColor.YELLOW + portalBlockA.getX() + "x, " + portalBlockA.getY() + "y, " + portalBlockA.getZ() + "z"));
                    Bukkit.broadcastMessage((String)((Object)ChatColor.GOLD + "The portal will close in " + (Object)ChatColor.UNDERLINE + closeTimer + " seconds" + (Object)ChatColor.GOLD + ", queue up fast!"));
                    if (hasBossSlayerTrinket) {
                        Bukkit.broadcastMessage((String)((Object)ChatColor.GOLD + "* " + (Object)ChatColor.GOLD + ChatColor.UNDERLINE.toString() + "Boss Slayer Trinket" + (Object)ChatColor.GOLD + " active!"));
                    }
                    if (!dungeon.isKeyRequired()) {
                        Bukkit.broadcastMessage((String)((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "NO KEY REQUIREMENT!"));
                    }
                    Bukkit.broadcastMessage((String)"");
                    if (AstroItemSkinsAPI.hasSkinEquipped((Player)p, (SkinType)SkinType.EASTER_AMULET) && Math.random() < 0.15) {
                        p.getInventory().addItem(new ItemStack[]{portalItem});
                        p.sendMessage(CC.GreenB + "(!) " + CC.Green + "Dungeon Portal saved! " + CC.GreenB + "(SRC: " + CC.Red + "Rabbit's Foot Amulet" + CC.GreenB + ")");
                    }
                    final int finalCloseTimer = closeTimer;
                    new BukkitRunnable(){
                        int countdown;
                        {
                            this.countdown = finalCloseTimer;
                        }

                        public void run() {
                            boolean showGlobalAnnounce;
                            if (this.countdown <= 0) {
                                this.cancel();
                                portalBlockA.removeMetadata("dungeonPortalBlock", (Plugin)CosmicDungeonsPlugin.get_());
                                portalBlockB.removeMetadata("dungeonPortalBlock", (Plugin)CosmicDungeonsPlugin.get_());
                                dungeon_portalBlockA.removeMetadata("dungeonPortalBlock", (Plugin)CosmicDungeonsPlugin.get_());
                                dungeon_portalBlockB.removeMetadata("dungeonPortalBlock", (Plugin)CosmicDungeonsPlugin.get_());
                                portalBlockA.getLocation().getChunk().load();
                                portalBlockA.setType(Material.AIR);
                                portalBlockB.setType(Material.AIR);
                                dungeon_portalBlockA.setType(Material.AIR);
                                dungeon_portalBlockB.setType(Material.AIR);
                                DungeonPortalListener.this.portalBlocks.remove((Object)portalBlockA.getLocation());
                                DungeonPortalListener.this.portalBlocks.remove((Object)portalBlockB.getLocation());
                                DungeonPortalListener.this.portalBlocks.remove((Object)dungeon_portalBlockA.getLocation());
                                DungeonPortalListener.this.portalBlocks.remove((Object)dungeon_portalBlockB.getLocation());
                                //holo_a.delete();
                                //holo_b.delete();
                                portalBlockA.getWorld().playSound(portalBlockA.getLocation(), Sound.AMBIENCE_THUNDER, 2.0f, 0.35f);
                                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.WITCH_MAGIC, (Location)portalBlockA.getLocation(), (float)0.45f, (int)50);
                                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.PORTAL, (Location)portalBlockA.getLocation(), (float)0.6f, (int)50);
                                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.PORTAL, (Location)portalBlockB.getLocation(), (float)0.6f, (int)50);
                                dungeon_portalBlockA.getWorld().playSound(portalBlockA.getLocation(), Sound.AMBIENCE_THUNDER, 2.0f, 0.35f);
                                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.WITCH_MAGIC, (Location)dungeon_portalBlockA.getLocation(), (float)0.45f, (int)50);
                                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.PORTAL, (Location)dungeon_portalBlockA.getLocation(), (float)0.6f, (int)50);
                                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.PORTAL, (Location)dungeon_portalBlockB.getLocation(), (float)0.6f, (int)50);
                                Bukkit.broadcastMessage((String)"");
                                Bukkit.broadcastMessage((String)((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "*** DUNGEON PORTAL ***"));
                                Bukkit.broadcastMessage((String)((Object)ChatColor.GOLD + "The portal has closed, " + (Object)ChatColor.UNDERLINE + dungeon.getPlayers().size() + (Object)ChatColor.GOLD + " player(s) made it into the"));
                                Bukkit.broadcastMessage((String)(dungeon.getDungeonName() + (Object)ChatColor.GOLD + " /dungeon!"));
                                Bukkit.broadcastMessage((String)"");
                                if (dungeon.getPlayers().size() > 0) {
                                    dungeon.setDungeonStatus(DungeonState.IN_PROGRESS);
                                } else {
                                    dungeon.setDungeonStatus(DungeonState.EMPTY);
                                }
                                dungeon.getPlayers().stream().filter(pl -> !pl.hasMetadata("NPC")).forEach(pl -> Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("contests add " + pl.getName() + " dungeonsEntered 1")));
                                return;
                            }
                            boolean bl = showGlobalAnnounce = this.countdown == 60 || finalCloseTimer == 45 && this.countdown == 30;
                            if (showGlobalAnnounce) {
                                dungeon.broadcastMessage("The dungeon portal will close in " + this.countdown + "s...");
                                TitleUtil.sendTitleList(dungeon.getPlayers(), ChatColor.GOLD + "The dungeon portal will close in " + ChatColor.YELLOW + this.countdown + "s...");
                                Bukkit.broadcastMessage((String)"");
                                Bukkit.broadcastMessage((String)((Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "*** DUNGEON PORTAL ***"));
                                Bukkit.broadcastMessage((String)((Object)ChatColor.YELLOW + "The dungeon portal to "));
                                Bukkit.broadcastMessage((String)(dungeon.getDungeonName() + (Object)ChatColor.GOLD + " at: " + (Object)ChatColor.YELLOW + portalBlockA.getX() + "x, " + portalBlockA.getY() + "y, " + portalBlockA.getZ() + "z will close in " + (Object)ChatColor.UNDERLINE + this.countdown + " seconds" + (Object)ChatColor.GOLD + ", hurry up!"));
                                Bukkit.broadcastMessage((String)"");
                            } else if (this.countdown == 30 || this.countdown == 15 || this.countdown == 10 || this.countdown == 5 || finalCloseTimer - this.countdown == 15) {
                                dungeon.broadcastMessage("The dungeon portal will close in " + this.countdown + "s...");
                                if (this.countdown == 30 || this.countdown == 5) {
                                    TitleUtil.sendTitleList(dungeon.getPlayers(), ChatColor.GOLD + "The dungeon portal will close in " + ChatColor.RED + this.countdown + "s...");
                                }
                            }
                            --this.countdown;
                        }
                    }.runTaskTimer((Plugin)CosmicDungeonsPlugin.get_(), 20L, 20L);
                    CosmicDungeonsPlugin.get().logPortalPlace(dungeon.getDungeonType(), p, portalBlockA);
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("fquests add DUNGEON_PORTALS " + p.getName()));
                }
            }
        }
    }
}

