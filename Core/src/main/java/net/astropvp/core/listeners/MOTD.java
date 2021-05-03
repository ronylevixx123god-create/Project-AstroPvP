package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
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

        Bukkit.getScheduler().runTaskLaterAsynchronously(AstroCore.getInstance(), () -> {
            Player player = e.getPlayer();
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(ChatManager.translate(CC.translate("&a&lASTRO&e&lPVP &f- &7Factions")));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(ChatManager.translate(CC.translate("&aDiscord: &f&ndiscord.astropvp.net")));
            player.sendMessage(ChatManager.translate(CC.translate("&aStore: &f&nbuy.astropvp.net")));
            player.sendMessage(CC.translate("&c "));
            player.sendMessage(CC.translate("&eRecommended Version: &f&nCosmic Client (1.8.8)"));
            player.sendMessage(CC.translate("&c "));
            if (Conf.gracePeriod) {
                player.sendMessage(CC.translate("&c&l[!]&c Grace Period is enabled. All explosives are disabled."));
                player.sendMessage("");
            }
            player.playSound(player.getLocation(), Sound.GHAST_MOAN, 5, 5);
        }, 15L);
    }

}
