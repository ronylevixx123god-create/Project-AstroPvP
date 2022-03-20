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

public class Tank extends CustomEnchantment
{
    public Tank() {
        super("Tank", GeneralUtil.armor, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(player instanceof Player)) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs")) {
            return;
        }

        if (attacker == null) return;
        if (!(attacker instanceof Player)) return;
        if ( AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.JULY_2021_BELT)) {
            return;
        }
        if (player.hasMetadata("neutralized")) {
            return;
        }
        ItemStack i = null;
        if (attacker instanceof Player) {
            final Player pAttacker = (Player)attacker;
            i = pAttacker.getItemInHand();
        }
        final double damage_negation = level * 0.01875;
        if (i != null && i.getType().name().contains("_AXE") && e.getDamage() * (1.0 - damage_negation) > 1.0) {
            //AstroDamageHandler.getInstance().getDamageHandler().reduceDamage((double)level, e, "&e&lTank");
            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 42, 2001);
        }
    }
}
