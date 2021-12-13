package org.arkhamnetwork.enchantments.enchantments.armor;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.Relation;
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

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class Spirits extends CustomEnchantment {
    public static Map<Entity, Integer> spirits;
    public static Map<Integer, Integer> ticks;

    static {
        Spirits.spirits = new WeakHashMap<Entity, Integer>();
        Spirits.ticks = new HashMap<Integer, Integer>();
    }

    public Spirits() {
        super("Spirits", GeneralUtil.armor, 2);
        this.max = 10;
        this.base = 20.0;
        this.interval = 5.0;
        Bukkit.getPluginManager().registerEvents((Listener) new acenstoralListener(), (Plugin) Enchantments.getInstance());
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) Enchantments.getInstance(), () -> Spirits.spirits.forEach((ent, level) -> {
            if (!(!ent.isValid())) {
                int required = (int) ((20 + (10 - level) * 4) * 1.5);
                Integer currentTick = Spirits.ticks.computeIfAbsent(ent.getEntityId(), k -> 0);
                if (currentTick >= required) {
                    Spirits.ticks.put(ent.getEntityId(), 0);
                    Faction faction = Factions.i.get(((MetadataValue) ent.getMetadata("faction").get(0)).asInt() + "");
                    if (faction != null && faction.getFPlayers().size() > 0) {
                        int healed = 0;
                        int maxHeal = ((level > 6) ? 2 : 1);
                        int radius = 8 + level;
                        int toHeal = ((level <= 5) ? 2 : ((level <= 8) ? 4 : 5));
                        int toHeal2 = toHeal / 2;
                        try {
                            de.inventivegames.particle.ParticleEffect.HEART.sendToPlayers(Bukkit.getOnlinePlayers(), ent.getLocation().clone().add(0.0, 1.0, 0.0), 0.5f, 0.5f, 0.5f, 0.01f, 20);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        for (Entity nearby : ent.getNearbyEntities((double) radius, (double) radius, (double) radius)) {
                            if (healed >= maxHeal) {
                                break;
                            } else if (nearby instanceof Player) {
                                Player player = (Player) nearby;
                                if (player.hasMetadata("heal_cd")) {
                                    long cd = player.getMetadata("heal_cd").get(0).asLong();
                                    if (cd > System.currentTimeMillis()) {
                                        continue;
                                    }
                                }
                                FPlayer fpl = FPlayers.i.get(player.getUniqueId().toString());
                                if (fpl != null && fpl.hasFaction() && fpl.getRelationTo((RelationParticipator) faction) == Relation.ALLY) {
                                    if (player.getHealth() < player.getMaxHealth()) {
                                        if (player.isDead()) {
                                            continue;
                                        } else {
                                            ++healed;
                                            //GeneralUtil.healPlayer((LivingEntity) player, toHeal2);
                                            player.setMetadata("heal_cd", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + required / 20.0 * 1000.0)));
                                            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 0.3f, 1.4f);
                                            try {
                                                de.inventivegames.particle.ParticleEffect.HEART.sendToPlayers(Bukkit.getOnlinePlayers(),player.getLocation().clone().add(0.0, 1.0, 0.0), 0.75f, 0.75f, 0.75f, 0.01f, 15);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Map<Integer, Integer> ticks = Spirits.ticks;
                    ent.getEntityId();
                    Integer currentTick2 = currentTick + 1;
                }
            }
        }), 20L, 1L);
    }

    @SneakyThrows
    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (e instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) e).getDamager() instanceof Player) {
            if (player.hasMetadata("acenstoralEvent") && System.currentTimeMillis() < player.getMetadata("acenstoralEvent").get(0).asLong()) {
                return;
            }
            double chance = 0.01 + level * 0.05;
            if (chance > 0.02) {
                chance = 0.02;
            }
            if (Math.random() < chance) {
                if (Exterminator.isExterminated((Player) player)) return;
                player.setMetadata("acenstoralEvent", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + 10000L)));
                int acenstoral_count = level / 10 + 1;
                final Player p = (Player) player;
                final Location spawnLoc = player.getLocation().add(0.0, 1.0, 0.0);
                try {
                    de.inventivegames.particle.ParticleEffect.SPELL.sendToPlayers(Bukkit.getOnlinePlayers(), spawnLoc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.64f, 45);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                try {
                    de.inventivegames.particle.ParticleEffect.FLAME.sendToPlayers(Bukkit.getOnlinePlayers(), spawnLoc, (float) Math.random(), (float) Math.random(), (float) Math.random(), 0.64f, 45);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                final FPlayer fp = FPlayers.i.get(player.getUniqueId().toString());
                final String facName = fp.hasFaction() ? fp.getFaction().getId() : "";
                while (acenstoral_count > 0) {
                    --acenstoral_count;
                    if (spawnLoc.getChunk().getEntities().length < 50) {
                        final Blaze blaze = (Blaze) player.getWorld().spawnEntity(spawnLoc, EntityType.BLAZE);
                        Spirits.spirits.put((Entity) blaze, level);
                        blaze.setMetadata("faction", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) facName));
                        blaze.setMetadata("acenstoralSummoner", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) p.getName()));
                        blaze.setMetadata("no_loot", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) true));
                        blaze.setCanPickupItems(false);
                        blaze.setCustomName(ChatColor.RED.toString() + ChatColor.BOLD.toString() + p.getName() + "'s Spirit");
                        blaze.setCustomNameVisible(true);
                        final double customHp = 50 + level * 10;
                        blaze.setMaxHealth(customHp);
                        blaze.setHealth(customHp);
                        blaze.getWorld().playSound(blaze.getLocation(), Sound.IRONGOLEM_DEATH, 1.0f, 0.55f);
                        blaze.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
                        if (level >= 4) {
                            blaze.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
                        }
                        if (level >= 6) {
                            blaze.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));
                        }
                        if (level >= 8) {
                            blaze.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                        }
                        if (level == 10) {
                            blaze.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0));
                        }
                        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), () -> {
                            blaze.damage(blaze.getHealth() * 10.0);
                            if (!blaze.isDead()) {
                                blaze.remove();
                            }
                        }, 200L);
                    }
                }
            }
        }
    }
}