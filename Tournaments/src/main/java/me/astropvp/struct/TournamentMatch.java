package me.astropvp.struct;

import lombok.Getter;
import lombok.Setter;
import me.astropvp.CosmicTournamentsPlugin;
import me.astropvp.tasks.TournamentMatchTask;
import org.bukkit.Bukkit;
import org.github.paperspigot.Title;

public class TournamentMatch {
    @Getter private TournamentPlayer player1;
    @Getter private TournamentPlayer player2;
    @Getter private TournamentMatchTask task;
    @Getter @Setter private boolean tickDmg;
    @Getter @Setter private boolean running;
    private Tournament tournament;

    public TournamentMatch(TournamentPlayer player1, TournamentPlayer player2, Tournament tournament) {
        this.player1 = player1;
        this.player2 = player2;
        this.task = new TournamentMatchTask(this);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(CosmicTournamentsPlugin.getInstance(), this.task, 20L, 20L);
        this.tickDmg = true;
        this.running = false;
        start();
    }

    private void start() {

    }

    public void end() {
        this.task.cancel();
        this.tournament.removeMatch(this);
    }

    public void sendTitle(Title title) {
        player1.getPlayer().sendTitle(title);
        player2.getPlayer().sendTitle(title);
    }

    public void tickPlayers(double damage) {
        player1.getPlayer().damage(damage);
        player2.getPlayer().damage(damage);
    }

}
