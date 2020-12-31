package me.ily2bb.slotbot.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ily2bb.slotbot.SlotBotPlugin;
import me.ily2bb.slotbot.objects.Glow;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class LootTable {
    public static ItemStack[] getLootTableItems() {
        final List<String> iteration = (List<String>) SlotBotPlugin.getInstance().getConfig().getList("items");
        final ItemStack[] items = new ItemStack[iteration.size()];
        for (int i = 0; i < iteration.size(); ++i) {
            final String l = iteration.get(i);
            items[i] = Utils.getItem(l);
        }
        return items;
    }

    public static List<ItemStack> getShowItems() {
        final List<String> iteration = (List<String>) SlotBotPlugin.getInstance().getConfig().getList("items");
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (int i = 0; i < iteration.size(); ++i) {
            final String l = iteration.get(i);
            if (Utils.isShowing(l)) {
                list.add(Utils.getItem(l));
            }
        }
        return list;
    }

    public static List<ItemStack> getBroadcastItems() {
        final List<String> iteration = (List<String>) SlotBotPlugin.getInstance().getConfig().getList("items");
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (int i = 0; i < iteration.size(); ++i) {
            final String l = iteration.get(i);
            if (Utils.isBroadcasting(l)) {
                list.add(Utils.getItem(l));
            }
        }
        return list;
    }

    public static ItemStack createLootTable() {
        final ItemStack lootbox = new ItemStack(Material.BEACON);
        final ItemMeta meta = lootbox.getItemMeta();
        meta.setDisplayName(String.valueOf(CC.AquaB) + "Loot Table");
        final AbstractList<String> lore = new ArrayList<String>();
        lore.addAll(Arrays.asList(CC.Gray + "The Slot Bot contains the items", CC.Gray + "from the following Lootboxes,", CC.Gray + "which will randomize every week:", ""));
        final List<ItemStack> items = getShowItems();
        for (final ItemStack x : items) {
            final String display = Utils.getDisplayName(x);
            lore.add(String.valueOf(CC.WhiteB) + "* " + ((x.getAmount() > 1) ? (String.valueOf(x.getAmount()) + "x ") : "") + display);
        }
        lore.add(String.valueOf(CC.LightPurple) + "Click To View All Items");
        meta.setLore((List) lore);
        meta.addEnchant((Enchantment) new Glow(70), 1, true);
        lootbox.setItemMeta(meta);
        return lootbox;
    }

    public static ItemStack createPane(final int id) {
        final ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) id);
        final ItemMeta paneMeta = pane.getItemMeta();
        paneMeta.setDisplayName(String.valueOf(CC.DarkRed));
        pane.setItemMeta(paneMeta);
        return pane;
    }

    public static ItemStack createRewardSlot() {
        final ItemStack pane = createPane(5);
        final ItemMeta paneMeta = pane.getItemMeta();
        paneMeta.setDisplayName(CC.GreenB + "Reward Slot");
        final List<String> lore = Arrays.asList(CC.Gray + "The item which lands on this slot", CC.Gray + "will be the item you receive.");
        paneMeta.setLore((List) lore);
        pane.setItemMeta(paneMeta);
        return pane;
    }

    public static ItemStack createSpinTicket(final boolean spinning) {
        if (!spinning) {
            final ItemStack item = new ItemStack(Material.MAGMA_CREAM);
            final ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(CC.RedB + "Spin (" + CC.Gray + "Missing Ticket" + CC.RedB + ")");
            itemMeta.setLore((List) Arrays.asList(CC.Gray + "Insert a Slot Bot Ticket", CC.Gray + "to spin the bot.", "", CC.Gray + "Purchase Slot Bot Tickets", CC.Gray + "at " + CC.White + "buy.astropvp.net"));
            item.setItemMeta(itemMeta);
            return item;
        }
        final ItemStack item = new ItemStack(Material.SLIME_BALL);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(CC.GreenB + "Spin");
        itemMeta.setLore((List) Collections.singletonList(CC.Gray + "Click to spin the bot."));
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createHowToPlayBook() {
        final ItemStack item = new ItemStack(Material.BOOK);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.AquaB + "How to Play");
        final List<String> lore = Arrays.asList(CC.LightPurpleB + "1. " + CC.Gray + "Insert up to 3 Slot Bot Tickets", CC.Gray + "into the bot from your inventory.", "", CC.LightPurpleB + "2. " + CC.Gray + "Click ''Spin'' (on the right) to roll", String.valueOf(CC.Gray) + "the bot or, click ''Return'' (on the left)", String.valueOf(CC.Gray) + "to withdraw all inserted tickets.", "", String.valueOf(CC.LightPurpleB) + "3. " + CC.Gray + "When the bot finishes spinning", String.valueOf(CC.Gray) + "the item which lands on the Reward Slot", String.valueOf(CC.Gray) + "will be the item you receive.", "", String.valueOf(CC.WhiteB) + "TIP: " + CC.Gray + "Closing the bot while spinning will", String.valueOf(CC.Gray) + "give the rewards you would have", String.valueOf(CC.Gray) + "received, otherwise your inserted", String.valueOf(CC.Gray) + "tickets will be refunded.");
        meta.setLore((List) lore);
        item.setItemMeta(meta);
        return item;
    }


    public static ItemStack createRoller(final int x) {
        if (x > 0) {
            final ItemStack item = createPane(14);
            final ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(CC.RedB + "LOCKED");
            final List<String> lore = Arrays.asList(CC.Gray + "Requires " + x + " more " + ((x > 1) ? "tickets" : "ticket") + " to roll.", "", String.valueOf(CC.Gray) + "Purchase Slot Bot Tickets", String.valueOf(CC.Gray) + "at " + CC.White + "buy.astropvp.net");
            itemMeta.setLore((List) lore);
            item.setItemMeta(itemMeta);
            item.setAmount(x);
            return item;
        }
        final ItemStack item = new ItemStack(Material.PAPER);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(CC.GreenB + "UNLOCKED");
        final List<String> lore = Collections.singletonList(CC.Gray + "Click ''Spin'' to roll.");
        itemMeta.setLore((List) lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createBackButton() {
        final ItemStack item = new ItemStack(Material.NETHER_STAR);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(String.valueOf(CC.RedB) + "Back");
        final List<String> list = Arrays.asList(String.valueOf(CC.Gray) + "Click to return to", String.valueOf(CC.Gray) + "the Slot Bot.");
        itemMeta.setLore((List) list);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack createReturnSlotBotsButton(final int slots) {
        if (slots <= 0) {
            return null;
        }
        final ItemStack item = new ItemStack(Material.NETHER_STAR);
        final ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(String.valueOf(CC.RedB) + "Return");
        final List<String> list = Arrays.asList(String.valueOf(CC.Gray) + "Click to return your " + slots + " tickets.");
        itemMeta.setLore((List) list);
        item.setItemMeta(itemMeta);
        return item;
    }
}
