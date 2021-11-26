package com.cosmicpvp.titles.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.titles.PlayerTitleAPI;
import net.astropvp.core.AstroCore;
import org.bukkit.ChatColor;
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

        if (AstroCore.getInstance().getCachedColors().containsKey(event.getPlayer())) {
            String message = event.getMessage();
            message = (event.getPlayer().isOp() ? ChatColor.DARK_RED : AstroCore.getInstance().getCachedColors().get(event.getPlayer())) + message;
            message = message.replaceAll(" ", " " + (event.getPlayer().isOp() ? ChatColor.DARK_RED : AstroCore.getInstance().getCachedColors().get(event.getPlayer())));
            event.setMessage(message);
        }
        event.setFormat(frm);

    }
}



