package org.arkhamnetwork.enchantments.libs;

import org.bukkit.event.*;
import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public class EnchantBreakBlockEvent extends Event implements Cancellable
{
    public static final HandlerList list;
    private boolean cancelled;
    private Block block;
    private Material blockType;
    private Player player;
    
    public EnchantBreakBlockEvent(final Player player, final Block block, final Material material) {
        this.cancelled = false;
        this.player = player;
        this.block = block;
        this.blockType = material;
    }
    
    public HandlerList getHandlers() {
        return EnchantBreakBlockEvent.list;
    }
    
    public static HandlerList getHandlerList() {
        return EnchantBreakBlockEvent.list;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public Material getBlockType() {
        return this.blockType;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public void setBlock(final Block block) {
        this.block = block;
    }
    
    public void setBlockType(final Material blockType) {
        this.blockType = blockType;
    }
    
    public void setPlayer(final Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    static {
        list = new HandlerList();
    }
}
