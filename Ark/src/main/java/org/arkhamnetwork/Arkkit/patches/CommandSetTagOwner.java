/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.UUID;

final class CommandSetTagOwner
        extends AbstractCommand {
    public CommandSetTagOwner(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/settagowner <factionTag/list/clear> <ownerUUID/none>");
            return true;
        }
        String tag = args[0];
        String ownerUUID = args[1];
        if (tag.equalsIgnoreCase("list")) {
            for (Map.Entry<String, UUID> data : FactionTagReserve.reservedTags.entrySet()) {
                sender.sendMessage("tag: " + data.getKey() + " is reserved for: " + data.getValue());
            }
            return true;
        }
        if (tag.equalsIgnoreCase("clear")) {
            sender.sendMessage("Cleared " + FactionTagReserve.reservedTags.size() + " local /f tag reservations.");
            FactionTagReserve.reservedTags.clear();
            return true;
        }
        if (ownerUUID.equalsIgnoreCase("all") || ownerUUID.equalsIgnoreCase("none")) {
            FactionTagReserve.reservedTags.remove(tag.toLowerCase());
            sender.sendMessage("The faction tag '" + tag + "' is no longer reserved.");
        } else {
            FactionTagReserve.reservedTags.put(tag.toLowerCase(), UUID.fromString(ownerUUID));
            sender.sendMessage("The faction tag '" + tag + "' is now reserved for UUID: " + ownerUUID);
        }
        return true;
    }
}

