package me.astropvp.cratekeys.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.cratekeys.AstroCrateKeysAPI;
import me.astropvp.cratekeys.AstroCrateKeysPlugin;
import me.astropvp.cratekeys.enums.CrateType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class CommandAdminCrate implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player) || !sender.isOp())
            return false;
        Player player = (Player)sender;
        if (args.length >= 3 && args[0].equalsIgnoreCase("additem")) {
            double percentage = Double.valueOf(args[1]).doubleValue();
            ItemStack item = player.getItemInHand();
            if (item == null || item.getType() == Material.AIR) {
                player.sendMessage(String.valueOf(CC.RedB) + "(!) " + CC.Red + "You can't add air to a crate!");
                return false;
            }
            CrateType type = CrateType.valueOf(args[2].toUpperCase());
            Map<ItemStack, Double> items = AstroCrateKeysAPI.getItems(type);
            items.put(player.getItemInHand(), Double.valueOf(percentage));
            AstroCrateKeysAPI.setItems(type, items);
            AstroCrateKeysPlugin.cachedCrateLoot.put(type, AstroCrateKeysAPI.getItems(type));
            player.sendMessage(String.valueOf(CC.Green) + "Added your item in hand to the crate '" + type.getName() + "'!");
            return false;
        }
        if (args.length >= 2 && args[0].equalsIgnoreCase("removeitem")) {
            ItemStack item = player.getItemInHand();
            if (item == null || item.getType() == Material.AIR) {
                player.sendMessage(String.valueOf(CC.RedB) + "(!) " + CC.Red + "You can't remove air from a crate!");
                return false;
            }
            CrateType type = CrateType.valueOf(args[1].toUpperCase());
            Map<ItemStack, Double> items = AstroCrateKeysAPI.getItems(type);
            for (Map.Entry<ItemStack, Double> entry : items.entrySet()) {
                if (((ItemStack)entry.getKey()).equals(item)) {
                    item = entry.getKey();
                    break;
                }
            }
            if (!items.containsKey(item)) {
                player.sendMessage(String.valueOf(CC.RedB) + "(!) " + CC.Red + "Could not find item!");
                return false;
            }
            items.remove(item);
            AstroCrateKeysAPI.setItems(type, items);
            AstroCrateKeysPlugin.cachedCrateLoot.put(type, AstroCrateKeysAPI.getItems(type));
            player.sendMessage(String.valueOf(CC.Green) + "Removed your item in hand from the crate '" + args[1] + "'!");
            return false;
        }
        if (args.length >= 2 && args[0].equalsIgnoreCase("viewitems")) {
            CrateType type = CrateType.valueOf(args[1].toUpperCase());
            List<ItemStack> items = AstroCrateKeysAPI.getListItems(type);
            Inventory inventory = Bukkit.createInventory(null, 54, String.valueOf(CC.Gray) + "Items");
            int pos = 0;
            for (ItemStack item : items)
                inventory.setItem(pos++, item);
            player.openInventory(inventory);
            return false;
        }

        player.sendMessage("");
        player.sendMessage(String.valueOf(CC.RedB) + "Admin Crate Commands");
        player.sendMessage(String.valueOf(CC.Red) + "/achestcrate additem <percentage> <rarity>");
        player.sendMessage(String.valueOf(CC.Red) + "/achestcrate removeitem <rarity>");
        player.sendMessage(String.valueOf(CC.Red) + "/achestcrate viewitems <rarity>");
        player.sendMessage("");
        return false;
    }
}
