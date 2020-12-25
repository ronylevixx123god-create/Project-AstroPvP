/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 */
package me.ifamasssxd.cosmicbosses.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockModifier {
    private HashMap<BossAbility, List<Location>> toRemove = new HashMap();
    private HashMap<BossAbility, HashMap<Location, NewBlockData>> toReplace = new HashMap();
    Random random = new Random();

    public boolean containsLocation(Location l) {
        for (List<Location> list : this.toRemove.values()) {
            if (!list.contains((Object)l)) continue;
            return true;
        }
        for (HashMap hashMap : this.toReplace.values()) {
            if (!hashMap.containsKey((Object)l)) continue;
            return true;
        }
        return false;
    }

    public boolean containsLocation(BossAbility ability, Location l) {
        if (this.toRemove.containsKey(ability)) {
            return this.toRemove.get(ability).contains((Object)l);
        }
        if (this.toReplace.containsKey(ability)) {
            return this.toReplace.get(ability).containsKey((Object)l);
        }
        return false;
    }

    public void removeBlock(BossAbility ability, Location loc) {
        List<Location> removed = this.toRemove.get(ability);
        Block b = loc.getBlock();
        b.setType(Material.AIR);
        removed.remove((Object)loc);
        this.toRemove.put(ability, removed);
    }

    public void removeAllBlocks(boolean particles) {
        for (BossAbility key : this.toRemove.keySet()) {
            this.removeBlocks(key, false, particles);
        }
        this.toRemove.clear();
    }

    public void removeBlocks(BossAbility ability, boolean remove, boolean particles) {
        if (!this.toRemove.containsKey(ability)) {
            return;
        }
        for (Location l : this.toRemove.get(ability)) {
            Block b = l.getBlock();
            if (!particles || !b.getType().isSolid() || b.getType().isBlock()) {
                // empty if block
            }
            b.setType(Material.AIR);
        }
        if (remove) {
            this.toRemove.remove(ability);
        }
    }

    public void replaceAll() {
        for (BossAbility key : this.toReplace.keySet()) {
            this.replaceBlocks(key, false);
        }
        this.toReplace.clear();
    }

    public void replaceBlocks(BossAbility ability, boolean removeAfter) {
        if (!this.toReplace.containsKey(ability)) {
            return;
        }
        HashMap<Location, NewBlockData> data = this.toReplace.get(ability);
        for (Map.Entry<Location, NewBlockData> blockData : data.entrySet()) {
            Block b = blockData.getKey().getBlock();
            NewBlockData newBlockDAta = blockData.getValue();
            b.setType(newBlockDAta.getMaterial());
            b.setData(newBlockDAta.getData());
        }
        if (removeAfter) {
            this.toReplace.remove(ability);
        }
    }

    public void restoreBlock(BossAbility ability, Block block) {
        Map data = this.toReplace.get(ability);
        if (data == null) {
            return;
        }
        NewBlockData blockData = (NewBlockData)data.get((Object)block.getLocation());
        if (blockData == null) {
            return;
        }
        block.setTypeIdAndData(blockData.getMaterial().getId(), blockData.getData(), false);
    }

    public NewBlockData removeBlockAndStore(BossAbility ability, Location location) {
        Map storedBlocks = this.toReplace.computeIfAbsent(ability, map -> new HashMap());
        Block block = location.getBlock();
        NewBlockData stored = new NewBlockData(block.getType(), block.getData());
        storedBlocks.put(block.getLocation(), stored);
        block.setTypeIdAndData(Material.AIR.getId(), (byte)0, true);
        return stored;
    }

    public NewBlockData storeBlockAndSet(BossAbility ability, Location location, Material material, byte data) {
        Map storedBlocks = this.toReplace.computeIfAbsent(ability, map -> new HashMap());
        Block block = location.getBlock();
        NewBlockData stored = new NewBlockData(block.getType(), block.getData());
        storedBlocks.put(block.getLocation(), stored);
        block.setTypeIdAndData(material.getId(), data, false);
        return stored;
    }

    public void storeBlockToRemove(BossAbility ability, Location l) {
        ArrayList<Location> locs = this.toRemove.containsKey(ability) ? (ArrayList<Location>) this.toRemove.get(ability) : new ArrayList<Location>();
        locs.add(l.clone());
        this.toRemove.put(ability, locs);
    }
}

