package org.arkhamnetwork.enchantments.enchantments.heroic;

import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class HeroicValor extends HeroicCustomEnchantment
{
    public HeroicValor() {
        super("Martyr Valor", "Valor", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(player instanceof Player)) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs")) {
            return;
        }
        final double damage_negation = level * 0.06;
        final double hp_threshold = level * 2 + 8;
        if (player.getHealth() <= hp_threshold && (((Player)player).getItemInHand().getType() == Material.GOLD_SWORD || ((Player)player).getItemInHand().getType() == Material.DIAMOND_SWORD) && e.getDamage() * (1.0 - damage_negation) > 1.0) {
            AstroDamageHandler.getInstance().getDamageHandler().reduceDamage((double)level, e, "&d&lMartyr Valor");
            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 41, 2001);
        }
    }
}
