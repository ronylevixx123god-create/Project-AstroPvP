package org.arkhamnetwork.enchantments.events;

import org.bukkit.event.*;
import org.bukkit.entity.*;
import com.rit.sucy.*;

public class CustomEnchantProcOnEntityEvent extends Event implements Cancellable
{
    private static HandlerList handlerList;
    private boolean cancelled;
    private LivingEntity player;
    private LivingEntity target;
    private Event event;
    private CustomEnchantment enchant;
    private int enchantLevel;
    
    public CustomEnchantProcOnEntityEvent(final LivingEntity player, final LivingEntity target, final Event rawEvent, final CustomEnchantment enchant, final int enchantLevel) {
        this.player = player;
        this.enchant = enchant;
        this.target = target;
        this.event = rawEvent;
        this.enchantLevel = enchantLevel;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public static HandlerList getHandlerList() {
        return CustomEnchantProcOnEntityEvent.handlerList;
    }
    
    public HandlerList getHandlers() {
        return CustomEnchantProcOnEntityEvent.handlerList;
    }
    
    public CustomEnchantProcOnEntityEvent(final boolean cancelled, final LivingEntity player, final LivingEntity target, final Event event, final CustomEnchantment enchant, final int enchantLevel) {
        this.cancelled = cancelled;
        this.player = player;
        this.target = target;
        this.event = event;
        this.enchant = enchant;
        this.enchantLevel = enchantLevel;
    }
    
    public LivingEntity getPlayer() {
        return this.player;
    }
    
    public LivingEntity getTarget() {
        return this.target;
    }
    
    public Event getEvent() {
        return this.event;
    }
    
    public CustomEnchantment getEnchant() {
        return this.enchant;
    }
    
    public int getEnchantLevel() {
        return this.enchantLevel;
    }
    
    public void setEvent(final Event event) {
        this.event = event;
    }
    
    static {
        CustomEnchantProcOnEntityEvent.handlerList = new HandlerList();
    }

}
