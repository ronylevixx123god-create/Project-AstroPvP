/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.astropvp.autogiveall.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.autogiveall.struct.GiveAllManager;
import net.astropvp.core.conaxgames.giveall.ManualGiveAll;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandClaim
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;

        if (ManualGiveAll.claim.containsKey(player)) {
            if (getEmptyInvSlots(player) >= ManualGiveAll.claim.get(player).size()) {
                for (ItemStack i : ManualGiveAll.claim.get(player)) {
                    player.getInventory().addItem(i);
                    if (i.hasItemMeta() && i.getItemMeta().hasDisplayName()) {
                        player.sendMessage(CC.Green + "Added " + i.getItemMeta().getDisplayName() + " to your inventory.");
                    } else {
                        player.sendMessage(CC.Green + "Added " + i.getType() + " to your inventory.");
                    }
                    ManualGiveAll.claim.get(player).remove(i);
                }
            } else {
                player.sendMessage(CC.Red + "You do not have enough inventory space to claim.");
                return true;
            }
        }

        if (!GiveAllManager.hasAvailableLootboxes(player)) {
            player.sendMessage(CC.Red + "You do not have any available lootboxes.");
            return true;
        }
        int amount = GiveAllManager.getAvailableLootboxes(player);
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(CC.Red + "You do not have enough inventory space to claim your lootboxes.");
            return true;
        }
        ItemStack item = GiveAllManager.getGiveAllItem();
        item.setAmount(amount);
        player.getInventory().addItem(new ItemStack[]{item});
        player.updateInventory();
        player.sendMessage(CC.Green + "You have been given your lootboxes.");
        GiveAllManager.setAvailableLootboxes(player, 0);
        return false;
    }

    public static int getEmptyInvSlots(Player player) {
        int count = 0;
        for (ItemStack i : player.getInventory()) {
            if (i == null) {
                count++;
            }
        }
        return count;
    }
}

