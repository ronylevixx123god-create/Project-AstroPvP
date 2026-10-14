package me.astropvp.discordsync;

import me.astropvp.discordsync.commands.SyncCommand;
import me.astropvp.discordsync.commands.UnSyncCommand;
import me.astropvp.discordsync.commands.discord.WhitelistCommand;
import me.astropvp.discordsync.listeners.ChatListener;
import me.astropvp.discordsync.listeners.DiscordListener;
import me.astropvp.discordsync.managers.DatabaseManager;
import me.astropvp.discordsync.managers.SyncManager;
import me.mattstudios.mf.base.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;

public class DiscordSyncPlugin extends JavaPlugin {

    private static final String TOKEN = System.getenv("ASTRO_DISCORD_TOKEN");
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
        saveDefaultConfig();
        if (TOKEN == null || TOKEN.trim().isEmpty()) {
            getLogger().severe("ASTRO_DISCORD_TOKEN is not configured; Discord linking cannot start");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        for (String name : new String[]{"ASTRO_DISCORD_DB_URL", "ASTRO_DISCORD_DB_USER", "ASTRO_DISCORD_DB_PASSWORD"}) {
            if (System.getenv(name) == null || System.getenv(name).trim().isEmpty()) {
                getLogger().severe(name + " is not configured; Discord linking cannot start");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }
        this.databaseManager = new DatabaseManager(this);
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                this.databaseManager.connect();
                this.databaseManager.createTable();
                this.databaseManager.fetchMap();
                this.jda = (new JDABuilder()).setToken(TOKEN).setActivity(Activity.playing("play.astropvp.net")).setStatus(OnlineStatus.ONLINE).build();
                this.jda.awaitReady();
                if (this.jda.getGuildById(getConfig().getString("guild-id")) == null) {
                    this.jda.shutdown();
                    Bukkit.getScheduler().runTask(this, () -> {
                        getLogger().severe("Bot cannot access configured Discord guild; verify bot invitation and guild-id");
                        Bukkit.getPluginManager().disablePlugin(this);
                    });
                    return;
                }
            } catch (Exception var2) {
                if (var2 instanceof InterruptedException) Thread.currentThread().interrupt();
                Bukkit.getScheduler().runTask(this, () -> {
                    Bukkit.getPluginManager().disablePlugin(this);
                    getLogger().severe("Discord/database initialization failed (" + var2.getClass().getSimpleName() + "); verify configured credentials and access");
                });
                return;
            }

            Bukkit.getScheduler().runTask(this, () -> {
                if (!isEnabled()) return;
                this.databaseManager.autoReconnect(850);
                this.commandManager = new CommandManager(this);
                this.syncManager = new SyncManager(this);
                this.chatListener = new ChatListener(this);
                this.getServer().getPluginManager().registerEvents(this.chatListener, this);
                this.commandManager.register(new SyncCommand(this));
                this.commandManager.register(new UnSyncCommand(this));
                this.discordListener = new DiscordListener(this);
                this.jda.addEventListener(this.discordListener);
                this.jda.addEventListener(new WhitelistCommand(this));
            });
        });
    }

    public void onDisable() {
        if (this.jda != null) {
            if (this.discordListener != null) this.jda.removeEventListener(this.discordListener);
            this.jda.shutdown();
        }

        try {
            if (this.databaseManager != null && this.databaseManager.getConnection() != null) {
                this.databaseManager.getConnection().close();
            }
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
