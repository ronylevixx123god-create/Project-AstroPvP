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
            priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void reduceDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (e.getDamager().getWorld().getName().equals("world_outpost_pluto")) {
            return;
        }
        Player player = ((Player) e.getEntity()).getPlayer();
        ArmorSetCache cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer(player).getCache("armor_set");
        if (cache != null && cache.getOmniPieces() != 0) {
            int omniAmount = cache.getOmniPieces();
            if (omniAmount > 3) {
                omniAmount = 3;
            }
            double totalReduction =  AstroArmorSets.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("omniSetDamageDecrease");
            totalReduction *=omniAmount;
            e.setDamage( e.getDamage() * (1-(totalReduction/100)));
        }

    }

    @EventHandler(
            priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void increaseDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (e.getDamager().getWorld().getName().equals("world_outpost_pluto")) {
            return;
        }
        Player player = ((Player) e.getEntity()).getPlayer();
        ArmorSetCache cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer(player).getCache("armor_set");
        if (cache != null && cache.getOmniPieces() != 0) {
            double totalIncrease = AstroArmorSets.getInstance().getConfigManager().getConfig(ConfigManager.ConfigFile.SETTINGS).getInt("omniSetDamageIncrease");
            totalIncrease *=cache.getOmniPieces();
            e.setDamage(e.getDamage() * (1+(totalIncrease/100)));
        }
    }
}
