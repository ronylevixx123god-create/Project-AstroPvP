/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class NoFallRegions
        extends ArkPlugin {
    public NoFallRegions(Plugin p) {
        super(p);
    }

    @Override
    public void enable() throws IOException {
        super.enable();
        this.registerListener(new FallDamageListener());
    }

    @Override
    public void disable() {
        this.unregisterListeners();
    }

    class FallDamageListener
            implements Listener {
        FallDamageListener() {
        }

        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onPlayerDamage(EntityDamageEvent event) {
            if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL && FactionUtils.isWarZone(event.getEntity().getLocation())) {
                event.setCancelled(true);
            }
        }
    }
}

