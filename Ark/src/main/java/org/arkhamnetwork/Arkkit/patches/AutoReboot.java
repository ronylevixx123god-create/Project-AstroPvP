/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class AutoReboot
extends ArkPlugin {
    public AutoReboot(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                Bukkit.getScheduler().runTask((Plugin)Arkkit.get(), new Runnable(){

                    @Override
                    public void run() {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)"arkreboot");
                    }
                });
            }
        }, 864000L);
    }

    @Override
    public void disable() {
    }
}

