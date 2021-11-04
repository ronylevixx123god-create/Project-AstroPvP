package com.cosmicpvp.titles.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.titles.PlayerTitleAPI;
import net.astropvp.core.AstroCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onChatEvent(AsyncPlayerChatEvent event) {
        String title = "";
        if (PlayerTitleAPI.getEquippedTitle(event.getPlayer().getUniqueId()) != null)
            if (PlayerTitleAPI.getEquippedTitle(event.getPlayer().getUniqueId()).equals("none")) {
                title = "";
            } else {
                title = " &8[&a" + PlayerTitleAPI.getEquippedTitle(event.getPlayer().getUniqueId()) + "&8]";
            }
        String frm = event.getFormat().replace("ATITLE", CC.translate(title));

        /*if (AstroCore.getInstance().getCachedColors().containsKey(event.getPlayer())) {
            frm = frm.substring(frm.lastIndexOf(":") + 1);
            frm = CC.strip(frm);
            frm = AstroCore.getInstance().getCachedColors().get(event.getPlayer()) + frm;
        }*/
        event.setFormat(frm);
    }
}



