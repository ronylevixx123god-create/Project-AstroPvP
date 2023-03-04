package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class Hex
        extends CustomEnchantment {
    public Hex() {
        super("Hex", GeneralUtil.axe, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents(new HexListener(), (Plugin)((Object)Enchantments.getInstance()));
    }

    public void applyEffect(LivingEntity player, final LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double chance;
        if (!(playerHit instanceof Player)) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        double random = Math.random();
        if (random < (chance = 0.02 * (double)level) && (!playerHit.hasMetadata("hexEnchantExpire") || playerHit.getMetadata("hexEnchantExpire").get(0).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("hexEnchantExpire", new FixedMetadataValue((Plugin)((Object)Enchantments.getInstance()), System.currentTimeMillis() + (long)((2 + level / 2) * 1000)));
            playerHit.setMetadata("hexEnchantLevel", new FixedMetadataValue((Plugin)((Object)Enchantments.getInstance()), level));
            ((Player)playerHit).sendMessage((Object)((Object)ChatColor.DARK_PURPLE) + "" + (Object)((Object)ChatColor.BOLD) + "* HEX DEBUFF [" + (Object)((Object)ChatColor.LIGHT_PURPLE) + (Object)((Object)ChatColor.BOLD) + (2 + level / 2) + "s" + (Object)((Object)ChatColor.DARK_PURPLE) + (Object)((Object)ChatColor.BOLD) + "] *");
            Bukkit.getScheduler().runTaskLater((Plugin)((Object)Enchantments.getInstance()), new Runnable(){

                @Override
                public void run() {
                    ((Player)playerHit).sendMessage((Object)((Object)ChatColor.LIGHT_PURPLE) + "" + (Object)((Object)ChatColor.BOLD) + "* HEX OFF *");
                }
            }, (long)(2 + level / 2) * 20L);
        }
    }

    public static boolean isHexed(LivingEntity e) {
        return e.hasMetadata("hexEnchantExpire") && e.getMetadata("hexEnchantExpire").get(0).asLong() > System.currentTimeMillis();
    }

    public static int getHexLevel(LivingEntity e) {
        if (!Hex.isHexed(e)) {
            return 0;
        }
        return e.getMetadata("hexEnchantLevel").get(0).asInt();
    }
}
