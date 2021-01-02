package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class ArrowDeflect extends CustomEnchantment {
    public ArrowDeflect() {
        super("Arrow Deflect", GeneralUtil.armor, 4);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent edbee = (EntityDamageByEntityEvent) e;
            if (edbee.getDamager() instanceof Projectile) {
                final Projectile shooter = (Projectile) edbee.getDamager();
                if (shooter != null && shooter.hasMetadata("lethalSniper")) {
                    final int sniper = shooter.getMetadata("lethalSniper").get(0).asInt();
                    final double chance = 0.1 * sniper;
                    if (Math.random() <= chance) {
                        return;
                    }
                }
                if (player.hasMetadata("lastArrowDamageEvent")) {
                    final long minDelayMilliseconds = level * 500;
                    final long last = player.getMetadata("lastArrowDamageEvent").get(0).asLong();
                    if (System.currentTimeMillis() - last <= minDelayMilliseconds) {
                        ParticleEffect.playEffect(ParticleEffect.SPELL, player.getLocation().add(0.0, 1.0, 0.0), 0.6f, 20);
                        ((Player) player).playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.2f);
                        e.setCancelled(true);
                        e.setDamage(0.0);
                        edbee.getDamager().remove();
                        player.removeMetadata("lastArrowDamageEvent", (Plugin) Enchantments.getInstance());
                        return;
                    }
                }
                player.setMetadata("lastArrowDamageEvent", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) System.currentTimeMillis()));
            }
        }
    }
}
