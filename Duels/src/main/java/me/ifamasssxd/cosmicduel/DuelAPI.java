package me.ifamasssxd.cosmicduel;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.gmail.nossr50.api.PartyAPI;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.listeners.SpectateInventory;
import me.ifamasssxd.cosmicduel.struct.Arena;
import me.ifamasssxd.cosmicduel.struct.DuelInvite;
import me.ifamasssxd.cosmicduel.struct.Kit;
import me.ifamasssxd.cosmicduel.struct.QueueManager;
import me.ifamasssxd.cosmicduel.struct.enums.CancelReason;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.enums.Setting;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmicduel.utils.CollectionBin;
import me.ifamasssxd.cosmicduel.utils.JSONMessage;
import me.ifamasssxd.cosmicduel.utils.PlayerUtils;
import me.ifamasssxd.factionupgrades.utils.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.commands.CommandArkReboot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DuelAPI {
    public static int MAX_ARENA_QUEUE = 10;
    public static List<NewDuel> duels = new ArrayList<NewDuel>();
    public static List<DuelInvite> invite_timer = new ArrayList<DuelInvite>();
    private static final ConcurrentHashMap<Arena, LinkedList<NewDuel>> duel_queue = new ConcurrentHashMap();
    private static final String red = ChatColor.RED + ChatColor.BOLD.toString() + "(!) ";
    private static Boolean arkkit = null;
    private static final QueueManager queueManager = CosmicDuel.get().getQueueManager();

    public static void invitePlayer(NewDuel duel, Player inviter, Player invited) {
        if (CosmicDuel.get().getDuelToggles().hasDuelsToggled(invited)) {
            inviter.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + invited.getName() + " has duels toggled off.");
            inviter.sendMessage(ChatColor.GRAY + "The duel has been cancelled.");
            DuelAPI.cancelDuel(duel, CancelReason.TOGGLED_OFF_OPPONENT);
            return;
        }
        for (DuelInvite invite : DuelAPI.getAllDuelInvites(inviter, InviteType.INVITED)) {
            DuelAPI.declineDuel(inviter, invite.getInviter(), false);
        }
        inviter.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "Your Duel request has been sent.");
        inviter.playSound(inviter.getLocation(), Sound.LEVEL_UP, 1.0f, 1.4f);
        invited.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + inviter.getName() + " has invited you to a " + ChatColor.RED + ChatColor.UNDERLINE.toString() + duel.getName() + ChatColor.YELLOW + " Duel.");
        invited.sendMessage("     " + ChatColor.GRAY + "Click one of the following options below.");
        JSONMessage accept = new JSONMessage(ChatColor.YELLOW + "                ");
        accept.addRunCommand(ChatColor.GREEN + ChatColor.BOLD.toString() + "ACCEPT", ChatColor.YELLOW, "/duel accept " + inviter.getName(), ChatColor.GREEN + "/duel accept " + inviter.getName());
        accept.addText("    ", ChatColor.YELLOW);
        accept.addRunCommand(ChatColor.RED + ChatColor.BOLD.toString() + "DECLINE", ChatColor.YELLOW, "/duel decline " + inviter.getName(), ChatColor.RED + "/duel decline " + inviter.getName());
        accept.sendToPlayer(invited);
        invite_timer.add(new DuelInvite(30, inviter.getName(), invited.getName(), false));
    }

    public static int getDuelPosition(NewDuel d) {
        if (DuelAPI.getArenaQueueSize(d.getArena()) > 0) {
            int position = 1;
            for (NewDuel duel : DuelAPI.getArenaQueue(d.getArena())) {
                if (duel == null) continue;
                if (duel.equals(d)) {
                    return position;
                }
                ++position;
            }
        }
        return 0;
    }

    public static boolean isRebooting() {
        if (arkkit == null) {
            arkkit = Bukkit.getPluginManager().isPluginEnabled("Arkkit");
        }
        return arkkit != false && CommandArkReboot.restart_task_exists;
    }

    public static void sendSettings(Player p, String p_name) {
        if (!DuelAPI.hasPendingInvite(p)) {
            p.sendMessage(red + ChatColor.RED + "You do not have a pending duel invite.");
            return;
        }
        NewDuel d = DuelAPI.getPendingDuel(p, p_name);
        if (d == null) {
            p.sendMessage(red + ChatColor.RED + "That duel is no longer valid.");
            DuelInvite invalid = DuelAPI.getDuelInvite(p_name, p);
            if (invalid != null) {
                invite_timer.remove(invalid);
            }
            return;
        }
        Inventory inv = Bukkit.createInventory(null, 9, p_name + "'s Duel Settings");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("");
        for (Setting setting : Setting.values()) {
            lore.add(ChatColor.YELLOW + setting.getName() + ": " + (d.getSettings().hasSetting(setting) ? ChatColor.GREEN + ChatColor.BOLD.toString() + "ON" : ChatColor.RED + ChatColor.BOLD.toString() + "OFF"));
        }

            lore.add(ChatColor.YELLOW + "Kit: " + (d.getKit().equals(Kit.BYOB) ? ChatColor.RED + ChatColor.BOLD.toString() + d.getKit().getName() : ChatColor.GREEN + ChatColor.BOLD.toString() + d.getKit().getName()));
        lore.add(CC.Yellow + "Arena: " + CC.Green + (d.getArena() == null ? "N/A" : d.getArena().getName()));
        lore.add("");
        lore.add(ChatColor.GRAY + "Click to accept access the stakes menu.");
        inv.setItem(inv.getSize() - 5, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)5, ChatColor.YELLOW + ChatColor.BOLD.toString() + "ACCEPT DUEL", lore));
        for (int i = 0; i < inv.getSize(); ++i) {
            if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR) continue;
            inv.setItem(i, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", new String[]{" "}));
        }
        p.openInventory(inv);
    }

    public static NewDuel getPendingDuel(Player p, String inviter) {
        for (DuelInvite invite : invite_timer) {
            if (!invite.getInvited().equalsIgnoreCase(p.getName()) || !invite.getInviter().equalsIgnoreCase(inviter)) continue;
            Player pl = Bukkit.getPlayer(invite.getInviter());
            if (pl == null) {
                return null;
            }
            return DuelAPI.getDuel(pl);
        }
        return null;
    }

    public static boolean canDuel(Player p) {
        return !DuelAPI.hasDuel(p) && !DuelAPI.isWaitingOnInvite(p);
    }

    public static List<DuelInvite> getAllDuelInvites(Player p, InviteType inviteType) {
        ArrayList<DuelInvite> retr = new ArrayList<DuelInvite>();
        for (DuelInvite invite : invite_timer) {
            if ((inviteType != InviteType.INVITER || !invite.getInviter().equalsIgnoreCase(p.getName())) && (inviteType != InviteType.INVITED || !invite.getInvited().equalsIgnoreCase(p.getName()))) continue;
            retr.add(invite);
        }
        return retr;
    }

    public static DuelInvite getDuelInvite(Player p, InviteType inviteType) {
        for (DuelInvite invite : invite_timer) {
            if ((inviteType != InviteType.INVITER || !invite.getInviter().equalsIgnoreCase(p.getName())) && (inviteType != InviteType.INVITED || !invite.getInvited().equalsIgnoreCase(p.getName()))) continue;
            return invite;
        }
        return null;
    }

    public static DuelInvite getDuelInvite(String inviter, Player invited) {
        for (DuelInvite invite : invite_timer) {
            if (!invite.getInviter().equalsIgnoreCase(inviter) || !invite.getInvited().equalsIgnoreCase(invited.getName())) continue;
            return invite;
        }
        return null;
    }

    public static void acceptInvite(Player p, String inviter, boolean settings) {
        if (settings) {
            p.performCommand("duel settings " + inviter);
            DuelInvite d = DuelAPI.getDuelInvite(inviter, p);
            if (d == null) {
                p.sendMessage(ChatColor.RED + "That is not a valid duel request.");
                return;
            }
            d.setPending(true);
        } else {
            NewDuel d2 = DuelAPI.getPendingDuel(p, inviter);
            if (d2 == null) {
                p.sendMessage(ChatColor.RED + "That is not a valid duel request.");
                return;
            }
            d2.addPlayer(p, true);
            invite_timer.remove(DuelAPI.getDuelInvite(p, InviteType.INVITED));
            DuelInvite i = null;
            while ((i = DuelAPI.getDuelInvite(p, InviteType.INVITED)) != null) {
                Player pl = Bukkit.getPlayer(i.getInviter());
                if (pl != null) {
                    DuelAPI.declineDuel(p, pl.getName(), true);
                }
                invite_timer.remove(i);
            }
        }
    }

    public static void declineDuel(Player p, String inviter, boolean notify) {
        NewDuel d = DuelAPI.getPendingDuel(p, inviter);
        if (d == null) {
            p.sendMessage(ChatColor.RED + "That is not a valid duel request.");
            return;
        }
        if (notify) {
            d.sendMessage(red + ChatColor.RED + ChatColor.UNDERLINE.toString() + p.getName() + ChatColor.RED + " has declined the duel.", false);
        }
        DuelInvite duelInvite = DuelAPI.getDuelInvite(p, InviteType.INVITED);
        invite_timer.remove(duelInvite);
        p.sendMessage(red + ChatColor.RED + "You have declined the duel invite from " + duelInvite.getInviter() + ".");
        DuelAPI.cancelDuel(d, CancelReason.DECLINE_INVITE);
    }

    public static void startQueuedDuel(NewDuel duel, Arena arena) {
        if (duel == null) {
            return;
        }
        if (DuelAPI.getCurrentlyActiveDuel(arena) != null) {
            return;
        }
        LinkedList<NewDuel> duels = DuelAPI.getArenaQueue(arena);
        duels.remove(duel);
        duel_queue.put(arena, duels);
        for (NewDuel d : duels) {
            if (d.equals(duel) || d.getPlayers().size() != 2) continue;
            for (Player p : d.getPlayers()) {
                p.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "Duel Queue Update");
                if (d.getArena() == null) {
                    p.sendMessage(ChatColor.YELLOW + "You are #" + DuelAPI.getDuelPosition(d) + ChatColor.YELLOW + " in Queue " + ChatColor.YELLOW + "vs " + ChatColor.YELLOW + ChatColor.BOLD.toString() + d.getOppositePlayer(p).getName() + ".");
                } else {
                    p.sendMessage(ChatColor.YELLOW + "You are #" + DuelAPI.getDuelPosition(d) + ChatColor.YELLOW + " in Queue for the " + d.getArena().getName() + " Arena " + ChatColor.YELLOW + "vs " + ChatColor.YELLOW + ChatColor.BOLD.toString() + d.getOppositePlayer(p).getName() + ".");
                }
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 0.8f);
            }
        }
        if (duel.getPlayers().size() < duel.getPlayersToStart()) {
            System.out.println("Duel was trashed.");
            DuelAPI.cancelDuel(duel, CancelReason.NOT_ENOUGH_PLAYERS_QUEUE);
            DuelAPI.startQueuedDuel(DuelAPI.getNextDuel(arena), arena);
            return;
        }
        for (Player p2 : duel.getPlayers()) {
            if (!PlayerUtils.inCombat(p2)) continue;
            duel.sendMessage(ChatColor.RED + p2.getName() + ChatColor.YELLOW + " was in combat and the duel was cancelled.", true);
            DuelAPI.cancelDuel(duel, CancelReason.IN_COMBAT_QUEUE);
            DuelAPI.startQueuedDuel(DuelAPI.getNextDuel(arena), arena);
            return;
        }
        duel.setCreateState(DuelCreateState.QUEUE_POP);
        duel.onQueuePop();
    }

    public static boolean inSameDuel(Player p1, Player p2) {
        return DuelAPI.hasDuel(p1) && DuelAPI.hasDuel(p2) && DuelAPI.getDuel(p1).equals(DuelAPI.getDuel(p2));
    }

    public static synchronized void queueDuel(Arena arena, NewDuel duel) {
        LinkedList<NewDuel> duels = DuelAPI.getArenaQueue(arena);
        if (!duels.contains(duel)) {
            duels.add(duel);
        }
        duel_queue.put(arena, duels);
    }

    public static NewDuel getNextDuel(Arena arena) {
        LinkedList<NewDuel> duels = DuelAPI.getArenaQueue(arena);
        if (duels.isEmpty()) {
            return null;
        }
        return duels.getFirst();
    }

    public static Arena getLeastPopulatedRankedQueue() {
        Arena arena = null;
        int queueCount = 0;
        for (Arena area : Arena.arenas) {
            int queue;
            if (!area.isRankedArena() || (queue = DuelAPI.getArenaQueueSize(area)) > queueCount && arena != null) continue;
            arena = area;
            queueCount = queue;
        }
        return arena;
    }

    public static boolean doesArenaHaveQueue(Arena arena) {
        return DuelAPI.doesArenaHaveQueue(arena, true);
    }

    public static boolean doesArenaHaveQueue(Arena arena, boolean takeActive) {
        return DuelAPI.getArenaQueue(arena).size() > 0 || takeActive && DuelAPI.getCurrentlyActiveDuel(arena) != null;
    }

    public static LinkedList<NewDuel> getArenaQueue(Arena arena) {
        if (arena == null) {
            return new LinkedList<NewDuel>();
        }
        if (!duel_queue.containsKey(arena)) {
            duel_queue.put(arena, new LinkedList());
        }
        boolean changed = false;
        LinkedList<NewDuel> duels = duel_queue.get(arena);
        for (NewDuel duel : Lists.newArrayList(duels)) {
            if (duel.getPlayers().size() >= duel.getPlayersToStart() || duel.getDuelState() != DuelState.IN_QUEUE) continue;
            duels.remove(duel);
            changed = true;
            DuelAPI.cancelDuel(duel, CancelReason.NOT_ENOUGH_PLAYERS_QUEUE);
        }
        if (changed) {
            duel_queue.put(arena, duels);
        }
        return duels;
    }

    public static NewDuel getCurrentlyActiveDuel(Arena arena) {
        ArrayList deadDuels = Lists.newArrayList();
        for (NewDuel d : duels) {
            if (d.getArena() == null || d.getDuelState() == DuelState.ENDED || d.getCreateState() != DuelCreateState.DONE && d.getCreateState() != DuelCreateState.QUEUE_POP || !d.getArena().equals(arena)) continue;
            if (d.getPlayers().size() >= d.getPlayersToStart()) {
                return d;
            }
            if (d.getPlayers().size() != 0) continue;
            deadDuels.add(d);
            CosmicDuel.logMessage("Found DEAD duel with ID: " + d.getDuelID() + " stuck for arena: " + arena.getName() + ", clearing...");
        }
        deadDuels.forEach(duel -> duels.remove(duel));
        return null;
    }

    public static int getArenaQueueSize(Arena arena) {
        ArrayList<NewDuel> duels = new ArrayList<NewDuel>();
        duels.addAll(DuelAPI.getArenaQueue(arena));
        if (DuelAPI.getCurrentlyActiveDuel(arena) != null) {
            duels.add(DuelAPI.getCurrentlyActiveDuel(arena));
        }
        return duels.size();
    }

    public static boolean checkStatus(Player player, Player target) {
        QueueManager queueManager = CosmicDuel.get().getQueueManager();
        try {
            if (DuelAPI.isRebooting()) {
                player.sendMessage(ChatColor.RED + "You cannot duel while the server is rebooting.");
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (CosmicDuel.get().getDuelToggles().hasDuelsToggled(player)) {
            player.sendMessage(red + ChatColor.RED + "You have duels toggled off.");
            return false;
        }
        if (DuelAPI.isSpectator(player)) {
            player.sendMessage(red + ChatColor.RED + "You are spectating a duel.");
            player.sendMessage(ChatColor.GRAY + "Use /duel leave to leave the duel.");
            return false;
        }
        if (DuelAPI.isWaitingOnInvite(player)) {
            player.sendMessage(red + ChatColor.RED + "You must wait for your current invite to expire.");
            return false;
        }
        if (player.hasMetadata("in_ranked_queue_menu")) {
            player.sendMessage(ChatColor.RED + "You cannot do this while in queue.");
            return false;
        }
        NewDuel d = DuelAPI.getDuel(player);
        if (d != null) {
            Player opposite = d.getOppositePlayer(player);
            if (opposite != null) {
                if (d.getDuelState() == DuelState.IN_QUEUE && d.getArena() != null) {
                    player.sendMessage(red + ChatColor.RED + "You are #" + DuelAPI.getDuelPosition(d) + " in queue for the " + d.getArena().getName() + " Arena " + ChatColor.RED + "vs " + opposite.getName() + ".");
                    player.sendMessage(ChatColor.GRAY + "Use '/duel leave' to leave the queue.");
                } else {
                    player.sendMessage(red + ChatColor.RED + "You are already in a duel with " + d.getOppositePlayer(player).getName() + ".");
                    player.sendMessage(ChatColor.GRAY + "Use /duel leave to leave the duel.");
                }
            }
            return false;
        }
        if (queueManager.getPreparingDuel(player) != null) {
            player.sendMessage(red + ChatColor.RED + "You are currently preparing a global queue duel.");
            return false;
        }
        if (queueManager.isSearching(player)) {
            player.sendMessage(ChatColor.RED + "You are currently in the Global Duel Queue.");
            return false;
        }
        if (queueManager.getRankedQueue().isInRankedQueue(player)) {
            player.sendMessage(ChatColor.RED + "You are currently in the Ranked Duel Queue.");
            return false;
        }
        if (queueManager.getDuel(player) != null) {
            player.sendMessage(red + ChatColor.RED + "You are currently in the Global Duel Queue.");
            return false;
        }
        if (DuelAPI.getDuel(player) != null) {
            player.sendMessage(ChatColor.RED + "You are in a duel already.");
            return false;
        }
        if (CollectionBin.getCollectionItems(player).size() > 0) {
            player.sendMessage(red + ChatColor.RED + "You have me.astropvp.cratekeys.items in your collection bin and cannot duel.");
            player.sendMessage(ChatColor.GRAY + "Use " + ChatColor.RED + "/duel collect" + ChatColor.GRAY + " to collect your me.astropvp.cratekeys.items.");
            return false;
        }
        if (PlayerUtils.inCombat(player)) {
            player.sendMessage(ChatColor.RED + "You are in combat and cannot duel at this time.");
            return false;
        }
        if (queueManager.getPreparingDuel(target) != null) {
            player.sendMessage(red + ChatColor.RED + target.getName() + " is currently preparing a global queue duel.");
            return false;
        }
        if (queueManager.getDuel(target) != null) {
            player.sendMessage(red + ChatColor.RED + target.getName() + " is currently in the Global Duel Queue.");
            return false;
        }
        if (queueManager.isSearching(target)) {
            player.sendMessage(ChatColor.RED + target.getName() + " is currently in the Global Duel Queue.");
            return false;
        }
        if (queueManager.getRankedQueue().isInRankedQueue(target)) {
            player.sendMessage(ChatColor.RED + target.getName() + " is currently in the Global Duel Queue.");
            return false;
        }
        NewDuel targetDuel = DuelAPI.getDuel(target);
        if (targetDuel != null) {
            if (targetDuel.getCreateState() != DuelCreateState.DONE) {
                Player opposite2;
                if (targetDuel.getCreateState() == DuelCreateState.WAITING && DuelAPI.isDuelQueued(targetDuel) && (opposite2 = targetDuel.getOppositePlayer(target)) != null) {
                    player.sendMessage(red + ChatColor.RED + target.getName() + " is currently #" + DuelAPI.getDuelPosition(targetDuel) + " in queue for the " + targetDuel.getArena().getName() + " Arena " + ChatColor.YELLOW + "vs " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + opposite2.getName() + ".");
                    return false;
                }
                player.sendMessage(red + ChatColor.RED + target.getName() + " is currently creating a duel.");
                return false;
            }
            if (targetDuel.getDuelState() == DuelState.STARTING) {
                player.sendMessage(red + ChatColor.RED + target.getName() + ChatColor.RED + " is currently starting a Duel with " + ChatColor.RED + ChatColor.UNDERLINE + targetDuel.getOppositePlayer(target).getName() + ChatColor.RED + ".");
            } else if (targetDuel.getDuelState() == DuelState.PLAYING) {
                player.sendMessage(red + ChatColor.RED + target.getName() + ChatColor.RED + " is currently in a Duel with " + ChatColor.RED + ChatColor.UNDERLINE + targetDuel.getOppositePlayer(target).getName() + ChatColor.RED + ".");
            } else if (targetDuel.getDuelState() == DuelState.ENDING) {
                player.sendMessage(red + ChatColor.RED + target.getName() + ChatColor.RED + " is currently ending a Duel with " + ChatColor.RED + ChatColor.UNDERLINE + targetDuel.getOppositePlayer(target).getName() + ChatColor.RED + ".");
            } else {
                player.sendMessage(red + ChatColor.RED + target.getName() + ChatColor.RED + " is currently in a Duel.");
            }
            return false;
        }
        if (CosmicDuel.get().getDuelToggles().hasDuelsToggled(target)) {
            player.sendMessage(red + ChatColor.RED + "That player has duel invites toggled off.");
            return false;
        }
        if (DuelAPI.isWaitingOnInvite(target)) {
            DuelInvite invite = DuelAPI.getDuelInvite(player, InviteType.INVITER);
            if (invite == null) {
                player.sendMessage(ChatColor.RED + "That player already has a pending invite.");
            } else {
                player.sendMessage(red + ChatColor.RED + "That player already has a pending invite to " + invite.getInvited() + ".");
            }
            return false;
        }
        if (DuelAPI.isSpectator(target)) {
            player.sendMessage(red + ChatColor.RED + "That player is currently spectating a duel.");
            return false;
        }
        if (target.hasMetadata("in_ranked_queue_menu")) {
            player.sendMessage(red + ChatColor.RED + "This player is currently in a ranked queue.");
            return false;
        }
        if (PlayerUtils.inCombat(target)) {
            player.sendMessage(ChatColor.RED + "That player is in combat and cannot duel at this time.");
            return false;
        }
        if (CollectionBin.getCollectionItems(target).size() > 0) {
            player.sendMessage(red + ChatColor.RED + "That player has me.astropvp.cratekeys.items in their collection bin and cannot duel.");
            return false;
        }
        if (CosmicDuel.mcMMOEnabled && PartyAPI.inSameParty(player, target)) {
            player.sendMessage(red + ChatColor.RED + "You are in the same party as that player.");
            player.sendMessage(ChatColor.GRAY + "Use /party leave to leave your current party.");
            return false;
        }
        return true;
    }

    public static boolean isDuelQueued(NewDuel d) {
        if (d == null) {
            return false;
        }
        LinkedList<NewDuel> queue = DuelAPI.getArenaQueue(d.getArena());
        if (queue.isEmpty()) {
            return false;
        }
        for (NewDuel duel : queue) {
            if (duel.getDuelID() != d.getDuelID()) continue;
            return true;
        }
        return false;
    }

    public static void cancelDuel(NewDuel duel, CancelReason cancelReason) {
        Bukkit.getLogger().info("Cancelling duel for " + cancelReason);
        if (duel == null || duel.isCancelled()) {
            return;
        }
        Arena arena = duel.getArena();
        if (arena != null) {
            NewDuel nextDuelInQueue;
            if (DuelAPI.isDuelCurrentlyQueued(duel, arena)) {
                if (duel.getDuelState() == DuelState.IN_QUEUE) {
                    LinkedList<NewDuel> currentQueue = DuelAPI.getArenaQueue(arena);
                    currentQueue.remove(duel);
                    duel_queue.put(arena, currentQueue);
                    CosmicDuel.logMessage("Removing duel " + duel.getDuelID() + " from queue list.");
                    CosmicDuel.logMessage("Returning stakes from left queue.");
                    if (!duel.isRanked()) {
                        duel.returnStakes();
                    }
                }
            } else if (duel.getCreateState() == DuelCreateState.STAKES || duel.isInQueueMenu()) {
                duel.returnStakes();
            }
            if (DuelAPI.doesArenaHaveQueue(arena) && (nextDuelInQueue = DuelAPI.getNextDuel(arena)) != null && cancelReason != CancelReason.RELOAD) {
                DuelAPI.startQueuedDuel(nextDuelInQueue, arena);
            }
        }
        duel.setCancelled(true);
        for (Player p : duel.getPlayers()) {
            if (p == null) continue;
            p.removeMetadata("in_duel", CosmicDuel.get());
            p.closeInventory();
        }
        duel.clearSpectators();
        duel.getPlayers().clear();
        duel.getSpectators().clear();
        duel.getStakes().clear();
        duel.resetEnvoys();
        duels.remove(duel);
        SpectateInventory.updateInventory();
    }

    public static boolean isDuelCurrentlyQueued(NewDuel duel, Arena arena) {
        for (NewDuel nDuel : DuelAPI.getArenaQueue(arena)) {
            if (duel.getDuelID() != nDuel.getDuelID()) continue;
            return true;
        }
        return false;
    }

    public static Arena getBestOpenRankedArena() {
        Arena arena = null;
        for (Arena area : Arena.arenas) {
            if (!area.isRankedArena() || DuelAPI.doesArenaHaveQueue(area)) continue;
            arena = area;
            break;
        }
        return arena;
    }

    public static boolean isWaitingOnInvite(Player p) {
        return DuelAPI.getDuelInvite(p, InviteType.INVITER) != null;
    }

    public static boolean hasPendingInvite(Player p) {
        return DuelAPI.getDuelInvite(p, InviteType.INVITED) != null;
    }

    public static NewDuel getDuelSpectating(Player p) {
        for (NewDuel d : duels) {
            if (!d.getSpectators().contains(p)) continue;
            return d;
        }
        return null;
    }

    public static boolean isSpectator(Player p) {
        return p.hasMetadata("spectator");
    }

    public static boolean hasDuel(Player p) {
        if (p.hasMetadata("in_duel")) {
            if (queueManager.getPreparingDuel(p) != null || queueManager.getDuel(p) != null || queueManager.isSearching(p) || queueManager.getRankedQueue().isInRankedQueue(p)) {
                return false;
            }
            NewDuel d = DuelAPI.getDuel(p);
            if (d == null) {
                p.removeMetadata("in_duel", CosmicDuel.get());
                return false;
            }
        }
        return p.hasMetadata("in_duel");
    }

    public static boolean isInArenaQueue(Player player) {
        for (List<NewDuel> list : duel_queue.values()) {
            for (NewDuel duel : list) {
                if (!duel.getPlayers().contains(player)) continue;
                return true;
            }
        }
        return false;
    }

    public static boolean isDuelValid(NewDuel d) {
        return d.getDuelState() != DuelState.WAITING && d.getDuelState() != DuelState.IN_QUEUE && d.getCreateState() == DuelCreateState.DONE;
    }

    public static NewDuel getDuel(Player p) {
        for (NewDuel duel : duels) {
            if (!duel.getPlayers().contains(p)) continue;
            return duel;
        }
        return null;
    }

    public enum InviteType {
        INVITER,
        INVITED

    }
}

