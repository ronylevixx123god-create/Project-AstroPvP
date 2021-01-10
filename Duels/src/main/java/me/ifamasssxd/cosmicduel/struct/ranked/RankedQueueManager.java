/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicduel.struct.ranked;

import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.Arena;
import me.ifamasssxd.cosmicduel.struct.DuelInvite;
import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import me.ifamasssxd.cosmicduel.struct.Kit;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.newduel.ClassicDuel;
import me.ifamasssxd.cosmicduel.struct.newduel.GlobalQueuePopInventory;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmicduel.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RankedQueueManager {
    private LinkedHashMap<NewDuel, Integer> duelsInQueue = new LinkedHashMap();
    private LinkedHashMap<UUID, Long> searching = new LinkedHashMap();
    private HashMap<UUID, Long> messages = new HashMap();

    public void addPlayerToQueue(Player player) {
        this.searching.put(player.getUniqueId(), System.currentTimeMillis());
        for (DuelInvite invite : DuelAPI.getAllDuelInvites(player, DuelAPI.InviteType.INVITED)) {
            DuelAPI.declineDuel(player, invite.getInviter(), false);
        }
    }

    public void updateQueue() {
        Player pl;
        ArrayList<UUID> invalids = Lists.newArrayList();
        block0: for (Map.Entry<UUID, Long> player : this.searching.entrySet()) {
            if (!this.searching.containsKey(player.getKey()) || (pl = Bukkit.getPlayer((UUID)player.getKey())) == null || invalids.contains(player.getKey()) || pl.hasMetadata("in_ranked_queue_menu") && pl.getMetadata("in_ranked_queue_menu").size() > 0) continue;
            if (PlayerUtils.inCombat(pl)) {
                pl.sendMessage((Object)ChatColor.RED + "You have been removed from queue due to being in combat.");
                invalids.add(player.getKey());
                continue;
            }
            long timeInQueue = System.currentTimeMillis() - player.getValue();
            int secondsInQueue = (int)((double)timeInQueue / 1000.0);
            int threshHold = this.getEloThreshhold(secondsInQueue);
            DuelPlayer duelPlayer = DuelPlayer.getDuelPlayer(pl);
            if (duelPlayer == null) continue;
            double elo = duelPlayer.getElo();
            for (UUID otherPlayer : this.searching.keySet()) {
                DuelPlayer dp;
                double otherElo;
                Player p;
                if (!this.searching.containsKey(otherPlayer) || invalids.contains(otherPlayer) || invalids.contains(pl.getUniqueId()) || (p = Bukkit.getPlayer((UUID)otherPlayer)) == null || pl.equals((Object)p) || p.hasMetadata("in_ranked_queue_menu") && p.getMetadata("in_ranked_queue_menu").size() > 0 || (otherElo = (dp = DuelPlayer.getDuelPlayer(p)).getElo()) < elo - (double)threshHold || otherElo > elo + (double)threshHold) continue;
                if (!PlayerUtils.inCombat(p)) {
                    invalids.add(p.getUniqueId());
                    invalids.add(pl.getUniqueId());
                    this.startRankedDuel(pl, p);
                    Bukkit.getLogger().info("Pairing " + pl.getName() + " (" + elo + ") with " + p.getName() + " (" + otherElo + ") with queue time of " + timeInQueue + "ms THRESHOLD: " + threshHold);
                    continue block0;
                }
                p.sendMessage((Object)ChatColor.RED + "You have been removed from queue due to being in combat.");
                invalids.add(p.getUniqueId());
            }
        }
        for (UUID invalid : invalids) {
            pl = Bukkit.getPlayer((UUID)invalid);
            this.searching.remove(pl == null ? invalid : pl.getUniqueId());
        }
        int position = 0;
        for (UUID id : this.searching.keySet()) {
            long msg;
            Player p2 = Bukkit.getPlayer((UUID)id);
            if (p2 == null) continue;
            ++position;
            if (this.messages.containsKey(p2.getUniqueId()) && (msg = this.messages.get(p2.getUniqueId()).longValue()) > System.currentTimeMillis()) continue;
            this.messages.put(p2.getUniqueId(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(15L));
            p2.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "You are currently " + (Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "#" + position + (Object)ChatColor.GREEN + " in the Ranked Duel Queue.");
            long time = (System.currentTimeMillis() - this.searching.get(id)) / 1000L;
            if (time < 60L) continue;
            p2.sendMessage((Object)ChatColor.GRAY + "It is taking longer than usual to find an opponent near your ELO...");
        }
    }

    public void startRankedDuel(final Player player, final Player player2) {
        this.messages.remove(player.getUniqueId());
        this.messages.remove(player2.getUniqueId());
        ClassicDuel ranked = new ClassicDuel(player, Lists.newArrayList(), true);
        ((NewDuel)ranked).addPlayer(player2, false);
        Arena arena = null;
        for (Arena area : Arena.arenas) {
            if (!area.isRankedArena() || DuelAPI.doesArenaHaveQueue(area)) continue;
            arena = area;
            break;
        }
        ranked.setKit(Kit.GODSET);
        if (arena != null) {
            ranked.setArena(arena);
            DuelAPI.duels.add(ranked);
            final GlobalQueuePopInventory inventory = new GlobalQueuePopInventory(ranked);
            player.setMetadata("in_ranked_queue_menu", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)inventory));
            player2.setMetadata("in_ranked_queue_menu", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), (Object)inventory));
            ranked.setDuelState(DuelState.IN_QUEUE);
            ranked.setCreateState(DuelCreateState.QUEUE_POP);
            CosmicDuel.logMessage("Found arena " + arena.getName() + " to use for duel #" + ranked.getDuelID());
            inventory.prepareQueue().alertOfQueue();
            new BukkitRunnable(){

                public void run() {
                    if (player.isOnline() && player2.isOnline() && !inventory.wasAccepted() && !inventory.cancelled) {
                        inventory.cancelled = true;
                        Player whoDidntAccept = inventory.getWhoDidntAccept();
                        if (whoDidntAccept == null) {
                            whoDidntAccept = player;
                        }
                        inventory.declineDuel(whoDidntAccept, true, true);
                    }
                }
            }.runTaskLater((Plugin)CosmicDuel.get(), 600L);
            return;
        }
        ranked.sendMessage((Object)ChatColor.RED + "No available arenas found, placing in queue.", false);
        arena = DuelAPI.getLeastPopulatedRankedQueue();
        if (arena == null) {
            Bukkit.getLogger().info("Unable to find any arenas available to duel in for " + player.getName() + " and " + player2.getName());
            return;
        }
        DuelAPI.duels.add(ranked);
        ranked.setDuelState(DuelState.IN_QUEUE);
        ranked.setCreateState(DuelCreateState.WAITING);
        DuelAPI.queueDuel(arena, ranked);
        ranked.setArena(arena);
        ranked.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "Your ranked match has been placed in the duel arena queue.", false);
        CosmicDuel.logMessage("Queueing RANKED Duel arena " + arena.getName() + " to use for duel #" + ranked.getDuelID());
    }

    public int getEloThreshhold(int seconds) {
        if (seconds < 30) {
            return 30;
        }
        if (seconds < 60) {
            return 50;
        }
        if (seconds < 90) {
            return 150;
        }
        if (seconds <= 120) {
            return 200;
        }
        if (seconds > 120 && seconds < 300) {
            return 500;
        }
        if (seconds > 500) {
            return 1000;
        }
        return 30;
    }

    public void removeFromQueue(Player player) {
        this.searching.remove(player.getUniqueId());
        this.messages.remove(player.getUniqueId());
    }

    public boolean isInRankedQueue(Player player) {
        return this.searching.containsKey(player.getUniqueId());
    }

    public LinkedHashMap<NewDuel, Integer> getDuelsInQueue() {
        return this.duelsInQueue;
    }

    public LinkedHashMap<UUID, Long> getSearching() {
        return this.searching;
    }
}

