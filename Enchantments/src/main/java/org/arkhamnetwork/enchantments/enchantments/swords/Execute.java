/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.WorldUtil
 *  com.rit.sucy.CustomEnchantment
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class Execute extends CustomEnchantment
{
    public Execute() {
        super("Execute", GeneralUtil.swords, 2);
        this.max = 7;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity target, final int level, final EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        if (target.getHealth() > 6.0) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.03 * level;
        if (random < chance) {
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage((double)(level * 10), e, "&b&lExecute");
            final Location l = target.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), Material.REDSTONE_WIRE.getId(), 2001);
            DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), CC.AquaB + "* EXECUTE *", "EXECUTE");
        }
    }
}






