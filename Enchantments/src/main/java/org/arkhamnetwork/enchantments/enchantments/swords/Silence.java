package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import me.astropvp.AstroBackpacksAPI;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.objects.BackpackType;
import me.astropvp.utils.BackpackUtils;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.plugin.*;
import org.bukkit.event.entity.*;
import com.rit.sucy.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.events.*;
import org.bukkit.event.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.metadata.*;
import org.bukkit.*;
import org.arkhamnetwork.Arkkit.libs.*;

public class Silence extends CustomEnchantment
{
    public Silence() {
        super("Silence", GeneralUtil.swords_and_bow, 2);
        Bukkit.getServer().getPluginManager().registerEvents((Listener)new SilenceListener(), (Plugin)Enchantments.getInstance());
        this.max = 4;
        this.base = 20.0;
        this.interval = 5.0;
    }
    
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        int solitudeLevel = 0;
        if (EnchantmentAPI.getEnchantments(user.getEquipment().getItemInHand()).containsKey(EnchantmentAPI.getEnchantment("Solitude"))) {
            solitudeLevel = EnchantmentAPI.getEnchantments(user.getEquipment().getItemInHand()).get(EnchantmentAPI.getEnchantment("Solitude"));
        }
        final double chance = (enchantLevel + solitudeLevel) * 0.09;
        if (Math.random() < chance && target instanceof Player) {
            final Player pTarget = (Player)target;
            if (!new CustomEnchantProcOnEntityEvent(user, (LivingEntity)pTarget, (Event)event, this, enchantLevel).callEvent()) {
                return;
            }
            if (pTarget.hasMetadata("immune_silence") && user instanceof Player && Math.random() < 0.75) {
                ((Player)user).sendMessage(CC.RedB + "* SILENCE BLOCKED [" + CC.Gray + pTarget.getName() + CC.RedB + "] *");
                return;
            }
            long durationTicks = (enchantLevel + solitudeLevel) * 20;

            if (pTarget.getInventory().getChestplate() != null && AstroBackpacksAPI.itemHasSkin(pTarget.getInventory().getChestplate()) && AstroBackpacksAPI.getBackpackByItem(pTarget.getInventory().getChestplate()).equals(BackpackType.CURSED_WINGS)) {
                durationTicks /= 2.5;
            }
            if (ItemSkinUtils.hasItemSkin(pTarget, "CORRUPT_HELMET")) {
                durationTicks /= 2L;
            }
            pTarget.setMetadata("noDefenseProcs", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)true));
            pTarget.playSound(pTarget.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
            pTarget.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + "[" + durationTicks / 20L + "s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");
            ParticleEffect.playEffect(ParticleEffect.ENCHANTMENT_TABLE, pTarget.getEyeLocation(), 0.4f, 80);
            ParticleEffect.playEffect(ParticleEffect.PORTAL, pTarget.getEyeLocation(), 0.4f, 60);
            Bukkit.getScheduler().runTaskLater((Plugin)Enchantments.getInstance(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    pTarget.removeMetadata("noDefenseProcs", (Plugin)Enchantments.getInstance());
                    ParticleEffect.playEffect(ParticleEffect.ENCHANTMENT_TABLE, pTarget.getEyeLocation(), 0.4f, 80);
                    ParticleEffect.playEffect(ParticleEffect.PORTAL, pTarget.getEyeLocation(), 0.4f, 60);
                }
            }, durationTicks);
        }
    }
}
