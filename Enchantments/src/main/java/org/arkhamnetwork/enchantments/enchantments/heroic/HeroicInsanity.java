package org.arkhamnetwork.enchantments.enchantments.heroic;

import me.astropvp.astropvpskins.objects.skins.boots.SinkOrSwipFlippers;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;

public class HeroicInsanity extends HeroicCustomEnchantment
{
    public HeroicInsanity() {
        super("Extreme Insanity", "Insanity", GeneralUtil.axe, 7);
        this.max = 8;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final boolean enemyHasSword = playerHit.getEquipment().getItemInHand().getType().name().contains("_SWORD");
        final boolean playerHAsAxe = player.getEquipment().getItemInHand().getType().name().contains("_AXE");

        if (!enemyHasSword) {
            return;
        }
        if (!playerHAsAxe) {
            return;
        }
        if (player instanceof Player) {
            if (SinkOrSwipFlippers.insanityDevourSilenced.contains(player)) {
                return;
            }
        }
            e.setDamage(e.getDamage() * 1.4);

    }
}
