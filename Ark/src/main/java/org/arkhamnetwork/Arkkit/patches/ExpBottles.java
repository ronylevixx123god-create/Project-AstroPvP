/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.ExpBottlesCommand;
import org.arkhamnetwork.Arkkit.patches.ExpBottlesListener;
import org.arkhamnetwork.Arkkit.patches.ExpCombineCommand;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ExpBottles
extends ArkPlugin {
    public ExpBottles(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new ExpBottlesListener());
        this.registerCommand(new ExpBottlesCommand("xpbottle", "/<command>", "Converts your vanilla levels into a tradeable XP bottle.", new ArrayList<String>(Arrays.asList("expbottle", "withdrawxp"))));
        this.registerCommand(new ExpCombineCommand("combineexp", "/<command>", "Combine EXP bottles in your inventory together.", Lists.newArrayList((Object[])new String[]{"combinexp", "combinebottles"})));
    }

    @Override
    public void disable() {
        if (!Arkkit.get().shutting_down) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.removeMetadata("customXpBottleValue", (Plugin)Arkkit.get());
            }
        }
    }

    public static ItemStack getXpBottle(int xpAmount, String enchanter) {
        ItemStack i = ItemUtils.buildItem(Material.EXP_BOTTLE, (short)0, ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Experience Bottle" + (Object)ChatColor.GRAY + " (Throw)", (Object)ChatColor.LIGHT_PURPLE + "Value " + (Object)ChatColor.WHITE + xpAmount + " XP", (Object)ChatColor.LIGHT_PURPLE + "Enchanter " + (Object)ChatColor.WHITE + enchanter);
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
            i = ItemID.setItemID(i, ItemID.generateNewItemID());
        }
        return i;
    }

    public static boolean isXpBottle(ItemStack i) {
        return i != null && i.getType() == Material.EXP_BOTTLE && i.hasItemMeta() && i.getItemMeta().hasLore() && ((String)i.getItemMeta().getLore().get(0)).startsWith((Object)ChatColor.LIGHT_PURPLE + "Value ");
    }

    public static int getXpAmount(ItemStack xpBottle) {
        try {
            return Integer.parseInt(ChatColor.stripColor((String)((String)xpBottle.getItemMeta().getLore().get(0)).split(" ")[1].replace("xp", "")));
        }
        catch (Exception err) {
            return 0;
        }
    }
}

