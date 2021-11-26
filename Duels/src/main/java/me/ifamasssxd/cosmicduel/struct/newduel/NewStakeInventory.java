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
import me.ifamasssxd.cosmicduel.struct.TeamData;
import me.ifamasssxd.cosmicduel.struct.enums.CancelReason;
import me.ifamasssxd.cosmicduel.struct.enums.DuelCreateState;
import me.ifamasssxd.cosmicduel.struct.enums.DuelState;
import me.ifamasssxd.cosmicduel.struct.enums.Setting;
import me.ifamasssxd.cosmicduel.utils.CollectionBin;
import me.ifamasssxd.cosmicduel.utils.PlayerUtils;
import me.ifamasssxd.factionupgrades.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class NewStakeInventory {
    public void setSelected(ItemStack selected) {
        this.selected = selected;
    }

    public ItemStack getSelected() {
        return this.selected;
    }

    public Inventory getTeamInventory(Player player) {
        if (this.p1 == player)
            return this.teamALeaderInventory;
        if (this.p2 == player)
            return this.teamBLeaderInventory;
        return null;
    }

    public NewStakeInventory(NewDuel duel, Player p1, Player p2) {
        this.modified = false;
        this.playerTypes = new HashMap<>();
        this.validSlots = new LinkedHashMap<Integer, Integer>() {
            private static final long serialVersionUID = 1L;
        };
        this.runnable = null;
        this.duel = duel;
        this.p1 = p1;
        this.p2 = p2;
        this.playerTypes.put(p1.getName(), PlayerType.ONE);
        this.playerTypes.put(p2.getName(), PlayerType.TWO);
        this.playerOneInventory = Bukkit.createInventory(null, 81, getTradeInventoryTitle(p1.getName(), p2.getName()));
        this.playerTwoInventory = Bukkit.createInventory(null, 81, getTradeInventoryTitle(p1.getName(), p2.getName()));
        setupInventory(this.playerOneInventory, this.p2);
        setupInventory(this.playerTwoInventory, this.p1);
    }

    public NewStakeInventory(TeamDuel duel, TeamData teamA, TeamData teamB, Player teamALeader, Player teamBLeader) {
        this.modified = false;
        this.playerTypes = new HashMap<>();
        this.teamTypes = new HashMap<>();
        this.teamLeaderTypes = new HashMap<>();
        this.validSlots = new LinkedHashMap<Integer, Integer>() {
            private static final long serialVersionUID = 1L;
        };
        this.runnable = null;
        this.duel = duel;
        this.p1 = teamALeader;
        this.p2 = teamBLeader;
        this.playerTypes.put(this.p1.getName(), PlayerType.ONE);
        this.playerTypes.put(this.p2.getName(), PlayerType.TWO);
        this.teamA = teamA;
        this.teamB = teamB;
        this.teamTypes.put(teamA, PlayerType.TEAM_A);
        this.teamTypes.put(teamB, PlayerType.TEAM_B);
        this.teamLeaderTypes.put(Bukkit.getPlayer(teamA.getLeaderUUID()).getName(), PlayerType.TEAM_A);
        this.teamLeaderTypes.put(Bukkit.getPlayer(teamB.getLeaderUUID()).getName(), PlayerType.TEAM_B);
        this.teamALeaderInventory = Bukkit.createInventory(null, 90, getTradeInventoryTitle(Bukkit.getPlayer(teamA.getLeaderUUID()).getName() + "'s Team", Bukkit.getPlayer(teamB.getLeaderUUID()).getName()) + "'s Team");
        this.teamBLeaderInventory = Bukkit.createInventory(null, 90, getTradeInventoryTitle(Bukkit.getPlayer(teamA.getLeaderUUID()).getName() + "'s Team", Bukkit.getPlayer(teamB.getLeaderUUID()).getName()) + "'s Team");
        setupTeamInventory(this.teamALeaderInventory, Bukkit.getPlayer(teamB.getLeaderUUID()), teamB);
        setupTeamInventory(this.teamBLeaderInventory, Bukkit.getPlayer(teamA.getLeaderUUID()), teamA);
    }

    private void setupInventory(Inventory inventory, Player oppositePlayer) {
        List<String> lore = Lists.newArrayList("", ChatColor.YELLOW + ChatColor.BOLD.toString() + "Settings");
        for (Setting setting : Setting.values()) {
            if (this.duel.getSettings().hasSetting(setting)) {
                lore.add(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.GREEN + ChatColor.BOLD.toString() + "ON");
            } else {
                lore.add(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.RED + ChatColor.BOLD.toString() + "OFF");
            }
        }

        lore.add(CC.GreenB + "Arena: " + CC.Green + ((this.duel.getArena() == null) ? "N/A" : this.duel.getArena().getName()));
        this.pendingItem = ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)14, ChatColor.YELLOW + "Click to ACCEPT Stake", lore);
        this.acceptedItem = ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)5, ChatColor.YELLOW + "Stake ACCEPTED", lore);
        prepareStake(inventory, oppositePlayer);
    }

    private void setupTeamInventory(Inventory inventory, Player oppositePlayer, TeamData team) {
        List<String> lore = Lists.newArrayList("", ChatColor.YELLOW + ChatColor.BOLD.toString() + "Settings");
        for (Setting setting : Setting.values()) {
            if (this.duel.getSettings().hasSetting(setting)) {
                lore.add(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.GREEN + ChatColor.BOLD.toString() + "ON");
            } else {
                lore.add(ChatColor.YELLOW + setting.getName() + ": " + ChatColor.RED + ChatColor.BOLD.toString() + "OFF");
            }
        }

        lore.add(CC.GreenB + "Arena: " + CC.Green + ((this.duel.getArena() == null) ? "N/A" : this.duel.getArena().getName()));
        this.pendingItem = ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)14, ChatColor.YELLOW + "Click to ACCEPT Stake", lore);
        this.acceptedItem = ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)5, ChatColor.YELLOW + "Stake ACCEPTED", lore);
        prepareTeamStake(inventory, oppositePlayer, team);
    }

    public void prepareStake(Inventory inv, Player oppositePlayer) {
        inv.setItem(0, this.pendingItem);
        inv.setItem(8, this.pendingItem);
        int i;
        for (i = 4; i < inv.getSize() - 45; i += 9) {
            inv.setItem(i, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        }
        for (i = 0; i < 9; i++) {
            inv.setItem(27 + i, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        }
        inv.setItem(72, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        inv.setItem(73, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        inv.setItem(76, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        inv.setItem(79, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        inv.setItem(80, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        ItemStack[] armor = oppositePlayer.getEquipment().getArmorContents();
        ItemStack[] items = oppositePlayer.getInventory().getContents();
        for (int j = 0; j < items.length; j++) {
            ItemStack item = items[j];
            if (item != null && item.getType() != Material.AIR)
                item = item.clone();
            inv.setItem(36 + j, item);
        }
        inv.setItem(inv.getSize() - 7, armor[3]);
        inv.setItem(inv.getSize() - 6, armor[2]);
        inv.setItem(inv.getSize() - 4, armor[1]);
        inv.setItem(inv.getSize() - 3, armor[0]);
    }

    public void prepareTeamStake(Inventory inv, Player oppositePlayer, TeamData team) {
        List<Player> players = Lists.newArrayList();
        players.add(Bukkit.getPlayer(team.getLeaderUUID()));
        team.getTeamates().forEach(teammate -> players.add(Bukkit.getPlayer(teammate)));
        inv.setItem(0, this.pendingItem);
        inv.setItem(8, this.pendingItem);
        int i;
        for (i = 4; i < inv.getSize() - 45; i += 9) {
            inv.setItem(i, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        }
        for (i = 0; i < 9; i++) {
            inv.setItem(27 + i, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        }
        inv.setItem(72, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        inv.setItem(73, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        inv.setItem(76, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        inv.setItem(79, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        inv.setItem(80, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        for (i = 81; i < 90; i++) {
            inv.setItem(i, ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", " "));
        }
        setEnemyInventory(inv, oppositePlayer);
        int index = 10;
        for (Player player : players) {
            index--;
            ItemStack item = getSkullItem(player);
            if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase(oppositePlayer.getName())) {
                inv.setItem(inv.getSize() - index, getSelectedIcon(item));
                continue;
            }
            inv.setItem(inv.getSize() - index, item);
        }
    }

    public ItemStack getSelectedIcon(ItemStack icon) {
        Player player = Bukkit.getPlayer(ChatColor.stripColor(icon.getItemMeta().getDisplayName()));
        Material type = Material.matchMaterial("SKULL_ITEM");
        ItemStack item = new ItemStack(type, 1);
        SkullMeta meta = (SkullMeta)Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setDisplayName(CC.translate("&c&l" + player.getName()));
        List<String> lore = Lists.newArrayList();
        lore.add(CC.translate("&7Only a leader has permission to click on this icon"));
        lore.add(CC.translate("&7and let their team view the enemy's inventory contents."));
        lore.add(CC.translate("&7"));
        lore.add(CC.translate("&a&lSELECTED"));
        meta.setOwner(player.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack getSkullItem(Player player) {
        Material type = Material.matchMaterial("SKULL_ITEM");
        ItemStack item = new ItemStack(type, 1);
        SkullMeta meta = (SkullMeta)Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        meta.setDisplayName(CC.translate("&c&l" + player.getName()));
        List<String> lore = Lists.newArrayList();
        lore.add(CC.translate("&7Only a leader has permission to click on this icon"));
        lore.add(CC.translate("&7and let their team view the enemy's inventory contents."));
        meta.setOwner(player.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public void setEnemyInventory(Inventory inv, Player player) {
        ItemStack[] armor = player.getEquipment().getArmorContents();
        ItemStack[] items = player.getInventory().getContents();
        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            if (item != null && item.getType() != Material.AIR)
                item = item.clone();
            inv.setItem(36 + i, item);
        }
        inv.setItem(inv.getSize() - 9 - 7, armor[3]);
        inv.setItem(inv.getSize() - 9 - 6, armor[2]);
        inv.setItem(inv.getSize() - 9 - 4, armor[1]);
        inv.setItem(inv.getSize() - 9 - 3, armor[0]);
    }

    private static String getTradeInventoryTitle(String lPName, String rPName) {
        String title;
        for (title = lPName; title.length() + rPName.length() < 32; title = title + " ");
        title = title + rPName;
        return (title.length() <= 32) ? title : title.substring(0, 32);
    }

    public void onInventoryEvent(InventoryClickEvent e) {
        e.setCancelled(true);
        e.setResult(Event.Result.DENY);
        if (getDuel().getCreateState() != DuelCreateState.STAKES)
            return;
        Player p = (Player)e.getWhoClicked();
        PlayerType pt = this.playerTypes.get(p.getName());
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
            return;
        Inventory inventory = p.getUniqueId().equals(this.p1.getUniqueId()) ? this.playerOneInventory : this.playerTwoInventory;
        Inventory inventory2 = p.getUniqueId().equals(this.p1.getUniqueId()) ? this.playerTwoInventory : this.playerOneInventory;
        if (e.getRawSlot() < e.getInventory().getSize()) {
            if (e.getRawSlot() == 0 && pt == PlayerType.ONE) {
                this.modified = true;
                if (inventory.getItem(0).equals(this.pendingItem)) {
                    inventory.setItem(0, this.acceptedItem);
                    inventory2.setItem(0, this.acceptedItem);
                    checkDone();
                } else {
                    inventory.setItem(0, this.pendingItem);
                    inventory2.setItem(0, this.pendingItem);
                }
            } else if (e.getRawSlot() == 8 && pt == PlayerType.TWO) {
                this.modified = true;
                if (inventory.getItem(8).equals(this.pendingItem)) {
                    inventory.setItem(8, this.acceptedItem);
                    inventory2.setItem(8, this.acceptedItem);
                    checkDone();
                } else {
                    inventory.setItem(8, this.pendingItem);
                    inventory2.setItem(8, this.pendingItem);
                }
            }
            if (!validSlots(pt).contains(Integer.valueOf(e.getRawSlot())))
                return;
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage(ChatColor.RED + "Your inventory is full.");
                return;
            }
            this.modified = true;
            ItemStack is = e.getCurrentItem();
            this.playerOneInventory.setItem(e.getRawSlot(), null);
            this.playerTwoInventory.setItem(e.getRawSlot(), null);
            p.getInventory().addItem(is);
            System.out.println("B");
            p.updateInventory();
            this.p2.updateInventory();
            if (isSomeoneReady()) {
                System.out.println("C");
                resetAcceptButtons();
            }
        } else {
            if (e.getRawSlot() < e.getInventory().getSize())
                return;
            if (isFull(pt, inventory)) {
                p.sendMessage(ChatColor.RED + "The Stake inventory is full.");
                p.sendMessage(ChatColor.RED + "Please remove some stake items to make room.");
                return;
            }
            this.modified = true;
            ItemStack is = e.getCurrentItem();
            p.getInventory().removeItem(is);
            p.updateInventory();
            int firstEmpty = getFirstEmpty(pt, inventory);
            if (firstEmpty == -1)
                return;
            inventory.setItem(firstEmpty, is);
            inventory2.setItem(firstEmpty, is);
            if (is != null)
                CosmicDuel.logMessage("Adding Stake item for " + p.getName() + " -> " + is.toString());
            if (isSomeoneReady())
                resetAcceptButtons();
        }
    }

    public void onTeamInventoryEvent(InventoryClickEvent e) {
        e.setCancelled(true);
        e.setResult(Event.Result.DENY);
        if (getDuel().getCreateState() != DuelCreateState.STAKES)
            return;
        Player p = (Player)e.getWhoClicked();
        PlayerType pt = this.playerTypes.get(p.getName());
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
            return;
        Inventory inventory = p.getUniqueId().equals(this.p1.getUniqueId()) ? this.teamALeaderInventory : this.teamBLeaderInventory;
        Inventory inventory2 = p.getUniqueId().equals(this.p1.getUniqueId()) ? this.teamBLeaderInventory : this.teamALeaderInventory;
        if (e.getRawSlot() < e.getInventory().getSize()) {
            if (e.getRawSlot() == 0 && pt == PlayerType.ONE) {
                this.modified = true;
                if (inventory.getItem(0).equals(this.pendingItem)) {
                    inventory.setItem(0, this.acceptedItem);
                    inventory2.setItem(0, this.acceptedItem);
                    checkTeamDone();
                } else {
                    inventory.setItem(0, this.pendingItem);
                    inventory2.setItem(0, this.pendingItem);
                }
            } else if (e.getRawSlot() == 8 && pt == PlayerType.TWO) {
                this.modified = true;
                if (inventory.getItem(8).equals(this.pendingItem)) {
                    inventory.setItem(8, this.acceptedItem);
                    inventory2.setItem(8, this.acceptedItem);
                    checkTeamDone();
                } else {
                    inventory.setItem(8, this.pendingItem);
                    inventory2.setItem(8, this.pendingItem);
                }
            }
            if (!validSlots(pt).contains(Integer.valueOf(e.getRawSlot())))
                return;
            if (p.getInventory().firstEmpty() == -1) {
                p.sendMessage(ChatColor.RED + "Your inventory is full.");
                return;
            }
            this.modified = true;
            ItemStack is = e.getCurrentItem();
            this.teamALeaderInventory.setItem(e.getRawSlot(), null);
            this.teamBLeaderInventory.setItem(e.getRawSlot(), null);
            p.getInventory().addItem(is);
            System.out.println("B");
            p.updateInventory();
            this.p2.updateInventory();
            updateInventory(this.teamA, this.teamB);
            if (isSomeoneReady()) {
                System.out.println("C");
                resetAcceptButtons();
            }
        } else {
            if (e.getRawSlot() < e.getInventory().getSize())
                return;
            if (isFull(pt, inventory)) {
                p.sendMessage(ChatColor.RED + "The Stake inventory is full.");
                p.sendMessage(ChatColor.RED + "Please remove some stake items to make room.");
                return;
            }
            this.modified = true;
            ItemStack is = e.getCurrentItem();
            p.getInventory().removeItem(is);
            p.updateInventory();
            int firstEmpty = getFirstEmpty(pt, inventory);
            if (firstEmpty == -1)
                return;
            inventory.setItem(firstEmpty, is);
            inventory2.setItem(firstEmpty, is);
            if (is != null)
                CosmicDuel.logMessage("Adding Stake item for " + p.getName() + " -> " + is.toString());
            if (isSomeoneReady())
                resetAcceptButtons();
        }
    }

    private void updateInventory(TeamData teamA, TeamData teamB) {
        List<UUID> players = Lists.newArrayList();
        players.add(teamA.getLeaderUUID());
        teamA.getTeamates().stream().forEach(teamate -> players.add(teamate));
        players.add(teamB.getLeaderUUID());
        teamB.getTeamates().stream().forEach(teamate -> players.add(teamate));
        for (UUID uuid : players)
            Bukkit.getPlayer(uuid).updateInventory();
    }

    public void resetAcceptButtons() {
        this.p1.playSound(this.p1.getLocation(), Sound.ORB_PICKUP, 0.4F, 0.4F);
        this.p2.playSound(this.p2.getLocation(), Sound.ORB_PICKUP, 0.4F, 0.4F);
        this.playerOneInventory.setItem(0, this.pendingItem);
        this.playerTwoInventory.setItem(0, this.pendingItem);
        this.playerOneInventory.setItem(8, this.pendingItem);
        this.playerTwoInventory.setItem(8, this.pendingItem);
    }

    public boolean isSomeoneReady() {
        return (this.playerOneInventory.getItem(0).equals(this.acceptedItem) || this.playerOneInventory.getItem(8).equals(this.acceptedItem));
    }

    public void checkDone() {
        if (this.runnable != null)
            return;
        if (this.playerOneInventory.getItem(0).equals(this.acceptedItem) && this.playerOneInventory.getItem(8).equals(this.acceptedItem)) {
            this.modified = false;
            this

                    .runnable = (new BukkitRunnable() {
                final AtomicInteger time = new AtomicInteger(6);

                public void run() {
                    if (NewStakeInventory.this.runnable.getTaskId() != getTaskId()) {
                        cancel();
                        return;
                    }
                    NewDuel duel = NewStakeInventory.this.getDuel();
                    if (duel.isCancelled()) {
                        cancel();
                        NewStakeInventory.this.runnable = null;
                        return;
                    }
                    if (NewStakeInventory.this.modified) {
                        NewStakeInventory.this.modified = false;
                        NewStakeInventory.this.runnable = null;
                        cancel();
                        NewStakeInventory.this.setMiddle(ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", ""));
                        NewStakeInventory.this.resetAcceptButtons();
                        return;
                    }
                    if (this.time.decrementAndGet() <= 0) {
                        cancel();
                        for (Player p : duel.getPlayers()) {
                            if (PlayerUtils.inCombat(p)) {
                                duel.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + p.getName() + " was in combat and cancelled the duel.", false);
                                DuelAPI.cancelDuel(duel, CancelReason.IN_COMBAT_STAKES);
                                return;
                            }
                        }
                        List<ItemStack> stakes = NewStakeInventory.this.getStakes(NewStakeInventory.PlayerType.ONE);
                        List<ItemStack> stakesTwo = NewStakeInventory.this.getStakes(NewStakeInventory.PlayerType.TWO);
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
                        for (Player p : duel.getPlayers())
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.4F);
                        short data = 13;
                        switch (this.time.get()) {
                            case 1:
                            case 2:
                                data = 14;
                                break;
                            case 3:
                            case 4:
                                data = 1;
                                break;
                            case 5:
                                data = 4;
                                break;
                        }
                        ItemStack is = ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, data, ChatColor.YELLOW + ChatColor.BOLD.toString() + this.time.get(), "");
                        is.setAmount(this.time.get());
                        NewStakeInventory.this.setMiddle(is);
                    }
                }
            }).runTaskTimer(CosmicDuel.get(), 20L, 20L);
        }
    }

    public void checkTeamDone() {
        if (this.runnable != null)
            return;
        if (this.teamALeaderInventory.getItem(0).equals(this.acceptedItem) && this.teamALeaderInventory.getItem(8).equals(this.acceptedItem)) {
            this.modified = false;
            this

                    .runnable = (new BukkitRunnable() {
                final AtomicInteger time = new AtomicInteger(6);

                public void run() {
                    if (NewStakeInventory.this.runnable.getTaskId() != getTaskId()) {
                        cancel();
                        return;
                    }
                    NewDuel duel = NewStakeInventory.this.getDuel();
                    if (duel.isCancelled()) {
                        cancel();
                        NewStakeInventory.this.runnable = null;
                        return;
                    }
                    if (NewStakeInventory.this.modified) {
                        NewStakeInventory.this.modified = false;
                        NewStakeInventory.this.runnable = null;
                        cancel();
                        NewStakeInventory.this.setTeamMiddle(ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, (short)7, " ", ""));
                        NewStakeInventory.this.resetAcceptButtons();
                        return;
                    }
                    if (this.time.decrementAndGet() <= 0) {
                        cancel();
                        for (Player p : duel.getPlayers()) {
                            if (PlayerUtils.inCombat(p)) {
                                duel.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + p.getName() + " was in combat and cancelled the duel.", false);
                                DuelAPI.cancelDuel(duel, CancelReason.IN_COMBAT_STAKES);
                                return;
                            }
                        }
                        List<ItemStack> stakes = NewStakeInventory.this.getTeamStakes(NewStakeInventory.PlayerType.ONE);
                        List<ItemStack> stakesTwo = NewStakeInventory.this.getTeamStakes(NewStakeInventory.PlayerType.TWO);
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
                        for (Player p : duel.getPlayers())
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.4F);
                        short data = 13;
                        switch (this.time.get()) {
                            case 1:
                            case 2:
                                data = 14;
                                break;
                            case 3:
                            case 4:
                                data = 1;
                                break;
                            case 5:
                                data = 4;
                                break;
                        }
                        ItemStack is = ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, data, ChatColor.YELLOW + ChatColor.BOLD.toString() + this.time.get(), "");
                        is.setAmount(this.time.get());
                        NewStakeInventory.this.setTeamMiddle(is);
                    }
                }
            }).runTaskTimer(CosmicDuel.get(), 20L, 20L);
        }
    }

    public void setMiddle(ItemStack is) {
        this.playerOneInventory.setItem(4, is);
        this.playerOneInventory.setItem(13, is);
        this.playerOneInventory.setItem(22, is);
        this.playerTwoInventory.setItem(4, is);
        this.playerTwoInventory.setItem(13, is);
        this.playerTwoInventory.setItem(22, is);
    }

    public void setTeamMiddle(ItemStack is) {
        this.teamALeaderInventory.setItem(4, is);
        this.teamALeaderInventory.setItem(13, is);
        this.teamALeaderInventory.setItem(22, is);
        this.teamBLeaderInventory.setItem(4, is);
        this.teamBLeaderInventory.setItem(13, is);
        this.teamBLeaderInventory.setItem(22, is);
    }

    public List<ItemStack> getStakes(PlayerType pt) {
        List<ItemStack> items = new ArrayList<>();
        Inventory inventory = (pt == PlayerType.ONE) ? this.playerOneInventory : this.playerTwoInventory;
        for (Iterator<Integer> iterator = validSlots(pt).iterator(); iterator.hasNext(); ) {
            int i = iterator.next().intValue();
            if (inventory.getItem(i) != null)
                items.add(inventory.getItem(i));
        }
        return items;
    }

    public List<ItemStack> getTeamStakes(PlayerType pt) {
        List<ItemStack> items = new ArrayList<>();
        Inventory inventory = (pt == PlayerType.ONE) ? this.teamALeaderInventory : this.teamBLeaderInventory;
        for (Iterator<Integer> iterator = validSlots(pt).iterator(); iterator.hasNext(); ) {
            int i = iterator.next().intValue();
            if (inventory.getItem(i) != null)
                items.add(inventory.getItem(i));
        }
        return items;
    }

    public void closeInventory(NewDuel duel) {
        if (!(duel instanceof TeamDuel)) {
            for (String p_name : this.playerTypes.keySet()) {
                PlayerType pt = this.playerTypes.get(p_name);
                Player p = Bukkit.getPlayer(p_name);
                if (p == null)
                    continue;
                for (ItemStack is : getStakes(pt)) {
                    if (p.getInventory().firstEmpty() == -1) {
                        CollectionBin.addItem(p, is);
                    } else {
                        p.getInventory().addItem(is);
                    }
                    p.updateInventory();
                }
                p.closeInventory();
            }
            this.playerOneInventory.clear();
            this.playerTwoInventory.clear();
        } else {
            for (String p_name : this.playerTypes.keySet()) {
                PlayerType pt = this.playerTypes.get(p_name);
                Player p = Bukkit.getPlayer(p_name);
                if (p == null)
                    continue;
                for (ItemStack is : getTeamStakes(pt)) {
                    if (p.getInventory().firstEmpty() == -1) {
                        CollectionBin.addItem(p, is);
                    } else {
                        p.getInventory().addItem(is);
                    }
                    p.updateInventory();
                }
                p.closeInventory();
            }
            this.teamALeaderInventory.clear();
            this.teamBLeaderInventory.clear();
        }
    }

    public int getFirstEmpty(PlayerType pt, Inventory inv) {
        for (Iterator<Integer> iterator = validSlots(pt).iterator(); iterator.hasNext(); ) {
            int i = iterator.next().intValue();
            if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR)
                return i;
        }
        return -1;
    }

    public boolean isFull(PlayerType pt, Inventory inv) {
        int amount = 0;
        for (Iterator<Integer> iterator = validSlots(pt).iterator(); iterator.hasNext(); ) {
            int i = iterator.next().intValue();
            if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR)
                amount++;
        }
        return (amount >= validSlots(pt).size());
    }

    public boolean isEmpty(Inventory inv) {
        for (ListIterator<ItemStack> listIterator = inv.iterator(); listIterator.hasNext(); ) {
            ItemStack is = listIterator.next();
            if (is != null && is.getType() != Material.AIR)
                return false;
        }
        return true;
    }

    public List<Integer> validSlots(PlayerType pt) {
        if (pt == PlayerType.ONE)
            return this.validSlots.keySet().stream().collect(Collectors.toList());
        if (pt == PlayerType.TWO)
            return this.validSlots.values().stream().collect(Collectors.toList());
        return null;
    }

    public Inventory getPlayerOneInventory() {
        return this.playerOneInventory;
    }

    public Inventory getPlayerTwoInventory() {
        return this.playerTwoInventory;
    }

    public Inventory getTeamALeaderInventory() {
        return this.teamALeaderInventory;
    }

    public Inventory getTeamBLeaderInventory() {
        return this.teamBLeaderInventory;
    }

    public Player getP1() {
        return this.p1;
    }

    public Player getP2() {
        return this.p2;
    }

    public TeamData getTeamA() {
        return this.teamA;
    }

    public TeamData getTeamB() {
        return this.teamB;
    }

    public NewDuel getDuel() {
        return this.duel;
    }

    public static String INVENTORY_NAME = "    Host                   Rival";

    Inventory teamALeaderInventory;

    Inventory teamBLeaderInventory;

    Inventory playerOneInventory;

    Inventory playerTwoInventory;

    Player p1;

    Player p2;

    TeamData teamA;

    TeamData teamB;

    NewDuel duel;

    ItemStack pendingItem;

    ItemStack acceptedItem;

    boolean modified;

    HashMap<String, PlayerType> playerTypes;

    HashMap<String, PlayerType> teamLeaderTypes;

    HashMap<TeamData, PlayerType> teamTypes;

    LinkedHashMap<Integer, Integer> validSlots;

    BukkitTask runnable;

    private ItemStack selected;

    public enum PlayerType {
        ONE, TWO, TEAM_A, TEAM_B
    }
}
