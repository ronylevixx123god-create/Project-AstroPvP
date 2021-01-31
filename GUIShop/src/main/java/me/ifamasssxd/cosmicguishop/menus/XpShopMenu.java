/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.InventoryHolder
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package me.ifamasssxd.cosmicguishop.menus;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

import me.ifamasssxd.cosmicguishop.CosmicGUIShop;
import me.ifamasssxd.cosmicguishop.utils.Base64Utils;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class XpShopMenu implements Menu{

    private Inventory inventory = Bukkit.createInventory(this, 54, "Astro XP Shop");

    List<String> itemList = CosmicGUIShop.getInstance().getConfig().getStringList("items");


    public XpShopMenu(Player player) {
        inventory.setItem(49, createGUIItem(player));
        inventory.setItem(50, createFillerItem());
        inventory.setItem(51, createFillerItem());
        inventory.setItem(52, createFillerItem());
        inventory.setItem(53, createFillerItem());
        inventory.setItem(48, createFillerItem());
        inventory.setItem(47, createFillerItem());
        inventory.setItem(46, createFillerItem());
        inventory.setItem(45, createFillerItem());
        for(int i = 0; i < itemList.size(); i++) {
            String s = itemList.get(i);
            ItemStack item = Base64Utils.getItem(s);
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
            lore.add(CC.translate("&f&l&c&b"));
            lore.add(CC.translate("&a&l* COST: " + CC.White + (int) Base64Utils.getCost(s) + CC.GreenB +  " XP"));
            lore.add(CC.translate("&f&l&c&b"));
            meta.setLore(lore);
            item.setItemMeta(meta);
            NBTWrapper wrapper = new NBTWrapper(item);
            wrapper.setFloat("xpShopPrice", Base64Utils.getCost(s));
            inventory.setItem(i, wrapper.build());
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void onClick(Player player, int slot, ClickType type, InventoryClickEvent e) {
        e.setCancelled(true);
        ItemStack item = e.getCurrentItem();
        if (item == null || item.getType().equals(Material.AIR) || item.getItemMeta().getDisplayName().startsWith(CC.GreenB + "Astro Xp Shop") || item.getItemMeta().getDisplayName().equals("&f")) {
            return;
        }
        int cost = (int) getCostFromItem(item);
        if (cost == 0)
            return;
        if (player.getTotalExperience() < cost) {
            player.sendMessage(CC.Red + "You do not have enough exp to purchase this item.");
        } else {
            int exp = player.getTotalExperience() - cost;
            player.setTotalExperience(0);
            player.setExp(0);
            player.setLevel(0);
            player.giveExp(exp);
            ItemStack itemStack = getInventory().getItem(slot).clone();
            ItemMeta meta = itemStack.getItemMeta();
            List<String> lore = meta.getLore();
            lore.remove(lore.size() - 1);
            lore.remove(lore.size() - 1);
            lore.remove(lore.size() - 1);
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
            player.getInventory().addItem(itemStack);
            player.updateInventory();
            player.sendMessage(CC.Green + "Purchased " + meta.getDisplayName() + CC.Green + " for " + (int) cost + "xp");
        }
    }

    public static float getCostFromItem(ItemStack item) {
        NBTWrapper wrapper = new NBTWrapper(item);
        return wrapper.getFloat("xpShopPrice");
    }

    public ItemStack createGUIItem(Player player) {
        ItemStack item = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.GreenB + "Astro Xp Shop");
        List<String> lore = Lists.newArrayList();
        lore.add(CC.Gray + "This shop contains various items that will help");
        lore.add(CC.Gray + "you set up on AstroPvP");
        lore.add("");
        lore.add(CC.Green + "Your XP: " + CC.White + player.getTotalExperience());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack createFillerItem() {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData());
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate("&f"));
        item.setItemMeta(meta);
        return item;
    }

}


