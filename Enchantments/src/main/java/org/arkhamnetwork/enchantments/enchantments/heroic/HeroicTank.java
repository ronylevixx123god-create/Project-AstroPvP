package org.arkhamnetwork.enchantments.enchantments.heroic;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.inventory.*;
import org.bukkit.*;

public class HeroicTank extends HeroicCustomEnchantment
{
    public HeroicTank() {
        super("Reinforced Tank", "Tank");
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(player instanceof Player) || !(attacker instanceof Player)) {
            return;
        }
        if (player.hasMetadata("neutralized")) {
            return;
        }

        if (attacker == null) return;
        if (!(attacker instanceof Player)) return;
        if (AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.JULY_2021_BELT)) {
            return;
        }

        ItemStack i = null;
        if (attacker instanceof Player) {
            final Player pAttacker = (Player)attacker;
            i = pAttacker.getItemInHand();
        }
        double damage_negation = (double)level * 0.0225D;
        if (i != null && i.getType().name().contains("_AXE") && e.getDamage(EntityDamageByEntityEvent.DamageModifier.BASE) * (1.0D - damage_negation) > 1.0D) {
            e.setDamage(EntityDamageByEntityEvent.DamageModifier.BASE,e.getDamage(EntityDamageByEntityEvent.DamageModifier.BASE) * (1.0D - damage_negation));
            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 42, 2001);
        }
    }
}
