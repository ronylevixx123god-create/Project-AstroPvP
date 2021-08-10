package net.astropvp.core.objects;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import net.astropvp.core.tasks.DimensionalGatewayTask;
import org.bukkit.Location;

public class GatewayData {
    private Hologram hologram;

    private Location location;

    private DimensionalGatewayTask task;

    public GatewayData(Hologram hologram, Location location, DimensionalGatewayTask task) {
        this.hologram = hologram;
        this.location = location;
        this.task = task;
    }


    public Hologram getHologram() {
        return hologram;
    }

    public Location getLocation() {
        return location;
    }

    public DimensionalGatewayTask getTask() {
        return task;
    }
}
