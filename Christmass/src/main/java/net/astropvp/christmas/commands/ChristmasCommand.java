package net.astropvp.christmas.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.StringUtils;
import me.matthawkk.chat.messages.PrefixedMessage;
import net.astropvp.christmas.Christmas;
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
        if(!player.isOp())
            return true;
        if(args[0].equalsIgnoreCase("setitem") && args.length == 2) {
            Christmas.getInstance().getChristmasManager().setCurrentItem(player.getItemInHand());
            Christmas.getInstance().getChristmasManager().setCurrentPrice(Double.parseDouble(args[1]));
            Christmas.getInstance().getChristmasManager().clearAlreadyBought();
            player.sendMessage(new PrefixedMessage(Christmas.getInstance(), "Success.").get());
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.Green + CC.Underline + "Christmas Shop"));
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.Gray + "The " + CC.LightPurple + "Christmas Shop " + CC.Gray + "has been " + CC.LightPurple + "restocked"));
            Bukkit.broadcastMessage(StringUtils.getCenteredMessage(CC.Gray + "with " + player.getItemInHand().getItemMeta().getDisplayName()) + CC.Gray + " for " + CC.LightPurple + "$ " +NumberUtils.formatMoney(Double.parseDouble(args[1])));
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
