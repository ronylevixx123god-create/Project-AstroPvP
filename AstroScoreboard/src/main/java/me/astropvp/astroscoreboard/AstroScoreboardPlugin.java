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

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () -> {
            try {

                String tps = df.format(EssentialsUtils.getEssentials().getTimer().getAverageTPS());
                int players = Bukkit.getOnlinePlayers().size();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (ProxyPatch.playerNames.contains(player.getName())) continue;
                    TabListManager.sendTablist(player,
                            CC.translate("\n&e&l●&6&l●&f&l● &f[ &6&lAstro&f&lPvP &f] &f&l●&6&l●&e&l●\n&7play.astropvp.net\n"),
                            CC.translate("\n&6  Factions &e// &d" + PlayerListener.getPing(player) + "ms &e// &a" + tps + " &7&oTPS &8➥ &f&ohttps://astropvp.net  \n" +
                                    "&e" + players + " &fOnline &8➥ &f&ohttps://buy.astropvp.net\n"));
                }
            } catch (Exception ignored) {
            }
        }, 100L, 100L);

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
