/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicbosses.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import java.util.List;
import me.ifamasssxd.cosmicbosses.struct.RewardInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandBossRewards
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean expired;
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        RewardInventory rewardInventory = RewardInventory.getRewardInventory(player);
        if (rewardInventory == null) {
            player.sendMessage((Object)ChatColor.RED + "You do not have items in your claim.");
            return true;
        }
        boolean bl = expired = rewardInventory.getExpirationTime() > 0L && rewardInventory.getExpirationTime() < System.currentTimeMillis();
        if (expired) {
            Bukkit.getLogger().info("Expiring previous collect items for " + player.getName() + "!");
            RewardInventory.removeRewardInventory(player);
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You had expired items inside your /collect!");
            sender.sendMessage(CC.Gray + "Items can only be stored for 24h.");
            return true;
        }
        int space = this.getEmptySpace(player);
        List<ItemStack> items = rewardInventory.getItems();
        if (items.size() > player.getInventory().getSize()) {
            for (int i = 0; i < 9 && !items.isEmpty(); ++i) {
                ItemStack item = items.get(0);
                PlayerUtil.giveOrDropProtectedItem((Player)player, (ItemStack)item, (int)-1);
                items.remove(0);
            }
            player.sendMessage(CC.RedB + "You had > 54 items in your /collect, 9 have been given to you or dropped at your feet to make room.");
            player.sendMessage(CC.Gray + "Please re-run /collect to receive additional items.");
            return true;
        }
        if (space < rewardInventory.getItems().size()) {
            player.sendMessage((Object)ChatColor.RED + "You must have " + (Object)ChatColor.RED + ChatColor.UNDERLINE.toString() + rewardInventory.getItems().size() + (Object)ChatColor.RED + " empty inventory slots to claim these items.");
            return true;
        }
        for (ItemStack is : rewardInventory.getItems()) {
            player.getInventory().addItem(new ItemStack[]{is});
        }
        RewardInventory.removeRewardInventory(player);
        player.updateInventory();
        player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0f, 1.4f);
        player.sendMessage(CC.GreenB + "(!) " + CC.Green + "Rewards added to your inventory!");
        return false;
    }

    private int getEmptySpace(Player pl) {
        int space = 0;
        for (ItemStack is : pl.getInventory()) {
            if (is != null && is.getType() != Material.AIR) continue;
            ++space;
        }
        return space;
    }
}

