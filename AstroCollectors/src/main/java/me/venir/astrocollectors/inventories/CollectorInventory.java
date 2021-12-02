package me.venir.astrocollectors.inventories;

import com.conaxgames.customskulls.util.CC;
import com.earth2me.essentials.Essentials;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import me.venir.astrocollectors.AstroCollectors;
import me.venir.astrocollectors.collector.Collector;
import me.venir.astrocollectors.collector.CollectorSellLog;
import me.venir.astrocollectors.language.Language;
import me.venir.astrocollectors.utils.GUIBuilder;
import me.venir.astrocollectors.utils.Utils;
import me.venir.astrocollectors.utils.itemutils.ItemBuilder;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CollectorInventory extends GUIBuilder {

    Collector collector;

    public CollectorInventory(Player player, Collector collector) {
        super(player);
        this.collector = collector;
    }

    @Override
    protected String getName() {
        return "Chunk Collector";
    }

    @Override
    protected int getSlots() {
        return 45;
    }

    @Override
    protected void setItems() {


        for (int i = 0; i < 45; i++) {
            this.inventory.setItem(i, getItems(-1));
        }
        this.inventory.setItem(11, getItems(11));
        this.inventory.setItem(12, getItems(12));
        this.inventory.setItem(13, getItems(13));
        this.inventory.setItem(14, getItems(14));
        this.inventory.setItem(15, getItems(15));
        this.inventory.setItem(21, getItems(21));
        this.inventory.setItem(22, getItems(22));
        this.inventory.setItem(23, getItems(23));
        this.inventory.setItem(31, getItems(31));
    }

    @Override
    protected ItemStack getItems(int slot) {
        if (slot == -1) {
            return ItemBuilder.buildItem(Material.STAINED_GLASS_PANE, 1, (short) 7,
                    Utils.format("&7"));
        }
        if (slot == 11) {
            return createGUIItem(Material.EMERALD, "Emerald");
        }

        if (slot == 12) {
            return createGUIItem(Material.DIAMOND, "Diamond");
        }

        if (slot == 13) {
            return ItemBuilder.buildItem(Material.TNT,
                    Utils.format("&aTNT"),
                    Utils.format(""),
                    Utils.format("&a&l* &fAmount: &a" + collector.getAmount(Material.TNT)),
                    Utils.format(""),
                    Utils.format("&7&nRight-Click&7 to deposit the drops into &f\"/f tnt\""));

        }
        if (slot == 14) {
            return createGUIItem(Material.GOLD_INGOT, "Gold Ingot");
        }
        if (slot == 15) {
            return createGUIItem(Material.IRON_INGOT, "Iron Ingot");
        }
        if (slot == 21) {
            return createGUIItem(Material.ROTTEN_FLESH, "Rotten Flesh");
        }
        if (slot == 22) {
            return createGUIItem(Material.STRING, "String");
        }
        if (slot == 23) {
            return createGUIItem(Material.BONE, "Bone");
        }
        if (slot == 31) {
            ItemStack item = ItemBuilder.buildItem(Material.BEACON, Utils.format("&e&lSell ALL"), Utils.format("&7&nRight-Click&7 to sell/deposit ALL items."));
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            return item;
        }
        return null;
    }


    @Override
    public void click(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE)
            return;
        Player p = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        Material material = itemStack.getType();
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (e.getSlot() == 13) {
            if (collector.getAmount(Material.TNT) == 0) return;
            FPlayer fplayer = FPlayers.i.get(player);
            Faction faction = fplayer.getFaction();
            if (faction.isWilderness()) return;
            faction.tnt += collector.getTntAmount();
            Language.COLLECTOR_TNT.send(p, str -> str.replace("%tnt%", NumberFormat.getIntegerInstance().format(collector.getAmount(Material.TNT))));
            collector.setAmount(Material.TNT, 0);
            lore.add(Utils.format("&a&l* &fAmount: &a0"));
            lore.add(Utils.format(""));
            lore.add(Utils.format("&7&nRight-Click&7 to deposit the drops into &f\"/f tnt\""));
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
            p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 5, 5);
            return;
        }
        if (e.getSlot() == 31) {
            Economy eco = AstroCollectors.getEconomy();
            Essentials essentials = AstroCollectors.INSTANCE.getEssentials();
            collector.getLootMap().forEach((m, i) -> {
                if (collector.getAmount(m) == 0) return;
                if (m == Material.TNT) {
                    FPlayer fplayer = FPlayers.i.get(player);
                    Faction faction = fplayer.getFaction();
                    if (faction.isWilderness()) return;
                    if (faction.tnt == Double.MAX_VALUE) {
                        p.sendMessage(Utils.format("&cYour /f tnt bank is FULL. Please clear some space."));
                        return;
                    }
                    faction.tnt += Double.valueOf(collector.getAmount(Material.TNT));
                    Language.COLLECTOR_TNT.send(p, str -> str.replace("%tnt%", NumberFormat.getIntegerInstance().format(collector.getAmount(Material.TNT))));
                } else {
                    double price = essentials.getWorth().getPrice(essentials, new ItemStack(m)).doubleValue() * collector.getAmount(m);
                    eco.depositPlayer(p, price);


                    Language.COLLECTOR_SELL.send(p, str -> str.replace("%price%", NumberFormat.getIntegerInstance().format(price)));
                }
                collector.resetAmount(m);
            });
            p.closeInventory();
            p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 5, 5);
            return;
        }
        if (collector.getAmount(material) == 0) return;
        Economy eco = AstroCollectors.getEconomy();
        Essentials essentials = AstroCollectors.INSTANCE.getEssentials();
        double price = essentials.getWorth().getPrice(essentials, itemStack).doubleValue() * collector.getAmount(material);

        //HERE SEELLOG
        eco.depositPlayer(p, price);
        Language.COLLECTOR_SELL.send(p, str -> str.replace("%price%", NumberFormat.getIntegerInstance().format(price)));
        collector.setAmount(material, 0);
        lore.add(Utils.format("&a&l* &fAmount: &a0"));
        lore.add(Utils.format("&a&l* &fSell Price: &a$0"));
        lore.add(Utils.format(""));
        lore.add(Utils.format("&7&nRight-Click&7 to sell the drops"));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 5, 5);
        return;
    }


    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public ItemStack createGUIItem(Material material, String name) {
        Essentials essentials = AstroCollectors.INSTANCE.getEssentials();
        return ItemBuilder.buildItem(material,
                Utils.format("&a&l" + name + "&7 (Right-Click)"),
                Utils.format(""),
                Utils.format("&a&l* &fAmount: &a" + collector.getAmount(material)),
                Utils.format("&a&l* &fSell Price: &a$" + NumberFormat.getIntegerInstance().format(collector.getAmount(material) * essentials.getWorth().getPrice(essentials, new ItemStack(material)).doubleValue())),
                Utils.format(""),
                Utils.format("&7&nRight-Click&7 to sell the drops"));
    }
}
