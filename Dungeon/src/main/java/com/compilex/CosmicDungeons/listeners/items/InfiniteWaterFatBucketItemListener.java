/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.BlockFace
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerBucketEmptyEvent
 */
package com.compilex.CosmicDungeons.listeners.items;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class InfiniteWaterFatBucketItemListener
implements Listener {
    @EventHandler(ignoreCancelled=true)
    private void onBucketEmpty(PlayerBucketEmptyEvent event) {
        NBTWrapper wrapper = new NBTWrapper(event.getPlayer().getItemInHand());
        if (wrapper.hasKey("cosmicType") && wrapper.getString("cosmicType").equals("infinite_water_fat_bucket")) {
            event.setCancelled(true);
            if (event.getPlayer().getItemInHand().getAmount() > 1) {
                event.getPlayer().sendMessage(CC.translate((String)"&c&l(!) &cYou cannot use stacked Infinite Water Fat Buckets."));
                return;
            }
            if (CosmicDungeonsPlugin.get().getEconomy().getBalance(event.getPlayer().getName()) < 500.0) {
                event.getPlayer().sendMessage(CC.translate((String)"&c&l(!) &cYou do not have enough money! (req: $500)"));
                return;
            }
            this.getPlacedLocation(event.getBlockClicked().getLocation(), event.getBlockFace()).getBlock().setType(Material.WATER);
            event.getPlayer().setItemInHand(wrapper.build());
            if (CosmicDungeonsPlugin.get().getEconomy().getBalance(event.getPlayer().getName()) >= 500.0) {
                CosmicDungeonsPlugin.get_().getEconomy().withdrawPlayer(event.getPlayer().getName(), 500.0);
                event.getPlayer().sendMessage(CC.translate((String)"&c&l- $500"));
            }
        }
    }

    private Location getPlacedLocation(Location l, BlockFace b) {
        int y;
        int x;
        int n = b == BlockFace.WEST ? -1 : (x = b == BlockFace.EAST ? 1 : 0);
        int n2 = b == BlockFace.DOWN ? -1 : (y = b == BlockFace.UP ? 1 : 0);
        int z = b == BlockFace.SOUTH ? 1 : (b == BlockFace.NORTH ? -1 : 0);
        return new Location(l.getWorld(), (double)(l.getBlockX() + x), (double)(l.getBlockY() + y), (double)(l.getBlockZ() + z));
    }
}

