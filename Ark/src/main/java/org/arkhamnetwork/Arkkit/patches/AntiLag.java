/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 *  net.minecraft.util.io.netty.util.ResourceLeakDetector
 *  net.minecraft.util.io.netty.util.ResourceLeakDetector$Level
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.Iterator;
import java.util.LinkedList;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.util.io.netty.util.ResourceLeakDetector;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.AntiCrash;
import org.arkhamnetwork.Arkkit.patches.AntiLagListener;
import org.arkhamnetwork.Arkkit.patches.AntiPistonListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class AntiLag
extends ArkPlugin {
    private long startTime;
    private boolean rebooting = false;
    LinkedList<Double> previousTPS;

    public AntiLag(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        this.startTime = System.currentTimeMillis();
        this.previousTPS = new LinkedList();
        try {
            ResourceLeakDetector.setLevel((ResourceLeakDetector.Level)ResourceLeakDetector.Level.DISABLED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Bukkit.getScheduler().runTaskTimer((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                if (AntiLag.this.rebooting) {
                    return;
                }
                AntiLag.this.previousTPS.add(MinecraftServer.getServer().recentTps[0]);
                if (AntiLag.this.previousTPS.size() > 6) {
                    AntiLag.this.previousTPS.removeFirst();
                    if (System.currentTimeMillis() - AntiLag.this.startTime >= 21600000L) {
                        Bukkit.getLogger().info("[Arkkit (AntiLag)] averageTPS: " + AntiLag.this.getAverageTPS());
                        if (AntiLag.this.getAverageTPS() < 18.0) {
                            AntiLag.this.rebooting = true;
                            AntiCrash.restartServer(60);
                        }
                    }
                }
            }
        }, 200L, 200L);
        this.registerListener(new AntiPistonListener());
        this.registerListener(new AntiLagListener());
    }

    @Override
    public void disable() {
        this.unregisterListeners();
    }

    private double getAverageTPS() {
        double d = this.previousTPS.size();
        double t = 0.0;
        Iterator iterator = this.previousTPS.iterator();
        while (iterator.hasNext()) {
            double x = (Double)iterator.next();
            t += x;
        }
        return t / d;
    }
}

