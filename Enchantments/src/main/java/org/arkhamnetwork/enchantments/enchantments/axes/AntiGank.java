package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.*;
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

public class AntiGank extends CustomEnchantment
{
    private static final String antiGankNextReset_MetadataKey = "antiGank_nextReset";
    private static final String antiGankUniqueEnemies_MetadataKey = "antiGank_uniqueEnemies";
    private static final int ticksToCheck = 120;
    
    public AntiGank() {
        super("Anti Gank", GeneralUtil.axe, 7);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (player.hasMetadata("antiGank_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > player.getMetadata("antiGank_nextReset").get(0).asLong()) {
                player.removeMetadata("antiGank_uniqueEnemies", (Plugin)Enchantments.getInstance());
                player.setMetadata("antiGank_nextReset", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServerUtils.getCurrentServerTick() + 120)));
                return;
            }
        }
        else {
            player.setMetadata("antiGank_nextReset", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServerUtils.getCurrentServerTick() + 120)));
        }
        final HashSet<UUID> enemies = (HashSet<UUID>)(player.hasMetadata("antiGank_uniqueEnemies") ? player.getMetadata("antiGank_uniqueEnemies").get(0).value() : new HashSet<UUID>());
        if (enemies.size() >= 6 - level) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * Math.min(1.5, 1.0 + 0.1 * enemies.size()));
            final Location l = playerHit.getLocation().add(0.0, 1.0, 0.0);
            WorldUtil.sendWorldEffect(l.getBlock(), 86, 2001);
        }
    }
    
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(attacker instanceof Player)) {
            return;
        }
        if (player.hasMetadata("antiGank_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > player.getMetadata("antiGank_nextReset").get(0).asLong()) {
                player.removeMetadata("antiGank_uniqueEnemies", (Plugin)Enchantments.getInstance());
            }
        }
        else {
            player.setMetadata("antiGank_nextReset", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServerUtils.getCurrentServerTick() + 120)));
        }
        final HashSet<UUID> enemies = (HashSet<UUID>)(player.hasMetadata("antiGank_uniqueEnemies") ? player.getMetadata("antiGank_uniqueEnemies").get(0).value() : new HashSet<UUID>());
        final Player pAttacker = (Player)attacker;
        enemies.add(pAttacker.getUniqueId());
        player.setMetadata("antiGank_uniqueEnemies", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enemies));
    }
}
