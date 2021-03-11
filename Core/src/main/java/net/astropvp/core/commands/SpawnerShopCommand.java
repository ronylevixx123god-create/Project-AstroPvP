package net.astropvp.core.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.guis.ArmorColorGUI;
import net.astropvp.core.guis.SpawnerShopGUI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnerShopCommand
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player) sender;

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = ((Player) sender).getPlayer();

        SpawnerShopGUI spawnerShopGUI = new SpawnerShopGUI(player);
        spawnerShopGUI.open();
        player.sendMessage(CC.translate("&aOpening the spawner shop gui..."));


        return false;
    }


}
