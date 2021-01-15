package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CommandDupe implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.DarkRed + "You have to be a player to use this command...");
            return false;
        }
        Player player = (Player) sender;
        ItemStack item = player.getItemInHand();
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !item.getItemMeta().hasLore()) {
            sender.sendMessage(CC.Red + "You cannot dupe this item!");
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        String display = itemMeta.getDisplayName();
        String color = display.substring(0, 2);
        List<String> lore = itemMeta.getLore();
        boolean isOpened = lore.contains(CC.Gray + "Drag n' Drop onto item to enchant.");
        if (!isOpened) {
            if (!display.contains(" ")) {
                sender.sendMessage("§cYou may not dupe this item.");
                return false;
            }
            display = display.substring(display.indexOf(" ") + 1);
        }
        if ((!display.startsWith("Enchantment Book") && !isOpened) || color.contains(CC.DarkRed)) {
            sender.sendMessage(CC.Red + "You cannot dupe this item.");
            return false;
        }
        item.setAmount(64);
        player.sendMessage(CC.Green + "You have successfully duplicated your item.");
        return false;
    }
}
