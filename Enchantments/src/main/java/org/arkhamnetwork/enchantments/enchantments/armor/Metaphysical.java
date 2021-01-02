package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Metaphysical extends CustomEnchantment {
    public Metaphysical() {
        super("Metaphysical", GeneralUtil.boots, 2);
        this.max = 4;
        this.base = 17.0;
        this.interval = 4.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
    }

    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("metaphysicalEnchant", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) enchantLevel));
    }

    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("metaphysicalEnchant", (Plugin) Enchantments.getInstance());
    }
}
