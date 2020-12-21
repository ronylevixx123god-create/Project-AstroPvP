/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  lilypad.client.connect.api.event.EventListener
 *  lilypad.client.connect.api.event.MessageEvent
 *  lilypad.client.connect.api.request.Request
 *  lilypad.client.connect.api.request.RequestException
 *  lilypad.client.connect.api.request.impl.MessageRequest
 */
package com.cosmicpvp.cosmicutils.utils;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.utils.CompilexUUIDFetcher;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.MessageRequest;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class CompilexUUIDFetcherListener
implements Listener {
    public static final String uuidSync_lilypadChannel = "uuidSync";

    @EventListener
    public void onLilypadMessageRecieved(MessageEvent event) {
        if (event.getChannel().equals(uuidSync_lilypadChannel)) {
            try {
                String[] uuidAndName = event.getMessageAsString().split(":");
                UUID uuid = UUID.fromString(uuidAndName[0]);
                String name = uuidAndName[1];
                UUID current = CompilexUUIDFetcher.getCachedUUIDOf(name, false);
                if (current == null || !current.equals(uuid)) {
                    CompilexUUIDFetcher.cacheUUID(name, uuid);
                    Bukkit.getLogger().info("[CompilexUUIDFetcher] Updated UUID of " + name + " to " + uuid + " from " + event.getSender());
                }
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void syncPlayerUUID(String name, UUID uuid) {
        CompilexUUIDFetcherListener.requestAll(uuidSync_lilypadChannel, uuid.toString() + ":" + name);
    }

    private static void requestAll(String channel, String message) {
        try {
            MessageRequest request = new MessageRequest(new ArrayList(), channel, message);
            CosmicUtils.get().getConnect().request((Request)request);
        }
        catch (UnsupportedEncodingException | RequestException e) {
            e.printStackTrace();
        }
    }
}

