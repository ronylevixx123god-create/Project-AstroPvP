/*    */ package me.astropvp.astropvpskins.events;
/*    */ 
/*    */ import me.astropvp.astropvpskins.enums.SkinType;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSkinApplyEvent
/*    */   extends Event
/*    */   implements Cancellable
/*    */ {
/*    */   public ItemSkinApplyEvent(Player player, ItemStack applied, SkinType type, InventoryClickEvent event) {
/* 21 */     this.player = player;
/* 22 */     this.type = type;
/* 23 */     this.event = event;
/* 24 */     this.applied = applied;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 28 */     return handlers;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 33 */     return handlers;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 38 */     this.cancelled = cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 43 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 47 */     return this.player;
/*    */   }
/*    */   
/*    */   public SkinType getSkinType() {
/* 51 */     return this.type;
/*    */   }
/*    */   
/*    */   public InventoryClickEvent getEvent() {
/* 55 */     return this.event;
/*    */   }
/*    */   
/*    */   public ItemStack getItem() {
/* 59 */     return this.applied;
/*    */   }
/*    */ 
/*    */   
/* 63 */   private static HandlerList handlers = new HandlerList();
/*    */   private boolean cancelled;
/*    */   private Player player;
/*    */   private SkinType type;
/*    */   private ItemStack applied;
/*    */   private InventoryClickEvent event;
/*    */ }


/* Location:              C:\Users\bryan\Downloads\GalacticItemSkins.jar!\me\galacticpvp\galacticitemskins\events\ItemSkinApplyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */