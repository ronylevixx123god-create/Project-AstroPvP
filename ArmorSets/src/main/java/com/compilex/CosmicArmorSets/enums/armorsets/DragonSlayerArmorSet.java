/*     */ package com.compilex.CosmicArmorSets.enums.armorsets;
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
/*     */ import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
/*     */ import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
/*     */ import com.compilex.CosmicArmorSets.utils.ItemUtils;
/*     */ import com.cosmicpvp.cosmicutils.utils.CC;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.rit.sucy.CustomEnchantment;
/*     */ import com.rit.sucy.EnchantmentAPI;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.arkhamnetwork.enchantments.EnchantmentBook;
/*     */ import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class DragonSlayerArmorSet extends ArmorSetInfo implements OtherPlayerEventListenerSet {
/*  28 */   private ChatColor color = ChatColor.YELLOW;
/*     */ 
/*     */   
/*     */   public void onEquip(Player player) {
/*  32 */     player.setMetadata("mastery_reflect_enchant", (MetadataValue)new FixedMetadataValue((Plugin)CosmicArmorSetsPlugin.get_(), Integer.valueOf(10)));
/*  33 */     player.setMetadata("immune_silence", (MetadataValue)new FixedMetadataValue((Plugin)CosmicArmorSetsPlugin.get_(), Long.valueOf(System.currentTimeMillis())));
/*  34 */     player.setMetadata("immune_freeze", (MetadataValue)new FixedMetadataValue((Plugin)CosmicArmorSetsPlugin.get_(), Long.valueOf(System.currentTimeMillis())));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUnequip(Player player) {
/*  39 */     player.removeMetadata("mastery_reflect_enchant", (Plugin)CosmicArmorSetsPlugin.get_());
/*  40 */     player.removeMetadata("immune_silence", (Plugin)CosmicArmorSetsPlugin.get_());
/*  41 */     player.removeMetadata("immune_freeze", (Plugin)CosmicArmorSetsPlugin.get_());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getHelmet(boolean applyTransmog) {
/*  46 */     ItemStack kothItem = createArmor(Material.DIAMOND_HELMET, "Decapitated Dragon Skull", "helmet");
/*     */     
/*  48 */     addEnchants(kothItem, 
/*  49 */         Lists.newArrayList(new String[] { "Drunk", "Armored", "Deathbringer", "Tank", "Angelic", "Implants", "Valor", "Death God", "Repair Guard", "Ender Shift", "Paladin Armored", "Planetary Deathbringer", "Alien Implants" }), 0.4D);
/*     */ 
/*     */ 
/*     */     
/*  53 */     if (this.random.nextDouble() <= 0.3D) {
/*  54 */       addEnchantment("Immortal", ItemUtils.getRandomArmorSetEnchantLevel("Immortal"), kothItem);
/*     */     }
/*  56 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getChestplate(boolean applyTransmog) {
/*  61 */     ItemStack kothItem = createArmor(Material.DIAMOND_CHESTPLATE, "Firey Chestplate of Dragons", "chestplate");
/*     */     
/*  63 */     addEnchants(kothItem, Lists.newArrayList(new String[] { "Overload", "Leadership", "Armored", "Tank", "Angelic", "Valor", "Aegis", "Dodge", "Frozen", "Enchant Reflect", "Godly Overload" }), 0.4D);
/*     */ 
/*     */     
/*  66 */     if (this.random.nextDouble() <= 0.05D) {
/*  67 */       addMaxedEnchant(getEnchantment("Enchant Reflect"), kothItem);
/*  68 */       addEnchantment("Heroic Enchant Reflect", ItemUtils.getRandomArmorSetEnchantLevel("Heroic Enchant Reflect"), kothItem);
/*     */     } 
/*  70 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getLeggings(boolean applyTransmog) {
/*  76 */     ItemStack kothItem = createArmor(Material.DIAMOND_LEGGINGS, "Scorched Leggings of Dragons", "leggings");
/*     */     
/*  78 */     addEnchants(kothItem, Lists.newArrayList(new String[] { "Enlighted", "Lucky", "Obsidianshield", "Armored", "Tank", "Valor", "Angelic", "Arrow Deflect", "Clarity", "Cactus", "Divine Enlighted" }), 0.4D);
/*     */     
/*  80 */     if (Math.random() < 0.075D) {
/*  81 */       addMaxedEnchant(EnchantmentAPI.getEnchantment("Nature Wrath"), kothItem);
/*     */     }
/*  83 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack addEnchants(ItemStack item, List<String> enchants, double heroic) {
/*  88 */     for (String cen : enchants) {
/*  89 */       CustomEnchantment ce = EnchantmentAPI.getEnchantment(cen);
/*  90 */       if (ce == null)
/*  91 */         continue;  if (ce.heroicEnchantment) {
/*  92 */         if (heroic > 0.0D && Math.random() > heroic)
/*  93 */           continue;  ce.nonHeroicVersion.addToItem(item, ce.nonHeroicVersion.getMaxLevel());
/*  94 */         ce.addToItem(item, 1 + (new Random()).nextInt(ce.getMaxLevel())); continue;
/*     */       } 
/*  96 */       ce.addToItem(item, Math.min(ce.getMaxLevel(), Math.max(1, ce.getMaxLevel() - 2) + this.random.nextInt(3)));
/*     */     } 
/*     */     
/*  99 */     return item;
/*     */   }
/*     */   
/*     */   public ItemStack addMaxedEnchant(CustomEnchantment enchantment, ItemStack item) {
/* 103 */     if (enchantment == null) return item; 
/* 104 */     enchantment.addToItem(item, enchantment.getMaxLevel());
/* 105 */     return item;
/*     */   }
/*     */   
/*     */   public ItemStack addRandomLeveledEnchant(String enchantName, ItemStack item) {
/* 109 */     CustomEnchantment enchantment = EnchantmentAPI.getEnchantment(enchantName);
/* 110 */     if (enchantment == null) return item; 
/* 111 */     enchantment.addToItem(item, ((enchantment.getMaxLevel() > 1) ? this.random.nextInt(enchantment.getMaxLevel()) : 0) + 1);
/* 112 */     return item;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBoots(boolean applyTransmog) {
/* 117 */     ItemStack kothItem = createArmor(Material.DIAMOND_BOOTS, "Dragon Slayer Battle Boots", "boots");
/* 118 */     addEnchants(kothItem, Lists.newArrayList(new String[] { "Gears", "Rocket Escape", "Armored", "Tank", "Valor", "Angelic", "Springs", "Gaurdians", "Voodoo", "Ender Walker", "Ghost", "Ghostly Ghost" }), 0.4D);
/*     */     
/* 120 */     if (this.random.nextDouble() <= 0.075D) {
/* 121 */       addEnchantment("Phoenix", ItemUtils.getRandomArmorSetEnchantLevel("Phoenix"), kothItem);
/*     */     }
/* 123 */     return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getEquipDescription() {
/* 128 */     return Lists.newArrayList(new String[] { CC.YellowB + "DRAGON SLAYER SET BONUS:", CC.YellowB + " * " + CC.Yellow + "+15% OUTGOING DAMAGE", CC.YellowB + " * " + CC.Yellow + "-20% INCOMING DAMAGE", CC.YellowB + " * " + CC.Yellow + "Immune to Freezes", CC.YellowB + " * " + CC.Yellow + "Mastery Enchant Reflect X", CC.YellowB + " * " + CC.Yellow + "Negate 75% of Enemy Silence", CC.YellowB + " * " + CC.Yellow + "25% Mastery Enchant Negation" });
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
/*     */   public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
/* 140 */     if (isBlockedOutpostWorld((Entity)damaged)) return false; 
/* 141 */     //event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.8D);
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
/* 147 */     if (isBlockedOutpostWorld((Entity)damager)) return false;
/*     */     
/* 149 */     if (event.getEntity() instanceof Player) {
/* 150 */       //event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.15D);
/*     */     }
/* 152 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
/* 157 */     if (isBlockedOutpostWorld((Entity)damaged)) return false;
/*     */     
/* 159 */     return modifyDamage(damaged, event, -5.0D, multiplier);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
/* 164 */     if (isBlockedOutpostWorld((Entity)damager)) return false;
/*     */     
/* 166 */     if (event.getEntity() instanceof Player) {
/* 167 */       return modifyDamage(damager, event, 3.0D, multiplier);
/*     */     }
/* 169 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onEnchantImmuneToProced(Player player, Player damager, String enchant, int multiplier, CustomEnchantProcOnEntityEvent event) {
/* 174 */     if (Math.random() <= multiplier * 0.1D) {
/* 175 */       event.setCancelled(true);
/* 176 */       player.sendMessage(CC.YellowB + "* DRAGON SLAYER CRYSTAL [" + CC.Gray + (enchant.startsWith("Ice") ? "Ice Aspect" : enchant) + " blocked" + CC.YellowB + "] *");
/*     */     } 
/* 178 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack createArmor(Material material, String itemName, String loreType) {
/* 182 */     String bold = this.color + CC.Bold + "* " + this.color;
/* 183 */     ItemStack armor = ItemUtils.buildItem(material, (short)0, this.color + CC.Bold + itemName + CC.Reset, new String[] { this.color + CC.Italic + "The mythical " + loreType + " of a Slayer of Dragons.", "", this.color + CC.Bold + "DRAGON SLAYER SET BONUS", bold + "+15% PvP Damage", bold + "-20% Incoming Damage", bold + "Immune to Freezes", bold + "Mastery Enchant Reflect X", bold + "Negate 75% of Enemy Silence", bold + "25% Mastery Enchant Negation", CC.Gray + "(" + CC.GrayI + "Requires all 4 dragon slayer items" + CC.Gray + ")" });
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
/* 196 */     armor.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
/* 197 */     armor.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
/* 198 */     return armor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerEvent(Event event) {
/* 204 */     if (event instanceof PlayerQuitEvent)
/* 205 */       onUnequip(((PlayerQuitEvent)event).getPlayer()); 
/*     */   }
/*     */ }


