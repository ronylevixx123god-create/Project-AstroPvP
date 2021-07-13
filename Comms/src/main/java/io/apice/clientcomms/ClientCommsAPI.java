package io.apice.clientcomms;

import io.apice.clientcomms.compat.FactionsHandler;

import java.util.UUID;

import org.bukkit.entity.Player;

public class ClientCommsAPI {
    public static void sendFocusedPlayer(Player to, UUID targetUUID) {
        if (to == null || !to.isOnline()) {
            return;
        }
        if (ClientComms.getServerType() != ServerType.COSMICPVP && ClientComms.getServerType() != ServerType.COSMICPRISONS && ClientComms.getServerType() != ServerType.COSMICPVP_HCF) {
            return;
        }
        if (targetUUID != null && !ClientComms.getRegisteredPlayers().contains(to)) {
            return;
        }
        if (targetUUID != null && !ClientComms.getRegisteredPlayers().containsKey((Object)to)) {
            return;
        }
        if (ClientComms.getServerType() == ServerType.COSMICPVP_HCF) {
            //Todo: CONAX: Using Factions>HCF
            ((FactionsHandler)ClientComms.getHandler()).sendFocusedPlayer(to, targetUUID);
        } else if (ClientComms.getServerType() == ServerType.COSMICPRISONS) {
            //Todo: CONAX: Using Factions>Prisons
            ((FactionsHandler)ClientComms.getHandler()).sendFocusedPlayer(to, targetUUID);
        } else {
            ((FactionsHandler)ClientComms.getHandler()).sendFocusedPlayer(to, targetUUID);
        }
    }

    public static void sendEvent(Player to, String name, EventType eventType, long start, long duration) {
        if (to == null || !to.isOnline()) {
            return;
        }
        ClientComms.getHandler().sendEvent(to, name, eventType, start, duration);
    }

    public static void messageAll(String message) {
        ClientComms.getRegisteredPlayers().keySet().forEach(player -> player.sendMessage(message));
    }
}

