package me.astropvp.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.enums.Language;
import me.astropvp.utils.Base64Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public class CommandAdminTournament implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return true;
        Player player = (Player) sender;
        if(!player.isOp())
            return true;
        if (CosmicTournamentsPlugin.getInstance().getActiveTournament() == null) {
            CosmicTournamentsPlugin.getInstance().createTournament();
            Bukkit.broadcastMessage(Language.CREATE_TOURNAMENT.getMessage());
        }


        String[] string = Base64Utils.playerInventoryToBase64(player.getInventory());
        Bukkit.broadcastMessage(string[0]);
        Bukkit.broadcastMessage(CC.Red + string[1]);
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
        Bukkit.getScheduler().runTaskLater(CosmicTournamentsPlugin.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    player.getInventory().setContents(Base64Utils.itemStackArrayFromBase64(string[0]));
                    player.getInventory().setArmorContents(Base64Utils.itemStackArrayFromBase64(string[1]));
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }, 100L);

        return false;
    }
}
