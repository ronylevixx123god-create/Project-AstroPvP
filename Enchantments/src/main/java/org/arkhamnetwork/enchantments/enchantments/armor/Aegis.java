package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Aegis extends CustomEnchantment {
    private static final String aegisNextReset_MetadataKey = "aegis_nextReset";

    private static final String aegisUniqueEnemies_MetadataKey = "aegis_uniqueEnemies";

    public Aegis() {
        super("Aegis", GeneralUtil.armor, 4);
        this.max = 6;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(attacker instanceof Player))
            return;
       /* if (player.hasMetadata("aegis_nextReset")) {
            if (MinecraftServerUtils.getCurrentServerTick() > player.getMetadata("aegis_nextReset").get(0).asLong()) {
                player.removeMetadata("aegis_uniqueEnemies", Enchantments.getInstance());
                player.setMetadata("aegis_nextReset", new FixedMetadataValue(Enchantments.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick() + 100)));
            }
        } else {
            player.setMetadata("aegis_nextReset", new FixedMetadataValue(Enchantments.getInstance(), Integer.valueOf(MinecraftServerUtils.getCurrentServerTick() + 100)));
        }
        HashSet<UUID> enemies = player.hasMetadata("aegis_uniqueEnemies") ? (HashSet<UUID>) player.getMetadata("aegis_uniqueEnemies").get(0).value() : new HashSet<>();
        Player pAttacker = (Player)attacker;
        if (enemies.size() > 8 - level && !enemies.contains(pAttacker.getUniqueId())) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE));
            ParticleEffect.playEffect(ParticleEffect.MAGIC_CRIT, player.getEyeLocation(), 0.6F, 16);
            return;
        }
        enemies.add(pAttacker.getUniqueId());
        player.setMetadata("aegis_uniqueEnemies", new FixedMetadataValue(Enchantments.getInstance(), enemies));*/
    }
}
