package org.arkhamnetwork.enchantments.enchantments.swords;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.skins.axes.GoldenAdmirer;
import net.astropvp.armorsets.AstroArmorSetsAPI;
import net.astropvp.armorsets.struct.ArmorSetType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import com.rit.sucy.*;
import me.ifamasssxd.cosmicmasks.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.scheduler.*;
import com.google.common.collect.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.plugin.*;
import org.arkhamnetwork.enchantments.events.*;
import org.bukkit.metadata.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.utils.*;

public class Silence extends CustomEnchantment
{
    public Silence() {
        super("Silence", GeneralUtil.weapons, 2);
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (!(user instanceof Player) || !(target instanceof Player)) {
            return;
        }
        int solitudeLevel = 0;
        if (EnchantmentAPI.getEnchantments(user.getEquipment().getItemInHand()).containsKey(EnchantmentAPI.getEnchantment("Solitude"))) {
            solitudeLevel = EnchantmentAPI.getEnchantments(user.getEquipment().getItemInHand()).get(EnchantmentAPI.getEnchantment("Solitude"));
        }
        if (EnchantmentAPI.getEnchantments(user.getEquipment().getItemInHand()).containsKey(EnchantmentAPI.getEnchantment("Perfect Solitude"))) {
            solitudeLevel = EnchantmentAPI.getEnchantments(user.getEquipment().getItemInHand()).get(EnchantmentAPI.getEnchantment("Perfect Solitude")) * 2;
        }
        final double chance = (enchantLevel + solitudeLevel) * 0.03;
        if (Math.random() < chance) {
            final Player pTarget = (Player)target;
            final long durationTicks = (enchantLevel + solitudeLevel) * 20L;


            this.silencePlayer((Player)user, pTarget, enchantLevel, (double)durationTicks, event);
        }
    }

    public boolean silencePlayer(final Player silencer, final Player target, final int level, double durationTicks, final EntityDamageByEntityEvent event) {
        if (target.hasMetadata("silenceTargetCooldown") && target.getMetadata("silenceTargetCooldown").get(0).asLong() > System.currentTimeMillis()) {
            return false;
        }
        if (event != null && !new CustomEnchantProcOnEntityEvent(silencer, target, event, this, level).callEvent()) {
            return false;
        }

        if (target.hasMetadata("immune_silence") && Math.random() < 0.1) {
            silencer.sendMessage(CC.RedB + "* SILENCE BLOCKED [" + CC.Gray + target.getName() + CC.RedB + "] *");
            return false;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.SUMMER_2021_HELMET) && Math.random() < 0.25) {
            silencer.sendMessage(CC.translate("&C&l* SILENCE BLOCKED [&7" + target.getName() + "&C&l] *"));
            target.sendMessage(CC.translate("&6&l* SILENCE BLOCKED [&7Tiki Mask&6&l] *"));
            return false;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.CORRUPT_HELMET)) {
            durationTicks /= 2.0;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.SUMMER_2021_HELMET)) {
            durationTicks *= 0.6;
        }

        if (AstroArmorSetsAPI.getCurrentArmorSet(target) != null) {
            if (AstroArmorSetsAPI.getCurrentArmorSet(target) == ArmorSetType.DRAGON) {
                durationTicks *=0.25;
            }
        }
        if (AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.BACK_TO_SCHOOL_2021_HELMET) || AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.SPRING_BREAK_2022_MUTATED_BELT)) {
            durationTicks *= 0.6;
        }

        if (AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.SPRING_BREAK_2022_BELT)) {
            durationTicks*=0.8;
        }

        if (AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.EASTER_2022_BACKPACK) || AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.EASTER_2022_MUTATED_BACKPACK)) {
            durationTicks *=0.65;
        }

        if (AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.CHRISTMAS_2021_HELMET) || AstroItemSkinsAPI.hasSkinEquipped((Player) target, SkinType.CHRISTMAS_2021_MUTATED_HELMET)) {
            durationTicks *= 0.55;
        }

        if (!GoldenAdmirer.isAdmirerSilenced(target)) {
            if (AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.THANKSGIVING_HELMET_WITCH)) {
                durationTicks -= durationTicks * 0.25;
            }
            if (AstroItemSkinsAPI.hasSkinEquipped(target, SkinType.HALLOWEEN_BACKPACK)) {
                durationTicks -= durationTicks * 0.75;
            }
        }
        target.setMetadata("noDefenseProcs", new FixedMetadataValue((Plugin)Enchantments.getInstance(), true));
        target.playSound(target.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
        target.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + "[" + (int)(durationTicks / 20.0) + "s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");
        DamageIndicatorUtils.sendDamageIndicator((Player) target, CC.DarkPurpleB + "* SILENCE " + CC.Gray + "[" + (int)durationTicks/20 + "s] " + CC.DarkPurpleB +  "*", "SILENCE");
        target.setMetadata("silenceTargetCooldown", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis() + 2500L));
        Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)Enchantments.getInstance(), new Runnable() {
            @Override
            public void run() {
                target.removeMetadata("noDefenseProcs", (Plugin)Enchantments.getInstance());
            }
        }, (long)durationTicks);
        return true;
    }
}
