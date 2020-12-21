/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  org.bukkit.Bukkit
 *  org.bukkit.World
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.libs.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

final class CommandCWarzone
extends AbstractCommand {
    public CommandCWarzone(String command, String usage, String description) {
        super(command, usage, description);
    }

    @Override
    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("/cwarzone <chunkRadius>");
            sender.sendMessage("i.e) /cwarzone 2 -- would claim 4x4 chunks or 64x64 blocks around the player in the world they're in.");
            return true;
        }
        Player p = (Player)sender;
        final World w = p.getWorld();
        int radius = Integer.valueOf(args[0]);
        final int xMin = p.getLocation().getChunk().getX() - radius;
        final int xMax = p.getLocation().getChunk().getX() + radius;
        final int zMin = p.getLocation().getChunk().getZ() - radius;
        final int zMax = p.getLocation().getChunk().getZ() + radius;
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Arkkit.get(), new Runnable(){

            @Override
            public void run() {
                int i = 0;
                Faction warzoneFaction = FactionUtils.getFactionById((String)"-2");
                for (int chunkX = xMin; chunkX <= xMax; ++chunkX) {
                    for (int chunkZ = zMin; chunkZ <= zMax; ++chunkZ) {
                        FLocation fl = new FLocation(w.getName(), chunkX, chunkZ);
                        Board.setFactionAt((Faction)warzoneFaction, (FLocation)fl);
                        Bukkit.getLogger().info("[Arkkit (ypperin)] Set chunk " + chunkX + "," + chunkZ + " in " + w.getName() + " to WARZONE");
                        ++i;
                    }
                }
                sender.sendMessage("[Arkkit] Set " + i + " chunks to WARZONE in " + w.getName() + "!");
            }
        });
        return true;
    }
}

