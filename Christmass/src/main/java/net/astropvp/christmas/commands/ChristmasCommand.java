package net.astropvp.christmas.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import me.matthawkk.chat.messages.PrefixedMessage;
import net.astropvp.christmas.Christmas;
import net.astropvp.christmas.inventories.ChristmasInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChristmasCommand implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player))
            return true;
        Player player = (Player) commandSender;
        if(!player.isOp() || args.length == 0) {
            new ChristmasInventory().open(player);
            player.sendMessage(new PrefixedMessage(Christmas.getInstance(), "Opening Christmas Shop.").get());
            return true;
        }
        if(args[0].equalsIgnoreCase("setitem") && args.length == 1) {
            Christmas.getInstance().getChristmasManager().setCurrentItem(player.getItemInHand());
            Christmas.getInstance().getChristmasManager().clearAlreadyBought();
            player.sendMessage(new PrefixedMessage(Christmas.getInstance(), "Success.").get());
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.DarkGreenB + CC.Underline + "Christmas Shop"));
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.Gray + "The " + CC.DarkGreen + "Christmas Shop " + CC.Gray + "has been " + CC.DarkGreen + "restocked"));
            Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.Gray + "with " + player.getItemInHand().getItemMeta().getDisplayName()) + CC.Gray + " for " + CC.DarkGreen + "NO COST");
            Bukkit.broadcastMessage("");
            return true;
        }
        if(args[0].equalsIgnoreCase("setspawn")) {
            Christmas.getInstance().getChristmasManager().setShopLocation(player.getLocation());
            player.sendMessage(new PrefixedMessage(Christmas.getInstance(), "Success.").get());
            return true;
        }



        return false;
    }
}
