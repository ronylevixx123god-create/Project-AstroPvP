package me.astropvp.astroendgrinding.entities.mobs;

import java.util.List;
import java.util.Random;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import me.astropvp.astroendgrinding.AstroEndGrindingPlugin;
import me.astropvp.astroendgrinding.entities.EndMonster;
import me.ifamasssxd.cosmicguishop.ArkhamMoneyNotesAPI;
import me.ifamasssxd.cosmicmysterychest.CosmicMysteryChest;
import me.ifamasssxd.cosmicmysterychest.struct.MysteryChest;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;

import me.astropvp.astroendgrinding.AstroEndGrindingAPI;
import me.astropvp.astroendgrinding.data.EndPlayerData;
import me.astropvp.astroendgrinding.enums.MonsterType;


public class EndSkeletonMonster extends EndMonster {
	
	@Override
	public LivingEntity spawn(Location location) {
		Skeleton entity = (Skeleton) AstroEndGrindingAPI.getWorld().spawnEntity(location, EntityType.SKELETON);
		entity.setCustomName(CC.RedB + "End Skeleton");
		entity.setMetadata(getMetadata(), new FixedMetadataValue(AstroEndGrindingPlugin.getInstance(), null));
		entity.getEquipment().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET)
		.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
		.enchant(Enchantment.DURABILITY, 3).build());
		entity.getEquipment().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
		.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
		.enchant(Enchantment.DURABILITY, 3).build());
		entity.getEquipment().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS)
		.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
		.enchant(Enchantment.DURABILITY, 3).build());
		entity.getEquipment().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS)
		.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
		.enchant(Enchantment.DURABILITY, 3).build());
		entity.getEquipment().setItemInHand(new ItemBuilder(Material.BOW)
		.enchant(Enchantment.ARROW_DAMAGE, 5)
		.enchant(Enchantment.DURABILITY, 3).build());
		return entity;
	}
	
	@Override
	public String getMetadata() {
		return "end_skeleton_mob";
	}
	
	@Override
	public List<ItemStack> getRewards(Player player) {
		List<ItemStack> rewards = Lists.newArrayList();
		Random random = new Random();
		int randomNumber = random.nextInt(35000 - 15000) + 15000;
		int upgradeLevel = FactionUpgradeAPI.getPerkLevel(player, FactionUpgrade.ENDER_FARMING);
		switch (upgradeLevel) {
			case 1:
				randomNumber *= 2;
				break;
			case 2:
				randomNumber *= 3;
				break;
			case 3:
				randomNumber += 5;
				break;
			default:
				break;
		}
		MysteryChest reward = CosmicMysteryChest.get().getChestManager().getMysteryChestMap().get("SwagBag");


		rewards.add(ExpBottles.getXpBottle(randomNumber, "End Monster"));
		if(Math.random() < 0.15) {
			rewards.add(reward.createClaimableItem(true));

		}
		return rewards;
	}
	
	@Override
	public void onDeath(EntityDeathEvent event, EndPlayerData killer) {
		event.getEntity().removeMetadata(getMetadata(), AstroEndGrindingPlugin.getInstance());

	}
	
	@Override
	public double getDivisorAmount() {
		return 20;
	}
}
