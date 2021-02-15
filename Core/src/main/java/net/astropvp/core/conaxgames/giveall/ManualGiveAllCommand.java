package net.astropvp.core.conaxgames.giveall;


import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.conaxgames.staff.Freeze;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ManualGiveAllCommand extends Command {

    public ManualGiveAllCommand() {
        super("giveall");
        setAliases(Arrays.asList("mgiveall", "manualgiveall"));
        setUsage(ChatColor.RED + "/giveall");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("Player command.");
            return true;
        }
        Player player = (Player) commandSender;
        if (!player.hasPermission("astrocore.admin.giveall")) {
            player.sendMessage(CC.Red + "No Permission.");
            return false;
        }

        if (args[0] != null) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(CC.Red + "Can't find target or target was not online.");
                return false;
            }
            Freeze.handle(target, player);
        }

        if (player.getItemInHand() == null || player.getItemInHand().getType().equals(Material.AIR)) {
            player.sendMessage(CC.Red + "Item was air or null. Cancelling this giveall.");
            return false;
        }
        ManualGiveAll.func(player.getItemInHand());
        if (player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName()) {
            player.sendMessage(CC.LightPurple + "Successfully given everybody a " + player.getItemInHand().getItemMeta().getDisplayName());
        } else {
            player.sendMessage(CC.LightPurple + "Successfully given everybody a " + player.getItemInHand().getType());
        }
        return false;
    }
}