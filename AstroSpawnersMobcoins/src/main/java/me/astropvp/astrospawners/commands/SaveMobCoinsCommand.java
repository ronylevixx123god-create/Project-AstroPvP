package me.astropvp.astrospawners.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveMobCoinsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!commandSender.isOp()) return false;

        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = AstroSpawners.getInstance().getManager().getPlayerData(p);
            AstroSpawners.getInstance().getManager().savePlayerDataAsync(data);
        }

        Bukkit.broadcastMessage(CC.translate("&a&l* &aSaved all Mobcoins Data to Disk..."));
        return true;



    }
}
