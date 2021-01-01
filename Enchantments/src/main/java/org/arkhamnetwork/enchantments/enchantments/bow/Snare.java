package org.arkhamnetwork.enchantments.enchantments.bow;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.rit.sucy.*;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.ItemSkinUtils;
import org.bukkit.event.entity.*;
import org.bukkit.metadata.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.*;

public class Snare extends CustomEnchantment
{
    public Snare() {
        super("Snare", new Material[] { Material.BOW }, 2);
        this.max = 4;
        this.base = 15.0;
        this.interval = 5.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        double chance = level * 0.09;
        final double random = Math.random();
        if (random < chance) {
            if (playerHit.hasMetadata("metaphysicalEnchant")) {
                final int metaphysicalLevel = playerHit.getMetadata("metaphysicalEnchant").get(0).asInt();
                chance -= metaphysicalLevel * 0.07;
                if (random > chance) {
                    ((Player)playerHit).sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "** METAPHYSICAL (" + ChatColor.DARK_GRAY + "Snare blocked!" + ChatColor.BOLD + ") **");
                    return;
                }
            }

            if (AstroItemSkinsAPI.hasSkinEquipped((Player) playerHit, SkinType.VIRUS_HELMET_HAZMAT)) {
                ((Player)playerHit).sendMessage(CC.translate("&2&L* PRISON BREAK &7(Hazmat Helmet) &2&L*"));
                ASound.ANVIL_LAND.play((Player)playerHit, 2.0f, 1.4f);
                return;
            }

            playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, level * 20 + 20, level));
            if (level >= 3) {
                playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, level * 20 + 20, level - 3));
            }
            final Location l = playerHit.getLocation().add(0.0, 0.5, 0.0);
            WorldUtil.sendWorldEffect(l.getBlock(), 106, 2001);
        }
    }
}
