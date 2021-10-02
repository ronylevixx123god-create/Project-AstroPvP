// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.tasks;

import net.astropvp.SandBots;
import net.astropvp.managers.SandBotManager;
import org.bukkit.entity.FallingBlock;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class BlockSpawnTask implements Runnable
{
    private SandBots plugin;
    
    public BlockSpawnTask(final SandBots plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void run() {
        if(SandBots.sandBots.isEmpty())
            return;
        for (SandBotManager bot : SandBots.sandBots) {
            if (!bot.isActivated()) {
                continue;
            }
            if (bot.getBalance() < bot.getLapisBlocks().size() * 10 || bot.getLapisBlocks().isEmpty()) {
                bot.setActivated(false);
                bot.setAnimate(false);
            }
            else {
                boolean stopAnimation = true;
                for (final Block block : bot.getLapisBlocks()) {
                    if (block.getLocation().getBlock().getType() != Material.LAPIS_BLOCK) {
                        Bukkit.getServer().getScheduler().runTask((Plugin)this.plugin, () -> bot.removeBlock(block.getLocation().getBlock()));
                    }
                    else {
                        if (block.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
                            continue;
                        }
                        stopAnimation = false;
                        if (!bot.isAnimate()) {
                            bot.setAnimate(true);
                        }
                        final Location location = block.getLocation();
                        location.add(0.0, -1.0, 0.0);
                        final FallingBlock falling = bot.getLocation().getWorld().spawnFallingBlock(location, Material.SAND, (byte)0);
                        falling.setDropItem(false);
                        falling.setVelocity(new Vector(0, -1, 0));
                        bot.removeBalance((double) 10);
                    }
                }
                if (!stopAnimation) {
                    continue;
                }
                bot.setAnimate(false);
            }
        }
    }
}
