package org.arkhamnetwork.enchantments.mastery;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;

public class MasteryNeutralize extends MasteryCustomEnchantment
{
    public MasteryNeutralize() {
        super("Neutralize", GeneralUtil.weapons, 10);
        this.max = 5;
        this.base = 25.0;
        this.interval = 10.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity target, final int level, final EntityDamageByEntityEvent e) {
        if (!(player instanceof Player) || !(target instanceof Player)) {
            return;
        }
        final Player user = (Player)player;
        final Player pTarget = (Player)target;
        if (pTarget.hasMetadata("neutralized")) {
            return;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.CORRUPT_HELMET)) {
            return;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.CORRUPT_SWORD)) {
            if (Math.random() >= 0.0015 * level * 2.0) {
                return;
            }
        }
        else if (Math.random() >= 0.0015 * level) {
            return;
        }
        pTarget.sendMessage(CC.translate("&4&l* NEUTRALIZED &7[" + user.getName() + "&7]&4&l *"));
        user.sendMessage(CC.translate("&5&l* NEUTRALIZED &7[" + pTarget.getName() + "&7]&5&l *"));
        DamageIndicatorUtils.sendDamageIndicator((Player) pTarget, CC.DarkPurpleB + "* NEUTRALIZED" + CC.Gray +  "[5s]" + CC.Gray + " *", "NEUTRALIZE");
        pTarget.setMetadata("neutralized", new FixedMetadataValue((Plugin)Enchantments.getInstance(), "true"));
        Enchantments.getInstance().getServer().getScheduler().runTaskLaterAsynchronously((Plugin)Enchantments.getInstance(), () -> pTarget.removeMetadata("neutralized", (Plugin)Enchantments.getInstance()), 10L);
    }
}
