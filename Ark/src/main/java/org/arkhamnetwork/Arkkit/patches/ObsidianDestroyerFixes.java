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
import org.arkhamnetwork.Arkkit.patches.ExplosionPhysicsSanity;
import org.arkhamnetwork.Arkkit.patches.GrowthPhysicsSanity;
import org.arkhamnetwork.Arkkit.patches.ObsidianDestroyerBootstrapListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

public class ObsidianDestroyerFixes
extends ArkPlugin {
    protected static List<Material> bad_blocks = new ArrayList<Material>();
    protected static final int radius = 3;

    public ObsidianDestroyerFixes(Plugin p) {
        super(p);
        bad_blocks.add(Material.COBBLE_WALL);
        bad_blocks.add(Material.FENCE);
        bad_blocks.add(Material.NETHER_FENCE);
        bad_blocks.add(Material.SIGN);
        bad_blocks.add(Material.SIGN_POST);
        bad_blocks.add(Material.FENCE_GATE);
        bad_blocks.add(Material.IRON_FENCE);
        bad_blocks.add(Material.ANVIL);
        bad_blocks.add(Material.ITEM_FRAME);
        bad_blocks.add(Material.COCOA);
        bad_blocks.add(Material.BREWING_STAND);
        bad_blocks.add(Material.BEACON);
        bad_blocks.add(Material.PORTAL);
        bad_blocks.add(Material.SKULL);
        bad_blocks.add(Material.STAINED_GLASS_PANE);
        bad_blocks.add(Material.CACTUS);
        bad_blocks.add(Material.SUGAR_CANE_BLOCK);
        bad_blocks.add(Material.THIN_GLASS);
    }

    @Override
    public void enable() {
        this.registerListener(new ObsidianDestroyerBootstrapListener());
        this.registerListener(new ExplosionPhysicsSanity());
        this.registerListener(new GrowthPhysicsSanity());
    }

    @Override
    public void disable() {
    }
}

