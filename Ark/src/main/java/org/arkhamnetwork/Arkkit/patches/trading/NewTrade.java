/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  com.earth2me.essentials.User
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches.trading;

import com.earth2me.essentials.Essentials;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class NewTrade
        extends ArkPlugin {
    private static final int tradeInventorySize = 54;
    public static HashMap<Integer, Integer> slotConversionMap;
    protected static ItemStack divider;
    protected static ItemStack gray_button;
    protected static ItemStack green_button;
    private static Essentials ess3 = null;

    public NewTrade(Plugin p) {
        super(p);
        if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            ess3 = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        }
        divider = ItemUtils.buildItem(Material.THIN_GLASS, (short) 0, ChatColor.WHITE + " ", "");
        gray_button = NewTrade.setAmount(ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.YELLOW.toString() + "Click to ACCEPT Trade", ""), 10);
        green_button = NewTrade.setAmount(ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN.toString() + "Trade ACCEPTING...", ChatColor.GRAY.toString() + "Modify the trade to cancel."), 10);
        slotConversionMap = new HashMap();
        slotConversionMap.put(1, 5);
        slotConversionMap.put(2, 6);
        slotConversionMap.put(3, 7);
        slotConversionMap.put(0, 8);
        slotConversionMap.put(9, 14);
        slotConversionMap.put(10, 15);
        slotConversionMap.put(11, 16);
        slotConversionMap.put(12, 17);
        slotConversionMap.put(18, 23);
        slotConversionMap.put(19, 24);
        slotConversionMap.put(20, 25);
        slotConversionMap.put(21, 26);
        slotConversionMap.put(27, 32);
        slotConversionMap.put(28, 33);
        slotConversionMap.put(29, 34);
        slotConversionMap.put(30, 35);
        slotConversionMap.put(36, 41);
        slotConversionMap.put(37, 42);
        slotConversionMap.put(38, 43);
        slotConversionMap.put(39, 44);
        slotConversionMap.put(45, 50);
        slotConversionMap.put(46, 51);
        slotConversionMap.put(47, 52);
        slotConversionMap.put(48, 53);
    }

    public static Set<Integer> getValidSlots() {
        return slotConversionMap.keySet();
    }

    public static Integer getLinkedSlot(int slot) {
        return slotConversionMap.containsKey(slot) ? slotConversionMap.get(slot) : Integer.valueOf(-1);
    }

    protected static void startTrade(Player p1, Player p2) {
        p1.closeInventory();
        p2.closeInventory();
        p1.setMetadata("compilexTradePartnerUUID", new FixedMetadataValue(Arkkit.get(), p2.getUniqueId()));
        p2.setMetadata("compilexTradePartnerUUID", new FixedMetadataValue(Arkkit.get(), p1.getUniqueId()));
        p1.openInventory(NewTrade.buildTradeWindow(p1, p2));
        p2.openInventory(NewTrade.buildTradeWindow(p2, p1));
        TradeSyncTask TST = new TradeSyncTask(p1, p2);
        TST.runTaskTimer(Arkkit.get(), 2L, 2L);
        p1.setMetadata("compilexTradeTaskID", new FixedMetadataValue(Arkkit.get(), TST.getTaskId()));
        p2.setMetadata("compilexTradeTaskID", new FixedMetadataValue(Arkkit.get(), TST.getTaskId()));
        p1.playSound(p1.getLocation(), Sound.WOOD_CLICK, 1.0f, 0.8f);
        p2.playSound(p2.getLocation(), Sound.WOOD_CLICK, 1.0f, 0.8f);
        p1.sendMessage(ChatColor.YELLOW + "Trading with " + ChatColor.BOLD + p2.getName() + ChatColor.YELLOW + "...");
        p2.sendMessage(ChatColor.YELLOW + "Trading with " + ChatColor.BOLD + p1.getName() + ChatColor.YELLOW + "...");
    }

    private static Inventory buildTradeWindow(Player p1, Player p2) {
        Inventory TradeWindow = Bukkit.createInventory(null, 54, NewTrade.getTradeInventoryTitle(p1.getName(), p2.getName()));
        for (int i = 4; i < TradeWindow.getSize(); i += 9) {
            TradeWindow.setItem(i, divider);
        }
        TradeWindow.setItem(0, NewTrade.setAmount(ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.YELLOW.toString() + "Click to ACCEPT Trade", ""), 10));
        TradeWindow.setItem(8, NewTrade.setAmount(ItemUtils.buildItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.YELLOW.toString() + "Click to ACCEPT Trade", ""), 10));
        return TradeWindow;
    }

    private static ItemStack setAmount(ItemStack is, int x) {
        ItemStack i = is.clone();
        i.setAmount(x);
        return i;
    }

    private static String getTradeInventoryTitle(String lPName, String rPName) {
        lPName = "You";
        String title = "  " + lPName;
        while (title.length() + rPName.length() <= 30) {
            title = title + " ";
        }
        return (title = title + rPName).length() <= 32 ? title : title.substring(0, 31);
    }

    @Override
    public void enable() {
        this.registerListener(new NewTradeListener());
        this.registerCommand(new NewTradeCommand("trade", "/<command>", "Trade items with other players!", new ArrayList<String>(Arrays.asList("trades", "trading", "exchange"))));
    }

    @Override
    public void disable() {
        if (Arkkit.get().shutting_down) {
            return;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasMetadata("compilexTradeTaskID")) continue;
            p.closeInventory();
        }
    }
}

