/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.massivecraft.factions.Faction
 *  org.bukkit.craftbukkit.libs.com.google.gson.Gson
 *  org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder
 *  org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken
 */
package com.compilex.CosmicOutposts.outpost.managers;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.compilex.CosmicOutposts.outpost.libs.OutpostCapTime;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.massivecraft.factions.Faction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private Map<String, OutpostCapTime> factionKothCapTimes = new HashMap<String, OutpostCapTime>();
    private File file;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public DataManager() {
        this.loadStoredTimers();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void loadStoredTimers() {
        try {
            long start = System.currentTimeMillis();
            File dir = CosmicKOTHPlugin.get_().getDataFolder();
            if (!dir.exists()) {
                dir.mkdir();
            }
            this.file = new File(dir, "kothCapTimes.json");
            if (!this.file.exists()) {
                this.file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(this.file));
            try {
                String line;
                StringBuilder build = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    build.append(line);
                }
                this.factionKothCapTimes = (Map)this.gson.fromJson(build.toString(), new TypeToken<HashMap<String, OutpostCapTime>>(){}.getType());
                if (this.factionKothCapTimes == null) {
                    this.factionKothCapTimes = new HashMap<String, OutpostCapTime>();
                }
                System.out.println("Took " + (System.currentTimeMillis() - start) + "ms to load " + this.factionKothCapTimes.size() + " homes.");
            }
            finally {
                if (Collections.singletonList(reader).get(0) != null) {
                    reader.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveStoredTimes() {
        try {
            String map = this.gson.toJson(this.factionKothCapTimes);
            FileWriter writer = new FileWriter(this.file);
            try {
                writer.write(map);
                writer.flush();
            }
            finally {
                if (Collections.singletonList(writer).get(0) != null) {
                    writer.close();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void incrementCapTime(Faction fac, OutpostTier tier, int amount) {
        OutpostCapTime cap = this.factionKothCapTimes.computeIfAbsent(fac.getId(), k -> new OutpostCapTime());
        Integer current = (Integer)cap.get((Object)tier);
        if (current == null) {
            cap.put(tier, amount);
        } else {
            cap.put(tier, current + amount);
        }
    }

    public OutpostCapTime getCapTimes(Faction faction) {
        return this.getCapTimes(faction.getId());
    }

    public OutpostCapTime getCapTimes(String id) {
        return this.factionKothCapTimes.get(id);
    }

    public Map<String, OutpostCapTime> getFactionKothCapTimes() {
        return this.factionKothCapTimes;
    }
}

