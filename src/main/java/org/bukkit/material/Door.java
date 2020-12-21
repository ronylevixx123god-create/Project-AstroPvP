/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.material;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

@Deprecated
public class Door
extends MaterialData
implements Directional,
Openable {
    public Door() {
        super(Material.WOODEN_DOOR);
    }

    @Deprecated
    public Door(int type) {
        super(type);
    }

    public Door(Material type) {
        super(type);
    }

    @Deprecated
    public Door(int type, byte data) {
        super(type, data);
    }

    @Deprecated
    public Door(Material type, byte data) {
        super(type, data);
    }

    @Override
    @Deprecated
    public boolean isOpen() {
        return (this.getData() & 4) == 4;
    }

    @Override
    @Deprecated
    public void setOpen(boolean isOpen) {
        this.setData((byte)(isOpen ? this.getData() | 4 : this.getData() & 0xFFFFFFFB));
    }

    public boolean isTopHalf() {
        return (this.getData() & 8) == 8;
    }

    @Deprecated
    public void setTopHalf(boolean isTopHalf) {
        this.setData((byte)(isTopHalf ? this.getData() | 8 : this.getData() & 0xFFFFFFF7));
    }

    @Deprecated
    public BlockFace getHingeCorner() {
        byte d = this.getData();
        if ((d & 3) == 3) {
            return BlockFace.NORTH_WEST;
        }
        if ((d & 1) == 1) {
            return BlockFace.SOUTH_EAST;
        }
        if ((d & 2) == 2) {
            return BlockFace.SOUTH_WEST;
        }
        return BlockFace.NORTH_EAST;
    }

    @Override
    public String toString() {
        return (this.isTopHalf() ? "TOP" : "BOTTOM") + " half of " + super.toString();
    }

    @Override
    @Deprecated
    public void setFacingDirection(BlockFace face) {
        byte data = (byte)(this.getData() & 0x12);
        switch (face) {
            case NORTH: {
                data = (byte)(data | 1);
                break;
            }
            case EAST: {
                data = (byte)(data | 2);
                break;
            }
            case SOUTH: {
                data = (byte)(data | 3);
            }
        }
        this.setData(data);
    }

    @Override
    @Deprecated
    public BlockFace getFacing() {
        byte data = (byte)(this.getData() & 3);
        switch (data) {
            case 0: {
                return BlockFace.WEST;
            }
            case 1: {
                return BlockFace.NORTH;
            }
            case 2: {
                return BlockFace.EAST;
            }
            case 3: {
                return BlockFace.SOUTH;
            }
        }
        return null;
    }

    @Override
    public Door clone() {
        return (Door)super.clone();
    }
}

