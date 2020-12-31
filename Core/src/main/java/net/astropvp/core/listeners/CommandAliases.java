package net.astropvp.core.listeners;

import net.minecraft.util.com.google.common.util.concurrent.ServiceManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandAliases implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent e) {
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
        }
        if (cmd.equals("/tpyes")) {
            e.setMessage(e.getMessage().toLowerCase().replace(cmd, "/tpaccept"));
        }
        if (cmd.equals("/tpno")) {
            e.setMessage("/tpdeny");
        }
        if (cmd.equals("/f unclaim all")) {
            e.setMessage("/f unclaimall");
        }
        if ((cmd.equals("/tps") || cmd.equals("/ping") || cmd.equals("/eping") || cmd.equals("/pong") || cmd.equals("/epong"))) {
            e.setMessage("/lag");
        }
        if (cmd.equals("/craft")) {
            e.setMessage("/workbench");
        }
        if (cmd.equals("/elightning") || cmd.equals("/smite") || cmd.equals("/esmite") || cmd.equals("/strike") || cmd.equals("/estrike") || cmd.equals("/shock") || cmd.equals("/eshock") || cmd.equals("/thor") || cmd.equals("/ethor")) {
            e.setMessage("/lightning");
        }
        if (cmd.equals("/eat")) {
            e.setMessage("/feed");
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
