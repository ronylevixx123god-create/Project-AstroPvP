package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SmokeBomb extends CustomEnchantment {
    public SmokeBomb() {
        super("Smoke Bomb", GeneralUtil.helmets, 3);
        this.max = 8;
        this.base = 25.0;
        this.interval = 10.0;
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player) {
            final Player p = (Player) player;
            double dmg = e.getFinalDamage();
            if (player.getHealth() - dmg <= 0.0) {
                if (p.hasMetadata("SmokeBomb")) {
                    return;
                }
                p.setMetadata("SmokeBomb", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) System.currentTimeMillis()));
                Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), () -> {
                    p.removeMetadata("SmokeBomb", Enchantments.getInstance());
                }, 180*20);
                final int radius = Math.round(level * 1.5f);
                final List<LivingEntity> victims = new ArrayList<LivingEntity>();
                final FPlayer fp = (FPlayer) FPlayers.i.get((OfflinePlayer) p);
                for (final Entity ent : player.getNearbyEntities((double) radius, (double) radius, (double) radius)) {
                    if (ent instanceof LivingEntity) {
                        if (ent instanceof Player) {
                            final FPlayer fpEnt = (FPlayer) FPlayers.i.get((OfflinePlayer) ent);
                            if (fp.getRelationTo((RelationParticipator) fpEnt).isAtLeast(Relation.TRUCE)) {
                                continue;
                            }
                        }
                        victims.add((LivingEntity) ent);
                    }
                }
                de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), p.getLocation(), 0f,1f,0f,1f,70);

                p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_TWINKLE2, 2.0f, 0.75f);
                for (final LivingEntity victim : victims) {
                    if (victim instanceof Player) {
                        ((Player) victim).sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + p.getName() + " has thrown a Smoke Bomb in an attempt to escape!");
                        ((Player) victim).getWorld().playSound(victim.getLocation(), Sound.FIREWORK_TWINKLE2, 1.6f, 0.75f);
                        ((Player) victim).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Math.max(level, 3) * 20, Math.min(level, 3)));
                        ((Player) victim).addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Math.max(level, 3) * 20, Math.min(level, 3)));
                    }
                }
                e.setCancelled(true);
                e.setDamage(0.0);
            }
        }
    }

    private Location getNearbyLocation(final Location l, final int radius, final int y_boost) {
        final Random rand = new Random();
        Location rand_loc = l.clone();
        rand_loc.add((double) ((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 1)), 0.0, (double) ((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 1)));
        rand_loc.add(0.5, (double) y_boost, 0.5);
        while ((rand_loc.getBlock().getType() != Material.AIR || rand_loc.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) && rand_loc.getY() < 255.0) {
            rand_loc = rand_loc.add(0.0, 1.0, 0.0);
        }
        return rand_loc;
    }
}
