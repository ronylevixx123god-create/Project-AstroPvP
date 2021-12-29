/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
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

public class Dominate
extends CustomEnchantment {
    protected static String dominateEffectLevel_metadata = "dominateEffect";
    protected static String dominateEffectExpire_metadata = "dominateExpire";

    public Dominate() {
        super("Dominate", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 15.0;
        Bukkit.getServer().getPluginManager().registerEvents((Listener)new DominateEffectListener(), (Plugin)Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        double procChance = 0.04 * (double)level;
        if (Math.random() < procChance) {
            if (playerHit.hasMetadata(dominateEffectLevel_metadata) && ((MetadataValue)playerHit.getMetadata(dominateEffectLevel_metadata).get(0)).asInt() > level) {
                return;
            }
            if (!playerHit.hasMetadata(dominateEffectLevel_metadata) && playerHit instanceof Player) {
                ((Player)playerHit).sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "* DOMINATED [" + (Object)ChatColor.RED + "-" + level * 5 + "% DMG for " + level * 2 + "s" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "] *");
            }
            DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), CC.RedB + "* DOMINATE *", "DOMINATE");

            playerHit.setMetadata(dominateEffectExpire_metadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(MinecraftServerUtils.getCurrentServerTick() + level * 2 * 20)));
            playerHit.setMetadata(dominateEffectLevel_metadata, (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            try {
                ParticleEffect.ENCHANTMENT_TABLE.sendToPlayers(Bukkit.getOnlinePlayers(), playerHit.getLocation().add(0.0, 1.0, 0.0), 0.8f, 0.8f, 0.8f, 0.1f, 32);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }
}

