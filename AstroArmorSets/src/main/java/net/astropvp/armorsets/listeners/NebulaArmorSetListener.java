package net.astropvp.armorsets.listeners;

import com.golfing8.winespigot.event.PotionEffectAddEvent;
import net.astropvp.armorsets.struct.ArmorSetType;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.core.AstroCore;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

public class NebulaArmorSetListener implements Listener {
    @EventHandler
    public void onPot(PotionEffectAddEvent e) {
        if (e.getEntity() instanceof Player && !e.isCancelled()) {
            ArmorSetCache cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer((Player) e.getEntity()).getCache("armor_set");
            if (cache.getCurrentSetCrystals().containsKey(ArmorSetType.NEBULA)) {
                if (e.getEffect().getType() == PotionEffectType.HEAL && e.getCause() == PotionEffectAddEvent.EffectCause.POTION_SPLASH) {
                    GeneralUtil.healPlayer(e.getEntity(), 2);
                }
            }
        }
    }
}
