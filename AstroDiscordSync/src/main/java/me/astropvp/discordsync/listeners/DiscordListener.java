package me.astropvp.discordsync.listeners;

import me.astropvp.discordsync.utils.CC;
import me.astropvp.discordsync.DiscordSyncPlugin;

import java.util.Objects;
import java.util.UUID;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DiscordListener implements EventListener {
    private final DiscordSyncPlugin plugin;

    public DiscordListener(DiscordSyncPlugin plugin) {
        this.plugin = plugin;
    }

    public void onEvent(GenericEvent gEvent) {
        if (gEvent instanceof PrivateMessageReceivedEvent) {
            PrivateMessageReceivedEvent event = (PrivateMessageReceivedEvent)gEvent;
            User user = event.getAuthor();
            String message = event.getMessage().getContentStripped();
            if (message.startsWith("!verify ")) {
                //int codeEntered = CommonsUtil.tryParseInt(message.split(" ")[1]);
                int codeEntered = Integer.parseInt(message.split(" ")[1]);
                if (this.plugin.getDatabaseManager().contains(user.getIdLong())) {
                    event.getChannel().sendMessage("Your discord account is already linked!").queue();
                } else if (!this.plugin.getSyncManager().isActiveCode(codeEntered)) {
                    event.getChannel().sendMessage("You have entered an invalid code!").queue();
                } else {
                    UUID playerId = (UUID)this.plugin.getSyncManager().getActiveSyncMap().get(codeEntered);
                    Player player = Bukkit.getPlayer(playerId);
                    if (player == null) {
                        event.getChannel().sendMessage("That player isn't online anymore!").queue();
                    } else {
                        event.getChannel().sendMessage("Congrats! You have successfully linked your account to " + player.getName() + " on AstroPvP.").queue();
                        this.plugin.getJda().getGuildById("470324147411615744").getMember(user).modifyNickname(player.getName()).queue();
                        this.plugin.getJda().getGuildById("470324147411615744").addRoleToMember(user.getId(), Objects.requireNonNull(this.plugin.getJda().getGuildById("470324147411615744").getRoleById("980116575736696853"))).queue();

                        this.plugin.getSyncManager().getActiveSyncMap().remove(codeEntered, playerId);
                        player.sendMessage(CC.translate("&a&l(!)&a You have successfully linked your account to &f&n" + user.getAsTag() + "&a."));
                        player.playSound(player.getLocation(),Sound.ORB_PICKUP, 10, 10);
                        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, (Runnable)(() -> {
                            this.plugin.getDatabaseManager().insert(playerId, user.getIdLong());
                        }));
                        this.plugin.getChatListener().updateNitroBoosters();
                    }
                }
            }
        }
    }
}
