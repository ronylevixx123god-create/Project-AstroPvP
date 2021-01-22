package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;

public class Lucky extends CustomEnchantment {
    public Lucky() {
        super("Lucky", GeneralUtil.armor, 3);
        this.max = 10;
        this.base = 25.0;
        this.interval = 10.0;
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player && new Random().nextInt(400) <= level) {
            final Player p = (Player) player;
            double dmg = e.getDamage(EntityDamageEvent.DamageModifier.BASE);
            dmg *= ArmorUtil.getArmorDamageNullificationPercent((float) ArmorUtil.getArmorValue(p));
            if (player.getHealth() - dmg <= 0.0) {
                e.setCancelled(true);
                e.setDamage(0.0);
                de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayer(p, p.getLocation(), 0,0,0,0.3f,30);
            }
        }
    }
}
