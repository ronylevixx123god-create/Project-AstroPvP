package me.bryside.weaponrunes.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.InventoryUtils;
import com.google.common.collect.Lists;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandWeaponBonus implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        int inventorySize = 9;

        Player p = ((Player) commandSender).getPlayer();

        Inventory inventory = Bukkit.createInventory(null, 9, CC.translate("&8Weapon Bonuses"));

        int slot = 0;
        for (WeaponBonusType weaponBonusType : WeaponBonusType.values()) {
            inventory.addItem(new ItemStack(weaponBonusType.getInstance().getItem(100)));
            slot++;
        }

        ItemStack helpBook = new ItemStack(Material.BOOK);
        ItemMeta meta = helpBook.getItemMeta();

        meta.setDisplayName(CC.translate("&b&lWeapon Bonuses"));
        meta.setLore(Lists.newArrayList(
                CC.translate("&7Weapon Bonuses are orbs which you"), CC.translate("&7apply onto Heroic Swords only and"), CC.translate("&7imbue the weapon with special powers.")));

        helpBook.setItemMeta(meta);

        inventory.setItem(inventorySize-1, helpBook);



        p.sendMessage(CC.translate("&aOpening the Weapon Bonuses Menu..."));
        p.openInventory(inventory);


        return true;
    }
}
