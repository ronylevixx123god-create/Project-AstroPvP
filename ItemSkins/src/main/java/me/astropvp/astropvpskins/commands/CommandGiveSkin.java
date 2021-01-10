/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package me.astropvp.astropvpskins.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandGiveSkin
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!sender.hasPermission("skin.give")) {
            return false;
        }
        if (args.length < 3) {
            sender.sendMessage("/giveskin <type of skin> <amount> <player>");
            SkinType[] arrayOfSkinType = SkinType.values();
            int i = arrayOfSkinType.length;
            for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
                SkinType skinType1 = arrayOfSkinType[b];
                sender.sendMessage(String.valueOf(skinType1.name()) + CC.WhiteB + " - " + skinType1.getDisplayName());
            }
            return false;
        }
        String type = args[0];
        int amount = Integer.parseInt(args[1]);
        Player player = Bukkit.getPlayer((String)args[2]);
        SkinType skinType = SkinType.valueOf(type);
        ItemStack item = skinType.getItem();
        if (Utils.isInventoryFull(player)) {
            player.getWorld().dropItem(player.getLocation(), item);
        } else {
            player.getInventory().addItem(new ItemStack[]{item});
        }
        player.sendMessage(CC.Green + "Added item " + amount + "x " + skinType.getDisplayName() + CC.Green + " to " + player.getName() + "'s Inventory.");
        return false;
    }
}

