/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.arkhamnetwork.Arkkit.patches.FactionTeleportSanityListener;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class FactionTeleportSanity
extends ArkPlugin {
    private final String flatfileLocation = "plugins/Arkkit/factionTeleportSanity.dat";
    protected static HashMap<String, Long> logoutTimes;

    public FactionTeleportSanity(Plugin p) {
        super(p);
    }

    @Override
    public void enable() {
        logoutTimes = new HashMap();
        this.loadLogoutTimes();
        this.registerListener(new FactionTeleportSanityListener());
    }

    @Override
    public void disable() {
        this.saveLogoutTimes();
    }

    private void loadLogoutTimes() {
        this.getClass();
        if (!new File("plugins/Arkkit/factionTeleportSanity.dat").exists()) {
            try {
                this.getClass();
                new File("plugins/Arkkit/factionTeleportSanity.dat").createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.getClass();
            BufferedReader reader = new BufferedReader(new FileReader(new File("plugins/Arkkit/factionTeleportSanity.dat")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.length() <= 0) continue;
                try {
                    if (!line.contains(",")) continue;
                    String[] s = line.split(",");
                    logoutTimes.put(s[0], Long.parseLong(s[1]));
                }
                catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
            }
            reader.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void saveLogoutTimes() {
        try {
            this.getClass();
            new File("plugins/Arkkit/factionTeleportSanity.dat").delete();
            this.getClass();
            BufferedWriter out = new BufferedWriter(new FileWriter("plugins/Arkkit/factionTeleportSanity.dat"));
            for (Map.Entry<String, Long> data : logoutTimes.entrySet()) {
                out.write(data.getKey() + "," + data.getValue() + "\n");
            }
            out.close();
            Bukkit.getLogger().info("[Arkkit (FactionTeleportSanity)] Saved " + logoutTimes.size() + " logoutTimes to disk.");
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
}

