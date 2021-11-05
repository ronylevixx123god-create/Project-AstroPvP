package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralCooldown;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Hex extends CustomEnchantment {
    public Hex() {
        super("Hex", GeneralUtil.axe, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener) new HexListener(), (Plugin) Enchantments.getInstance());
    }

    public static boolean isHexed(final LivingEntity e) {
        return e.hasMetadata("hexEnchantExpire") && e.getMetadata("hexEnchantExpire").get(0).asLong() > System.currentTimeMillis();
    }

    public static int getHexLevel(final LivingEntity e) {
        if (!isHexed(e)) {
            return 0;
        }
        return e.getMetadata("hexEnchantLevel").get(0).asInt();
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!(playerHit instanceof Player)) {
            return;
        }

        if (isHexed(playerHit))
            return;
        final double random = Math.random();
        final double chance = 0.02 * level;
        if (random < chance && (!playerHit.hasMetadata("hexEnchantExpire") || playerHit.getMetadata("hexEnchantExpire").get(0).asLong() < System.currentTimeMillis())) {
            playerHit.setMetadata("hexEnchantExpire", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + (2 + level / 2) * 1000)));
            playerHit.setMetadata("hexEnchantLevel", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
            try {
                de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), playerHit.getEyeLocation(), 0,1,0,0.6f,25);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            ((Player) playerHit).sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* HEX DEBUFF [" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + (2 + level / 2) + "s" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "] *");
            GeneralCooldown.enterCooldown((Player) player);
            Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
                @Override
                public void run() {
                    ((Player) playerHit).sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "* HEX OFF *");
                }
            }, (2 + level / 2) * 20L);
        }
    }
}
