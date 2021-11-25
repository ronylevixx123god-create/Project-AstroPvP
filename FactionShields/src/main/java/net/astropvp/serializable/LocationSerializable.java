// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.io.Serializable;

public class LocationSerializable implements Serializable
{
    private transient Location location;
    private String world;
    private double x;
    private double y;
    private double z;
    
    public LocationSerializable(final Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }
    
    public Location getLocation() {
        if (this.location == null) {
            this.location = new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
        }
        return this.location;
    }
}
