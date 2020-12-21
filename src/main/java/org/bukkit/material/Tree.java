/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.material;

import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.block.BlockFace;
import org.bukkit.material.MaterialData;

public class Tree
extends MaterialData {
    public Tree() {
        super(Material.LOG);
    }

    public Tree(TreeSpecies species) {
        this();
        this.setSpecies(species);
    }

    public Tree(TreeSpecies species, BlockFace dir) {
        this();
        this.setSpecies(species);
        this.setDirection(dir);
    }

    @Deprecated
    public Tree(int type) {
        super(type);
    }

    public Tree(Material type) {
        super(type);
    }

    @Deprecated
    public Tree(int type, byte data) {
        super(type, data);
    }

    @Deprecated
    public Tree(Material type, byte data) {
        super(type, data);
    }

    public TreeSpecies getSpecies() {
        return TreeSpecies.getByData((byte)(this.getData() & 3));
    }

    public void setSpecies(TreeSpecies species) {
        this.setData((byte)(this.getData() & 0xC | species.getData()));
    }

    public BlockFace getDirection() {
        switch (this.getData() >> 2 & 3) {
            default: {
                return BlockFace.UP;
            }
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.NORTH;
            }
            case 3: 
        }
        return BlockFace.SELF;
    }

    public void setDirection(BlockFace dir) {
        int dat;
        switch (dir) {
            default: {
                dat = 0;
                break;
            }
            case WEST: 
            case EAST: {
                dat = 1;
                break;
            }
            case NORTH: 
            case SOUTH: {
                dat = 2;
                break;
            }
            case SELF: {
                dat = 3;
            }
        }
        this.setData((byte)(this.getData() & 3 | dat << 2));
    }

    @Override
    public String toString() {
        return (Object)((Object)this.getSpecies()) + " " + (Object)((Object)this.getDirection()) + " " + super.toString();
    }

    @Override
    public Tree clone() {
        return (Tree)super.clone();
    }
}

