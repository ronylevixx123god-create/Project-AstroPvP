package net.astropvp.core.stats;

import lombok.Getter;
import net.astropvp.core.AstroCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

@Getter
public class StatsPlayer {
    private transient Player player;

    private transient File file;

    private double kills;

    private double deaths;

    private double kdr;


    public StatsPlayer(Player player) {
        this.player = player;
        this.file = new File(AstroCore.getInstance().getDataFolder(),  "stats/" + this.player.getUniqueId() + ".json");
    }

    public void kill() {
        this.kills++;
        calculateKdr();
    }

    public void death() {
        this.deaths++;
        calculateKdr();
    }

    public void calculateKdr() {
        DecimalFormat format = new DecimalFormat("0.00");
        double value = this.kills/this.deaths;
        this.kdr = Double.parseDouble(format.format(value));
    }

}
