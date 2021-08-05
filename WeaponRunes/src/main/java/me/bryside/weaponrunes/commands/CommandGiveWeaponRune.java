package me.bryside.weaponrunes.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.runes.HeroWeaponBonus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandGiveWeaponRune implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player)sender;
        if (!player.isOp())
            return true;
        if (args.length == 2) {
            WeaponBonusType weaponBonusType = WeaponBonusType.valueOf(args[0]);
            if (weaponBonusType == null) {
                player.sendMessage(CC.RedB + "(!)" + CC.Red + " Weapon Bonus not found. Available Weapon Bonuses:");
                player.sendMessage(CC.Red + ((List) Arrays.<WeaponBonusType>stream(WeaponBonusType.values()).map(WeaponBonusType::getId).collect(Collectors.toList())).toString() + ".");
                return true;
            }
            player.getInventory().addItem(new ItemStack[] { new ItemStack(weaponBonusType.getInstance().getItem(Double.parseDouble(args[1]))) });
        } else {
            player.sendMessage(CC.Red + "Correct Usage: /giveweaponbonus <type> <success>");
        }
        return false;
    }
}
