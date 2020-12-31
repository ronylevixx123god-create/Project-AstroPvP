/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicKOTH.utils.KothItemUtils
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  com.google.common.collect.Lists
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicArmorSets.cmds;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.HeroicWeaponType;
import com.compilex.CosmicArmorSets.enums.armorsets.DimensionalTravelerSet;
import com.compilex.CosmicArmorSets.enums.armorsets.MotherOfYijki;
import com.compilex.CosmicArmorSets.menu.ArmorSetHelpMenu;
import com.compilex.CosmicKOTH.utils.KothItemUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CommandArmorSet
        implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("help") && sender instanceof Player) {
            new ArmorSetHelpMenu((Player) sender).openGUI(CosmicArmorSetsPlugin.get_());
            return true;
        }
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/armorset <armorSet>/AXE/SWORD/extractioncrystal [crystal]");
            return true;
        }
        if (args.length == 1 && (args[0].equals("travelerpassive") || args[0].equalsIgnoreCase("dimensionalpassive"))) {
            ((DimensionalTravelerSet) ArmorSet.DIMENSIONAL_TRAVELER.getArmorSetInfo()).onDimensionalRift((Player) sender);
            sender.sendMessage(CC.Red + "Proc'ing Dimensional Traveler!");
            return true;
        }
        if (args.length == 1 && args[0].equals("yijkirev")) {
            Player p = (Player) sender;
            ((MotherOfYijki) ArmorSet.MOTHER_OF_YIJKI.getArmorSetInfo()).activateYijki(p.getLocation(), p);
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("rangerbow")) {
            ((Player) sender).getInventory().addItem(new ItemStack[]{ArmorSet.createRangerBow()});
            return true;
        }
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("AXE") || args[0].equalsIgnoreCase("SWORD")) {
                HeroicWeaponType type = HeroicWeaponType.valueOf(args[0]);
                ((Player) sender).getInventory().addItem(CosmicArmorSetsAPI.createHeroicWeapon(type));
                return true;
            }
            ArmorSet as = null;
            try {
                as = ArmorSet.valueOf(args[0].toUpperCase());
            } catch (Exception exception) {
                // empty catch block
            }
            if (args.length >= 2 && args[1].equalsIgnoreCase("crystal") && as != null) {
                Player pSender = (Player) sender;
                Double given = args.length == 3 ? NumberUtils.parseDoubleOrNull(args[2]) : null;
                PlayerUtil.giveOrDropProtectedItem(pSender, ArmorSet.createCrystal(Lists.newArrayList(as), given == null ? 20.0 : given), (int) -1);
                sender.sendMessage(CC.Red + "Crystal added!");
                return true;
            }
            if (as != null && as.getArmorSetInfo() != null && sender instanceof Player) {
                Player pSender = (Player) sender;
                for (ItemStack i : CosmicArmorSetsAPI.getArmorSet(as)) {
                    pSender.getInventory().addItem(i);
                }
                ItemStack weapon = CosmicArmorSetsAPI.getWeaponFromArmorSet(as);
                if (weapon != null) {
                    pSender.getInventory().addItem(weapon);
                }
                if (as == ArmorSet.KOTH) {
                    pSender.getInventory().addItem(KothItemUtils.getKothAxe(pSender.getName()));
                    pSender.getInventory().addItem(KothItemUtils.getKothSwordPvP(pSender.getName()));
                }
                return true;
            }
        }
        sender.sendMessage(CC.Red + "Armor Sets: " + Arrays.toString(ArmorSet.values()));
        return true;
    }
}

