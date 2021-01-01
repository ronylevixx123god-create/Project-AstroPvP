package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.bukkit.event.entity.*;
import com.massivecraft.factions.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import com.massivecraft.factions.iface.*;
import com.massivecraft.factions.struct.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.Arkkit.libs.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.util.*;
import org.bukkit.util.Vector;

public class Shockwave extends CustomEnchantment
{
    public Shockwave() {
        super("Shockwave", GeneralUtil.chestplates, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (GeneralUtil.isKOTHWorld(player.getWorld())) {
            return;
        }
        final double chance = level * 0.02;
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && Math.random() < chance) {
            final FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
            final boolean KOTH = player.getWorld().getName().equals("world_koth");
            for (final Entity ent : player.getNearbyEntities((double)level, (double)level, (double)level)) {
                if (ent instanceof LivingEntity) {
                    if (ent instanceof Player) {
                        if (Enchantments.getEssentials().getUser((Player)ent).isVanished()) {
                            continue;
                        }
                        final FPlayer fpEnt = (FPlayer)FPlayers.i.get((OfflinePlayer)ent);
                        if (fpEnt.getRelationTo((RelationParticipator)fp).isAtLeast(Relation.ALLY)) {
                            continue;
                        }
                    }
                    if (WorldGuardUtils.isPvPDisabled(ent.getLocation())) {
                        continue;
                    }
                    if (KOTH) {
                        GeneralUtil.pushAwayEntity(player, ent, 1.350000023841858);
                    }
                    else {
                        GeneralUtil.pushAwayEntity(player, ent, 1.7000000476837158);
                    }
                    final Location l = ent.getLocation();
                    ParticleEffect.sendToLocation(ParticleEffect.ENCHANTMENT_TABLE, l, (float)Math.random() * 2.0f, (float)Math.random() * 2.0f, (float)Math.random() * 2.0f, 0.73f, 100);
                }
            }
            player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 0.1f);
            ParticleEffect.sendToLocation(ParticleEffect.ENCHANTMENT_TABLE, player.getEyeLocation(), (float)Math.random() * 3.0f, (float)Math.random() * 3.0f, (float)Math.random() * 3.0f, 0.73f, 100);
        }
    }
    
    private void pushAwayEntity(final LivingEntity center, final Entity entity, final double speed) {
        final Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
