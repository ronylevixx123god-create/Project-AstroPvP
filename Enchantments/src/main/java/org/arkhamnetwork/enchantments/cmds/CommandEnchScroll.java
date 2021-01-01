package org.arkhamnetwork.enchantments.cmds;

import org.bukkit.command.*;
import org.bukkit.*;
import com.cosmicpvp.CosmicSpaceChests.libs.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;

public class CommandEnchScroll implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage("/enchscroll <type> <player> (rarity)");
            sender.sendMessage("i.e.) /enchorb randomization Vaquxine LEGENDARY");
            return true;
        }
        final String p_name = args[1];
        if (Bukkit.getPlayer(p_name) != null) {
            final Player pl = Bukkit.getPlayer(p_name);
            ItemStack i = null;
            if (args[0].equalsIgnoreCase("randomization")) {
                i = RandomizationScroll.getRandomizationScroll(ItemRarity.valueOf(args[2]));
            }
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
