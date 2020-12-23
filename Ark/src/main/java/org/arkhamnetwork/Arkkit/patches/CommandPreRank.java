/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.itembuilder.CommandItemBuilder
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.itembuilder.CommandItemBuilder;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

final class CommandPreRank
        extends AbstractCommand {
    public CommandPreRank(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/prerank <player> <rankTier>");
            return true;
        }
        String playerName = args[0];
        int rankTier = Integer.parseInt(args[1]);
        Player p = Bukkit.getPlayer(playerName);
        if (p == null) {
            sender.sendMessage("Could not find player:" + playerName + " online!");
            return true;
        }
        CommandItemBuilder cmdItem = new CommandItemBuilder();
        String rankName = "Pilot";
        if (rankTier == 5) {
            rankName = "Cosmonaut";
        } else if (rankTier == 4) {
            rankName = "Commander";
        } else if (rankTier == 3) {
            rankName = "Engineer";
        } else if (rankTier == 2) {
            rankName = "Specialist";
        } else if (rankTier == 1) {
            rankName = "Pilot";
        }
        cmdItem.setCommand("queue legacyplanet ip:0.0.0.0 package:Kit Legacy id:prebuy_{playerName} username:{playerName} uuid:{playerUUID} rankquest giveitems {playerName} " + rankTier);
        cmdItem.setMessage("&e&l(!) &eYou have successfully redeemed a &b&lCosmicPvP: &c&lLegacy &erank! (&7" + rankName + "&e), login to /server legacyplanet and run /confirm!");
        cmdItem.setItemName("&6&lLEGACY RANK \"&f" + rankName + "&6&l\"");
        cmdItem.setItemLore("&7Right-click this item to unlock", "&7the " + rankName + " Rank (Tier " + rankTier + ") on the", "&7released Legacy Alien Planet.");
        p.getInventory().addItem(cmdItem.build());
        return true;
    }
}

