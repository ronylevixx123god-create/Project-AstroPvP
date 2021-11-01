/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.TimeUtils
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.ClickType
 *  org.bukkit.event.me.astropvp.cratekeys.inventory.InventoryClickEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.Inventory
 *  org.bukkit.me.astropvp.cratekeys.inventory.InventoryHolder
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package com.compilex.CosmicDungeons.listeners;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonState;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import net.astropvp.core.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class DungeonMenuListener
implements Listener {
    private final String dungeonMenuInventoryName = "Astro Dungeons";

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerTransfer(InventoryClickEvent event) {
        if (!event.getWhoClicked().getWorld().getName().startsWith(DungeonType.DESTROYED_OUTPOST.getInternalName())) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player)event.getWhoClicked();
        if (player.getOpenInventory() != null && player.getOpenInventory().getTopInventory() != null && (player.getOpenInventory().getTopInventory().getName().equals("container.crafting") || player.getOpenInventory().getTopInventory().getName().equals("container.inventory"))) {
            return;
        }
        if (event.getClick() == ClickType.NUMBER_KEY && event.getWhoClicked().getWorld().getName().toLowerCase().contains("dungeon")) {
            event.setCancelled(true);
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "Please do not use Number Key clicks inside the dungeon!");
            return;
        }
        if (event.getClickedInventory() == null || !event.getClickedInventory().equals((Object)player.getInventory())) {
            return;
        }
        ItemStack clicked = event.getCurrentItem();
        ItemStack cursor = event.getCursor();
        if (cursor != null && cursor.getType() != Material.AIR && DungeonsAPI.isDungeonType(cursor)) {
            event.setCancelled(true);
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot move this dungeon item out of your me.astropvp.cratekeys.inventory!");
            return;
        }
        if (clicked != null && clicked.getType() != Material.AIR && DungeonsAPI.isDungeonType(clicked)) {
            event.setCancelled(true);
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot move this dungeon item out of your me.astropvp.cratekeys.inventory!");
        }
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onDungeonMenuClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equals("Astro Dungeons")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.TRIPWIRE_HOOK) {
                Player p = (Player)e.getWhoClicked();
                for (Dungeon d : DungeonsAPI.get().getDungeons()) {
                    if (d.getDungeonKeyMenuSlot() == e.getRawSlot() && !d.playerHasPermission(p)) continue;
                }
            }
        }
    }

    public Inventory getDungeonMenu(Player p) {
        this.getClass();
        Inventory inv = Bukkit.createInventory((InventoryHolder)null, (int)45, (String)"Astro Dungeons");
        inv.setItem(3, ItemUtils.buildItem(Material.BOOK, (short)0, (Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "" + (Object)ChatColor.UNDERLINE + "Astro Dungeons", (Object)ChatColor.GRAY + "Dungeon are " + (Object)ChatColor.UNDERLINE + "top tier PvE instances", (Object)ChatColor.GRAY + "for the most powerful players to conquer.", "", (Object)ChatColor.GRAY + "Filled with " + (Object)ChatColor.RED + "advanced custom AI", (Object)ChatColor.GRAY + "and unique challenges, these maps", (Object)ChatColor.GRAY + "will push any Cosmonaut to the limit.", "", (Object)ChatColor.GRAY + "In order to start a dungeon you", (Object)ChatColor.GRAY + "need to unlock its dungeon key.", (Object)ChatColor.GRAY + "These keys can be purchased", (Object)ChatColor.GRAY + "from the " + (Object)ChatColor.RED + "Dungeon Master at /spawn", (Object)ChatColor.GRAY + "or from using " + (Object)ChatColor.UNDERLINE + "/dungeonmaster", "", (Object)ChatColor.GRAY + "Once you have the key, you will", (Object)ChatColor.GRAY + "need to " + (Object)ChatColor.UNDERLINE + "craft a dungeon portal", (Object)ChatColor.GRAY + "each time you wish to run the dungeon."));
        inv.setItem(5, ItemUtils.buildItem(Material.IRON_FENCE, (short)0, (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "" + (Object)ChatColor.UNDERLINE + "Dungeon Tips", (Object)ChatColor.GRAY + "Fallout Dungeons operate differently than", (Object)ChatColor.GRAY + "the normal of CosmicPvP gameplay.", "", (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "You do " + (Object)ChatColor.UNDERLINE + "NOT" + (Object)ChatColor.GRAY + " lose your me.astropvp.cratekeys.inventory.", (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Every boss has custom mechanics.", (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "If you do not strategize, you die.", (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Anyone can enter your dungeon portals.", (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Dungeons are PvP-enabled zones.", (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Cosmic Dungeons are end-game", (Object)ChatColor.GRAY + "PvM content, they are HARD."));
        inv.setItem(4, ItemUtils.buildItem(Material.DIAMOND, (short)0, (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "" + (Object)ChatColor.UNDERLINE + "Dungeon Rewards", (Object)ChatColor.GRAY + "You can obtain all kinds of", (Object)ChatColor.GRAY + "legendary treasures by defeating", (Object)ChatColor.GRAY + "dungeons, but most importantly", (Object)ChatColor.GRAY + "they allow you to obtain...", "", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Heroic Armor", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Heroic Blackscrolls", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Equipment Lootboxes", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Heroic Enchantments", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Soul Trinkets", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Holy White Scrolls", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Dungeon Armor Sets", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "* " + (Object)ChatColor.GRAY + "Massive $/XP/mcMMO", CC.GoldB + "* " + CC.Gray + "Heroic Armor Upgrades", CC.GoldB + "* " + CC.Gray + "Superior Cosmic Crates"));
        if (CosmicDungeonsPlugin.get_().getConfig().getBoolean("holiday.dailyPortals")) {
            inv.setItem(13, ItemUtils.buildItem(Material.NETHER_STAR, (short)0, (Object)ChatColor.AQUA + "" + (Object)ChatColor.BOLD + "" + (Object)ChatColor.UNDERLINE + "Dungeon Portal Event", "", (Object)ChatColor.AQUA + "Next Dungeon Portal spawns in...", (Object)ChatColor.GRAY + com.compilex.CosmicDungeons.utils.TimeUtils.formatDifference(com.compilex.CosmicDungeons.utils.TimeUtils.getMilliUntilNextPublicPortal()), "", (Object)ChatColor.GRAY + "This portal opens at /spawn", (Object)ChatColor.GRAY + "next to the Dungeon Master."));
        }
        DungeonPlayer player = DungeonsAPI.get().getPlayerDungeonData(p);
        for (Dungeon dungeon : DungeonsAPI.get().getDungeons()) {
            ArrayList locked;
            ItemStack atSlot;
            boolean beenCompleted;
            LinkedList<String> dungeonLore = new LinkedList<String>();
            dungeonLore.add("");
            dungeonLore.add((dungeon.isDestroyedOutpost() ? CC.DarkRed : CC.LightPurple) + "" + (Object)ChatColor.BOLD + "STATUS");
            dungeonLore.add(dungeon.getDungeonStatus().getDisplayText() + " -");
            if (dungeon.getDungeonStatus() == DungeonState.IN_PROGRESS) {
                dungeonLore.add(dungeon.getDungeonStatus().getDetailText() + (Object)ChatColor.GRAY + " [" + dungeon.getPlayers().size() + " players]");
                for (Player pl : dungeon.getPlayers()) {
                    FPlayer fpl = (FPlayer)FPlayers.i.get((OfflinePlayer)pl);
                    DungeonPlayer dp = DungeonsAPI.get().getPlayerDungeonData(pl);
                    if (dp.getCurrentDungeon() != dungeon) continue;
                    if (fpl.getFaction().isNormal()) {
                        dungeonLore.add((Object)ChatColor.GRAY + fpl.getTag() + " " + pl.getName());
                        continue;
                    }
                    dungeonLore.add((Object)ChatColor.GRAY + pl.getName());
                }
            } else {
                dungeonLore.add(dungeon.getDungeonStatus().getDetailText());
            }
            LinkedList dungeonDescription = (LinkedList)dungeon.getDungeonDescription().clone();
            dungeonLore.addAll(dungeonDescription);
            inv.setItem(dungeon.getDungeonMenuSlot(), ItemUtils.buildItem(Material.MOB_SPAWNER, (short)0, dungeon.getDungeonName(), dungeonLore));
            LinkedList<String> fastestLore = new LinkedList<String>();
            boolean bl = beenCompleted = dungeon.getTopDungeonTime().getSecondsCompletedIn() <= 0;
            if (beenCompleted) {
                fastestLore.add(CC.Gray + "Complete this dungeon to acquire the top spot!");
            } else {
                fastestLore.add(CC.Gold + "Completed In");
                fastestLore.add(CC.White + " " + TimeUtils.formatSeconds((long)dungeon.getTopDungeonTime().getSecondsCompletedIn()));
                fastestLore.add("");
                fastestLore.add(CC.Gold + "Date");
                fastestLore.add(CC.White + " " + TimeUtils.formatTimeToDate((long)dungeon.getTopDungeonTime().getTimeCompleted(), (String)"MMM d'%s' hh:mmaa", (String)"CST").replace(" 0", " ") + " CST");
                fastestLore.add("");
                fastestLore.add(CC.Gold + "Completed By");
                dungeon.getTopDungeonTime().getFactionsWhoCompleted().forEach(fac -> fastestLore.add(CC.White + " - " + fac));
            }
            inv.setItem(dungeon.getDungeonKeyMenuSlot() + 9, ItemUtils.buildItem(Material.WATCH, (short)0, beenCompleted ? CC.YellowB + "No Fastest Time Recorded" : CC.YellowB + "Fastest Time Recorded", fastestLore));
            boolean hasCompletedPreviousRecently = !dungeon.isDestroyedOutpost() || DungeonType.ABANDONED_SHIP.getDungeonInstance().hasCompletedRecently(player, 24L);
            boolean hasKeyPermission = dungeon.playerHasPermission(p);
            boolean hasKey = dungeon.playerHasKey(p);
            if (dungeon.playerHasCooldown(player)) {
                inv.setItem(dungeon.getDungeonKeyMenuSlot(), ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)4, (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "REBUILDING", (Object)ChatColor.GRAY + "Your loot cooldown for this dungeon", (Object)ChatColor.GRAY + "expires in: " + (Object)ChatColor.UNDERLINE + com.compilex.CosmicDungeons.utils.TimeUtils.formatDifference(DungeonsAPI.get().getPlayerDungeonData(p).getCooldowns().getCooldown(dungeon)), "", (Object)ChatColor.GRAY + "You cannot earn rewards from this", (Object)ChatColor.GRAY + "dungeon until your cooldown expires."));
            } else if ((hasKeyPermission || (hasKey = dungeon.playerHasKey(p))) && hasCompletedPreviousRecently) {
                inv.setItem(dungeon.getDungeonKeyMenuSlot(), ItemUtils.buildItem(Material.TRIPWIRE_HOOK, (short)0, (Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + "UNLOCKED", "", (Object)ChatColor.GRAY + "You are eligible to enter the", (Object)ChatColor.GRAY + dungeon.getDungeonName() + " Portal", "", (Object)ChatColor.GRAY + "To access the portal, you must", (Object)ChatColor.GRAY + "either craft it yourself, or", (Object)ChatColor.GRAY + "find another player with one.", "", (Object)ChatColor.GRAY + "Dungeon Portals are very complex", (Object)ChatColor.GRAY + "crafting recipes that take time", (Object)ChatColor.GRAY + "to collect all the ingredients for."));
            }
            if ((atSlot = inv.getItem(dungeon.getDungeonKeyMenuSlot())) != null && atSlot.getType() != Material.AIR) continue;
            if (dungeon.getDungeonType() != DungeonType.DESTROYED_OUTPOST) {
                locked = Lists.newArrayList(new String[]{(Object)ChatColor.GRAY + "In order to enter a dungeon portal,", (Object)ChatColor.GRAY + "you must have the key to that dungeon", (Object)ChatColor.GRAY + "inside of your me.astropvp.cratekeys.inventory.", "", (Object)ChatColor.GRAY + "Unlock access from the " + (Object)ChatColor.RED + "Dungeon Master", (Object)ChatColor.GRAY + "at /spawn, or using /dungeonmaster!"});
            } else {
                locked = Lists.newArrayList(new String[]{(Object)ChatColor.GRAY + "In order to enter a dungeon portal,", (Object)ChatColor.GRAY + "you must have the following:"});
                locked.add(CC.WhiteB + " * " + CC.White + (hasKeyPermission || hasKey ? CC.Strike : "") + "Dungeon Key in Inventory");
                locked.add(CC.WhiteB + " * " + CC.White + (hasCompletedPreviousRecently ? CC.Strike : "") + "Teleportation Coordinates");
                locked.add("    " + CC.Gray + (hasCompletedPreviousRecently ? CC.Strike : "") + "(Defeat Abandoned Spaceship within 24 hours)");
                locked.add("");
                locked.add((Object)ChatColor.GRAY + "Unlock access from the " + (Object)ChatColor.RED + "Dungeon Master");
                locked.add((Object)ChatColor.GRAY + "at /spawn, or using /dungeonmaster!");
            }
            inv.setItem(dungeon.getDungeonKeyMenuSlot(), new ItemBuilder(Material.STAINED_GLASS_PANE).data((short)14).name((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "LOCKED").lore((List)locked).build());
        }
        for (int i = 0; i < inv.getSize(); ++i) {
            if (inv.getItem(i) != null) continue;
            if (i > 19 && i < 25) {
                inv.setItem(i, ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)1, (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "???", (Object)ChatColor.GRAY + "This dungeon has not", (Object)ChatColor.GRAY + "been discovered yet."));
                continue;
            }
            if (i > 28 && i < 34) {
                inv.setItem(i, ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)14, (Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "???", (Object)ChatColor.GRAY + "The key to this dungeon", (Object)ChatColor.GRAY + "cannot be obtained yet."));
                continue;
            }
            inv.setItem(i, ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short)7, (Object)ChatColor.BLACK + " "));
        }
        return inv;
    }
}

