/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.exceptions.CosmicException
 *  com.cosmicpvp.cosmicutils.exceptions.PrefixedException
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable
 *  me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicDungeons.items;

import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.utils.ItemUtils;
import com.compilex.CosmicDungeons.utils.PortalTrinketUtils;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import java.util.UUID;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PortalTimeReductionTrinketItem
extends CosmicItem
implements InventoryInteractable {
    public PortalTimeReductionTrinketItem() {
        super("portal_time_reduction_trinket");
    }

    public static int getSecondsFromTier(int tier) {
        return tier == 1 ? 10 : (tier == 2 ? 20 : 30);
    }

    public ItemStack createItem(Player player, Object ... objects) {
        int tier = objects[0].equals("1") ? 1 : (tier = objects[0].equals("2") ? 2 : 3);
        String romanNumeral = tier == 1 ? "I" : (tier == 2 ? "II" : "III");
        return new NBTWrapper(this.applyItemData(ItemUtils.addGlow(new ItemBuilder(Material.FLINT_AND_STEEL, CC.GoldB + "Portal Time Reduction Trinket " + CC.GrayB + "[" + CC.GoldB + romanNumeral + CC.GrayB + "]", new String[]{CC.Gray + "Apply to a /dungeon portal item", CC.Gray + "that, when placed, will reduce the", CC.Gray + "portal close time by " + PortalTimeReductionTrinketItem.getSecondsFromTier(tier) + "s.", "", CC.Gray + "Drag n' drop onto item to apply."}).build())).build()).setString("random", UUID.randomUUID().toString()).build();
    }

    public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
        return inventoryItemType == InventoryItemType.CURSOR;
    }

    public void onInventoryClick(Player player, ItemStack cursor, NBTWrapper wrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent e) throws CosmicException {
        if (clicked == null || clicked.getType() == Material.AIR || clickedWrapper == null) {
            return;
        }
        if (!DungeonsAPI.get().isDungeonPortal(clicked)) {
            return;
        }
        if (DungeonsAPI.get().isHeroicPortal(clicked)) {
            throw new PrefixedException("You cannot apply trinkets to heroic portals!");
        }
        if (cursor.getAmount() > 1) {
            throw new PrefixedException("You cannot apply stacked Portal Time Reduction Trinkets to an item.");
        }
        if (clicked.getAmount() > 1) {
            throw new PrefixedException("You cannot apply Time Reduction Trinkets to stacked portals.");
        }
        if (PortalTrinketUtils.portalHasTrinket(e.getCurrentItem())) {
            throw new PrefixedException("You cannot apply more than 1 trinket to a portal!");
        }
        e.setCancelled(true);
        e.getWhoClicked().setItemOnCursor(null);
        clicked = clickedWrapper.build();
        int tier = PortalTrinketUtils.getTierFromName(cursor.getItemMeta().getDisplayName());
        String romanNumeral = tier == 1 ? "I" : (tier == 2 ? "II" : "III");
        PortalTrinketUtils.applyTrinket(clicked, "TIME_REDUCTION_" + PortalTrinketUtils.getTierFromName(cursor.getItemMeta().getDisplayName()), "Time Reduction " + romanNumeral);
        e.setCurrentItem(clicked);
    }

    public void onItemUse(Player player, ItemStack item, NBTWrapper wrapper, PlayerInteractEvent event) throws CosmicException {
        event.setCancelled(true);
    }
}

