package me.astropvp.astrospawners.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.MobCoinUtils;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMobCoins implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player player = (Player)sender;
        PlayerDataManager manager = AstroSpawners.getInstance().getManager();
        PlayerData data = manager.getPlayerData(player);

        if (args.length == 0) {
            player.sendMessage("");
            player.sendMessage(String.valueOf(CC.White + "❀ " + CC.GreenB) + "Mob Coins: " + CC.White + ((data == null) ? 0 : data.getCoins()));
            player.sendMessage(CC.translate("&7You can buy items in the shop using &f/transfer&7."));
            player.sendMessage("");
            return false;
        } else if (args[0].equals("withdraw") || args[0].equals("w")) {
            if (args.length == 1) {
                player.sendMessage(CC.translate("&cUsage: /mobcoins withdraw (amount)"));
                return false;
            }
            int amount = Integer.valueOf(args[1]);
            if (amount <=0) {
                player.sendMessage(CC.translate("&cInvalid number."));
                return false;
            }
            if (amount > data.getCoins()) {
                player.sendMessage(CC.translate("&c&l(!)&c You do not have enough mobcoins to withdraw this amount"));
                return false;
            }
            player.getInventory().addItem(MobCoinUtils.createCoinsNote(player.getName(), amount));
            data.setCoins(data.getCoins()-amount);
        }
        return true;
    }
}
