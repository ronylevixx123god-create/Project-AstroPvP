package net.astropvp.core.commands;

import net.astropvp.core.utils.ChatManager;
import net.astropvp.core.utils.InvseeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Invsee implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage(ChatManager.translate("&cInvalid Arguments: /invsee <Player>"));
            return false;
        } else {
            try {
                Player target = Bukkit.getPlayer(args[0]);
                if (target.getDisplayName() == null) {
                    player.sendMessage(ChatManager.translate("&cInvalid player."));
                    return false;

                }

            } catch (NullPointerException e) {
                player.sendMessage(ChatManager.translate("&cInvalid player."));
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            InvseeManager inventoryManager = new InvseeManager(player, target);
            player.openInventory(inventoryManager.getInventory());
        }

        return false;
    }
}
