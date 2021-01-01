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

public class BloodLust extends CustomEnchantment {
    public BloodLust() {
        super("Blood Lust", GeneralUtil.chestplates, 2);
        this.max = 6;
        this.base = 17.0D;
        this.interval = 4.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {}

    public void applyEquipEffect(Player player, int enchantLevel) {
        player.setMetadata("bloodLust", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), Integer.valueOf(enchantLevel)));
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        player.removeMetadata("bloodLust", (Plugin)Enchantments.getInstance());
    }
}
