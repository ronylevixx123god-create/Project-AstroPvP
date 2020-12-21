/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches.networkevents;

import org.bukkit.entity.Player;

public interface IPacketBuffer {
    public void writeString(String var1);

    public void writeInt(int var1);

    public void writeLong(long var1);

    public void send(Player var1, String var2);
}

