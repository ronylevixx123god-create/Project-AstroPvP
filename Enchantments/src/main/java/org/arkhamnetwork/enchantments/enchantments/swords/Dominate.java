package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.nms.*;
import org.bukkit.metadata.*;
import org.arkhamnetwork.Arkkit.libs.*;

public class Dominate extends CustomEnchantment
{
    protected static String dominateEffectLevel_metadata;
    protected static String dominateEffectExpire_metadata;
    
    public Dominate() {
        super("Dominate", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 15.0;
        Bukkit.getServer().getPluginManager().registerEvents((Listener)new DominateEffectListener(), (Plugin)Enchantments.getInstance());
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final double procChance = 0.04 * level;
        if (Math.random() < procChance) {
            if (playerHit.hasMetadata(Dominate.dominateEffectLevel_metadata) && playerHit.getMetadata(Dominate.dominateEffectLevel_metadata).get(0).asInt() > level) {
                return;
            }
            if (!playerHit.hasMetadata(Dominate.dominateEffectLevel_metadata) && playerHit instanceof Player) {
                ((Player)playerHit).sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "* DOMINATED [" + ChatColor.RED + "-" + level * 5 + "% DMG for " + level * 2 + "s" + ChatColor.RED + ChatColor.BOLD + "] *");
            }
            playerHit.setMetadata(Dominate.dominateEffectExpire_metadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServerUtils.getCurrentServerTick() + level * 2 * 20)));
            playerHit.setMetadata(Dominate.dominateEffectLevel_metadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            ParticleEffect.playEffect(ParticleEffect.ENCHANTMENT_TABLE, playerHit.getLocation().add(0.0, 1.0, 0.0), 0.8f, 32);
        }
    }
    
    static {
        Dominate.dominateEffectLevel_metadata = "dominateEffect";
        Dominate.dominateEffectExpire_metadata = "dominateExpire";
    }
}
