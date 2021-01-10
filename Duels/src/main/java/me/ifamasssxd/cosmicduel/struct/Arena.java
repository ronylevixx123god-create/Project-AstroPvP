/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 */
package me.ifamasssxd.cosmicduel.struct;

import me.ifamasssxd.cosmicduel.CosmicDuel;
import me.ifamasssxd.cosmicduel.utils.LocationUtils;
import me.ifamasssxd.cosmicduel.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    public static List<Arena> arenas = new ArrayList<Arena>();
    String description;
    String region;
    String name;
    String color = ChatColor.YELLOW.toString();
    public List<Location> spawnLocations = new ArrayList<Location>();
    int currentSpawnIndex = 0;
    Random rand = new Random();
    Material icon = Material.PAPER;
    boolean rankedArena = false;
    private static String duelWorldName;

    public Arena(String name, String description, String region) {
        this.name = name;
        this.region = region;
        this.description = description;
    }

    public static String getDuelWorldName() {
        if (duelWorldName == null) {
            if (arenas.size() == 0) {
                return "world_duels2";
            }
            duelWorldName = arenas.get(0).getSpawnLocations().get(0).getWorld().getName();
        }
        return duelWorldName;
    }

    public void setSpawnLocations(List<String> serialized) {
        for (String s : serialized) {
            this.spawnLocations.add(LocationUtils.getLocationFromString(s, true));
        }
        if (this.spawnLocations.isEmpty()) {
            Bukkit.getLogger().severe("There were no locations saved for " + this.name);
        }
    }

    public void setSpawns(List<Location> locations) {
        this.spawnLocations = locations;
        if (this.spawnLocations.isEmpty()) {
            Bukkit.getLogger().severe("There were no locations saved for " + this.name);
        }
    }

    public boolean isPlayerInside(Player p) {
        return WorldGuardUtils.getRegion(p.getLocation()).getId().contains(this.getRegion());
    }

    public Location getSpawnLocation(boolean random) {
        if (random) {
            return this.spawnLocations.get(this.rand.nextInt(this.spawnLocations.size()));
        }
        if (this.currentSpawnIndex >= this.spawnLocations.size()) {
            this.currentSpawnIndex = 0;
        }
        return this.spawnLocations.get(this.currentSpawnIndex++);
    }

    public String getName() {
        return this.getColor() + this.name;
    }

    public static Arena getArena(String name) {
        if (name.contains(" Arena")) {
            name = name.split(" Arena")[0];
        }
        for (Arena arena : arenas) {
            if (!ChatColor.stripColor((String)arena.getName()).equalsIgnoreCase(ChatColor.stripColor((String)name))) continue;
            return arena;
        }
        return null;
    }

    public static Arena getArenaFromRegion(String name) {
        for (Arena arena : arenas) {
            if (!arena.getRegion().equals(name)) continue;
            return arena;
        }
        return null;
    }

    public void saveSpawns() {
        ArrayList<String> locs = new ArrayList<String>();
        for (Location l : this.spawnLocations) {
            locs.add(LocationUtils.getStringFromLocation(l));
        }
        CosmicDuel.get().getConfig().set("Arenas." + ChatColor.stripColor((String)this.getName()) + ".Spawns", locs);
        CosmicDuel.get().getConfig().set("Arenas." + ChatColor.stripColor((String)this.getName()) + ".Ranked", (Object)this.isRankedArena());
        CosmicDuel.get().saveConfig();
    }

    public String getDescription() {
        return this.description;
    }

    public String getRegion() {
        return this.region;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Location> getSpawnLocations() {
        return this.spawnLocations;
    }

    public Material getIcon() {
        return this.icon;
    }

    public void setIcon(Material icon) {
        this.icon = icon;
    }

    public boolean isRankedArena() {
        return this.rankedArena;
    }

    public void setRankedArena(boolean rankedArena) {
        this.rankedArena = rankedArena;
    }
}

