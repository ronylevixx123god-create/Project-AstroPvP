/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  me.ifamasssxd.factionupgrades.utils.ItemBuilder
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package me.ifamasssxd.cosmicduel.struct.newduel;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.DuelAPI;
import me.ifamasssxd.cosmicduel.struct.Kit;
import me.ifamasssxd.cosmicduel.struct.enums.CancelReason;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.enums.Setting;
import me.ifamasssxd.cosmicduel.utils.CollectionBin;
import me.ifamasssxd.cosmicduel.utils.PlayerUtils;
import me.ifamasssxd.factionupgrades.utils.ItemBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class NewStakeInventory {
    public static String INVENTORY_NAME = "    Host                   Rival";
    Inventory inventory;
    Player p1;
    Player p2;
    NewDuel duel;
    ItemStack pendingItem;
    ItemStack acceptedItem;
    boolean modified = false;
    HashMap<String, PlayerType> playerTypes = new HashMap();
    LinkedHashMap<Integer, Integer> validSlots = new LinkedHashMap<Integer, Integer>(){
        private static final long serialVersionUID = 1L;
        {
            this.put(1, 5);
            this.put(2, 6);
            this.put(3, 7);
            this.put(9, 14);
            this.put(10, 15);
            this.put(11, 16);
            this.put(12, 17);
            this.put(18, 23);
            this.put(19, 24);
            this.put(20, 25);
            this.put(21, 26);
        }
    };
    BukkitTask runnable = null;

    public NewStakeInventory(NewDuel duel, Player p1, Player p2) {
        this.duel = duel;
        this.p1 = p1;
        this.p2 = p2;
        this.playerTypes.put(p1.getName(), PlayerType.ONE);
        this.playerTypes.put(p2.getName(), PlayerType.TWO);
        this.inventory = Bukkit.createInventory(null, 54, NewStakeInventory.getTradeInventoryTitle(p1.getName(), p2.getName()));
        ArrayList lore = Lists.newArrayList((Object[])new String[]{"", ChatColor.YELLOW + ChatColor.BOLD.toString() + "Settings"});
        for (Setting setting : Setting.values()) {
            if (duel.getSettings().hasSetting(setting)) {
                lore.add(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.GREEN + ChatColor.BOLD.toString() + "ON");
                continue;
            }
            lore.add(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.RED + ChatColor.BOLD.toString() + "OFF");
        }
        if (this.duel.getKit() == Kit.GKIT) {
            lore.add(ChatColor.GREEN + ChatColor.BOLD.toString() + "GKit: " + ChatColor.GREEN + StringUtils.capitalize(this.duel.getCustomGKit().getKitName()));
        } else {
            lore.add(ChatColor.GREEN + ChatColor.BOLD.toString() + "Kit: " + ChatColor.GREEN + StringUtils.capitalize(this.duel.getKit().getName(true)));
        }
        lore.add(CC.GreenB + "Arena: " + CC.Green + (this.duel.getArena() == null ? "N/A" : this.duel.getArena().getName()));
        this.pendingItem = ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)14, ChatColor.YELLOW + "Click to ACCEPT Stake", lore);
        this.acceptedItem = ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)5, ChatColor.YELLOW + "Stake ACCEPTED", lore);
        this.prepareStake(this.inventory);
    }

    public void prepareStake(Inventory inv) {
        inv.setItem(0, this.pendingItem);
        inv.setItem(8, this.pendingItem);
        for (int i = 4; i < inv.getSize(); i += 9) {
            inv.setItem(i, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", new String[]{" "}));
        }
    }

    private static String getTradeInventoryTitle(String lPName, String rPName) {
        String title = lPName;
        while (title.length() + rPName.length() < 32) {
            title = title + " ";
        }
        return (title = title + rPName).length() <= 32 ? title : title.substring(0, 32);
    }

    public void onInventoryEvent(InventoryClickEvent e) {
        e.setCancelled(true);
        e.setResult(Event.Result.DENY);
        if (this.getDuel().getCreateState() != DuelCreateState.STAKES) {
            return;
        }
        Player p = (Player)e.getWhoClicked();
        PlayerType pt = this.playerTypes.get(p.getName());
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        if (e.getRawSlot() < e.getInventory().getSize()) {
            if (e.getRawSlot() == 0 && pt == PlayerType.ONE) {
                this.modified = true;
                if (this.inventory.getItem(0).equals(this.pendingItem)) {
                    this.inventory.setItem(0, this.acceptedItem);
                    this.checkDone();
                } else {
                    this.inventory.setItem(0, this.pendingItem);
                }
            } else if (e.getRawSlot() == 8 && pt == PlayerType.TWO) {
                this.modified = true;
                if (this.inventory.getItem(8).equals(this.pendingItem)) {
                    this.inventory.setItem(8, this.acceptedItem);
                    this.checkDone();
                } else {
                    this.inventory.setItem(8, this.pendingItem);
                }
            }
            if (!this.validSlots(pt).contains(e.getRawSlot())) {
                return;
            }
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage(ChatColor.RED + "Your inventory is full.");
                return;
            }
            this.modified = true;
            ItemStack is = e.getCurrentItem();
            e.getInventory().setItem(e.getRawSlot(), null);
            p.getInventory().addItem(is);
            p.updateInventory();
            if (this.isSomeoneReady()) {
                this.resetAcceptButtons();
            }
        } else {
            if (e.getRawSlot() < e.getInventory().getSize()) {
                return;
            }
            if (this.isFull(pt, this.inventory)) {
                p.sendMessage(ChatColor.RED + "The Stake inventory is full.");
                p.sendMessage(ChatColor.RED + "Please remove some stake items to make room.");
                return;
            }
            this.modified = true;
            ItemStack is = e.getCurrentItem();
            p.getInventory().removeItem(is);
            p.updateInventory();
            int firstEmpty = this.getFirstEmpty(pt, this.inventory);
            if (firstEmpty == -1) {
                return;
            }
            this.inventory.setItem(firstEmpty, is);
            if (is != null) {
                CosmicDuel.logMessage("Adding Stake item for " + p.getName() + " -> " + is.toString());
            }
            if (this.isSomeoneReady()) {
                this.resetAcceptButtons();
            }
        }
    }

    public void resetAcceptButtons() {
        this.p1.playSound(this.p1.getLocation(), Sound.ORB_PICKUP, 0.4f, 0.4f);
        this.p2.playSound(this.p2.getLocation(), Sound.ORB_PICKUP, 0.4f, 0.4f);
        this.inventory.setItem(0, this.pendingItem);
        this.inventory.setItem(8, this.pendingItem);
    }

    public boolean isSomeoneReady() {
        return this.inventory.getItem(0).equals(this.acceptedItem) || this.inventory.getItem(8).equals(this.acceptedItem);
    }

    public void checkDone() {
        if (this.runnable != null) {
            return;
        }
        if (this.inventory.getItem(0).equals(this.acceptedItem) && this.inventory.getItem(8).equals(this.acceptedItem)) {
            this.modified = false;
            this.runnable = new BukkitRunnable(){
                final AtomicInteger time = new AtomicInteger(6);

                public void run() {
                    if (NewStakeInventory.this.runnable.getTaskId() != this.getTaskId()) {
                        this.cancel();
                        return;
                    }
                    NewDuel duel = NewStakeInventory.this.getDuel();
                    if (duel.isCancelled()) {
                        this.cancel();
                        NewStakeInventory.this.runnable = null;
                        return;
                    }
                    if (NewStakeInventory.this.modified) {
                        NewStakeInventory.this.modified = false;
                        NewStakeInventory.this.runnable = null;
                        this.cancel();
                        NewStakeInventory.this.setMiddle(ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", new String[]{""}));
                        NewStakeInventory.this.resetAcceptButtons();
                        return;
                    }
                    if (this.time.decrementAndGet() <= 0) {
                        this.cancel();
                        for (Player p : duel.getPlayers()) {
                            if (!PlayerUtils.inCombat(p)) continue;
                            duel.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + p.getName() + " was in combat and cancelled the duel.", false);
                            DuelAPI.cancelDuel(duel, CancelReason.IN_COMBAT_STAKES);
                            return;
                        }
                        List<ItemStack> stakes = NewStakeInventory.this.getStakes(PlayerType.ONE);
                        List<ItemStack> stakesTwo = NewStakeInventory.this.getStakes(PlayerType.TWO);
                        duel.getStakes().put(NewStakeInventory.this.p1.getUniqueId(), stakes);
                        duel.getStakes().put(NewStakeInventory.this.p2.getUniqueId(), stakesTwo);
                        if (DuelAPI.getArenaQueueSize(duel.getArena()) > 0 || DuelAPI.getCurrentlyActiveDuel(duel.getArena()) != null) {
                            duel.setCreateState(DuelCreateState.WAITING);
                            duel.duelState = DuelState.IN_QUEUE;
                            duel.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "(!) " + ChatColor.YELLOW + "This Arena currently has a queue of " + ChatColor.AQUA + ChatColor.BOLD.toString() + DuelAPI.getArenaQueueSize(duel.getArena()) + " Duel(s).", true);
                            duel.sendMessage(ChatColor.GRAY + "You must wait for the current duels to be over or you can use '/duel leave' to retrieve your items back.", true);
                            NewStakeInventory.this.p1.closeInventory();
                            NewStakeInventory.this.p2.closeInventory();
                            DuelAPI.queueDuel(duel.getArena(), duel);
                            return;
                        }
                        duel.setCreateState(DuelCreateState.DONE);
                        NewStakeInventory.this.p1.closeInventory();
                        NewStakeInventory.this.p2.closeInventory();
                        duel.onStart();
                        CosmicDuel.logMessage("Starting duel with " + NewStakeInventory.this.p1.getName() + " -> " + stakes.toString() + " " + NewStakeInventory.this.p2.getName() + " stakes -> " + stakesTwo.toString() + "!");
                    } else {
                        for (Player p : duel.getPlayers()) {
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.4f);
                        }
                        short data = 13;
                        switch (this.time.get()) {
                            case 1: 
                            case 2: {
                                data = 14;
                                break;
                            }
                            case 3: 
                            case 4: {
                                data = 1;
                                break;
                            }
                            case 5: {
                                data = 4;
                            }
                        }
                        ItemStack is = ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, data, ChatColor.YELLOW + ChatColor.BOLD.toString() + this.time.get(), new String[]{""});
                        is.setAmount(this.time.get());
                        NewStakeInventory.this.setMiddle(is);
                    }
                }
            }.runTaskTimer(CosmicDuel.get(), 20L, 20L);
        }
    }

    public void setMiddle(ItemStack is) {
        this.inventory.setItem(4, is);
        this.inventory.setItem(13, is);
        this.inventory.setItem(22, is);
    }

    public List<ItemStack> getStakes(PlayerType pt) {
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        for (int i : this.validSlots(pt)) {
            if (this.inventory.getItem(i) == null) continue;
            items.add(this.inventory.getItem(i));
        }
        return items;
    }

    public void closeInventory() {
        for (String p_name : this.playerTypes.keySet()) {
            PlayerType pt = this.playerTypes.get(p_name);
            Player p = Bukkit.getPlayer(p_name);
            if (p == null) continue;
            for (ItemStack is : this.getStakes(pt)) {
                if (p.getInventory().firstEmpty() == -1) {
                    CollectionBin.addItem(p, is);
                } else {
                    p.getInventory().addItem(is);
                }
                p.updateInventory();
            }
            p.closeInventory();
        }
        this.inventory.clear();
    }

    public int getFirstEmpty(PlayerType pt, Inventory inv) {
        for (int i : this.validSlots(pt)) {
            if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR) continue;
            return i;
        }
        return -1;
    }

    public boolean isFull(PlayerType pt, Inventory inv) {
        int amount = 0;
        for (int i : this.validSlots(pt)) {
            if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) continue;
            ++amount;
        }
        return amount >= this.validSlots(pt).size();
    }

    public boolean isEmpty(Inventory inv) {
        for (ItemStack is : inv) {
            if (is == null || is.getType() == Material.AIR) continue;
            return false;
        }
        return true;
    }

    public List<Integer> validSlots(PlayerType pt) {
        if (pt == PlayerType.ONE) {
            return this.validSlots.keySet().stream().collect(Collectors.toList());
        }
        if (pt == PlayerType.TWO) {
            return this.validSlots.values().stream().collect(Collectors.toList());
        }
        return null;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Player getP1() {
        return this.p1;
    }

    public Player getP2() {
        return this.p2;
    }

    public NewDuel getDuel() {
        return this.duel;
    }

    public enum PlayerType {
        ONE,
        TWO

    }
}

