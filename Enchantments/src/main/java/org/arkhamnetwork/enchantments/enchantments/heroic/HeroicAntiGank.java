package org.arkhamnetwork.enchantments.enchantments.heroic;

import org.arkhamnetwork.enchantments.util.*;
import com.cosmicpvp.cosmicutils.nms.*;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import java.util.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;

public class HeroicAntiGank extends HeroicCustomEnchantment
{
    public HeroicAntiGank() {
        super("Righteous Anti Gank", "Anti Gank", GeneralUtil.axe, 7);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (player.hasMetadata("HantiGank_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > player.getMetadata("HantiGank_nextReset").get(0).asLong()) {
                player.removeMetadata("HantiGank_uniqueEnemies", (Plugin)Enchantments.getInstance());
                player.setMetadata("HantiGank_nextReset", new FixedMetadataValue((Plugin)Enchantments.getInstance(), MinecraftServerUtils.getCurrentServerTick() + 120));
                return;
            }
        }
        else {
            player.setMetadata("HantiGank_nextReset", new FixedMetadataValue((Plugin)Enchantments.getInstance(), MinecraftServerUtils.getCurrentServerTick() + 120));
        }
        final HashSet<UUID> enemies = (HashSet<UUID>)(player.hasMetadata("HantiGank_uniqueEnemies") ? player.getMetadata("HantiGank_uniqueEnemies").get(0).value() : new HashSet<UUID>());
        if (enemies.size() >= 2) {
            if (Math.random() < 0.5) {
                final Location l = playerHit.getLocation().add(0.0, 1.0, 0.0);
                WorldUtil.sendWorldEffect(l.getBlock(), 86, 2001);
                return;
            }
            final Location l = playerHit.getLocation().add(0.0, 1.0, 0.0);
            WorldUtil.sendWorldEffect(l.getBlock(), 86, 2001);
        }
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(attacker instanceof Player)) {
            return;
        }
        if (player.hasMetadata("HantiGank_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > player.getMetadata("HantiGank_nextReset").get(0).asLong()) {
                player.removeMetadata("HantiGank_uniqueEnemies", (Plugin)Enchantments.getInstance());
            }
        }
        else {
            player.setMetadata("HantiGank_nextReset", new FixedMetadataValue((Plugin)Enchantments.getInstance(), MinecraftServerUtils.getCurrentServerTick() + 120));
        }
        final HashSet<UUID> enemies = (HashSet<UUID>)(player.hasMetadata("HantiGank_uniqueEnemies") ? player.getMetadata("HantiGank_uniqueEnemies").get(0).value() : new HashSet<UUID>());
        final Player pAttacker = (Player)attacker;
        enemies.add(pAttacker.getUniqueId());
        player.setMetadata("HantiGank_uniqueEnemies", new FixedMetadataValue((Plugin)Enchantments.getInstance(), enemies));
    }
}
