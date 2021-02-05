package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GiveApolloCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender.isOp() || sender instanceof ConsoleCommandSender)) {
            return false;
        }

        if (args.length != 1) {
            return false;
        }

        Player receiver = Bukkit.getServer().getPlayer(args[0]);
        if (receiver != null) {

            ItemStack item = new ItemStack(Material.NETHER_STAR);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(CC.translate("&3&l✪ &b&l&o&nA&f&l&o&np&b&l&n&oo&f&l&n&ol&b&l&n&ol&f&l&n&oo&b&l RANK &f&l&oVoucher &3&l✪ &7(Right Click)"));
            ArrayList<String> lore = new ArrayList<String>();
            lore.add(CC.translate("&7"));
            lore.add(CC.translate("&7&nRight-Click&7 to unlock"));
            lore.add(CC.translate("&7the &bA&fp&bo&fl&bl&fo &7rank on this server!"));
            lore.add("");
            lore.add(CC.translate("&b&lApollo Perks"));
            lore.add(CC.translate("&b&l* &3Apollo &f/kit"));
            lore.add(CC.translate("&b&l* &3Unlimited playervaults"));
            lore.add(CC.translate("&b&l* &3Unlimited homes"));
            lore.add(CC.translate("&b&l* &3Aqua global chat color"));
            lore.add(CC.translate("&b&l* &320 AH listings"));
            lore.add(CC.translate("&b&l* &3No &f/cf &3tax"));
            lore.add(CC.translate("&b&l* &3Priority discord support"));
            lore.add(CC.translate("&b&l* &3Apollo prefix"));
            lore.add(CC.translate("&b&l* &3and much more..."));
            lore.add("");
            lore.add(CC.translate("&8[&3✬&8] &bobtained from &f&nbuy.astropvp.net&7 (&n/buy&7)"));
            meta.setLore(lore);
            item.setItemMeta(meta);

            receiver.getPlayer().getInventory().addItem(new ItemStack(item));
            receiver.sendMessage(CC.translate("&a&l(!)&a Received the Apollo Rank!"));

            return false;
        }
        return false;
    }

    public static boolean isApolloRank(ItemStack item) {
        if (item == null) {
            return false;
        }
        if (item.getType() == Material.AIR) {
            return false;
        }
        if (!(item.hasItemMeta())) {
            return false;
        }
        if (!(item.getItemMeta().hasLore())) {
            return false;
        }
        return item.getItemMeta().getLore().contains(CC.translate("&8[&3✬&8] &bobtained from &f&nbuy.astropvp.net&7 (&n/buy&7)")) && item.getType() == Material.NETHER_STAR;
    }

}