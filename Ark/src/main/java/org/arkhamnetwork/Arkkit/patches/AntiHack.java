/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  org.bukkit.Bukkit
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.Essentials;
import java.util.ArrayList;
import java.util.List;
import org.arkhamnetwork.Arkkit.patches.PhazeListener;
import org.arkhamnetwork.Arkkit.patches.PlayerMoveUnderBedrockListener;
import org.arkhamnetwork.Arkkit.patches.mcMMOExploitListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class AntiHack
extends ArkPlugin {
    protected static Essentials ess3 = null;
    protected static List<String> playerNamesTeleportToSpawn = new ArrayList<String>();

    public AntiHack(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerListener(new PhazeListener());
        this.registerListener(new PlayerMoveUnderBedrockListener());
        if (Bukkit.getPluginManager().isPluginEnabled("mcMMO")) {
            this.registerListener(new mcMMOExploitListener());
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Essentials")) {
            ess3 = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
        }
    }

    @Override
    public void disable() {
        ess3 = null;
    }

    public static boolean hasTeleportedCrossWorld(Player p) {
        return p.hasMetadata("arkkitTeleportEvent") && System.currentTimeMillis() - ((MetadataValue)p.getMetadata("arkkitTeleportEvent").get(0)).asLong() <= 500L;
    }
}

