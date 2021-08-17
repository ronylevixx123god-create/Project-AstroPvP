package org.arkhamnetwork.enchantments.enchantments.bow;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DimensionRift extends CustomEnchantment {
    public static HashMap<Integer, HashMap<Location, Material>> blockRevertTasks;

    static {
        DimensionRift.blockRevertTasks = new HashMap<Integer, HashMap<Location, Material>>();
    }

    public DimensionRift() {
        super("Dimension Rift", new Material[]{Material.BOW}, 3);
        this.max = 4;
        this.base = 17.0;
        this.interval = 4.0;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (Math.random() > level * 0.05) {
            return;
        }
        if (!(e.getDamager() instanceof Projectile)) {
            return;
        }
        if (!(playerHit instanceof Player)) {
            return;
        }
        final HashMap<Location, Material> blocks = new HashMap<Location, Material>();
        final Location enemyLoc = playerHit.getLocation().subtract(0.0, 1.0, 0.0).clone();
        if (playerHit.getWorld().getName().equals("world_koth") || player.getWorld().getName().startsWith("dungeon")) {
            return;
        }
        final FLocation fL = new FLocation(enemyLoc);
        if (Board.getFactionAt(fL).isNormal()) {
            return;
        }
        if (WorldGuardUtils.getRegionName(playerHit.getLocation()).equals("")) {
            return;
        }
        for (int x = (level >= 4) ? -1 : 0; x <= 1; ++x) {
            for (int z = (level >= 3) ? -1 : 0; z <= 1; ++z) {
                final Location l = enemyLoc.clone().add((double) x, 0.0, (double) z);
                final Material m = l.getBlock().getType();
                if ((m.isBlock() || m == Material.AIR) && !(l.getBlock().getState() instanceof Chest) && m != Material.SOUL_SAND && m != Material.WEB && m != Material.BEDROCK && m != Material.ENDER_PORTAL && m != Material.PORTAL) {
                    blocks.put(l.clone(), m);
                    l.getBlock().setType(Material.SOUL_SAND);
                    de.inventivegames.particle.ParticleEffect.DRIP_LAVA.sendToPlayers(Bukkit.getOnlinePlayers(), l.getBlock().getLocation(), 0f,1f,0f,0.75f,10);

                    if (Math.random() < level * 0.1) {
                        final Location lAbove = l.getBlock().getLocation().add(0.0, 1.0, 0.0);
                        final Material mAbove = lAbove.getBlock().getType();
                        if ((mAbove.isBlock() || mAbove == Material.AIR) && !(lAbove.getBlock().getState() instanceof Chest) && mAbove != Material.SOUL_SAND && mAbove != Material.WEB && mAbove != Material.BEDROCK && mAbove != Material.ENDER_PORTAL && mAbove != Material.PORTAL) {
                            blocks.put(lAbove.clone(), mAbove);
                            lAbove.getBlock().setType(Material.WEB);
                            de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), lAbove.clone(), 0f,0.5f,0f,0.7f,10);

                        }
                    }
                }
            }
        }
        final BukkitTask bt = Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                final HashSet<Player> playersToBoost = new HashSet<Player>();
                for (final Map.Entry<Location, Material> data : blocks.entrySet()) {
                    final Location l = data.getKey();
                    for (final Player p : l.getWorld().getPlayers()) {
                        if (!playersToBoost.contains(p) && p.getWorld().equals(l.getWorld()) && Math.abs(p.getLocation().getChunk().getX() - l.getChunk().getX()) <= 1 && Math.abs(p.getLocation().getChunk().getZ() - l.getChunk().getZ()) <= 1 && p.getLocation().distanceSquared(l) <= 4.0) {
                            playersToBoost.add(p);
                        }
                    }
                    data.getKey().getBlock().setType((Material) data.getValue());
                    for (final Player p : playersToBoost) {
                        p.removePotionEffect(PotionEffectType.JUMP);
                        p.setVelocity(new Vector(0.0, 0.5, 0.0));
                    }
                }
            }
        }, (long) (level * 15 + 40));
        DimensionRift.blockRevertTasks.put(bt.getTaskId(), (HashMap<Location, Material>) blocks.clone());
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                DimensionRift.blockRevertTasks.remove(bt.getTaskId());
            }
        }, (long) (level * 15 + 40 + 1));
    }

    @SneakyThrows
    public void applyProjectileEffect(final LivingEntity player, final int level, final ProjectileLaunchEvent e) {
        de.inventivegames.particle.ParticleEffect.WITCH_MAGIC.sendToPlayers(Bukkit.getOnlinePlayers(), e.getEntity().getLocation(), 0f,0.5f,0f,0.7f,16);

    }
}
