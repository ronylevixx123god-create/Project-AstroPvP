package me.ifamasssxd.cosmicsky.inventorypets.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CageCooldownUtils {


    public static void enterCageCooldown(Player target) {
        CosmicSkyInventoryPets.getCageCooldownMap().put(target.getUniqueId(), target);
        target.sendMessage(CC.translate("&c&lCAGE PET: &aYou cannot be caged for 60 seconds anymore!"));

        Bukkit.getScheduler().runTaskLaterAsynchronously(CosmicSkyInventoryPets.get(), () -> {
            CosmicSkyInventoryPets.getCageCooldownMap().remove(target.getUniqueId());
            target.sendMessage(CC.translate("&a&lCAGE PET: &aYou can now get caged again!"));
        }, 1200L);
    }

    public static boolean isOnCageCooldown(Player target) {
        return CosmicSkyInventoryPets.getCageCooldownMap().containsKey(target.getUniqueId());
    }
}
