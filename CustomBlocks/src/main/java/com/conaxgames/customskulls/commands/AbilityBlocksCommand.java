package com.conaxgames.customskulls.commands;

import com.conaxgames.customskulls.ability.AbilityData;
import com.conaxgames.customskulls.data.MakeCustomBlocks;
import com.conaxgames.customskulls.enums.AbilityType;
import com.conaxgames.customskulls.ui.CustomBlocksUI;
import de.tr7zw.nbtapi.NBTItem;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Map;

public class AbilityBlocksCommand extends Command {

    public AbilityBlocksCommand() {
        super("testabc");
        setAliases(Collections.singletonList("testabc1"));
        setUsage(ChatColor.RED + "/testabc");
    }

    @SneakyThrows
    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage("Player command.");
            return true;
        }

        Player player = ((Player) commandSender).getPlayer();
        if (strings.length == 0) {
            player.sendMessage("debug");
            player.sendMessage("list");
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
            MakeCustomBlocks.newFileItem(player, AbilityType.FIVE_PERCENT_DAMAGE_INCREASE);
            return true;
        }
        if (strings[0].equals("list")) {
            for (Map.Entry<String, AbilityData> data : AbilityData.getAbilityDataMap().entrySet()) {
                player.sendMessage(data.toString());
            }
            return true;
        }

        return false;
    }
}
