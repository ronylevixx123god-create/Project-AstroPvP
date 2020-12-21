/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.plugin.messaging;

import org.bukkit.entity.Player;

public interface PluginMessageListener {
    public void onPluginMessageReceived(String var1, Player var2, byte[] var3);
}

