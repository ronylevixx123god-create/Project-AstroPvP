/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 */
package org.arkhamnetwork.Arkkit.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class PistonManager
extends HashMap<String, HashSet<Location>> {
    private static final long serialVersionUID = -6754405085225360026L;

    public void addPiston(Block b) {
        Chunk c = b.getChunk();
        int cx = c.getX();
        int cz = c.getZ();
        if (this.containsKey(cx + "," + cz)) {
            HashSet hloc = (HashSet)this.get(cx + "," + cz);
            hloc.add(b.getLocation());
            this.put(cx + "," + cz, hloc);
        } else {
            HashSet<Location> hloc = new HashSet<Location>(Arrays.asList(new Location[]{b.getLocation()}));
            this.put(cx + "," + cz, hloc);
        }
    }

    public void removePiston(Block b) {
        Chunk c = b.getChunk();
        int cx = c.getX();
        int cz = c.getZ();
        if (!this.containsKey(cx + "," + cz)) {
            return;
        }
        HashSet hloc = (HashSet)this.get(cx + "," + cz);
        hloc.remove((Object)b.getLocation());
        this.put(cx + "," + cz, hloc);
    }

    public boolean containsPiston(Block b) {
        Chunk c = b.getChunk();
        int cx = c.getX();
        int cz = c.getZ();
        if (this.containsKey(cx + "," + cz)) {
            return ((HashSet)this.get(cx + "," + cz)).contains((Object)b.getLocation());
        }
        return false;
    }

    public int getPistonCount(Chunk c) {
        int cx = c.getX();
        int cz = c.getZ();
        if (this.containsKey(cx + "," + cz)) {
            return ((HashSet)this.get(cx + "," + cz)).size();
        }
        return 0;
    }

    public boolean isAllowedPiston(Block b) {
        return this.containsPiston(b);
    }
}

