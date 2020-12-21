/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.MinecraftServer
 */
package com.cosmicpvp.cosmicutils.nms;

import net.minecraft.server.v1_7_R4.MinecraftServer;

public class MinecraftServerUtils {
    public static int getCurrentServerTick() {
        return MinecraftServer.currentTick;
    }

    public static boolean isRunning() {
        return MinecraftServer.getServer().isRunning();
    }
}

