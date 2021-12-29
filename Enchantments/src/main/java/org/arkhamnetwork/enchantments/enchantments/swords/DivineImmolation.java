/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils
 *  com.rit.sucy.CustomEnchantment
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.utils.WorldGuardUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.cmds.CommandEnchDust;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class DivineImmolation
extends CustomEnchantment {
    public DivineImmolation() {
        super("Divine Immolation", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 10.0;
        this.interval = 10.0;
        Bukkit.getPluginManager().registerEvents((Listener)new DivineImmolationListener(), (Plugin)Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity player, LivingEntity playerHit, int level, EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        Player p = (Player)player;
        double radius = 1.0 * (double)level;
        double damage = Math.floor((double)level * 1.25);
        if (!GeneralUtil.isSword(p.getItemInHand())) {
            return;
        }
        if (p.hasMetadata("lastItemHeldChange") && (long)MinecraftServerUtils.getCurrentServerTick() - ((MetadataValue)p.getMetadata("lastItemHeldChange").get(0)).asLong() <= 5L) {
            return;
        }
        if (p.hasMetadata("soulTrap") && ((MetadataValue)p.getMetadata("soulTrap").get(0)).asLong() > System.currentTimeMillis()) {
            return;
        }
        if (KillTrackerAPI.hasGodMode((Player)p) && KillTrackerAPI.getAllSouls((Player)p) > 0) {
            if (!p.hasMetadata("lastSoulRemoveEvent") || System.currentTimeMillis() - ((MetadataValue)p.getMetadata("lastSoulRemoveEvent").get(0)).asLong() > 1000L) {
                KillTrackerAPI.removeSoulsFromGems((Player)p, (int)20);
            }
            List<Entity> ents = playerHit.getNearbyEntities(radius, radius, radius);
            ents.add(playerHit);
            for (Entity ent : ents) {
                if (!(ent instanceof LivingEntity)) continue;
                Player pEnt = null;
                if (ent instanceof Player) {
                    pEnt = (Player)ent;
                }
                if (pEnt != null && p.getName().equals(pEnt.getName()) || pEnt != null && GeneralUtil.isAtleastTruce(p, pEnt) || pEnt != null && pEnt.getGameMode() != GameMode.SURVIVAL || pEnt != null && !p.canSee(pEnt) || WorldGuardUtils.isPvPDisabled((Location)ent.getLocation()) || ent.hasMetadata("spectator")) continue;
                LivingEntity le = (LivingEntity)ent;
                le.damage(damage);
                try {
                    ParticleEffect.FLAME.sendToPlayers(Bukkit.getOnlinePlayers(), le.getEyeLocation(), 0.15f, 0.15f, 0.15f, 0.1f, 30);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                try {
                    ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), le.getEyeLocation(), 0.5f, 0.5f, 0.5f, 0.1f, 20);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                le.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, (int)(damage * 20.0), 1));
                if (le instanceof Player) {
                    ((Player)le).playSound(le.getLocation(), Sound.FIREWORK_BLAST, 1.0f, 0.3f);
                    ((Player)le).playSound(le.getLocation(), Sound.ZOMBIE_PIG_ANGRY, 0.8f, 0.5f);
                }
                ent.setMetadata("divineFire", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)System.currentTimeMillis()));
                ent.setMetadata("divineFireLevel", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
            }
        }
    }
}

