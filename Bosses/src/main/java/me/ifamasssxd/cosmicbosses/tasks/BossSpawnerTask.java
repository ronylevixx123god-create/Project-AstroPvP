/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 */
package me.ifamasssxd.cosmicbosses.tasks;

import me.ifamasssxd.cosmicbosses.CosmicBossEggsPlugin;
import me.ifamasssxd.cosmicbosses.boss.type.Boss;
import me.ifamasssxd.cosmicbosses.boss.type.Bosses;
import me.ifamasssxd.cosmicbosses.utils.MiscUtil;
import me.ifamasssxd.cosmicbosses.utils.WorldGuardUtil;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class BossSpawnerTask
implements Runnable {
    CosmicBossEggsPlugin plugin;
    World w;

    public BossSpawnerTask(CosmicBossEggsPlugin cbe) {
        this.plugin = cbe;
        this.w = this.plugin.getServer().getWorlds().get(0);
    }

    @Override
    public void run() {
        long time = this.w.getTime();
        if (time > 14500L && time < 22999L) {
            for (Player pl : this.w.getPlayers()) {
                FLocation ploc;
                if (new Random().nextInt(100) != 0 || WorldGuardUtil.isPvPDisabled(pl.getLocation()) || !WorldGuardUtil.getRegionName(pl.getLocation()).contains("warzone") || this.plugin.getServer().getPluginManager().isPluginEnabled("Factions") && !Board.getFactionAt((FLocation)(ploc = new FLocation(pl.getLocation()))).isWarZone()) continue;
                Boss b = Bosses.getRandomBoss();
                if (b == Bosses.HEADLESS_KING.b) {
                    b = Bosses.PIG_ZOMBIE.b;
                }
                Location l = MiscUtil.getRandomLocationNearby(pl.getLocation(), 16, 2);
                if (WorldGuardUtil.isPvPDisabled(pl.getLocation()) || WorldGuardUtil.getRegionName(pl.getLocation()).startsWith("spaw")) continue;
                b.spawnBoss(l);
                break;
            }
        }
    }
}

