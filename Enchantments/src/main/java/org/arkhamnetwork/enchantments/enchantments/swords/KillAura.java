package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class KillAura extends CustomEnchantment {
    public KillAura() {
        super("Kill Aura", GeneralUtil.swords, 2);
        this.max = 5;
        this.base = 15.0;
        this.interval = 6.0;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        final double random = Math.random();
        final double chance = 0.05 * enchantLevel;
        if (target.hasMetadata("monsterAmount") && random < chance) {
            final int mobsToKill = (enchantLevel == 5 && Math.random() < 0.05) ? 3 : 2;
            target.setMetadata("killAura", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) mobsToKill));
            //ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, target.getLocation().add(0.0, 0.5, 0.0), 0.55f, 3);
        }
    }
}
