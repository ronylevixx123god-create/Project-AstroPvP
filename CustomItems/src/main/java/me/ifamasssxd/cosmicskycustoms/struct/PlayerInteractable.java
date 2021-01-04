/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.exceptions.CosmicException
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  org.bukkit.entity.Player
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicskycustoms.struct;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface PlayerInteractable {
    public void onPlayerInteractable(Player var1, ItemStack var2, NBTWrapper var3, PlayerInteractEvent var4) throws CosmicException;
}

