package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroicCactus extends HeroicCustomEnchantment {
    public HeroicCactus() {
        super("Mighty Cactus", "Cactus");
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            if (player.hasMetadata("neutralized"))
                return;
            Player p = (Player) player;
            if (attacker != null && attacker instanceof Player) {
                Player pAttacker = (Player) attacker;
                if (pAttacker != null && Math.random() < 0.35) {
                    pAttacker.damage(0.5D);
                    Location l = p.getLocation();
                }
            }
            double thornNegateChance = (level == 1) ? 0.2D : 0.5D;
            if (e.getCause() == EntityDamageEvent.DamageCause.THORNS && Math.random() < thornNegateChance) {
                e.setCancelled(true);
                e.setDamage(0.0D);
            }
        }
    }
}
