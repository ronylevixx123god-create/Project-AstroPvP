package net.astropvp.armorsets.listeners;

import me.astropvp.astropvpskins.armorequip.ArmorEquipEvent;
import net.astropvp.armorsets.AstroArmorSets;
import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Map;

public class ArmorSetApplyListener implements Listener {

    public ArmorSetApplyListener(AstroArmorSets plugin) {
        plugin = AstroArmorSets.getInstance();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        AstroArmorSetsAPI.refreshArmorSets(e.getPlayer());
    }

    @EventHandler
    public void onArmorChange(ArmorEquipEvent e) {
        ArmorSetCache cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer(e.getPlayer()).getCache("armor_set");

        //Remove
        if (e.getOldArmorPiece() != null && e.getOldArmorPiece().getType() != Material.AIR) {
            if (AstroArmorSetsAPI.hasSetTypeOnItem(e.getOldArmorPiece())) {
                cache.removeArmorSet(AstroArmorSetsAPI.getSetTypeOnItem(e.getOldArmorPiece()));

            }
            if (AstroArmorSetsAPI.isHeroic(e.getOldArmorPiece())) {
                cache.removeHeroicArmor(e.getOldArmorPiece());

            }

            if (AstroArmorSetsAPI.hasCrystalsApplied(e.getOldArmorPiece())) {
                List<ArmorSetType> crystals = AstroArmorSetsAPI.getCrystalsFromItem(e.getOldArmorPiece());
                assert crystals != null;
                for (ArmorSetType type : crystals) {
                    cache.removeArmorCrystal(type);

                }
            }
        }

        //Add
        if (e.getNewArmorPiece() != null && e.getNewArmorPiece().getType() != Material.AIR) {
            if (AstroArmorSetsAPI.hasSetTypeOnItem(e.getNewArmorPiece())) {
                cache.addArmorSet(AstroArmorSetsAPI.getSetTypeOnItem(e.getNewArmorPiece()));

            }
            if (AstroArmorSetsAPI.isHeroic(e.getNewArmorPiece())) {
                cache.addHeroicArmor(e.getNewArmorPiece());

            }

            if (AstroArmorSetsAPI.hasCrystalsApplied(e.getNewArmorPiece())) {
                List<ArmorSetType> crystals = AstroArmorSetsAPI.getCrystalsFromItem(e.getNewArmorPiece());
                for (ArmorSetType type : crystals) {
                    cache.addArmorCrystal(type);

                }
            }
        }
    }

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
                for (Map.Entry<ArmorSetType, Integer> entry : cache.getCurrentSetCrystals().entrySet()) {
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
        if (attacked instanceof Player) {
            cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer((Player) attacked).getCache("armor_set");
            //armor set damaged
            if (cache.getCurrentArmorSet() != null) {
                cache.getCurrentArmorSet().getArmorSet().onDamaged(attacker, (Player) attacked, e);
            }

            //crystals damaged
            if (!cache.getCurrentSetCrystals().isEmpty()) {
                for (Map.Entry<ArmorSetType, Integer> entry : cache.getCurrentSetCrystals().entrySet()) {
                    ArmorSetType armorSetType = entry.getKey();
                    int amount = entry.getValue();
                    armorSetType.getArmorSet().onDamagedCrystal(attacker, (Player) attacked, e, amount);

                }
            }
        }

    }
}
