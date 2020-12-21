/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.command;

import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

public interface BlockCommandSender
extends CommandSender {
    public Block getBlock();
}

