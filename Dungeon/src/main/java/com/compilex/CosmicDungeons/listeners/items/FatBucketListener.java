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
 *  org.bukkit.event.player.PlayerBucketFillEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicDungeons.listeners.items;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FatBucketListener
implements Listener {
    public static ItemStack updateFatBucketItem(ItemStack item) {
        String after;
        NBTWrapper wrapper = new NBTWrapper(item);
        if (wrapper.getCosmicData().getString("mode").equals("use")) {
            after = "&7(&f" + wrapper.getCosmicData().getInt("uses") + " uses&7)";
            item.setType(Material.LAVA_BUCKET);
        } else {
            after = "&7(&f" + wrapper.getCosmicData().getInt("percentFull") + "% full&7)";
            item.setType(Material.BUCKET);
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate((String)("&c&lFat Bucket " + after)));
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler(ignoreCancelled=true)
    private void onBucketFill(PlayerBucketFillEvent event) {
        NBTWrapper wrapper = new NBTWrapper(event.getPlayer().getItemInHand());
        if (wrapper.hasKey("cosmicType") && wrapper.getString("cosmicType").equals("fat_bucket")) {
            event.setCancelled(true);
            if (event.getPlayer().getItemInHand().getAmount() > 1) {
                event.getPlayer().sendMessage(CC.translate((String)"&c&l(!) &cYou cannot fill stacked Fat Buckets."));
                return;
            }
            String mode = wrapper.getCosmicData().getString("mode");
            if (mode.equals("fill") && event.getBlockClicked().getType().name().contains("LAVA")) {
                event.getBlockClicked().setType(Material.AIR);
                wrapper.getCosmicData().setInt("percentFull", Integer.valueOf(wrapper.getCosmicData().getInt("percentFull") + 2));
                if (wrapper.getCosmicData().getInt("percentFull") >= 100) {
                    wrapper.getCosmicData().setString("mode", "use");
                    wrapper.getCosmicData().setInt("percentFull", Integer.valueOf(0));
                    wrapper.getCosmicData().setInt("uses", Integer.valueOf(256));
                }
                event.getPlayer().setItemInHand(FatBucketListener.updateFatBucketItem(wrapper.build()));
            }
        }
    }

    @EventHandler(ignoreCancelled=true)
    private void onBucketEmpty(PlayerBucketEmptyEvent event) {
        NBTWrapper wrapper = new NBTWrapper(event.getPlayer().getItemInHand());
        if (wrapper.hasKey("cosmicType") && wrapper.getString("cosmicType").equals("fat_bucket")) {
            event.setCancelled(true);
            if (event.getPlayer().getItemInHand().getAmount() > 1) {
                event.getPlayer().sendMessage(CC.translate((String)"&c&l(!) &cYou cannot use stacked Fat Buckets."));
                return;
            }
            String mode = wrapper.getCosmicData().getString("mode");
            if (mode.equals("use")) {
                this.getPlacedLocation(event.getBlockClicked().getLocation(), event.getBlockFace()).getBlock().setType(Material.LAVA);
                wrapper.getCosmicData().setInt("uses", Integer.valueOf(wrapper.getCosmicData().getInt("uses") - 1));
                if (wrapper.getCosmicData().getInt("uses") <= 0) {
                    wrapper.getCosmicData().setString("mode", "fill");
                    wrapper.getCosmicData().setInt("uses", Integer.valueOf(0));
                    wrapper.getCosmicData().setInt("percentFull", Integer.valueOf(0));
                }
                event.getPlayer().setItemInHand(FatBucketListener.updateFatBucketItem(wrapper.build()));
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

