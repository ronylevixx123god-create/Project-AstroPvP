/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmicduel.commands;

import me.ifamasssxd.cosmicduel.struct.DuelPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandResetELO
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        int amount = 0;
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            DuelPlayer duelPl = DuelPlayer.getDuelPlayer(pl);
            if (duelPl == null) continue;
            ++amount;
            duelPl.setElo(1000.0);
        }
        sender.sendMessage("Reset " + amount + " DuelPlayer elo..");
        return false;
    }
}

