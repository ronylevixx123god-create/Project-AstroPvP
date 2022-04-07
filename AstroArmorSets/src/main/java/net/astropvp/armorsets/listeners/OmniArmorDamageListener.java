package net.astropvp.armorsets.listeners;

import net.astropvp.armorsets.AstroArmorSets;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.core.AstroCore;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OmniArmorDamageListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void reduceDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = ((Player) e.getEntity()).getPlayer();
        ArmorSetCache cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer(player).getCache("armor_set");
        if (cache != null && cache.getOmniPieces() != 0) {
            double totalReduction = cache.getOmniPieces() * AstroArmorSets.OMNI_MULTIPLIER;
            e.setDamage(e.getDamage() * (1-(totalReduction/100)));
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void increaseDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = ((Player) e.getEntity()).getPlayer();
        ArmorSetCache cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer(player).getCache("armor_set");
        if (cache != null && cache.getOmniPieces() != 0) {
            double totalIncrease = cache.getOmniPieces() * AstroArmorSets.OMNI_MULTIPLIER;
            e.setDamage(e.getDamage() * (1+(totalIncrease/100)));
        }

    }
}
