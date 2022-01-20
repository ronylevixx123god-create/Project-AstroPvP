package org.arkhamnetwork.enchantments.enchantments.axes;

import com.rit.sucy.*;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.nms.*;
import org.bukkit.metadata.*;
import org.bukkit.inventory.*;

public class BossSlayer extends CustomEnchantment
{
    public BossSlayer() {
        super("Boss Slayer", GeneralUtil.axe, 2);
        this.max = 5;
        this.base = 10.0;
        this.interval = 10.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity playerHit, final int level, final EntityDamageByEntityEvent e) {
        final Player p = (Player)player;
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
            AstroDamageHandler.getInstance().getDamageHandler().increaseDamage((double)(level * 2), e, "&6&lBoss Slayer");
        }
    }
}
