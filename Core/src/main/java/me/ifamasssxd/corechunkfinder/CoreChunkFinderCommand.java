/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.FactionUtils
 *  com.cosmicpvp.factionpoints.CosmicFactionPoints
 *  com.cosmicpvp.factionpoints.FactionsCoreChunkAPI
 *  com.cosmicpvp.factionpoints.struct.CoreChunkData
 *  com.cosmicpvp.factionpoints.struct.ExpiringChunkData
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.corechunkfinder;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.factionpoints.CosmicFactionPoints;
import com.cosmicpvp.factionpoints.FactionsCoreChunkAPI;
import com.cosmicpvp.factionpoints.struct.CoreChunkData;
import com.cosmicpvp.factionpoints.struct.ExpiringChunkData;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import net.astropvp.core.AstroCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoreChunkFinderCommand
implements CommandExecutor {
    DateFormat dateFormat = new SimpleDateFormat("MM dd hh:mm aa");

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("tntdebug")) {
                AstroCore.getInstance().setTntDebug(!AstroCore.getInstance().isTntDebug());
                sender.sendMessage(CC.Red + "Tnt debug: " + AstroCore.getInstance().isTntDebug());
                return true;
            }
            Faction faction = FactionUtils.getFactionByTag((String)args[0]);
            if (faction == null) {
                sender.sendMessage(CC.Red + "Invalid faction!");
                return true;
            }
            CoreChunkData data = FactionsCoreChunkAPI.getChunkData((Faction)faction);
            sender.sendMessage(CC.Red + faction.getTag() + "'s Core Chunk Data: ");
            sender.sendMessage(CC.Red + "Core Chunk: " + (Object)data.getMasterChunk());
            sender.sendMessage(CC.Red + "Claim Time: " + (data.getCoreChunkClaimTime() == null ? "None" : this.dateFormat.format(data.getCoreChunkClaimTime())));
            sender.sendMessage(CC.Red + "Claim Removed: " + (data.getCoreChunkRemovedAt() == null ? "None" : this.dateFormat.format(data.getCoreChunkRemovedAt())));
            sender.sendMessage(CC.Red + "Last Raided By: " + data.getLastFactionWhoRaided());
            sender.sendMessage(CC.Red + "Raided at: " + (data.getLastRaided() == null ? "None" : this.dateFormat.format(data.getLastRaided())));
            return true;
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("setcorechunktime")) {
                Long val;
                Faction faction = FactionUtils.getFactionByTag((String)args[1]);
                if (faction == null) {
                    sender.sendMessage(CC.Red + "Invalid faction!");
                    return true;
                }
                try {
                    val = Long.valueOf(args[2]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    sender.sendMessage(CC.Red + "invalid epoch time given!");
                    return true;
                }
                CoreChunkData data = FactionsCoreChunkAPI.getChunkData((Faction)faction);
                if (data == null) {
                    sender.sendMessage(CC.Red + "No core chunk me.astropvp.cratekeys.data stored for " + faction.getTag());
                    return true;
                }
                if (data.getMasterChunk() == null) {
                    sender.sendMessage(CC.Red + "No master chunk stored for them!");
                    return true;
                }
                data.setCoreChunkClaimTime(val);
                sender.sendMessage(CC.Red + "Set core chunk claim time to " + this.dateFormat.format(val) + " core chunk: " + (Object)data.getMasterChunk());
                return true;
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("setclaimtime")) {
            Long val;
            try {
                val = Long.valueOf(args[1]);
            }
            catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(CC.Red + "invalid epoch time given!");
                return true;
            }
            ExpiringChunkData data = CosmicFactionPoints.get().getChunkManager().getChunksToExpire().get(new FLocation((Player)sender));
            if (data == null) {
                sender.sendMessage(CC.Red + "No chunk me.astropvp.cratekeys.data stored at your location!");
                return true;
            }
            long claimed = data.getTimeClaimed();
            data.timeClaimed = val;
            sender.sendMessage(CC.Red + "Time claimed set! Previous: " + claimed);
            return true;
        }
        FLocation at = new FLocation((Player)sender);
        sender.sendMessage(CC.Red + "Core chunk: " + FactionsCoreChunkAPI.isCoreChunk((FLocation)at));
        sender.sendMessage(CC.Red + "Faction Owned: " + Board.getFactionAt((FLocation)at).getTag());
        return false;
    }
}

