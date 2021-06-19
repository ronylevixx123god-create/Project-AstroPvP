package me.astropvp.astroendgrinding.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astroendgrinding.data.EndPlayerData;
import me.astropvp.astroendgrinding.utils.TimeUtils;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.utils.ActionBarMessageUtils;
import me.astropvp.astrospawners.utils.PlayerData;
import me.astropvp.astrospawners.utils.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import me.astropvp.astroendgrinding.enums.MonsterType;
import org.bukkit.inventory.ItemStack;


public class EndMobListener implements Listener {

	private final PlayerDataManager manager;
	private final AstroSpawners plugin;


	public EndMobListener(AstroSpawners plugin ) {
		this.plugin = plugin;
		this.manager = plugin.getManager();


	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onKill(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		for(MonsterType type : MonsterType.values()) {
			if(type.getInstance().isMobType(entity)) {
				event.getDrops().clear();
				for (ItemStack item : type.getInstance().getRewards()) {
					event.getDrops().add(item);
				}
				if(event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
					Player damager = (Player) event.getEntity().getKiller();
					EndPlayerData data = EndPlayerData.findPlayer(damager);
					data.increment(type);


					PlayerData dataCoins = manager.getPlayerData((Player) event.getEntity().getKiller());
					dataCoins.setCoins(dataCoins.getCoins() + 25);
					ActionBarMessageUtils.sendMessage((Player) event.getEntity().getKiller(), CC.translate("&c+25 Mobcoins &7(Total: &f&n") + dataCoins.getCoins() + CC.Gray + ")");

					type.getInstance().onDeath(event, data);

					int totalMobKills = data.getKills(MonsterType.END_BASIC_MONSTER) + data.getKills(MonsterType.END_CREEPER_MONSTER) + data.getKills(MonsterType.END_SKELETON_MONSTER);

					if (totalMobKills == 20) {
						Bukkit.broadcastMessage(CC.translate(""));
						Bukkit.broadcastMessage(CC.translate("&d&lEND GRINDING &7(/warp end)"));
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage(CC.translate("&f" + damager.getName() + " &7has killed a total of &f&n20&7 end monsters!"));
						Bukkit.broadcastMessage(CC.translate("&7This warrior has received many OP rewards."));
						Bukkit.broadcastMessage(CC.translate(""));
						Bukkit.broadcastMessage(CC.translate("&5&l* &dTime Spent&7: &f" + TimeUtils.format(data.getTimeInEnd())));
						Bukkit.broadcastMessage(CC.translate(""));
						return;
					}

					if (totalMobKills == 50) {
						Bukkit.broadcastMessage(CC.translate(""));
						Bukkit.broadcastMessage(CC.translate("&d&lEND GRINDING &7(/warp end)"));
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage(CC.translate("&f" + damager.getName() + " &7has killed a total of &f&n50&7 end monsters!"));
						Bukkit.broadcastMessage(CC.translate("&7This warrior has received many OP rewards."));
						Bukkit.broadcastMessage(CC.translate(""));
						Bukkit.broadcastMessage(CC.translate("&5&l* &dTime Spent&7: &f" + TimeUtils.format(data.getTimeInEnd())));
						Bukkit.broadcastMessage(CC.translate(""));
						return;
					}

					if (totalMobKills == 100) {
						Bukkit.broadcastMessage(CC.translate(""));
						Bukkit.broadcastMessage(CC.translate("&d&lEND GRINDING &7(/warp end)"));
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage(CC.translate("&f" + damager.getName() + " &7has killed a total of &f&n100&7 end monsters!"));
						Bukkit.broadcastMessage(CC.translate("&7This warrior has received many OP rewards."));
						Bukkit.broadcastMessage(CC.translate(""));
						Bukkit.broadcastMessage(CC.translate("&5&l* &dTime Spent&7: &f" + TimeUtils.format(data.getTimeInEnd())));
						Bukkit.broadcastMessage(CC.translate(""));
						return;
					}

					if (totalMobKills == 150) {
						Bukkit.broadcastMessage(CC.translate(""));
						Bukkit.broadcastMessage(CC.translate("&d&lEND GRINDING &7(/warp end)"));
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage(CC.translate("&f" + damager.getName() + " &7has killed a total of &f&n150&7 end monsters!"));
						Bukkit.broadcastMessage(CC.translate("&7This warrior has received many OP rewards."));
						Bukkit.broadcastMessage(CC.translate(""));
						Bukkit.broadcastMessage(CC.translate("&5&l* &dTime Spent&7: &f" + TimeUtils.format(data.getTimeInEnd())));
						Bukkit.broadcastMessage(CC.translate(""));
						return;
					}

					if (totalMobKills == 200) {
						Bukkit.broadcastMessage(CC.translate(""));
						Bukkit.broadcastMessage(CC.translate("&d&lEND GRINDING &7(/warp end)"));
						Bukkit.broadcastMessage("");
						Bukkit.broadcastMessage(CC.translate("&f" + damager.getName() + " &7has killed a total of &f&n200&7 end monsters!"));
						Bukkit.broadcastMessage(CC.translate("&7This warrior has received many OP rewards."));
						Bukkit.broadcastMessage(CC.translate(""));
						Bukkit.broadcastMessage(CC.translate("&5&l* &dTime Spent&7: &f" + TimeUtils.format(data.getTimeInEnd())));
						Bukkit.broadcastMessage(CC.translate(""));
						return;
					}
				}
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onDamaged(EntityDamageEvent event) {
		LivingEntity entity = (LivingEntity) event.getEntity();
		for(MonsterType type : MonsterType.values()) {
			if(type.getInstance().isMobType(entity)) {
				event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE)*0.50);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFocus(EntityTargetEvent event) {
		if(!(event.getEntity() instanceof Creeper)) {
			return;
		}
		if(MonsterType.END_CREEPER_MONSTER.getInstance().isMobType((LivingEntity)event.getEntity())) {
			event.setCancelled(true);
		}
	}
}
