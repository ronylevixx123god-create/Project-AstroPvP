/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  lilypad.client.connect.api.Connect
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.google.common.collect.Lists;
import java.util.HashSet;
import lilypad.client.connect.api.Connect;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.KitLegacyListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class KitLegacy
extends ArkPlugin {
    protected static Connect lilypadConnect;
    protected static HashSet<String> redeemedUUIDs;
    protected static String kitLegacy_lilypadChannel;
    private static KitLegacyListener kitLegacyListener;

    public KitLegacy(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        if (Arkkit.get().getConfig().contains("settings.kitLegacy.redeemedUUIDs")) {
            for (String uuid : Arkkit.get().getConfig().getStringList("settings.kitLegacy.redeemedUUIDs")) {
                redeemedUUIDs.add(uuid);
            }
            Bukkit.getLogger().info("[Arkkit (KitLegacy)] Loaded " + redeemedUUIDs.size() + " /kit legacy redeemedUUIDs");
        }
        lilypadConnect = (Connect)Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
        kitLegacyListener = new KitLegacyListener();
        this.registerListener(kitLegacyListener);
        lilypadConnect.registerEvents((Object)kitLegacyListener);
    }

    @Override
    public void disable() {
        lilypadConnect.unregisterEvents((Object)kitLegacyListener);
        Arkkit.get().getConfig().set("settings.kitLegacy.redeemedUUIDs", (Object)Lists.newArrayList(redeemedUUIDs));
        Arkkit.get().saveConfig();
    }

    static {
        redeemedUUIDs = new HashSet();
        kitLegacy_lilypadChannel = "kitLegacy";
    }
}

