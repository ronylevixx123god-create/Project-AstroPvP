/*     */ package com.compilex.CosmicArmorSets.enums.armorsets;

/*     */ class DimensionalDamageTracker
/*     */ {
/*     */   private int hitsTaken;
/*     */   private long lastHitTaken;
/*     */   
/*     */   public boolean trackHit() {
/* 447 */     checkHit();
/*     */     
/* 449 */     this.hitsTaken++;
/* 450 */     return (this.hitsTaken >= 4);
/*     */   }
/*     */   
/*     */   public boolean checkHit() {
/* 454 */     if (this.lastHitTaken == 0L || System.currentTimeMillis() - this.lastHitTaken > 10000L) {
/* 455 */       this.hitsTaken = 0;
/* 456 */       this.lastHitTaken = System.currentTimeMillis();
/*     */     } 
/* 458 */     return (this.hitsTaken >= 4);
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\armorsets\DimensionalDamageTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */