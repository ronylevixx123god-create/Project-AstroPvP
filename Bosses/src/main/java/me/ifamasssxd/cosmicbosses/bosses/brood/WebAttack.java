/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicbosses.bosses.brood;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.struct.BlockModifier;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class WebAttack
extends BossAbility {
    Random random = new Random();

    public WebAttack(Boss boss, BlockModifier modifier) {
        super(boss, modifier);
    }

    @Override
    public void onAbility() {
        for (Entity ent : ((Boss)this.getBoss()).getNearbyEntities(20.0, EntityType.PLAYER)) {
            Player pl = (Player)ent;
            if (!BossUtils.canAffectPlayer(pl, false)) continue;
            ((Boss)this.getBoss()).sendMessage(pl, (Object)ChatColor.ITALIC + "Feel the despair wash over you, for you have fallen into my trap.");
            Location playerLoc = pl.getLocation();
            int minX = playerLoc.getBlockX() - 1;
            int maxX = playerLoc.getBlockX() + 2;
            int minZ = playerLoc.getBlockZ() - 1;
            int maxZ = playerLoc.getBlockZ() + 2;
            int minY = playerLoc.getBlockY() - 1;
            int maxY = playerLoc.getBlockY() + 2;
            Location location = pl.getLocation().clone();
            for (int x = minX; x < maxX; ++x) {
                for (int y = minY; y < maxY; ++y) {
                    for (int z = minZ; z < maxZ; ++z) {
                        Block b;
                        location.setX((double)x);
                        location.setY((double)y);
                        location.setZ((double)z);
                        if (this.getBlockModifier().containsLocation(this, location) || this.random.nextInt(100) < 40 || (b = location.getBlock()).getType() != Material.AIR) continue;
                        b.setType(Material.WEB);
                        this.getBlockModifier().storeBlockToRemove(this, location);
                    }
                }
            }
            pl.playSound(pl.getLocation(), Sound.SPIDER_IDLE, 4.0f, 0.8f);
        }
        final WebAttack instance = this;
        new BukkitRunnable(){

            public void run() {
                if (WebAttack.this.getBlockModifier() != null) {
                    WebAttack.this.getBlockModifier().removeBlocks(instance, true, true);
                }
            }
        }.runTaskLater((Plugin)CosmicBosses.getInstance(), 100L);
        this.activateCooldown();
    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(15L);
    }

    @Override
    public String getAbilityName() {
        return "Web Attack";
    }
}

