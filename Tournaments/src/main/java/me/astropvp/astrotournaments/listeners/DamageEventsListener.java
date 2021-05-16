package me.astropvp.astrotournaments.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astrotournaments.AstroTournamentsAPI;
import me.astropvp.astrotournaments.data.ActiveTournamentData;
import me.astropvp.astrotournaments.struct.ActiveTournamentUser;
import me.astropvp.astrotournaments.struct.TeamData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.util.Vector;


public class DamageEventsListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onDeath(EntityDeathEvent event) {
		if(!AstroTournamentsAPI.isActive()) {
			return;
		}
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		ActiveTournamentUser user = ActiveTournamentData.findUser(player);
		if(user == null) {
			return;
		}
		user.remove();
		event.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDamaged(EntityDamageEvent event) {
		if(!AstroTournamentsAPI.isActive()) {
			return;
		}
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		Player entity = (Player) event.getEntity();
		if(!(event.getDamager() instanceof Player)) {
			return;
		}
		Player damager = (Player) event.getDamager();
		ActiveTournamentUser userEntity = ActiveTournamentData.findUser(entity);
		if(userEntity == null) {
			return;
		}
		ActiveTournamentUser userDamager = ActiveTournamentData.findUser(damager);
		if(userDamager == null) {
			return;
		}
		TeamData team = ActiveTournamentData.findTeam(userEntity);
		if(team.getUsers().contains(userDamager)) {
			damager.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot damage your teammate!");
			event.setCancelled(true);
		}
		else {
			event.setCancelled(false);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onSpectatorDamaged(EntityDamageEvent event) {
		if(!(event.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		ActiveTournamentUser user = ActiveTournamentData.findUser(player);
		if(user == null) {
			return;
		}
		if(user.isSpectating()) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onSpectatorAttack(EntityDamageEvent event) {
		if(!(event.getDamager() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getDamager();
		ActiveTournamentUser user = ActiveTournamentData.findUser(player);
		if(user == null) {
			return;
		}
		if(user.isSpectating()) {
			player.setVelocity(new Vector(0.5, 1.0, 0.5));
			event.setCancelled(true);
		}
	}
}
