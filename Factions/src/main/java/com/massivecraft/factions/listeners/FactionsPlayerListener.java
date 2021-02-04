package com.massivecraft.factions.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.*;
import com.massivecraft.factions.cmd.CmdClaim;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import com.massivecraft.factions.event.PowerLossEvent;
import com.massivecraft.factions.struct.*;
import com.massivecraft.factions.util.HeartsUnderName;
import com.massivecraft.factions.util.PrefixType;
import com.massivecraft.factions.util.ScoreboardManager;
import com.massivecraft.factions.util.TimeUtil;
import com.massivecraft.factions.zcore.util.MojangUUIDFetcher;
import com.massivecraft.factions.zcore.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.*;

public class FactionsPlayerListener implements Listener {
    public P p;

    private final HashMap<UUID, Long> showTimes;

    List<Material> containerBlocks;

    private final Map<String, InteractAttemptSpam> interactSpammers;

    public FactionsPlayerListener(P p) {
        this.showTimes = new HashMap<>();
        this.containerBlocks = new ArrayList<>(Arrays.asList(Material.CHEST, Material.TRAPPED_CHEST, Material.HOPPER, Material.HOPPER_MINECART, Material.DISPENSER, Material.DROPPER, Material.BREWING_STAND, Material.FURNACE, Material.BURNING_FURNACE, Material.BEACON,
                Material.STORAGE_MINECART));
        this.interactSpammers = new HashMap<>();
        this.p = p;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLeaveFaction(final FPlayerLeaveEvent event) {
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(this.p, new Runnable() {
            public void run() {
                if (event.getFPlayer() == null || event.getFPlayer().isOffline())
                    return;
                if (Conf.factionScoreboardEnable) {
                    if (!event.getFPlayer().hasFaction() || event.getFPlayer().getFaction() == null)
                        return;
                }
            }
        },  10L);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onLoss(final PowerLossEvent event) {
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(this.p, new Runnable() {
            public void run() {
                if (event.getFPlayer() == null || event.getFPlayer().isOffline())
                    return;
            }
        },  10L);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FPlayer fplayer = FPlayers.i.get(player);

        if (P.getP().getPrefixType() == PrefixType.FACTION) {
            player.sendMessage("debug101");
        }

        if (P.getP().getBoardManager() != null) {
            P.getP().getBoardManager().getPlayerBoards().put(player.getUniqueId(), new com.massivecraft.factions.board.Board(player, P.getP().getBoardManager().getAdapter()));
            HeartsUnderName.updateNametag(player, true);
        }

        if (fplayer == null)
            return;
        fplayer.setLastLoginTime(System.currentTimeMillis());
        fplayer.updateLastKnownName(event.getPlayer().getName());
        Faction fac = fplayer.getFaction();
        if (fac == null || !fac.isNormal())
            return;

        String message = ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW.toString() + ChatColor.BOLD + player.getName() + ChatColor.YELLOW + " has logged in.";
        for (FPlayer member : fac.getFPlayers()) {
            if (member.equals(fplayer))
                continue;
            member.sendMessage(message);
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(P.p, () -> fac.sendMOTD(fplayer, false), 80L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (!event.getPlayer().isOnline())
            return;
        Player player = event.getPlayer();
        FPlayer fplayer = FPlayers.i.get(player);
        if (fplayer == null)
            return;
        Faction fac = fplayer.getFaction();
        if (fac == null || !fac.isNormal())
            return;
        if (P.getP().getBoardManager() != null) {
            P.getP().getBoardManager().getPlayerBoards().remove(player.getUniqueId());
        }
        String message = ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW.toString() + ChatColor.BOLD + player.getName() + ChatColor.YELLOW + " has logged out.";
        for (FPlayer member : fac.getFPlayers()) {
            if (member.equals(fplayer))
                continue;
            member.sendMessage(message);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLogin(PlayerLoginEvent e) {
        FPlayer me = FPlayers.i.get(e.getPlayer().getUniqueId().toString());
        me.clearCachedPlayer();
        MojangUUIDFetcher.updateCachedUUID(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        FPlayer me = FPlayers.i.get(event.getPlayer().getUniqueId().toString());
        me.getPower();
        me.setLastLoginTime(System.currentTimeMillis());
        me.clearCachedPlayer();
        Faction myFaction = me.getFaction();
        if (myFaction != null)
            myFaction.memberLoggedOff();
        if (Conf.factionScoreboardEnable)
            ScoreboardManager.removeScoreboardSync(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() >> 4 == event.getTo().getBlockX() >> 4 && event.getFrom().getBlockZ() >> 4 == event.getTo().getBlockZ() >> 4 && event.getFrom().getWorld() == event.getTo().getWorld())
            return;
        Player player = event.getPlayer();
        FPlayer me = FPlayers.i.get(player);
        FLocation from = me.getLastStoodAt();
        FLocation to = new FLocation(event.getTo());
        if (from.equals(to))
            return;
        me.setLastStoodAt(to);
        Faction factionFrom = Board.getFactionAt(from);
        Faction factionTo = Board.getFactionAt(to);
        boolean changedFaction = !me.lastFactionMessage.equals(me.getFactionHereMessage());
        if (me.isMapAutoUpdating()) {
            if (!this.showTimes.containsKey(player.getUniqueId()) || this.showTimes.get(player.getUniqueId()) <= System.currentTimeMillis()) {
                me.sendMessage(Board.getMap(me.getFaction(), to, player));
                this.showTimes.put(player.getUniqueId(), System.currentTimeMillis() + 4000L);
            }
        } else {
            Faction myFaction = me.getFaction();
            String ownersTo = myFaction.getOwnerListStringAsync(to);
            if (changedFaction) {
                me.sendFactionHereMessage();
                if (Conf.ownedAreasEnabled && Conf.ownedMessageOnBorder && myFaction == factionTo && !ownersTo.isEmpty())
                    me.sendMessage(Conf.ownedLandMessage + ownersTo);
            } else if (Conf.ownedAreasEnabled && Conf.ownedMessageInsideTerritory && factionFrom == factionTo && myFaction == factionTo) {
                String ownersFrom = myFaction.getOwnerListStringAsync(from);
                if (Conf.ownedMessageByChunk || !ownersFrom.equals(ownersTo))
                    if (!ownersTo.isEmpty()) {
                        me.sendMessage(Conf.ownedLandMessage + ownersTo);
                    } else if (!Conf.publicLandMessage.isEmpty()) {
                        me.sendMessage(Conf.publicLandMessage);
                    }
            }
        }
        if (me.getAutoClaimFor() != null) {
            if (!CmdClaim.chunkCoordsPendingClaim.containsKey(event.getTo().getChunk().getX() + "," + event.getTo().getChunk().getZ())) {
                me.attemptClaimAsync(me.getAutoClaimFor(), event.getTo(), true);
                P.p.logFactionEvent(me.getAutoClaimFor(), FLogType.CHUNK_CLAIMS, me.getNameAsync(), CC.GreenB + "CLAIMED", String.valueOf(1), (new FLocation(player.getLocation())).formatXAndZ(","));
            } else {
                Bukkit.getLogger().info("[Factions] Skipping auto-claim for chunk at " + event.getTo() + " due to chunkCoordsPendingClaim.");
            }
        } else if (me.isAutoSafeClaimEnabled()) {
            if (!Permission.MANAGE_SAFE_ZONE.has(player)) {
                me.setIsAutoSafeClaimEnabled(false);
            } else if (!Board.getFactionAt(to).isSafeZone()) {
                Board.setFactionAt(Factions.i.getSafeZone(), to);
                me.msg("<i>This land is now a safe zone.");
            }
        } else if (me.isAutoWarClaimEnabled()) {
            if (!Permission.MANAGE_WAR_ZONE.has(player)) {
                me.setIsAutoWarClaimEnabled(false);
            } else if (!Board.getFactionAt(to).isWarZone()) {
                Board.setFactionAt(Factions.i.getWarZone(), to);
                me.msg("<i>This land is now a war zone.");
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractMon(PlayerInteractEvent event) {
        if (FactionPermissions.debug)
            Bukkit.getLogger().info("Final result: " + event.useInteractedBlock() + " Hand: " + event.useItemInHand());
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteractNew(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.PHYSICAL)
            return;
        Block clickedBlock = event.getClickedBlock();
        Player player = event.getPlayer();
        if (clickedBlock == null)
            return;
        ItemStack item = event.getItem();
        if (item != null && item.getType() == Material.MONSTER_EGG && item.getDurability() != EntityType.CREEPER.getTypeId()) {
            Faction in = Board.getFactionAt(new FLocation(clickedBlock));
            if (in != null && in.isWarZone()) /*&& MiscUtil.isBossEgg(item)) */
                return;
        }
        ItemStack hand = event.getPlayer().getItemInHand();
        Material blockClickedType = clickedBlock.getType();
        FLocation location = new FLocation(clickedBlock);
        Faction interactedFaction = Board.getFactionAt(location);
        if (Conf.playersWhoBypassAllProtection.contains(player.getName()))
            return;
        FPlayer me = FPlayers.i.get(player);
        if (me.isAdminBypassing())
            return;
        FactionWrapper wrapper = FactionWrappers.get(interactedFaction);
        MaterialData handData = (hand != null) ? hand.getData() : null;
        FactionPermission desiredBlockPermission = FactionPermission.getFromMaterial(new MaterialData(blockClickedType));
        FactionPermission desiredHandPermissions = FactionPermission.getFromMaterial(handData);
        if (handData != null && (handData.getItemType() == Material.LAVA_BUCKET || handData
                .getItemType() == Material.WATER_BUCKET || handData.getItemType() == Material.BUCKET)) {
            Block block = clickedBlock.getRelative(event.getBlockFace());
            FLocation waterLocation = new FLocation(block.getLocation());
            Faction faction = Board.getFactionAt(waterLocation);
            if (faction != null && faction != interactedFaction) {
                FactionPermissions otherFaction = PermissionManager.get().getPermissions(faction);
                Set<FactionPermission> permissions = otherFaction.getPermissions(faction, waterLocation, me.getFaction(), me, "interact");
                if (!permissions.contains(FactionPermission.OTHER) && !permissions.contains(FactionPermission.FULL)) {
                    event.setUseItemInHand(Event.Result.DENY);
                    event.setCancelled(true);
                    Bukkit.getLogger().info("Cancelling bucket placement from " + me.getNameAsync() + " due to placing on the edge of 2 factions!");
                    return;
                }
            }
        }
        boolean isOverridableitem = (handData != null && (FactionPermission.isConsumable(handData.getItemType()) || handData.getItemType() == Material.BOW));
        Relation rel = interactedFaction.getRelationTo(me);
        FactionPermissions perms = PermissionManager.get().getPermissions(interactedFaction);
        Set<FactionPermission> playerOrFactionPermissions = perms.getPermissions(interactedFaction, location, me.getFaction(), me, "interact");
        boolean doAnyEntitiesHaveChunkAccess = perms.hasChunkSpecificPerms(location);
        boolean allowedToOpen = FactionPermission.isAlwaysOpennableContainer(blockClickedType);
        if (!allowedToOpen && (blockClickedType == Material.CHEST || blockClickedType == Material.TRAPPED_CHEST) && (
                playerOrFactionPermissions.contains(FactionPermission.CHEST) || rel == Relation.ENEMY))
            allowedToOpen = true;
        if (doAnyEntitiesHaveChunkAccess) {
            boolean hasHandPermission = (playerOrFactionPermissions.contains(desiredHandPermissions) || playerOrFactionPermissions.contains(FactionPermission.FULL));
            if (!hasHandPermission) {
                if (isOverridableitem) {
                    event.setUseItemInHand(Event.Result.ALLOW);
                } else {
                    event.setUseItemInHand(Event.Result.DENY);
                    if (desiredHandPermissions == FactionPermission.CREEPER) {
                        event.setUseInteractedBlock(Event.Result.DENY);
                        if (FactionPermissions.debug)
                            Bukkit.getLogger().info("Denying block interact as well due to creeper click with no permissions!");
                    }
                }
                if (FactionPermissions.debug)
                    Bukkit.getLogger().info("Denying hand item: " + event.useItemInHand() + " for " + desiredHandPermissions);
            } else if (desiredHandPermissions == FactionPermission.CREEPER) {
                event.setUseItemInHand(Event.Result.ALLOW);
                if (FactionPermissions.debug)
                    Bukkit.getLogger().info("Allowing creeper egg since we have explicit access");
            }
            boolean hasBlockPermission = (playerOrFactionPermissions.contains(desiredBlockPermission) || playerOrFactionPermissions.contains(FactionPermission.FULL));
            if (!hasBlockPermission)
                if (!allowedToOpen) {
                    event.setUseInteractedBlock(Event.Result.DENY);
                    if (isOverridableitem) {
                        event.setUseItemInHand(Event.Result.ALLOW);
                        if (FactionPermissions.debug)
                            Bukkit.getLogger().info("Allowing override item: " + handData + " " + event.useItemInHand() + " : " + desiredBlockPermission);
                    }
                    if (FactionPermissions.debug)
                        Bukkit.getLogger().info("Denying block permission item: " + event.useItemInHand() + " for " + handData + " : " + desiredBlockPermission + " KeyS: " +
                                Arrays.toString(playerOrFactionPermissions.toArray((Object[])new FactionPermission[playerOrFactionPermissions.size()])));
                    sendTargetBlockUpdate(player);
                }
            if (event.useItemInHand() == Event.Result.DENY && !allowedToOpen && desiredBlockPermission != FactionPermission.OTHER) {
                if (TimeUtil.isOnCooldown(player, "cooldownMessage"))
                    return;
                TimeUtil.setCooldown(player, "cooldownMessage", 1);
                player.sendMessage(ChatColor.RED + "You do not have permission to do that in this territory! A Coleader+ of " + interactedFaction.getTag() + " needs to grant you /f perm to perform this action.");
            }
            return;
        }
        if (interactedFaction.getRelationTo(me) == Relation.MEMBER && me.getRole() == Role.RECRUIT) {
            if (isOverridableitem) {
                event.setUseItemInHand(Event.Result.ALLOW);
                event.setUseInteractedBlock(Event.Result.DENY);
                sendTargetBlockUpdate(player);
                return;
            }
            player.sendMessage(ChatColor.RED + "You are a Recruit, you must be promoted to Member or given /f access to this chunk.");
            event.setCancelled(true);
            sendTargetBlockUpdate(player);
            return;
        }
        allowedToOpen = (allowedToOpen || desiredBlockPermission == FactionPermission.HOPPER || desiredBlockPermission == FactionPermission.DROPPER || desiredBlockPermission == FactionPermission.DISPENSE || desiredBlockPermission == FactionPermission.CHEST);
        if (allowedToOpen)
            event.setUseInteractedBlock(Event.Result.ALLOW);
        if (!isOverridableitem && (desiredHandPermissions == FactionPermission.CREEPER || (handData != null && handData.getItemType() == Material.MONSTER_EGG && handData.getData() == EntityType.CREEPER.getTypeId())))
            isOverridableitem = true;
        if (isOverridableitem)
            event.setUseItemInHand(Event.Result.ALLOW);
        if (wrapper.isLocationTracked(location)) {
            if (wrapper.doesPlayerHaveChunkAccess(location, me, true))
                return;
            if (!allowedToOpen && !isOverridableitem) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "This chunk is privately owned by " + interactedFaction.getTag() + " and you do not have access.");
                sendTargetBlockUpdate(player);
            } else if (!isOverridableitem) {
                event.setUseItemInHand(Event.Result.DENY);
            } else if (!allowedToOpen) {
                event.setUseInteractedBlock(Event.Result.DENY);
            }
            if (FactionPermissions.debug)
                Bukkit.getLogger().info("No access to tracked location for " + player.getName() + " b=" + event.useInteractedBlock() + " item=" + event.useItemInHand());
            return;
        }
        if (interactedFaction.isNone()) {
            if (!Conf.wildernessDenyUseage || Conf.worldsNoWildernessProtection.contains(location.getWorld().getName()))
                return;
            me.msg("<b>You can't use <h>%s<b> in the wilderness.", TextUtil.getMaterialName(blockClickedType));
            if (!allowedToOpen)
                event.setUseInteractedBlock(Event.Result.DENY);
            if (!isOverridableitem)
                event.setUseItemInHand(Event.Result.DENY);
            return;
        }
        if (interactedFaction.isSafeZone()) {
            if (!Conf.safeZoneDenyUseage || Permission.MANAGE_SAFE_ZONE.has(player))
                return;
            me.msg("<b>You can't use <h>%s<b> in a safe zone.", TextUtil.getMaterialName(blockClickedType));
            event.setCancelled(true);
            return;
        }
        if (interactedFaction.isWarZone()) {
            if (!Conf.warZoneDenyUseage || Permission.MANAGE_WAR_ZONE.has(player))
                return;
            Material type;
            if (handData != null && ((type = handData.getItemType()) == Material.WATER_BUCKET || type == Material.LAVA_BUCKET || type == Material.BUCKET || (type == Material.MONSTER_EGG && handData
                    .getData() == EntityType.CREEPER.getTypeId())) && !event.getPlayer().isOp()) {
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);
                if (FactionPermissions.debug)
                    Bukkit.getLogger().info("Cancelling water bucket placement for " + player.getName() + " in WarZone!");
            }
            if (clickedBlock.getLocation().getWorld().getName().startsWith("dungeon_"))
                return;
            if (WorldGuardUtils.isInRegionsContainsFast(clickedBlock.getLocation().clone().add(0.0D, 1.0D, 0.0D), Lists.newArrayList("_blocks")))
                return;
            if (!allowedToOpen)
                event.setUseInteractedBlock(Event.Result.DENY);
            if (!isOverridableitem)
                event.setUseItemInHand(Event.Result.DENY);
            return;
        }
        if (rel.isNeutral() || (rel.isEnemy() && Conf.territoryEnemyProtectMaterials) || (rel.isTruce() && Conf.territoryTruceProtectMaterials) || (rel.isAlly() && Conf.territoryAllyProtectMaterials)) {
            if (!allowedToOpen)
                event.setUseInteractedBlock(Event.Result.DENY);
            if (!isOverridableitem)
                if (desiredHandPermissions != FactionPermission.CREEPER) {
                    event.setUseItemInHand(Event.Result.DENY);
                } else {
                    event.setUseItemInHand(Event.Result.ALLOW);
                }
            if (FactionPermissions.debug)
                Bukkit.getLogger().info("Denying access due to default relation status: " + rel + " Action: " + event.getAction() + " Use Item: " + event.useItemInHand() + " block: " + event.useInteractedBlock());
            if (event.getAction() != Action.PHYSICAL && Conf.territoryProtectedMaterials.contains(blockClickedType.name()))
                me.msg("<b>You can't %s <h>%s<b> in the territory of <h>%s<b>.", (blockClickedType == Material.SOIL) ? "trample" : "use",
                        TextUtil.getMaterialName(blockClickedType), interactedFaction.getTag(me));
            if (FactionPermissions.debug)
                Bukkit.getLogger().info("Clicked: " + blockClickedType);
            sendTargetBlockUpdate(player);
            return;
        }
        if (Conf.ownedAreasEnabled && Conf.ownedAreaProtectMaterials && !interactedFaction.playerHasOwnershipRights(me, location)) {
            if (event.getAction() != Action.PHYSICAL) {
                me.msg("<b>You can't use <h>%s<b> in this territory, it is owned by: %s<b>.", TextUtil.getMaterialName(blockClickedType), interactedFaction
                        .getOwnerListStringAsync(location));
                sendTargetBlockUpdate(player);
            }
            if (!allowedToOpen)
                event.setUseInteractedBlock(Event.Result.DENY);
            if (!isOverridableitem)
                event.setUseItemInHand(Event.Result.DENY);
            if (FactionPermissions.debug)
                Bukkit.getLogger().info("action: " + event.getAction() + " Use Item: " + event.useItemInHand() + " block: " + event.useInteractedBlock());
        }
    }

    private void sendTargetBlockUpdate(Player player) {
        try {
            Block target = player.getTargetBlock((HashSet<Byte>) null, 4);
//            if (target != null && (target.getType().name().contains("WATER") || target.getType().name().contains("LAVA")))
//                //AsyncPacketSenderThread.queuePacket(((CraftPlayer)player).getHandle(), (Packet)new PacketPlayOutBlockChange(target.getX(), target.getY(), target.getZ(), (World)((CraftWorld)player.getWorld()).getHandle()));
        } catch (Exception exception) {}
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        FPlayer me = FPlayers.i.get(event.getPlayer());
        me.getPower();
        Location home = me.getFaction().getHome();
        if (Conf.homesEnabled && Conf.homesTeleportToOnDeath && home != null && (Conf.homesRespawnFromNoPowerLossWorlds ||
                !Conf.worldsNoPowerLoss.contains(event.getPlayer().getWorld().getName())))
            event.setRespawnLocation(home);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandPRe(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equals("/f scoreboard")) {
            event.setMessage("/togglesb");
        } else if (event.getMessage().equalsIgnoreCase("/tl")) {
            event.setMessage("/f sendcoords");
        } else if (event.getMessage().equals("/audit") || event.getMessage().equals("/logs")) {
            event.setMessage("/f audit");
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (event.isCancelled())
            return;
        Block block = event.getBlockClicked();
        Player player = event.getPlayer();
        PlayerInteractEvent spoofedEvent = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, player.getItemInHand(), block, event.getBlockFace());
        onPlayerInteractNew(spoofedEvent);
        if (spoofedEvent.useItemInHand() == Event.Result.DENY || spoofedEvent.useInteractedBlock() == Event.Result.DENY) {
            event.setCancelled(true);
            if (FactionPermissions.debug)
                Bukkit.getLogger().info("Cancelling " + event.getPlayer().getName() + " Bucket empty event: " + spoofedEvent.useItemInHand() + " block: " + spoofedEvent.useInteractedBlock());
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        if (event.isCancelled())
            return;
        if (FactionPermissions.debug)
            Bukkit.getLogger().info("Bucket fill: " + event.getBucket());
        Block block = event.getBlockClicked();
        Player player = event.getPlayer();
        PlayerInteractEvent spoofedEvent = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, player.getItemInHand(), block, event.getBlockFace());
        onPlayerInteractNew(spoofedEvent);
        if (spoofedEvent.useItemInHand() == Event.Result.DENY || spoofedEvent.useInteractedBlock() == Event.Result.DENY) {
            event.setCancelled(true);
            //AsyncPacketSenderThread.queuePacket(((CraftPlayer)player).getHandle(), (Packet)new PacketPlayOutBlockChange(block.getX(), block.getY(), block.getZ(), (World)((CraftWorld)player.getWorld()).getHandle()));
            if (FactionPermissions.debug)
                Bukkit.getLogger().info("Cancelling " + event.getPlayer().getName() + " Bucket fill event: " + spoofedEvent.useItemInHand() + " block: " + spoofedEvent.useInteractedBlock());
        }
    }

    public static boolean preventCommand(String fullCmd, Player player) {
        String shortCmd;
        if (Conf.territoryNeutralDenyCommands.isEmpty() && Conf.territoryEnemyDenyCommands.isEmpty() && Conf.territoryAllyDenyCommands.isEmpty() && Conf.territoryTruceDenyCommands
                .isEmpty() && Conf.permanentFactionMemberDenyCommands.isEmpty())
            return false;
        fullCmd = fullCmd.toLowerCase();
        FPlayer me = FPlayers.i.get(player.getUniqueId().toString());
        if (fullCmd.startsWith("/")) {
            shortCmd = fullCmd.substring(1);
        } else {
            shortCmd = fullCmd;
            fullCmd = "/" + fullCmd;
        }
        if (me.hasFaction() && !me.isAdminBypassing() && !Conf.permanentFactionMemberDenyCommands.isEmpty() && me.getFaction().isPermanent() && isCommandInList(fullCmd, shortCmd, Conf.permanentFactionMemberDenyCommands
                .iterator())) {
            me.msg("<b>You can't use the command \"" + fullCmd + "\" because you are in a permanent faction.");
            return true;
        }
        if (!me.isInOthersTerritory())
            return false;
        Relation rel = me.getRelationToLocation();
        if (rel.isAtLeast(Relation.ALLY))
            return false;
        if (rel.isNeutral() && !Conf.territoryNeutralDenyCommands.isEmpty() && !me.isAdminBypassing() && isCommandInList(fullCmd, shortCmd, Conf.territoryNeutralDenyCommands
                .iterator())) {
            me.msg("<b>You can't use the command \"" + fullCmd + "\" in neutral territory.");
            return true;
        }
        if (rel.isEnemy() && !Conf.territoryEnemyDenyCommands.isEmpty() && !me.isAdminBypassing() && isCommandInList(fullCmd, shortCmd, Conf.territoryEnemyDenyCommands
                .iterator())) {
            me.msg("<b>You can't use the command \"" + fullCmd + "\" in enemy territory.");
            return true;
        }
        if (rel.isTruce() && !Conf.territoryTruceDenyCommands.isEmpty() && !me.isAdminBypassing() && isCommandInList(fullCmd, shortCmd, Conf.territoryTruceDenyCommands
                .iterator())) {
            me.msg("<b>You can't use the command \"" + fullCmd + "\" in truce territory.");
            return true;
        }
        if (rel.isAlly() && !Conf.territoryAllyDenyCommands.isEmpty() && !me.isAdminBypassing() && isCommandInList(fullCmd, shortCmd, Conf.territoryAllyDenyCommands
                .iterator())) {
            me.msg("<b>You can't use the command \"" + fullCmd + "\" in ally territory.");
            return true;
        }
        return false;
    }

    private static boolean isCommandInList(String fullCmd, String shortCmd, Iterator<String> iter) {
        while (iter.hasNext()) {
            String cmdCheck = iter.next();
            if (cmdCheck == null) {
                iter.remove();
                continue;
            }
            cmdCheck = cmdCheck.toLowerCase();
            if (fullCmd.startsWith(cmdCheck) || shortCmd.startsWith(cmdCheck))
                return true;
        }
        return false;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerKick(PlayerKickEvent event) {
        FPlayer badGuy = FPlayers.i.get(event.getPlayer().getUniqueId().toString());
        Player player = event.getPlayer();
        if (player != null) {
            if (P.getP().getBoardManager() != null) {
                P.getP().getBoardManager().getPlayerBoards().remove(player.getUniqueId());
            }
        }
        if (badGuy == null)
            return;
        if (Conf.removePlayerDataWhenBanned && event.getReason().equals("Banned by admin.")) {
            if (badGuy.getRole() == Role.ADMIN)
                badGuy.getFaction().promoteNewLeader(badGuy);
            badGuy.leave(false);
            badGuy.detach();
        }
    }

    private static class InteractAttemptSpam {
        private int attempts = 0;

        private long lastAttempt = System.currentTimeMillis();

        public int increment() {
            long Now = System.currentTimeMillis();
            if (Now > this.lastAttempt + 2000L) {
                this.attempts = 1;
            } else {
                this.attempts++;
            }
            this.lastAttempt = Now;
            return this.attempts;
        }
    }
}
