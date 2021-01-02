package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Unfocus extends CustomEnchantment {
    public Unfocus() {
        super("Unfocus", new Material[]{Material.BOW}, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 0.0;
        Bukkit.getPluginManager().registerEvents((Listener) new UnfocusListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double random = Math.random() * 1.0;
        final double chance = 0.5 * level;
        if (random < chance && playerHit instanceof Player && (!playerHit.hasMetadata("unfocusEnchantmentExpire") || System.currentTimeMillis() > playerHit.getMetadata("unfocusEnchantmentExpire").get(0).asLong())) {
            ((Player) playerHit).sendMessage(ChatColor.DARK_GREEN + "** UNFOCUS [" + level * 2 + "s] **");
            ((Player) playerHit).playSound(playerHit.getLocation(), Sound.PORTAL_TRIGGER, 1.2f, 0.6f);
            ((Player) player).playSound(playerHit.getLocation(), Sound.ARROW_HIT, 1.2f, 2.5f);
            playerHit.setMetadata("unfocusEnchantmentLevel", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
            playerHit.setMetadata("unfocusEnchantmentExpire", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + level * 2000)));
        }
    }
}
