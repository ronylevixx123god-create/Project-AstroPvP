package net.astropvp.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import com.massivecraft.factions.util.TimeUtil;
import net.astropvp.Carnage;
import net.astropvp.inventories.CarnageInventory;
import org.bukkit.Bukkit;
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
        if (sender.isOp() && args.length == 0) {
            player.openInventory(CarnageInventory.get());
            return true;
        }
        if(!sender.isOp() || args.length == 0) {
            if(!Carnage.getInstance().isCarnageEnabled()) {
                player.sendMessage(CC.translate("&c&l(!)&c Carnage mode is currently not enabled."));
                return true;

            }
            if(Carnage.getInstance().getCarnageManager().isPlayerOnCooldown(player) && !player.isOp()) {
                sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You are still on cooldown for " + TimeUtil.formatSeconds(Carnage.getInstance().getCarnageManager().getPlayerCooldown(player)) + ".");
                return true;
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
                player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have successfully removed " + player.getItemInHand().getItemMeta().getDisplayName() + CC.Green + " to the carnage loot.");
                return true;
            } else {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "Item not found in carnage loot.");
                return true;
            }
        } if(args[0].equalsIgnoreCase("toggle")) {
            if(Carnage.getInstance().isCarnageEnabled()) {
                Carnage.getInstance().setCarnageEnabled(false);
                Bukkit.broadcastMessage(StringUtils.getCenteredMessage(""));
                Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.translate("&4&lCARNAGE MODE &8- &c&lDISABLED")));
                Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.DarkGray + "You can no longer use /carnage."));

            } else {
                Carnage.getInstance().setCarnageEnabled(true);
                Bukkit.broadcastMessage(StringUtils.getCenteredMessage(""));
                Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.translate("&4&lCARNAGE MODE &8- &a&lENABLED")));
                Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.DarkGray + "Use /carnage to access."));

            }
            Bukkit.broadcastMessage(StringUtils.getCenteredMessage(""));

        }
        return false;
    }
}
