package net.astropvp.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.NebulaShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NebulaCommand implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player))
            return true;
        Player player = (Player) commandSender;
        if(!player.hasPermission("astrocore.nebula")) {
            player.sendMessage("");
            player.sendMessage(CC.translate("&c&l(!)&c You cannot use the \"/nebula\" command."));
            player.sendMessage(CC.translate("&7Purchase the nebula rank on &f&nbuy.astropvp.net&7!"));
            player.sendMessage("");
            return true;
        }
        if(!player.isOp() || args.length == 0) {
            player.teleport(NebulaShop.getInstance().getNebulaShopManager().getShopLocation());
            return true;
        }
        if(args[0].equalsIgnoreCase("setitem") && args.length == 2) {
            NebulaShop.getInstance().getNebulaShopManager().setCurrentItem(player.getItemInHand());
            NebulaShop.getInstance().getNebulaShopManager().setCurrentPrice(Double.parseDouble(args[1]));
            NebulaShop.getInstance().getNebulaShopManager().clearAlreadyBought();
            player.sendMessage(CC.Green + "Success!");
            return true;
        }
        if(args[0].equalsIgnoreCase("setspawn")) {
            NebulaShop.getInstance().getNebulaShopManager().setShopLocation(player.getLocation());
            player.sendMessage("Success!");
            return true;
        }



        return false;
    }
}
