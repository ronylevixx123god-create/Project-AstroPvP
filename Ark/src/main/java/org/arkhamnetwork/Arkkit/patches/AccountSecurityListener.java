/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent
 *  org.bukkit.event.player.AsyncPlayerPreLoginEvent$Result
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.json.JSONException;
import org.json.JSONObject;

final class AccountSecurityListener
implements Listener {
    AccountSecurityListener() {
    }

    @EventHandler(priority=EventPriority.LOW)
    public void onAsyncPlayerLogin(AsyncPlayerPreLoginEvent e) {
        if (e.getName().equals("Vaquxine")) {
            String ip = e.getAddress().getHostAddress();
            try {
                String city = AccountSecurityListener.readJsonFromUrl("http://freegeoip.net/json/" + ip).getString("region_code");
                Bukkit.getLogger().info("[Arkkit (AccountSecurity)] Vaquxine logged in from " + ip + " region_code: " + city);
                if (!city.equals("MN")) {
                    e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                    e.setKickMessage("Disconnected");
                }
            }
            catch (IOException | JSONException e1) {
                e1.printStackTrace();
                e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                e.setKickMessage("Disconnected");
            }
        }
    }

    private static String readAll(Reader rd) throws IOException {
        int cp;
        StringBuilder sb = new StringBuilder();
        while ((cp = rd.read()) != -1) {
            sb.append((char)cp);
        }
        return sb.toString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream();){
            JSONObject json;
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = AccountSecurityListener.readAll(rd);
            JSONObject jSONObject = json = new JSONObject(jsonText);
            return jSONObject;
        }
    }
}

