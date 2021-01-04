/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.exceptions.CosmicException
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  org.bukkit.entity.Player
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicskycustoms.struct;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface InventoryInteractable {
    public boolean acceptsInventoryClickType(InventoryItemType var1);

    public void onInventoryClick(Player var1, ItemStack var2, NBTWrapper var3, NBTWrapper var4, ItemStack var5, InventoryClickEvent var6) throws CosmicException;
}

