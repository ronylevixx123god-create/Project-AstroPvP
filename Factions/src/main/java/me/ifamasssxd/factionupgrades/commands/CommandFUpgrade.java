/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.FactionUtils
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.factionupgrades.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import me.ifamasssxd.factionupgrades.struct.menu.FUpgradeMenu;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandFUpgrade
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player)sender;
        FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
        Faction toSee = fplayer.getFaction();
        if (args.length == 1 && (toSee = FactionUtils.getFactionByTag((String)args[0])) == null) {
            sender.sendMessage(CC.Red + "Invalid faction given, using your faction instead.");
            toSee = fplayer.getFaction();
        }
        if (toSee == null || !toSee.isNormal()) {
            sender.sendMessage(CC.RedB + "(!) " + CC.Red + "You must be in a faction to view your /f upgrades!");
            return true;
        }
        new FUpgradeMenu(player, toSee).openGUI(P.getP());
        return false;
    }
}

