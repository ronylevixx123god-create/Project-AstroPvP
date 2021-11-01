/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.GeneralUtil
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.NumberUtils
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.PlayerUtil
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package com.compilex.CosmicArmorSets.cmds;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import com.compilex.CosmicArmorSets.enums.HeroicWeaponType;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.*;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CommandHeroicArmor implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(CC.Red + "No permission.");
            return true;
        }
        if (args.length >= 2) {
            Player pl = Bukkit.getPlayer(args[1]);
            if (pl == null) {
                sender.sendMessage(CC.Red + "No player found!");
                return true;
            }
            HeroicArmorType type = HeroicArmorType.getFromName(args[0]);
            if (type == null) {
                sender.sendMessage(CC.Red + "Invalid Heroic Armor type! Valid: " + Arrays.toString(HeroicArmorType.values()));
                return true;
            }
            ItemStack item = CosmicArmorSetsAPI.createHeroicPeice(type);
            if (args.length == 3) {
                if (args[2].equalsIgnoreCase("ench")) {
                    item = new NBTWrapper(item).setGlowing().build();
                } else {
                    Integer parsed = NumberUtils.parseIntOrNull(args[2]);
                    if (parsed != null && parsed > 0) {
                        NBTWrapper wrapper = new NBTWrapper(item);
                        item = wrapper.setNBTBase("conaxData", new NBTTagCompoundBuilder(wrapper.getNBTBase("conaxData")).setInt("armorModifier", parsed).get()).build();
                        sender.sendMessage(CC.Red + "Item given with armor modifier: " + parsed);
                    }
                }
            }
            if (item != null) {
                PlayerUtil.giveOrDropProtectedItem(pl, item, -1);
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("convert")) {
            Player player = (Player) sender;
            ItemStack hand = player.getItemInHand();
            boolean armor = GeneralUtil.isArmor(hand);
            boolean weapon = GeneralUtil.isWeapon(hand);
            if (!armor && !weapon) {
                sender.sendMessage(CC.Red + "Invalid armor item!");
                return true;
            }
            if (armor) {
                HeroicArmorType type = HeroicArmorType.getFromMaterial(hand.getType());
                if (type == null) {
                    sender.sendMessage(CC.Red + "Invalid armor type given!");
                    return true;
                }
                player.setItemInHand(CosmicArmorSetsAPI.convertItemToHeroic(hand, type, false));
            } else if (weapon) {
                HeroicWeaponType type = HeroicWeaponType.findFromMaterial(hand.getType());
                if (type == null) {
                    sender.sendMessage(CC.Red + "Unable to find item to convert to!");
                    return true;
                }
                player.setItemInHand(CosmicArmorSetsAPI.convertIntoHeroicWeapon(type, hand, false));
            }
            player.updateInventory();
            player.sendMessage(CC.Red + "Item converted.");
            return true;
        }
        sender.sendMessage(CC.Red + "Usage: /heroicarmor <HELMET/CHESTPLATE/LEGGINGS/BOOTS> <name>");
        return false;
    }
}

