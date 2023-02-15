package org.arkhamnetwork.enchantments.enchantments.heroic;

import de.inventivegames.particle.ParticleEffect;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

import java.util.Random;

public class HeroicHex extends HeroicCustomEnchantment
{
    public HeroicHex() {
        super("Bewitched Hex", "Hex");
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player)) {
            return;
        }
        if (e.isCancelled())return;

        final double random = Math.random();
        final double chance = 0.0075 * level;
        if (random < chance && !playerHit.hasMetadata("hexEnchant")) {
            playerHit.setMetadata("hexEnchant", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis() + (level / 2) * 1000L));
            ((Player)playerHit).sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* HEX DEBUFF [" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + (level / 2) * 2 + "s" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "] *");
            Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)Enchantments.getInstance(), () -> {
                ((Player)playerHit).sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "* HEX OFF *");
                playerHit.removeMetadata("hexEnchant", Enchantments.getInstance());
            }, (level / 2)  * 20L);
        }
    }

    public static boolean isHexed(final LivingEntity e) {
        return e.hasMetadata("hexEnchant");
    }

    public static int getHexLevel(final LivingEntity e) {
        if (!isHexed(e)) {
            return 0;
        }
        return e.getMetadata("hexEnchantLevel").get(0).asInt();
    }
}
