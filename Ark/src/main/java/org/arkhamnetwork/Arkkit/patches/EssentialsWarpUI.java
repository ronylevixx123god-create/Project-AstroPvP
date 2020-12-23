/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.Essentials;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class EssentialsWarpUI
        extends ArkPlugin {
    public static LinkedHashMap<String, Material> warp_data = new LinkedHashMap();
    protected static Essentials ess;

    public EssentialsWarpUI(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (!this.getBukkitPlugin().getServer().getPluginManager().isPluginEnabled("Essentials")) {
            Bukkit.getLogger().info("(EssentialsWarpUI) Could not find Essentials.jar dependancy, disabling patch.");
            this.kill();
            return;
        }
        ess = (Essentials) this.getBukkitPlugin().getServer().getPluginManager().getPlugin("Essentials");
        LinkedList<String> config_data = new LinkedList(this.getBukkitPlugin().getConfig().getStringList("settings.essentials_warp_ui"));
        if (config_data != null && config_data.size() > 0) {
            Material m = null;
            Location warp_loc = null;
            for (String entry : config_data) {
                if (!entry.contains("=")) continue;
                String[] data = entry.split("=");
                try {
                    warp_loc = ess.getWarps().getWarp(data[0]).getBlock().getLocation();
                } catch (Exception e) {
                    Bukkit.getLogger().warning("Invalid warp name specified, '" + data[0] + "', skipping.");
                    e.printStackTrace();
                    continue;
                }
                try {
                    m = Material.valueOf(data[1].toUpperCase());
                } catch (Exception err) {
                    Bukkit.getLogger().warning("Invalid material enum '" + data[1] + "' specified for " + data[0] + ", defaulting to stone.");
                    err.printStackTrace();
                    m = Material.STONE;
                }
                if (m == null) {
                    Bukkit.getLogger().warning("Invalid material enum '" + data[1] + "' specified for " + data[0] + ", defaulting to stone.");
                    m = Material.STONE;
                }
                warp_data.put(data[0], m);
                Bukkit.getLogger().info("Loaded warp " + data[0] + "(" + warp_loc.toString() + ") with MATERIAL." + m.name());
            }
        } else {
            Bukkit.getLogger().info("(EssentialsWarpUI) No essentials_warp_ui defined, disabling patch.");
            this.kill();
            return;
        }
        this.registerListener(new EssentialsWarpUIListener());
    }

    @Override
    public void disable() {
        warp_data.clear();
    }
}

