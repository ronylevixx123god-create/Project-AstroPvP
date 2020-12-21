/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.locations;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.CommandBookUtil;
import com.sk89q.commandbook.locations.LocationManager;
import com.sk89q.commandbook.locations.LocationManagerFactory;
import com.sk89q.commandbook.locations.NamedLocation;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class FlatFileLocationsManager
implements LocationManager<NamedLocation> {
    private World castWorld;
    private final File file;
    private Map<String, NamedLocation> locations = new HashMap<String, NamedLocation>();
    private final Map<String, List<NamedLocation>> unloadedLocations = new HashMap<String, List<NamedLocation>>();
    private final String type;

    public FlatFileLocationsManager(File file, String type) {
        this.file = file;
        this.type = type;
    }

    @Override
    public void castWorld(World world) {
        this.castWorld = world;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void load() throws IOException {
        FileInputStream input = null;
        HashMap<String, NamedLocation> locs = new HashMap<String, NamedLocation>();
        if (this.file.getParentFile().mkdirs() && !this.file.exists()) {
            this.file.createNewFile();
        }
        try {
            String[] line;
            input = new FileInputStream(this.file);
            InputStreamReader streamReader = new InputStreamReader((InputStream)input, "utf-8");
            BufferedReader reader = new BufferedReader(streamReader);
            CSVReader csv = new CSVReader(reader);
            while ((line = csv.readNext()) != null) {
                if (line.length < 8) {
                    CommandBook.logger().warning(this.type + " data file has an invalid line with < 8 fields");
                    continue;
                }
                try {
                    String name = line[0].trim().replace(" ", "");
                    String worldName = line[1];
                    String creator = line[2];
                    double x = Double.parseDouble(line[3]);
                    double y = Double.parseDouble(line[4]);
                    double z = Double.parseDouble(line[5]);
                    float pitch = Float.parseFloat(line[6]);
                    float yaw = Float.parseFloat(line[7]);
                    World world = CommandBook.server().getWorld(worldName);
                    if (world != null && this.castWorld != null && !this.castWorld.equals(world)) continue;
                    Location loc = new Location(world, x, y, z, yaw, pitch);
                    NamedLocation warp = new NamedLocation(name, loc);
                    warp.setWorldName(worldName);
                    warp.setCreatorName(creator);
                    if (world == null) {
                        CommandBookUtil.getNestedList(this.unloadedLocations, worldName).add(warp);
                        continue;
                    }
                    locs.put(name.toLowerCase(), warp);
                }
                catch (NumberFormatException e) {
                    CommandBook.logger().warning(this.type + " data file has an invalid line with non-numeric numeric fields");
                }
            }
            this.locations = locs;
            if (this.castWorld != null) {
                CommandBook.logger().info(locs.size() + " " + this.type + "(s) loaded for " + this.castWorld.getName());
            } else {
                CommandBook.logger().info(locs.size() + " " + this.type + "(s) loaded");
            }
        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void save() throws IOException {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(this.file);
            OutputStreamWriter streamWriter = new OutputStreamWriter((OutputStream)output, "utf-8");
            BufferedWriter writer = new BufferedWriter(streamWriter);
            CSVWriter csv = new CSVWriter(writer);
            FlatFileLocationsManager flatFileLocationsManager = this;
            synchronized (flatFileLocationsManager) {
                HashSet<NamedLocation> toStore = new HashSet<NamedLocation>();
                for (List<NamedLocation> locList : this.unloadedLocations.values()) {
                    toStore.addAll(locList);
                }
                toStore.addAll(this.locations.values());
                for (NamedLocation warp : toStore) {
                    csv.writeNext(new String[]{warp.getName(), warp.getWorldName() != null ? warp.getWorldName() : warp.getLocation().getWorld().getName(), warp.getCreatorName(), String.valueOf(warp.getLocation().getX()), String.valueOf(warp.getLocation().getY()), String.valueOf(warp.getLocation().getZ()), String.valueOf(warp.getLocation().getPitch()), String.valueOf(warp.getLocation().getYaw())});
                }
            }
            csv.flush();
            csv.close();
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    @Override
    public void updateWorlds() {
        Iterator<Object> i = this.unloadedLocations.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<String, List<NamedLocation>> entry = i.next();
            World world = CommandBook.server().getWorld(entry.getKey());
            if (world == null) continue;
            i.remove();
            for (NamedLocation warp : entry.getValue()) {
                warp.getLocation().setWorld(world);
                this.locations.put(warp.getName().toLowerCase(), warp);
            }
        }
        i = this.locations.values().iterator();
        while (i.hasNext()) {
            NamedLocation loc = (NamedLocation)i.next();
            if (loc.getWorldName() == null || CommandBook.server().getWorld(loc.getWorldName()) != null) continue;
            i.remove();
            loc.getLocation().setWorld(null);
            CommandBookUtil.getNestedList(this.unloadedLocations, loc.getWorldName()).add(loc);
        }
    }

    @Override
    public NamedLocation get(String id) {
        return this.locations.get(id.toLowerCase());
    }

    @Override
    public boolean remove(String id) {
        return this.locations.remove(id.toLowerCase()) != null;
    }

    @Override
    public List<NamedLocation> getLocations() {
        return new ArrayList<NamedLocation>(this.locations.values());
    }

    @Override
    public NamedLocation create(String id, Location loc, Player player) {
        id = id.trim();
        NamedLocation warp = new NamedLocation(id, loc);
        this.locations.put(id.toLowerCase(), warp);
        if (player != null) {
            warp.setCreatorName(player.getName());
        } else {
            warp.setCreatorName("");
        }
        return warp;
    }

    public static class LocationsFactory
    implements LocationManagerFactory<LocationManager<NamedLocation>> {
        private final File rootDir;
        private final String type;

        public LocationsFactory(File rootDir, String type) {
            this.rootDir = rootDir;
            this.type = type;
        }

        @Override
        public LocationManager<NamedLocation> createManager() {
            return new FlatFileLocationsManager(new File(this.rootDir, this.type.toLowerCase() + ".csv"), this.type);
        }

        @Override
        public LocationManager<NamedLocation> createManager(World castWorld) {
            return new FlatFileLocationsManager(new File(this.rootDir, this.type.toLowerCase() + File.separator + castWorld.getName() + ".csv"), this.type);
        }
    }
}

