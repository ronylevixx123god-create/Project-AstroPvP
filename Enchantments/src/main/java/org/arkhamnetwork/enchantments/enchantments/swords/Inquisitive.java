package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Inquisitive extends CustomEnchantment {
    public Inquisitive() {
        super("Inquisitive", GeneralUtil.swords, 7);
        this.max = 4;
        this.base = 25.0;
        this.interval = 5.0;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                Inquisitive.this.heroicVersion = EnchantmentAPI.getEnchantment("Master Inquisitive");
            }
        }, 2L);
        Bukkit.getPluginManager().registerEvents((Listener) new InquisitiveEvents(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (target instanceof Player) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.075 * enchantLevel;
        if (random < chance) {
            target.setMetadata("inquisitive_enchant", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) enchantLevel));
        }
    }

    private class InquisitiveEvents implements Listener {
        @EventHandler(priority = EventPriority.HIGH)
        public void onEntityDeath(final EntityDeathEvent e) {
            if (e.getEntity().hasMetadata("inquisitive_enchant")) {
                final double multiple = 1.0 + 0.25 * e.getEntity().getMetadata("inquisitive_enchant").get(0).asDouble();
                e.setDroppedExp((int) (e.getDroppedExp() * multiple));
            }
        }
    }
}
