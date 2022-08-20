package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.enchantments.axes.Bleed;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;

public class HeroicDeepWounds extends HeroicCustomEnchantment
{
    public HeroicDeepWounds() {
        super("Bloody Deep Wounds", "Deep Wounds", GeneralUtil.weapons, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 15.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (playerHit instanceof Player) {
            if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.JULY_2021_BOOTS) && Math.random() < 0.15) {
                playerHit.sendMessage(CC.translate("&c&l** BARRY's BOLTS [&7Blocked Heroic Deep Wounds&c&l] **"));
                return;
            }
            playerHit.setMetadata("deepWounds", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + level * 1500)));
             double mod = Bleed.getBleedStack(playerHit) * 0.0075 + level * 0.01 + 1.0;

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.JULY4TH_2022_HELMET)) {
                mod *= 0.8;
            }
            e.setDamage(e.getDamage() * mod);        }
    }
}
