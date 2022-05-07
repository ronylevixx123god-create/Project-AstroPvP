package net.astropvp.armorsets.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.armorsets.AstroArmorSets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandAdminArmorSet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp()) return false;
        if(args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            AstroArmorSets.getInstance().getConfigManager().reloadFiles();
            sender.sendMessage(CC.Green + "Reloaded config");
            return true;
        }
        return false;
    }
}
