/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemBuilder
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicDungeons.items;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import java.util.List;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChunkBusterItem
extends CosmicItem {
    public ChunkBusterItem() {
        super("chunk_buster");
    }

    public ItemStack createItem(Player player, Object ... objects) {
        int uses = 1;
        if (objects.length > 0) {
            try {
                uses = Integer.parseInt(objects[0].toString());
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return this.applyItemData(new ItemBuilder(Material.BEACON, CC.translate((String)"&4&lChunk Buster &7(Place Down)"), new String[]{CC.translate((String)"&fPlace this &4&nChunk Buster&f in your claimed"), CC.translate((String)"&ffaction land to destroy the chunk!"), "", CC.translate((String)("&f&l* &4Remaining Uses: &f" + uses)), "", CC.translate((String)"&c&l(!) &fThis item will &c&ndestroy&f the entire chunk.")}).build()).build();
    }

    public static boolean isChunkBuster(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
            ItemMeta meta = item.getItemMeta();
            return meta.getDisplayName().equalsIgnoreCase(CC.translate((String)"&4&lChunk Buster &7(Place Down)")) && ((String)meta.getLore().get(0)).equalsIgnoreCase(CC.translate((String)"&fPlace this &4&nChunk Buster&f in your claimed"));
        }
        return false;
    }

    public static int getUses(ItemStack item) {
        String l = (String)item.getItemMeta().getLore().get(3);
        return Integer.parseInt(l.substring(26));
    }

    public static void setUses(ItemStack item, int uses) {
        ItemMeta meta = item.getItemMeta();
        List lore = meta.getLore();
        lore.set(3, CC.translate((String)("&f&l* &4Remaining Uses: &f" + uses)));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}

