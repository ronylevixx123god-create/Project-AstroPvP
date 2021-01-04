/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.EconomyUtils
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.NumberUtils
 *  com.massivecraft.factions.Faction
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
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.struct.BannerDesign;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandBanner
implements CommandExecutor {
    private double BANNER_COST = 100000.0;

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        Faction faction = FactionUtils.getFaction((Player)player, (boolean)false);
        if (!FactionUtils.isNormal((Faction)faction)) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be in a faction to use this command!");
            return true;
        }
        if (!EconomyUtils.hasBalance((OfflinePlayer)player, (double)this.BANNER_COST)) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot afford " + CC.RedB + "$" + CC.Red + NumberUtils.formatMoney((double)this.BANNER_COST) + CC.Red + " for a Faction Banner!");
            return true;
        }
        if (player.getInventory().firstEmpty() == -1) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Your inventory is full!");
            return true;
        }
        BannerDesign design = P.getP().getBannerManager().getBannerDesignMap().get(faction.getId());
        ItemStack banner = design != null ? design.getBannerItem(faction) : P.getP().getBannerManager().createBanner(faction, null, true);
        sender.sendMessage(CC.RedB + "- $" + CC.Red + NumberUtils.formatMoney((double)this.BANNER_COST));
        sender.sendMessage(CC.GreenB + "+ 1 " + CC.Green + "Faction Banner");
        if (P.getP().getConfig().getInt("soulCost") > 0) {
            sender.sendMessage(CC.Gray + "(note: /f banner costs " + P.getP().getConfig().getInt("soulCost") + " souls to place.)");
        }
        EconomyUtils.withdrawBalance((OfflinePlayer)player, (double)this.BANNER_COST);
        player.getInventory().addItem(new ItemStack[]{banner});
        player.updateInventory();
        return false;
    }
}

