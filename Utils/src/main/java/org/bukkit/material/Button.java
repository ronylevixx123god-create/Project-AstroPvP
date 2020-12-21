/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.material;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Redstone;
import org.bukkit.material.SimpleAttachableMaterialData;

public class Button
extends SimpleAttachableMaterialData
implements Redstone {
    public Button() {
        super(Material.STONE_BUTTON);
    }

    @Deprecated
    public Button(int type) {
        super(type);
    }

    public Button(Material type) {
        super(type);
    }

    @Deprecated
    public Button(int type, byte data) {
        super(type, data);
    }

    @Deprecated
    public Button(Material type, byte data) {
        super(type, data);
    }

    @Override
    public boolean isPowered() {
        return (this.getData() & 8) == 8;
    }

    public void setPowered(boolean bool) {
        this.setData((byte)(bool ? this.getData() | 8 : this.getData() & 0xFFFFFFF7));
    }

    @Override
    public BlockFace getAttachedFace() {
        byte data = (byte)(this.getData() & 7);
        switch (data) {
            case 1: {
                return BlockFace.WEST;
            }
            case 2: {
                return BlockFace.EAST;
            }
            case 3: {
                return BlockFace.NORTH;
            }
            case 4: {
                return BlockFace.SOUTH;
            }
        }
        return null;
    }

    @Override
    public void setFacingDirection(BlockFace face) {
        byte data = (byte)(this.getData() & 8);
        switch (face) {
            case EAST: {
                data = (byte)(data | 1);
                break;
            }
            case WEST: {
                data = (byte)(data | 2);
                break;
            }
            case SOUTH: {
                data = (byte)(data | 3);
                break;
            }
            case NORTH: {
                data = (byte)(data | 4);
            }
        }
        this.setData(data);
    }

    @Override
    public String toString() {
        return super.toString() + " " + (this.isPowered() ? "" : "NOT ") + "POWERED";
    }

    @Override
    public Button clone() {
        return (Button)super.clone();
    }
}

