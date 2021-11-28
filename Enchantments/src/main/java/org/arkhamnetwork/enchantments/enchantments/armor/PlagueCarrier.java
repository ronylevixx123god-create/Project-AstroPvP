package org.arkhamnetwork.enchantments.enchantments.armor;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.arkhamnetwork.enchantments.util.GeneralCooldown;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlagueCarrier extends CustomEnchantment {
    public PlagueCarrier() {
        super("Plague Carrier", GeneralUtil.leggings, 3);
        this.max = 8;
        this.base = 25.0;
        this.interval = 20.0;
        Enchantments.getInstance().getServer().getPluginManager().registerEvents((Listener) new PlagueCarrierListener(), (Plugin) Enchantments.getInstance());
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (player instanceof Player) {
            final Player p = (Player) player;
            double dmg = e.getFinalDamage();
            if (player.getHealth() - dmg <= 0.0) {
                if (p.hasMetadata("PlagueCarrier")) {
                    return;
                }
                p.setMetadata("PlagueCarrier", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) System.currentTimeMillis()));
                Bukkit.getScheduler().runTaskLater(Enchantments.getInstance(), () -> {p.removeMetadata("PlagueCarrier", Enchantments.getInstance());}, 10000L);
                final int radius = Math.round(level * 1.5f);
                final List<LivingEntity> victims = new ArrayList<LivingEntity>();
                final FPlayer fp = (FPlayer) FPlayers.i.get((OfflinePlayer) p);
                for (final Entity ent : player.getNearbyEntities((double) radius, (double) radius, (double) radius)) {
                    if (ent instanceof LivingEntity) {
                        if (ent instanceof Player) {
                            final Player pEnt = (Player) ent;
                            final FPlayer fpEnt = (FPlayer) FPlayers.i.get((OfflinePlayer) pEnt);
                            if (fpEnt.getRelationTo((RelationParticipator) fp).isAtLeast(Relation.TRUCE)) {
                                continue;
                            }
                        }
                        victims.add((LivingEntity) ent);
                    }
                }
                de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(),player.getEyeLocation(), 0f,1f,0f,1f,75);

                p.getWorld().playSound(p.getLocation(), Sound.CREEPER_HISS, 2.0f, 0.75f);
                for (final LivingEntity victim : victims) {
                    if (victim instanceof Player) {
                        ((Player) victim).sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "(!) " + ChatColor.LIGHT_PURPLE + p.getName() + " has died, and was a Plague Carrier! RUN!");
                        ((Player) victim).getWorld().playSound(victim.getLocation(), Sound.CREEPER_HISS, 1.6f, 0.75f);
                    }
                }
                int plagueCarriers = radius / 2;
                while (plagueCarriers > 0) {
                    --plagueCarriers;
                    this.spawnPlagueCreeper(p.getLocation().add(0.0, 1.0, 0.0), level);
                }
                if (p.getHealth() > 0.0) {
                    p.damage(p.getHealth());
                }
            }
        }
    }

    private void spawnPlagueCreeper(final Location l, final int level) throws Exception {
        final Creeper c = (Creeper) l.getWorld().spawnEntity(l, EntityType.CREEPER);
        c.setMetadata("plagueCarrier", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) level));
        c.setCustomName(ChatColor.LIGHT_PURPLE + "Plague Carrier");
        c.setCanPickupItems(false);
        c.setMetadata("no_loot", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) true));
        c.setCustomNameVisible(true);
        c.setPowered(true);
        de.inventivegames.particle.ParticleEffect.CLOUD.sendToPlayers(Bukkit.getOnlinePlayers(), c.getLocation(), 0f, 0.5f, 0f, 0.5f, 20);

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
