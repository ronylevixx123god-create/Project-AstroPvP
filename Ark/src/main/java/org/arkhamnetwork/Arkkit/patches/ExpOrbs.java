/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.Arrays;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.ExpOrbsCommand;
import org.arkhamnetwork.Arkkit.patches.ExpOrbsListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ExpOrbs
extends ArkPlugin {
    public ExpOrbs(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new ExpOrbsListener());
        this.registerCommand(new ExpOrbsCommand("xporb", "/<command>", "Converts your vanilla levels into an XP Orb.", new ArrayList<String>(Arrays.asList("exporb", "withdrawxp"))));
    }

    @Override
    public void disable() {
        if (!Arkkit.get().shutting_down) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.removeMetadata("customXpOrbValue", (Plugin)Arkkit.get());
            }
        }
    }

    public static ItemStack getXpOrb(int xpAmount, String enchanter) {
        return ItemUtils.buildItem(Material.SLIME_BALL, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Experience Orb" + (Object)ChatColor.GRAY + " (Right Click)", (Object)ChatColor.YELLOW + "Value " + (Object)ChatColor.GRAY + xpAmount + " XP", (Object)ChatColor.YELLOW + "Summoner " + (Object)ChatColor.GRAY + enchanter);
    }

    public static boolean isXpBottle(ItemStack i) {
        return i != null && i.getType() == Material.SLIME_BALL && i.hasItemMeta() && i.getItemMeta().hasLore() && ((String)i.getItemMeta().getLore().get(0)).startsWith((Object)ChatColor.YELLOW + "Value ");
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

