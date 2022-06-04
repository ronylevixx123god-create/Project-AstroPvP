/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.MaskUtils
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class HeroicLifesteal
extends HeroicCustomEnchantment {
    public HeroicLifesteal() {
        super("Demonic Lifesteal", "Lifesteal", GeneralUtil.swords, 2);
        this.max = 3;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        final double random = Math.random();
        final double chance = enchantLevel * 0.065;
        if (random <= chance) {
            if (target instanceof Player && MaskType.hasEquipped((Player) ((Player) target), (String) "NECROMANCER")) {
                return;
            }
            if (target instanceof Player && AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.SUMMER_2021_BELT)) {
                return;
            }

            if (target instanceof Player && (AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.EASTER_2022_BELT) || AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.EASTER_2022_MUTATED_BELT))) return;

            GeneralUtil.healPlayer(user, 3);
        }
    }
}

