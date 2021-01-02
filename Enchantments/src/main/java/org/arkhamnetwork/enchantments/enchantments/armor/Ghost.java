package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Ghost extends CustomEnchantment {
    public Ghost() {
        super("Ghost", GeneralUtil.armor, 9);
        this.max = 3;
        this.base = 25.0;
        this.interval = 10.0;
    }

    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("ghostEnchantment", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) enchantLevel));
    }

    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("ghostEnchantment", (Plugin) Enchantments.getInstance());
    }
}
