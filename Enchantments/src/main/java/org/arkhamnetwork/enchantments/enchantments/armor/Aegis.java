package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.nms.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;
import java.util.*;
import com.cosmicpvp.cosmicutils.utils.*;

public class Aegis extends CustomEnchantment
{
    private static final String aegisNextReset_MetadataKey = "aegis_nextReset";
    private static final String aegisUniqueEnemies_MetadataKey = "aegis_uniqueEnemies";

    public Aegis() {
        super("Aegis", GeneralUtil.armor, 4);
        this.max = 6;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        /*if (!(attacker instanceof Player)) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs")) {
            return;
        }
        final int ticksToCheck = 100;
        if (player.hasMetadata("aegis_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > player.getMetadata("aegis_nextReset").get(0).asLong()) {
                player.removeMetadata("aegis_uniqueEnemies", (Plugin)Enchantments.getInstance());
                player.setMetadata("aegis_nextReset", new FixedMetadataValue((Plugin)Enchantments.getInstance(), MinecraftServerUtils.getCurrentServerTick() + 100));
            }
        }
        else {
            player.setMetadata("aegis_nextReset", new FixedMetadataValue((Plugin)Enchantments.getInstance(), MinecraftServerUtils.getCurrentServerTick() + 100));
        }
        final HashSet<UUID> enemies = (HashSet<UUID>)(player.hasMetadata("aegis_uniqueEnemies") ? player.getMetadata("aegis_uniqueEnemies").get(0).value() : new HashSet<UUID>());
        final Player pAttacker = (Player)attacker;
        if (enemies.size() > 8 - level && !enemies.contains(pAttacker.getUniqueId())) {
            return;
        }
        enemies.add(pAttacker.getUniqueId());
        player.setMetadata("aegis_uniqueEnemies", new FixedMetadataValue((Plugin)Enchantments.getInstance(), enemies));*/
    }
}
