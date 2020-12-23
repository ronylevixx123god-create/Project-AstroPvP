/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.java.JavaPlugin
 */
package me.ifamasssxd.cosmicguishop.utils;

import com.earth2me.essentials.Essentials;
import me.ifamasssxd.cosmicguishop.struct.ShopMaterialData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemUtils {
    public static String getPrettyName(ShopMaterialData data) {
        return ItemNames.lookup(data);
    }

    public static boolean hasEssentialsPrice(ItemStack item) {
        try {
            Essentials ess = (Essentials) JavaPlugin.getPlugin(Essentials.class);
            if (ess != null && ess.getWorth().getPrice(ess, item) != null) {
                return true;
            }
        } catch (Exception exception) {
            // empty catch block
        }
        return false;
    }
}

