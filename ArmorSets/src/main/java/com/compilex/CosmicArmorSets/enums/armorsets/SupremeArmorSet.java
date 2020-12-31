/*     */ package com.compilex.CosmicArmorSets.enums.armorsets;
/*     */ 
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
/*     */ import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
/*     */ import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
/*     */ import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
/*     */ import com.compilex.CosmicArmorSets.utils.ItemUtils;
/*     */ import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
/*     */ import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
/*     */ import com.cosmicpvp.cosmicutils.utils.CC;
/*     */ import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
/*     */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.rit.sucy.CustomEnchantment;
/*     */ import com.rit.sucy.EnchantmentAPI;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ import org.bukkit.event.player.PlayerChangedWorldEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class SupremeArmorSet
/*     */   extends ArmorSetInfo implements OtherPlayerEventListenerSet, WeaponedArmorSet {
/*     */   public ItemStack getHelmet(boolean applyTransmog) {
/*  39 */     List<CustomEnchantment> enchants = new LinkedList<>(Arrays.asList(new CustomEnchantment[] { 
/*  40 */             EnchantmentAPI.getEnchantment("Drunk"), 
/*  41 */             EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  42 */             EnchantmentAPI.getEnchantment("Enlighted"), 
/*  43 */             EnchantmentAPI.getEnchantment("Armored"), 
/*  44 */             EnchantmentAPI.getEnchantment("Dodge"), 
/*  45 */             EnchantmentAPI.getEnchantment("Tank"), 
/*  46 */             EnchantmentAPI.getEnchantment("Spirits"), 
/*  47 */             EnchantmentAPI.getEnchantment("Marksman"), 
/*  48 */             EnchantmentAPI.getEnchantment("Angelic"), 
/*  49 */             EnchantmentAPI.getEnchantment("Heavy"), 
/*  50 */             EnchantmentAPI.getEnchantment("Valor"), 
/*  51 */             EnchantmentAPI.getEnchantment("Smoke Bomb"), 
/*  52 */             EnchantmentAPI.getEnchantment("Hardened") }));
/*  53 */     if (Math.random() <= 0.1D) {
/*  54 */       enchants.add(getEnchantment("Phoenix"));
/*     */     } else {
/*  56 */       enchants.add(getEnchantment("Marksman"));
/*     */     } 
/*  58 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(
/*  59 */           ItemUtils.buildItem(Material.DIAMOND_HELMET, (short)0, "&4&lSupreme Headgear", new String[] { "&4&oA lightweight headpiece", "&4&ofit for take off.", "", "&4&lSUPREME SET BONUS", "&4&l* &4Gears IV", "&4&l* &4No Fall Damage / Food Loss", "&4&l* &4Deal +15% damage to all enemies", "&4&l* &4Enemy arrows deal 10% more damage to you.", "&4&l* &4+200% Clout", "&7(&oRequires all 4 supreme items.&7)" }), enchants));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getChestplate(boolean applyTransmog) {
/*  77 */     ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short)0, "&4&lSupreme Vest", new String[] { "&4&oAn aerodynamic vest that", "&4&ois capable of sustaining flight.", "", "&4&lSUPREME SET BONUS", "&4&l* &4Gears IV", "&4&l* &4No Fall Damage / Food Loss", "&4&l* &4Deal +15% damage to all enemies", "&4&l* &4Enemy arrows deal 10% more damage to you.", "&4&l* &4+200% Clout", "&7(&oRequires all 4 supreme items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  89 */           Arrays.asList((Object[])new CustomEnchantment[] { 
/*  90 */               EnchantmentAPI.getEnchantment("Aegis"), 
/*  91 */               EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  92 */               EnchantmentAPI.getEnchantment("Blood Lust"), 
/*  93 */               EnchantmentAPI.getEnchantment("Diminish"), 
/*  94 */               EnchantmentAPI.getEnchantment("Armored"), 
/*  95 */               EnchantmentAPI.getEnchantment("Overload"), 
/*  96 */               EnchantmentAPI.getEnchantment("Leadership"), 
/*  97 */               EnchantmentAPI.getEnchantment("Blood Link"), 
/*     */               
/*  99 */               EnchantmentAPI.getEnchantment("Tank"), 
/* 100 */               EnchantmentAPI.getEnchantment("Marksman"), 
/* 101 */               EnchantmentAPI.getEnchantment("Angelic"), 
/* 102 */               EnchantmentAPI.getEnchantment("Repair Guard") })));
/*     */     
/* 104 */     if (Math.random() <= 0.1D) {
/* 105 */       addEnchantment("Enlighted", getMaxEnchantLevel("Enlighted"), item);
/* 106 */       addEnchantment("Divine Enlighted", ItemUtils.getRandomArmorSetEnchantLevel("Divine Enlighted"), item);
/*     */     } else {
/* 108 */       addEnchantment("Enlighted", getMaxEnchantLevel("Enlighted"), item);
/*     */     } 
/*     */     
/* 111 */     return getWithTransmog(applyTransmog, item);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getLeggings(boolean applyTransmog) {
/* 116 */     ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short)0, "&4&lSupreme Chaps", new String[] { "&4&oLightweight and clout powered,", "&4&oprovides enough thrust to boost", "&4&oeven the most feeble into the skies.", "", "&4&lSUPREME SET BONUS", "&4&l* &4Gears IV", "&4&l* &4No Fall Damage / Food Loss", "&4&l* &4Deal +15% damage to all enemies", "&4&l* &4Enemy arrows deal 10% more damage to you.", "&4&l* &4+200% Clout", "&7(&oRequires all 4 supreme items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 129 */           Arrays.asList((Object[])new CustomEnchantment[] { 
/* 130 */               EnchantmentAPI.getEnchantment("Deathbringer"), 
/* 131 */               EnchantmentAPI.getEnchantment("Enlighted"), 
/* 132 */               EnchantmentAPI.getEnchantment("Armored"), 
/* 133 */               EnchantmentAPI.getEnchantment("Clarity"), 
/* 134 */               EnchantmentAPI.getEnchantment("Tank"), 
/* 135 */               EnchantmentAPI.getEnchantment("Lucky"), 
/* 136 */               EnchantmentAPI.getEnchantment("Angelic"), 
/* 137 */               EnchantmentAPI.getEnchantment("Guardians"), 
/* 138 */               EnchantmentAPI.getEnchantment("Obsidianshield"), 
/* 139 */               EnchantmentAPI.getEnchantment("Valor"), 
/* 140 */               EnchantmentAPI.getEnchantment("Sticky"), 
/* 141 */               EnchantmentAPI.getEnchantment("Cactus") })));
/*     */     
/* 143 */     if (Math.random() <= 0.1D) {
/* 144 */       addEnchantment("Nature Wrath", ItemUtils.getRandomArmorSetEnchantLevel("Nature Wrath"), item);
/*     */     } else {
/* 146 */       addEnchantment("Protection", ItemUtils.getRandomArmorSetEnchantLevel("Protection"), item);
/*     */     } 
/* 148 */     return getWithTransmog(applyTransmog, item);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBoots(boolean applyTransmog) {
/* 153 */     ItemStack item = ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short)0, "&4&lSupreme Thruster Boots", new String[] { "&4&oSupreme boots capable of", "&4&oconverting clout into flight.", "", "&4&lSUPREME SET BONUS", "&4&l* &4Gears IV", "&4&l* &4No Fall Damage / Food Loss", "&4&l* &4Deal +15% damage to all enemies", "&4&l* &4Enemy arrows deal 10% more damage to you.", "&4&l* &4+200% Clout", "&7(&oRequires all 4 supreme items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 165 */           Arrays.asList((Object[])new CustomEnchantment[] {
/* 166 */               EnchantmentAPI.getEnchantment("Gears"), 
/* 167 */               EnchantmentAPI.getEnchantment("Enlighted"), 
/* 168 */               EnchantmentAPI.getEnchantment("Armored"), 
/* 169 */               EnchantmentAPI.getEnchantment("Tank"), 
/* 170 */               EnchantmentAPI.getEnchantment("Marksman"), 
/* 171 */               EnchantmentAPI.getEnchantment("Angelic"), 
/* 172 */               EnchantmentAPI.getEnchantment("Heavy"), 
/* 173 */               EnchantmentAPI.getEnchantment("Ender Walker"), 
/* 174 */               EnchantmentAPI.getEnchantment("Hardened"), 
/* 175 */               EnchantmentAPI.getEnchantment("Rocket Escape") })));
/* 176 */     item = addEnchantment("Springs", 2, item);
/* 177 */     if (Math.random() <= 0.1D) {
/* 178 */       item = addEnchantment("Deathbringer", getMaxEnchantLevel("Deathbringer"), item);
/* 179 */       item = addEnchantment("Planetary Deathbringer", ItemUtils.getRandomArmorSetEnchantLevel("Planetary Deathbringer"), item);
/*     */     } else {
/* 181 */       item = addEnchantment("Deathbringer", ItemUtils.getRandomArmorSetEnchantLevel("Deathbringer"), item);
/* 182 */       item = addEnchantment("Ghost", ItemUtils.getRandomArmorSetEnchantLevel("Ghost"), item);
/*     */     } 
/*     */     
/* 186 */     return getWithTransmog(applyTransmog, item);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getEquipDescription() {
/* 191 */     return Lists.newArrayList(new String[] { ChatColor.DARK_RED + "" + ChatColor.BOLD + "SUPREME SET BONUS:", CC.DarkRedB + " * " + CC.DarkRed + "+15% OUTGOING DAMAGE", CC.DarkRedB + " * " + CC.DarkRed + "+10% INCOMING ARROW DAMAGE", CC.DarkRedB + " * " + CC.DarkRed + "NO FALL DAMAGE", CC.DarkRedB + " * " + CC.DarkRed + "NO FOOD LOSS", CC.DarkRedB + " * " + CC.DarkRed + "GEARS IV", CC.DarkRedB + " * " + CC.DarkRed + "+200% CLOUT (Flight Enabled)" });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
/* 202 */     if (isBlockedOutpostWorld(event.getDamager())) {
/* 203 */       return false;
/*     */     }
/* 205 */     if (event.getDamager() instanceof org.bukkit.entity.Arrow) {
/* 206 */       event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.1D);
/*     */     }
/* 208 */     ItemStack handItem = damaged.getItemInHand();
/* 209 */     if (handItem != null) {
/* 210 */       Material type = handItem.getType();
/* 211 */       if (type != null && (type == Material.DIAMOND_SWORD || type == Material.GOLD_SWORD) && (new NBTWrapper(handItem)).getString("cosmicType").equals("SUPREME_SWORD")) {
/* 212 */         event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.9D);
/*     */       }
/*     */     } 
/*     */     
/* 216 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
/* 221 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
/* 226 */     if (isBlockedOutpostWorld((Entity)damager)) return false; 
/* 227 */     return modifyDamage(damager, event, 3.0D, multiplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEquip(Player player) {
/* 232 */     OutpostTier outpostTier = CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)player);
/* 233 */     if (outpostTier == OutpostTier.TRAINEE || outpostTier == OutpostTier.VANILLA || outpostTier == OutpostTier.HERO) {
/*     */       return;
/*     */     }
/*     */     
/* 237 */     EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(player, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnequip(Player player) {
/* 242 */     EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(player, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlayerEvent(Event event) {
/* 247 */     if (event instanceof EntityDamageEvent) {
/* 248 */       if (((EntityDamageEvent)event).getCause() == EntityDamageEvent.DamageCause.FALL) {
/* 249 */         ((EntityDamageEvent)event).setCancelled(true);
/*     */       }
/* 251 */     } else if (event instanceof FoodLevelChangeEvent) {
/* 252 */       FoodLevelChangeEvent e = (FoodLevelChangeEvent)event;
/* 253 */       if (e.getFoodLevel() < ((Player)e.getEntity()).getFoodLevel())
/* 254 */         e.setCancelled(true); 
/* 255 */     } else if (event instanceof PlayerChangedWorldEvent) {
/*     */       
/* 257 */       PlayerChangedWorldEvent world = (PlayerChangedWorldEvent)event;
/* 258 */       if (world.getFrom() != null) {
/* 259 */         Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicArmorSetsPlugin.get_(), () -> { onUnequip(world.getPlayer()); Bukkit.getLogger().info("Removing Gears 4 affected from " + ((PlayerChangedWorldEvent)event).getPlayer().getName()); }, 1L);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
/* 269 */     if (isBlockedOutpostWorld(event.getDamager())) {
/* 270 */       return false;
/*     */     }
/* 272 */     if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player)event.getEntity(), 2, damager)) {
/* 273 */       return false;
/*     */     }
/* 275 */     ItemStack handItem = ((Player)event.getDamager()).getItemInHand();
/* 276 */     if (handItem != null) {
/* 277 */       Material type = handItem.getType();
/* 278 */       if (type != null && (type == Material.DIAMOND_SWORD || type == Material.GOLD_SWORD) && (new NBTWrapper(handItem)).getString("cosmicType").equals("SUPREME_SWORD")) {
/* 279 */         event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.9D);
/*     */       }
/*     */     } 
/*     */     
/* 283 */     event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.15D);
/* 284 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getWeapon(boolean applyTransmog) {
/* 302 */     ItemStack item = (new NBTWrapper((new ItemBuilder(Material.DIAMOND_SWORD, CC.DarkRedB + "Supreme Fanny Pack", new String[] { CC.Gray + "They laughed at you, they", CC.Gray + "told you a fanny pack is not", CC.Gray + "a practical piece of clothing...", CC.Gray + "now they will pay for their ignorance!", "", CC.DarkRedB + "SUPREME WEAPON BONUS", CC.DarkRed + "Deal +20% damage to all enemies", CC.DarkRed + "Enemies deal 10% less damage to you.", CC.Gray + "(" + CC.GrayI + "Requires all 4 supreme items." + CC.Gray + ")" })).enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build())).setString("cosmicType", "SUPREME_SWORD").build();
/* 303 */     addEnchantment("Block", getMaxEnchantLevel("Block"), item);
/* 304 */     addEnchantment("Reflective Block", getMaxEnchantLevel("Reflective Block"), item);
/*     */     
/* 306 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\armorsets\SupremeArmorSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */