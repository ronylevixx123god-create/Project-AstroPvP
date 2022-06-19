package me.astropvp.astroscoreboard;

import com.astropvp.cc.player.CosmicPlayers;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.titles.PlayerTitleAPI;
import com.massivecraft.factions.FPlayer;
import me.astropvp.astroscoreboard.board.BoardAdapter;
import me.astropvp.astroscoreboard.board.Scoreboard;
import me.astropvp.astroscoreboard.listeners.ListenerSB;
import me.astropvp.astroscoreboard.listeners.PlayerListener;
import me.astropvp.astroscoreboard.manager.BoardManager;
import me.astropvp.astroscoreboard.manager.PlayerDisplayManager;
import me.astropvp.astroscoreboard.manager.TabListManager;
import me.astropvp.proxypach.ProxyPatch;
import me.neznamy.tab.api.TABAPI;
import org.arkhamnetwork.Arkkit.utils.EssentialsUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class AstroScoreboardPlugin extends JavaPlugin {

    private static AstroScoreboardPlugin astroScoreboardPlugin;
    public static BoardManager boardManager;
    public static final DecimalFormat df = new DecimalFormat("#.#");


    public static void registerListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, astroScoreboardPlugin);
    }

    public BoardManager getBoardManager() {
        return boardManager;
    }

    @Override
    public void onEnable() {
        astroScoreboardPlugin = this;
        registerCommands();
        registerListeners();
        getInstance().setBoardManager(new BoardManager((BoardAdapter) new Scoreboard()));
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
        AstroScoreboardPlugin.boardManager = boardManager;
        long interval = AstroScoreboardPlugin.boardManager.getAdapter().getInterval();
        getServer().getScheduler().runTaskTimerAsynchronously((Plugin) this, (Runnable) AstroScoreboardPlugin.boardManager, 0L, interval);
    }


    public static AstroScoreboardPlugin getInstance() {
        return astroScoreboardPlugin;
    }

}
