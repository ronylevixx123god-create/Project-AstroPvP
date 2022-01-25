package org.arkhamnetwork.enchantments.enchantments.heroic;

import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.enchantments.axes.*;
import org.bukkit.metadata.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class HeroicDevour extends CustomEnchantment
{
    public HeroicDevour() {
        super("Vampiric Devour", GeneralUtil.axe, 2);
        this.max = 4;
        Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
            this.heroicEnchantment = true;
            this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Devour");
        }, 2L);
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamage() > 0.0 && user instanceof Player) {
            if (((Player)user).getItemInHand() != null && !EnchantmentAPI.itemHasEnchantment(((Player)user).getItemInHand(), "Lifesteal") && !EnchantmentAPI.itemHasEnchantment(((Player)user).getItemInHand(), "Demonic Lifesteal")) {
                final double random = Math.random();
                final double chance = (enchantLevel == 1) ? 0.02 : ((enchantLevel == 2) ? 0.03 : ((enchantLevel == 3) ? 0.04 : 0.05));
                final int toHeal = (enchantLevel == 1) ? 2 : ((enchantLevel == 2) ? 3 : ((enchantLevel == 3) ? 3 : 4));
                int health = (int)user.getHealth()/2 + toHeal;
                if (health > user.getMaxHealth()) {
                    health = (int)user.getMaxHealth();
                }
                if (random < chance) {
                    AstroDamageHandler.getInstance().getDamageHandler().healPlayer(user, (double)health, "&d&lVampiric Devour");
                }
            }
            double bleedStacks = Bleed.getBleedStack(target);
            if (bleedStacks >= 100.0) {
                bleedStacks = 99.0;
            }
            if (Bleed.getBleedStack(target) > 0) {
                if (com.cosmicpvp.cosmicutils.utils.GeneralUtil.isEffectedByRage(target)) {
                    return;
                }
                AstroDamageHandler.getInstance().getDamageHandler().increaseDamage(bleedStacks + 6.0, event, "&6&lDevour");
                target.setMetadata("effectedByDevour", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis()));
                final Location l = target.getLocation();
                WorldUtil.sendWorldEffect(l.getBlock(), Material.MYCEL.getId(), 2001);
            }
        }
    }
}
