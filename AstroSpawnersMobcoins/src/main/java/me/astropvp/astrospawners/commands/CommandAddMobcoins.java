package me.astropvp.astrospawners.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAddMobcoins implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player)sender;
        if (!(player.isOp()))
            return false;
        PlayerDataManager manager = AstroSpawners.getInstance().getManager();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&c/addmobcoins <player> <amount>"));
            return false;
        }

        if (args.length != 2) {
            player.sendMessage(CC.translate("&c/addmobcoins <player> <amount>"));
            return false;
        }

        Player receiver = Bukkit.getPlayer(args[0]);
        int amount = Integer.valueOf(args[1]);
        PlayerData data = manager.getPlayerData(receiver);

        if (amount < 0) {
            player.sendMessage(CC.translate("Amount must me > 0"));
            return false;
        }

        data.setCoins(data.getCoins()+amount);
        player.sendMessage(CC.translate("&aAdded " + receiver.getName() + " mobcoins to " + data.getCoins()));
        receiver.sendMessage(CC.translate("&a" + amount + " Mobcoins have been added to your balance!"));


        return true;
    }
}
