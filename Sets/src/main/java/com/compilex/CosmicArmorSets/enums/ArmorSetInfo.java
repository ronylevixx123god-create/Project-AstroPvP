package com.compilex.CosmicArmorSets.enums;

import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.EnchantmentBook;
import org.arkhamnetwork.enchantments.enchantments.armor.Phoenix;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class ArmorSetInfo {
    protected Random random = ThreadLocalRandom.current();

    public static boolean subtractDirectHealth(Player player, LivingEntity damager, double toSubtract) throws Exception {
        if (player.getHealth() <= 0.0) {
            return false;
        }
        double newHealth = player.getHealth() - toSubtract;
        if (newHealth <= 0.0 && Phoenix.hasPhoenixEquipped(player) && Phoenix.procPhoenix(player, damager, toSubtract)) {
            Bukkit.getLogger().info("[ArmorSets] Not killing " + player.getName() + " due to Phoenix proc!");
            return false;
        }
        if (newHealth < 0.0) {
            newHealth = 0.0;
        }
        player.setHealth(newHealth);
        return true;
    }

    public boolean supportsBowDamage() {
        return false;
    }

    public void onEquip(Player player) {
    }

    public void onUnequip(Player player) {
    }

    public List<String> getArmorItemDescription() {
        return null;
    }

    public ItemStack addEnchantment(String name, int level, ItemStack item) {
        return EnchantmentAPI.getEnchantment(name).addToItem(item, level);
    }

    public CustomEnchantment getEnchantment(String name) {
        return EnchantmentAPI.getEnchantment(name);
    }

    public int getMaxEnchantLevel(String name) {
        return EnchantmentAPI.getEnchantment(name).getMaxLevel();
    }

    public abstract ItemStack getHelmet(boolean var1);

    public abstract ItemStack getChestplate(boolean var1);

    public abstract ItemStack getLeggings(boolean var1);

    public abstract ItemStack getBoots(boolean var1);

    public abstract List<String> getEquipDescription();

    public abstract boolean onPlayerDamaged(Player var1, EntityDamageByEntityEvent var2) throws Exception;

    public abstract boolean onPlayerAttacked(Player var1, EntityDamageByEntityEvent var2);

    public abstract boolean onPlayerDamagedBonus(Player var1, int var2, EntityDamageByEntityEvent var3) throws Exception;

    public abstract boolean onPlayerAttackedBonus(Player var1, int var2, EntityDamageByEntityEvent var3);

    protected ItemStack getWithTransmog(boolean applyTransmog, ItemStack item) {
        return applyTransmog ? EnchantmentBook.applyTransmogScroll(item) : item;
    }

    protected boolean isBlockedOutpostWorld(Entity damager) {
        return false;
    }

    protected boolean modifyDamage(Player player, EntityDamageByEntityEvent event, double multiplierIncrementInHundreds, int multiplier) {
        double modifier = ((multiplierIncrementInHundreds < 0.0D) ? Math.abs(multiplierIncrementInHundreds) : multiplierIncrementInHundreds) * 0.01D * (double) multiplier;
        double starterDamage = event.getDamage();
        if (multiplierIncrementInHundreds < 0.0D) {
            event.setDamage(Math.max(0.0D, event.getDamage() - event.getDamage() * modifier));
        } else {
            event.setDamage(Math.max(0.0D, event.getDamage() + event.getDamage() * modifier));
        }
        return starterDamage != event.getDamage();
    }

    protected void removeResistance(Player player) {
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    }
}

