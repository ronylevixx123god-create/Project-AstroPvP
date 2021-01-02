package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class HeroicGhost extends HeroicCustomEnchantment {
    private final String ghostlyGhostMetadata = "heroicGhostEnchantment";

    public HeroicGhost() {
        super("Ghostly Ghost", "Ghost", false);
        this.setNaturalMaterials(GeneralUtil.armor);
        this.max = 3;
    }

    public void applyEquipEffect(final Player player, final int enchantLevel) {
        player.setMetadata("heroicGhostEnchantment", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) enchantLevel));
    }

    public void applyUnequipEffect(final Player player, final int enchantLevel) {
        player.removeMetadata("heroicGhostEnchantment", (Plugin) Enchantments.getInstance());
    }
}
