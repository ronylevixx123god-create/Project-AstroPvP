package me.kingsavaqe.astrotournaments.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TournamentListener implements Listener {
    public List<String> allowedCommands =  Arrays.asList("/msg", "/r", "/message", "/reply", "/tournament", "/tm", "/tournaments", "/f c f", "/f c p", "/f c t", "/f c m");
    //TODO: disable opening settings gui in tournament

    @EventHandler
    public void onCommand() {

    }

    @EventHandler
    public void onItemDrop() {

    }

    @EventHandler
    public void onTeleport() {

    }

    @EventHandler
    public void onInventoryOpen() {

    }
}
