package org.arkhamnetwork.enchantments.enchantments.axes;

import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import com.cosmicpvp.cosmicutils.utils.*;
import com.rit.sucy.*;
import org.bukkit.*;

public class Corrupt extends CustomEnchantment
{
    public Corrupt() {
        super("Corrupt", GeneralUtil.axe, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player)) {
            return;
        }
        final double random = Math.random();
        final double chance = 5.0 + 0.02 * level;
        if (random < chance && (!playerHit.hasMetadata("corruptEnchantExpire") || playerHit.getMetadata("corruptEnchantExpire").get(0).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("corruptEnchantExpire", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis() + level * 2 * 1000));
            playerHit.setMetadata("corruptEnchantLevel", new FixedMetadataValue((Plugin)Enchantments.getInstance(), level));
            if (EnchantmentAPI.itemHasEnchantment(((Player)playerHit).getItemInHand(), "Inversion")) {
                return;
            }
            if (playerHit.hasMetadata("corruptTaskID")) {
                return;
            }
            final int damageAmount = (level >= 3) ? 2 : 1;
            final int damageInterval = level;
            final int taskID = new CorruptDamageTask((Player)playerHit, damageInterval, damageAmount).runTaskTimer((Plugin)Enchantments.getInstance(), 0L, damageInterval * 20).getTaskId();
            Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)Enchantments.getInstance(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.getScheduler().cancelTask(taskID);
                    Bukkit.getScheduler().runTask((Plugin)Enchantments.getInstance(), () -> playerHit.removeMetadata("corruptTaskID", (Plugin)Enchantments.getInstance()));
                }
            }, (long)Math.pow(level, 2.0) * 20L);
            playerHit.setMetadata("corruptTaskID", new FixedMetadataValue((Plugin)Enchantments.getInstance(), taskID));
        }
    }

    public static boolean isCorrupted(final LivingEntity e) {
        return e.hasMetadata("corruptEnchantExpire") && e.getMetadata("corruptEnchantExpire").get(0).asLong() > System.currentTimeMillis();
    }

    public static int getCorruptLevel(final LivingEntity e) {
        if (!isCorrupted(e)) {
            return 0;
        }
        return e.getMetadata("corruptEnchantLevel").get(0).asInt();
    }
}
