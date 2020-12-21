/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.arkhamnetwork.Arkkit.patches.FinalBattle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class FinalBattleCommand
extends AbstractCommand {
    public FinalBattleCommand(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("/finalbattle togglespawn <preston/woofless>");
            return true;
        }
        if (args[1].equals("togglespawn")) {
            String targ = args[2];
            if (targ.equalsIgnoreCase("preston")) {
                FinalBattle.prestonRespawn = !FinalBattle.prestonRespawn;
                sender.sendMessage("prestonRespawn => " + FinalBattle.prestonRespawn);
            }
            if (targ.equalsIgnoreCase("woofless")) {
                FinalBattle.wooflessRespawn = !FinalBattle.wooflessRespawn;
                sender.sendMessage("wooflessRespawn => " + FinalBattle.wooflessRespawn);
            }
            return true;
        }
        if (args[1].equals("spawn")) {
            String targ = args[2];
            Player p = (Player)sender;
            if (targ.equalsIgnoreCase("preston")) {
                p.teleport(new Location(Bukkit.getWorld((String)"world_final_battle"), -4.0, 126.0, -143.0));
            } else if (targ.equalsIgnoreCase("woofless")) {
                p.teleport(new Location(Bukkit.getWorld((String)"world_final_battle"), -9.0, 126.0, 75.0));
            }
            return true;
        }
        return true;
    }
}

