/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.block.Block
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffect
 *  org.bukkit.potion.PotionEffectType
 */
package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.skins.axes.GoldenAdmirer;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class HeroicTrap
extends HeroicCustomEnchantment {
    private DecimalFormat df = new DecimalFormat("#.##");

    public HeroicTrap() {
        super("Titan Trap", "Trap");
        this.max = 3;
    }

    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        if (((Player)player).getWalkSpeed() < 0.2f) {
            return;
        }
        if(AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.THANKSGIVING_HELMET_WITCH) && !GoldenAdmirer.isAdmirerSilenced((Player) target))
            return;
        if(AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.VIRUS_HELMET_HAZMAT))
            return;
        double random = Math.random();
        double chance = 0.04 * (double)level;
        double durationTicks = 35 + level * 10;
        if (random < chance) {
            Player ptarget = (Player)target;
            if (target.hasMetadata("metaphysicalEnchant")) {
                int metaphysicalLevel = ((MetadataValue)target.getMetadata("metaphysicalEnchant").get(0)).asInt();
                chance -= (double)metaphysicalLevel * 0.0125;
                if (random > (chance = Math.max(chance, 0.01))) {
                    ptarget.sendMessage((Object)ChatColor.DARK_GRAY + "" + (Object)ChatColor.BOLD + "** METAPHYSICAL (" + (Object)ChatColor.DARK_GRAY + "Trap blocked!" + (Object)ChatColor.BOLD + ") **");
                    DamageIndicatorUtils.sendDamageIndicator(ptarget, CC.translate("&8&l* METAPHYSICAL *"), "METAPHYSICAL");

                    return;
                }
            }
            if (AstroItemSkinsAPI.hasSkinEquipped(ptarget, SkinType.CHRISTMAS_BOOTS_SLIPPERS)) {
                ptarget.sendMessage(CC.translate("&3&l* TEDDY SLIPPERS &7Reflected &dTitan Trap &3&l*"));
                return;
            }
            if (AstroItemSkinsAPI.hasSkinEquipped(ptarget, SkinType.FLAMING_HALO) && Math.random() < 0.50) {
                ptarget.sendMessage(CC.translate("&6&l* FLAMING HALO BLOCKED &7[Titan Trap] &6&l*"));
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(ptarget, SkinType.CHRISTMAS_AMULET_SNOWFLAKE)) {
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(ptarget, SkinType.THANKSGIVING_HELMET_WITCH) && (!GoldenAdmirer.isAdmirerSilenced(ptarget))) {
                ptarget.sendMessage(CC.translate("&e&l* WITCH HAT &7Blocked &dTitan Trap &e&l*"));
                return;
            }
            ptarget.setWalkSpeed(0.001f);
            ptarget.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (int)durationTicks, 128), true);
            Location location = ptarget.getEyeLocation();
            Material typeItWas = location.getBlock().getType();
            List<Player> pl = ptarget.getNearbyEntities(32.0, 32.0, 32.0).stream().filter(ent -> ent instanceof Player).map(ent -> (Player)ent).collect(Collectors.toList());
            pl.add(ptarget);
            for (Player near : pl) {
                near.sendBlockChange(location, Material.STATIONARY_WATER, (byte)0);
            }
            ptarget.playSound(ptarget.getLocation(), Sound.WATER, 10.0f, 1.1f);
            ptarget.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** TITAN TRAP [" + (Object)ChatColor.GRAY + this.df.format(durationTicks / 20.0) + "s" + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "] **");
            DamageIndicatorUtils.sendDamageIndicator((Player) ptarget, CC.DarkAquaB + "* TITAN TRAP " + CC.Gray + "[" + durationTicks/20 + "]" + CC.DarkAquaB + "*" + CC.DarkPurpleB +  "*", "TRAP");

            Enchantments.getInstance().getServer().getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), () -> {
                ptarget.setWalkSpeed(0.2f);
                ptarget.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "** FREE [" + (Object)ChatColor.GRAY + "Titan Trap" + (Object)ChatColor.GREEN + (Object)ChatColor.BOLD + "] **");
                for (Player tar : pl) {
                    tar.sendBlockChange(location, typeItWas, (byte)0);
                }
            }, (long)((int)durationTicks));
            Location l = ptarget.getLocation();
            WorldUtil.sendWorldEffect((Block)l.getBlock(), (int)80, (int)2001);
        }
    }
}

