package net.astropvp.armorsets.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.armorsets.struct.cache.ArmorSetCache;
import net.astropvp.core.AstroCore;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroicArmorDamageListener implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void reduceDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = ((Player) e.getEntity()).getPlayer();
        if (e.getDamager().getWorld().getName().equals("world_outpost_pluto")) {
            return;
        }

        ArmorSetCache cache = (ArmorSetCache) AstroCore.getInstance().getCacheManager().getCachedPlayer(player).getCache("armor_set");
        if (cache != null && cache.getHeroicPieces() != 0) {
            double totalReduction = cache.getHeroicPieces() * 4.5;
            if (e.getDamager() instanceof Player) {
                if (Math.random() < 0.33 && AstroItemSkinsAPI.hasSkinEquipped((Player) e.getDamager(), SkinType.THANKSGIVING_2021_SWORD)) {
                    e.getEntity().sendMessage(CC.translate("&4&l* HEROIC ARMOR BLOCKED [&7" + e.getDamager().getName() + "'s Carver &4&l] *"));
                    return;
                }
            }
            e.setDamage(e.getDamage() *1 - (totalReduction  * 0.01));        }

    }
}
