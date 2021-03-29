package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.AstroCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TrayPickaxeCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        ItemStack hoe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = hoe.getItemMeta();
        meta.setDisplayName(
                CC.translate("&c&lTray Pickaxe &7(8x8)"));
        List<String> lores = new ArrayList<>();
        lores.add(CC.translate("&7Use this to mine netherrack quickly."));
        meta.setLore(lores);
        hoe.setItemMeta(meta);

        if (!sender.isOp()) {
            sender.sendMessage(CC.translate("&cNo Perms."));
            return false;
        }

        sender.getServer().getPlayer(sender.getName()).getInventory().addItem(new ItemStack[] { hoe });

        return true;
    }
}
