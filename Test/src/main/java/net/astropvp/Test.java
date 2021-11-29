package net.astropvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test extends JavaPlugin implements Runnable, Listener {
    private List<String> playerNames;

    private int playerCount;

    private int currentCount;

    public void onEnable() {
        this.playerNames = new ArrayList<>();
        this.playerCount = 0;
        this.currentCount = 0;
        this.loadConfig();
        Bukkit.getScheduler().runTaskAsynchronously(this, this);
    }

    public void onDisable() {
        getConfig().set("player-count", this.playerCount);
    }



    private void loadConfig() {
        File file = new File("plugins/Test/config.yml");
        if(!file.exists())
            saveDefaultConfig();
        this.playerCount = this.getConfig().getInt("player-count");
    }


    public void run() {
        if(this.playerCount >= this.currentCount)
            return;
        if(this.playerNames.isEmpty())
            return;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spoof add " + this.playerNames.get(0));
        this.playerNames.remove(0);
    }

    private void loadPlayers() {
        //bry code here nigga
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String message = e.getMessage();
        if(!message.startsWith("/setplayers"))
            return;
        if(player.getName().equals("MattHawkk") || player.getName().equals("Bryside")) {
            e.setCancelled(true);
            int count = Integer.parseInt(message.split("")[1]);
            this.playerCount = count;
            player.sendMessage(ChatColor.GREEN + "Player count is now + " + count);
        }
    }
}
