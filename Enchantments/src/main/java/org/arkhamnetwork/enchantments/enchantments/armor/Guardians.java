package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Guardians extends CustomEnchantment {
    public Guardians() {
        super("Guardians", GeneralUtil.armor, 2);
        this.max = 10;
        this.base = 20.0;
        this.interval = 15.0;
        Bukkit.getPluginManager().registerEvents((Listener) new GuardiansListener(), (Plugin) Enchantments.getInstance());
    }

    public static IronGolem spawnGuardianEntity(final Location spawnLocation, final Player summoner, final int level) {
        final IronGolem ig = (IronGolem) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.IRON_GOLEM);
        ig.setMetadata("guardianSummoner", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) summoner.getName()));
        ig.setMetadata("no_loot", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) true));
        ig.setCanPickupItems(false);
        ig.setCustomName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + summoner.getName() + "'s Guardian");
        ig.setCustomNameVisible(true);
        final double customHp = level * 15;
        ig.setMaxHealth(customHp);
        ig.setHealth(customHp);
        ig.getWorld().playSound(ig.getLocation(), Sound.IRONGOLEM_DEATH, 1.0f, 0.55f);
        ig.setPlayerCreated(false);
        ig.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
        if (level >= 4) {
            ig.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
        }
        if (level >= 6) {
            ig.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
        }
        if (level >= 8) {
            ig.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        }
        if (level == 10) {
            ig.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
        }
        final IronGolem golem = ig;
        final int target_task_id = new BukkitRunnable() {
            public void run() {
                if (!golem.isDead()) {
                    final Player ptarg = getClosestPlayer((Entity) golem);
                    if (ptarg != null) {
                        golem.setTarget((LivingEntity) ptarg);
                    }
                } else {
                    this.cancel();
                    if (golem.hasMetadata("targetTaskID")) {
                        golem.removeMetadata("targetTaskID", (Plugin) Enchantments.getInstance());
                    }
                }
            }
        }.runTaskTimer((Plugin) Enchantments.getInstance(), 20L, 20L).getTaskId();
        ig.setMetadata("targetTaskID", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) target_task_id));
        final IronGolem igf = ig;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                igf.damage(igf.getHealth());
                if (!igf.isDead()) {
                    igf.remove();
                }
            }
        }, 600L);
        return ig;
    }

    private static Player getClosestPlayer(final Entity boss) {
        if (!boss.hasMetadata("guardianSummoner")) {
            return null;
        }
        final String summoner = boss.getMetadata("guardianSummoner").get(0).asString();
        final Player pSummoner = Bukkit.getPlayer(summoner);
        if (pSummoner != null) {
            for (final Entity ent : pSummoner.getNearbyEntities(9.0, 9.0, 9.0)) {
                if (ent instanceof Player && !ent.hasMetadata("spectator") && !((Player) ent).getName().equals(summoner)) {
                    if (GeneralUtil.isAtleastTruce(pSummoner, (Player) ent)) {
                        continue;
                    }
                    return (Player) ent;
                }
            }
        }
        return null;
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) e).getDamager() instanceof Player) {
            if (player.hasMetadata("guardianEvent") && System.currentTimeMillis() < player.getMetadata("guardianEvent").get(0).asLong()) {
                return;
            }
            if (player.hasMetadata("inDungeonParkour")) {
                return;
            }
            final boolean KOTH_or_duel = player.getWorld().getName().equals("world_koth") || player.getWorld().getName().equals("world_duels") || player.getWorld().getName().equals("world_duels2");
            double chance = 0.005;
            if (chance > 0.005) {
                chance = 0.005;
            }
            if (KOTH_or_duel) {
                chance /= 2.0;
            }
            if (Math.random() < chance) {
                player.setMetadata("guardianEvent", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + 10000L)));
                int guardian_count = level / 10 + 1;
                if (KOTH_or_duel) {
                    guardian_count = 1;
                }
                final Player p = (Player) player;
                final Location spawnLoc = player.getLocation().add(0.0, 2.0, 0.0);
                try {
                    de.inventivegames.particle.ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), spawnLoc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.64f, 45);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                while (guardian_count > 0) {
                    --guardian_count;
                    if (spawnLoc.getChunk().getEntities().length < 100) {
                        final IronGolem ig = spawnGuardianEntity(spawnLoc, p, level);
                        ig.setTarget(attacker);
                    }
                }
            }
        }
    }
}
