package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroicExecute extends HeroicCustomEnchantment{
    public HeroicExecute() {
        super("Permanent Execute","Execute", GeneralUtil.swords, 2);
        this.max = 7;
        this.base = 15.0;
        this.interval = 3.0;
    }

    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }

        double healThres = 12.0;
        if (((Player)player).getInventory().getBoots() != null && AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.HALLOWEEN_2021_BOOTS))
            healThres*=1.3;

        if (target.getHealth() > healThres) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.07 * level;

        if (random < chance) {
            int mod = 50;
            e.setDamage(e.getDamage() * (level));
            final Location l = target.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), Material.REDSTONE_WIRE.getId(), 2001);
            DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), CC.RedB + "* PERMANENT EXECUTE *", "EXECUTE");

        }
    }
}
