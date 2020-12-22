/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import org.arkhamnetwork.Arkkit.libs.ArkPluginCommand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

final class TrickOrTreaterCommand
extends ArkPluginCommand<TrickOrTreaters> {
    public TrickOrTreaterCommand(TrickOrTreaters patch, String command, String usage, String description) {
        super(patch, command, usage, description, (List<String>) Lists.newArrayList(new String[]{"trickortreat"}));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player)sender;
        ((TrickOrTreaters)this.patch).spawnTrickOrTreater(p.getTargetBlock(Collections.singleton(Material.AIR), 4).getLocation().add(0.0, 1.0, 0.0));
        return true;
    }
}

