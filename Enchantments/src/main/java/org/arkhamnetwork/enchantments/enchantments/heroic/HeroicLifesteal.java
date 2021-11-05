package org.arkhamnetwork.enchantments.enchantments.heroic;

import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class HeroicLifesteal extends HeroicCustomEnchantment {
    public HeroicLifesteal() {
        super("Demonic Lifesteal", "Lifesteal", GeneralUtil.swords, 2);
        this.max = 3;
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        double random = Math.random();
        double chance = (enchantLevel == 1) ? 0.001D : ((enchantLevel == 2) ? 0.001D : ((enchantLevel == 3) ? 0.001D : 0.002D));
        int toHeal = (enchantLevel == 1) ? 2 : ((enchantLevel == 2) ? 2 : ((enchantLevel == 3) ? 2 : 3));
        int health = (int) user.getHealth() + toHeal;
        if (health > user.getMaxHealth())
            health = (int) user.getMaxHealth();
        if (random < chance) {
            if (target instanceof Player && MaskType.hasEquipped((Player) target, "NECROMANCER"))
                return;
            GeneralUtil.setPlayerHealth(user, health);
        }
    }
}
