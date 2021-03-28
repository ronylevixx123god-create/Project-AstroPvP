package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class ArrowBreak extends CustomEnchantment {
    public ArrowBreak() {
        super("Arrow Break", GeneralUtil.axe, 2);
        this.max = 6;
        this.base = 15.0;
        this.interval = 5.0;
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) e;
            if (edbee.getDamager() instanceof Projectile && Math.random() < (0.1 + 0.04 * level) / 2.0) {
                //ParticleEffect.playEffect(ParticleEffect.CRIT, edbee.getDamager().getLocation(), 0.6f, 16);
                if (!(edbee.getDamager() instanceof Player)) return;
                de.inventivegames.particle.ParticleEffect.CRIT.sendToPlayer((Player) edbee.getDamager(),edbee.getDamager().getLocation(), 0,0,0,0.6f,16);
                ((Player) player).playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.2f);
                e.setCancelled(true);
                e.setDamage(0.0);
                edbee.getDamager().remove();
            }
        }
    }
}
