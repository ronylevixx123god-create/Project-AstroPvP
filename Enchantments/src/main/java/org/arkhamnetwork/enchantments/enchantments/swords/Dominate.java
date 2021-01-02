package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Dominate extends CustomEnchantment {
    protected static String dominateEffectLevel_metadata;
    protected static String dominateEffectExpire_metadata;

    static {
        Dominate.dominateEffectLevel_metadata = "dominateEffect";
        Dominate.dominateEffectExpire_metadata = "dominateExpire";
    }

    public Dominate() {
        super("Dominate", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 15.0;
        Bukkit.getServer().getPluginManager().registerEvents((Listener) new DominateEffectListener(), (Plugin) Enchantments.getInstance());
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double procChance = 0.04 * level;
        if (Math.random() < procChance) {
            if (playerHit.hasMetadata(Dominate.dominateEffectLevel_metadata) && playerHit.getMetadata(Dominate.dominateEffectLevel_metadata).get(0).asInt() > level) {
                return;
            }
            if (!playerHit.hasMetadata(Dominate.dominateEffectLevel_metadata) && playerHit instanceof Player) {
                ((Player) playerHit).sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "* DOMINATED [" + ChatColor.RED + "-" + level * 5 + "% DMG for " + level * 2 + "s" + ChatColor.RED + ChatColor.BOLD + "] *");
            }
            playerHit.setMetadata(Dominate.dominateEffectExpire_metadata, (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (MinecraftServerUtils.getCurrentServerTick() + level * 2 * 20)));
            playerHit.setMetadata(Dominate.dominateEffectLevel_metadata, (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
            ParticleEffect.playEffect(ParticleEffect.ENCHANTMENT_TABLE, playerHit.getLocation().add(0.0, 1.0, 0.0), 0.8f, 32);
        }
    }
}
