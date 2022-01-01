/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.rit.sucy.CustomEnchantment
 *  com.rit.sucy.EnchantmentAPI
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.enchantments.enchantments.swords;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import de.inventivegames.particle.ParticleEffect;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.skins.axes.GoldenAdmirer;
import me.bryside.weaponrunes.enums.WeaponBonusType;
import me.bryside.weaponrunes.utils.WeaponBonusUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Silence
extends CustomEnchantment {
    public Silence() {
        super("Silence", GeneralUtil.swords_and_bow, 2);
        Bukkit.getServer().getPluginManager().registerEvents((Listener)new SilenceListener(), (Plugin)Enchantments.getInstance());
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        int solitudeLevel = 0;
        if (EnchantmentAPI.getEnchantments((ItemStack)user.getEquipment().getItemInHand()).containsKey((Object)EnchantmentAPI.getEnchantment((String)"Solitude"))) {
            solitudeLevel = (Integer)EnchantmentAPI.getEnchantments((ItemStack)user.getEquipment().getItemInHand()).get((Object)EnchantmentAPI.getEnchantment((String)"Solitude"));
        }
        double chance = (double)(enchantLevel + solitudeLevel) * 0.02;
        if (Math.random() < chance && target instanceof Player) {
            final Player pTarget = (Player)target;
            if (!new CustomEnchantProcOnEntityEvent(user, (LivingEntity)pTarget, (Event)event, this, enchantLevel).callEvent()) {
                return;
            }
            if (pTarget.hasMetadata("immune_silence") && user instanceof Player && Math.random() < 0.75) {
                ((Player)user).sendMessage(CC.RedB + "* SILENCE BLOCKED [" + CC.Gray + pTarget.getName() + CC.RedB + "] *");
                return;
            }
            long durationTicks = (enchantLevel + solitudeLevel) * 20L;

            if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.SUMMER_2021_HELMET) && Math.random() < 0.15) {
                pTarget.sendMessage(CC.translate("&6&l* TIKI MASK &7Blocked Silence &6&l*"));
            }
            if (WeaponBonusUtils.hasWeaponBonus(((Player) user).getItemInHand()) && WeaponBonusUtils.getWeaponBonusByItem(((Player) user).getItemInHand()) == WeaponBonusType.REFORGED && Math.random() < 0.5) {
                    durationTicks = (long) (durationTicks * 1.5);

            }

            if (pTarget.getInventory().getChestplate() != null && AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.HALLOWEEN_BACKPACK) && (!GoldenAdmirer.isAdmirerSilenced(pTarget)) && (!MaskUtils.hasMaskEquipped(pTarget, "WITCHER"))) {
                durationTicks = (long) (durationTicks * 0.25);
            }
            if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.CORRUPT_HELMET)) {
                durationTicks = (long) (durationTicks * 0.5);
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.THANKSGIVING_HELMET_WITCH) && (!GoldenAdmirer.isAdmirerSilenced(pTarget))) {
                durationTicks = (long) (durationTicks * 0.75);
            }
            if((enchantLevel + solitudeLevel) * 20 * 0.6 < durationTicks && AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.SUMMER_2021_HELMET)) {
                durationTicks = (enchantLevel + solitudeLevel) * 20L;
            }
            DamageIndicatorUtils.sendDamageIndicator((Player) pTarget, CC.DarkPurpleB + "* SILENCE " + CC.Gray + "[" + durationTicks/20 + "s] " + CC.DarkPurpleB +  "*", "SILENCE");

            pTarget.setMetadata("noDefenseProcs", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)true));
            pTarget.playSound(pTarget.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
            pTarget.sendMessage((Object)ChatColor.DARK_PURPLE + "" + (Object)ChatColor.BOLD + "* SILENCED " + (Object)ChatColor.GRAY + "[" + durationTicks / 20L + "s] " + (Object)ChatColor.DARK_PURPLE + (Object)ChatColor.BOLD + "*");
            try {
                ParticleEffect.ENCHANTMENT_TABLE.sendToPlayers(Bukkit.getOnlinePlayers(), pTarget.getEyeLocation(), 0.4f, 0.4f, 0.4f, 0.1f, 80);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            try {
                ParticleEffect.PORTAL.sendToPlayers(Bukkit.getOnlinePlayers(), pTarget.getEyeLocation(), 0.4f, 0.4f, 0.4f, 0.1f, 60);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), new Runnable(){

                @Override
                public void run() {
                    pTarget.removeMetadata("noDefenseProcs", (Plugin)Enchantments.getInstance());
                    try {
                        ParticleEffect.ENCHANTMENT_TABLE.sendToPlayers(Bukkit.getOnlinePlayers(), pTarget.getEyeLocation(), 0.4f, 0.4f, 0.4f, 0.1f, 80);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    try {
                        ParticleEffect.PORTAL.sendToPlayers(Bukkit.getOnlinePlayers(), pTarget.getEyeLocation(), 0.4f, 0.4f, 0.4f, 0.1f, 60);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
            }, durationTicks);
        }
    }
}

