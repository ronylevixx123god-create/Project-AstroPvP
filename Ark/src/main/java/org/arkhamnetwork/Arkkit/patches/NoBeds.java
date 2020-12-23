/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.player.PlayerBedEnterEvent
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.Arkkit.libs.ArkPluginListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class NoBeds
        extends ArkPlugin {
    public NoBeds(Plugin p) {
        super(p);
    }

    @Override
    public void enable() throws IOException {
        super.enable();
        this.registerListener(new NoBedListener(this));
    }

    @Override
    public void disable() throws IOException {
        super.disable();
    }

    class NoBedListener
            extends ArkPluginListener<NoBeds> {
        public NoBedListener(NoBeds patch) {
            super(patch);
        }

        @EventHandler
        public void onPLayerEnterBed(PlayerBedEnterEvent event) {
            if (!event.getPlayer().isOp()) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot sleep in a bed on CosmicPvP!");
            }
        }
    }
}

