/*     */ package com.compilex.CosmicArmorSets.listeners;
/*     */ 
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*     */ import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
/*     */ import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
/*     */ import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
/*     */ import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
/*     */ import com.cosmicpvp.cosmicutils.CosmicUtils;
/*     */ import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
/*     */ import com.cosmicpvp.cosmicutils.utils.CC;
/*     */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*     */ import java.util.List;
/*     */ import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
/*     */ import me.ifamasssxd.cosmichcf.struct.OutpostType;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class HeroicArmorListener
/*     */   implements Listener
/*     */ {
/*     */   @EventHandler
/*     */   public void onPlayerCraftItem(CraftItemEvent event) {
/*  33 */     if (event.getInventory() != null && event.getRecipe() != null && event.getRecipe().getResult() != null) {
/*  34 */       Material type = event.getRecipe().getResult().getType();
/*  35 */       if (type != null && (type == Material.GOLD_AXE || type == Material.GOLD_SWORD)) {
/*  36 */         event.setCancelled(true);
/*  37 */         if (event.getWhoClicked() instanceof Player) {
/*  38 */           ((Player)event.getWhoClicked()).sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot craft this weapon!");
/*     */         }
/*  40 */       } else if (type != null && (type == Material.LEATHER_HELMET || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_BOOTS) && event.getWhoClicked().getInventory().contains(Material.INK_SACK)) {
/*     */         
/*  43 */         if (event.getWhoClicked() instanceof Player && !event.getWhoClicked().isOp()) {
/*  44 */           event.setCancelled(true);
/*  45 */           ((Player)event.getWhoClicked()).sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot manually dye Leather Armor!");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
/*     */   public void onPlayerClickedHeroic(InventoryClickEvent event) {
/*  53 */     if (!(event.getWhoClicked() instanceof Player))
/*     */       return; 
/*  55 */     Player player = (Player)event.getWhoClicked();
/*  56 */     if (event.getWhoClicked().getInventory() != null && player.getOpenInventory() != null && event.getWhoClicked().getInventory().equals(player.getOpenInventory().getBottomInventory())) {
/*     */       
/*  58 */       ItemStack current = event.getCurrentItem();
/*  59 */       if (current != null) {
/*  60 */         Material type = current.getType();
/*  61 */         NBTWrapper wrapper = null;
/*  62 */         HeroicArmorType typeToCompare = null;
/*  63 */         if (type == Material.LEATHER_HELMET) {
/*     */           
/*  65 */           wrapper = new NBTWrapper(current);
/*  66 */           typeToCompare = HeroicArmorType.HELMET;
/*  67 */         } else if (type == Material.LEATHER_CHESTPLATE) {
/*  68 */           wrapper = new NBTWrapper(current);
/*  69 */           typeToCompare = HeroicArmorType.CHESTPLATE;
/*  70 */         } else if (type == Material.LEATHER_LEGGINGS) {
/*  71 */           wrapper = new NBTWrapper(current);
/*  72 */           typeToCompare = HeroicArmorType.LEGGINGS;
/*  73 */         } else if (type == Material.LEATHER_BOOTS) {
/*  74 */           wrapper = new NBTWrapper(current);
/*  75 */           typeToCompare = HeroicArmorType.BOOTS;
/*     */         } 
/*     */         
/*  78 */         if (wrapper != null && wrapper.getString("cosmicType").equals("HEROIC_ARMOR")) {
/*  79 */           NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"));
/*  80 */           int armorModifier = builder.getInt("armorModifier").intValue();
/*  81 */           String abilitySet = builder.getString("abilitySet");
/*  82 */           int requiredModifier = typeToCompare.getArmorModifier();
/*  83 */           if (abilitySet != null && !abilitySet.isEmpty()) {
/*  84 */             AbilityArmorSet set = AbilityArmorSet.fromName(abilitySet);
/*  85 */             if (set != null) {
/*  86 */               requiredModifier = (int)Math.ceil(requiredModifier / 2.0D);
/*     */             }
/*     */           } 
/*  89 */           if (armorModifier != requiredModifier) {
/*  90 */             Bukkit.getLogger().info("Updating outdated Heroic Armor with modifier: " + armorModifier + " new: " + typeToCompare.getArmorModifier() + " for " + player.getName());
/*     */             
/*  92 */             builder.setInt("armorModifier", Integer.valueOf(requiredModifier));
/*  93 */             wrapper.setNBTBase("cosmicData", (NBTBase)builder.get());
/*     */             
/*  95 */             ItemStack item = wrapper.build();
/*  96 */             ItemMeta im = item.getItemMeta();
/*  97 */             List<String> lore = im.getLore();
/*  98 */             if (lore != null) {
/*  99 */               for (int i = 0; i < lore.size(); i++) {
/* 100 */                 String line = lore.get(i);
/* 101 */                 if (line.startsWith((CC.Gray + "+").intern()) && line.endsWith("Armor Value")) {
/*     */                   
/* 103 */                   lore.set(i, CC.Gray + "+" + requiredModifier + " Armor Value");
/* 104 */                   im.setLore(lore);
/* 105 */                   item.setItemMeta(im);
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             }
/* 111 */             event.setCurrentItem(item);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onPlayerDamage(EntityDamageByEntityEvent event) {
/* 120 */     if (event.getEntity() instanceof Player) {
/* 121 */       double damageReduction = 0.0D;
/*     */       
/* 123 */       OutpostTier tier = CosmicOutpostsAPI.getOutpostTierOfWorld(event.getEntity());
/* 124 */       if (tier != null && (tier == OutpostTier.VANILLA || tier == OutpostTier.TRAINEE))
/*     */         return; 
/* 126 */       Player player = (Player)event.getEntity();
/* 127 */       for (ItemStack item : player.getInventory().getArmorContents()) {
/* 128 */         if (item != null) {
/* 129 */           Material type = item.getType();
/*     */           
/* 131 */           if (type == Material.LEATHER_CHESTPLATE) {
/* 132 */             if (CosmicArmorSetsAPI.isHeroicArmor(item))
/* 133 */               if (isMkitArmor(item)) {
/* 134 */                 damageReduction += 0.16D;
/*     */               } else {
/* 136 */                 damageReduction += 0.1D;
/*     */               }  
/* 138 */           } else if (type == Material.LEATHER_LEGGINGS) {
/* 139 */             if (CosmicArmorSetsAPI.isHeroicArmor(item))
/* 140 */               if (isMkitArmor(item)) {
/* 141 */                 damageReduction += 0.13D;
/*     */               } else {
/* 143 */                 damageReduction += 0.08D;
/*     */               }  
/* 145 */           } else if ((type == Material.LEATHER_BOOTS || type == Material.LEATHER_HELMET) && 
/* 146 */             CosmicArmorSetsAPI.isHeroicArmor(item)) {
/* 147 */             if (isMkitArmor(item)) {
/* 148 */               damageReduction += 0.08D;
/*     */             } else {
/* 150 */               damageReduction += 0.045D;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 155 */       if (damageReduction > 0.0D) {
/* 156 */         double reductionDamage = damageReduction * event.getDamage(EntityDamageEvent.DamageModifier.BASE);
/* 157 */         event.setDamage(Math.max(0.0D, event.getDamage(EntityDamageEvent.DamageModifier.BASE) - reductionDamage));
/*     */       } 
/*     */     } 
/*     */     
/* 161 */     if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
/* 162 */       double damageMod = 0.0D;
/* 163 */       double damageIncrease = 0.0D;
/*     */       
/* 165 */       if (CosmicUtils.get().isOutpostsEnabled() && CosmicOutpostsAPI.controlsTraineeOutpost((Player)event.getDamager())) {
/* 166 */         damageMod += 0.025D;
/*     */       }
/* 168 */       if (HCFOutpostAPI.isControllingOutpost((Player)event.getDamager(), OutpostType.COURTYARD)) {
/* 169 */         damageMod += 0.0625D;
/*     */       }
/* 171 */       for (ItemStack item : ((Player)event.getEntity()).getInventory().getArmorContents()) {
/* 172 */         if (item != null) {
/* 173 */           Material type = item.getType();
/*     */           
/* 175 */           if (type == Material.LEATHER_CHESTPLATE)
/* 176 */           { if (CosmicArmorSetsAPI.isHeroicArmor(item))
/* 177 */               damageIncrease += damageMod;  }
/* 178 */           else if (type == Material.LEATHER_LEGGINGS)
/* 179 */           { if (CosmicArmorSetsAPI.isHeroicArmor(item)) damageIncrease += damageMod;  }
/* 180 */           else if ((type == Material.LEATHER_BOOTS || type == Material.LEATHER_HELMET) && 
/* 181 */             CosmicArmorSetsAPI.isHeroicArmor(item)) { damageIncrease += damageMod; }
/*     */         
/*     */         } 
/*     */       } 
/* 185 */       if (damageIncrease > 0.0D) {
/* 186 */         event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0D + damageIncrease));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMkitArmor(ItemStack item) {
/* 194 */     NBTTagCompoundBuilder builder = (new NBTWrapper(item)).getCompound("cosmicData");
/* 195 */     if (builder == null) return false; 
/* 196 */     return builder.hasKey("abilitySet");
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\listeners\HeroicArmorListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */