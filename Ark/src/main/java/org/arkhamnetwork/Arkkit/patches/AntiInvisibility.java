/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class AntiInvisibility
        extends ArkPlugin {
    public static boolean classesEnabled = false;

    public AntiInvisibility(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        //this.registerTask(new AntiInvisibilityTask().runTaskTimer(this.getBukkitPlugin(), 20L, 40L), this.getClass());
        classesEnabled = new File("plugins/CosmicClasses.jar").exists();
    }

    @Override
    public void disable() {
    }
}

