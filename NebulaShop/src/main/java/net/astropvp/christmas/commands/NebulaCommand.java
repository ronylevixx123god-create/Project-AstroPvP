package net.astropvp.christmas.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import me.matthawkk.chat.messages.PrefixedMessage;
import net.astropvp.christmas.NebulaShop;
import net.astropvp.christmas.inventories.NebulaShopInventory;
import org.bukkit.Bukkit;
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
            player.sendMessage(CC.translate("&a&l(!)&a Teleporting to Nebula Lounge..."));
            return true;
        }
        if(args[0].equalsIgnoreCase("setitem") && args.length == 2) {
            NebulaShop.getInstance().getNebulaShopManager().setCurrentItem(player.getItemInHand());
            NebulaShop.getInstance().getNebulaShopManager().setCurrentPrice(Double.parseDouble(args[1]));
            NebulaShop.getInstance().getNebulaShopManager().clearAlreadyBought();
            player.sendMessage(new PrefixedMessage(NebulaShop.getInstance(), "Success.").get());
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.LightPurpleB + CC.Underline + "Nebula Shop"));
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.Gray + "The " + CC.LightPurple + "Nebula Shop " + CC.Gray + "has been " + CC.LightPurple + "restocked"));
            Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.Gray + "with " + player.getItemInHand().getItemMeta().getDisplayName()) + CC.Gray + " for " + CC.LightPurple + "$ " +NumberUtils.formatMoney(Double.parseDouble(args[1])));
            Bukkit.broadcastMessage("");
            return true;
        }
        if(args[0].equalsIgnoreCase("setspawn")) {
            NebulaShop.getInstance().getNebulaShopManager().setShopLocation(player.getLocation());
            player.sendMessage(new PrefixedMessage(NebulaShop.getInstance(), "Success.").get());
            return true;
        }

        if (args[0].equalsIgnoreCase("shop")) {
            new NebulaShopInventory().open(player);
            player.sendMessage(new PrefixedMessage(NebulaShop.getInstance(), "Opening Nebula Shop.").get());
            return true;
        }



        return false;
    }
}
