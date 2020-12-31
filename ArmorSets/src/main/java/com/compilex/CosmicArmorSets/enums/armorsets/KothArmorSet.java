/*     */
package com.compilex.CosmicArmorSets.enums.armorsets;
/*     */

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
/*     */ import com.compilex.CosmicArmorSets.enums.ArmorSet;
/*     */ import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
/*     */ import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
/*     */ import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
/*     */ import com.compilex.CosmicArmorSets.utils.ItemUtils;
/*     */ import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
/*     */ import com.cosmicpvp.cosmicutils.utils.CC;
/*     */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.rit.sucy.CustomEnchantment;
/*     */ import com.rit.sucy.EnchantmentAPI;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.arkhamnetwork.enchantments.EnchantmentBook;
/*     */ import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
/*     */ import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.util.ArmorSlot;
/*     */ import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

/*     */
/*     */ public class KothArmorSet extends ArmorSetInfo implements OtherPlayerEventListenerSet {
    /*  35 */   private SimpleDateFormat format = new SimpleDateFormat("EEE MM/dd/yy");
    /*  36 */   private ChatColor color = ChatColor.LIGHT_PURPLE;
    /*     */
    /*  38 */   private Set<Player> kothEquippedPlayers = new HashSet<>();

    /*     */
    /*     */
    /*     */
    public void onEquip(Player player) {
        /*  42 */
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public void onUnequip(Player player) {
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }
    /*  47 */
    /*     */

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public ItemStack getHelmet(boolean transmog) {
        /*  61 */
        return getHelmet(transmog, (String) null);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public ItemStack getHelmet(boolean applyTransmog, String playerName) {
        /*  66 */
        ItemStack kothItem = createKothItem(Material.DIAMOND_HELMET, "Helmet", playerName);
        /*     */
        /*  68 */
        List<String> customEnchantments = Arrays.asList(new String[]{"Drunk", "Deathbringer", "Enlighted", "Armored", "Tank", "Angelic", "Implants"});
        /*  69 */
        for (String s : customEnchantments) {
            /*  70 */
            addEnchantment(s, getMaxEnchantLevel(s), kothItem);
            /*     */
        }
        /*     */
        /*  73 */
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public ItemStack getChestplate(boolean transmog) {
        /*  79 */
        return getChestplate(transmog, (String) null);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public ItemStack getChestplate(boolean applyTransmog, String playerName) {
        /*  84 */
        ItemStack kothItem = createKothItem(Material.DIAMOND_CHESTPLATE, "Chestplate", playerName);
        /*     */
        /*  86 */
        List<String> customEnchantments = Arrays.asList(new String[]{"Blood Lust", "Overload", "Deathbringer", "Enlighted", "Armored", "Angelic", "Tank", "Dodge"});
        /*  87 */
        for (String s : customEnchantments) {
            /*  88 */
            addEnchantment(s, getMaxEnchantLevel(s), kothItem);
            /*     */
        }
        /*     */
        /*  91 */
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public ItemStack getLeggings(boolean transmog) {
        /*  97 */
        return getLeggings(transmog, (String) null);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public ItemStack getLeggings(boolean applyTransmog, String playerName) {
        /* 102 */
        ItemStack kothItem = createKothItem(Material.DIAMOND_LEGGINGS, "Leggings", playerName);
        /* 103 */
        List<String> customEnchantments = Arrays.asList(new String[]{"Clarity", "Deathbringer", "Enlighted", "Armored", "Tank", "Angelic", "Valor", "Cactus"});
        /* 104 */
        for (String s : customEnchantments) {
            /* 105 */
            addMaxedEnchant(EnchantmentAPI.getEnchantment(s), kothItem);
            /*     */
        }
        /* 107 */
        if (Math.random() < 0.075D) {
            /* 108 */
            addMaxedEnchant(EnchantmentAPI.getEnchantment("Nature Wrath"), kothItem);
            /*     */
        }
        /*     */
        /*     */
        /* 112 */
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public ItemStack addMaxedEnchant(CustomEnchantment enchantment, ItemStack item) {
        /* 118 */
        if (enchantment == null) return item;
        /* 119 */
        enchantment.addToItem(item, enchantment.getMaxLevel());
        /* 120 */
        return item;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public ItemStack getBoots(boolean transmog) {
        /* 126 */
        return getBoots(transmog, (String) null);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public ItemStack getBoots(boolean applyTransmog, String playerName) {
        /* 131 */
        ItemStack kothItem = createKothItem(Material.DIAMOND_BOOTS, "Boots", playerName);
        /*     */
        /* 133 */
        List<String> customEnchantments = Arrays.asList(new String[]{"Deathbringer", "Enlighted", "Gears", "Armored", "Tank", "Angelic", "Rocket Escape"});
        /* 134 */
        for (String s : customEnchantments) {
            /* 135 */
            addMaxedEnchant(EnchantmentAPI.getEnchantment(s), kothItem);
            /*     */
        }
        /*     */
        /*     */
        /* 139 */
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public ItemStack createKothItem(Material material, String itemName, String playerName) {
        /* 144 */
        String bold = this.color + CC.Bold + "* " + this.color;
        /* 145 */
        ItemStack kothItem = ItemUtils.buildItem(material, (short) 0,
                /* 146 */         getKothName() + " " + CC.WhiteB + CC.Underline + itemName + CC.Reset, "", this.color + CC.Bold + "KOTH SET BONUS", bold + "+20% PvP Damage", bold + "+50% PvE Damage", bold + "Auto Bless", bold + "No Fall Damage", CC.Gray + "(" + CC.GrayI + "Requires all 4 koth items" + CC.Gray + ")", "", ChatColor.AQUA.toString() + ChatColor.UNDERLINE.toString() + this.format.format(new Date()));
        /*     */
        /*     */
        /* 159 */
        kothItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        /* 160 */
        kothItem.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        /* 161 */
        return CosmicArmorSetsAPI.convertItemToHeroic(kothItem, HeroicArmorType.getFromMaterial(material), true);
        /*     */
    }

    /*     */
    /*     */
    public String getKothName() {
        /* 165 */
        return ChatColor.translateAlternateColorCodes('&', "&f&l&k!&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k!&r");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public List<String> getEquipDescription() {
        /* 171 */
        String boldStar = this.color + CC.Bold + " * " + this.color;
        /* 172 */
        return Lists.newArrayList(new String[]{this.color + CC.Bold + "KOTH SET BONUS", boldStar + "+20% PvP DMG", boldStar + "+50% PvE damage", boldStar + "Auto Bless", boldStar + "No Fall Damage"});
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        /* 182 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        /* 189 */
        if (isBlockedOutpostWorld((Entity) damager)) return false;
        /*     */
        /* 191 */
        if (event.getEntity() instanceof Player) {
            /* 192 */
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.2D);
            /*     */
        } else {
            /* 194 */
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.5D);
            /*     */
        }
        /* 196 */
        ItemStack item = damager.getItemInHand();
        /* 197 */
        if (event.getEntity() instanceof Player && item != null && (item
/* 198 */.getType() == Material.GOLD_AXE || item.getType() == Material.GOLD_SWORD)) {
            /* 199 */
            NBTWrapper wrapper = new NBTWrapper(item);
            /* 200 */
            if (wrapper.getString("cosmicType").equals("HEROIC_WEAPON")) {
                /* 201 */
                NBTTagCompoundBuilder builder = wrapper.getCompound("cosmicData");
                /* 202 */
                String kothWeapon = (builder != null) ? builder.getString("KOTH_WEAPON") : "";
                /* 203 */
                if (kothWeapon.equalsIgnoreCase("AXE")) {
                    /*     */
                    /* 205 */
                    Player damaged = (Player) event.getEntity();
                    /* 206 */
                    for (ArmorSlot slot : ArmorSlot.values()) {
                        /* 207 */
                        if (Math.random() <= 0.5D) {
                            /* 208 */
                            ArmorUtil.modifyPlayerArmor(damaged, slot, -(2 + this.random.nextInt(2)), false);
                            /* 209 */
                            Bukkit.getLogger().info("Damaging " + slot + " from " + damaged.getName() + " from " + damager.getName() + "'s Koth Axe!");
                            /*     */
                        }
                        /*     */
                    }
                    /* 212 */
                } else if (kothWeapon.equalsIgnoreCase("SWORD")) {
                    /* 213 */
                    event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.075D);
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */
        /* 218 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        /* 223 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        /* 228 */
        if (isBlockedOutpostWorld((Entity) damager)) return false;
        /*     */
        /* 230 */
        return modifyDamage(damager, event, (event.getEntity() instanceof Player) ? 5.0D : 12.5D, multiplier);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public void onPlayerEvent(Event event) {
        /* 235 */
        if (event instanceof EntityDamageEvent) {
            /* 236 */
            EntityDamageEvent damageEvent = (EntityDamageEvent) event;
            /* 237 */
            if (damageEvent.getCause() == EntityDamageEvent.DamageCause.FALL) {
                /* 238 */
                damageEvent.setCancelled(true);
                /*     */
            }
            /* 240 */
        } else if (event instanceof PlayerQuitEvent) {
            /* 241 */
            this.kothEquippedPlayers.remove(((PlayerQuitEvent) event).getPlayer());
            /*     */
        }
        /*     */
    }
    /*     */
}


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\enums\armorsets\KothArmorSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */