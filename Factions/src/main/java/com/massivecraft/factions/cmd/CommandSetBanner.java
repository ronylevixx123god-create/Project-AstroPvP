/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.EconomyUtils
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.struct.Role
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package com.massivecraft.factions.cmd;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.Role;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandSetBanner
implements CommandExecutor {
    private int BANNER_SET_COST = P.getP().getConfig().getInt("setBannerCost");

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        ItemStack handItem = player.getItemInHand();
        if (handItem == null || handItem.getType() != Material.BANNER) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be holding a banner to set your Faction Banner!");
            return true;
        }
        Faction faction = FactionUtils.getFaction((Player)player, (boolean)false);
        if (!FactionUtils.isNormal((Faction)faction)) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be in a valid faction to run /f setbanner");
            return true;
        }
        FPlayer fplayer = FactionUtils.getFPlayer((Player)player);
        if (!fplayer.getRole().isAtLeast(Role.COLEADER)) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be atleast a faction Co-Leader to set your Faction Banner!");
            return true;
        }
        if (!EconomyUtils.hasBalance((OfflinePlayer)player, (double)this.BANNER_SET_COST)) {
            player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot afford to customize your Faction Banner!");
            player.sendMessage(CC.GrayU + "Cost:" + CC.GrayB + " $" + CC.Gray + NumberUtils.formatMoney((double)this.BANNER_SET_COST));
            return true;
        }
        EconomyUtils.withdrawBalance((OfflinePlayer)player, (double)this.BANNER_SET_COST);
        player.sendMessage(CC.RedB + "- $" + CC.Red + NumberUtils.formatMoney((double)this.BANNER_SET_COST));
        player.sendMessage(CC.Green + "Faction banner updated to your held item!");
        player.sendMessage(CC.Gray + "Use /f banner to receive a Faction Banner for a " + this.BANNER_SET_COST + "!");
        BannerDesign design = P.getP().getBannerManager().getBannerDesignMap().computeIfAbsent(faction.getId(), des -> new BannerDesign());
        design.setBanner(handItem);
        player.setItemInHand((ItemStack)null);
        return true;
    }
}

