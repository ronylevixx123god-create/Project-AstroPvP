package me.astropvp.discordsync;

import me.astropvp.discordsync.commands.SyncCommand;
import me.astropvp.discordsync.commands.UnSyncCommand;
import me.astropvp.discordsync.listeners.ChatListener;
import me.astropvp.discordsync.listeners.DiscordListener;
import me.astropvp.discordsync.managers.DatabaseManager;
import me.astropvp.discordsync.managers.SyncManager;
import org.bukkit.plugin.java.JavaPlugin;
import co.aikar.commands.CommandManager;
import co.aikar.commands.PaperCommandManager;

import java.sql.SQLException;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DiscordSyncPlugin extends JavaPlugin {
    private static final String TOKEN = "OTM1ODU1NzI3ODk2MDY4MTQ2.Gvat2I.aZvGBUsFLZ7MXLjjf9AhXmAb1NNG1ar9FpLhdo";
    private JDA jda;
    private SyncManager syncManager;
    private CommandManager commandManager;
    private DatabaseManager databaseManager;
    private DiscordListener discordListener;
    private ChatListener chatListener;

    public SyncManager getSyncManager() {
        return this.syncManager;
    }

    public ChatListener getChatListener() {
        return this.chatListener;
    }

    public JDA getJda() {
        return this.jda;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    public void onEnable() {
        Bukkit.getScheduler().runTaskAsynchronously(this, (Runnable)(() -> {
            try {
                this.jda = (new JDABuilder()).setToken(TOKEN).setActivity(Activity.playing("play.astropvp.net")).setStatus(OnlineStatus.ONLINE).build();
            } catch (LoginException var2) {
                Bukkit.getScheduler().runTask(this, (Runnable)(() -> {
                    Bukkit.getPluginManager().disablePlugin(this);
                    throw new RuntimeException("Shutting Discord Link plugin down, failed to initiate bot!", var2);
                }));
            }

            Bukkit.getScheduler().runTask(this, (Runnable)(() -> {
                this.commandManager = new PaperCommandManager(this);
                this.syncManager = new SyncManager(this);
                this.chatListener = new ChatListener(this);
                this.getServer().getPluginManager().registerEvents(this.chatListener, this);
                this.commandManager.registerCommand(new SyncCommand(this));
                this.commandManager.registerCommand(new UnSyncCommand(this));
                this.discordListener = new DiscordListener(this);
                this.jda.addEventListener(this.discordListener);
            }));
        }));
        this.databaseManager = new DatabaseManager(this);
        (new BukkitRunnable() {
            public void run() {
                try {
                    DiscordSyncPlugin.this.databaseManager.connect();
                    DiscordSyncPlugin.this.databaseManager.createTable();
                    Bukkit.getScheduler().runTaskLater(DiscordSyncPlugin.this, (Runnable)(() -> {
                        DiscordSyncPlugin.this.databaseManager.fetchMap();
                    }), 5L);
                    DiscordSyncPlugin.this.databaseManager.autoReconnect(850);
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            }
        }).runTaskAsynchronously(this);
    }

    public void onDisable() {
        this.jda.removeEventListener(this.discordListener);

        try {
            this.databaseManager.getConnection().close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public DiscordListener getDiscordListener() {
        return this.discordListener;
    }
}
