package me.astropvp.itemflip;

import me.astropvp.itemflip.commands.CommandItemFlip;
import me.astropvp.itemflip.data.ItemFlipData;
import me.astropvp.itemflip.data.ItemFlipPlayerData;
import me.astropvp.itemflip.listeners.ItemFlipLogicListener;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class AstroItemFlipPlugin
        extends JavaPlugin {
    public void onEnable() {
        GalacticItemFlipPlugin = this;
        registerListeners();
        registerCommands();
    }

    private static AstroItemFlipPlugin GalacticItemFlipPlugin;

    public void onDisable() {
        for (int i = ItemFlipData.getFlipData().size() - 1; i >= 0; i--) {
            ItemFlipData data = ItemFlipData.getFlipData().get(i);
            if (((ItemFlipPlayerData) data.getData().get(0)).getPlayer().isOnline()) {
                ((ItemFlipPlayerData) data.getData().get(0)).getPlayer().closeInventory();
            }
            if (((ItemFlipPlayerData) data.getData().get(1)).getPlayer().isOnline()) {
                ((ItemFlipPlayerData) data.getData().get(1)).getPlayer().closeInventory();
            }
            data.remove();
        }
        GalacticItemFlipPlugin = null;
    }

    private void registerListeners() {
        registerListener((Listener) new ItemFlipLogicListener());
    }

    private void registerCommands() {
        getCommand("itemflip").setExecutor((CommandExecutor) new CommandItemFlip());
    }

    public static AstroItemFlipPlugin getInstance() {
        return GalacticItemFlipPlugin;
    }

    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, (Plugin) GalacticItemFlipPlugin);
    }
}
