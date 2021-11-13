/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.decimatemc.decimatecommons.util.CommonsUtil
 *  com.rit.sucy.EnchantmentAPI
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.inventory.InventoryType$SlotType
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicDungeons.items;

import com.compilex.CosmicDungeons.menus.GodlyTransmogGUI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.decimatemc.decimatecommons.util.CommonsUtil;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class GodlyTransmogScrollItem
extends CosmicItem
implements InventoryInteractable {
    public GodlyTransmogScrollItem() {
        super("godly_transmog_scroll");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData(CommonsUtil.createItem((Material)Material.PAPER, (String)"&6&lGodly Transmog Scroll", (String[])new String[]{"&7Apply to equipment to &6&ncustomize", "&7the order of &5custom &denchantments", "&7displayed on the item.", "", "&6&oPlace scroll on item to apply."})).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) {
        if (clicked == null || clicked.getType() == Material.AIR || clickedWrapper == null) {
            return;
        }
        if (cursor.getAmount() > 1) {
            player.sendMessage(CC.translate((String)"&c&l(!) &cYou cannot apply stacked Godly Transmog Scrolls."));
            return;
        }
        if (e.getClickedInventory().getType() != InventoryType.PLAYER) {
            return;
        }
        if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
            return;
        }
        if (EnchantmentAPI.getEnchantments((ItemStack)clicked).size() > 0) {
            e.setCancelled(true);
            player.setItemOnCursor(null);
            new GodlyTransmogGUI(player, new NBTWrapper(clicked).build().clone()).getInventory().open(player);
            player.getInventory().removeItem(new ItemStack[]{clicked});
        }
    }
}

