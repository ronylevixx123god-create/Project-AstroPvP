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

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.cosmicutils.utils.*;
import com.earth2me.essentials.Essentials;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicmasks.MaskType;
import net.astropvp.core.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.Enchantments;
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
    protected static boolean cosmicOutposts;
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
        cosmicOutposts = Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts");
        factionUpgrades = Bukkit.getPluginManager().isPluginEnabled("FactionUpgrades");
        this.ess = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
        Bukkit.getPluginManager().registerEvents((Listener)new PhoenixListener(this), (Plugin)Enchantments.getInstance());
        Bukkit.getScheduler().runTaskTimer((Plugin)Enchantments.getEssentials(), (Runnable)((Object)new reducePhoenixCount()), 12000L, 12000L);
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
            if (Phoenix.procPhoenix((Player)player, attacker, level, dmg)) {
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
                if (120L - (System.currentTimeMillis() - ((Long)last_phoenix.get(player.getUniqueId().toString())).longValue()) / 1000L != 0L)
                player.sendMessage(CC.translate("&c&l** PHOENIX ON COOLDOWN ** &7[" + TimeUtils.formatSeconds(120L - (System.currentTimeMillis() - ((Long)last_phoenix.get(player.getUniqueId().toString())).longValue()) / 1000L) + "]"));
            }
            if (damager != null && damager instanceof Player && Math.random() <= 0.5 && MaskType.hasEquipped((Player)((Player)damager), (String)"DEATH_KNIGHT") && !AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)) {
                player.sendMessage(String.valueOf(CC.RedB) + "* PHOENIX BLOCKED [" + CC.Gray + ((Player)damager).getName() + CC.RedB + "] *");
                ((Player)damager).sendMessage(String.valueOf(CC.RedB) + "* DEATH KNIGHT MASK [" + CC.Gray + player.getName() + "'s Phoenix Blocked" + CC.RedB + "] *");
                last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                Bukkit.getLogger().info("Phoenix blocked from proccing for " + player.getName() + " from damager " + ((Player)damager).getName());
                return false;
            }

            if (damager != null && damager instanceof Player && MaskType.hasEquipped((Player) damager, (String)"THANOS") && !AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)) {
                player.sendMessage(String.valueOf(CC.RedB) + "* PHOENIX BLOCKED [" + CC.Gray + ((Player)damager).getName() + CC.RedB + "] *");
                ((Player)damager).sendMessage(String.valueOf(CC.RedB) + "* THANOS MASK [" + CC.Gray + player.getName() + "'s Phoenix Blocked" + CC.RedB + "] *");
                last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                Bukkit.getLogger().info("Phoenix blocked from proccing for " + player.getName() + " from damager " + ((Player)damager).getName());
                return false;
            }
            if (damager != null && damager instanceof Player && damager.hasMetadata("banshee_scrolls") && !AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)) {
                player.sendMessage(String.valueOf(CC.RedB) + "* PHOENIX BLOCKED [&4BANSHEE RKO] *");
                ((Player)damager).sendMessage(String.valueOf(CC.RedB) + "* BANSHEE RKO [" + CC.Gray + player.getName() + "'s Phoenix Blocked" + CC.RedB + "] *");
                last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                Bukkit.getLogger().info("Phoenix blocked from proccing for " + player.getName() + " from damager " + ((Player)damager).getName());
                return false;
            }
            if (player.hasMetadata("witchered") && !AstroItemSkinsAPI.hasSkinEquipped(player, SkinType.CORRUPT_BELT_UTILITY)) {
                player.sendMessage(CC.translate("&c&l* PHOENIX BLOCKED [&7Witcher Ability&c&l] *"));
                last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                return false;
            }
            if (MaskType.hasEquipped((Player) damager, "CRANKED") && Math.random() < 0.5) {
                ((Player) damager).playSound(damager.getLocation(), Sound.ANVIL_LAND, 2, 2);
                ((Player) damager).playSound(player.getLocation(), Sound.ANVIL_LAND, 2, 2);
                damager.sendMessage(CC.translate("&4&l* CRANKED MASK BLOCKED PHOENIX [&c" + player.getName() + "&4&l] *"));
                player.sendMessage(CC.translate("&4&l* PHOENIX BLOCKED [&c" + damager.getName() + " Cranked Mask&4&l] *"));
                last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                return false;
            }
            int soulCost = Phoenix.getPhoenixProcSoulCost(player);
            if (KillTrackerAPI.hasSoulAmount((Player)player, (int)soulCost) && Phoenix.canUsePhoenix(player, level)) {
                KillTrackerAPI.removeSoulsFromGems((Player)player, (int)soulCost);
                last_phoenix.put(player.getUniqueId().toString(), System.currentTimeMillis());
                player.setMetadata("phoenixProcs", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(player.hasMetadata("phoenixProcs") ? ((MetadataValue)player.getMetadata("phoenixProcs").get(0)).asInt() + 1 : 1)));
                GeneralUtil.setPlayerHealth((LivingEntity)player, player.getMaxHealth());
                player.sendMessage("");
                player.sendMessage(String.valueOf(ChatColor.GOLD.toString()) + ChatColor.BOLD.toString() + "*** " + (Object)ChatColor.UNDERLINE + "PHOENIX SOUL" + (Object)ChatColor.GOLD + (Object)ChatColor.BOLD + " ***");
                player.sendMessage(String.valueOf(ChatColor.RED.toString()) + ChatColor.BOLD.toString() + "- " + soulCost + " Soul Gems");
                player.sendMessage((Object)ChatColor.GRAY + "You have " + (Object)ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls((Player)player) + (Object)ChatColor.GRAY + " souls left.");
                player.sendMessage("");
                DamageIndicatorUtils.sendDamageIndicator((Player) player, CC.translate( "&c&l* PHOENIX *"), "PHOENIX");
                Title title = new Title(CC.translate("&c&l** PHOENIX**"), CC.translate("&7You have &n" + KillTrackerAPI.getAllSouls((Player)player) + "&7 souls left." ), 3, 30, 3);
                player.sendTitle(title);
                player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.25f);
                for (Entity ent : player.getNearbyEntities(48.0, 48.0, 48.0)) {
                    if (!(ent instanceof Player)) continue;
                    ((Player)ent).sendMessage(String.valueOf(ChatColor.RED.toString()) + ChatColor.BOLD.toString() + "*** PHOENIX SOUL (" + (Object)ChatColor.GRAY + player.getName() + ", -" + soulCost + " souls" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + ") ***");
                    ((Player)ent).playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.25f);
                }
                Location loc = player.getLocation();
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.FLAME, (Location)loc, (float)((float)Math.random()), (float)((float)Math.random()), (float)((float)Math.random()), (float)0.75f, (int)80);
                ParticleEffect.sendToLocation((ParticleEffect)ParticleEffect.LAVA, (Location)loc, (float)((float)Math.random()), (float)((float)Math.random()), (float)((float)Math.random()), (float)0.7f, (int)20);
                return true;
            }
            if (!player.hasMetadata("outOfSoulsMessage") || ((MetadataValue)player.getMetadata("outOfSoulsMessage").get(0)).asLong() < System.currentTimeMillis()) {
                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.LAVA, (Location)player.getEyeLocation(), (float)0.4f, (int)20);
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.4f);
                player.sendMessage(String.valueOf(ChatColor.RED.toString()) + ChatColor.BOLD.toString() + "** OUT OF SOULS **");
                player.setMetadata("outOfSoulsMessage", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(System.currentTimeMillis() + 15000L)));
            }
        }
        return false;
    }

    public static boolean canUsePhoenix(Player pl, int enchantLevel) {
        return !last_phoenix.containsKey(pl.getUniqueId().toString()) || System.currentTimeMillis() - last_phoenix.get(pl.getUniqueId().toString()) > (long)((4 - 2) * 60000);
    }

    public static int getPhoenixProcSoulCost(Player player) {
        int soulCost = 500;
        int procsSinceOutOfCombat = player.hasMetadata("phoenixProcs") ? ((MetadataValue)player.getMetadata("phoenixProcs").get(0)).asInt() : 0;
        while (procsSinceOutOfCombat-- > 0) {
            soulCost *= 2;
        }
        if (cosmicOutposts && CosmicOutpostsAPI.controlsCosmonautOutpost((Player)player)) {
            soulCost /= 2;
        }
        soulCost = Math.min(soulCost, 8000);
        return soulCost;

    }

}

