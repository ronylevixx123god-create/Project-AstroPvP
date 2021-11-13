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
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType
 *  net.minecraft.server.v1_8_R3.NBTBase
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerInteractEvent
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
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HolyWaterItem
extends CosmicItem
implements InventoryInteractable {
    public HolyWaterItem() {
        super("holy_water");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData(ItemUtils.addGlow(new ItemBuilder(Material.MILK_BUCKET, CC.GoldB + "Holy Water [" + CC.Yellow + "Max: 4" + CC.GoldB + "]", new String[]{CC.Yellow + "A rare consumable that", CC.Yellow + "increases the # of Holy White Scrolls", CC.Yellow + "that can be applied to an item by 1."}).build())).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {
        NBTTagCompoundBuilder cosmicData;
        if (clicked == null || clicked.getType() == Material.AIR || clickedWrapper == null) {
            return;
        }
        boolean corruptedOnce = false;
        if (clickedWrapper.hasKey("cosmicData")) {
            boolean bl = corruptedOnce = clickedWrapper.getCompoundOrCreate("cosmicData").hasKey("corrupted") && !clickedWrapper.getCompoundOrCreate("cosmicData").hasKey("holyWater");
        }
        if ((cosmicData = clickedWrapper.getCompoundOrCreate("cosmicData")).hasKey("holyWater")) {
            throw new PrefixedException("Holy Water has already been applied to this item!");
        }
        if (!HolyWhiteScrollUtils.itemHasHolyWhitescroll(clicked) && !corruptedOnce) {
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "This item must contain a Holy White Scroll before you can apply Holy Water!");
            e.setCancelled(true);
            return;
        }
        if (cursor.getAmount() > 1) {
            throw new PrefixedException("You cannot apply stacked Holy Water to an item.");
        }
        cosmicData.setInt("holyWater", Integer.valueOf(1));
        cosmicData.remove("corrupted");
        int scrollsApplied = HolyWhiteScrollUtils.getHolyScrollsApplied(clickedWrapper);
        e.setCancelled(true);
        e.getWhoClicked().setItemOnCursor(null);
        clickedWrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        clicked = clickedWrapper.build();
        HolyWhiteScrollUtils.applyHolyWhitescroll(clicked, scrollsApplied, 4, false);
        e.setCurrentItem(clicked);
        player.sendMessage(CC.Gold + "Successfully applied Holy Water to this item!");
    }

    public void onItemUse(Player player, ItemStack item, NBTWrapper wrapper, PlayerInteractEvent event) throws CosmicException {
        event.setCancelled(true);
    }
}

