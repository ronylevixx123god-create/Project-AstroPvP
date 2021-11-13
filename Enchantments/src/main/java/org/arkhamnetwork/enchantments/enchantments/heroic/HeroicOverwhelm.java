package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.rit.sucy.CustomEnchantment;
import com.rit.sucy.EnchantmentAPI;
import lombok.SneakyThrows;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class HeroicOverwhelm extends CustomEnchantment {
    public HeroicOverwhelm() {
        super("Bitter Overwhelm", GeneralUtil.axe, 2);
        this.max = 4;
        this.base = 10.0;
        this.interval = 8.0;
        Bukkit.getScheduler().runTaskLater((Plugin) Enchantments.getInstance(), (Runnable) new Runnable() {
            @Override
            public void run() {
                HeroicOverwhelm.this.heroicEnchantment = true;
                HeroicOverwhelm.this.nonHeroicVersion = EnchantmentAPI.getEnchantment("Overwhelm");
            }
        }, 2L);
    }

    @SneakyThrows
    public void applyEffect(final LivingEntity user, final LivingEntity target, final int enchantLevel, final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }


        if (target.hasMetadata("heroic_overwhelm_effect")) return;

        if (Math.random() < 0.05) {
            target.setMetadata("heroic_overwhelm_effect", new FixedMetadataValue(Enchantments.getInstance(), true));
            target.sendMessage(CC.translate("&6&l* STRUCK BY BITTER OVERWHELM EFFECT &7[" + user.getName() + "&7]&6&l *"));
            event.setDamage(event.getDamage() * (1 + (enchantLevel/100)));

            Bukkit.getScheduler().runTaskLaterAsynchronously(Enchantments.getInstance(), () -> {
                target.removeMetadata("heroic_overwhelm_effect", Enchantments.getInstance());
                target.sendMessage(CC.translate("&6&l* BITTER OVERWHELM EFFECT &c&lEXPIRED &6&l*"));
            }, 120L);
        }
    }
}
