package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Resilience extends CustomEnchantment {
    public Resilience() {
        super("Resilience", GeneralUtil.helmets, 2);
        this.max = 4;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
        final double minDmgToProc = 10 - level;
        final double chance = level * 0.05;
        if (player.getHealth() - dmg <= 0.0 && dmg >= minDmgToProc && Math.random() < chance) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) / 2.0);
            ((Player) player).sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "** RESILIENCE (" + ChatColor.DARK_GREEN + "50% DMG TAKEN" + ChatColor.BOLD + ") **");
        }
    }

    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("resilienceEnchant", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) enchantLevel));
    }

    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("resilienceEnchant", (Plugin) Enchantments.getInstance());
    }
}
