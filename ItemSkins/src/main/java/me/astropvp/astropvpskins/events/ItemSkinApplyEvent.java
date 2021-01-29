/*    */ package me.astropvp.astropvpskins.events;
/*    */ 
/*    */ import me.astropvp.astropvpskins.enums.SkinType;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;

public class ItemSkinApplyEvent extends Event implements Cancellable {
    public ItemSkinApplyEvent(Player player, ItemStack applied, SkinType type, InventoryClickEvent event) {
        this.player = player;
        this.type = type;
        this.event = event;
        this.applied = applied;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public Player getPlayer() {
        return this.player;
    }

    public SkinType getSkinType() {
        return this.type;
    }

    public InventoryClickEvent getEvent() {
        return this.event;
    }

    public ItemStack getItem() {
        return this.applied;
    }

    private static HandlerList handlers = new HandlerList();

    private boolean cancelled;

    private Player player;

    private SkinType type;

    private ItemStack applied;

    private InventoryClickEvent event;
}
