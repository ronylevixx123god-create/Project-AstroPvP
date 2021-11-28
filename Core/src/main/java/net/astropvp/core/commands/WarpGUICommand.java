package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.guis.WarpGUI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpGUICommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) return false;
        Player p = ((Player) commandSender).getPlayer();
        WarpGUI warpGUI = new WarpGUI(p);
        warpGUI.getLootGUI().open(p);
        p.sendMessage(CC.translate("&aOpening the Warp GUI..."));
        p.playSound(p.getLocation(), Sound.CHEST_OPEN, 2, 2);

        return true;
    }
}
