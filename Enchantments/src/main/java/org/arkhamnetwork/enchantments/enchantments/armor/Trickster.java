package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.event.player.*;
import org.arkhamnetwork.Arkkit.libs.*;
import org.bukkit.*;

public class Trickster extends CustomEnchantment
{
    public Trickster() {
        super("Trickster", GeneralUtil.armor, 2);
        this.max = 8;
        this.base = 20.0;
        this.interval = 5.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        final double random = Math.random();
        final double chance = 0.0125 * level;
        if (!(attacker instanceof Player)) {
            return;
        }
        if (attacker != null && random < chance) {
            final Location l1 = player.getLocation().add(0.0, 1.0, 0.0);
            final Location l2 = player.getTargetBlock((HashSet<Byte>) null, 4).getLocation().add(0.0, 1.0, 0.0).setDirection(attacker.getLocation().getDirection());
            if (l2.getBlock().getType() == Material.AIR && l2.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.AIR) {
                player.teleport(l2, PlayerTeleportEvent.TeleportCause.PLUGIN);
                player.getWorld().playSound(l1, Sound.PORTAL_TRIGGER, 0.8f, 1.4f);
                ParticleEffect.sendToLocation(ParticleEffect.CLOUD, l1, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5f, 20);
                ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, l2, (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.5f, 20);
            }
        }
    }
}
