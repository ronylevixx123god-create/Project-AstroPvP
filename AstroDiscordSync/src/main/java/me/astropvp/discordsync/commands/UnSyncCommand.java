package me.astropvp.discordsync.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;

import me.astropvp.discordsync.DiscordSyncPlugin;
import me.astropvp.discordsync.managers.SyncManager;
import me.astropvp.discordsync.utils.CC;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.bukkit.Bukkit;
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
    public void onCommand(Player player) {
        if (!this.plugin.getDatabaseManager().isConnected()) {
            player.sendMessage(CC.translate("&c&l(!)&c Account Sync is currently unavailable right now, please try again in a few."));
        } else if (!this.syncManager.isAccountLinked(player)) {
            player.sendMessage((String) CC.translate("&c&l(!) &cYour account is not synced!"));
            player.sendMessage((String) CC.translate("&7Sync using &f/sync&7!"));
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, (Runnable) (() -> {
                this.plugin.getDatabaseManager().remove(player.getUniqueId());
            }));
            player.sendMessage((String) CC.translate("&a&l(!) &fYou have &asuccessfully &funsync'ed your account!"));


            if (syncManager.isAccountLinked(player)) {
                this.plugin.getJda().getGuildById("470324147411615744").removeRoleFromMember(this.syncManager.getLinkedUser(player).getId(), Objects.requireNonNull(this.plugin.getJda().getGuildById("470324147411615744").getRoleById("980116575736696853"))).queue();
            }
        }
    }
}
