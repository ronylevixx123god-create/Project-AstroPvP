package net.astropvp.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandAliases implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase();
        if (cmd.equalsIgnoreCase("/f upgrades")) {
            e.setMessage("/f upgrade");
            return;
        }
        if (cmd.contains(" ")) {
            cmd = cmd.split(" ")[0];
        }
        if (cmd.equals("/masks")) {
            e.setMessage("/help masks");
            return;
        }
        if (cmd.equals("/tpyes")) {
            e.setMessage(e.getMessage().toLowerCase().replace(cmd, "/tpaccept"));
            return;
        }
        if (cmd.equals("/tpno")) {
            e.setMessage("/tpdeny");
            return;
        }

        if (cmd.equals("/f unclaim all")) {
            e.setMessage("/f unclaimall");
            return;
        }
        if (cmd.equals("/craft")) {
            e.setMessage("/workbench");
            return;
        }
        if (cmd.equals("/elightning") || cmd.equals("/smite") || cmd.equals("/esmite") || cmd.equals("/strike") || cmd.equals("/estrike") || cmd.equals("/shock") || cmd.equals("/eshock") || cmd.equals("/thor") || cmd.equals("/ethor")) {
            e.setMessage("/lightning");
            return;
        }
        if (cmd.equals("/eat")) {
            e.setMessage("/feed");
            return;
        }
    }

    @EventHandler
    public void onPlayerPreProcessCommand(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage();
        if (msg != null && !msg.isEmpty()) {
            msg = msg.toLowerCase();
            if (msg.startsWith("/lootbox"))
                e.setMessage(msg.replace("/lootbox", "/mc"));
        }
    }
}
