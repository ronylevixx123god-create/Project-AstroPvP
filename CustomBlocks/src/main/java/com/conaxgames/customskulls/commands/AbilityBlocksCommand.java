package com.conaxgames.customskulls.commands;

import com.conaxgames.customskulls.ui.CustomBlocksAdminUI;
import com.conaxgames.customskulls.ui.ViewCustomBlocksFromFileUI;
import com.conaxgames.customskulls.util.CC;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class AbilityBlocksCommand extends Command {

    public AbilityBlocksCommand() {
        super("cb");
        setAliases(Collections.singletonList("cblocks"));
    }

    @SneakyThrows
    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("CustomBlocks is a player only command.");
            return true;
        }

        Player player = ((Player) commandSender).getPlayer();
        if (strings.length == 0) {
            usage(player);
            return false;
        }

        if (strings[0].equals("view")) {
            CustomBlocksAdminUI.open(player);
            return true;
        }

        if (strings[0].equals("list")) {
            if (player.hasPermission("customblocks.admin")) {
                ViewCustomBlocksFromFileUI.open(player);
                return true;
            } else {
                player.sendMessage(CC.translate("&cNo permission."));
                return true;
            }
        }

        usage(player);
        return false;
    }

    public void usage(Player player) {
        player.sendMessage(CC.GRAY + CC.STRIKE_THROUGH + "---------------------------------------");
        player.sendMessage(CC.GOLD + "/cb view" + CC.YELLOW + " - Opens a UI to view all custom blocks.");
        player.sendMessage(CC.GOLD + "/cb list" + CC.YELLOW + " - View all placed CustomBlocks in memory.");
        player.sendMessage(CC.GRAY + CC.STRIKE_THROUGH + "---------------------------------------");
    }
}
