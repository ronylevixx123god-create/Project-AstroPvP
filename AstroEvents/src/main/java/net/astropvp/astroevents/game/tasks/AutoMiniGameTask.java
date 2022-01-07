package net.astropvp.astroevents.game.tasks;

import net.astropvp.astroevents.game.MiniGame;
import net.astropvp.astroevents.game.MiniGameManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoMiniGameTask extends BukkitRunnable {

    private final MiniGame<?> miniGame;
    private final MiniGameManager miniGameManager;

    public AutoMiniGameTask(MiniGame<?> miniGame, MiniGameManager miniGameManager) {
        this.miniGame = miniGame;
        this.miniGameManager = miniGameManager;
    }

    @Override
    public void run() {

        if (Bukkit.getOnlinePlayers().size() == 0) {
            Bukkit.getConsoleSender().sendMessage("&6[AstroEvents] Not enough online players to start the event");
            return;
        }

        this.miniGame.startMiniGameCountDown();
        this.miniGameManager.setLastMiniGameTime(System.currentTimeMillis());
    }
}