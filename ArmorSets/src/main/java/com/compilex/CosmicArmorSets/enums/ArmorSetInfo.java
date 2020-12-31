package com.compilex.CosmicArmorSets.enums;

import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
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

public abstract class ArmorSetInfo {
	protected Random random = ThreadLocalRandom.current();

	public static boolean subtractDirectHealth(Player player, LivingEntity damager, double toSubtract) {
		if (player.getHealth() <= 0.0D)
			return false;
		double newHealth = player.getHealth() - toSubtract;
		if (newHealth <= 0.0D && Phoenix.hasPhoenixEquipped(player) && Phoenix.procPhoenix(player, damager, toSubtract)) {
			Bukkit.getLogger().info("[ArmorSets] Not killing " + player.getName() + " due to Phoenix proc!");
			return false;
		}
		if (newHealth < 0.0D)
			newHealth = 0.0D;
		player.setHealth(newHealth);
		return true;
	}

	public boolean supportsBowDamage() {
		return false;
	}

	public void onEquip(Player player) {}

	public void onUnequip(Player player) {}

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

	public abstract ItemStack getHelmet(boolean paramBoolean);

	public abstract ItemStack getChestplate(boolean paramBoolean);

	public abstract ItemStack getLeggings(boolean paramBoolean);

	public abstract ItemStack getBoots(boolean paramBoolean);

	public abstract List<String> getEquipDescription();

	public abstract boolean onPlayerDamaged(Player paramPlayer, EntityDamageByEntityEvent paramEntityDamageByEntityEvent);

	public abstract boolean onPlayerAttacked(Player paramPlayer, EntityDamageByEntityEvent paramEntityDamageByEntityEvent);

	public abstract boolean onPlayerDamagedBonus(Player paramPlayer, int paramInt, EntityDamageByEntityEvent paramEntityDamageByEntityEvent);

	public abstract boolean onPlayerAttackedBonus(Player paramPlayer, int paramInt, EntityDamageByEntityEvent paramEntityDamageByEntityEvent);

	protected ItemStack getWithTransmog(boolean applyTransmog, ItemStack item) {
		return applyTransmog ? EnchantmentBook.applyTransmogScroll(item) : item;
	}

	protected boolean isBlockedOutpostWorld(Entity damager) {
		return false;
	}

	protected boolean modifyDamage(Player player, EntityDamageByEntityEvent event, double multiplierIncrementInHundreds, int multiplier) {
		double modifier = ((multiplierIncrementInHundreds < 0.0D) ? Math.abs(multiplierIncrementInHundreds) : multiplierIncrementInHundreds) * 0.01D * multiplier;
		double starterDamage = event.getDamage(EntityDamageEvent.DamageModifier.BASE);
		if (multiplierIncrementInHundreds < 0.0D) {
			event.setDamage(Math.max(0.0D, event.getDamage(EntityDamageEvent.DamageModifier.BASE) - event.getDamage(EntityDamageEvent.DamageModifier.BASE) * modifier));
		} else {
			event.setDamage(Math.max(0.0D, event.getDamage(EntityDamageEvent.DamageModifier.BASE) + event.getDamage(EntityDamageEvent.DamageModifier.BASE) * modifier));
		}
		if (starterDamage != event.getDamage(EntityDamageEvent.DamageModifier.BASE)) {
			if (CosmicArmorSetsPlugin.get_().isDebugging()) {
				CosmicArmorSetsPlugin.get_().getLogger().info("(" + getClass().getSimpleName() + ") Modifying damage from bonus by " + multiplierIncrementInHundreds + " equipped: " + multiplier + " modifier: " + modifier + " for " + player.getName() + " was " + starterDamage + " -> " + event.getDamage(EntityDamageEvent.DamageModifier.BASE) + " damaged: " + ((event.getEntity() instanceof Player) ? ((Player)event.getEntity()).getName() : event.getEntity().getType().name()));
				if (player.isOp())
					player.sendMessage(CC.Red + "Modified Damage: " + modifier + " (" + starterDamage + " old: " + event.getDamage(EntityDamageEvent.DamageModifier.BASE) + ")");
			}
			return true;
		}
		return false;
	}

	protected void removeResistance(Player player) {
		player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
	}
}