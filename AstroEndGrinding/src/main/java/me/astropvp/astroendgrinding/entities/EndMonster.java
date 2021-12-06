package me.astropvp.astroendgrinding.entities;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import me.astropvp.astroendgrinding.data.EndPlayerData;

public class EndMonster {
	
	public LivingEntity spawn(Location location) {
		return null;
	}
	
	public boolean isMobType(LivingEntity entity) {
		return entity.hasMetadata(getMetadata());
	}
	
	public String getMetadata() {
		return null;
	}
	
	public List<ItemStack> getRewards(Player player){
		return Lists.newArrayList();
	}
	
	public void onDeath(EntityDeathEvent event, EndPlayerData killer) {
		
	}
	
	public double getDivisorAmount() {
		return 0.0;
	}
}
