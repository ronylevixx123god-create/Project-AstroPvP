/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 */
package me.ifamasssxd.cosmicbosses.struct.blockmanager;

import org.bukkit.Material;

public class StoredBlock {
    int bossId = -1;
    private Material material;
    private byte blockData;
    private long resetTime;

    public StoredBlock(int bossId, Material material, byte blockData, long resetTime) {
        this.bossId = bossId;
        this.material = material;
        this.blockData = blockData;
        this.resetTime = resetTime;
    }

    public int getBossId() {
        return this.bossId;
    }

    public Material getMaterial() {
        return this.material;
    }

    public byte getBlockData() {
        return this.blockData;
    }

    public long getResetTime() {
        return this.resetTime;
    }
}

