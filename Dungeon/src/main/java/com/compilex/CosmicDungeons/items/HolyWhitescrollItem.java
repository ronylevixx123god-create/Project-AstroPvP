/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.exceptions.CosmicException
 *  com.cosmicpvp.cosmicutils.exceptions.PrefixedException
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  me.ifamasssxd.cosmicskycustoms.exceptions.ItemDupedException
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicDungeons.items;

import com.compilex.CosmicDungeons.utils.HolyWhiteScrollUtils;
import com.compilex.CosmicDungeons.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmicskycustoms.exceptions.ItemDupedException;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class HolyWhitescrollItem
extends CosmicItem
implements InventoryInteractable {
    public HolyWhitescrollItem() {
        super("holy_white_scroll");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData(ItemUtils.addGlow(new ItemBuilder(Material.PAPER, CC.GoldB + "Holy White Scroll", new String[]{CC.Yellow + "A legendary /dungeon reward that", CC.Yellow + "can be applied to armor/weapons,", CC.Yellow + "gives a 100% chance of not losing", CC.Yellow + "the blessed item on death.", "", CC.GoldU + "REQ:" + CC.Yellow + " White Scroll"}).build())).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {
        if (clicked == null || clicked.getType() == Material.AIR || clickedWrapper == null) {
            return;
        }
        if (!EnchantmentBook.hasWhitescroll((ItemStack)clicked)) {
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "This item must contain a White Scroll before you can apply a Holy White Scroll!");
            e.setCancelled(true);
            return;
        }
        NBTTagCompoundBuilder cosmicData = clickedWrapper.getCompoundOrCreate("cosmicData");
        if (cosmicData != null) {
            if (cosmicData.hasKey("abilitySet")) {
                throw new PrefixedException("You cannot apply a Holy Whitescroll to Mastery Kit items!");
            }
            if (cosmicData.hasKey("corrupted")) {
                throw new PrefixedException("Holy White Scrolls can no longer be applied to this corrupted item!");
            }
        }
        if (HolyWhiteScrollUtils.itemHasHolyWhitescroll(e.getCurrentItem())) {
            throw new PrefixedException("You cannot apply more than 1 Holy White Scroll to an item!");
        }
        if (wrapper.isBlacklistedIID()) {
            throw new ItemDupedException("Holy White scroll already used!");
        }
        int scrollsApplied = HolyWhiteScrollUtils.getHolyScrollsApplied(clickedWrapper);
        if (cosmicData.hasKey("holyWater")) {
            if (scrollsApplied >= 4) {
                throw new PrefixedException("This item has already had 4 Holy Scrolls applied to it!");
            }
        } else if (scrollsApplied >= 3) {
            throw new PrefixedException("This item has already had 3 Holy Scrolls applied to it!");
        }
        HolyWhiteScrollUtils.setHolyWhiteScrollsApplied(clickedWrapper, ++scrollsApplied);
        wrapper.blacklistIID("holyWhiteScrollApply");
        e.setCancelled(true);
        if (e.getWhoClicked().getItemOnCursor().getAmount() > 1) {
            e.getWhoClicked().getItemOnCursor().setAmount(e.getWhoClicked().getItemOnCursor().getAmount() - 1);
        } else {
            e.getWhoClicked().setItemOnCursor(null);
        }
        clicked = clickedWrapper.build();
        HolyWhiteScrollUtils.applyHolyWhitescroll(clicked, scrollsApplied, HolyWhiteScrollUtils.getMaxHolyAllowed(clicked), true);
        e.setCurrentItem(clicked);
        player.sendMessage(CC.GoldB + (HolyWhiteScrollUtils.getMaxHolyAllowed(clicked) - scrollsApplied) + " Holy White Scrolls " + CC.Gold + "left to apply to this item!");
        player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0f, 1.0f);
    }
}

