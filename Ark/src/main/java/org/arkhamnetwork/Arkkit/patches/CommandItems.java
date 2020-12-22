/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CommandItems
extends ArkPlugin {
    public CommandItems(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerCommand(new CommandCommandItem("commanditem", "/<command>", "Spawns command items.", Lists.newArrayList("icmd")));
        this.registerCommand(new CommandPreRank("prerank", "/<command>", "Spawns prerank items."));
        this.registerListener(new CommandItemsListener());
    }

    @Override
    public void disable() {
    }

    public static boolean isCommandItem(ItemStack i) {
        return i != null && i.getType() != Material.AIR && new NBTWrapper(i).getString("cosmicType").equals("commandItem");
    }

    public static String getCommandItemCommand(ItemStack i) {
        if (CommandItems.isCommandItem(i)) {
            return new NBTWrapper(i).getCompound("conaxData").getString("command");
        }
        return null;
    }

    public static String getCommandItemMessage(ItemStack i) {
        if (CommandItems.isCommandItem(i)) {
            return new NBTWrapper(i).getCompound("conaxData").getString("message");
        }
        return null;
    }
}

