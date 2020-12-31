/*     */ package com.compilex.CosmicArmorSets.enums.armorsets;
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*     */ import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.WeaponedArmorSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
/*     */ import com.compilex.CosmicArmorSets.utils.LocationUtils;
import com.cosmicpvp.cosmicutils.utils.*;
/*     */
/*     */
/*     */ import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
/*     */ import com.massivecraft.factions.FPlayers;
/*     */ import com.massivecraft.factions.iface.RelationParticipator;
/*     */ import com.massivecraft.factions.struct.Relation;
/*     */ import com.rit.sucy.CustomEnchantment;
/*     */ import com.rit.sucy.EnchantmentAPI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ThreadLocalRandom;

import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
/*     */ import org.arkhamnetwork.enchantments.enchantments.armor.Phoenix;
/*     */ import org.bukkit.*;
            import me.astropvp.astropvpskins.AstroItemSkinsAPI;
/*     */
/*     */
/*     */
/*     */
/*     */ import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/*     */
/*     */ public class MotherOfYijki extends ArmorSetInfo implements WeaponedArmorSet {
/*     */   public ItemStack getHelmet(boolean applyTransmog) {
/*  33 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_HELMET, (short)0, "&f&lMask of Yijki the Destroyer of Worlds", new String[] { "&f&oThe god-mask of Yijki, imbued with", "&f&othe power of absolute destruction.", "", "&f&lYIJKI SET BONUS", "&f&l* &fEnemies deal 30% less damage to you.", "&f&l* &fRevenge of Yijki Passive Ability", "&7(&oRequires all 4 yijki items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  42 */             Arrays.asList((Object[])new CustomEnchantment[] { 
/*  43 */                 EnchantmentAPI.getEnchantment("Planetary Deathbringer"), 
/*  44 */                 EnchantmentAPI.getEnchantment("Drunk"), 
/*  45 */                 EnchantmentAPI.getEnchantment("Enlighted"), 
/*  46 */                 EnchantmentAPI.getEnchantment("Armored"), 
/*  47 */                 EnchantmentAPI.getEnchantment("Tank"), 
/*  48 */                 EnchantmentAPI.getEnchantment("Angelic"), 
/*  49 */                 EnchantmentAPI.getEnchantment("Hardened"), 
/*  50 */                 EnchantmentAPI.getEnchantment("Valor"), 
/*  51 */                 EnchantmentAPI.getEnchantment("Dodge"), 
/*  52 */                 EnchantmentAPI.getEnchantment("Guardians"), 
/*  53 */                 EnchantmentAPI.getEnchantment("Sticky"), 
/*  54 */                 EnchantmentAPI.getEnchantment("Implants"), 
/*  55 */                 EnchantmentAPI.getEnchantment("Repair Guard") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getChestplate(boolean applyTransmog) {
/*  60 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_CHESTPLATE, (short)0, "&f&lMantel of Yijki the Destroyer of Worlds", new String[] { "&f&oThe forebidden mantel of Yijki,", "&f&owoven from the dust of fallen stars.", "", "&f&lYIJKI SET BONUS", "&f&l* &fEnemies deal 30% less damage to you.", "&f&l* &fRevenge of Yijki Passive Ability", "&7(&oRequires all 4 yijki items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  69 */             Arrays.asList((Object[])new CustomEnchantment[] { 
/*  70 */                 EnchantmentAPI.getEnchantment("Godly Overload"), 
/*  71 */                 EnchantmentAPI.getEnchantment("Blood Lust"), 
/*  72 */                 EnchantmentAPI.getEnchantment("Enlighted"), 
/*  73 */                 EnchantmentAPI.getEnchantment("Deathbringer"), 
/*  74 */                 EnchantmentAPI.getEnchantment("Armored"), 
/*  75 */                 EnchantmentAPI.getEnchantment("Tank"), 
/*  76 */                 EnchantmentAPI.getEnchantment("Angelic"), 
/*  77 */                 EnchantmentAPI.getEnchantment("Arrow Deflect"), 
/*  78 */                 EnchantmentAPI.getEnchantment("Dodge"), 
/*  79 */                 EnchantmentAPI.getEnchantment("Valor"), 
/*  80 */                 EnchantmentAPI.getEnchantment("Hardened"), 
/*  81 */                 EnchantmentAPI.getEnchantment("Aegis"), 
/*  82 */                 EnchantmentAPI.getEnchantment("Spirit Link"), 
/*  83 */                 EnchantmentAPI.getEnchantment("Blood Link"), 
/*  84 */                 EnchantmentAPI.getEnchantment("Leadership") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getLeggings(boolean applyTransmog) {
/*  89 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_LEGGINGS, (short)0, "&f&lRobeset of Yijki the Destroyer of Worlds", new String[] { "&f&oThe fabled robes of Yijki,", "&f&ohalf angelic, half demonic.", "", "&f&lYIJKI SET BONUS", "&f&l* &fEnemies deal 30% less damage to you.", "&f&l* &fRevenge of Yijki Passive Ability", "&7(&oRequires all 4 yijki items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  98 */             Arrays.asList((Object[])new CustomEnchantment[] { 
/*  99 */                 EnchantmentAPI.getEnchantment("Enlighted"), 
/* 100 */                 EnchantmentAPI.getEnchantment("Clarity"), 
/* 101 */                 EnchantmentAPI.getEnchantment("Deathbringer"), 
/* 102 */                 EnchantmentAPI.getEnchantment("Nature Wrath"), 
/* 103 */                 EnchantmentAPI.getEnchantment("Lucky"), 
/* 104 */                 EnchantmentAPI.getEnchantment("Armored"), 
/* 105 */                 EnchantmentAPI.getEnchantment("Tank"), 
/* 106 */                 EnchantmentAPI.getEnchantment("Angelic"), 
/* 107 */                 EnchantmentAPI.getEnchantment("Obsidianshield"), 
/* 108 */                 EnchantmentAPI.getEnchantment("Cactus"), 
/* 109 */                 EnchantmentAPI.getEnchantment("Valor"), 
/* 110 */                 EnchantmentAPI.getEnchantment("Hardened"), 
/* 111 */                 EnchantmentAPI.getEnchantment("Metaphysical"), 
/* 112 */                 EnchantmentAPI.getEnchantment("Death God"), 
/* 113 */                 EnchantmentAPI.getEnchantment("Dodge") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getBoots(boolean applyTransmog) {
/* 118 */     return getWithTransmog(applyTransmog, ItemUtils.applyCustomEnchantments(ItemUtils.buildItem(Material.DIAMOND_BOOTS, (short)0, "&f&lFootwraps of Yijki the Destroyer of Worlds", new String[] { "&f&oLight as a feather, heavy as", "&f&oa mountain - the footwraps of Yijki.", "", "&f&lYIJKI SET BONUS", "&f&l* &fEnemies deal 30% less damage to you.", "&f&l* &fRevenge of Yijki Passive Ability", "&7(&oRequires all 4 yijki items.&7)" }), new LinkedList(
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 127 */             Arrays.asList((Object[])new CustomEnchantment[] { 
/* 128 */                 EnchantmentAPI.getEnchantment("Divine Enlighted"), 
/* 129 */                 EnchantmentAPI.getEnchantment("Gears"), 
/* 130 */                 EnchantmentAPI.getEnchantment("Deathbringer"), 
/* 131 */                 EnchantmentAPI.getEnchantment("Armored"), 
/* 132 */                 EnchantmentAPI.getEnchantment("Tank"), 
/* 133 */                 EnchantmentAPI.getEnchantment("Angelic"), 
/* 134 */                 EnchantmentAPI.getEnchantment("Ender Walker"), 
/* 135 */                 EnchantmentAPI.getEnchantment("Hardened"), 
/* 136 */                 EnchantmentAPI.getEnchantment("Rocket Escape"), 
/* 137 */                 EnchantmentAPI.getEnchantment("Springs"), 
/* 138 */                 EnchantmentAPI.getEnchantment("Valor"), 
/* 139 */                 EnchantmentAPI.getEnchantment("Dodge"), 
/* 140 */                 EnchantmentAPI.getEnchantment("Guardians"), 
/* 141 */                 EnchantmentAPI.getEnchantment("Sticky"), 
/* 142 */                 EnchantmentAPI.getEnchantment("Diminish") }))));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean supportsBowDamage() {
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getEquipDescription() {
/* 152 */     return Lists.newArrayList(new String[] { ChatColor.RED + "" + ChatColor.BOLD + "MOTHER OF YIJKI SET BONUS: " + ChatColor.RED + "-30% INCOMING DMG" });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
/* 157 */     if (isBlockedOutpostWorld(event.getDamager())) {
/* 158 */       return false;
/*     */     }



/* 160 */     event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.7D);
/* 161 */     if (event.getEntity() instanceof Player) {
/* 162 */       removeResistance((Player)event.getEntity());
/*     */     }
/* 164 */     double yijkiAbilityChance = 0.05D;
/* 165 */     ItemStack handItem = damaged.getItemInHand();
/* 166 */     if (handItem != null) {
/* 167 */       Material type = handItem.getType();
/* 168 */       if (type != null && type == Material.DIAMOND_SWORD && (new NBTWrapper(handItem)).getString("cosmicType").equals("YIJKI_SWORD")) {
/* 169 */         yijkiAbilityChance *= 1.25D;
/*     */       }
/*     */     } 
/*     */     
/* 173 */     if (Math.random() < yijkiAbilityChance) {
/* 174 */       activateYijki(event.getEntity().getLocation(), damaged);
/*     */     }
/* 176 */     return true;
/*     */   }
/*     */   
/*     */   public void activateYijki(Location center, Player player) {
/* 180 */     List<Location> procLocations = new ArrayList<>();
/* 181 */     List<Player> nearbyPlayers = new ArrayList<>();
/*     */     
/* 183 */     FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
/*     */     
/* 185 */     for (int i = 0; i < 16; i++) {
/* 186 */       Location l = LocationUtils.getNearbyLocation(center, 8, 2, 0);
/* 187 */       l.getWorld().playSound(l, Sound.WITHER_SPAWN, 1.0F, 0.4F);
/* 188 */       ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, l, 0.7F, 32);
/* 189 */       ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, l.clone().add(0.0D, 1.0D, 0.0D), 0.7F, 32);
/* 190 */       procLocations.add(l);
/*     */     } 
/*     */     
/* 193 */     for (Entity nearbyEnt : player.getNearbyEntities(32.0D, 64.0D, 32.0D)) {
/* 194 */       if (nearbyEnt instanceof Player) {
/* 195 */         Player pNear = (Player)nearbyEnt;
/* 196 */         FPlayer fpNear = (FPlayer)FPlayers.i.get((OfflinePlayer)pNear);
/*     */         
/* 198 */         if (CosmicArmorSetsAPI.hasInfiniteLuck(pNear, 4, player)) {
/*     */           continue;
/*     */         }
            if (AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CUPID_AMULET)) {
                player.sendMessage(CC.translate("&c&l* Amulet of Angles &7(Block Yijki) &c&l*"));
                continue;
            }
/* 201 */         if (fpNear.getRelationTo((RelationParticipator)fp).isAtLeast(Relation.TRUCE)) {
/*     */           continue;
/*     */         }
/*     */         
/* 205 */         if (!WorldGuardUtils.isPvPEnabled(pNear.getLocation())) {
/*     */           continue;
/*     */         }
/* 208 */         pNear.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "** REVENGE OF YIJKI (" + ChatColor.RED + fp.getNameAsync() + " [1.5s]" + ChatColor.DARK_PURPLE + ChatColor.BOLD + ") **");
/* 209 */         nearbyPlayers.add(pNear);
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     Bukkit.getScheduler().runTaskLater((Plugin) CosmicArmorSetsPlugin.get_(), () -> { for (Location l : procLocations) { l.getWorld().strikeLightningEffect(l); l.getWorld().playSound(l, Sound.WITHER_DEATH, 1.0F, 0.4F); ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, l, 0.7F, 4); ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, l.clone().add(0.0D, 1.0D, 0.0D), 0.7F, 4); for (Player pNear : nearbyPlayers) { if (pNear.getLocation().distanceSquared(l) <= 2.0D) { FPlayer fpNear = (FPlayer)FPlayers.i.get((OfflinePlayer)pNear); if (fpNear.getRelationTo((RelationParticipator)fp).isAtLeast(Relation.TRUCE)) continue;  if (CosmicArmorSetsAPI.hasInfiniteLuck(pNear, 4, player)) continue;  if (!WorldGuardUtils.isPvPEnabled(pNear.getLocation())) continue;  double dmg = CosmicArmorSetsAPI.getPoltergeistChance(player); if (dmg >= ThreadLocalRandom.current().nextDouble(100.0D) + 1.0D) { pNear.sendMessage(CC.DarkRedB + "* POLTERGEIST [" + CC.Gray + "Immune: Mother of Yijki" + CC.DarkRedB + "] *"); continue; }  int damage = 16; int heroLevel = UpgradeUtils.getUpgradeLevel(pNear, "HEROIC_ARMOR_SET"); damage = (int)(damage * ((heroLevel == 1) ? 0.95D : ((heroLevel == 2) ? 0.9D : ((heroLevel == 3) ? 0.85D : ((heroLevel == 4) ? 0.75D : 1.0D))))); if (Phoenix.hasPhoenixEquipped(pNear) && Phoenix.procPhoenix(pNear, (LivingEntity)player, damage)) continue;  pNear.setHealth(Math.max(1.0D, pNear.getHealth() - damage)); }  }  }  }, 20L);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
/* 252 */     removeResistance(damager);
/* 253 */     if (isBlockedOutpostWorld((Entity)damager)) {
/* 254 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 261 */     ItemStack handItem = damager.getItemInHand();
/* 262 */     if (handItem != null) {
/* 263 */       Material type = handItem.getType();
/* 264 */       if (type != null && (type == Material.DIAMOND_SWORD || type == Material.GOLD_SWORD) && (new NBTWrapper(handItem)).getString("cosmicType").equals("YIJKI_SWORD")) {
/* 265 */         event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.2D);
/*     */       }
/*     */     } 
/* 268 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getWeapon(boolean applyTransmog) {
/* 273 */     return (new NBTWrapper((new ItemBuilder(Material.DIAMOND_SWORD, CC.WhiteB + "Yijki's World Ender", new String[] { CC.Gray + "An absolutely massive, corrupted", CC.Gray + "executioner blade imbued with", CC.Gray + "highly destructive dark magic.", "", CC.WhiteB + "YIJKI WEAPON BONUS", CC.White + "Deal +20% damage to all enemies.", CC.White + "125% Revenge of Yijki Ability", CC.White + "Immune to Rot and Decay", CC.Gray + "(" + CC.GrayI + "Requires all 4 yijki items." + CC.Gray + ")"
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
/* 284 */           })).enchant(Enchantment.DAMAGE_ALL, 5)
/* 285 */         .enchant(Enchantment.DURABILITY, 3).build()))
/* 286 */       .setString("cosmicType", "YIJKI_SWORD").build();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
/* 291 */     if (isBlockedOutpostWorld((Entity)damaged)) return false;
/*     */     
/* 293 */     modifyDamage(damaged, event, -5.0D, multiplier);
/* 294 */     if (Math.random() < multiplier * 0.010000000000000002D) {
/* 295 */       activateYijki(damaged.getLocation(), damaged);
/*     */     }
/* 297 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
/* 302 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\armorsets\MotherOfYijki.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */