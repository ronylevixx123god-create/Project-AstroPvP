/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.chest;

import com.sk89q.worldguard.chest.ChestProtection;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class SignChestProtection
implements ChestProtection {
    @Override
    public boolean isProtected(Block block, Player player) {
        if (this.isChest(block.getTypeId())) {
            Block below = block.getRelative(0, -1, 0);
            return this.isProtectedSignAround(below, player);
        }
        if (block.getTypeId() == 63) {
            return this.isProtectedSignAndChestBinary(block, player);
        }
        Block above = block.getRelative(0, 1, 0);
        Boolean res = this.isProtectedSign(above, player);
        if (res != null) {
            return res;
        }
        return false;
    }

    @Override
    public boolean isProtectedPlacement(Block block, Player player) {
        return this.isProtectedSignAround(block, player);
    }

    private boolean isProtectedSignAround(Block searchBlock, Player player) {
        Block side = searchBlock;
        Boolean res = this.isProtectedSign(side, player);
        if (res != null && res.booleanValue()) {
            return res;
        }
        side = searchBlock.getRelative(-1, 0, 0);
        res = this.isProtectedSignAndChest(side, player);
        if (res != null && res.booleanValue()) {
            return res;
        }
        side = searchBlock.getRelative(1, 0, 0);
        res = this.isProtectedSignAndChest(side, player);
        if (res != null && res.booleanValue()) {
            return res;
        }
        side = searchBlock.getRelative(0, 0, -1);
        res = this.isProtectedSignAndChest(side, player);
        if (res != null && res.booleanValue()) {
            return res;
        }
        side = searchBlock.getRelative(0, 0, 1);
        res = this.isProtectedSignAndChest(side, player);
        if (res != null && res.booleanValue()) {
            return res;
        }
        return false;
    }

    private Boolean isProtectedSign(Sign sign, Player player) {
        if (sign.getLine(0).equalsIgnoreCase("[Lock]")) {
            if (player == null) {
                return true;
            }
            String name = player.getName();
            if (name.equalsIgnoreCase(sign.getLine(1).trim()) || name.equalsIgnoreCase(sign.getLine(2).trim()) || name.equalsIgnoreCase(sign.getLine(3).trim())) {
                return false;
            }
            return true;
        }
        return null;
    }

    private Boolean isProtectedSign(Block block, Player player) {
        BlockState state = block.getState();
        if (state == null || !(state instanceof Sign)) {
            return null;
        }
        return this.isProtectedSign((Sign)state, player);
    }

    private Boolean isProtectedSignAndChest(Block block, Player player) {
        if (!this.isChest(block.getRelative(0, 1, 0).getTypeId())) {
            return null;
        }
        return this.isProtectedSign(block, player);
    }

    private boolean isProtectedSignAndChestBinary(Block block, Player player) {
        Boolean res = this.isProtectedSignAndChest(block, player);
        return res != null && res != false;
    }

    @Override
    public boolean isAdjacentChestProtected(Block searchBlock, Player player) {
        Block side = searchBlock;
        Boolean res = this.isProtected(side, player);
        if (res != null && res.booleanValue()) {
            return res;
        }
        side = searchBlock.getRelative(-1, 0, 0);
        res = this.isProtected(side, player);
        if (res != null && res.booleanValue()) {
            return res;
        }
        side = searchBlock.getRelative(1, 0, 0);
        res = this.isProtected(side, player);
        if (res != null && res.booleanValue()) {
            return res;
        }
        side = searchBlock.getRelative(0, 0, -1);
        res = this.isProtected(side, player);
        if (res != null && res.booleanValue()) {
            return res;
        }
        side = searchBlock.getRelative(0, 0, 1);
        res = this.isProtected(side, player);
        if (res != null && res.booleanValue()) {
            return res;
        }
        return false;
    }

    @Override
    @Deprecated
    public boolean isChest(Material material) {
        return this.isChest(material.getId());
    }

    @Override
    public boolean isChest(int type) {
        return type == 54 || type == 23 || type == 61 || type == 62;
    }
}

