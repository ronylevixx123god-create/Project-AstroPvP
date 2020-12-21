/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.command;

import org.bukkit.Server;
import org.bukkit.permissions.Permissible;

public interface CommandSender
extends Permissible {
    public void sendMessage(String var1);

    public void sendMessage(String[] var1);

    public Server getServer();

    public String getName();
}

