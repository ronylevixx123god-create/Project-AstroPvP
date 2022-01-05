/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.rit.sucy.CustomEnchantment
 *  net.minecraft.server.v1_7_R4.EntityPlayer
 *  net.minecraft.server.v1_7_R4.EntityTracker
 *  net.minecraft.server.v1_7_R4.EntityTrackerEntry
 *  net.minecraft.server.v1_7_R4.WorldServer
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.craftbukkit.v1_7_R4.CraftWorld
 *  org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityTracker;
import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class EnderWalker
extends CustomEnchantment {
    public EnderWalker() {
        super("Ender Walker", GeneralUtil.boots, 2);
        this.max = 5;
        this.base = 20.0;
        this.interval = 10.0;
    }

    public void applyEquipEffect(Player player, int level) {
        player.playSound(player.getLocation(), Sound.WITHER_HURT, 1.0f, 0.6f);
    }

    public void applyUnequipEffect(Player player, int level) {
    }

    public void applyDefenseEffect(final LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.WITHER || e.getCause() == EntityDamageEvent.DamageCause.POISON) {
            boolean heal;
            e.setCancelled(true);
            boolean bl = heal = Math.random() <= (double)level * 0.15;
            if (heal && GeneralUtil.healPlayer(player, e.getDamage())) {
                try {
                    ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation().add(0.0, 1.0, 0.0), 0.8f, 0.8f, 0.8f, 0.1f, 40);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            e.setDamage(0.0);
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable(){

                @Override
                public void run() {
                    if (!player.hasPotionEffect(PotionEffectType.BLINDNESS) && !player.hasMetadata("clarityEnchant")) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
                    }
                }
            }, 2L);
            try {
                ParticleEffect.PORTAL.sendToPlayers(Bukkit.getOnlinePlayers(), player.getLocation().add(0.0, 1.0, 0.0), 0.8f, 0.8f, 0.8f, 0.1f, 70);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private List<Player> getNearbyPlayers(List<Entity> ents) {
        ArrayList<Player> players = new ArrayList<Player>();
        for (Entity e : ents) {
            if (!(e instanceof Player)) continue;
            players.add((Player)e);
        }
        return players;
    }

    public static void updateEntity(Entity entity, List<Player> observers) {
        World world = entity.getWorld();
        WorldServer worldServer = ((CraftWorld)world).getHandle();
        EntityTracker tracker = worldServer.tracker;
        EntityTrackerEntry entry = (EntityTrackerEntry)tracker.trackedEntities.get(entity.getEntityId());
        List<EntityHuman> nmsPlayers = EnderWalker.getNmsPlayers(observers);
        if (entry != null) {
            entry.trackedPlayers.removeAll(nmsPlayers);
            entry.scanPlayers(nmsPlayers);
        }
    }

    private static List<EntityHuman> getNmsPlayers(List<Player> players) {
        ArrayList<EntityHuman> nsmPlayers = new ArrayList<>();
        for (Player bukkitPlayer : players) {
            CraftPlayer craftPlayer = (CraftPlayer)bukkitPlayer;
            nsmPlayers.add(craftPlayer.getHandle());
        }
        return nsmPlayers;
    }
}

