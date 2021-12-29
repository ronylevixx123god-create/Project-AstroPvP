package org.arkhamnetwork.enchantments.enchantments.heroic;

import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.axes.Hex;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class HeroicHex extends HeroicCustomEnchantment{
    public HeroicHex() {
        super("Bewitched Hex","Hex", GeneralUtil.axe, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyEffect(LivingEntity player, final LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double chance;
        if (!(playerHit instanceof Player)) {
            return;
        }
        double random = Math.random();
        if (random < (chance = 0.02 * (double)level) && (!playerHit.hasMetadata("hexEnchantExpire") || ((MetadataValue)playerHit.getMetadata("hexEnchantExpire").get(0)).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("hexEnchantExpire", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(System.currentTimeMillis() + (long)((2 + level / 2) * 1000))));
            playerHit.setMetadata("hexEnchantLevel", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            try {
                ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), playerHit.getEyeLocation(), 0.6f, 0.6f, 0.6f, 0.1f, 20);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            ((Player)playerHit).sendMessage((Object) ChatColor.DARK_PURPLE + "" + (Object)ChatColor.BOLD + "* HEX DEBUFF [" + (Object)ChatColor.LIGHT_PURPLE + (Object)ChatColor.BOLD + (2 + level / 2) + "s" + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + "] *");
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable(){

                @Override
                public void run() {
                    ((Player)playerHit).sendMessage((Object)ChatColor.LIGHT_PURPLE + "" + (Object)ChatColor.BOLD + "* HEX OFF *");
                }
            }, (long)(2 + level) * 20L);
        }
    }

    public static boolean isHexed(LivingEntity e) {
        return e.hasMetadata("hexEnchantExpire") && ((MetadataValue)e.getMetadata("hexEnchantExpire").get(0)).asLong() > System.currentTimeMillis();
    }

    public static int getHexLevel(LivingEntity e) {
        if (!Hex.isHexed(e)) {
            return 0;
        }
        return ((MetadataValue)e.getMetadata("hexEnchantLevel").get(0)).asInt();
    }
}
