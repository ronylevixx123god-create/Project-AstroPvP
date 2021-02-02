package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GiveApolloCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        if (!(sender.isOp())) {
            sender.sendMessage(CC.translate("&cYou cannot run the command."));
            return false;
        }
        Player p = (Player) sender;

        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CC.translate("&3&l✪ &b&l&o&nA&f&l&o&np&b&l&n&oo&f&l&n&ol&b&l&n&ol&f&l&n&oo&b&l RANK &f&l&oVoucher &3&l✪"));
        List lore = meta.getLore();
        lore.add(CC.translate("&7"));
        lore.add(CC.translate("&7&nRight-Click&7 to unlock"));
        lore.add(CC.translate("&7the &bA&fp&bo&fl&bl&fo &7rank on this server!"));
        lore.add("");
        lore.add("");
        item.setItemMeta(meta);

        p.getPlayer().getInventory().addItem(item);



        return false;
    }

}