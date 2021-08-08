package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.items.pearls.EscapistPearlItem;
import net.astropvp.core.items.pearls.OmniPearlItem;
import net.astropvp.core.items.pearls.SuperPearlItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GivePearlCommand implements CommandExecutor {
    String error = CC.Red + "Correct usage: /givepearl <player> <type>";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if(!player.isOp())
            return true;
        if(args.length != 2) {
            player.sendMessage(error);
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(args[1].equals("escapist"))
            target.getInventory().addItem(EscapistPearlItem.getItem());
        switch (args[1]) {
            case "escapist":
                target.getInventory().addItem(EscapistPearlItem.getItem());
                break;
            case "super":
                target.getInventory().addItem(SuperPearlItem.getItem());
                break;
            case "omni":
                target.getInventory().addItem(OmniPearlItem.getItem());
                break;
        }
        return false;
    }
}
