package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.Arkkit.libs.*;
import com.massivecraft.factions.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import com.massivecraft.factions.iface.*;
import com.massivecraft.factions.struct.*;
import java.util.*;

public class Epicness extends CustomEnchantment
{
    public Epicness() {
        super("Epicness", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 10.0;
        this.interval = 10.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double random = Math.random();
        final double chance = 0.5;
        if (random < chance) {
            if (level == 1) {
                this.playSoundToEnemies(Sound.CHICKEN_HURT, 1.0f, 0.85f, playerHit);
                ParticleEffect.sendToLocation(ParticleEffect.CLOUD, playerHit.getEyeLocation(), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.25f, 20);
            }
            else if (level == 2) {
                this.playSoundToEnemies(Sound.MAGMACUBE_WALK2, 1.2f, 0.8f, playerHit);
                ParticleEffect.sendToLocation(ParticleEffect.SLIME, playerHit.getEyeLocation(), (float)Math.random(), (float)Math.random(), (float)Math.random(), 1.0f, 20);
            }
            else if (level == 3) {
                this.playSoundToEnemies(Sound.GHAST_SCREAM, 1.0f, 1.25f, playerHit);
                ParticleEffect.sendToLocation(ParticleEffect.WITCH_MAGIC, playerHit.getEyeLocation(), (float)Math.random(), (float)Math.random(), (float)Math.random(), 0.95f, 15);
            }
        }
    }
    
    private void playSoundToEnemies(final Sound s, final float volume, final float pitch, final LivingEntity le) {
        if (!(le instanceof Player)) {
            return;
        }
        final Player p = (Player)le;
        final FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)p);
        for (final Entity ent : p.getNearbyEntities(16.0, 16.0, 16.0)) {
            if (ent instanceof Player) {
                final Player pEnt = (Player)ent;
                final FPlayer fpEnt = (FPlayer)FPlayers.i.get((OfflinePlayer)pEnt);
                if (fp.getFaction().getRelationTo((RelationParticipator)fpEnt.getFaction()).isAtLeast(Relation.TRUCE)) {
                    continue;
                }
                pEnt.playSound(pEnt.getLocation(), s, volume, pitch);
            }
        }
    }
}
