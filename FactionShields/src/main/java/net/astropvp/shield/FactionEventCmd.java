// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.shield;

import net.astropvp.FactionShieldsPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.Listener;

public class FactionEventCmd implements Listener
{
    public FactionEventCmd() {
        FactionShieldsPlugin.getInstance().registerListener((Listener)this);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onFactionShieldCmd(final PlayerCommandPreprocessEvent event) {
        final String[] args = event.getMessage().split(" ");
        if (args.length >= 2) {
            final Player player = event.getPlayer();
            if (args[0].equalsIgnoreCase("/f")) {
                if (args[1].equalsIgnoreCase("shield")) {
                    event.setCancelled(true);
                    if (args.length == 2) {
                        player.chat("/fs");
                    }
                    else if (args.length > 2) {
                        if (args[2].equalsIgnoreCase("reset") || args[2].equalsIgnoreCase("resetall") || args[2].equalsIgnoreCase("forcereset")) {
                            player.sendMessage("§cTry using: §7/fs " + args[2] + " §cinstead.");
                            return;
                        }
                        player.chat("/fs " + args[2]);
                    }
                }
                else if (args[1].equalsIgnoreCase("setregion")) {
                    event.setCancelled(true);
                    player.chat("/fs setregion");
                }
            }
        }
    }
}
