/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.DedicatedPlayerList
 *  net.minecraft.server.v1_7_R4.JsonListEntry
 *  net.minecraft.util.com.mojang.authlib.GameProfile
 *  org.bukkit.Bukkit
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_7_R4.CraftServer
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.IOException;
import java.util.ArrayList;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.DedicatedPlayerList;
import net.minecraft.server.v1_8_R3.JsonListEntry;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;

final class CommandWipeOps
extends AbstractCommand {
    public CommandWipeOps(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("/wipeops true");
            return true;
        }
        CraftServer CS = (CraftServer)Bukkit.getServer();
        DedicatedPlayerList DPL = CS.getHandle();
        int x = 0;
        for (JsonListEntry entry : DPL.getOPs().getValues()) {
            OfflinePlayer player = CS.getOfflinePlayer((GameProfile)entry.getKey());
            DPL.getOPs().getValues().remove(entry);
            sender.sendMessage("* Removed " + player.getName() + " (" + player.getUniqueId().toString() + ") from ops.json");
            ++x;
        }
        try {
            DPL.getOPs().save();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        sender.sendMessage("*** " + x + " players cleared from local ops.json list.");
        return true;
    }
}

