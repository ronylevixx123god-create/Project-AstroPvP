package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.rit.sucy.CustomEnchantment;
import javafx.scene.control.Skin;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.arkhamnetwork.enchantments.util.GeneralCooldown;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class MasteryBlackout extends CustomEnchantment {
    public MasteryBlackout() {
        super("Blackout", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 25.0D;
        this.interval = 10.0D;
    }


    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (!(target instanceof Player)) {
            return;
        }
        if (target.hasMetadata("blackout")) {
            return;
        }

        Player pTarget = (Player) target;



        if (AstroItemSkinsAPI.hasSkinEquipped(((Player) user).getPlayer(), SkinType.SUMMER_2021_SWORD)) {
            enchantLevel = 5;
        }

        if (Math.random() <= 0.002D * enchantLevel) {
            int multi = AstroItemSkinsAPI.hasSkinEquipped((Player) user, SkinType.CORRUPT_SWORD) ? 2 : 1;
            if (AstroItemSkinsAPI.hasSkinEquipped(pTarget.getPlayer(), SkinType.CHRISTMAS_HELMET_STAR)) {
                multi = 1;
            }
            if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.VIRUS_BELT) && Math.random() < 0.50) {
                pTarget.sendMessage(CC.translate("&2&l* BLOCKED ") + ((Player) user).getName() + CC.translate("'s BLACKOUT &7(First Aid Fannypack) &2&l*"));
                ((Player) user).sendMessage(CC.translate("&7&l* &7&l&mBLACKOUT&r &7[Blocked by " + pTarget.getName() + "'s First Aid Fannypack] &7&l*"));
                return;
            }
            pTarget.playSound(pTarget.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
            String text = CC.DarkPurpleB + "* BLACKOUT " + CC.Gray + "[" + (2 + enchantLevel) * multi + "s]" + CC.DarkPurpleB + " *";
            DamageIndicatorUtils.sendDamageIndicator((Player) pTarget, text, "BLACKOUT");
            pTarget.sendMessage(CC.translate("&5&L* BLACKOUT &7[" + (2 + enchantLevel) * multi + "&7s] &5&l *"));
            user.sendMessage(CC.translate(text));
            pTarget.setMetadata("blackout", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), "banana"));
            Enchantments.getInstance().getServer().getScheduler().runTaskLaterAsynchronously((Plugin) Enchantments.getInstance(), () -> target.removeMetadata("blackout", (Plugin) Enchantments.getInstance()), ((4 + enchantLevel * multi) * 20));
        }
    }


}