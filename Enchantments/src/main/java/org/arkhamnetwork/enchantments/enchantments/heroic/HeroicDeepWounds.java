package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import me.astropvp.damageindicators.utils.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.axes.Bleed;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class HeroicDeepWounds extends HeroicCustomEnchantment {
    public HeroicDeepWounds() {
        super("Bloody Deep Wounds", "Deep Wounds", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 15.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        playerHit.setMetadata("deepWounds", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + level * 1500)));
        final double mod = Bleed.getBleedStack(playerHit) * 0.005 + level * 0.01 + 1.3;
        e.setDamage(e.getDamage() * mod);
    }
}
