package com.cosmicpvp.cosmicutils.utils;

import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.utils.CompilexUUIDFetcher;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.event.Listener;

public class CompilexUUIDFetcherListener
implements Listener {
    public static final String uuidSync_lilypadChannel = "uuidSync";


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

