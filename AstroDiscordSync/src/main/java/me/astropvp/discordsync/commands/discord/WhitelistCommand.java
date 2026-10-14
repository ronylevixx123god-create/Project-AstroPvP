package me.astropvp.discordsync.commands.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.events.GenericEvent;
import org.bukkit.Bukkit;

public class WhitelistCommand implements EventListener {

    private final me.astropvp.discordsync.DiscordSyncPlugin plugin;
    public WhitelistCommand(me.astropvp.discordsync.DiscordSyncPlugin plugin) { this.plugin = plugin; }

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof MessageReceivedEvent) {
            onMessageReceived((MessageReceivedEvent) event);
        }
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (!message.equalsIgnoreCase("!whitelist on") && !message.equalsIgnoreCase("!whitelist off")) return;
        if (!event.isFromGuild() || !event.getGuild().getId().equals(plugin.getConfig().getString("guild-id"))) return;
        // Require the configured staff role in the configured guild.
        if (event.getMember() == null || event.getMember().getRoles().stream().noneMatch(role -> role.getId().equals(plugin.getConfig().getString("whitelist-role-id")))) {
            event.getChannel().sendMessage("You do not have permission to use this command.").queue();
            return;
        }

        if (message.equalsIgnoreCase("!whitelist on")) {
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getServer().setWhitelist(true));
            event.getChannel().sendMessage("Whitelist is now enabled!").queue();
        } else if (message.equalsIgnoreCase("!whitelist off")) {
            Bukkit.getScheduler().runTask(plugin, () -> Bukkit.getServer().setWhitelist(false));
            event.getChannel().sendMessage("Whitelist is now disabled!").queue();
        }
    }
}

