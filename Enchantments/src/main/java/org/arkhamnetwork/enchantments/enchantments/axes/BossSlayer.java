package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.nms.MinecraftServerUtils;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class BossSlayer extends CustomEnchantment {
    public BossSlayer() {
        super("Boss Slayer", GeneralUtil.axe, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 10.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final Player p = (Player) player;
        if (playerHit instanceof Player) {
            return;
        }
        final ItemStack used = p.getItemInHand();
        if (used == null || !used.getType().name().endsWith("_AXE")) {
            return;
        }
        if (p.hasMetadata("lastItemHeldChange") && MinecraftServerUtils.getCurrentServerTick() - p.getMetadata("lastItemHeldChange").get(0).asLong() <= 5L) {
            return;
        }
        if (playerHit.hasMetadata("boss")) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0 + 0.075 * level));
        }
    }
}
