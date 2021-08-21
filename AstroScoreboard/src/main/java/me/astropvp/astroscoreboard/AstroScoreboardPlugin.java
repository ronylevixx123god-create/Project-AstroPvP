package me.astropvp.astroscoreboard;

import com.astropvp.cc.player.CosmicPlayers;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.FPlayer;
import me.astropvp.astroscoreboard.board.BoardAdapter;
import me.astropvp.astroscoreboard.board.Scoreboard;
import me.astropvp.astroscoreboard.listeners.ListenerSB;
import me.astropvp.astroscoreboard.listeners.PlayerListener;
import me.astropvp.astroscoreboard.manager.BoardManager;
import me.astropvp.astroscoreboard.manager.PlayerDisplayManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AstroScoreboardPlugin extends JavaPlugin {

    private static AstroScoreboardPlugin astroScoreboardPlugin;
    public static BoardManager boardManager;

    public static void registerListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, astroScoreboardPlugin);
    }

    public BoardManager getBoardManager() {
        return this.boardManager;
    }

    @Override
    public void onEnable() {
        astroScoreboardPlugin = this;
        registerCommands();
        registerListeners();
        getInstance().setBoardManager(new BoardManager((BoardAdapter) new Scoreboard()));

        runTasks();
    }

    @Override
    public void onDisable() {
        astroScoreboardPlugin = null;
    }

    public void registerListeners() {
        registerListener(new PlayerListener());
        registerListener(new ListenerSB());
    }

    public void registerCommands() {

    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
        long interval = this.boardManager.getAdapter().getInterval();
        getServer().getScheduler().runTaskTimerAsynchronously((Plugin) this, (Runnable) this.boardManager, 0L, interval);
    }

    public void runTasks() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, (Runnable) new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    //FPlayer fplayer = FactionUtils.getFPlayer(player);
                    for (Player other : Bukkit.getOnlinePlayers()) {
                        try {
                            PlayerDisplayManager.setUserPrefix(player, other, AstroScoreboardAPI.getDefaultPrefix(player, other) + " ");
                            //PlayerDisplayManager.setUserSuffix(player, other, ((CosmicPlayers.get().getCosmicPlayer(player).isOnCosmicClient()) ? ChatColor.GREEN.toString() + ChatColor.BOLD + " ✓" : ChatColor.RED.toString() + ChatColor.BOLD + " ✗"));
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 20L, 20L));
    }

    public static AstroScoreboardPlugin getInstance() {
        return astroScoreboardPlugin;
    }

}
