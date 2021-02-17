/*     */
package me.astropvp.astropvpskins.listeners;
/*     */
/*     */

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EssentialsUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
import org.arkhamnetwork.enchantments.enchantments.axes.Devour;
import org.arkhamnetwork.enchantments.enchantments.axes.Hex;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicCleave;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicDodge;
import org.arkhamnetwork.enchantments.mastery.MasteryDemonicGateway;
import org.arkhamnetwork.enchantments.mastery.MasteryMarkOfTheBeast;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */

public class ItemSkinProccessEventsListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamaged(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            boolean shouldContinue = true;
            Player damager = (Player) e.getDamager();
            if (damager.getWorld().getName().contains("dungeon")) {
                DungeonPlayer data = CosmicDungeonsPlugin.get().getApi().getPlayerDungeonData(damager);
            }
            if (shouldContinue) {
                List<ItemStack> contents = Lists.newArrayList();
                ItemStack[] armorContents;
                for (int length = (armorContents = damager.getInventory().getArmorContents()).length, i = 0; i < length; ++i) {
                    ItemStack item = armorContents[i];
                    contents.add(item);
                }
                contents.add(damager.getItemInHand());
                for (ItemStack x : contents) {
                    SkinType type = AstroItemSkinsAPI.getSkinTypeFromNBT(x);
                    if (type == null) {
                        continue;
                    }
                    if (type == SkinType.FLAMING_HALO) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.04));
                    } else if (type == SkinType.EASTER_SWORD && !(e.getEntity() instanceof Player)) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.15));
                        if (!(e.getEntity() instanceof LivingEntity)) {
                            continue;
                        }
                        new Devour().applyEffect(damager, (LivingEntity) e.getEntity(), 5, e);
                    } else if (type == SkinType.CORRUPT_AMULET_DESTRUCTION) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.075));
                    } else if (type == SkinType.VIRUS_SWORD && e.getEntity() instanceof LivingEntity) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.035));
                        new HeroicCleave().applyEffect(damager, (LivingEntity) e.getEntity(), 5, e);
                    } else if (type == SkinType.EASTER_AMULET) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.1));
                    } else if (type == SkinType.SPRING_2020_AXE) {
                        if (!(e.getEntity() instanceof Player)) {
                            continue;
                        }
                        Player damaged = (Player) e.getEntity();
                        if (damaged.getItemInHand() == null || !damaged.getItemInHand().getType().name().endsWith("_SWORD")) {
                            continue;
                        }
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.05));
                    } else if (type == SkinType.TRIDENT_SWORD && Math.random() <=0.08) {

                        new MasteryMarkOfTheBeast().applyDefenseEffect(damager, (LivingEntity) e.getEntity(), 3, e);
                        new Blessed().applyEffect(damager, (LivingEntity) e.getEntity(), 4, e);
                        //new Hex().applyEffect(damager, (LivingEntity) e.getEntity(), 5, e);
                    } else if (type == SkinType.BOOTS_RETALIATION) {
                        if (e.getEntity() instanceof Player) {
                            continue;
                        }
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.15);
                    } else if (type == SkinType.CUPID_HELMET) {
                        if (!(e.getEntity() instanceof Player)) {
                            continue;
                        }
                        Player damaged = (Player) e.getEntity();
                        if (damaged.getMaxHealth() / 2.0 <= damaged.getHealth() || Math.random() >= 0.5) {
                            continue;
                        }
                        double health = damaged.getHealth() * 0.1;
                        //damaged.setHealth(damaged.getHealth() - health);
                        //damager.setHealth(Math.min(damaged.getMaxHealth(), damager.getHealth() + health));
                    } else if (type == SkinType.SKULL_SCYTHE) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.025);
                    } else if (type == SkinType.DEEPSPACE_BACKPACK) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.003);
                    } else if (type == SkinType.SANTA_HAT) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.005);
                    } else if (type == SkinType.JOLLY_CANDY_SWORD) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.075);
                    } else if (type == SkinType.LEVIATHAN_TROPHY) {

                        if (ThreadLocalRandom.current().nextInt(101) <= 3) {
                            List<Player> players = damager.getNearbyEntities(7, 12, 7)
                                    .stream()
                                    .filter(entry -> entry != damager)
                                    .filter(entry -> entry instanceof Player)
                                    .filter(entry -> !(GeneralUtil.isAtleastTruce((Player) entry, damager)))
                                    .filter(it -> !it.hasMetadata("noSoulProcs"))
                                    .map(entry -> (Player) entry)
                                    .collect(Collectors.toList());
                            for (Player p : players) {
                                p.sendMessage(CC.translate("&c&l* " + damager.getName() + "'s LEVIATHAN TROPHY &7(12s Soul Enchant Silence) &c&l*"));
                                p.setMetadata("noSoulProcs", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), true));
                                Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> p.removeMetadata("noSoulProcs", AstroItemSkinsPlugin.getInstance()), 12 * 20L);
                            }
                            damager.sendMessage(CC.translate("&3&l* LEVIATHAN TROPHY &7(7x7 Soul Enchant Silence, " + players.size() + " enem" + (players.size() > 1 ? "ies" : "y") + " affected) &3&l*"));
                        }


                        return;
                    } else {
                        if (type != SkinType.MOON_BOOTS) {
                            continue;
                        }
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.2);
                    }
                }
            }
        }

        if (e.getEntity() instanceof Player) {
            Player entity = (Player) e.getEntity();
            boolean shouldContinue2 = true;
            if (entity.getWorld().getName().contains("dungeon")) {
                DungeonPlayer data = CosmicDungeonsPlugin.get().getApi().getPlayerDungeonData(entity);

            }
            if (shouldContinue2) {
                List<ItemStack> contents = Lists.newArrayList();
                ItemStack[] armorContents2;
                for (int length2 = (armorContents2 = entity.getInventory().getArmorContents()).length, j = 0; j < length2; ++j) {
                    ItemStack item = armorContents2[j];
                    contents.add(item);
                }
                contents.add(entity.getItemInHand());
                boolean damagerIsProjectile = e.getDamager() instanceof Projectile;
                boolean damagerIsPlayer = e.getDamager() instanceof Player;
                boolean damagerIsArrow = e.getDamager() instanceof Arrow;
                ItemStack[] armorContents3;
                for (int length3 = (armorContents3 = entity.getInventory().getArmorContents()).length, k = 0; k < length3; ++k) {
                    ItemStack x2 = armorContents3[k];
                    SkinType type2 = AstroItemSkinsAPI.getSkinTypeFromNBT(x2);
                    if (type2 != null) {
                        if (type2 == SkinType.SEXY_BELT && damagerIsProjectile) {
                            e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) - e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.25));
                        } else if (type2 == SkinType.EASTER_PICKAXE) {
                            e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) - e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.1));
                        } else if (type2 == SkinType.SPRING_2020_AMULET && damagerIsArrow) {
                            e.setCancelled(true);
                        } else if (type2 == SkinType.VIRUS_HELMET_N95) {
                            if (Math.random() < 0.02) {
                                entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1, 500));
                            }
                        } else if (type2 == SkinType.SPRING_2020_AXE && damagerIsPlayer) {
                            Player damager2 = (Player) e.getDamager();
                            if (damager2.getItemInHand() != null && damager2.getItemInHand().getType().name().endsWith("_SWORD")) {
                                e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) - e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.05));
                            }
                        } else if (type2 == SkinType.EASTER_HELMET) {
                            if (Math.random() < 0.01) {
                                new HeroicDodge().applyDefenseEffect(entity, (LivingEntity) e.getDamager(), 6, e);
                            }
                        } else if (type2 == SkinType.CAMOUFLAGE_BOOTS) {
                            new MasteryDemonicGateway().applyDefenseEffect(entity, (LivingEntity) e.getDamager(), 5, e);
                        } else if (type2 == SkinType.SANTA_HAT) {
                            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.95);
                        } else if (type2 == SkinType.BOOTS_RETALIATION) {
                            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.1);
                        } else if (type2 == SkinType.CANDY_COLLECTOR_HELMET) {
                            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.8);
                            if (Math.random() < 0.01) {
//                                InventoryWitcherPet.(entity, 12, 6);
                            }
                        } else if (type2 == SkinType.KOALA) {
                            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                                entity.setFireTicks(0);
                                e.setCancelled(true);
                            }
                        } else if (type2 == SkinType.CUPID_BOOTS && Math.random() < 0.015) {
                            e.getDamager().setVelocity(new Vector(Math.random(), Math.random(), Math.random()));
                        } else if (type2 == SkinType.VIRUS_BOOTS && Math.random() < 0.02) {
                            getHazmattablePlayersNearby(entity, 12, 12, 12).forEach(
                                    it -> effectPlayerWithHazmatBoots(entity, it));
                        }else if (type2 == SkinType.LOST_AMULET) {
                            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.95);
                        }
                    }
                }
            }
        }
    }

    protected List<Player> getHazmattablePlayersNearby(Player medium, int x, int y, int z) {

        return medium.getNearbyEntities(x, y, z)
                .stream()
                .filter(Objects::nonNull)
                .filter(entry -> WorldGuardUtils.isPvPEnabled(entry.getLocation()))
                .filter(entry -> entry instanceof Player)
                .filter(entry -> !(GeneralUtil.isAtleastTruce(medium, (Player) entry)))
                .filter(entry -> !(EssentialsUtils.isVanished((Player) entry)))
                .filter(entry -> !(EssentialsUtils.isInGodMode((Player) entry)))
                .filter(entry -> ((Player) entry).getGameMode() != GameMode.CREATIVE)
                .filter(entry -> entry != medium)
                .filter(entry -> !entry.hasMetadata("hazmatBoots"))
                .map(e -> (Player) e)
                .collect(Collectors.toList());
    }

    private void effectPlayerWithHazmatBoots(Player hazmatter, Player p) {
        p.sendMessage(CC.translate("&2&l** " + hazmatter.getName() +
                (hazmatter.getName().endsWith("s") ? "' " : "'s ") + "HAZMAT BOOTS [&c12s&2&l] **"));
        hazmatter.sendMessage(CC.translate("&2&l** " + hazmatter.getName() +
                (hazmatter.getName().endsWith("s") ? "' " : "'s ") + "HAZMAT BOOTS [&c12s&2&l] **"));
        p.sendMessage(CC.translate("&c( 12s Pet Silence )"));
        p.setMetadata("hazmatBoots", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), System.currentTimeMillis()));
        p.setMetadata("hazmatTime", new FixedMetadataValue(AstroItemSkinsPlugin.getInstance(), TimeUnit.SECONDS.toMillis(12)));
        Bukkit.getScheduler().runTaskLaterAsynchronously(AstroItemSkinsPlugin.getInstance(), () -> removeHazmatBootsEffect(hazmatter, p), 12 * 20);
    }

    private void removeHazmatBootsEffect(Player hazmatter, Player p) {
        p.sendMessage(CC.translate("&a&l(!) &2&lPet Silence &afrom " + hazmatter.getName() +
                (hazmatter.getName().endsWith("s") ? "' " : "'s ") + "Hazmat Boots &a&nEXPIRED!"));
        p.removeMetadata("hazmatBoots", AstroItemSkinsPlugin.getInstance());
        p.removeMetadata("hazmatTime", AstroItemSkinsPlugin.getInstance());
    }

}
