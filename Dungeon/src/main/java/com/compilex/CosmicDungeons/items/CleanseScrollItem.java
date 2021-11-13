/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.exceptions.CosmicException
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.decimatemc.decimatecommons.util.TimeUtil
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType
 *  net.minecraft.server.v1_8_R3.NBTBase
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryType
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicDungeons.items;

import com.compilex.CosmicDungeons.utils.HolyWhiteScrollUtils;
import com.compilex.CosmicDungeons.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.decimatemc.decimatecommons.util.TimeUtil;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CleanseScrollItem
extends CosmicItem
implements InventoryInteractable {
    public CleanseScrollItem() {
        super("cleanse_scroll");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        return this.applyItemData(ItemUtils.addGlow(new ItemBuilder(Material.PAPER, CC.GoldB + "Cleanse Scroll", new String[]{CC.Yellow + "Apply this scroll to any item", CC.Yellow + "that is corrupted or semi-corrupted,", CC.Yellow + "and it will cleanse it by 1 phase.", "", CC.Gold + "Can only be used once per 30 minutes."}).build())).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {
        if (clicked == null || clicked.getType() == Material.AIR || clickedWrapper == null) {
            return;
        }
        if (e.getClickedInventory().getType() != InventoryType.PLAYER) {
            return;
        }
        NBTTagCompoundBuilder cosmicData = clickedWrapper.getCompoundOrCreate("cosmicData");
        if (!cosmicData.hasKey("holyScrollsApplied")) {
            e.setCancelled(true);
            return;
        }
        if (!player.isOp() && cosmicData.hasKey("nextCleanseScroll") && cosmicData.getLong("nextCleanseScroll") > System.currentTimeMillis()) {
            player.sendMessage(CC.translate((String)("&c&l(!) &cYou cannot apply another Cleanse Scroll to this item for " + TimeUtil.formatTimeAbbreviated((long)((cosmicData.getLong("nextCleanseScroll") - System.currentTimeMillis()) / 1000L)))) + ".");
            e.setCancelled(true);
            return;
        }
        int scrollsApplied = HolyWhiteScrollUtils.getHolyScrollsApplied(clickedWrapper);
        if (scrollsApplied <= 1) {
            player.sendMessage(CC.translate((String)"&c&l(!) &cThis item must be corrupted at least twice to be able to cleanse it."));
            e.setCancelled(true);
            return;
        }
        if (e.getWhoClicked().getItemOnCursor().getAmount() > 1) {
            e.getWhoClicked().getItemOnCursor().setAmount(e.getWhoClicked().getItemOnCursor().getAmount() - 1);
        } else {
            e.getWhoClicked().setItemOnCursor(null);
        }
        cosmicData.remove("corrupted");
        cosmicData.setInt("holyScrollsApplied", Integer.valueOf(cosmicData.getInt("holyScrollsApplied") - 1));
        cosmicData.setLong("nextCleanseScroll", Long.valueOf(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30L)));
        e.setCancelled(true);
        clickedWrapper.setNBTBase("cosmicData", (NBTBase)cosmicData.get());
        clicked = clickedWrapper.build();
        HolyWhiteScrollUtils.applyHolyWhitescroll(clicked, scrollsApplied - 1, cosmicData.hasKey("holyWater") ? 4 : 3, false);
        e.setCurrentItem(clicked);
        player.sendMessage(CC.GreenB + "(!) " + CC.Green + "Successfully applied a Cleanse Scroll to this item!");
    }

    public void onItemUse(Player player, ItemStack item, NBTWrapper wrapper, PlayerInteractEvent event) throws CosmicException {
        event.setCancelled(true);
    }
}

