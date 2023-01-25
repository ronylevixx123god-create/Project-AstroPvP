package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.UUID;

public class HeroicAntiGank extends HeroicCustomEnchantment {
    private DecimalFormat df;

    public HeroicAntiGank() {
        super("Righteous Anti Gank", "Anti Gank", GeneralUtil.axe, 2);
        this.max = 4;
    }


    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (player.hasMetadata("antiGankHeroic_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > player.getMetadata("antiGankHeroic_nextReset").get(0).asLong()) {
                player.removeMetadata("antiGankHeroic_uniqueEnemies", (Plugin) Enchantments.getInstance());
                player.setMetadata("antiGankHeroic_nextReset", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (MinecraftServerUtils.getCurrentServerTick() + 120)));
                return;
            }
        } else {
            player.setMetadata("antiGankHeroic_nextReset", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (MinecraftServerUtils.getCurrentServerTick() + 120)));
        }
        final HashSet<UUID> enemies = (HashSet<UUID>) (player.hasMetadata("antiGankHeroic_uniqueEnemies") ? player.getMetadata("antiGankHeroic_uniqueEnemies").get(0).value() : new HashSet<UUID>());
        if (enemies.size() >= 2 && Math.random() < 0.2) {
            e.setDamage(e.getDamage() * 1.5);
            final Location l = playerHit.getLocation().add(0.0, 1.0, 0.0);
        }
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(attacker instanceof Player)) {
            return;
        }
        if (player.hasMetadata("antiGankHeroic_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > player.getMetadata("antiGankHeroic_nextReset").get(0).asLong()) {
                player.removeMetadata("antiGankHeroic_uniqueEnemies", (Plugin) Enchantments.getInstance());
            }
        } else {
            player.setMetadata("antiGankHeroic_nextReset", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (MinecraftServerUtils.getCurrentServerTick() + 120)));
        }
        final HashSet<UUID> enemies = (HashSet<UUID>) (player.hasMetadata("antiGankHeroic_uniqueEnemies") ? player.getMetadata("antiGankHeroic_uniqueEnemies").get(0).value() : new HashSet<UUID>());
        final Player pAttacker = (Player) attacker;
        enemies.add(pAttacker.getUniqueId());
        player.setMetadata("antiGankHeroic_uniqueEnemies", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) enemies));
    }
}
