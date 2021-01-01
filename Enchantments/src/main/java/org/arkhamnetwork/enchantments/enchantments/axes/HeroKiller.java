package org.arkhamnetwork.enchantments.enchantments.axes;

import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.arkhamnetwork.enchantments.cmds.*;
import org.bukkit.entity.*;
import com.rit.sucy.*;
import com.cosmicpvp.cosmicutils.nms.*;
import org.bukkit.metadata.*;
import me.ifamasssxd.killtracker.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import com.cosmicpvp.cosmicutils.utils.*;

public class HeroKiller extends CustomEnchantment
{
    public HeroKiller() {
        super("Hero Killer", GeneralUtil.axe, 2);
        this.max = 3;
        this.base = 10.0;
        this.interval = 10.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        if (!CommandEnchDust.soulEnabled) {
            return;
        }
        if (!(playerHit instanceof Player)) {
            return;
        }
        final Player p = (Player)player;
        final ItemStack used = p.getItemInHand();
        if (used == null || !EnchantmentAPI.itemHasEnchantment(used, this.enchantName)) {
            return;
        }
        if (p.hasMetadata("lastItemHeldChange") && MinecraftServerUtils.getCurrentServerTick() - p.getMetadata("lastItemHeldChange").get(0).asLong() <= 5L) {
            return;
        }
        if (p.hasMetadata("soulTrap") && p.getMetadata("soulTrap").get(0).asLong() > System.currentTimeMillis()) {
            return;
        }
        if (KillTrackerAPI.hasGodMode(p) && KillTrackerAPI.getAllSouls(p) > 0) {
            if (!this.isWearingHeroic((Player)playerHit)) {
                return;
            }
            if (!p.hasMetadata("lastSoulRemoveEvent") || System.currentTimeMillis() - p.getMetadata("lastSoulRemoveEvent").get(0).asLong() > 1000L) {
                KillTrackerAPI.removeSoulsFromGems(p, 4);
            }
            final double increase = 0.1 * level;
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0 + increase));
        }
    }
    
    private boolean isWearingHeroic(final Player player) {
        for (final ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null) {
                final Material type = armor.getType();
                if ((type == Material.LEATHER_BOOTS || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_HELMET) && new NBTWrapper(armor).getString("cosmicType").equals("HEROIC_ARMOR")) {
                    return true;
                }
            }
        }
        return false;
    }
}
