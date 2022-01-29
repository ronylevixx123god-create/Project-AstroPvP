package net.astropvp.armorsets.listeners;

import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.core.AstroCore;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;

public class ArmorSetDamageListener implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        Entity attacker = e.getDamager();
        Entity attacked = e.getEntity();
        ArmorSetCache cache;
        if (attacker instanceof Player) {
            cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer((Player) attacker).getCache("armor_set");

            //armor set damaged
            if (cache.getCurrentArmorSet() != null) {
                cache.getCurrentArmorSet().getArmorSet().onAttack((Player) attacker, attacked, e);
            }

            //crystals damaged
            if (!cache.getCurrentSetCrystals().isEmpty()) {
                Map<ArmorSetType, Integer> crystals = cache.getCurrentSetCrystals();
                for (Map.Entry<ArmorSetType, Integer> entry : crystals.entrySet()) {
                    ArmorSetType armorSetType = entry.getKey();
                    int amount = entry.getValue();
                    armorSetType.getArmorSet().onAttackCrystal((Player) attacker, attacked, e, amount);

                }
            }

        }

    }

    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent e) throws Exception {
        Entity attacker = e.getDamager();
        Entity attacked = e.getEntity();
        ArmorSetCache cache;
        if (attacker instanceof Player && attacked instanceof Player) {
            cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer((Player) attacked).getCache("armor_set");
            //armor set damaged
            if (cache != null && cache.getCurrentArmorSet() != null) {
                cache.getCurrentArmorSet().getArmorSet().onDamaged(attacker, (Player) attacked, e);
            }

            //crystals damaged
            if (cache != null && !cache.getCurrentSetCrystals().isEmpty()) {
                Map<ArmorSetType, Integer> crystals = AstroArmorSetsAPI.getBonusCrystalsEquippedIncludingCorruption((Player)attacker, (Player)attacked);
                for (Map.Entry<ArmorSetType, Integer> entry : crystals.entrySet()) {
                    ArmorSetType armorSetType = entry.getKey();
                    int amount = entry.getValue();
                    armorSetType.getArmorSet().onDamagedCrystal(attacker, (Player) attacked, e, amount);

                }
            }
        }

    }
}
