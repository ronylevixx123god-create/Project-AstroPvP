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

import java.util.ArrayList;
import java.util.Arrays;
import org.arkhamnetwork.Arkkit.patches.CommandMysteryMobSpawner;
import org.arkhamnetwork.Arkkit.patches.MysteryMobSpawnersListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class MysteryMobSpawners
extends ArkPlugin {
    public MysteryMobSpawners(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new MysteryMobSpawnersListener());
        this.registerCommand(new CommandMysteryMobSpawner("mysterymobspawner", "/<command>", "Spawns mystery mob spawner.", new ArrayList<String>(Arrays.asList("mms"))));
    }

    @Override
    public void disable() {
    }

    public static ItemStack getMysteryMobSpawner() {
        return ItemUtils.buildItem(Material.MOB_SPAWNER, (short)0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Mystery Mob Spawner", (Object)ChatColor.GRAY + "Right-click in hand to reveal one", (Object)ChatColor.GRAY + "of the following mob spawner types:", "", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.GRAY + "Endermen", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.GRAY + "Pigzombie", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.GRAY + "Creeper", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.GRAY + "Blaze", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.GRAY + "Iron Golem", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.YELLOW + "Mooshroom Cow", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.YELLOW + "Magma Cube", ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "* " + (Object)ChatColor.YELLOW + "Ghast");
    }

    public static boolean isMysteryMobSpawner(ItemStack i) {
        return i != null && i.getType() == Material.MOB_SPAWNER && i.hasItemMeta() && i.getItemMeta().hasLore() && ((String)i.getItemMeta().getLore().get(0)).equals((Object)ChatColor.GRAY + "Right-click in hand to reveal one");
    }
}

