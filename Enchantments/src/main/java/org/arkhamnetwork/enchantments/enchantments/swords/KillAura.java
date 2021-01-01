package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import org.arkhamnetwork.Arkkit.libs.*;

public class KillAura extends CustomEnchantment
{
    public KillAura() {
        super("Kill Aura", GeneralUtil.swords, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
    }
    
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        final double random = Math.random();
        final double chance = 0.05 * enchantLevel;
        if (target.hasMetadata("monsterAmount") && random < chance) {
            final int mobsToKill = (enchantLevel == 5 && Math.random() < 0.05) ? 3 : 2;
            target.setMetadata("killAura", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)mobsToKill));
            ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, target.getLocation().add(0.0, 0.5, 0.0), 0.55f, 3);
        }
    }
}
