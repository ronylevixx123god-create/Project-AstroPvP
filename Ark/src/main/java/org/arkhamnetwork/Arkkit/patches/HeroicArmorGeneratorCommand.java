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
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

final class HeroicArmorGeneratorCommand
        extends ArkPluginCommand<EquipmentLootboxes> {
    public HeroicArmorGeneratorCommand(EquipmentLootboxes patch) {
        super(patch, "heroicarmorgenerator");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 1) {
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(CC.Red + "Invalid player entered!");
                return true;
            }
            player.sendMessage(CC.GoldB + "(!) " + CC.Gold + "You have been given a " + CC.RedB + "Heroic G-Kit Generator" + CC.Gold + "!");
            PlayerUtil.giveOrDropProtectedItem(player, this.patch.createHeroicGKitGenerator(true), -1);
            sender.sendMessage(CC.Red + "Given heroic generator to " + player.getName() + "!");
            return true;
        }
        sender.sendMessage(CC.Red + "Usage: /heroicarmorgenerator <player>");
        return false;
    }
}

