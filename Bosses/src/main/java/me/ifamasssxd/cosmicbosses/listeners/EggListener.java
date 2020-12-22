/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicbosses.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.utils.BossEggUtils;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class EggListener
implements Listener {
    @EventHandler(priority=EventPriority.LOW)
    public void onInteractPLayer(PlayerInteractEvent e) {
        Player p;
        ItemStack is;
        BossType type;
        if (e.hasItem() && (type = BossEggUtils.getBossType(is = (p = e.getPlayer()).getItemInHand())) != null) {
            boolean alreadyCancelled = e.isCancelled();
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            if (!e.hasBlock() || alreadyCancelled) {
                return;
            }
            Block block = e.getClickedBlock();
            Block above = block.getRelative(BlockFace.UP);
            if (!CosmicBosses.getInstance().isEggEnabled()) {
                p.sendMessage((Object)ChatColor.RED + "Bosses are currently disabled!");
                return;
            }
            int highestY = BossUtils.getHighestBlockYAt(block.getLocation(), false);
            if (!(block.getY() >= highestY - 1 || block.getWorld().getName().equals("world_private") || p.isOp() && p.isSneaking())) {
                p.sendMessage((Object)ChatColor.RED + "You must spawn this boss at the highest Y coordinate.");
                return;
            }
            if (p.isOp() && p.isSneaking() || above.isEmpty() && above.getRelative(BlockFace.UP).isEmpty() && WorldGuardUtils.isPvPEnabled(block.getLocation()) && BossUtils.isWarzone(block.getLocation())) {
                if (Boss.bosses.values().stream().filter(Boss::isBossSpawned).count() > 10L) {
                    p.sendMessage(CC.RedB + "(!) " + CC.Red + "There are currently too many active bosses to summon this creature!");
                    return;
                }
                Location spawnLoc = block.getLocation().add(0.5, 1.0, 0.5);
                if (is.getAmount() > 1) {
                    is.setAmount(is.getAmount() - 1);
                } else {
                    p.setItemInHand(null);
                }
                p.updateInventory();
                Boss boss = type.createInstance(spawnLoc);
                boss.onBossSpawn();
                if (boss.getEntity() != null) {
                    boss.getEntity().setMetadata("playerSpawnedBoss", (MetadataValue)new FixedMetadataValue((Plugin)CosmicBosses.getInstance(), (Object)""));
                }
            } else {
                p.sendMessage((Object)ChatColor.RED + "You cannot spawn this boss here.");
            }
        }
    }
}

