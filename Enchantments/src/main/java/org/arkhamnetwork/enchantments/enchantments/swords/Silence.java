package org.arkhamnetwork.enchantments.enchantments.swords;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import me.astropvp.astroparticles.utils.ParticleUtils;
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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class Silence extends CustomEnchantment {
    public Silence() {
        super("Silence", GeneralUtil.weapons, 2);
        Bukkit.getServer().getPluginManager().registerEvents((Listener) new SilenceListener(), (Plugin) Enchantments.getInstance());
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        int solitudeLevel = 0;
        if (EnchantmentAPI.itemHasEnchantment(user.getEquipment().getItemInHand(), "Solitude"))
            solitudeLevel = 3;
        if (EnchantmentAPI.itemHasEnchantment(user.getEquipment().getItemInHand(), "Perfect Solitude"));
            solitudeLevel *=2;
        final double chance = (enchantLevel + solitudeLevel) * 0.011D;
        if (Math.random() < chance && target instanceof Player) {
            final Player pTarget = (Player) target;
            if (pTarget.hasMetadata("noDefenseProcs")) {
                return;
            }
            if (!new CustomEnchantProcOnEntityEvent(user, (LivingEntity) pTarget, (Event) event, this, enchantLevel).callEvent()) {
                return;
            }
            if (pTarget.hasMetadata("immune_silence") && user instanceof Player && Math.random() < 0.75) {
                ((Player) user).sendMessage(CC.RedB + "* SILENCE BLOCKED [" + CC.Gray + pTarget.getName() + CC.RedB + "] *");
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.SUMMER_2021_HELMET) && Math.random() < 0.15) {
                pTarget.sendMessage(CC.translate("&6&l* TIKI MASK &7Blocked Silence &6&l*"));
            }


            long durationTicks = (enchantLevel + solitudeLevel) * 20L;

            if (WeaponBonusUtils.hasWeaponBonus(((Player) user).getItemInHand()) && WeaponBonusUtils.getWeaponBonusByItem(((Player) user).getItemInHand()) == WeaponBonusType.REFORGED && Math.random() < 0.5) {
                if (!(CosmicArmorSetsAPI.getBonusCrystalsEquipped((Player) target).containsKey(ArmorSet.SANTA)) && Math.random() < 0.35) {
                    durationTicks = (long) (durationTicks * 1.5);
                }
            }

            if (pTarget.getInventory().getChestplate() != null && AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.HALLOWEEN_BACKPACK) && (!GoldenAdmirer.isAdmirerSilenced(pTarget))) {
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
            pTarget.setMetadata("noDefenseProcs", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) true));
            pTarget.playSound(pTarget.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
            pTarget.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + "[" + durationTicks / 20L + "s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");

            try {
                de.inventivegames.particle.ParticleEffect.ENCHANTMENT_TABLE.sendToPlayer(pTarget,target.getLocation(),0,0,0,0.4f, 5);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            try {
                de.inventivegames.particle.ParticleEffect.PORTAL.sendToPlayer(pTarget,target.getLocation(),0,0,0,0.4f, 5);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    pTarget.removeMetadata("noDefenseProcs", (Plugin) Enchantments.getInstance());

                    de.inventivegames.particle.ParticleEffect.ENCHANTMENT_TABLE.sendToPlayer(pTarget, target.getLocation(), 0, 0, 0, 0.4f, 5);


                    de.inventivegames.particle.ParticleEffect.PORTAL.sendToPlayer(pTarget, target.getLocation(), 0, 0, 0, 0.4f, 5);
                }

            }, durationTicks);
        }
    }
}
