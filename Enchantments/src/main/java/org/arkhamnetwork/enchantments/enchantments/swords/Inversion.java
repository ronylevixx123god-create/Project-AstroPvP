package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import java.util.*;
import org.arkhamnetwork.enchantments.enchantments.axes.*;
import org.bukkit.*;

public class Inversion extends CustomEnchantment
{
    public Inversion() {
        super("Inversion", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(final LivingEntity entityHit, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final float procChance = (float)(0.0025 * level);
        if (Math.random() <= procChance) {
            final Player playerHit = (Player)entityHit;
            final double heal = new Random().nextInt(1) + 1;
            if (heal >= 1.0) {
                if (Corrupt.isCorrupted(playerHit) && Math.random() < Corrupt.getCorruptLevel(playerHit) * 0.2f) {
                    playerHit.damage(heal);
                    playerHit.sendMessage(ChatColor.DARK_PURPLE + "* CORRUPTED [" + ChatColor.RED + heal + ChatColor.DARK_PURPLE + " DMG] *");
                    return;
                }
                GeneralUtil.healPlayer(playerHit, heal);
                playerHit.playSound(playerHit.getLocation(), Sound.PISTON_EXTEND, 0.8f, 2.0f);
            }
        }
    }
}
