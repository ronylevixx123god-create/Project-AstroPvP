/*    */ package com.compilex.CosmicArmorSets.enums;
/*    */ 
/*    */ public class CachedArmorSet<T> {
/*    */   private T set;
/*    */   private long lastUpdated;
/*    */   
/*    */   public CachedArmorSet(T set, long lastUpdated) {
/*  8 */     this.set = set; this.lastUpdated = lastUpdated;
/*    */   }
/* 10 */   public void setSet(T set) { this.set = set; } public void setLastUpdated(long lastUpdated) { this.lastUpdated = lastUpdated; }
/*    */   
/* 12 */   public T getSet() { return this.set; } public long getLastUpdated() {
/* 13 */     return this.lastUpdated;
/*    */   }
/*    */   public void update(T set) {
/* 16 */     this.lastUpdated = System.currentTimeMillis();
/* 17 */     this.set = set;
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\CachedArmorSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */