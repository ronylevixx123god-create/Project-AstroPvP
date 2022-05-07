/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.CC
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.MaskUtils
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.ParticleEffect
 *  com.cosmicpvp.cosmicutils.net.astropvp.armorsets.utils.UpgradeUtils
 *  com.earth2me.essentials.Essentials
 *  com.rit.sucy.CustomEnchantment
 *  me.ifamasssxd.factionupgrades.FactionUpgradeAPI
 *  me.ifamasssxd.factionupgrades.net.astropvp.armorsets.struct.FactionUpgrade
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.*;
import com.earth2me.essentials.Essentials;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.skins.axes.BoostedChainsaw;
import me.astropvp.astropvpskins.objects.skins.boots.BarrysBoltsSkin;
import me.astropvp.astropvpskins.tasks.UndertowFieldTask;
import me.ifamasssxd.cosmicmasks.MaskType;
import net.astropvp.core.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.mastery.MasteryFeignDeath;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.github.paperspigot.Title;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Phoenix
        extends CustomEnchantment {
    Essentials ess;
    protected static boolean factionUpgrades;
    protected static HashMap<String, Long> last_phoenix;
    public static Map<UUID, Integer> phoenixEquipped;

    static {
        last_phoenix = new HashMap();
        phoenixEquipped = new HashMap<UUID, Integer>();
    }

    public Phoenix() {
        super("Phoenix", GeneralUtil.boots, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
        factionUpgrades = Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades");
        this.ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        Bukkit.getPluginManager().registerEvents(new PhoenixListener(this), Enchantments.getInstance());
        Bukkit.getScheduler().runTaskTimer(Enchantments.getEssentials(), (Runnable) new reducePhoenixCount(), 12000L, 12000L);
    }

    public static boolean hasPhoenixEquipped(Player player) {
        return phoenixEquipped.containsKey(player.getUniqueId());
    }

    public void applyEquipEffect(Player player, int enchantLevel) {
        phoenixEquipped.put(player.getUniqueId(), enchantLevel);
    }

    public void applyUnequipEffect(Player player, int level, ItemStack item) {
        phoenixEquipped.remove(player.getUniqueId());
    }

    public void applyUnequipEffect(Player player, int enchantLevel) {
        this.applyUnequipEffect(player, enchantLevel, null);
    }


    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player instanceof Player) {
            double dmg = e.getFinalDamage();
            if (Phoenix.procPhoenix((Player) player, attacker, level, dmg)) {
                e.setDamage(0.0);
            }
        }
    }

    public static boolean procPhoenix(Player player, LivingEntity damager, double dmg) {
        Integer level = phoenixEquipped.get(player.getUniqueId());
        return level != null && Phoenix.procPhoenix(player, damager, level, dmg);
    }

    public static boolean procPhoenix(Player player, LivingEntity damager, int level, double dmg) {
        if (player.getHealth() <= 0.0) {
            return false;
        }
        if (player.getHealth() - dmg <= 0.0) {
            boolean canUse = canUsePhoenix(player, level);
            if (!canUse) {
                if (120L - (System.currentTimeMillis() - last_phoenix.get(player.getUniqueId().toString())) / 1000L != 0L)
                    player.sendMessage(CC.translate("&c&l** PHOENIX ON COOLDOWN ** &7[" + TimeUtils.formatSeconds(120L - (System.currentTimeMillis() - last_phoenix.get(player.getUniqueId().toString())) / 1000L) + "]"));
            }


            if (player.hasMetadata("noPhoenixProcs")) {
                if (!AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)|| AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_MUTATED_HELMET)) {
                    player.sendMessage(CC.translate("&c&l* PHOENIX BLOCKED [&7The Crumble&c&l] *"));
                    return false;
                }
            }
            if (damager instanceof Player && Math.random() <= 0.5 && MaskType.hasEquipped((Player) damager, "DEATH_KNIGHT")) {
                if (!AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)|| AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_MUTATED_HELMET)) {

                    player.sendMessage(CC.RedB + "* DEATH KNIGHT PHOENIX BLOCKED [" + CC.Gray + damager.getName() + CC.RedB + "] *");
                    damager.sendMessage(CC.RedB + "* DEATH KNIGHT MASK [" + CC.Gray + player.getName() + "'s Phoenix Blocked" + CC.RedB + "] *");
                    last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                    Bukkit.getLogger().info("Phoenix blocked from proccing for " + player.getName() + " from damager " + damager.getName());
                    return false;
                }
            }

            if (((BoostedChainsaw) SkinType.BACK_TO_SCHOOL_2021_AXE.getItemSkin()).getChainSawedPlayers().contains(player.getName())) {
                if (!AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)|| AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_MUTATED_HELMET)) {

                    player.sendMessage(CC.translate("&c&l* PHOENIX BLOCKED [&7Chainsaw Silence Ability&c&l] *"));
                    return false;
                }
            }

            if (damager instanceof Player && MaskType.hasEquipped((Player) damager, "THANOS")) {
                if (!AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)|| AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_MUTATED_HELMET)) {

                    player.sendMessage(CC.RedB + "* THANOS MASK PHOENIX BLOCKED [" + CC.Gray + damager.getName() + CC.RedB + "] *");
                    damager.sendMessage(CC.RedB + "* THANOS MASK [" + CC.Gray + player.getName() + "'s Phoenix Blocked" + CC.RedB + "] *");
                    last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                    Bukkit.getLogger().info("Phoenix blocked from proccing for " + player.getName() + " from damager " + damager.getName());
                    return false;
                }
            }
            if (damager instanceof Player && damager.hasMetadata("banshee_scrolls")) {
                if (!AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)|| AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_MUTATED_HELMET)) {

                    player.sendMessage(CC.translate("&c&l* PHOENIX BLOCKED [&7BANSHEE RKO&c&l] *"));
                    damager.sendMessage(CC.RedB + "* BANSHEE RKO [" + CC.Gray + player.getName() + "'s Phoenix Blocked" + CC.RedB + "] *");
                    last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                    Bukkit.getLogger().info("Phoenix blocked from proccing for " + player.getName() + " from damager " + damager.getName());
                    return false;
                }
            }
            if (player.hasMetadata("witchered")) {
                if (!AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY) || AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_MUTATED_HELMET)) {
                    player.sendMessage(CC.translate("&c&l* PHOENIX BLOCKED [&7Witcher Ability&c&l] *"));
                    last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                    return false;
                }
            }

            if (UndertowFieldTask.affected.contains(player)) {
                if (!AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)|| AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_MUTATED_HELMET)) {

                    player.sendMessage(CC.translate("&4&l* PHOENIX BLOCKED &7Undertow Field&4&l *"));
                    return false;
                }
            }

            if (MaskType.hasEquipped((Player) damager, "CRANKED") && Math.random() < 0.65) {
                if (!AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)|| AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_MUTATED_HELMET)) {

                    damager.sendMessage(CC.translate("&4&l* CRANKED MASK BLOCKED PHOENIX [&c" + player.getName() + "&4&l] *"));
                    player.sendMessage(CC.translate("&4&l* PHOENIX BLOCKED [&c" + damager.getName() + " Cranked Mask&4&l] *"));
                    last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                    return false;
                }
            }

            if (((BarrysBoltsSkin) SkinType.JULY_2021_BOOTS.getItemSkin()).getCurrentSilencedPlayers().contains(player)) {
                if (!AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)|| AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.EASTER_2022_MUTATED_HELMET)) {

                    player.sendMessage(CC.translate("&c&l** &7Enemy Garry's Bolt &c&lBLOCKED PHOENIX **"));
                    last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                    return false;
                }
            }
            int soulCost = Phoenix.getPhoenixProcSoulCost(player);
            if (KillTrackerAPI.hasSoulAmount(player, soulCost) && Phoenix.canUsePhoenix(player, level)) {
                KillTrackerAPI.removeSoulsFromGems(player, soulCost);
                last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                player.setMetadata("phoenixProcs", new FixedMetadataValue(Enchantments.getInstance(), player.hasMetadata("phoenixProcs") ? player.getMetadata("phoenixProcs").get(0).asInt() + 1 : 1));
                GeneralUtil.setPlayerHealth(player, player.getMaxHealth());
                player.sendMessage("");
                player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "*** " + ChatColor.UNDERLINE + "PHOENIX SOUL" + ChatColor.GOLD + ChatColor.BOLD + " ***");
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "- " + soulCost + " Soul Gems");
                player.sendMessage(ChatColor.GRAY + "You have " + ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls(player) + ChatColor.GRAY + " souls left.");
                player.sendMessage("");
                DamageIndicatorUtils.sendDamageIndicator(player, CC.translate("&c&l* PHOENIX *"), "PHOENIX");
                Title title = new Title(CC.translate("&c&l** PHOENIX**"), CC.translate("&7You have &n" + KillTrackerAPI.getAllSouls(player) + "&7 souls left."), 3, 30, 3);
                player.sendTitle(title);
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.25f);
                if (AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)) {
                    try {
                        MasteryFeignDeath.vanishPlayer(player, 4);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (damager instanceof Player) {
                    if (AstroItemSkinsAPI.hasSkinEquipped((Player) damager, SkinType.HALLOWEEN_2021_HELMET) && Math.random() < 0.25 && !damager.hasMetadata("muzzleCD") && !player.hasMetadata("muzzle")) {
                        player.setMetadata("muzzle", new FixedMetadataValue(Enchantments.getInstance(), true));
                        Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), () -> {
                            player.removeMetadata("muzzle", Enchantments.getInstance());
                        }, 10 * 20);
                        damager.setMetadata("muzzleCD", new FixedMetadataValue(Enchantments.getInstance(), true));
                        damager.sendMessage(CC.translate("&6&l* MINEMAN MUZZLE &7Cursed &c" + player.getName() + " &6&l*"));
                        Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), () -> {
                            damager.removeMetadata("muzzleCD", Enchantments.getInstance());
                        }, 600 * 20);
                    }
                }
                for (Entity ent : player.getNearbyEntities(48.0, 48.0, 48.0)) {
                    if (!(ent instanceof Player)) continue;
                    ent.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "*** PHOENIX SOUL (" + ChatColor.GRAY + player.getName() + ", -" + soulCost + " souls" + ChatColor.RED + ChatColor.BOLD + ") ***");
                    ((Player) ent).playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.25f);
                }
                Location loc = player.getLocation();
                return true;
            }
            if (!player.hasMetadata("outOfSoulsMessage") || player.getMetadata("outOfSoulsMessage").get(0).asLong() < System.currentTimeMillis()) {
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.4f);
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** OUT OF SOULS **");
                player.setMetadata("outOfSoulsMessage", new FixedMetadataValue(Enchantments.getInstance(), System.currentTimeMillis() + 15000L));
            }
        }
        return false;
    }

    public static boolean canUsePhoenix(Player pl, int enchantLevel) {
        return !last_phoenix.containsKey(pl.getUniqueId().toString()) || System.currentTimeMillis() - last_phoenix.get(pl.getUniqueId().toString()) > (long) ((4 - 2) * 60000);
    }

    public static int getPhoenixProcSoulCost(Player player) {
        int soulCost = 500;
        int procsSinceOutOfCombat = player.hasMetadata("phoenixProcs") ? player.getMetadata("phoenixProcs").get(0).asInt() : 0;
        while (procsSinceOutOfCombat-- > 0) {
            soulCost *= 2;
        }

        soulCost = Math.min(soulCost, 8000);
        return soulCost;

    }

}

