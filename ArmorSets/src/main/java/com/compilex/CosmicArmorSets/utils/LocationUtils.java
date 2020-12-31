/*    */ package com.compilex.CosmicArmorSets.utils;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.block.Block;
/*    */ 
/*    */ 
/*    */ public class LocationUtils
/*    */ {
/*    */   public static Location getNearbyLocation(Location l, int radius, int minDistance, int y_boost) {
/* 11 */     Random rand = new Random();
/* 12 */     Location rand_loc = l.clone();
/*    */ 
/*    */     
/* 15 */     rand_loc.add(((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + minDistance)), 0.0D, ((
/*    */         
/* 17 */         rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + minDistance)));
/*    */ 
/*    */     
/* 20 */     rand_loc.add(0.5D, y_boost, 0.5D);
/*    */     
/* 22 */     Block b = rand_loc.getWorld().getHighestBlockAt(rand_loc).getLocation().getBlock();
/*    */     
/* 24 */     return (b.getY() < rand_loc.getY()) ? b.getLocation() : rand_loc;
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSet\\utils\LocationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */