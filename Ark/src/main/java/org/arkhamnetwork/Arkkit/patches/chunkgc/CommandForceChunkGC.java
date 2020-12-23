/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches.chunkgc;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

final class CommandForceChunkGC
        extends AbstractCommand {
    public CommandForceChunkGC(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        ChunkGCTask.doChunkGC();
        sender.sendMessage("doChunkGC() running!");
        return true;
    }
}

