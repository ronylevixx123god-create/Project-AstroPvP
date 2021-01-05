/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.help.commands;

import me.ifamasssxd.help.gui.HCFSpawnGUI;
import net.astropvp.core.AstroCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawnMenu
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (!AstroCore.getInstance().getHelpCommand().isHardCoreFactions()) {
            return true;
        }
        Player player = (Player)sender;
        new HCFSpawnGUI(player).openGUI(AstroCore.getInstance());
        return false;
    }
}

