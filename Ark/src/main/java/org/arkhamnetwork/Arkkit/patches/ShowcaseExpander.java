/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.Arrays;
import org.arkhamnetwork.Arkkit.patches.CommandShowcaseExpander;
import org.arkhamnetwork.Arkkit.patches.ShowcaseExpanderListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ShowcaseExpander
extends ArkPlugin {
    public ShowcaseExpander(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandShowcaseExpander("/showcaseexpander", "/<command>", "Root command for showcase expanders.", Arrays.asList("sce")));
        this.registerListener(new ShowcaseExpanderListener());
    }

    @Override
    public void disable() {
    }

    public static ItemStack getShowcaseExpanderItem() {
        return ItemUtils.buildItem(Material.PAINTING, (short)0, (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "Showcase Expansion " + (Object)ChatColor.GRAY + "(Right Click)", (Object)ChatColor.GRAY + "Adds " + (Object)ChatColor.YELLOW + "+9 slots" + (Object)ChatColor.GRAY + " to your " + (Object)ChatColor.UNDERLINE + "/showcase");
    }

    public static boolean isShowcaseExpanderItem(ItemStack i) {
        return i != null && i.getType() == Material.PAINTING && i.hasItemMeta() && i.getItemMeta().hasLore() && ((String)i.getItemMeta().getLore().get(0)).equals(ShowcaseExpander.getShowcaseExpanderItem().getItemMeta().getLore().get(0));
    }
}

