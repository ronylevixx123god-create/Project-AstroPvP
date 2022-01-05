package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.UUID;

public class HeroicAegis extends HeroicCustomEnchantment {
    private static final String aegisNextReset_MetadataKey = "aegis_nextReset";
    private static final String aegisUniqueEnemies_MetadataKey = "aegis_uniqueEnemies";

    public HeroicAegis() {
        super("Holy Aegis", "Aegis", GeneralUtil.armor, 2);
        this.max = 6;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(attacker instanceof Player)) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs"))
            return;
        int ticksToCheck = 100;
        if (player.hasMetadata(aegisNextReset_MetadataKey)) {
            if ((long)MinecraftServerUtils.getCurrentServerTick() > player.getMetadata(aegisNextReset_MetadataKey).get(0).asLong()) {
                player.removeMetadata(aegisUniqueEnemies_MetadataKey, Enchantments.getInstance());
                player.setMetadata(aegisNextReset_MetadataKey, new FixedMetadataValue(Enchantments.getInstance(), (MinecraftServerUtils.getCurrentServerTick() + ticksToCheck)));
            }
        } else {
            player.setMetadata(aegisNextReset_MetadataKey, new FixedMetadataValue(Enchantments.getInstance(), (MinecraftServerUtils.getCurrentServerTick() + ticksToCheck)));
        }
        HashSet<UUID> enemies = player.hasMetadata(aegisUniqueEnemies_MetadataKey) ? (HashSet<UUID>) player.getMetadata(aegisUniqueEnemies_MetadataKey).get(0).value() : new HashSet<>();
        Player pAttacker = (Player)attacker;
        if (enemies.size() > 1 && !enemies.contains(pAttacker.getUniqueId())) {
            e.setDamage(e.getDamage() * 0.2); //Could change this value?
            try {
                ParticleEffect.MAGIC_CRIT.sendToPlayers(Bukkit.getOnlinePlayers(), player.getEyeLocation(), 0.6f, 0.6f, 0.6f, 0.1f, 16);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return;
        }
        enemies.add(pAttacker.getUniqueId());
        player.setMetadata(aegisUniqueEnemies_MetadataKey, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)enemies));
    }
}
