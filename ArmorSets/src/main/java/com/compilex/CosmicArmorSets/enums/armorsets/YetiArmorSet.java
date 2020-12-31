/*     */ package com.compilex.CosmicArmorSets.enums.armorsets;
/*     */ 
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*     */ import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
/*     */ import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
/*     */ import com.compilex.CosmicArmorSets.utils.ItemUtils;
/*     */ import com.cosmicpvp.cosmicutils.utils.CC;
/*     */ import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
/*     */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.rit.sucy.CustomEnchantment;
/*     */ import com.rit.sucy.EnchantmentAPI;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import org.arkhamnetwork.enchantments.EnchantmentBook;
/*     */ import org.arkhamnetwork.enchantments.util.ArmorSlot;
/*     */ import org.arkhamnetwork.enchantments.util.ArmorUtil;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class YetiArmorSet
/*     */   extends ArmorSetInfo
/*     */   implements WeaponedArmorSet
/*     */ {
/*     */   public ItemStack getHelmet(boolean applyTransmog) {
/*  32 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short)0, "&b&lYeti Facemask", new String[] { "&bThe savage mask of the Yeti.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  40 */             }), Arrays.asList(new CustomEnchantment[] { 
/*  41 */               EnchantmentAPI.getEnchantment("Drunk"), 
/*  42 */               EnchantmentAPI.getEnchantment("Enlighted"), 
/*  43 */               EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  44 */               EnchantmentAPI.getEnchantment("Armored"), 
/*  45 */               EnchantmentAPI.getEnchantment("Tank"), 
/*  46 */               EnchantmentAPI.getEnchantment("Armored"), 
/*  47 */               EnchantmentAPI.getEnchantment("Hardened"), 
/*  48 */               EnchantmentAPI.getEnchantment("Implants"), 
/*  49 */               EnchantmentAPI.getEnchantment("Valor"), 
/*  50 */               EnchantmentAPI.getEnchantment("Protection"), 
/*  51 */               EnchantmentAPI.getEnchantment("Ender Shift"), 
/*  52 */               EnchantmentAPI.getEnchantment("Arrow Deflect"), 
/*  53 */               EnchantmentAPI.getEnchantment("Death God"), 
/*  54 */               EnchantmentAPI.getEnchantment("Angelic") })));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getChestplate(boolean applyTransmog) {
/*  60 */     return getWithTransmog(applyTransmog, EnchantmentBook.applyTransmogScroll(ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short)0, "&b&lBloody Yeti Torso", new String[] { "&bThe impermeable chestplate of the Yeti.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  68 */               }), Arrays.asList(new CustomEnchantment[] { 
/*  69 */                 EnchantmentAPI.getEnchantment("Godly Overload"), 
/*  70 */                 EnchantmentAPI.getEnchantment("Blood Lust"), 
/*  71 */                 EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  72 */                 EnchantmentAPI.getEnchantment("Enlighted"), 
/*  73 */                 EnchantmentAPI.getEnchantment("Tank"), 
/*  74 */                 EnchantmentAPI.getEnchantment("Armored"), 
/*  75 */                 EnchantmentAPI.getEnchantment("Leadership"), 
/*  76 */                 EnchantmentAPI.getEnchantment("Spirit Link"), 
/*  77 */                 EnchantmentAPI.getEnchantment("Aegis"), 
/*  78 */                 EnchantmentAPI.getEnchantment("Blood Link"), 
/*  79 */                 EnchantmentAPI.getEnchantment("Guardians"), 
/*  80 */                 EnchantmentAPI.getEnchantment("Angelic") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getLeggings(boolean applyTransmog) {
/*  85 */     return getWithTransmog(applyTransmog, EnchantmentBook.applyTransmogScroll(ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short)0, "&b&lFuzzy Yeti Leggings", new String[] { "&bThe cozy yet monstrous legs of the Yeti.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  93 */               }), Arrays.asList(new CustomEnchantment[] { 
/*  94 */                 EnchantmentAPI.getEnchantment("Clarity"), 
/*  95 */                 EnchantmentAPI.getEnchantment("Enlighted"), 
/*  96 */                 EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  97 */                 EnchantmentAPI.getEnchantment("Armored"), 
/*  98 */                 EnchantmentAPI.getEnchantment("Tank"), 
/*  99 */                 EnchantmentAPI.getEnchantment("Angelic"), 
/* 100 */                 EnchantmentAPI.getEnchantment("Obsidianshield"), 
/* 101 */                 EnchantmentAPI.getEnchantment("Dodge"), 
/* 102 */                 EnchantmentAPI.getEnchantment("Sticky"), 
/* 103 */                 EnchantmentAPI.getEnchantment("Repair Guard"), 
/* 104 */                 EnchantmentAPI.getEnchantment("Valor"), 
/* 105 */                 EnchantmentAPI.getEnchantment("Lucky"), 
/* 106 */                 EnchantmentAPI.getEnchantment("Cactus") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBoots(boolean applyTransmog) {
/* 111 */     return getWithTransmog(applyTransmog, EnchantmentBook.applyTransmogScroll(ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short)0, "&b&lBig-Yeti Boots", new String[] { "&bBigfoot has nothing on these boots.", "", "&b&lYETI SET BONUS", "&b&l* &bDeal +10% damage to all enemies.", "&b&l* &bEnemies deal 10% less damage to you.", "&7(&oRequires all 4 yeti items.&7)"
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 119 */               }), Arrays.asList(new CustomEnchantment[] { 
/* 120 */                 EnchantmentAPI.getEnchantment("Gears"), 
/* 121 */                 EnchantmentAPI.getEnchantment("Deathbringer"), 
/* 122 */                 EnchantmentAPI.getEnchantment("Enlighted"), 
/* 123 */                 EnchantmentAPI.getEnchantment("Armored"), 
/* 124 */                 EnchantmentAPI.getEnchantment("Angelic"), 
/* 125 */                 EnchantmentAPI.getEnchantment("Springs"), 
/* 126 */                 EnchantmentAPI.getEnchantment("Rocket Escape"), 
/* 127 */                 EnchantmentAPI.getEnchantment("Ender Walker"), 
/* 128 */                 EnchantmentAPI.getEnchantment("Hardened"), 
/* 129 */                 EnchantmentAPI.getEnchantment("Metaphysical"), 
/* 130 */                 EnchantmentAPI.getEnchantment("Repair Guard"), 
/* 131 */                 EnchantmentAPI.getEnchantment("Valor"), 
/* 132 */                 EnchantmentAPI.getEnchantment("Tank") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getEquipDescription() {
/* 137 */     return Lists.newArrayList(new String[] { ChatColor.WHITE + "" + ChatColor.BOLD + "YETI SET BONUS: " + ChatColor.WHITE + "+10% ATK / -10% DMG" });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
/* 142 */     if (isBlockedOutpostWorld(event.getDamager())) return false;
/*     */ 
/*     */     
/* 145 */     removeResistance(damaged);
/* 146 */     event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.9D);
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
/* 152 */     if (isBlockedOutpostWorld(event.getDamager())) return false;
/*     */     
/* 154 */     removeResistance(damager);
/*     */     
/* 156 */     if (event.getEntity() instanceof Player && CosmicArmorSetsAPI.hasInfiniteLuck((Player)event.getEntity(), 1, damager)) {
/* 157 */       return false;
/*     */     }
/* 159 */     event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.1D);
/*     */     
/* 161 */     ItemStack item = damager.getItemInHand();
/* 162 */     if (event.getEntity() instanceof Player && item != null && item.getType() == Material.DIAMOND_AXE && (new NBTWrapper(item))
/* 163 */       .getString("cosmicType").equals("YETI_AXE")) {
/*     */       
/* 165 */       Player damaged = (Player)event.getEntity();
/* 166 */       int heroicArmorItems = 0;
/*     */       
/* 168 */       for (ArmorSlot slot : ArmorSlot.values()) {
/* 169 */         if (Math.random() <= 0.75D) {
/* 170 */           ArmorUtil.modifyPlayerArmor(damaged, slot, -1, false);
/*     */         }
/*     */       } 
/*     */       
/* 174 */       for (ItemStack i : damaged.getEquipment().getArmorContents()) {
/* 175 */         if (CosmicArmorSetsAPI.isHeroicArmor(i)) {
/* 176 */           heroicArmorItems++;
/*     */         }
/*     */       } 
/*     */       
/* 180 */       event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.075D + 1.0D + heroicArmorItems * 0.03D));
/*     */     } 
/* 182 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
/* 187 */     if (isBlockedOutpostWorld((Entity)damaged)) return false; 
/* 188 */     modifyDamage(damaged, event, -2.5D, multiplier);
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
/* 194 */     if (isBlockedOutpostWorld((Entity)damager)) return false; 
/* 195 */     modifyDamage(damager, event, 2.5D, multiplier);
/* 196 */     return false;
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
/*     */   public ItemStack getWeapon(boolean applyTransmog) {
/* 210 */     ItemStack item = (new NBTWrapper((new ItemBuilder(Material.DIAMOND_AXE, CC.AquaB + "Yeti Maul", new String[] { CC.Gray + "A penetrating axe used by a Yeti.", "", CC.AquaB + "YETI WEAPON BONUS", CC.Aqua + "Deal +75% Durability Damage.", CC.Aqua + "Deal +7.5% DMG to enemies", CC.Aqua + "Bypass 50% of Heroic Armor", CC.Gray + "(" + CC.GrayI + "Requires all 4 yeti items." + CC.Gray + ")" })).enchant(Enchantment.DAMAGE_ALL, 5).enchant(Enchantment.DURABILITY, 3).build())).setString("cosmicType", "YETI_AXE").build();
/*     */     
/* 212 */     addEnchantment("Silence", getMaxEnchantLevel("Silence"), item);
/* 213 */     addEnchantment("Lifesteal", getMaxEnchantLevel("Lifesteal"), item);
/* 214 */     if (Math.random() <= 0.25D) {
/* 215 */       addEnchantment("Demonic Lifesteal", ThreadLocalRandom.current().nextInt(getMaxEnchantLevel("Demonic Lifesteal")) + 1, item);
/*     */     }
/* 217 */     return item;
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\armorsets\YetiArmorSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */