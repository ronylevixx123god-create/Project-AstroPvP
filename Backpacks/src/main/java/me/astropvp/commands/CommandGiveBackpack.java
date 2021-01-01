package me.astropvp.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.objects.BackpackType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGiveBackpack implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if(!(player.isOp()))
            return true;
        if(args.length  != 2) {
            player.sendMessage(CC.Red + "Correct Usage: /givebackpack <type> <player>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[1]);
        BackpackType type = BackpackType.valueOf(args[0]);
        target.getInventory().addItem(type.getInstance().getItem());
        target.sendMessage(CC.Green + "Given " + type.getInstance().getItem().getItemMeta().getDisplayName() + " to " + target.getDisplayName());
        return false;
    }
}
