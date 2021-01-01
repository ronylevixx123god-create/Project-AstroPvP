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
 *  org.spigotmc.SpigotConfig
 */
package com.compilex.CosmicArmorSets.cmds;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.SpigotConfig;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class CommandHeroicUpgrade
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(CC.Red + "No permission.");
            return true;
        }
        if (args.length >= 3) {
            if (args[0].equalsIgnoreCase("give")) {
                Double percent;
                Player player = Bukkit.getPlayer((String) args[1]);
                if (player == null) {
                    sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Invalid player given!");
                    return true;
                }
                ArmorSet set = ArmorSet.fromName(args[2]);
                if (set == null) {
                    sender.sendMessage(CC.Red + "Invalid armor set: " + Arrays.toString((Object[]) ArmorSet.values()));
                    return true;
                }
                Double d = percent = args.length == 4 ? NumberUtils.parseDoubleOrNull((String) args[3]) : null;
                if (percent == null) {
                    // TODO: WHY IS THIS IGNORED?!>!?!?!?!
                    Double.valueOf((ThreadLocalRandom.current().nextInt(51) + 50));
                }

                PlayerUtil.giveOrDropProtectedItem((Player) player, (ItemStack) CosmicArmorSetsAPI.createArmorSetUpgrade(set, percent), (int) -1);
                sender.sendMessage(CC.Red + "Added " + set.name() + " ArmorSet at " + percent + "% to " + player.getName() + "'s Inventory");
                return true;
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("giverandom")) {
            Player player = Bukkit.getPlayer((String) args[1]);
            if (player == null) {
                sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Invalid player given!");
                return true;
            }
            PlayerUtil.giveOrDropProtectedItem((Player) player, (ItemStack) CosmicArmorSetsAPI.createRandomArmorSetUpgrade(), (int) -1);
            sender.sendMessage(CC.Red + "Added Random Heroic Upgrade to " + player.getName() + "!");
            return true;
        }
        sender.sendMessage(CC.Red + "/heroicupgrade give <player> <armorset> [percent]");
        sender.sendMessage(CC.Red + "/heroicupgrade giverandom <player>");
        return false;
    }
}

