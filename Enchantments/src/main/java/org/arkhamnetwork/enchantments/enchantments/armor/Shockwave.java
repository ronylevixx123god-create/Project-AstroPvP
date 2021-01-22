package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class Shockwave extends CustomEnchantment {
    public Shockwave() {
        super("Shockwave", GeneralUtil.chestplates, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 5.0;
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (GeneralUtil.isKOTHWorld(player.getWorld())) {
            return;
        }
        final double chance = level * 0.02;
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && Math.random() < chance) {
            final FPlayer fp = (FPlayer) FPlayers.i.get((OfflinePlayer) player);
            final boolean KOTH = player.getWorld().getName().equals("world_koth");
            for (final Entity ent : player.getNearbyEntities((double) level, (double) level, (double) level)) {
                if (ent instanceof LivingEntity) {
                    if (ent instanceof Player) {
                        if (Enchantments.getEssentials().getUser((Player) ent).isVanished()) {
                            continue;
                        }
                        final FPlayer fpEnt = (FPlayer) FPlayers.i.get((OfflinePlayer) ent);
                        if (fpEnt.getRelationTo((RelationParticipator) fp).isAtLeast(Relation.ALLY)) {
                            continue;
                        }
                    }
                    if (WorldGuardUtils.isPvPDisabled(ent.getLocation())) {
                        continue;
                    }
                    if (KOTH) {
                        GeneralUtil.pushAwayEntity(player, ent, 1.350000023841858);
                    } else {
                        GeneralUtil.pushAwayEntity(player, ent, 1.7000000476837158);
                    }
                    final Location l = ent.getLocation();
                    de.inventivegames.particle.ParticleEffect.ENCHANTMENT_TABLE.sendToPlayers(Bukkit.getOnlinePlayers(), l, (float) Math.random() * 2.0f, (float) Math.random() * 2.0f, (float) Math.random() * 2.0f, 0.73f, 100);

                }
            }
            player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 0.1f);
            de.inventivegames.particle.ParticleEffect.ENCHANTMENT_TABLE.sendToPlayers(Bukkit.getOnlinePlayers(), player.getEyeLocation(), (float) Math.random() * 3.0f, (float) Math.random() * 3.0f, (float) Math.random() * 3.0f, 0.73f, 100);
        }
    }

    private void pushAwayEntity(final LivingEntity center, final Entity entity, final double speed) {
        final Vector unitVector = entity.getLocation().toVector().subtract(center.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
