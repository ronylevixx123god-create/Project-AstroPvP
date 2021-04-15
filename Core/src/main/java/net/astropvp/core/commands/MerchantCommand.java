package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.guis.MerchantGUI;
import net.astropvp.core.guis.SpawnerShopGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MerchantCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player) sender;

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = ((Player) sender).getPlayer();

        MerchantGUI merchantGUI = new MerchantGUI(player);
        merchantGUI.open();
        player.sendMessage(CC.translate("&aOpening the Scratch Card Merchant gui..."));


        return false;
    }


}
