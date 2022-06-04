package org.arkhamnetwork.enchantments.enchantments.heroic;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
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

        if (attacker instanceof Player) {
            if (AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.BACK_TO_SCHOOL_2021_AXE) && Math.random() < 0.5) {
                return;
            }
        }

        double damage_negation = level * 0.06;
        final double hp_threshold = level * 2 + 8;
        if (attacker instanceof Player && AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.BACK_TO_SCHOOL_2021_BELT)) {
            damage_negation /=2;
        }

        if (player.getHealth() <= hp_threshold && (((Player)player).getItemInHand().getType() == Material.GOLD_SWORD || ((Player)player).getItemInHand().getType() == Material.DIAMOND_SWORD) && e.getDamage() * (1.0 - damage_negation) > 1.0) {
            e.setDamage(e.getDamage() * (1.0D - damage_negation));
            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 41, 2001);
        }
    }
}
