package me.ifamasssxd.cosmicbosses.bosses.brood;

import me.ifamasssxd.cosmicbosses.struct.*;
import org.bukkit.entity.*;
import me.ifamasssxd.cosmicbosses.utils.*;
import org.bukkit.scheduler.*;
import me.ifamasssxd.cosmicbosses.*;
import org.bukkit.plugin.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import java.util.concurrent.*;

public class WebAttack extends BossAbility
{
    Random random;
    
    public WebAttack(final Boss boss, final BlockModifier modifier) {
        super(boss, modifier);
        this.random = new Random();
    }
    
    @Override
    public void onAbility() {
        for (final Entity ent : this.getBoss().getNearbyEntities(20.0, EntityType.PLAYER)) {
            final Player pl = (Player)ent;
            if (BossUtils.canAffectPlayer(pl, false)) {
                this.getBoss().sendMessage(pl, ChatColor.ITALIC + "Feel the despair wash over you, for you have fallen into my trap.");
                final Location playerLoc = pl.getLocation();
                final int minX = playerLoc.getBlockX() - 1;
                final int maxX = playerLoc.getBlockX() + 2;
                final int minZ = playerLoc.getBlockZ() - 1;
                final int maxZ = playerLoc.getBlockZ() + 2;
                final int minY = playerLoc.getBlockY() - 1;
                final int maxY = playerLoc.getBlockY() + 2;
                final Location location = pl.getLocation().clone();
                for (int x = minX; x < maxX; ++x) {
                    for (int y = minY; y < maxY; ++y) {
                        for (int z = minZ; z < maxZ; ++z) {
                            location.setX((double)x);
                            location.setY((double)y);
                            location.setZ((double)z);
                            if (!this.getBlockModifier().containsLocation(this, location)) {
                                if (this.random.nextInt(100) >= 40) {
                                    final Block b = location.getBlock();
                                    if (b.getType() == Material.AIR) {
                                        b.setType(Material.WEB);
                                        this.getBlockModifier().storeBlockToRemove(this, location);
                                    }
                                }
                            }
                        }
                    }
                }
                pl.playSound(pl.getLocation(), Sound.SPIDER_IDLE, 4.0f, 0.8f);
            }
        }
        final WebAttack instance = this;
        new BukkitRunnable() {
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
