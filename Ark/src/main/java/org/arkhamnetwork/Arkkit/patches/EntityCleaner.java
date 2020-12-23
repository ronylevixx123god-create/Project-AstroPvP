/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

public class EntityCleaner
        extends ArkPlugin {
    public EntityCleaner(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.registerTask(new EntityCleanerTask((Arkkit) this.getBukkitPlugin()).runTaskTimer(this.getBukkitPlugin(), 20L, 200L), EntityCleanerTask.class);
    }

    @Override
    public void disable() {
    }
}

