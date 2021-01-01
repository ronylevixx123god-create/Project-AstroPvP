package org.arkhamnetwork.enchantments.cmds;

import org.bukkit.command.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.libs.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;

public class CommandEnchOrb implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 4) {
            sender.sendMessage("/enchorb <type> <successRate> <maxSlots> <player>");
            sender.sendMessage("i.e.) /enchorb ARMOR 90 10 Vaquxine");
            return true;
        }
        final String p_name = args[3];
        if (Bukkit.getPlayer(p_name) != null) {
            final Player pl = Bukkit.getPlayer(p_name);
            final ItemStack i = EnchantmentOrb.getEnchantmentOrb(Integer.parseInt(args[1]), Integer.parseInt(args[2]), OrbType.valueOf(args[0].toUpperCase()));
            if (pl.getInventory().firstEmpty() != -1) {
                pl.getInventory().addItem(new ItemStack[] { i });
            }
            else {
                pl.getWorld().dropItem(pl.getLocation(), i);
            }
        }
        return true;
    }
}
