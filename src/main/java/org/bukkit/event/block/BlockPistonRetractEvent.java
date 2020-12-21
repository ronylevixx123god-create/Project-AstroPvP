/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.event.block;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockPistonEvent;

public class BlockPistonRetractEvent
extends BlockPistonEvent {
    private static final HandlerList handlers = new HandlerList();

    public BlockPistonRetractEvent(Block block, BlockFace direction) {
        super(block, direction);
    }

    public Location getRetractLocation() {
        return this.getBlock().getRelative(this.getDirection(), 2).getLocation();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

