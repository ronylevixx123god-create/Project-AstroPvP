package me.astropvp.moneyshop.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.moneyshop.MoneyShop;
import me.astropvp.moneyshop.guis.MoneyShopGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            MoneyShopGUI moneyShopGUI = new MoneyShopGUI(((Player) sender).getPlayer()) ;
            moneyShopGUI.getPaginatedGui().open(((Player) sender).getPlayer());
            sender.sendMessage(CC.translate("&a&l(!)&a Opening the moneyshop gui."));
            return true;
        }
        return false;
    }
}
