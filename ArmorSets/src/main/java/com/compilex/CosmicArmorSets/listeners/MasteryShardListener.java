/*    */ package com.compilex.CosmicArmorSets.listeners;
/*    */ 
/*    */ import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
/*    */ import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
/*    */ import com.cosmicpvp.cosmicutils.nbt.NBTTagListBuilder;
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import com.cosmicpvp.cosmicutils.utils.ItemUtils;
/*    */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.server.v1_7_R4.NBTBase;
/*    */ import org.arkhamnetwork.enchantments.EnchantmentBook;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Sound;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class MasteryShardListener implements Listener {
/*    */   @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
/*    */   public void onInventoryClick(InventoryClickEvent event) {
/* 27 */     ItemStack clicked = event.getCurrentItem();
/* 28 */     if (clicked == null || clicked.getType() != Material.BOOK)
/* 29 */       return;  ItemStack cursor = event.getCursor();
/* 30 */     if (cursor == null || cursor.getType() == Material.AIR)
/*    */       return; 
/* 32 */     if (event.getWhoClicked().getInventory() == null || event.getWhoClicked().getOpenInventory() == null || !event.getWhoClicked().getInventory().equals(event.getWhoClicked().getOpenInventory().getBottomInventory())) {
/*    */       return;
/*    */     }
/* 35 */     if (event.getWhoClicked().getWorld().getName().startsWith("world_duels")) {
/*    */       return;
/*    */     }
/* 38 */     NBTWrapper cursorWrapper = new NBTWrapper(cursor);
/* 39 */     if (cursorWrapper.getString("cosmicType").equals("MASTERY_SHARD") && EnchantmentBook.isEnchantmentBook(clicked)) {
/*    */       
/* 41 */       event.setCancelled(true);
/*    */       
/* 43 */       Player player = (Player)event.getWhoClicked();
/* 44 */       NBTTagCompoundBuilder builder = cursorWrapper.getCompound("cosmicData");
/* 45 */       if (builder != null && builder.hasKey("masteryShard")) {
/* 46 */         AbilityArmorSet set = AbilityArmorSet.fromName(builder.getString("masteryShard"));
/* 47 */         if (set != null) {
/* 48 */           NBTWrapper clickedWrapper = new NBTWrapper(clicked);
/* 49 */           NBTTagCompoundBuilder clickedCosmicData = clickedWrapper.getCompoundOrCreate("cosmicData");
/*    */           
/* 51 */           NBTTagListBuilder shardList = !clickedCosmicData.hasKey("masteryShard") ? new NBTTagListBuilder() : new NBTTagListBuilder(clickedCosmicData.getNBTBase("masteryShard"));
/* 52 */           if (shardList.contains(set.name())) {
/* 53 */             player.sendMessage(CC.RedB + "(!) " + CC.Red + "This book already has this Mastery Shard equipped!");
/*    */             
/*    */             return;
/*    */           } 
/*    */           
/* 58 */           shardList.addString(set.name());
/* 59 */           clickedCosmicData.setNBTBase("masteryShard", (NBTBase)shardList.build());
/* 60 */           clickedWrapper.setNBTBase("cosmicData", (NBTBase)clickedCosmicData.get());
/*    */           
/* 62 */           ItemStack newBook = clickedWrapper.build();
/* 63 */           ItemMeta im = newBook.getItemMeta();
/* 64 */           List<String> lore = (im.getLore() == null) ? Lists.newArrayList() : im.getLore();
/* 65 */           lore.add(CC.GoldB + "* Mastery Shard " + CC.Gold + "(" + set.getDisplayName() + CC.Gold + ")");
/* 66 */           im.setLore(lore);
/* 67 */           newBook.setItemMeta(im);
/*    */           
/* 69 */           player.setItemOnCursor(null);
/* 70 */           event.setCurrentItem(newBook);
/* 71 */           player.sendMessage(CC.GoldB + "(!) " + CC.GoldU + ChatColor.stripColor(set.getDisplayName()) + " Mastery Shard" + CC.Gold + " applied to your " + ItemUtils.getDisplayName(clicked) + CC.Gold + "!");
/* 72 */           player.sendMessage(CC.Gray + "This book is now powerful enough to attempt to apply to the " + set.getDisplayName() + CC.Gray + " /mkit items!");
/* 73 */           player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 1.1F);
/*    */           return;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\listeners\MasteryShardListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */