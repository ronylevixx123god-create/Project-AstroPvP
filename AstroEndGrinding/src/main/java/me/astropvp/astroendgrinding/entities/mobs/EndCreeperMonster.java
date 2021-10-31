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
import org.arkhamnetwork.Arkkit.patches.ExpBottles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;

import me.astropvp.astroendgrinding.AstroEndGrindingAPI;
import me.astropvp.astroendgrinding.data.EndPlayerData;
import me.astropvp.astroendgrinding.enums.MonsterType;


public class EndCreeperMonster extends EndMonster {
	
	@Override
	public LivingEntity spawn(Location location) {
		Creeper entity = (Creeper) AstroEndGrindingAPI.getWorld().spawnEntity(location, EntityType.CREEPER);
		entity.setCustomName(CC.GreenB + "End Creeper");
		entity.setMetadata(getMetadata(), new FixedMetadataValue(AstroEndGrindingPlugin.getInstance(), null));
		return entity;
	}
	
	@Override
	public String getMetadata() {
		return "end_creeper_mob";
	}
	
	@Override
	public List<ItemStack> getRewards() {
		List<ItemStack> rewards = Lists.newArrayList();
		Random random = new Random();
		int randomNumber = random.nextInt(1000000 - 600000) + 600000;


		rewards.add(ArkhamMoneyNotesAPI.getMoneyNote("End Monster", randomNumber));
		return rewards;
	}
	
	@Override
	public void onDeath(EntityDeathEvent event, EndPlayerData killer) {
		event.getEntity().removeMetadata(getMetadata(), AstroEndGrindingPlugin.getInstance());


	}
	
	@Override
	public double getDivisorAmount() {
		return 50;
	}
}
