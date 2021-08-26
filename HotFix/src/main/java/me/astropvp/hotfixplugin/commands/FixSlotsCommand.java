package me.astropvp.hotfixplugin.commands;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FixSlotsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        Player sender = ((Player) commandSender).getPlayer();

        if (sender.getItemInHand() == null) {
            sender.sendMessage(ChatColor.RED + "Empty hand.");
            return false;
        }
        if (sender.getItemInHand().getType() == null) {
            sender.sendMessage(ChatColor.RED + "Empty hand.");
            return false;
        }
        if (sender.getItemInHand().getType() != Material.PAPER) {
            sender.sendMessage(ChatColor.RED + "This is not a slotbot ticket.");
            return false;
        }

        NBTWrapper wrapper = new NBTWrapper(sender.getItemInHand());
        if (wrapper.hasKey("xpShopPrice")) {
            wrapper.remove("xpShopPrice");
            NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"));
            sender.setItemInHand(wrapper.setNBTBase("cosmicData", builder.get()).build());
            sender.sendMessage(CC.translate("&a&l(!)&a Fixed your slotbots!"));
            return true;
        }
        sender.sendMessage(ChatColor.RED + "This is not a broken slotbot ticket.");
        return false;
    }
}
