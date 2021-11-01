/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.struct.factions.points.FactionPointUtils
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.FactionUtils
 *  com.massivecraft.factions.Faction
 *  net.minecraft.util.com.google.common.util.concurrent.AtomicDouble
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 */
package me.ifamasssxd.factionupgrades.commands;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.google.common.util.concurrent.AtomicDouble;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import me.ifamasssxd.factionupgrades.struct.UpgradeManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandConvertToFactionPoints
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        sender.sendMessage(CC.RedB + "(!) " + CC.Red + "Attempting to convert existing f upgrades...");
        UpgradeManager manager = P.getP().getUpgradeManager();
        manager.getUpgradeInfo().forEach((factionId, upgradeInfo) -> {
            AtomicDouble cost = new AtomicDouble(0.0);
            upgradeInfo.getUpgradeTiers().forEach((upgrade, level) -> {
                if (cost.get() <= 0.0) {
                    return;
                }
                Faction faction = FactionUtils.getFactionById((String)factionId);
                if (faction == null) {
                    return;
                }
                Bukkit.getLogger().info("[FactionUpgrades] Giving " + factionId + " (" + faction.getTag() + ") " + cost.get() + " for faction upgrade levels!");
            });
        });
        sender.sendMessage(CC.Red + "Converted faction upgrades to $!");
        return false;
    }
}

