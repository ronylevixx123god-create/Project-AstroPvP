package org.arkhamnetwork.enchantments.enchantments.swords;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Epicness extends CustomEnchantment {
    public Epicness() {
        super("Epicness", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 10.0;
        this.interval = 10.0;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double random = Math.random();
        final double chance = 0.5;
        if (random < chance) {
            if (level == 1) {
                this.playSoundToEnemies(Sound.CHICKEN_HURT, 1.0f, 0.85f, playerHit);
                de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(),playerHit.getEyeLocation(), (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.25f, 20);

            } else if (level == 2) {
                this.playSoundToEnemies(Sound.MAGMACUBE_WALK2, 1.2f, 0.8f, playerHit);
                de.inventivegames.particle.ParticleEffect.SLIME.sendToPlayers(Bukkit.getOnlinePlayers(),playerHit.getEyeLocation(), (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.25f, 20);
            } else if (level == 3) {
                this.playSoundToEnemies(Sound.GHAST_SCREAM, 1.0f, 1.25f, playerHit);
                ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(),playerHit.getEyeLocation(), (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.25f, 20);
            }
        }
    }

    private void playSoundToEnemies(final Sound s, final float volume, final float pitch, final LivingEntity le) {
        if (!(le instanceof Player)) {
            return;
        }
        final Player p = (Player) le;
        final FPlayer fp = (FPlayer) FPlayers.i.get((OfflinePlayer) p);
        for (final Entity ent : p.getNearbyEntities(16.0, 16.0, 16.0)) {
            if (ent instanceof Player) {
                final Player pEnt = (Player) ent;
                final FPlayer fpEnt = (FPlayer) FPlayers.i.get((OfflinePlayer) pEnt);
                if (fp.getFaction().getRelationTo((RelationParticipator) fpEnt.getFaction()).isAtLeast(Relation.TRUCE)) {
                    continue;
                }
                pEnt.playSound(pEnt.getLocation(), s, volume, pitch);
            }
        }
    }
}
