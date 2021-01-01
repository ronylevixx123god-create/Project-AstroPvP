package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.events.*;
import org.bukkit.event.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.potion.*;
import org.bukkit.*;

public class IceAspect extends CustomEnchantment
{
    public IceAspect() {
        super("IceAspect", GeneralUtil.swords, 2);
        this.max = 3;
        this.base = 15.0;
        this.interval = 3.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (playerHit.hasPotionEffect(PotionEffectType.SLOW)) {
            return;
        }
        final double random = Math.random();
        final double chance = 0.035 * level;
        final int time = 2 * level;
        if (random < chance && playerHit instanceof Player) {
            final Location l = playerHit.getLocation();
            if (!new CustomEnchantProcOnEntityEvent(player, playerHit, (Event)e, this, level).callEvent()) {
                return;
            }
            if (playerHit.hasMetadata("immune_freeze")) {
                ((Player)playerHit).sendMessage(CC.DarkGrayB + "* DRAGON SLAYER [" + ChatColor.GRAY + "Ice Aspect blocked!" + CC.DarkGrayB + "] *");
                return;
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VIRUS_HELMET_HAZMAT)) {
                ((Player)playerHit).sendMessage(CC.translate("&2&L* PRISON BREAK &7(Hazmat Helmet) &2&L*"));
                ASound.ANVIL_LAND.play((Player) playerHit, 2.0f, 1.4f);
                return;
            }
            WorldUtil.sendWorldEffect(l.getBlock(), 57, 2001);
            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time * 20, 5));
        }
    }
}
