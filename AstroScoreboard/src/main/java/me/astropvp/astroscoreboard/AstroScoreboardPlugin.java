package me.astropvp.astroscoreboard;


import me.astropvp.astroscoreboard.manager.BoardManager;
import me.neznamy.tab.api.TabAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class AstroScoreboardPlugin extends JavaPlugin {

    private static AstroScoreboardPlugin astroScoreboardPlugin;
    public static BoardManager boardManager;

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
        //registerListeners();
        //this.tabHook = new TabHook();
        //this.runTickMethods();

        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%player-vanished%", 50, player -> player.isVanished());

    }

    @Override
    public void onDisable() {
        astroScoreboardPlugin = null;
    }

    public void registerListeners() {
        //registerListener(new PlayerListener());
        //registerListener(new ListenerSB());
    }

    public void registerCommands() {

    }



    public static AstroScoreboardPlugin getInstance() {
        return astroScoreboardPlugin;
    }

}
