package com.massivecraft.factions;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class FactionWrappers {
    public static HashMap<String, FactionWrapper> factionWrappers = new HashMap<>();

    P p = P.getP();
    private static YamlConfiguration config;
    private static File file;
    private static Random random = new Random();

    public static void removeLocation(FactionWrapper wrapper, FLocation fl) {
        String key = fl.getWorldName() + ":" + fl.getX() + ":" + fl.getZ();
        config.set("factionwrappers." + wrapper.getFactionId() + ".chunkClaims." + key, null);
    }

    public static void loadFactionWrappers() {
        try {
            file = new File(P.p.getDataFolder(), "factionWrappers.yml");
            if (!file.exists())
                file.createNewFile();
            config = YamlConfiguration.loadConfiguration(file);
            if (config.contains("factionwrappers"))
                for (String id : config.getConfigurationSection("factionwrappers").getKeys(false)) {
                    ConcurrentHashMap<FLocation, Set<String>> set = new ConcurrentHashMap<>();
                    ConcurrentHashMap<String, Location> allyWarps = new ConcurrentHashMap<>();
                    ConcurrentHashMap<String, Location> allAllyWarps = new ConcurrentHashMap<>();
                    if (config.contains("factionwrappers." + id + ".chunkClaims"))
                        for (String chunkClaims : config.getConfigurationSection("factionwrappers." + id + ".chunkClaims").getKeys(false)) {
                            String[] args = chunkClaims.split(":");
                            FLocation fl = new FLocation(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                            Set<String> locs = new HashSet<>();
                            for (String access : config.getStringList("factionwrappers." + id + ".chunkClaims." + chunkClaims))
                                locs.add(access);
                            set.put(fl, locs);
                        }
                    for (String s : config.getStringList("factionwrappers." + id + ".allyWarps")) {
                        String ally = s.split(",")[0];
                        Location l = getLocationFromString(s.split(",")[1].split(":"));
                        allyWarps.put(ally.toLowerCase(), l);
                    }
                    for (String s : config.getStringList("factionwrappers." + id + ".allallywarps")) {
                        String name = s.contains("=") ? s.split("=")[0] : ("none" + random.nextInt(100000));
                        Location l = getLocationFromString(s.contains("=") ? s.split("=")[1].split(":") : s.split(":"));
                        allAllyWarps.put(name.toLowerCase(), l);
                    }
                    FactionWrapper wrapper = new FactionWrapper();
                    wrapper.setFactionId(id);
                    wrapper.setAllyWarps(allyWarps);
                    wrapper.setAllAllyWarps(allAllyWarps);
                    wrapper.setChunkClaims(set);
                    factionWrappers.put(id, wrapper);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Location getLocationFromString(String[] s) {
        Location loc = new Location(Bukkit.getWorld(s[0]), Double.valueOf(s[1]).doubleValue(), Double.valueOf(s[2]).doubleValue(), Double.valueOf(s[3]).doubleValue());
        loc.setYaw(Float.valueOf(s[4]).floatValue());
        loc.setPitch(Float.valueOf(s[5]).floatValue());
        return loc;
    }

    public static void saveToDisc() {
        for (Map.Entry<String, FactionWrapper> wrapper : factionWrappers.entrySet()) {
            for (Map.Entry<FLocation, Set<String>> entry : wrapper.getValue().chunkClaims.entrySet()) {
                List<String> list = Lists.newArrayList(entry.getValue());
                String key = entry.getKey().getWorldName() + ":" + entry.getKey().getX() + ":" + entry.getKey().getZ();
                config.set("factionwrappers." + wrapper.getKey() + ".chunkClaims." + key, list);
            }
            List<String> locs = new ArrayList<>();
            List<String> allAllies = new ArrayList<>();
            for (Map.Entry<String, Location> entry : wrapper.getValue().getAllyWarps().entrySet()) {
                Location l = entry.getValue();
                String s = entry.getKey() + "," + l.getWorld().getName() + ":" + l.getX() + ":" + l.getY() + ":" + l.getZ() + ":" + l.getYaw() + ":" + l.getPitch();
                locs.add(s);
            }
            for (Map.Entry<String, Location> entry : wrapper.getValue().getAllAllyWarps().entrySet()) {
                Location l = entry.getValue();
                String name = entry.getKey();
                allAllies.add(name + "=" + l.getWorld().getName() + ":" + l.getX() + ":" + l.getY() + ":" + l.getZ() + ":" + l.getYaw() + ":" + l.getPitch());
            }
            config.set("factionwrappers." + wrapper.getKey() + ".allallywarps", allAllies);
            config.set("factionwrappers." + wrapper.getKey() + ".allyWarps", locs);
        }
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FactionWrapper get(Faction f) {
        return get(f.getId());
    }

    public static FactionWrapper get(String id) {
        if (factionWrappers.containsKey(id))
            return factionWrappers.get(id);
        FactionWrapper wrapper = new FactionWrapper();
        wrapper.setFactionId(id);
        factionWrappers.put(id, wrapper);
        return wrapper;
    }
}
