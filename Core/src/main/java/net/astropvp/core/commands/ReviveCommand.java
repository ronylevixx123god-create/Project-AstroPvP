package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.data.InventoryData;
import net.astropvp.core.guis.ReviveGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReviveCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player) || !sender.hasPermission("astrostaff.revive"))
            return false;
        if (args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(String.valueOf(CC.RedB) + "(!) " + CC.Red + "That player is not online!");
                return false;
            }
            if (!InventoryData.getData().containsKey(target.getUniqueId())) {
                sender.sendMessage(String.valueOf(CC.RedB) + "(!) " + CC.Red + "That player can not be revived!");
                return false;
            }
            InventoryData[] data = (InventoryData[]) InventoryData.getData().get(target.getUniqueId());
            if (data[0] == null) {
                sender.sendMessage(String.valueOf(CC.RedB) + "(!) " + CC.Red + "That player can not be revived!");
                return false;
            }
            ((Player)sender).openInventory(ReviveGUI.getInventory(target, 0));
            return false;
        }
        sender.sendMessage("");
        sender.sendMessage(String.valueOf(CC.LightPurpleB) + "Astro Revive");
        sender.sendMessage(String.valueOf(CC.Red) + "/revive <user> " + CC.Gray + "- Revives the User");
        sender.sendMessage("");
        return false;
    }
}
