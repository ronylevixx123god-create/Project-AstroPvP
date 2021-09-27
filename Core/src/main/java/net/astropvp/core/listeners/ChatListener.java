package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.AstroCore;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if(!AstroCore.cachedColors.containsKey(e.getPlayer()))
            return;
        String s = e.getMessage().substring(e.getMessage().lastIndexOf(":") + 1);
        s = CC.strip(s);
        s = AstroCore.cachedColors.get(e.getPlayer()) + s;
        e.setMessage(s);
    }


}
