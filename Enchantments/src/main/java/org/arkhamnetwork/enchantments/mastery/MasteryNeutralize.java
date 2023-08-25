package org.arkhamnetwork.enchantments.mastery;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Sound;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.plugin.*;
import org.bukkit.metadata.*;

import java.util.concurrent.ThreadLocalRandom;

public class MasteryNeutralize extends MasteryCustomEnchantment
{
    public MasteryNeutralize() {
        super("Neutralize", GeneralUtil.weapons, 3);
        this.max = 5;
        this.base = 25.0;
        this.interval = 10.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity target, final int level, final EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        Player user = (Player)player;
        Player pTarget = (Player)target;
        int multi = 1;
        try {
            multi = AstroItemSkinsAPI.hasSkinEquipped((Player)user, (SkinType)SkinType.CORRUPT_SWORD) ? 2 : 1;
        }
        catch (Exception exception) {
            // empty catch block
        }
        if ((7 + level) * multi >= ThreadLocalRandom.current().nextInt(100)) {
            if (AstroItemSkinsAPI.hasSkinEquipped((Player)pTarget, (SkinType)SkinType.RUINS_AMULET) && Math.random() < 0.5) {
                pTarget.sendMessage("§5§l* BLOCKED " + user.getName() + "'s NEUTRALIZE §7(Lost Amulet) §5§l*");
                return;
            }
            pTarget.sendMessage(CC.translate((String)("&5&l* NEUTRALIZED &7[" + user.getName() + "&7]&5&l *")));
            if (!pTarget.hasMetadata("noDefenseProcs")) {
                pTarget.setMetadata("noDefenseProcs", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)"banana"));
            } else {
                pTarget.setMetadata("silenced", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)"banana"));
            }
            pTarget.playSound(pTarget.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.3f);
            user.sendMessage(CC.translate("&5&l* NEUTRALIZED &7[" + pTarget.getName() + "&7]&5&l *"));
            DamageIndicatorUtils.sendDamageIndicator((Player) pTarget, CC.DarkPurpleB + "* NEUTRALIZED" + CC.Gray +  "[5s]" + CC.Gray + " *", "NEUTRALIZE");
            pTarget.setMetadata("neutralized", new FixedMetadataValue((Plugin)Enchantments.getInstance(), "true"));
            Enchantments.getInstance().getServer().getScheduler().runTaskLaterAsynchronously((Plugin)Enchantments.getInstance(), () -> pTarget.removeMetadata("neutralized", (Plugin)Enchantments.getInstance()), 20L * 4);        }
    }
}


