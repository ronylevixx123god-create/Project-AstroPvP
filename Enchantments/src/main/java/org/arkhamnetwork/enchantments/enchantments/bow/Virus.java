package org.arkhamnetwork.enchantments.enchantments.bow;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class Virus extends CustomEnchantment {
    public Virus() {
        super("Virus", new Material[]{Material.BOW}, 2);
        this.max = 4;
        this.base = 20.0;
        this.interval = 0.0;
        Bukkit.getPluginManager().registerEvents((Listener) new VirusListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        playerHit.setMetadata("virusEnchantmentLevel", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
        playerHit.setMetadata("virusEnchantmentExpire", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + level * 1500)));
        final double random = Math.random() * 1.0;
        final double chance = 0.15 * level;
        if (random < chance) {
            playerHit.removePotionEffect(PotionEffectType.REGENERATION);
            ParticleEffect.playEffect(ParticleEffect.WITCH_MAGIC, playerHit.getLocation().add(0.0, 1.0, 0.0), 0.4f, 25);
            if (playerHit instanceof Player) {
                ((Player) playerHit).playSound(playerHit.getLocation(), Sound.ENDERMAN_TELEPORT, 0.6f, 2.0f);
            }
        }
    }
}
