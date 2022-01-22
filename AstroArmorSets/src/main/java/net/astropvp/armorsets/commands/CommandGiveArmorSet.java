package net.astropvp.armorsets.commands;


import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.ArmorSetType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CommandGiveArmorSet implements CommandExecutor {
    @SneakyThrows
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && !sender.isOp()) {
            //new ArmorSetHelpMenu((Player) sender).openGUI(CosmicArmorSetsPlugin.get_());
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("help") && sender instanceof Player) {
           //new ArmorSetHelpMenu((Player) sender).openGUI(CosmicArmorSetsPlugin.get_());
            return true;
        }
        if (!sender.isOp()) {
            sender.sendMessage(CC.Red + "No permission.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(CC.GoldB + "/armorset <armorSet>");
            return true;
        }


        ArmorSetType as = null;
        try {
            as = ArmorSetType.valueOf(args[0].toUpperCase());
        } catch (Exception exception) {
            // empty catch block
        }
        if (args.length >= 2 && args[1].equalsIgnoreCase("crystal") && as != null) {
            assert sender instanceof Player;
            Player pSender = (Player) sender;
            Double given = args.length == 3 ? NumberUtils.parseDoubleOrNull(args[2]) : null;
            PlayerUtil.giveOrDropProtectedItem(pSender, AstroArmorSetsAPI.createCrystal(Lists.newArrayList(as), given == null ? 20.0 : given), -1);
            sender.sendMessage(CC.Red + "Crystal added!");
            return true;
        }
        if (as != null && sender instanceof Player) {
            Player pSender = (Player) sender;
            for (ItemStack i : as.getItems()) {
                pSender.getInventory().addItem(i);
            }
            return true;
        }
        sender.sendMessage(CC.Red + "Armor Sets: " + Arrays.toString(ArmorSetType.values()));
        return true;
    }
}

