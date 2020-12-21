/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.patches.DropperBlockerListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

public class DropperBlocker
extends ArkPlugin {
    protected static List<Material> no_dropper = new ArrayList<Material>();

    public DropperBlocker(Plugin p) {
        super(p);
        no_dropper.add(Material.MONSTER_EGG);
        no_dropper.add(Material.MONSTER_EGGS);
        no_dropper.add(Material.ENDER_PEARL);
        no_dropper.add(Material.EXP_BOTTLE);
        no_dropper.add(Material.EGG);
        no_dropper.add(Material.POTION);
        no_dropper.add(Material.DIAMOND);
        no_dropper.add(Material.MOB_SPAWNER);
    }

    @Override
    public void enable() {
        this.registerListener(new DropperBlockerListener());
    }

    @Override
    public void disable() {
    }
}

