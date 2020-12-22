/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockBreakEvent
 *  org.bukkit.event.block.BlockPistonExtendEvent
 *  org.bukkit.event.entity.EntityExplodeEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.utils.PistonManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

final class AntiPistonLagListener
implements Listener {
    PistonManager pm = new PistonManager();
    protected static HashSet<String> warnedChunkCoords = new HashSet();

    @EventHandler
    public void onBlockPistonExtendEvent(BlockPistonExtendEvent e) {
        if (e.getBlocks().size() > 6) {
            e.setCancelled(true);
            return;
        }
        if (this.pm.getPistonCount(e.getBlock().getChunk()) >= Arkkit.get().getConfig().getInt("settings.max_pistons_per_chunk") && !this.pm.isAllowedPiston(e.getBlock())) {
            Bukkit.broadcast((String)((Object)ChatColor.WHITE + "(AntiPistonLag) " + (Object)ChatColor.GRAY + "pistonCount => " + this.pm.getPistonCount(e.getBlock().getChunk()) + ", chunk => " + e.getBlock().getChunk().getX() + "," + e.getBlock().getChunk().getZ()), (String)"arkkit.anti_piston_lag.notify");
            e.setCancelled(true);
            String cs = e.getBlock().getChunk().getX() + "x," + e.getBlock().getChunk().getZ() + "z";
            if (!warnedChunkCoords.contains(cs)) {
                for (Entity ent : e.getBlock().getChunk().getEntities()) {
                    if (!(ent instanceof Player)) continue;
                    Player p = (Player)ent;
                    p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "(!) " + (Object)ChatColor.RED + "You have too many pistons in this chunk (" + cs + ")!");
                    p.sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "LIMIT: " + Arkkit.get().getConfig().getInt("settings.max_pistons_per_chunk") + " pistons per/chunk");
                    p.sendMessage((Object)ChatColor.GRAY + "Up to " + Arkkit.get().getConfig().getInt("settings.max_pistons_per_chunk") + " pistons will be tracked and functional in each chunk, any pistons that attempt physics logic after that will be blocked. Optimize your build.");
                }
                warnedChunkCoords.add(cs);
            }
            return;
        }
        if (!this.pm.containsPiston(e.getBlock())) {
            this.pm.addPiston(e.getBlock());
        }
        ArrayList<Block> block_list = (ArrayList<Block>) e.getBlocks();
        for (Block b : block_list) {
            Material m = b.getType();
            byte data = b.getData();
            if (!b.getType().isSolid()) {
                e.setCancelled(true);
                return;
            }
            if (!this.isFarmable(b.getType())) continue;
            b.setType(Material.AIR);
            if (m == Material.MELON_BLOCK) {
                b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.MELON, new Random().nextInt(3) + 1));
                continue;
            }
            if (m == Material.WEB) {
                b.getWorld().dropItem(b.getLocation(), new ItemStack(Material.STRING, 1));
                continue;
            }
            b.getWorld().dropItem(b.getLocation(), new ItemStack(m, 1, (short)data));
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onBlockBreakPersistant(BlockBreakEvent e) {
        Material m = e.getBlock().getType();
        if ((m == Material.PISTON_BASE || m == Material.PISTON_EXTENSION || m == Material.PISTON_MOVING_PIECE || m == Material.PISTON_STICKY_BASE) && this.pm.containsPiston(e.getBlock())) {
            this.pm.removePiston(e.getBlock());
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onEntityExplode(EntityExplodeEvent e) {
        for (Block b : e.blockList()) {
            Material m = b.getType();
            if (m != Material.PISTON_BASE && m != Material.PISTON_EXTENSION && m != Material.PISTON_MOVING_PIECE && m != Material.PISTON_STICKY_BASE || !this.pm.containsPiston(b)) continue;
            this.pm.removePiston(b);
        }
    }

    private boolean isFarmable(Material m) {
        return m == Material.MELON_BLOCK || m == Material.MELON_STEM || m == Material.CACTUS || m == Material.PUMPKIN || m == Material.PUMPKIN_STEM || m == Material.JACK_O_LANTERN || m == Material.SUGAR_CANE || m == Material.SUGAR_CANE_BLOCK || m == Material.DRAGON_EGG || m == Material.WEB;
    }
}

