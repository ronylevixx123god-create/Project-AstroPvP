package me.astropvp.astropvpskins.objects.skins.belts;

import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.rit.sucy.EnchantmentAPI;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class CinderWolf extends ItemSkin {


    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if (!WorldGuardUtils.isPvPEnabled(damager.getLocation())) return;
        if (damaged instanceof Player) return;

        if (damager.hasMetadata("cinderWolf")) {
            damager.setLastDamage(damager.getLastDamage() * 1.05);
        } else if (Math.random() < 0.15) {

            damager.setMetadata("cinderWolf", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), true));
            EnchantmentAPI.getEnchantment("Gears").applyEquipEffect(damager, 4);

        }
        Bukkit.getScheduler().runTaskLater(AstroItemSkinsPlugin.getInstance(), () -> {

            EnchantmentAPI.getEnchantment("Gears").applyUnequipEffect(damager, 4);
            damager.removeMetadata("cinderWolf", AstroItemSkinsPlugin.getInstance());

        }, 140L);
    }
}
