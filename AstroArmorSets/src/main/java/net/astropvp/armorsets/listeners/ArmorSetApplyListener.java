package net.astropvp.armorsets.listeners;

import me.astropvp.astropvpskins.armorequip.ArmorEquipEvent;
import me.astropvp.proxypach.ProxyPatch;
import net.astropvp.armorsets.AstroArmorSets;
import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.antiMkit.AntiMkit;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArmorSetApplyListener implements Listener {

    public ArmorSetApplyListener(AstroArmorSets plugin) {
        plugin = AstroArmorSets.getInstance();
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

            if (AstroArmorSetsAPI.itemHasMkitCrystals(e.getOldArmorPiece())) {
                Set<AntiMkit> crystals = AstroArmorSetsAPI.getAntiMkitCrystalsOnItem(e.getOldArmorPiece());
                assert crystals != null;
                for (AntiMkit type : crystals) {
                    cache.removeMkitCrystal(type);

                }
            }

            if (AstroArmorSetsAPI.isOmniPiece(e.getOldArmorPiece())) {
                cache.removeOmniPiece(e.getOldArmorPiece());
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
            if (AstroArmorSetsAPI.itemHasMkitCrystals(e.getNewArmorPiece())) {
                Set<AntiMkit> crystals = AstroArmorSetsAPI.getAntiMkitCrystalsOnItem(e.getNewArmorPiece());
                assert crystals != null;
                for (AntiMkit type : crystals) {
                    cache.addMkitCrystal(type);

                }
            }

            if (AstroArmorSetsAPI.isOmniPiece(e.getNewArmorPiece())) {
                cache.addOmniPiece(e.getNewArmorPiece());
            }
        }
    }



    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity() != null) {
            Bukkit.getScheduler().runTaskLater(AstroArmorSets.getInstance(), () -> {
                AstroArmorSetsAPI.refreshArmorSets(e.getEntity());
            }, 30L);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (ProxyPatch.playerNames.contains(e.getPlayer().getName())) return;
        if (e.getPlayer() != null) {
            Bukkit.getScheduler().runTaskLater(AstroArmorSets.getInstance(), () -> {
                AstroArmorSetsAPI.refreshArmorSets(e.getPlayer());
            }, 30L);
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (e.getPlayer() != null) {
            Bukkit.getScheduler().runTaskLater(AstroArmorSets.getInstance(), () -> {
                AstroArmorSetsAPI.refreshArmorSets(e.getPlayer());
            }, 30L);
        }
    }
}
