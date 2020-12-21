/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.AntiSpamBot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

final class VipOnlyCommand
extends AbstractCommand {
    public VipOnlyCommand(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        AntiSpamBot.vipOnly = !AntiSpamBot.vipOnly;
        sender.sendMessage("vipOnlyChat = " + AntiSpamBot.vipOnly);
        return true;
    }
}

