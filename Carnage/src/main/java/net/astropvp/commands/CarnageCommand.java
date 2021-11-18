package net.astropvp.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import net.astropvp.Carnage;
import net.astropvp.inventories.CarnageInventory;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CarnageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if(!sender.isOp() || args.length == 0) {
            if(Carnage.getInstance().getCarnageManager().isPlayerOnCooldown(player)) {
                sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You are still on cooldown for " + NumberUtils.formatSeconds(Carnage.getInstance().getCarnageManager().getPlayerCooldown(player)) + ".");
            } else {
                player.openInventory(CarnageInventory.get());
                Carnage.getInstance().getCarnageManager().registerCooldown(player);
            }
            return true;
        } if(args[0].equalsIgnoreCase("additem")) {
            if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must hold an item to do this.");
                return true;
            }
            Carnage.getInstance().getCarnageManager().addCarnageItem(player.getItemInHand());
            player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have successfully added " + player.getItemInHand().getItemMeta().getDisplayName() + CC.Green + " to the carnage loot.");
            return true;
        } if(args[0].equalsIgnoreCase("removeitem")) {
            if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must hold an item to do this.");
                return true;
            }
            if(Carnage.getInstance().getCarnageManager().removeCarnageItem(player.getItemInHand())) {
                player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have successfully added " + player.getItemInHand().getItemMeta().getDisplayName() + CC.Green + " to the carnage loot.");
                return true;
            } else {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "Item not found in carnage loot.");
                return true;
            }
        }
        return false;
    }
}
