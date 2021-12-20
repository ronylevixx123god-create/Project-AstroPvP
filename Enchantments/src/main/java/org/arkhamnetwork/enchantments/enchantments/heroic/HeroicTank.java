package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class HeroicTank extends HeroicCustomEnchantment {
    public HeroicTank() {
        super("Reinforced Tank", "Tank");
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(player instanceof Player)) {
            return;
        }
        if (player.hasMetadata("neutralized"))
            return;
        ItemStack i = null;
        if (attacker instanceof Player) {
            final Player pAttacker = (Player) attacker;
            i = pAttacker.getItemInHand();
        }
        double damage_negation = level * 0.0225D;
        if (i != null && i.getType().name().contains("_AXE") && e.getDamage() * (1.0 - damage_negation) > 1.0) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0 - damage_negation));
            final Location l = player.getLocation();

        }
    }
}
