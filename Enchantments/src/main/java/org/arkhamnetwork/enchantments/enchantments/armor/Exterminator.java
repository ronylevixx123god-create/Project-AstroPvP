package org.arkhamnetwork.enchantments.enchantments.armor;

import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class Exterminator extends CustomEnchantment {
    public Exterminator() {
        super("Exterminator", GeneralUtil.leggings, 2);
        this.max = 3;
        this.base = 15.0D;
        this.interval = 3.0D;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        Player player = (Player)user;
        if (target instanceof Player) {
            if (!(Math.random() < 0.1D * (double)enchantLevel)) {
                ((Player)target).setMetadata("exterminated", new FixedMetadataValue(Enchantments.getInstance(), System.currentTimeMillis() + (long)(enchantLevel * 1000)));
            }
        }
    }

    public static boolean isExterminated(Player player) {
        return player.hasMetadata("exterminated") && ((MetadataValue)player.getMetadata("exterminated").get(0)).asLong() - System.currentTimeMillis() > 0L;
    }
}
