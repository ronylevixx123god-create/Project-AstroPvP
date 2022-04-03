package me.astropvp.astrospacegrinding.entities.mobs;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NumberUtils;
import me.astropvp.astrospacegrinding.AstroSpaceGrindingAPI;
import me.astropvp.astrospacegrinding.AstroSpaceGrindingPlugin;
import me.astropvp.astrospacegrinding.entities.SpaceMonster;
import me.astropvp.astrospacegrinding.enums.MonsterType;
import me.astropvp.astrospacegrinding.utils.config.ConfigManager;
import me.ifamasssxd.cosmicbosses.CosmicBosses;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;

import me.astropvp.astrospacegrinding.data.SpacePlayerData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class SpaceAlien extends SpaceMonster {
	
	@Override
	public LivingEntity spawn(Location location) {

		Zombie entity = (Zombie) AstroSpaceGrindingAPI.getWorld().spawnEntity(location, EntityType.ZOMBIE);
		entity.setCustomName(CC.GreenB + "Space Alien");
		entity.setMetadata(getMetadata(), new FixedMetadataValue(AstroSpaceGrindingPlugin.getInstance(), null));
		entity.setMetadata("minion", (MetadataValue)new FixedMetadataValue((Plugin) CosmicBosses.getInstance(), entity.getUniqueId()));

		entity.setRemoveWhenFarAway(false);
		entity.setCustomNameVisible(true);
		entity.setCanPickupItems(false);

		entity.getEquipment().setHelmet(new ItemBuilder(new ItemStack(Material.GLASS, 1, (short)95))
				.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
				.enchant(Enchantment.DURABILITY, 3).build());
		entity.setMaxHealth(12.5);
		entity.setHealth(entity.getMaxHealth());
		entity.getEquipment().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE)
		.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
		.enchant(Enchantment.DURABILITY, 3).build());
		entity.getEquipment().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS)
		.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
		.enchant(Enchantment.DURABILITY, 3).build());
		entity.getEquipment().setBoots(new ItemBuilder(Material.IRON_BOOTS)
		.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
		.enchant(Enchantment.DURABILITY, 3).build());
		entity.getEquipment().setItemInHand(new ItemBuilder(Material.DIAMOND_SWORD)
		.enchant(Enchantment.DAMAGE_ALL, 5)
		.enchant(Enchantment.DURABILITY, 3).build());
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 2));
		entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 9999, 3));
		if (entity.getEquipment() != null) {
			entity.getEquipment().setItemInHandDropChance(0.0F);
			entity.getEquipment().setHelmetDropChance(0.0F);
			entity.getEquipment().setChestplateDropChance(0.0F);
			entity.getEquipment().setLeggingsDropChance(0.0F);
			entity.getEquipment().setBootsDropChance(0.0F);
		}

		return entity;
	}
	
	@Override
	public String getMetadata() {
		return "space_zombie";
	}
	
	@Override
	public List<ItemStack> getRewards(Player player) {
		List<ItemStack> rewards = Lists.newArrayList();

		Random random = new Random();



		if (Math.random() < 0.5) {
			int maxMoney = AstroSpaceGrindingPlugin.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("spaceAlienMaxMoney");
			int minMoney = AstroSpaceGrindingPlugin.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("spaceAlienMinMoney");
			int randomNumberMoney = random.nextInt(maxMoney - minMoney + 1) + minMoney;
			player.sendMessage(CC.translate("&a&l+ $" + NumberUtils.formatMoney(randomNumberMoney)));
			EconomyUtils.depositBalance(player, randomNumberMoney);
			player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);

		} else {
			int maxXP = AstroSpaceGrindingPlugin.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("spaceAlienMaxXP");
			int minXP = AstroSpaceGrindingPlugin.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("spaceAlienMinXP");
			int randomNumberXP = random.nextInt(maxXP - minXP + 1) + minXP;
			player.sendMessage(CC.translate("&a&l+ " + NumberUtils.formatMoney(randomNumberXP)) + " xp");
			player.giveExp(randomNumberXP);
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);


		}


		Map<ItemStack, Double> loot = AstroSpaceGrindingPlugin.cachedCrateLoot.get(MonsterType.SPACE_ALIEN);

		if(Math.random() < 0.15) {
			//TODO add loot
		}
		return rewards;
	}
	
	@Override
	public void onDeath(EntityDeathEvent event, SpacePlayerData killer) {
		event.getEntity().removeMetadata(getMetadata(), AstroSpaceGrindingPlugin.getInstance());

	}


	}
