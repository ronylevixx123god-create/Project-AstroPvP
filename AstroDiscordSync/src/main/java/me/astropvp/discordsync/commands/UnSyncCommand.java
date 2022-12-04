package me.astropvp.discordsync.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;

import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.astropvp.discordsync.DiscordSyncPlugin;
import me.astropvp.discordsync.managers.SyncManager;
import me.astropvp.discordsync.utils.CC;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class UnSyncCommand extends BaseCommand {
    private DiscordSyncPlugin plugin;
    private SyncManager syncManager;
    private static final long ASTRO_GUILD_ID = 470324147411615744L;
    private final Guild astroDiscord;
    public UnSyncCommand(DiscordSyncPlugin plugin) {
        this.plugin = plugin;
        this.syncManager = plugin.getSyncManager();
        this.astroDiscord = plugin.getJda().getGuildById(ASTRO_GUILD_ID);

    }

    @CommandAlias("unsync|unsyncaccount|unsyncdiscord")
    public void onCommand(Player player, String toUnsync) {
        if (!player.isOp()) {
            player.sendMessage(CC.translate("&c&l(!)&c Only administrators can unsync your account, open a #general-support ticket in the astropvp /discord!"));
            return;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(toUnsync);
        if (!this.plugin.getDatabaseManager().isConnected()) {
            player.sendMessage(CC.translate("&c&l(!)&c Account Sync is currently unavailable right now, please try again in a few."));
        } else if (!this.syncManager.isAccountLinked(offlinePlayer.getUniqueId())) {
            player.sendMessage((String) CC.translate("&c&l(!) &cTheir account is not synced!"));
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, (Runnable) (() -> {
                this.plugin.getDatabaseManager().remove(offlinePlayer.getUniqueId());
            }));
            player.sendMessage((String) CC.translate("&a&l(!) &fYou have &asuccessfully &funsync'ed " + offlinePlayer.getName() + "'s account"));
            if (offlinePlayer.isOnline()) {
                offlinePlayer.getPlayer().sendMessage(CC.translate("&c&l(!)&c Your account has been &funsync'ed &cby an administrator!"));
            }


            if (syncManager.isAccountLinked(offlinePlayer.getUniqueId())) {
                this.plugin.getJda().getGuildById("470324147411615744").removeRoleFromMember(this.syncManager.getLinkedUser(offlinePlayer.getUniqueId()).getId(), Objects.requireNonNull(this.plugin.getJda().getGuildById("470324147411615744").getRoleById("980116575736696853"))).queue();
            }
        }
    }
}
