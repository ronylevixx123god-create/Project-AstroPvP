package net.astropvp.armorsets.listeners;

import net.astropvp.armorsets.AstroArmorSets;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.armorsets.utils.config.ConfigManager;
import net.astropvp.core.AstroCore;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OmniArmorDamageListener implements Listener {
    @EventHandler(
            priority = EventPriority.LOW, ignoreCancelled = true)
    public void reduceDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (e.getDamager().getWorld().getName().equals("world_outpost_pluto")) {
            return;
        }
        Player player = ((Player) e.getEntity()).getPlayer();
        ArmorSetCache cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer(player).getCache("armor_set");
        if (cache != null && cache.getOmniPieces() != 0) {
            double totalReduction =  AstroArmorSets.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("omniSetDamageDecrease");
            e.setDamage( e.getDamage() * (1-(totalReduction/100) * (1+(cache.getOmniPieces()/10F))));
        }

    }

    @EventHandler(
            priority = EventPriority.LOW,
            ignoreCancelled = true
    )
    public void increaseDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (e.getDamager().getWorld().getName().equals("world_outpost_pluto")) {
            return;
        }
        Player player = ((Player) e.getEntity()).getPlayer();
        ArmorSetCache cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer(player).getCache("armor_set");
        if (cache != null && cache.getOmniPieces() != 0) {
            double totalIncrease = AstroArmorSets.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("omniSetDamageIncrease");
            e.setDamage(e.getDamage() * (1+(totalIncrease/100) * (1+(cache.getOmniPieces()/10F))));
        }
    }
}
