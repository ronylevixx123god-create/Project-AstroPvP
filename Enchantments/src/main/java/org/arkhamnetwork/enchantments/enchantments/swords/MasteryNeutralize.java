package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ThreadLocalRandom;

public class MasteryNeutralize extends CustomEnchantment {
    public MasteryNeutralize() {
        super("Neutralize", GeneralUtil.swords, 2);
        this.max = 5;
        this.base = 25.0D;
        this.interval = 10.0D;
    }


    public void applyEffect(LivingEntity player, LivingEntity target, int level, EntityDamageByEntityEvent e) {

        if (!(target instanceof Player))
            return;

        Player user = (Player) player;
        Player pTarget = (Player) target;


        int multi = 1;
        try {
            multi = AstroItemSkinsAPI.hasSkinEquipped(user, SkinType.CORRUPT_SWORD) ? 2 : 1;
        } catch (Exception ignored) {
        }


        if ((7 + level) * multi >= ThreadLocalRandom.current().nextInt(100)) {

            if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.LOST_AMULET) && Math.random() < 0.5) {
                pTarget.sendMessage("§5§l* BLOCKED " + ((Player) user).getName() + "'s NEUTRALIZE §7(Lost Amulet) §5§l*");
                return;
            }
            pTarget.sendMessage(CC.translate("&5&l* NEUTRALIZED &7[" + user.getName() + "&7]&5&l *"));
            if (!pTarget.hasMetadata("noDefenseProcs")) {
                pTarget.setMetadata("noDefenseProcs", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), "banana"));
            } else {
                pTarget.setMetadata("silenced", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), "banana"));
            }
            pTarget.playSound(pTarget.getLocation(), Sound.ENDERMAN_SCREAM, 1.0f, 0.3f);

            pTarget.setMetadata("neutralized", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), "banana"));
        }
    }
}