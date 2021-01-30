/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 */
package me.ifamasssxd.cosmicheroicenvoys.commands;

import net.astropvp.core.AstroCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandSpawnHeroicBoss
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        //AstroCore.getInstance().getEnvoyManager().spawnBoss();
        //AstroCore.getInstance().getEnvoyManager().startEnvoyTimer();
        return false;
    }
}

