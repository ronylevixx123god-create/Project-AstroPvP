/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Material
 *  org.bukkit.entity.Creeper
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public class DoubleDrops
        extends ArkPlugin {
    public DoubleDrops(Plugin p) {
        super(p);
    }

    @Override
    public void enable() throws IOException {
        super.enable();
        this.registerListener(new DoubleDropListener());
    }

    @Override
    public void disable() throws IOException {
        super.disable();
        this.unregisterListeners();
    }

    class DoubleDropListener
            implements Listener {
        DoubleDropListener() {
        }

        @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
        public void onPlayerDEath(EntityDeathEvent event) {
            if (event instanceof Player) {
                return;
            }
            if (event.getEntity() instanceof Creeper) {
                int overflow = 0;
                for (ItemStack sulpherDrop : event.getDrops()) {
                    if (sulpherDrop == null || sulpherDrop.getType() != Material.SULPHUR) continue;
                    int doubleAmount = sulpherDrop.getAmount() * 2;
                    if (doubleAmount > sulpherDrop.getMaxStackSize()) {
                        sulpherDrop.setAmount(sulpherDrop.getMaxStackSize());
                        overflow += doubleAmount - sulpherDrop.getMaxStackSize();
                        continue;
                    }
                    sulpherDrop.setAmount(doubleAmount);
                }
                if (overflow > 0) {
                    int stacksToGive;
                    int leftOver = overflow % 64;
                    if (overflow > 64 && (stacksToGive = (int) Math.floor((double) overflow / 64.0)) >= 1) {
                        for (int i = 0; i < stacksToGive; ++i) {
                            event.getDrops().add(new ItemStack(Material.SULPHUR, Material.SULPHUR.getMaxStackSize()));
                        }
                    }
                    if (leftOver > 0) {
                        event.getDrops().add(new ItemStack(Material.SULPHUR, leftOver));
                    }
                }
            }
        }
    }
}

