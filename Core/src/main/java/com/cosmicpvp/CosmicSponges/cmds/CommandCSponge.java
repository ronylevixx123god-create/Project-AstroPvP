/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package com.cosmicpvp.CosmicSponges.cmds;

import com.cosmicpvp.CosmicSponges.CosmicSpongesAPI;
import com.cosmicpvp.CosmicSponges.CosmicSpongesPlugin;
import com.cosmicpvp.CosmicSponges.libs.SpongeChunk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandCSponge
implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("/csponge <save / list / debug / clean>");
            return true;
        }
        if (args[0].equalsIgnoreCase("save")) {
            Bukkit.getScheduler().runTaskAsynchronously((Plugin)CosmicSpongesPlugin.get_(), new Runnable(){

                @Override
                public void run() {
                    CosmicSpongesAPI.saveSpongeChunks();
                }
            });
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            for (SpongeChunk sc : CosmicSpongesAPI.getCachedSpongeChunks().values()) {
                if (sc.getSponges().size() <= 0) continue;
                Bukkit.getLogger().info("[CosmicSponges] " + sc.getFileName() + " has " + sc.getSponges().size() + " sponges.");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("debug")) {
            if (CosmicSpongesAPI.debug) {
                CosmicSpongesAPI.debug = false;
                Bukkit.getLogger().info("[CosmicSponges] DEBUG => FALSE");
            } else {
                CosmicSpongesAPI.debug = true;
                Bukkit.getLogger().info("[CosmicSponges] DEBUG => TRUE");
            }
        }
        if (args[0].equalsIgnoreCase("clean")) {
            Player pSender = (Player)sender;
            Chunk centerChunk = pSender.getLocation().getChunk();
            for (int x = -3; x < 3; ++x) {
                for (int z = -3; z < 3; ++z) {
                    Chunk c = pSender.getWorld().getChunkAt(centerChunk.getX() + x, centerChunk.getZ() + z);
                    SpongeChunk sc = CosmicSpongesAPI.cacheSpongeChunk(c);
                    sc.clean();
                }
            }
            pSender.sendMessage((Object)ChatColor.GREEN + "Successfully cleaned all null sponges in a 3x3 chunk radius.");
            return true;
        }
        return true;
    }
}

