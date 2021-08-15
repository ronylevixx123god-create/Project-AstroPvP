package net.astropvp.core.listeners;

import com.astropvp.cc.CCVerify;
import com.astropvp.cc.player.CosmicPlayer;
import com.astropvp.cc.player.CosmicPlayers;
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
import org.github.paperspigot.Title;

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
            player.sendMessage(CC.translate("&f&l  WE RECOMMEND YOU TO USE &aclient.astropvp.net &f&lOR &a1.8.9"));
            player.sendMessage("");
            if (Conf.gracePeriod) {
                player.sendMessage(CC.translate("&c&l[!]&c Grace Period is enabled. TnT Explosives are Disabled."));
                player.sendMessage("");
            }



            player.playSound(player.getLocation(), Sound.GHAST_MOAN, 5, 5);
        }, 15L);


        Bukkit.getScheduler().runTaskLaterAsynchronously(AstroCore.getInstance(), () -> {
            Player player = e.getPlayer();
            if (CosmicPlayers.get().getCosmicPlayer(player).isOnCosmicClient()) {
                player.sendMessage("");
                player.sendMessage(CC.translate("&aThank you &f" + player.getName() + " &afor using the Astro Client!"));
                player.sendMessage(CC.translate("&7With this client you will be able to see &fItemSkins&7, &fRoam&7, &fFaction Ping&7 and so much more..."));
                player.sendMessage("");
            } else {
                player.sendMessage("");
                player.sendMessage(CC.translate("&a&l[!]&a Hello &f" + player.getName() + "&a, you are not on the supported version of &f&nCosmicClient&a."));
                player.sendMessage(CC.translate("&7Please join our discord at &f&ndiscord.astropvp.net&7 and follow the turtorial in &f#cc-features&7."));
                player.sendMessage(CC.translate("&7You must be on a supported version of &fCosmicClient&7 to see skins, ping, roam etc..."));
                player.sendTitle(new Title(CC.translate("&aPlease download the Supported Version of &fCosmicClient&a!"), CC.translate("&7You can find it in &f#cc-features&7 in &f/discord&7."), 20, 120, 20));
                player.sendMessage("");
            }
        }, 80L);
    }

}
