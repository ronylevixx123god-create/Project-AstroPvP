package me.ifamasssxd.cosmicsky.inventorypets.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SkrrtCooldownUtils {
    public static void enterSkrrtCooldown(Player target) {
        CosmicSkyInventoryPets.getCageCooldownMap().put(target.getUniqueId(), target);
        target.sendMessage(CC.translate("&c&lSKRRT PET: &aYou cannot be skrrt for 60 seconds anymore!"));

        Bukkit.getScheduler().runTaskLaterAsynchronously(CosmicSkyInventoryPets.get(), () -> {
            CosmicSkyInventoryPets.getCageCooldownMap().remove(target.getUniqueId());
            target.sendMessage(CC.translate("&a&lSKRRT PET: &aYou can now get skrrt again!"));
        }, 1200L);
    }

    public static boolean isOnSkrrtCooldown(Player target) {
        return CosmicSkyInventoryPets.getCageCooldownMap().containsKey(target.getUniqueId());
    }
}
