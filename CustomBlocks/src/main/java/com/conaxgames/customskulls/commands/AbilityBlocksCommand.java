package com.conaxgames.customskulls.commands;

import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.data.MakeCustomBlocks;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.ui.CustomBlocksUI;
import com.conaxgames.customskulls.util.CC;
import de.tr7zw.nbtapi.NBTItem;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Map;

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

        if (strings[0].equals("items")) {
            CustomBlocksUI.open(player);
            return true;
        }
        if (strings[0].equals("nbt")) {
            if (player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
                NBTItem nbtItem = new NBTItem(player.getItemInHand());
                player.sendMessage(String.valueOf(nbtItem.getKeys()));
            }
            return true;
        }
        if (strings[0].equals("debug")) {
            player.sendMessage("debug command! you should see a yml file!");
            MakeCustomBlocks.newFileItem(player, AbilityType.CAULDRON);
            return true;
        }
        if (strings[0].equals("list")) {
            for (Map.Entry<String, AbilityData> data : AbilityData.getAbilityDataMap().entrySet()) {
                player.sendMessage(data.getValue().getUuid());
            }
            return true;
        }

        usage(player);
        return false;
    }

    public void usage(Player player) {
        player.sendMessage(CC.GRAY + CC.STRIKE_THROUGH + "---------------------------------------");
        player.sendMessage(CC.GOLD + "/cb items" + CC.YELLOW + " - Opens a UI to get all CustomBlocks.");
        player.sendMessage(CC.GOLD + "/cb list" + CC.YELLOW + " - Sends a list of all CustomBlocks in memory.");
        player.sendMessage(CC.GRAY + CC.STRIKE_THROUGH + "---------------------------------------");
    }
}
