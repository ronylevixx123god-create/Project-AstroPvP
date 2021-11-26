/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryCloseEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicduel.struct.newduel;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.inventory.CustomInventory;
import me.ifamasssxd.cosmicduel.inventory.CustomInventoryCallback;
import me.ifamasssxd.cosmicduel.inventory.CustomInventoryItem;
import me.ifamasssxd.cosmicduel.struct.Arena;
import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import me.ifamasssxd.cosmicduel.struct.QueueManager;
import me.ifamasssxd.cosmicduel.struct.enums.CancelReason;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.enums.Setting;
import me.ifamasssxd.cosmicduel.utils.ItemBuilder;
import me.ifamasssxd.cosmicduel.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class GlobalQueuePopInventory extends CustomInventory {
    public NewDuel duel;

    boolean p1Ready;

    boolean p2Ready;

    public boolean cancelled;

    private HashMap<String, Integer> closeAttempts;

    public GlobalQueuePopInventory(final NewDuel duel) {
        super("Accept Duel Queue", 1);
        this.p1Ready = false;
        this.p2Ready = false;
        this.cancelled = false;
        this.closeAttempts = new HashMap<>();
        this.duel = duel;
        setCallback(new CustomInventoryCallback() {
            public void inventoryCloseEvent(CustomInventory inventory, InventoryCloseEvent event) {
                if (GlobalQueuePopInventory.this.cancelled)
                    event.getPlayer().removeMetadata("in_ranked_queue_menu", (Plugin)CosmicDuel.get());
                if (duel != null && duel.getCreateState() != DuelCreateState.STAKES && !duel.isCancelled() && !GlobalQueuePopInventory.this.cancelled && !event.getPlayer().hasMetadata("close_exempt")) {
                    if (duel.isRanked() && duel.getPlayers().contains(event.getPlayer())) {
                        Player player = (Player)event.getPlayer();
                        if (GlobalQueuePopInventory.this.closeAttempts.containsKey(player.getName())) {
                            GlobalQueuePopInventory.this.closeAttempts.put(player.getName(), Integer.valueOf(((Integer)GlobalQueuePopInventory.this.closeAttempts.get(player.getName())).intValue() + 1));
                        } else {
                            GlobalQueuePopInventory.this.closeAttempts.put(player.getName(), Integer.valueOf(1));
                        }
                        if (((Integer)GlobalQueuePopInventory.this.closeAttempts.get(player.getName())).intValue() >= 10) {
                            GlobalQueuePopInventory.this.declineDuel(player, true, true);
                        } else {
                            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDuel.get(), () -> {
                                GlobalQueuePopInventory.this.openInventory((Player)event.getPlayer());
                                event.getPlayer().sendMessage(ChatColor.RED + "Please accept or decline the duel queue.");
                            },1L);
                        }
                        return;
                    }
                    GlobalQueuePopInventory.this.cancelled = true;
                    if (event.getPlayer().equals(duel.getPlayers().get(0))) {
                        duel.sendMessage(ChatColor.GRAY + "The duel has been cancelled and has been removed from queue.", false);
                        DuelAPI.cancelDuel(duel, CancelReason.CLOSE_QUEUE_POP);
                    } else {
                        duel.sendMessage(duel.getColoredMessage("&e&l(!) &e%s closed the &e&nQUEUE&e confirm menu.", new Object[] { event.getPlayer().getName() }), false);
                        Player player = (Player)event.getPlayer();
                        duel.players.remove(player);
                        QueueManager queueManager = CosmicDuel.get().getQueueManager();
                        duel.sendMessage(ChatColor.RED + player.getName() + " has declined the settings.", false);
                        queueManager.getGlobalDuelQueue().add(duel);
                        duel.sendMessage(ChatColor.RED + "You have been placed back into the Global Duel Queue.", false);
                        for (Player pl : duel.getPlayers())
                            pl.closeInventory();
                    }
                }
            }
        });
    }

    public boolean wasAccepted() {
        return (this.p1Ready && this.p2Ready);
    }

    public GlobalQueuePopInventory prepareQueue() {
        int index = 0;
        for (Player pl : this.duel.getPlayers()) {
            insertItem(index, getReadyItem(pl, index), true);
            index += 8;
        }
        for (int i = 0; i < getInventorySize(); i++) {
            if (getItem(i) == null || getItem(i).getType() == Material.AIR)
                insertItem(i, new CustomInventoryItem(ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", new String[] { " " })), true);
        }
        if (this.duel.isRanked())
            insertItem(4, new CustomInventoryItem(ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getWoolData(), ChatColor.RED + "Cancel", new String[] { ChatColor.GRAY + "Click to cancel queue." }), (inventory, item, event) -> {
                event.setCancelled(true);
                this.cancelled = true;
                Player player = (Player)event.getWhoClicked();
                declineDuel(player, true, true);
            }));
        return this;
    }

    public Player getWhoDidntAccept() {
        if (this.duel == null || this.duel.players.size() == 0)
            return null;
        if (!this.p2Ready && !this.p1Ready)
            return null;
        Player first = this.duel.players.get(0);
        if (this.p2Ready && !this.p1Ready)
            return first;
        if (this.duel.players.size() == 2) {
            Player second = this.duel.players.get(1);
            if (!this.p2Ready && this.p1Ready)
                return second;
        }
        return null;
    }

    public void declineDuel(Player whoDecline, boolean replaceQueue, boolean lockout) {
        whoDecline.removeMetadata("in_ranked_queue_menu", (Plugin)CosmicDuel.get());
        this.duel.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + whoDecline.getName() + " has left the Queue.", false);
        if (lockout)
            whoDecline.setMetadata("rank_queue_dodge", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), Long.valueOf(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5L))));
        Player opponent = this.duel.getOppositePlayer(whoDecline);
        QueueManager manager = CosmicDuel.get().getQueueManager();
        if (opponent != null) {
            opponent.removeMetadata("in_ranked_queue_menu", (Plugin)CosmicDuel.get());
            this.duel.players.remove(opponent);
        }
        DuelAPI.cancelDuel(this.duel, CancelReason.CLOSE_QUEUE_POP);
        if (opponent != null) {
            opponent.closeInventory();
            if (replaceQueue && !PlayerUtils.inCombat(opponent)) {
                manager.getRankedQueue().addPlayerToQueue(opponent);
                opponent.sendMessage(ChatColor.RED + "You have been placed back in the Ranked Queue.");
            }
        }
        whoDecline.closeInventory();
    }

    public void alertOfQueue() {
        updateInventoryItems();
        for (Player p : this.duel.players) {
            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 5.0F, 1.4F);
            openInventory(p);
        }
    }

    public CustomInventoryItem getReadyItem(Player p, int index) {
        List<String> lore = Lists.newArrayList(ChatColor.GRAY + "Click to accept the " + (this.duel.isRanked() ? "ranked" : "globally queued") + " duel.", "", ChatColor.YELLOW + ChatColor.BOLD
                .toString() + "Settings");
        for (Setting setting : Setting.values()) {
            if (this.duel.getSettings().hasSetting(setting)) {
                lore.add(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.GREEN + ChatColor.BOLD.toString() + "ON");
            } else {
                lore.add(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.RED + ChatColor.BOLD.toString() + "OFF");
            }
        }
        String extra = null;
        if (this.duel.isRanked())
            extra = ChatColor.GRAY + " (" + (int)DuelPlayer.getDuelPlayer(p).getElo() + ")";
        CustomInventoryItem item = new CustomInventoryItem(ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)4, ChatColor.YELLOW + ChatColor.BOLD.toString() + p.getName() + ((extra == null) ? "" : extra), lore));
        item.setCallback((inventory, customItem, event) -> {
            event.setCancelled(true);
            Player clicked = (Player)event.getWhoClicked();
            if (this.duel == null || this.duel.getDuelState() != DuelState.IN_QUEUE) {
                this.duel = null;
                CosmicDuel.logMessage("DUEL WASNT IN QUEUE OR WAS NULL!");
                return;
            }
            if (this.duel.getPlayers().size() < this.duel.getPlayersToStart())
                return;
            if (clicked.equals(p)) {
                if (index == 0) {
                    this.p1Ready = true;
                } else {
                    this.p2Ready = true;
                }
                customItem.setDurability((short)5);
                if (wasAccepted()) {
                    if (this.duel.isRanked()) {
                        this.cancelled = true;
                        for (Player player : this.duel.getPlayers())
                            player.closeInventory();
                        NewDuel currentlyActiveDuel = DuelAPI.getCurrentlyActiveDuel(this.duel.getArena());
                        if ((currentlyActiveDuel != null || this.duel.getArena() == null) && currentlyActiveDuel != this.duel) {
                            Arena arena = DuelAPI.getBestOpenRankedArena();
                            if (arena == null) {
                                arena = DuelAPI.getLeastPopulatedRankedQueue();
                                if (arena != null) {
                                    this.duel.setArena(arena);
                                    this.duel.setDuelState(DuelState.IN_QUEUE);
                                    this.duel.setCreateState(DuelCreateState.WAITING);
                                    DuelAPI.queueDuel(arena, this.duel);
                                    this.duel.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + "Your ranked match has been placed in the duel queue.", false);
                                    CosmicDuel.logMessage("Queueing RANKED Duel arena " + arena.getName() + " to use for duel #" + this.duel.getDuelID());
                                    return;
                                }
                            } else {
                                this.duel.setArena(arena);
                            }
                        }
                        this.duel.setDuelState(DuelState.STARTING);
                        this.duel.setCreateState(DuelCreateState.DONE);
                        this.duel.onStart();
                        return;
                    }
                    NewStakeInventory stakeInventory = new NewStakeInventory(this.duel, this.duel.getPlayers().get(0), this.duel.getPlayers().get(1));
                    this.duel.setCreateState(DuelCreateState.STAKES);
                    this.duel.setStakeInventory(stakeInventory);
                    DuelAPI.duels.add(this.duel);
                    for (Player pl : this.duel.getPlayers()) {
                        pl.setMetadata("close_exempt", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), ""));
                        pl.closeInventory();
                        pl.setMetadata("in_duel", (MetadataValue)new FixedMetadataValue((Plugin)CosmicDuel.get(), ""));
                        pl.removeMetadata("close_exempt", (Plugin)CosmicDuel.get());
                        if (pl.getUniqueId().equals(stakeInventory.getP1().getUniqueId())) {
                            pl.openInventory(stakeInventory.getPlayerOneInventory());
                            continue;
                        }
                        pl.openInventory(stakeInventory.getPlayerTwoInventory());
                    }
                    return;
                }
                updateInventoryItems();
            } else {
                clicked.sendMessage(ChatColor.RED + "Wrong Button.");
            }
        });
        return item;
    }
}
