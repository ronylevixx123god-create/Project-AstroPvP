package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.inventory.*;
import org.bukkit.*;

public class Armored extends CustomEnchantment
{
    public Armored() {
        super("Armored", GeneralUtil.armor, 4);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(player instanceof Player)) {
            return;
        }


        if (player.hasMetadata("neutralized")) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs")) {
            return;
        }

        if (attacker instanceof Player) {

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.EASTER_2022_SWORD) || AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.EASTER_2022_MUTATED_SWORD)) return;

        }
        ItemStack i = null;
        if (attacker instanceof Player) {
            final Player pAttacker = (Player)attacker;
            i = pAttacker.getItemInHand();
        }
        final double damage_negation = level * 0.01;
        if (i != null && i.getType().name().contains("_SWORD") && e.getDamage(EntityDamageByEntityEvent.DamageModifier.BASE) * (1.0 - damage_negation) > 1.0) {
            e.setDamage(EntityDamageByEntityEvent.DamageModifier.BASE,e.getDamage(EntityDamageByEntityEvent.DamageModifier.BASE) * (1.0D - damage_negation));            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 57, 2001);
        }
    }
}
