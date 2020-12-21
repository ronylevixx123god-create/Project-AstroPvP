/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.Easter;
import org.arkhamnetwork.Arkkit.patches.EasterEggHunt;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class EasterEggHuntCommand
extends AbstractCommand {
    public EasterEggHuntCommand() {
        super("egghunt", "/<command>", "Easter egg ");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 1 && (args[0].equalsIgnoreCase("summon") || args[0].equalsIgnoreCase("drop") || args[0].equalsIgnoreCase("start"))) {
            EasterEggHunt task = ((Easter)ArkPlugins.EASTER.getArkPlugin()).getEggHuntTask();
            if (task != null) {
                task.run();
                sender.sendMessage(CC.Red + "Easter egg task summoned!");
            }
            return true;
        }
        return false;
    }
}

