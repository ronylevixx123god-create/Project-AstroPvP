package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;

public class Lifesteal extends CustomEnchantment {
    public Lifesteal() {
        super("Lifesteal", GeneralUtil.weapons, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 8.0;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        final double random = Math.random();

        final double chance = enchantLevel * 0.055;
        if (random <= chance) {
            if (target instanceof Player && MaskType.hasEquipped((Player) target, "NECROMANCER")) {
                return;
            }
            if (target instanceof Player && AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.SUMMER_2021_BELT)) {
                return;
            }
            GeneralUtil.healPlayer(user, 3);
        }
    }
}
