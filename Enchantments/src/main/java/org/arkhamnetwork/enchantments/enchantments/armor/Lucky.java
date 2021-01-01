package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import java.util.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.Arkkit.libs.*;

public class Lucky extends CustomEnchantment
{
    public Lucky() {
        super("Lucky", GeneralUtil.armor, 3);
        this.max = 10;
        this.base = 25.0;
        this.interval = 10.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player && new Random().nextInt(400) <= level) {
            final Player p = (Player)player;
            double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            dmg *= ArmorUtil.getArmorDamageNullificationPercent((float)ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0) {
                e.setCancelled(true);
                e.setDamage(0.0);
                ParticleEffect.playEffect(ParticleEffect.CLOUD, p.getLocation(), 0.3f, 30);
            }
        }
    }
}
