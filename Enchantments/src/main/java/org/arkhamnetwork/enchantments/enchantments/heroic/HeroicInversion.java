package org.arkhamnetwork.enchantments.enchantments.heroic;

import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.bukkit.entity.*;
import java.util.*;
import org.arkhamnetwork.enchantments.enchantments.axes.*;
import org.bukkit.*;

public class HeroicInversion extends HeroicCustomEnchantment
{
    public HeroicInversion() {
        super("Reality Inversion", "Inversion", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(final LivingEntity entityHit, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final float procChance = (float)(0.05 * level);
        if (Math.random() < 0.28 && Bleed.getBleedStack(entityHit) > 1) {
            final int newStack = Bleed.getBleedStack(entityHit) - 1;
            if (newStack > 0) {
                entityHit.setMetadata("bleedStack", new FixedMetadataValue((Plugin)Enchantments.getInstance(), newStack));
            }
            else {
                Bleed.clearBleedEffect(entityHit);
            }
        }
        if (Math.random() <= procChance) {
            final Player playerHit = (Player)entityHit;
            final double heal = new Random().nextInt(5) + 1;
            if (heal >= 1.0) {
                if (Corrupt.isCorrupted(playerHit) && Math.random() < Corrupt.getCorruptLevel(playerHit) * 0.2f) {
                    playerHit.damage(heal);
                    playerHit.sendMessage(ChatColor.DARK_PURPLE + "* CORRUPTED [" + ChatColor.RED + heal + ChatColor.DARK_PURPLE + " DMG] *");
                    return;
                }
                AstroDamageHandler.getInstance().getDamageHandler().healPlayer((LivingEntity)playerHit, heal, "&d&lReality Inversion");
                e.setDamage(0);
                playerHit.playSound(playerHit.getLocation(), Sound.PISTON_EXTEND, 0.8f, 2.0f);
            }
        }
    }
}
