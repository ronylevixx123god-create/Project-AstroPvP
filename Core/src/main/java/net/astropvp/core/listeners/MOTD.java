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
            player.sendMessage(CC.translate("&f"));
            player.sendMessage(CC.translate("&f                   Welcome, &a" + player.getName() + "&f to &a&lAstro&e&lPvP&f!"));
            player.sendMessage(CC.translate(""));
            player.sendMessage(CC.translate("                     &a&lSHOP &fbuy.astropvp.net"));
            player.sendMessage(CC.translate("               &6&lDISCORD &fdiscord.astropvp.net"));
            player.sendMessage(CC.translate("                   &c&lRULES &frules.astropvp.net"));
            player.sendMessage("");
            player.sendMessage(CC.translate("&f&l    WE RECOMMEND YOU TO USE &acosmicclient.com &f&lOR &a1.8.9"));
            player.sendMessage("");
            if (Conf.gracePeriod) {
                player.sendMessage(CC.translate("&c&l[!]&c Grace Period is enabled. All explosives are disabled."));
                player.sendMessage("");
            }
            player.playSound(player.getLocation(), Sound.GHAST_MOAN, 5, 5);
        }, 15L);
    }

}
