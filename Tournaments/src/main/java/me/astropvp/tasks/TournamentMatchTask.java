package me.astropvp.tasks;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.struct.TournamentMatch;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

public class TournamentMatchTask extends BukkitRunnable {
    private TournamentMatch match;

    private int time;

    public TournamentMatchTask(TournamentMatch match) {
        this.match = match;
        this.time = 0;
    }

    public void run() {
        time++;
        if(this.time < 10) {
            this.match.sendTitle(new Title(CC.GreenB + "MATCH STARTING IN " + (this.time - 10)));
        }
        if(this.time == 10) {
            this.match.setRunning(true);
            this.match.sendTitle(new Title(CC.GreenB + "MATCH STARTED"));
        }
        if(this.time == 300) {
            this.match.setTickDmg(true);
            this.match.sendTitle(new Title(CC.RedB + "TICK DAMAGE HAS STARTED"));
        }
        if(this.time >= 300 && (this.time % 10 == 0)) {
            this.match.tickPlayers((this.time - 300)/10);
        }
    }
}
