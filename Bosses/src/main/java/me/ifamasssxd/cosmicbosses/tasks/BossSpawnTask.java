/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package me.ifamasssxd.cosmicbosses.tasks;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicbosses.utils.BossUtils;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BossSpawnTask
implements Runnable {
    Random random = new Random();

    @Override
    public void run() {
        boolean enabled = CosmicBosses.getInstance().isNaturalSpawnEnabled();
        if (!enabled) {
            return;
        }
        if (Bukkit.getOnlinePlayers().size() == 0 || Boss.bosses.size() >= 10) {
            return;
        }
        BossType type = null;
        int chance = this.random.nextInt(100);
        if (chance < 40) {
            type = BossType.BROOD_MOTHER;
        } else if (chance < 70) {
            type = BossType.KING_SLIME;
        } else if (chance < 85) {
            type = BossType.SKELETON_HORSE;
        } else if (chance < 94) {
            type = BossType.PLAGUE_BLOATER;
        } else if (chance < 100) {
            type = BossType.DEMONIC_WITHER;
        }
        Location l = BossUtils.getRandomRegionLocation("warzone_spawn_500000");
        if (l == null) {
            Bukkit.getLogger().info("[CosmicBosses] Null location returned by getRandomRegionLocation, skipping BossSpawnTask.");
            return;
        }
        Location highest = l.clone();
        highest.setY((double)(BossUtils.getHighestBlockYAt(highest, false) + 2));
        boolean isSuiteableLocation = this.isSuitableLocation(highest);
        Bukkit.getLogger().info("[CosmicBosses] getRandomRegionLocation(): " + (Object)l + ", goodLocation: " + isSuiteableLocation);
        if (!isSuiteableLocation) {
            Bukkit.getScheduler().runTask((Plugin)CosmicBosses.get(), (Runnable)this);
            return;
        }
        Boss boss = type.createInstance(highest);
        boss.setNaturallySpawned(true);
        boss.onBossSpawn();
    }

    private boolean isSuitableLocation(Location loc) {
        return !WorldGuardUtils.isPvPDisabled(loc) && !WorldGuardUtils.getRegionName(loc).startsWith("spawn_") && !WorldGuardUtils.getRegionName(loc).contains("warp");
    }

    private Player getRandomWarzonePlayer() {
        ArrayList players = Lists.newArrayList((Iterable)Bukkit.getOnlinePlayers());
        Collections.shuffle(players);
        for (Player retr : players) {
            if (retr.hasMetadata("NPC") || !(retr.getLocation().getY() < 256.0) || !BossUtils.canAffectPlayer(retr, false) || WorldGuardUtils.isPvPDisabled(retr.getLocation()) || WorldGuardUtils.getRegionName(retr.getLocation()).startsWith("spawn_") || WorldGuardUtils.getRegionName(retr.getLocation()).contains("warp")) continue;
            return retr;
        }
        return null;
    }
}

