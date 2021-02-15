package com.cosmicpvp.titles.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.titles.PlayerTitleAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onChatEvent(AsyncPlayerChatEvent event) {

        String title = "";

        if (PlayerTitleAPI.getEquippedTitle(event.getPlayer().getUniqueId()) != null) {
            if (PlayerTitleAPI.getEquippedTitle(event.getPlayer().getUniqueId()).equals("none")) {
                title = "";
            } else {
                title = " &8[&6" + PlayerTitleAPI.getEquippedTitle(event.getPlayer().getUniqueId()) + "&8]";
            }
        }

        String frm = event.getFormat().replace("ATITLE", CC.translate(title));
        event.setFormat(frm);
    }
}
