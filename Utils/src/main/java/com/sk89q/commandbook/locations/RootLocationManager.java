/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.commandbook.locations;

import com.sk89q.commandbook.CommandBook;
import com.sk89q.commandbook.locations.LocationManager;
import com.sk89q.commandbook.locations.LocationManagerFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class RootLocationManager<T> {
    private LocationManager<T> rootManager;
    private Map<String, LocationManager<T>> managers;
    private final LocationManagerFactory<LocationManager<T>> factory;
    private final boolean perWorld;

    public RootLocationManager(LocationManagerFactory<LocationManager<T>> factory, boolean perWorld) {
        this.factory = factory;
        this.perWorld = perWorld;
        if (perWorld) {
            this.managers = new HashMap<String, LocationManager<T>>();
        } else {
            this.rootManager = factory.createManager();
            try {
                this.rootManager.load();
            }
            catch (IOException e) {
                CommandBook.logger().warning("Failed to load warps: " + e.getMessage());
            }
        }
    }

    private LocationManager<T> getManager(World world) {
        if (!this.perWorld) {
            return this.rootManager;
        }
        LocationManager<T> manager = this.managers.get(world.getName());
        if (manager != null) {
            return manager;
        }
        manager = this.factory.createManager(world);
        manager.castWorld(world);
        try {
            manager.load();
        }
        catch (IOException e) {
            CommandBook.logger().warning("Failed to load warps for world " + world.getName() + ": " + e.getMessage());
        }
        this.managers.put(world.getName(), manager);
        return manager;
    }

    public T get(World world, String id) {
        return this.getManager(world).get(id);
    }

    public T create(String id, Location loc, Player player) {
        LocationManager<T> manager = this.getManager(loc.getWorld());
        T ret = manager.create(id, loc, player);
        this.save(manager);
        return ret;
    }

    public boolean remove(World world, String id) {
        LocationManager<T> manager = this.getManager(world);
        boolean ret = this.getManager(world).remove(id);
        this.save(manager);
        return ret;
    }

    private void save(LocationManager<T> manager) {
        try {
            manager.save();
        }
        catch (IOException e) {
            CommandBook.logger().warning("Failed to save warps: " + e.getMessage());
        }
    }

    public List<T> getLocations(World world) {
        return this.getManager(world).getLocations();
    }

    public boolean isPerWorld() {
        return this.perWorld;
    }

    public void updateWorlds(World world) {
        this.getManager(world).updateWorlds();
    }
}

