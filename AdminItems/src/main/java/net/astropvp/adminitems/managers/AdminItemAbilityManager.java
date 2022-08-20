package net.astropvp.adminitems.managers;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@Getter
public class AdminItemAbilityManager {
    private List<UUID> trapBlockered;
    private Map<UUID, BukkitRunnable> timeTravellers;
    public AdminItemAbilityManager() {
        this.trapBlockered = new ArrayList<>();
        this.timeTravellers = new HashMap<>();
    }



    public boolean isTrapBlockered(UUID uuid) {
        return trapBlockered.contains(uuid);
    }

    public void trapBlock(UUID uuid) {
        trapBlockered.add(uuid);

    }

}
