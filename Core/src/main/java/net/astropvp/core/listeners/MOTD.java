package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.AstroCore;
import net.astropvp.core.utils.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MOTD implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Bukkit.getScheduler().runTaskLater(AstroCore.getInstance(), () -> {
            Player player = e.getPlayer();
            for (int i = 0; i < 120; i++) {
                player.sendMessage(CC.translate("&c "));
            }
            player.sendMessage(ChatManager.translate(CC.translate("&a&lASTRO&e&lPVP &f- &7Factions")));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(ChatManager.translate(CC.translate("&aDiscord: &f&ndiscord.astropvp.net")));
            player.sendMessage(ChatManager.translate(CC.translate("&aStore: &f&nbuy.astropvp.net")));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&eRecommended Version: &f&nAstro Client (1.8.8)"));
            player.sendMessage(CC.translate("&c "));
            player.playSound(player.getLocation(), Sound.GHAST_MOAN, 5, 5);
            sendBeta(player);
        }, 15L);
    }

    private void sendBeta(Player player) {
        player.sendMessage(CC.translate("&7"));
        player.sendMessage(CC.translate("&c<&c&l!&c> &eThis map is being ran as a &e&lBETA&e."));
        player.sendMessage(CC.translate("&c<&c&l!&c> &7Report all bugs or issues in our discord: &fdiscord.astropvp.net&7."));
        player.sendMessage(CC.translate("&7"));
    }
}
