package me.astropvp.discordsync.commands;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;


import java.util.ArrayList;
import java.util.List;

import me.astropvp.discordsync.DiscordSyncPlugin;
import me.astropvp.discordsync.managers.SyncManager;
import me.astropvp.discordsync.utils.CC;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SyncCommand  extends BaseCommand {
    private DiscordSyncPlugin plugin;
    private SyncManager syncManager;

    public SyncCommand(DiscordSyncPlugin plugin) {
        this.plugin = plugin;
        this.syncManager = plugin.getSyncManager();
    }

    @CommandAlias("sync|syncaccount|syncdiscord")
    public void onCommand(Player player) {
        if (!this.plugin.getDatabaseManager().isConnected()) {
            player.sendMessage(CC.translate("&c&l(!)&c Account Sync is currently unavailable right now, please try again in a few."));
        } else if (this.syncManager.isAccountLinked(player.getUniqueId())) {
            player.sendMessage(CC.translate("&c&l(!) &cYour account is already linked to &f&n" + this.syncManager.getLinkedUser(player.getUniqueId()).getAsTag() + "&c. Type &f/unsync&c to unsync your account."));
        } else {
            int newCode;
            if (this.syncManager.isCurrentlySyncing(player)) {
                newCode = this.syncManager.getActiveSyncCode(player);
                player.sendMessage(CC.translate("&a&l(!)&f Please send the &bAstro Sync Bot&f this message \"!verify " + newCode + "\"."));
            } else {
                newCode = this.syncManager.getRandomCode();
                this.syncManager.getActiveSyncMap().put(newCode, player.getUniqueId());
                List<String> messages = new ArrayList<>();

                messages.add("");
                messages.add("&b&lDISCORD ACCOUNT SYNC &8(Tutorial)");
                messages.add("");
                messages.add("&f* &7Join the astro /discord and find the &bAstro Sync Bot&7.");
                messages.add("&f* &7Send the bot this message: &b'!verify " + newCode + "'&7.");
                messages.add("");
                messages.add("&7Once you have followed these steps, your account should be linked!");
                messages.add("");

                for (String message : messages) {
                    player.sendMessage(CC.translate(message));
                }
            }
        }
    }

    @CommandAlias("updateboosters")
    @CommandPermission("admin")
    public void onUpdateBoosters(Player player) {
        this.plugin.getChatListener().updateNitroBoosters();
        player.sendMessage((String)CC.translate("&a&l(!) &aUpdate has occured!"));
    }

    @CommandAlias("checksync")
    @CommandPermission("admin")
    public void onCheck(CommandSender sender, OnlinePlayer target) {
        if (!this.plugin.getDatabaseManager().isConnected()) {
            sender.sendMessage(CC.translate("The DB isn't connected. You may use &7/forcerecon&c to force a re-connection."));
        } else if (!this.syncManager.isAccountLinked(target.getPlayer().getUniqueId())) {
            sender.sendMessage(CC.translate( "&cThat player isn't synced."));
        } else {
            User user = this.syncManager.getLinkedUser(target.getPlayer().getUniqueId());
            sender.sendMessage(CC.translate("&a" + target.getPlayer().getName() + "&f is synced with &a" + user.getAsTag() + "&f."));
        }
    }

    @CommandAlias("forcerecon")
    @CommandPermission("admin")
    public void onForceRecon(final CommandSender sender) {
        sender.sendMessage("Forcing database re-connection...");
        (new BukkitRunnable() {
            public void run() {
                try {
                    if (SyncCommand.this.plugin.getDatabaseManager().isConnected()) {
                        sender.sendMessage(ChatColor.AQUA + "DB is currently connected, closing the connection...");
                        SyncCommand.this.plugin.getDatabaseManager().getConnection().close();
                        sender.sendMessage(ChatColor.AQUA + "Disconnected from DB.");
                    } else {
                        sender.sendMessage(ChatColor.AQUA + "DB is currently NOT connected, opening new connection.");
                    }

                    sender.sendMessage(ChatColor.AQUA + "Opening a new connection.");
                    SyncCommand.this.plugin.getDatabaseManager().connect();
                    sender.sendMessage(ChatColor.AQUA + "New connection opened successfully.");
                } catch (Exception var2) {
                    var2.printStackTrace();
                    sender.sendMessage(ChatColor.AQUA + "Error in console.");
                }

                sender.sendMessage("Connection Status: " + SyncCommand.this.plugin.getDatabaseManager().isConnected());
            }
        }).runTaskAsynchronously(this.plugin);
    }
}
