/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicArmorSets.cmds;

import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CommandMasteryShard
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(CC.Red + "No permission.");
            return true;
        }
        if (args.length >= 3 && args[0].equalsIgnoreCase("give")) {
            Player pl = Bukkit.getPlayer(args[1]);
            if (pl == null) {
                sender.sendMessage(CC.Red + "Invalid online player!");
                return true;
            }
            AbilityArmorSet set = AbilityArmorSet.fromName(args[2]);
            if (set == null) {
                sender.sendMessage(CC.Red + "Invalid Ability Armor set: " + Arrays.toString(AbilityArmorSet.values()));
                return true;
            }
            if (args.length >= 4 && args[3].equalsIgnoreCase("anticrystal")) {
                Integer crystal = args.length >= 5 ? NumberUtils.parseIntOrNull(args[4]) : null;
                PlayerUtil.giveOrDropProtectedItem(pl, set.createAntiMkitShard(crystal == null || crystal <= 0 ? 1 : crystal), -1);
                return true;
            }
            PlayerUtil.giveOrDropProtectedItem(pl, set.createMasteryShard(true), -1);
            return true;
        }
        sender.sendMessage(CC.Red + "/masteryshard give <player> <abilitySet> [anticrystal]");
        return false;
    }
}

