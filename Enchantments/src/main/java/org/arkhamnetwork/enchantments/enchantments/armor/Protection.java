/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Multimap
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.net.astropvp.armorsets.struct.Relation
 *  com.rit.sucy.CustomEnchantment
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
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

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.perms.Relation;
import com.rit.sucy.CustomEnchantment;
import de.inventivegames.particle.ParticleEffect;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
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

public class Protection
extends CustomEnchantment {
    private static HashMap<Integer, BukkitTask> protectionTasks = new HashMap();
    private Multimap<Integer, Player> protectionPlayers = HashMultimap.create();

    public Protection() {
        super("Protection", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 5.0;
        for (int i = 1; i <= this.max; ++i) {
            final int level = i;
            BukkitTask existing = protectionTasks.get(i);
            if (existing != null) {
                existing.cancel();
            }
            final int radius = level * 2;
            BukkitTask task = new BukkitRunnable(){

                public void run() {
                    Collection<Player> leveledPlayers = Protection.this.protectionPlayers.get(level);
                    if (leveledPlayers == null || leveledPlayers.isEmpty()) {
                        return;
                    }
                    leveledPlayers.forEach(player -> {
                        if (!player.isValid() || player.isDead() || player.hasMetadata("spectator") || player.hasMetadata("in_duel")) {
                            return;
                        }
                        FPlayer fplayer = (FPlayer)FPlayers.getInstance().getByPlayer(player);
                        int amountToHeal = level > 4 ? 2 : 1;
                        for (Entity ent : player.getNearbyEntities((double)radius, (double)radius, (double)radius)) {
                            Player p;
                            FPlayer fp;
                            if (!(ent instanceof Player) || (fp = (FPlayer)FPlayers.getInstance().getByPlayer((p = (Player)ent))) == null || fp.getFaction() == null || !fp.getFaction().getRelationTo((RelationParticipator)fplayer.getFaction()).isAtLeast(Relation.ALLY)) continue;
                            if (p.getHealth() + (double)amountToHeal < p.getMaxHealth() && p.getHealth() > 0.0 && !p.isDead()) {
                                p.setHealth(p.getHealth() + (double)amountToHeal);
                                try {
                                    //ParticleEffect.RED_DUST.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 0.5f, 0.5f, 0.1f, 20);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                            if (level >= 1 && Math.random() <= 0.15) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, level * 20, level > 2 ? 1 : 0));
                                try {
                                    //ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 0.5f, 0.5f, 0.1f, 10);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }
                            }
                            if (level >= 2 && Math.random() <= 0.1) {
                                try {
                                    //ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 0.5f, 0.5f, 0.1f, 20);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }                            }
                            if (level < 3 || !(Math.random() <= 0.005)) continue;
                            p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, level * 20 * 2, level + 1));
                            try {
                                //ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation().add(0.0, 1.0, 0.0), 0.5f, 0.5f, 0.5f, 0.1f, 30);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }                        }
                    });
                }
            }.runTaskTimer((Plugin)Enchantments.getInstance(), (long)(40 * (5 / level)), (long)(40 * (5 / level)));
            protectionTasks.put(i, task);
        }
    }

    public void applyEquipEffect(Player player, int level) {
        player.setMetadata(this.name() + "_enchant", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)level));
        this.protectionPlayers.put(level, player);
    }

    public void applyUnequipEffect(Player player, int level) {
        player.removeMetadata(this.name() + "_enchant", (Plugin)Enchantments.getInstance());
        this.protectionPlayers.remove((Object)level, (Object)player);
    }
}

