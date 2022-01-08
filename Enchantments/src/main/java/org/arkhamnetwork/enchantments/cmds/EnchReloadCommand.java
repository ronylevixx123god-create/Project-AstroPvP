package org.arkhamnetwork.enchantments.cmds;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.enchantments.files.CustomConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnchReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        Player player = (Player) commandSender;
        if (!player.isOp())  {
            player.sendMessage(CC.Red + "No perms.");
            return false;
        }
        CustomConfig.reload();
        player.sendMessage(CC.translate("&a&l(!)&a Reloaded the damage modifiers config!"));


        return true;    }
}
