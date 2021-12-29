package commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import org.arkhamnetwork.Arkkit.patches.combatlog.CombatLog;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import struct.Language;

public class GambleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = ((Player) sender).getPlayer();
        if (CombatLog.inCombat(player)) {
            player.sendMessage(Language.COMMAND_IN_COMBAT.getMessage());
            return false;
        }



        return true;
    }
}

