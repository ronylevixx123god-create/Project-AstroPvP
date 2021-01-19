package me.ifamasssxd.cosmicbosses.tasks;

import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossDespawnEvent;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;

public class BossTickTask extends BukkitRunnable {
    @Override
    public void run() {
        for (final Boss boss : Boss.bosses.values()) {
            if (boss.isBossSpawned()) {
                boss.onTick();
            }
        }
    }
}
