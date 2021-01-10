/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.struct;

import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.newduel.ClassicDuel;
import me.ifamasssxd.cosmicduel.struct.newduel.GlobalQueuePopInventory;
import me.ifamasssxd.cosmicduel.struct.newduel.NewDuel;
import me.ifamasssxd.cosmicduel.struct.ranked.RankedQueueManager;
import me.ifamasssxd.cosmicduel.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class QueueManager {
    private LinkedList<NewDuel> globalDuelQueue = new LinkedList();
    private LinkedList<NewDuel> pendingDuelQueue = new LinkedList();
    private LinkedList<Player> searching = new LinkedList();
    private RankedQueueManager rankedQueue = new RankedQueueManager();
    long lastSent = System.currentTimeMillis();

    public void queuePlayer(Player player) {
        for (DuelInvite invite : DuelAPI.getAllDuelInvites(player, DuelAPI.InviteType.INVITED)) {
            DuelAPI.declineDuel(player, invite.getInviter(), false);
        }
        if (this.globalDuelQueue.size() > 0) {
            NewDuel duel = this.globalDuelQueue.remove();
            player.sendMessage((Object)ChatColor.GREEN + "Pairing with duel partner " + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + duel.getPlayers().get(0).getName() + "!");
            duel.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "Found opponent " + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + player.getName() + (Object)ChatColor.GREEN + ", please accept the queue.", false);
            duel.addPlayer(player, false);
            new GlobalQueuePopInventory(duel).prepareQueue().alertOfQueue();
        } else {
            if (this.pendingDuelQueue.size() > 0) {
                this.searching.add(player);
                player.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "You have been added to the " + (Object)ChatColor.YELLOW + ChatColor.UNDERLINE.toString() + "Global Duel Queue.");
                player.sendMessage((Object)ChatColor.YELLOW + "Searching for a duel opponent...");
                return;
            }
            this.createQueuedDuel(player);
            player.sendMessage((Object)ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "No players found in queue, please create a duel.");
        }
    }

    public void queueDuel(NewDuel duel) {
        this.pendingDuelQueue.remove(duel);
        this.globalDuelQueue.add(duel);
        this.updateQueue();
    }

    public void updateQueue() {
        if (this.searching.size() > 0) {
            if (this.globalDuelQueue.size() > 0) {
                NewDuel currentDuel = this.globalDuelQueue.getFirst();
                Player searching = this.getSearching().remove();
                if (searching == null || searching.isDead() || PlayerUtils.inCombat(searching) || DuelAPI.hasDuel(searching) || DuelAPI.isSpectator(searching)) {
                    if (searching != null) {
                        searching.sendMessage((Object)ChatColor.RED + "You have been removed from the Global Duel Queue.");
                    }
                    this.updateQueue();
                    return;
                }
                currentDuel.addPlayer(searching, false);
                new GlobalQueuePopInventory(currentDuel).prepareQueue().alertOfQueue();
                this.globalDuelQueue.remove(currentDuel);
            } else if (this.pendingDuelQueue.size() <= 0) {
                Player player = this.getSearching().remove();
                player.sendMessage((Object)ChatColor.RED + "No players found in Global Duel Queue, please create a duel.");
                this.createQueuedDuel(player);
                return;
            }
            if (System.currentTimeMillis() - this.lastSent >= TimeUnit.SECONDS.toMillis(30L)) {
                this.lastSent = System.currentTimeMillis();
                for (Player pl : this.searching) {
                    if (pl == null) continue;
                    pl.sendMessage((Object)ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.YELLOW + "Searching for opponent in the " + ChatColor.YELLOW.toString() + "Global Duel Queue.");
                }
            }
        }
    }

    private void createQueuedDuel(Player player) {
        ClassicDuel duel = new ClassicDuel(player, Lists.newArrayList());
        duel.setGlobalQueued(true);
        this.pendingDuelQueue.add(duel);
        duel.setCreateState(DuelCreateState.SETTINGS);
        duel.getSettings().getSettings().openInventory(player);
    }

    public boolean isDuelQueued(NewDuel duel) {
        return this.globalDuelQueue.contains(duel);
    }

    public NewDuel getDuel(Player player) {
        for (NewDuel duel : this.globalDuelQueue) {
            if (!duel.getPlayers().contains((Object)player)) continue;
            return duel;
        }
        return null;
    }

    public NewDuel getPreparingDuel(Player player) {
        for (NewDuel duel : this.pendingDuelQueue) {
            if (!duel.getPlayers().contains((Object)player)) continue;
            return duel;
        }
        return null;
    }

    public boolean isInQueue(Player player) {
        for (NewDuel duel : this.globalDuelQueue) {
            if (!duel.getPlayers().contains((Object)player)) continue;
            return true;
        }
        return false;
    }

    public boolean isSearching(Player player) {
        return this.searching.contains((Object)player);
    }

    public boolean isQueued(Player player) {
        return this.isSearching(player) || this.isInQueue(player);
    }

    public void removePlayerFromQueue(Player player) {
        this.searching.remove((Object)player);
        player.removeMetadata("in_duel", (Plugin)CosmicDuel.get());
    }

    public void removeDuelFromQueue(NewDuel duel) {
        this.globalDuelQueue.remove(duel);
        for (Player pl : duel.getPlayers()) {
            pl.removeMetadata("in_duel", (Plugin)CosmicDuel.get());
        }
    }

    public void removeDuelFromPrepareQueue(NewDuel duel) {
        for (Player pl : duel.getPlayers()) {
            pl.removeMetadata("in_duel", (Plugin)CosmicDuel.get());
        }
        this.pendingDuelQueue.remove(duel);
    }

    public LinkedList<NewDuel> getGlobalDuelQueue() {
        return this.globalDuelQueue;
    }

    public LinkedList<NewDuel> getPendingDuelQueue() {
        return this.pendingDuelQueue;
    }

    public LinkedList<Player> getSearching() {
        return this.searching;
    }

    public RankedQueueManager getRankedQueue() {
        return this.rankedQueue;
    }
}

