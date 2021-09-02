package me.ifamasssxd.cosmicsky.inventorypets.utils;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WitcherCooldownUtils {


    public static void enterWitcherCooldown(Player target) {
        CosmicSkyInventoryPets.getWitcherCooldownMap().put(target.getUniqueId(), target);
        target.sendMessage(CC.translate("&c&lWITCHER PET: &aYou cannot be witchered for 60 seconds anymore!"));

        Bukkit.getScheduler().runTaskLaterAsynchronously(CosmicSkyInventoryPets.get(), () -> {
            CosmicSkyInventoryPets.getWitcherCooldownMap().remove(target.getUniqueId());
            target.sendMessage(CC.translate("&a&lWITCHER PET: &aYou can now get witchered again!"));
        }, 1200L);
    }

    public static boolean isOnWitcherCooldown(Player target) {
        return CosmicSkyInventoryPets.getWitcherCooldownMap().containsKey(target.getUniqueId());
    }
}
