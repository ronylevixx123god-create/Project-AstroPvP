package me.astropvp.hotfixplugin.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.Arkkit.patches.CombatLog;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FixHealth implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp()) return false;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getMaxHealth() < 44) continue;
            if (CombatLog.inCombat(p) ) continue;
            p.setMaxHealth(20);
            if (commandSender instanceof Player) {
                commandSender.sendMessage(CC.Green + "Fix health for people not in combat");
            }
            System.out.println("Set the max health for players");
        }
        return true;
    }
}
