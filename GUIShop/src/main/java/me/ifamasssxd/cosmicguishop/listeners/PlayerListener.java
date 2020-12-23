/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.event.player.PlayerInteractEntityEvent
 */
package me.ifamasssxd.cosmicguishop.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerListener
        implements Listener {
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().equals("/shop")) {
            event.setMessage(event.getMessage().replace("/shop", "/cshop"));
        }
    }

    @EventHandler
    public void onPlayerinteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player && ((Player) event.getRightClicked()).getName().equalsIgnoreCase("Cosmic Merchant")) {
            event.getPlayer().chat("/cshop");
        }
    }
}

