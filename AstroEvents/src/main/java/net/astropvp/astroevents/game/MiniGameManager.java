package net.astropvp.astroevents.game;

import lombok.Getter;
import lombok.Setter;
import net.astropvp.astroevents.AstroEvents;
import net.astropvp.astroevents.game.tasks.AutoMiniGameTask;
import net.astropvp.astroevents.utils.Utils;
import org.bukkit.scheduler.BukkitRunnable;

public class MiniGameManager {

    @Getter
    @Setter
    private long lastMiniGameTime;

    private final MiniGameRegister miniGameRegister;
    private final AstroEvents plugin;
    private BukkitRunnable autoMiniGameTask;
    private MiniGameType previousMiniGame;

    public MiniGameManager(AstroEvents plugin) {
        this.miniGameRegister = plugin.getMiniGameRegister();
        this.plugin = plugin;
    }

    public void startAutoMiniGameTask() {
        MiniGameType nextMiniGame = getNextMiniGame();
        this.autoMiniGameTask = new AutoMiniGameTask(miniGameRegister.getMiniGame(nextMiniGame), this);
        this.autoMiniGameTask.runTaskTimer(plugin, 144000L, 144000L);
        this.previousMiniGame = nextMiniGame;
        this.lastMiniGameTime = System.currentTimeMillis();
    }

    public MiniGameType getNextMiniGame() {
        return previousMiniGame == MiniGameType.PARKOUR ? MiniGameType.SUMO : MiniGameType.PARKOUR;
    }

    public void stopAutoMiniGameTask() {
        this.autoMiniGameTask.cancel();
    }

    public long getTimeInMsUntilNextMiniGame() {
        return (lastMiniGameTime + 7200000L) - System.currentTimeMillis();
    }

    public String getTimeUntilNextMiniGame() {
        return Utils.getSecondsAsString(this.getTimeInMsUntilNextMiniGame() / 1000);
    }
}
