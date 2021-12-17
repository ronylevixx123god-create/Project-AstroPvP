package org.arkhamnetwork.enchantments.enchantments.axes;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.rit.sucy.CustomEnchantment;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Overwhelm extends CustomEnchantment {
    public Overwhelm() {
        super("Overwhelm", GeneralUtil.axe, 2);
        this.max = 4;
        this.base = 10.0;
        this.interval = 8.0;
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }


        if (target.hasMetadata("overwhelm_effect")) return;

        if (Math.random() < 0.08) {
            target.setMetadata("overwhelm_effect", new FixedMetadataValue(Enchantments.getInstance(), true));
            target.sendMessage(CC.translate("&6&l* STRUCK BY OVERWHELM EFFECT &7[" + user.getName() + "&7]&6&l *"));

            Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), () -> {
                target.removeMetadata("overwhelm_effect", Enchantments.getInstance());
                target.sendMessage(CC.translate("&6&l* OVERWHELM EFFECT &c&lEXPIRED &6&l*"));
            }, 120L);
        }
    }
}
