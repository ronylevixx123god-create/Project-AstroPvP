package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
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
            playerHit.setMetadata("deepWounds", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis() + level * 1500));
        }
    }
}
