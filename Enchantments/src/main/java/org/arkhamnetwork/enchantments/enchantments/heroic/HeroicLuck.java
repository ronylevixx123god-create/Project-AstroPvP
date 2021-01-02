package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class HeroicLuck extends HeroicCustomEnchantment {
    public HeroicLuck() {
        super("Infinite Luck", "Lucky", GeneralUtil.armor, 2);
        this.max = 5;
    }

    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("infiniteLucky", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) enchantLevel));
    }

    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("infiniteLucky", (Plugin) Enchantments.getInstance());
    }
}
