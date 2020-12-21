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
import org.arkhamnetwork.Arkkit.patches.HalloweenListener;
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

    @Override
    public void enable() {
        this.registerListener(new HalloweenListener());
    }

    @Override
    public void disable() {
    }

    public static ItemStack getHalloweenCandy() {
        return Halloween.getHalloweenCandy(1);
    }

    public static ItemStack getHalloweenCandy(int amount) {
        ItemStack retr = org.arkhamnetwork.Arkkit.utils.ItemUtils.buildItem(Material.MAGMA_CREAM, (short)0, (Object)ChatColor.YELLOW + "Halloween Candy " + (Object)ChatColor.GRAY + "(Right Click)", (Object)ChatColor.GRAY + "A yummy piece of space candy, only", (Object)ChatColor.GRAY + "obtainable during " + (Object)ChatColor.UNDERLINE + "Halloween Events!");
        retr.setAmount(amount);
        return retr;
    }

    public static boolean isHalloweenCandy(ItemStack i) {
        try {
            return ItemUtils.areItemsSimilarIgnoringIID((ItemStack)Halloween.getHalloweenCandy(), (ItemStack)i);
        }
        catch (Exception e) {
            return i.isSimilar(Halloween.getHalloweenCandy());
        }
    }
}

