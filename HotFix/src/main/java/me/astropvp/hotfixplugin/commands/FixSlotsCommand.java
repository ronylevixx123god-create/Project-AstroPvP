package me.astropvp.hotfixplugin.commands;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.astropvp.astrospawners.AstroSpawners;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FixSlotsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        Player sender = ((Player) commandSender).getPlayer();

        AstroSpawners.getInstance().getWaitingForAfkGrindingVerification().clear();
        AstroSpawners.getInstance().getAfkGrinders().clear();
        sender.sendMessage(CC.Red + "Cleared");
        return false;
    }
}
