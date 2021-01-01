package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.*;
import org.bukkit.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;

public class Pacify extends CustomEnchantment
{
    public Pacify() {
        super("Pacify", new Material[] { Material.BOW }, 3);
        this.max = 4;
        this.base = 17.0;
        this.interval = 4.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (Math.random() < 0.5 + level * 0.125 && playerHit instanceof Player) {
            final Player pHit = (Player)playerHit;
            pHit.setMetadata("noRageUntil", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(System.currentTimeMillis() + level * 750)));
        }
    }
}
