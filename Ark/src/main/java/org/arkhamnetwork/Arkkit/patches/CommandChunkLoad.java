/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.spigotmc.SpigotConfig
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.spigotmc.SpigotConfig;

final class CommandChunkLoad
extends AbstractCommand {
    public CommandChunkLoad(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (SpigotConfig.loadUnloadedTNTEntities) {
            SpigotConfig.loadUnloadedEnderPearls = false;
            SpigotConfig.loadUnloadedFallingBlocks = false;
            SpigotConfig.loadUnloadedTNTEntities = false;
            sender.sendMessage("ENTITES WILL NO LONGER LOAD CHUNKS");
        } else {
            SpigotConfig.loadUnloadedEnderPearls = true;
            SpigotConfig.loadUnloadedFallingBlocks = true;
            SpigotConfig.loadUnloadedTNTEntities = true;
            sender.sendMessage("ENTITIES WILL NOW LOAD CHUNKS");
        }
        return true;
    }
}

