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
        double random = Math.random();
        double chance = (enchantLevel == 1) ? 0.001D : ((enchantLevel == 2) ? 0.001D : ((enchantLevel == 3) ? 0.001D : 0.002D));
        int toHeal = (enchantLevel == 1) ? 2 : ((enchantLevel == 2) ? 2 : ((enchantLevel == 3) ? 2 : 3));
        int health = (int) user.getHealth() + toHeal;
        if (health > user.getMaxHealth())
            health = (int) user.getMaxHealth();

        if (random <= chance) {
            if (target instanceof Player && MaskType.hasEquipped((Player) ((Player) target), (String) "NECROMANCER")) {
                return;
            }
            if (target instanceof Player && AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.SUMMER_2021_BELT)) {
                return;
            }
            if ((target instanceof Player && AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.SPRING_BREAK_2022_MUTATED_BELT))) {
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.JULY4TH_2022_HELMET)) {
                return;
            }

            if (target instanceof Player && AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.VALENTINES_2022_MUTATED_BACKPACK)) {
                return;
            }

            if (target instanceof Player && (AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.EASTER_2022_BELT) || AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.EASTER_2022_MUTATED_BELT)))
                return;

            GeneralUtil.setPlayerHealth(user, health);
        }
    }
}

