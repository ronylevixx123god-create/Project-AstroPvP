package me.astropvp.astrotournaments.tasks;

import java.util.List;

import me.astropvp.astrotournaments.AstroTournamentsPlugin;
import me.astropvp.astrotournaments.data.ActiveTournamentData;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;

import me.galacticpvp.galacticpvputils.utils.CC;
import me.galacticpvp.galacticpvputils.utils.PlayerUtils;
import me.astropvp.astrotournaments.struct.ActiveTournamentUser;
import me.astropvp.astrotournaments.struct.OpponentData;
import me.astropvp.astrotournaments.struct.TeamData;

public class ActiveMatchTask extends BukkitRunnable {
	
	public ActiveMatchTask() {
		this.runTaskTimer(AstroTournamentsPlugin.getInstance(), 20, 20);
	}

	private int ticks;
	@Override
	public void run() {
		if(!ActiveTournamentData.isActive() || ActiveTournamentData.getOpponents().size() == 0) {
			this.cancel();
			return;
		}
		ticks++;
		if(ticks > 60 * 5) {
			boolean sendMessage = ticks == 60 * 5;
			List<OpponentData> opponents = ActiveTournamentData.getOpponents();
			for(int i = opponents.size() - 1; i >= 0; i--) {
				OpponentData data = opponents.get(i);
				for(TeamData team : data.getTeams()) {
					for(ActiveTournamentUser user : team.getUsers()) {
						if(user == null) {
							continue;
						}
						Player player = user.getPlayer();
						if(player == null || !player.isOnline() || ActiveTournamentData.findUser(player) == null) {
							continue;
						}
						if(sendMessage) {
							player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.75F, 2F);
							player.sendMessage(CC.RedB + "(!) " + CC.Red + "Tick damage is now enabled. It will slowly increment.");
						}
						PlayerUtils.safelyDamageEntity(player, ticks / 60 - 4, DamageCause.CUSTOM);
					}
				}
			}
		}
		if(ticks == 150) {
			for(ActiveTournamentUser user : ActiveTournamentData.getUsers()) {
				Player player = user.getPlayer();
				player.playSound(player.getLocation(), Sound.ANVIL_LAND, 0.75F, 2F);
				player.sendMessage(CC.RedB + "(!) " + CC.Red + "2x Damage is now enabled.");
			}
		}
	}

}
