/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.arkhamnetwork.enchantments.EnchantmentBook
 *  org.arkhamnetwork.enchantments.libs.PlayerPotionEffects
 *  org.arkhamnetwork.enchantments.util.ArmorSlot
 *  org.arkhamnetwork.enchantments.util.ArmorUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.potion.PotionEffectType
 */
package com.compilex.CosmicArmorSets.enums.armorsets;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSetInfo;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import com.compilex.CosmicArmorSets.enums.OtherPlayerEventListenerSet;
import com.compilex.CosmicArmorSets.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.util.ArmorSlot;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.text.SimpleDateFormat;
import java.util.*;

public class KothArmorSet
        extends ArmorSetInfo
        implements OtherPlayerEventListenerSet {
    private final SimpleDateFormat format = new SimpleDateFormat("EEE MM/dd/yy");
    private final ChatColor color = ChatColor.LIGHT_PURPLE;
    private final Set<Player> kothEquippedPlayers = new HashSet<Player>();

    @Override
    public void onEquip(Player player) {
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }

    @Override
    public void onUnequip(Player player) {
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW);
        PlayerPotionEffects.untrackPotionEffect(player, PotionEffectType.SLOW_DIGGING);
    }

    @Override
    public ItemStack getHelmet(boolean transmog) {
        return this.getHelmet(transmog, null);
    }

    public ItemStack getHelmet(boolean applyTransmog, String playerName) {
        ItemStack kothItem = this.createKothItem(Material.DIAMOND_HELMET, "Helmet", playerName);
        List<String> customEnchantments = Arrays.asList("Drunk", "Deathbringer", "Enlighted", "Armored", "Tank", "Angelic", "Implants");
        for (String s : customEnchantments) {
            this.addEnchantment(s, this.getMaxEnchantLevel(s), kothItem);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    @Override
    public ItemStack getChestplate(boolean transmog) {
        return this.getChestplate(transmog, null);
    }

    public ItemStack getChestplate(boolean applyTransmog, String playerName) {
        ItemStack kothItem = this.createKothItem(Material.DIAMOND_CHESTPLATE, "Chestplate", playerName);
        List<String> customEnchantments = Arrays.asList("Blood Lust", "Overload", "Deathbringer", "Enlighted", "Armored", "Angelic", "Tank", "Dodge");
        for (String s : customEnchantments) {
            this.addEnchantment(s, this.getMaxEnchantLevel(s), kothItem);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    @Override
    public ItemStack getLeggings(boolean transmog) {
        return this.getLeggings(transmog, null);
    }

    public ItemStack getLeggings(boolean applyTransmog, String playerName) {
        ItemStack kothItem = this.createKothItem(Material.DIAMOND_LEGGINGS, "Leggings", playerName);
        List<String> customEnchantments = Arrays.asList("Clarity", "Deathbringer", "Enlighted", "Armored", "Tank", "Angelic", "Valor", "Cactus");
        for (String s : customEnchantments) {
            this.addMaxedEnchant(EnchantmentAPI.getEnchantment(s), kothItem);
        }
        if (Math.random() < 0.075) {
            this.addMaxedEnchant(EnchantmentAPI.getEnchantment("Nature Wrath"), kothItem);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    public ItemStack addMaxedEnchant(CustomEnchantment enchantment, ItemStack item) {
        if (enchantment == null) {
            return item;
        }
        enchantment.addToItem(item, enchantment.getMaxLevel());
        return item;
    }

    @Override
    public ItemStack getBoots(boolean transmog) {
        return this.getBoots(transmog, null);
    }

    public ItemStack getBoots(boolean applyTransmog, String playerName) {
        ItemStack kothItem = this.createKothItem(Material.DIAMOND_BOOTS, "Boots", playerName);
        List<String> customEnchantments = Arrays.asList("Deathbringer", "Enlighted", "Gears", "Armored", "Tank", "Angelic", "Rocket Escape");
        for (String s : customEnchantments) {
            this.addMaxedEnchant(EnchantmentAPI.getEnchantment(s), kothItem);
        }
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(kothItem) : kothItem;
    }

    public ItemStack createKothItem(Material material, String itemName, String playerName) {
        String bold = this.color + CC.Bold + "* " + this.color;
        ItemStack kothItem = ItemUtils.buildItem(material, (short) 0, this.getKothName() + " " + CC.WhiteB + CC.Underline + itemName + CC.Reset, "", this.color + CC.Bold + "KOTH SET BONUS", bold + "+20% PvP Damage", bold + "+50% PvE Damage", bold + "Auto Bless", bold + "No Fall Damage", CC.Gray + "(" + CC.GrayI + "Requires all 4 koth items" + CC.Gray + ")", "", ChatColor.AQUA.toString() + ChatColor.UNDERLINE.toString() + this.format.format(new Date()));
        kothItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        kothItem.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        return CosmicArmorSetsAPI.convertItemToHeroic(kothItem, Objects.requireNonNull(HeroicArmorType.getFromMaterial(material)), true);
    }

    public String getKothName() {
        return ChatColor.translateAlternateColorCodes('&', "&f&l&k!&r &c&lK&6&l.&e&lO&a&l.&b&lT&5&l.&d&lH &f&l&k!&r");
    }

    @Override
    public List<String> getEquipDescription() {
        String boldStar = this.color + CC.Bold + " * " + this.color;
        return Lists.newArrayList(this.color + CC.Bold + "KOTH SET BONUS", boldStar + "+20% PvP DMG", boldStar + "+50% PvE damage", boldStar + "Auto Bless", boldStar + "No Fall Damage");
    }

    @Override
    public boolean onPlayerDamaged(Player damaged, EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.85);
        return false;
    }

    @Override
    public boolean onPlayerAttacked(Player damager, EntityDamageByEntityEvent event) {
        NBTWrapper wrapper;
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        if (event.getEntity() instanceof Player) {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.2);
        } else {
            event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.5);
        }
        ItemStack item = damager.getItemInHand();
        if (event.getEntity() instanceof Player && item != null && (item.getType() == Material.GOLD_AXE || item.getType() == Material.GOLD_SWORD) && (wrapper = new NBTWrapper(item)).getString("cosmicType").equals("HEROIC_WEAPON")) {
            String kothWeapon;
            NBTTagCompoundBuilder builder = wrapper.getCompound("conaxData");
            String string = kothWeapon = builder != null ? builder.getString("KOTH_WEAPON") : "";
            if (kothWeapon.equalsIgnoreCase("AXE")) {
                Player damaged = (Player) event.getEntity();
                for (ArmorSlot slot : ArmorSlot.values()) {
                    if (!(Math.random() <= 0.5)) continue;
                    ArmorUtil.modifyPlayerArmor(damaged, slot, -(2 + this.random.nextInt(2)), false);
                    Bukkit.getLogger().info("Damaging " + slot + " from " + damaged.getName() + " from " + damager.getName() + "'s Koth Axe!");
                }
            } else if (kothWeapon.equalsIgnoreCase("SWORD")) {
                event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.075);
            }
        }
        return false;
    }

    @Override
    public boolean onPlayerDamagedBonus(Player damaged, int multiplier, EntityDamageByEntityEvent event) {
        return false;
    }

    @Override
    public boolean onPlayerAttackedBonus(Player damager, int multiplier, EntityDamageByEntityEvent event) {
        if (this.isBlockedOutpostWorld(damager)) {
            return false;
        }
        return this.modifyDamage(damager, event, event.getEntity() instanceof Player ? 5.0 : 12.5, multiplier);
    }

    @Override
    public void onPlayerEvent(Event event) {
        if (event instanceof EntityDamageEvent) {
            EntityDamageEvent damageEvent = (EntityDamageEvent) event;
            if (damageEvent.getCause() == EntityDamageEvent.DamageCause.FALL) {
                damageEvent.setCancelled(true);
            }
        } else if (event instanceof PlayerQuitEvent) {
            this.kothEquippedPlayers.remove(((PlayerQuitEvent) event).getPlayer());
        }
    }
}

