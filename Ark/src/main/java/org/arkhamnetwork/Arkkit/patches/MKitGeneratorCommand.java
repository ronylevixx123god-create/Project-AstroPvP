/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import org.arkhamnetwork.Arkkit.libs.ArkPluginCommand;
import org.arkhamnetwork.Arkkit.patches.EquipmentLootboxes;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

final class MKitGeneratorCommand
extends ArkPluginCommand<EquipmentLootboxes> {
    public MKitGeneratorCommand(EquipmentLootboxes patch) {
        super(patch, "mkitgenerator");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 1) {
            Player player = Bukkit.getPlayer((String)args[0]);
            if (player == null) {
                sender.sendMessage(CC.Red + "Invalid player entered!");
                return true;
            }
            player.sendMessage(CC.GoldB + "(!) " + CC.Gold + "You have been given a " + CC.DarkRedB + "M-Kit Generator" + CC.Gold + "!");
            PlayerUtil.giveOrDropProtectedItem((Player)player, (ItemStack)((EquipmentLootboxes)this.patch).createMKitGenerator(true), (int)-1);
            sender.sendMessage(CC.Red + "Given mkit generator to " + player.getName() + "!");
            return true;
        }
        sender.sendMessage(CC.Red + "Usage: /mkitgenerator <player>");
        return false;
    }
}

