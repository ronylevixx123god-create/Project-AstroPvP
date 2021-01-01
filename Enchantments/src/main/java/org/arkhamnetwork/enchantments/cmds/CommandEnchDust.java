package org.arkhamnetwork.enchantments.cmds;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import java.io.*;
import org.bukkit.*;
import com.cosmicpvp.CosmicSpaceChests.libs.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.inventory.*;

public class CommandEnchDust implements CommandExecutor
{
    public static boolean dustEnabled;
    public static boolean soulEnabled;
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length == 0) {
            sender.sendMessage("/enchdust <player> <rarity> <success> [mystery]");
            return false;
        }
        if (sender instanceof Player && !((Player)sender).isOp()) {
            return true;
        }
        final String p_name = args[0];
        if (args[1].equals("toggle_souls")) {
            if (CommandEnchDust.soulEnabled) {
                CommandEnchDust.soulEnabled = false;
                new File("plugins/Enchantments/soul_enabled").delete();
                sender.sendMessage(ChatColor.RED + "SOUL ENCHANTMENTS DISABLED");
            }
            else {
                CommandEnchDust.soulEnabled = true;
                try {
                    new File("plugins/Enchantments/soul_enabled").createNewFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "SOUL ENCHANTMENTS ENABLED");
            }
            return true;
        }
        if (args[1].equals("toggle_tinkerer")) {
            if (CommandEnchDust.dustEnabled) {
                CommandEnchDust.dustEnabled = false;
                new File("plugins/Enchantments/dust_enabled").delete();
                sender.sendMessage(ChatColor.RED + "DUST DISABLED");
            }
            else {
                CommandEnchDust.dustEnabled = true;
                try {
                    new File("plugins/Enchantments/dust_enabled").createNewFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "DUST ENABLED");
            }
            return true;
        }
        final Player pl = Bukkit.getPlayer(p_name);
        final ItemRarity ir = ItemRarity.valueOf(args[1].toUpperCase());
        final int successAdd = Integer.parseInt(args[2]);
        final boolean mystery = args.length == 4 && Boolean.valueOf(args[3]);
        final ItemStack i = mystery ? EnchantmentBook.getSecretDust(ir, successAdd) : ((args.length >= 3) ? EnchantmentBook.getMagicDust(ir, successAdd) : EnchantmentBook.getPlatinumMagicDust(ir, successAdd));
        if (pl.getInventory().firstEmpty() != -1) {
            pl.getInventory().addItem(new ItemStack[] { i });
        }
        else {
            pl.getWorld().dropItem(pl.getLocation(), i);
        }
        return true;
    }
    
    static {
        CommandEnchDust.dustEnabled = false;
        CommandEnchDust.soulEnabled = false;
    }
}
