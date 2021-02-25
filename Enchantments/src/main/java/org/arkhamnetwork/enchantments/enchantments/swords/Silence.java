package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import me.astropvp.AstroBackpacksAPI;
import me.astropvp.damageindicators.DamageIndicators;
import me.astropvp.damageindicators.utils.DamageIndicatorUtils;
import me.astropvp.objects.BackpackType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.arkhamnetwork.enchantments.util.ItemSkinUtils;
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
        super("Silence", GeneralUtil.swords_and_bow, 2);
        Bukkit.getServer().getPluginManager().registerEvents((Listener) new SilenceListener(), (Plugin) Enchantments.getInstance());
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        int solitudeLevel = 0;
        if (EnchantmentAPI.getEnchantments(user.getEquipment().getItemInHand()).containsKey(EnchantmentAPI.getEnchantment("Solitude"))) {
            solitudeLevel = EnchantmentAPI.getEnchantments(user.getEquipment().getItemInHand()).get(EnchantmentAPI.getEnchantment("Solitude"));
        }

        if (EnchantmentAPI.getEnchantments(user.getEquipment().getItemInHand()).containsKey(EnchantmentAPI.getEnchantment("Perfect Solitude"))) {
            solitudeLevel = 4;
        }
        final double chance = (enchantLevel + solitudeLevel) * 0.020;
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
            long durationTicks = (enchantLevel + solitudeLevel) * 20;

            if (pTarget.getInventory().getChestplate() != null && AstroBackpacksAPI.itemHasSkin(pTarget.getInventory().getChestplate()) && AstroBackpacksAPI.getBackpackByItem(pTarget.getInventory().getChestplate()).equals(BackpackType.CURSED_WINGS)) {
                durationTicks /= 2.5;
            }
            if (ItemSkinUtils.hasItemSkin(pTarget, "CORRUPT_HELMET")) {
                durationTicks /= 2L;
            }
            pTarget.setMetadata("noDefenseProcs", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) true));
            pTarget.playSound(pTarget.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
            pTarget.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + "[" + durationTicks / 20L + "s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");
            //ParticleEffect.playEffect(ParticleEffect.ENCHANTMENT_TABLE, pTarget.getEyeLocation(), 0.4f, 80);
            //ParticleEffect.playEffect(ParticleEffect.PORTAL, pTarget.getEyeLocation(), 0.4f, 60);

            try {
                de.inventivegames.particle.ParticleEffect.ENCHANTMENT_TABLE.sendToPlayer(pTarget,target.getLocation(),0,0,0,0.4f, 80);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            try {
                de.inventivegames.particle.ParticleEffect.PORTAL.sendToPlayer(pTarget,target.getLocation(),0,0,0,0.4f, 60);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            try {
                if (DamageIndicatorUtils.isPlayerIndicating((Player) user)) {
                    DamageIndicatorUtils.createDamageIndicator((Player) user,
                            target.getLocation().add(1.0D, 2.0D, 0.0D),
                            new String[]{ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + "[" + durationTicks / 20L + "s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*"});
                }
            } catch (Exception e) {

            }

            Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    pTarget.removeMetadata("noDefenseProcs", (Plugin) Enchantments.getInstance());
                    try {
                        de.inventivegames.particle.ParticleEffect.ENCHANTMENT_TABLE.sendToPlayer(pTarget,target.getLocation(),0,0,0,0.4f, 80);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    try {
                        de.inventivegames.particle.ParticleEffect.PORTAL.sendToPlayer(pTarget,target.getLocation(),0,0,0,0.4f, 60);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }, durationTicks);
        }
    }
}
