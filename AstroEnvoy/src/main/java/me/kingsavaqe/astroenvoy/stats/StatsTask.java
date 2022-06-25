package me.kingsavaqe.astroenvoy.stats;


import org.bukkit.scheduler.BukkitRunnable;

public class StatsTask extends BukkitRunnable {
    private StatsManager statsManager;

    public StatsTask(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    public void run() {
        this.statsManager.clearStats();
        this.statsManager.prepareStats();
    }
}
