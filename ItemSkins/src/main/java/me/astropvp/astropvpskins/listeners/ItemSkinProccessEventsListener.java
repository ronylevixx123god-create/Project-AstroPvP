/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicDungeons.CosmicDungeonsPlugin
 *  com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.EssentialsUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.enchantments.Enchantments
 *  org.arkhamnetwork.enchantments.enchantments.axes.Blessed
 *  org.arkhamnetwork.enchantments.enchantments.axes.Devour
 *  org.arkhamnetwork.enchantments.enchantments.heroic.HeroicCleave
 *  org.arkhamnetwork.enchantments.enchantments.heroic.HeroicDodge
 *  org.arkhamnetwork.enchantments.mastery.MasteryDemonicGateway
 *  org.arkhamnetwork.enchantments.mastery.MasteryMarkOfTheBeast
 *  org.arkhamnetwork.enchantments.util.GeneralUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.entity.Arrow
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.entity.Projectile
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageCause
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 *  org.bukkit.util.Vector
 */
package me.astropvp.astropvpskins.listeners;

import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.EssentialsUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.AstroItemSkinsPlugin;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
import org.arkhamnetwork.enchantments.enchantments.axes.Devour;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicCleave;
import org.arkhamnetwork.enchantments.enchantments.heroic.HeroicDodge;
import org.arkhamnetwork.enchantments.mastery.MasteryDemonicGateway;
import org.arkhamnetwork.enchantments.mastery.MasteryMarkOfTheBeast;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class ItemSkinProccessEventsListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onDamaged(EntityDamageByEntityEvent e) {
        ArrayList contents;
        if (e.getDamager() instanceof Player) {
            boolean shouldContinue = true;
            Player damager = (Player)e.getDamager();
            if (damager.getWorld().getName().contains("dungeon")) {
                DungeonPlayer dungeonPlayer = CosmicDungeonsPlugin.get().getApi().getPlayerDungeonData(damager);
            }
            if (shouldContinue) {
                contents = Lists.newArrayList();
                for (ItemStack item : damager.getInventory().getArmorContents()) {
                    contents.add(item);
                }
                contents.add(damager.getItemInHand());
                for (ItemStack x : contents) {
                    Player damaged;
                    SkinType type = AstroItemSkinsAPI.getSkinTypeFromNBT(x);
                    if (type == null) continue;
                    if (type == SkinType.FLAMING_HALO) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.04));
                        continue;
                    }
                    if (type == SkinType.EASTER_SWORD && !(e.getEntity() instanceof Player)) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.15));
                        if (!(e.getEntity() instanceof LivingEntity)) continue;
                        new Devour().applyEffect((LivingEntity)damager, (LivingEntity)e.getEntity(), 5, e);
                        continue;
                    }
                    if (type == SkinType.CORRUPT_AMULET_DESTRUCTION) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.075));
                        continue;
                    }
                    if (type == SkinType.VIRUS_SWORD && e.getEntity() instanceof LivingEntity) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.035));
                        new HeroicCleave().applyEffect((LivingEntity)damager, (LivingEntity)e.getEntity(), 5, e);
                        continue;
                    }
                    if (type == SkinType.EASTER_AMULET) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.1));
                        continue;
                    }
                    if (type == SkinType.SPRING_2020_AXE) {
                        if (!(e.getEntity() instanceof Player) || (damaged = (Player)e.getEntity()).getItemInHand() == null || !damaged.getItemInHand().getType().name().endsWith("_SWORD")) continue;
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) + e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.1));
                        continue;
                    }
                    if (type == SkinType.TRIDENT_SWORD) {
                        new MasteryMarkOfTheBeast().applyDefenseEffectIgnoreAntiMkit((LivingEntity)damager, (LivingEntity)e.getEntity(), 3, (EntityDamageEvent)e);
                        new Blessed().applyEffect((LivingEntity)damager, (LivingEntity)e.getEntity(), 4, e);
                        Enchantments.getHex().applyEffect((LivingEntity)damager, (LivingEntity)e.getEntity(), 5, e);
                        continue;
                    }
                    if (type == SkinType.BOOTS_RETALIATION) {
                        if (e.getEntity() instanceof Player) continue;
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.15);
                        continue;
                    }
                    if (type == SkinType.CUPID_HELMET) {
                        if (!(e.getEntity() instanceof Player) || (damaged = (Player)e.getEntity()).getMaxHealth() / 2.0 <= damaged.getHealth() || Math.random() >= 0.5) continue;
                        double health = damaged.getHealth() * 0.1;
                        damaged.setHealth(damaged.getHealth() - health);
                        damager.setHealth(Math.min(damaged.getMaxHealth(), damager.getHealth() + health));
                        continue;
                    }
                    if (type == SkinType.SKULL_SCYTHE) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.025);
                        continue;
                    }
                    if (type == SkinType.DEEPSPACE_BACKPACK) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.03);
                        continue;
                    }
                    if (type == SkinType.SANTA_HAT) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.05);
                        continue;
                    }
                    if (type == SkinType.JOLLY_CANDY_SWORD) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.075);
                        continue;
                    }
                    if (type == SkinType.LEVIATHAN_TROPHY) {
                        if (ThreadLocalRandom.current().nextInt(101) <= 3) {
                            List players = damager.getNearbyEntities(7.0, 12.0, 7.0).stream().filter(entry -> entry != damager).filter(entry -> entry instanceof Player).filter(entry -> !GeneralUtil.isAtleastTruce((Player)((Player)entry), (Player)damager)).filter(it -> !it.hasMetadata("noSoulProcs")).map(entry -> (Player)entry).collect(Collectors.toList());
                            for (Player p : players) {
                                p.sendMessage(CC.translate((String)("&c&l* " + damager.getName() + "'s LEVIATHAN TROPHY &7(12s Soul Enchant Silence) &c&l*")));
                                p.setMetadata("noSoulProcs", (MetadataValue)new FixedMetadataValue((Plugin)AstroItemSkinsPlugin.getInstance(), (Object)true));
                                Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)AstroItemSkinsPlugin.getInstance(), () -> p.removeMetadata("noSoulProcs", (Plugin)AstroItemSkinsPlugin.getInstance()), 240L);
                            }
                            damager.sendMessage(CC.translate((String)("&3&l* LEVIATHAN TROPHY &7(7x7 Soul Enchant Silence, " + players.size() + " enem" + (players.size() > 1 ? "ies" : "y") + " affected) &3&l*")));
                        }
                        return;
                    }
                    if (type != SkinType.MOON_BOOTS) continue;
                    e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.2);
                }
            }
        }
        if (e.getEntity() instanceof Player) {
            Player entity = (Player)e.getEntity();
            boolean shouldContinue2 = true;
            if (entity.getWorld().getName().contains("dungeon")) {
                contents = CosmicDungeonsPlugin.get().getApi().getPlayerDungeonData(entity);
            }
            if (shouldContinue2) {
                contents = Lists.newArrayList();
                for (ItemStack item : entity.getInventory().getArmorContents()) {
                    contents.add(item);
                }
                contents.add(entity.getItemInHand());
                boolean damagerIsProjectile = e.getDamager() instanceof Projectile;
                boolean damagerIsPlayer = e.getDamager() instanceof Player;
                boolean damagerIsArrow = e.getDamager() instanceof Arrow;
                for (ItemStack x2 : entity.getInventory().getArmorContents()) {
                    SkinType type2 = AstroItemSkinsAPI.getSkinTypeFromNBT(x2);
                    if (type2 == null) continue;
                    if (type2 == SkinType.SEXY_BELT && damagerIsProjectile) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) - e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.25));
                        continue;
                    }
                    if (type2 == SkinType.EASTER_PICKAXE) {
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) - e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.1));
                        continue;
                    }
                    if (type2 == SkinType.SPRING_2020_AMULET && damagerIsArrow) {
                        e.setCancelled(true);
                        continue;
                    }
                    if (type2 == SkinType.VIRUS_HELMET_N95) {
                        if (!(Math.random() < 0.02)) continue;
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1, 500));
                        continue;
                    }
                    if (type2 == SkinType.SPRING_2020_AXE && damagerIsPlayer) {
                        Player damager2 = (Player)e.getDamager();
                        if (damager2.getItemInHand() == null || !damager2.getItemInHand().getType().name().endsWith("_SWORD")) continue;
                        e.setDamage(Math.max(0.0, e.getDamage(EntityDamageEvent.DamageModifier.BASE) - e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.05));
                        continue;
                    }
                    if (type2 == SkinType.EASTER_HELMET) {
                        if (!(Math.random() < 0.01)) continue;
                        new HeroicDodge().applyDefenseEffect((LivingEntity)entity, (LivingEntity)e.getDamager(), 6, (EntityDamageEvent)e);
                        continue;
                    }
                    if (type2 == SkinType.CAMOUFLAGE_BOOTS) {
                        new MasteryDemonicGateway().applyDefenseEffect((LivingEntity)entity, (LivingEntity)e.getDamager(), 5, (EntityDamageEvent)e);
                        continue;
                    }
                    if (type2 == SkinType.SANTA_HAT) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.95);
                        continue;
                    }
                    if (type2 == SkinType.BOOTS_RETALIATION) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 1.1);
                        continue;
                    }
                    if (type2 == SkinType.CANDY_COLLECTOR_HELMET) {
                        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.8);
                        if (!(Math.random() < 0.01)) continue;
                        continue;
                    }
                    if (type2 == SkinType.KOALA) {
                        if (e.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION && e.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION && e.getCause() != EntityDamageEvent.DamageCause.FIRE && e.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK && e.getCause() != EntityDamageEvent.DamageCause.LAVA) continue;
                        entity.setFireTicks(0);
                        e.setCancelled(true);
                        continue;
                    }
                    if (type2 == SkinType.CUPID_BOOTS && Math.random() < 0.015) {
                        e.getDamager().setVelocity(new Vector(Math.random(), Math.random(), Math.random()));
                        continue;
                    }
                    if (type2 == SkinType.VIRUS_BOOTS && Math.random() < 0.02) {
                        this.getHazmattablePlayersNearby(entity, 12, 12, 12).forEach(it -> this.effectPlayerWithHazmatBoots(entity, (Player)it));
                        continue;
                    }
                    if (type2 != SkinType.LOST_AMULET) continue;
                    e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.95);
                }
            }
        }
    }

    protected List<Player> getHazmattablePlayersNearby(Player medium, int x, int y, int z) {
        return medium.getNearbyEntities((double)x, (double)y, (double)z).stream().filter(Objects::nonNull).filter(entry -> WorldGuardUtils.isPvPEnabled((Location)entry.getLocation())).filter(entry -> entry instanceof Player).filter(entry -> !GeneralUtil.isAtleastTruce((Player)medium, (Player)((Player)entry))).filter(entry -> !EssentialsUtils.isVanished((Player)((Player)entry))).filter(entry -> !EssentialsUtils.isInGodMode((Player)((Player)entry))).filter(entry -> ((Player)entry).getGameMode() != GameMode.CREATIVE).filter(entry -> entry != medium).filter(entry -> !entry.hasMetadata("hazmatBoots")).map(e -> (Player)e).collect(Collectors.toList());
    }

    private void effectPlayerWithHazmatBoots(Player hazmatter, Player p) {
        p.sendMessage(CC.translate((String)("&2&l** " + hazmatter.getName() + (hazmatter.getName().endsWith("s") ? "' " : "'s ") + "HAZMAT BOOTS [&c12s&2&l] **")));
        hazmatter.sendMessage(CC.translate((String)("&2&l** " + hazmatter.getName() + (hazmatter.getName().endsWith("s") ? "' " : "'s ") + "HAZMAT BOOTS [&c12s&2&l] **")));
        p.sendMessage(CC.translate((String)"&c( 12s Pet Silence )"));
        p.setMetadata("hazmatBoots", (MetadataValue)new FixedMetadataValue((Plugin)AstroItemSkinsPlugin.getInstance(), (Object)System.currentTimeMillis()));
        p.setMetadata("hazmatTime", (MetadataValue)new FixedMetadataValue((Plugin)AstroItemSkinsPlugin.getInstance(), (Object)TimeUnit.SECONDS.toMillis(12L)));
        Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)AstroItemSkinsPlugin.getInstance(), () -> this.removeHazmatBootsEffect(hazmatter, p), 240L);
    }

    private void removeHazmatBootsEffect(Player hazmatter, Player p) {
        p.sendMessage(CC.translate((String)("&a&l(!) &2&lPet Silence &afrom " + hazmatter.getName() + (hazmatter.getName().endsWith("s") ? "' " : "'s ") + "Hazmat Boots &a&nEXPIRED!")));
        p.removeMetadata("hazmatBoots", (Plugin)AstroItemSkinsPlugin.getInstance());
        p.removeMetadata("hazmatTime", (Plugin)AstroItemSkinsPlugin.getInstance());
    }
}

