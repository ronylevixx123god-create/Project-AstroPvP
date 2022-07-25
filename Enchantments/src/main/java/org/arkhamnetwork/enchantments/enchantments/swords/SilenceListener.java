package org.arkhamnetwork.enchantments.enchantments.swords;
import org.arkhamnetwork.enchantments.mastery.MasteryNeutralize;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.event.EventHandler;
import org.inventivetalent.particle.ParticleEffect;

public final class SilenceListener implements Listener {
    @EventHandler(
            priority = EventPriority.HIGH,
            ignoreCancelled = true
    )
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (e.getEntity().hasMetadata("neutralized")) {
            e.setDamage(e.getDamage(DamageModifier.BASE) * 1.35D);
        }

        if (e.getDamage(DamageModifier.BASE) > 0.0D && e.getEntity() instanceof Player && e.getEntity().hasMetadata("noDefenseProcs")) {
            int stacks = ((MetadataValue)e.getEntity().getMetadata("noDefenseProcs").get(0)).asInt();
            double extraStackDmg = 0.0625D * (double)stacks;
            e.setDamage(e.getDamage(DamageModifier.BASE) * (1.2D + extraStackDmg));

            for(int i = 0; i < stacks; ++i) {

                try {
                    de.inventivegames.particle.ParticleEffect.CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), e.getEntity().getLocation().clone().add(0.0D, 1.0D, 0.0D), 1.0f, 1.0f, 1.0f, 0.1f, 5);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }

    }
}
