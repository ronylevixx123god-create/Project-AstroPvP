/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.ItemUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Halloween
        extends ArkPlugin {
    public Halloween(Plugin p) {
        super(p);
    }

    public static ItemStack getHalloweenCandy() {
        return Halloween.getHalloweenCandy(1);
    }

    public static ItemStack getHalloweenCandy(int amount) {
        ItemStack retr = org.arkhamnetwork.Arkkit.utils.ItemUtils.buildItem(Material.MAGMA_CREAM, (short) 0, ChatColor.YELLOW + "Halloween Candy " + ChatColor.GRAY + "(Right Click)", ChatColor.GRAY + "A yummy piece of space candy, only", ChatColor.GRAY + "obtainable during " + ChatColor.UNDERLINE + "Halloween Events!");
        retr.setAmount(amount);
        return retr;
    }

    public static boolean isHalloweenCandy(ItemStack i) {
        try {
            return ItemUtils.areItemsSimilarIgnoringIID(Halloween.getHalloweenCandy(), i);
        } catch (Exception e) {
            return i.isSimilar(Halloween.getHalloweenCandy());
        }
    }

    @Override
    public void enable() {
        this.registerListener(new HalloweenListener());
    }

    @Override
    public void disable() {
    }
}

