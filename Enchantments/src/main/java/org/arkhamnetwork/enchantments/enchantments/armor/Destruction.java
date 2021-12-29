/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.Relation
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 *  org.zencode.shortninja.staffplus.StaffPlus
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.earth2me.essentials.Essentials;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.HashMap;

public class Destruction
extends CustomEnchantment {
    Essentials ess3;
    private static HashMap<Integer, BukkitTask> destructionTasks = new HashMap();
    private Multimap<Integer, Player> destructionPlayers = HashMultimap.create();

    public Destruction() {
        super("Destruction", GeneralUtil.helmets, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 5.0;
        this.ess3 = Bukkit.getPluginManager().isPluginEnabled("Essentials") ? (Essentials)Bukkit.getPluginManager().getPlugin("Essentials") : null;
        for (int i = 1; i <= this.max; ++i) {
            final int level = i;
            BukkitTask existing = destructionTasks.get(i);
            if (existing != null) {
                existing.cancel();
            }
            BukkitTask task = new BukkitRunnable(){

                public void run() {
                    Collection<Player> leveledPlayers = Destruction.this.destructionPlayers.get(level);
                    if (leveledPlayers == null || leveledPlayers.isEmpty()) {
                        return;
                    }
                    int radius = level;
                    leveledPlayers.forEach(player -> {
                        if (!player.isValid() || player.isDead() || player.hasMetadata("spectator") || Destruction.this.ess3.getUser(player).isGodModeEnabled()) {
                            return;
                        }
                        FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
                        for (Entity ent : player.getNearbyEntities((double)radius, (double)radius, (double)radius)) {
                            Player p;
                            if (!(ent instanceof Player) || (p = (Player)ent).getGameMode() != GameMode.SURVIVAL || p.hasMetadata("NPC")) continue;
                            if (p.hasMetadata("spectator") || Destruction.this.ess3.getUser(p).isVanished()) {
                                return;
                            }
                            FPlayer fp = (FPlayer)FPlayers.i.get((OfflinePlayer)p);
                            if (!fp.getFaction().getRelationTo((RelationParticipator)fplayer.getFaction()).isAtMost(Relation.NEUTRAL) || WorldGuardUtils.isPvPDisabled(p.getLocation())) continue;
                            if (p.getHealth() > 0.0 && !p.isDead()) {
                                p.damage(level == 5 ? 2.0 : 1.0);
                                try {
                                    ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 0.5f, 0.5f, 0.1f, 20);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                            if (level >= 1 && Math.random() <= 0.15) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 20, level > 2 ? 1 : 0));
                                try {
                                    ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 0.5f, 0.5f, 0.1f, 20);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                            if (level >= 2 && Math.random() <= 0.1) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, level * 20 * 2, level + 1));
                                try {
                                    ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 0.5f, 0.5f, 0.1f, 20);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                            if (level < 3 || !(Math.random() <= 0.085)) continue;
                            p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, level * 20 * 2, 1));
                            try {
                                ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 0.5f, 0.5f, 0.1f, 30);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    });
                }
            }.runTaskTimer((Plugin)Enchantments.getInstance(), 60L * (long)(5 / level), 60L * (long)(5 / level));
            destructionTasks.put(i, task);
        }
    }

    public void applyEquipEffect(Player player, int level) {
        player.setMetadata(this.name() + "_enchant", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
        this.destructionPlayers.put(level, player);
    }

    public void applyUnequipEffect(Player player, int level) {
        player.removeMetadata(this.name() + "_enchant", (Plugin)Enchantments.getInstance());
        this.destructionPlayers.remove((Object)level, (Object)player);
    }
}

