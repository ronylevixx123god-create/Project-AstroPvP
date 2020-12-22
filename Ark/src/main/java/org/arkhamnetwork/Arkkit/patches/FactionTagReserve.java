/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CommandSetTagOwner;
import org.arkhamnetwork.Arkkit.patches.FactionTagReserveListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class FactionTagReserve
extends ArkPlugin {
    protected static HashMap<String, UUID> reservedTags;

    public FactionTagReserve(Plugin p) {
        super(p);
    }

    @Override
    public void enable() throws IOException {
        super.enable();
        reservedTags = new HashMap();
        for (String s : Arkkit.get().getConfig().getStringList("settings.factionTagReserves.tags")) {
            reservedTags.put(s.split(":")[0].toLowerCase(), UUID.fromString(s.split(":")[1]));
        }
        Bukkit.getLogger().info("[Arkkit (FactionTagReserve)] Loaded " + reservedTags.size() + " reservedTags: " + reservedTags);
        this.registerCommand(new CommandSetTagOwner("settagowner", "/<command> [args]", "Sets who can claim the specified faction tag."));
        this.registerListener(new FactionTagReserveListener());
    }

    @Override
    public void disable() throws IOException {
        super.disable();
        ArrayList<String> L = new ArrayList<String>();
        for (Map.Entry<String, UUID> data : reservedTags.entrySet()) {
            L.add(data.getKey() + ":" + data.getValue().toString());
        }
        Arkkit.get().getConfig().set("settings.factionTagReserves.tags", L);
    }
}

