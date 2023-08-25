package org.arkhamnetwork.enchantments.mastery;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.massivecraft.factions.perms.Relation;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import java.util.*;
import org.bukkit.inventory.*;
import com.rit.sucy.*;

public class MasteryBlackout extends MasteryCustomEnchantment
{
    public MasteryBlackout() {
        super("Blackout", GeneralUtil.swords, 2);
        this.max = 4;
        this.base = 25.0;
        this.interval = 10.0;
    }

    public void applyEffect(final LivingEntity player, final LivingEntity target, final int level, final EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }

        final Player pTarget = (Player)target;
        final Relation relation = FactionUtils.getRelationTo(FactionUtils.getFaction((Player)player), FactionUtils.getFaction(pTarget));
        if (relation != Relation.NEUTRAL && relation != Relation.ENEMY) {
            return;
        }
        if (Math.random() <= 0.06 * (double)level / 4.0) {
            procBlackout((Player) player, pTarget, level);
        }
    }

    private void procBlackout(final Player player, final Player pTarget, final int level) {
        if (pTarget.hasMetadata("blackout")) {
            return;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped((Player)pTarget, (SkinType)SkinType.VIRUS_BELT) && Math.random() < 0.5) {
            pTarget.sendMessage("§2§l* BLOCKED " + ((Player)player).getName() + "'s BLACKOUT §7(First Aid Fannypack) §2§l*");
            ((Player)player).sendMessage(CC.translate((String)("&7&l* &7&l&mBLACKOUT&r &7[Blocked by " + pTarget.getName() + "'s First Aid Fannypack] &7&l*")));
            return;
        }

        if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.VIRUS_BELT) && Math.random() < 0.5) {
            ((Player)player).sendMessage(CC.translate("&4&l* BLACKOUT BLOCKED * &7[&7" + pTarget.getName() + "'s &a&lFirst Aid Fannypack&7]"));
            return;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.CHRISTMAS_BELT_CRYSTALINE) && Math.random() < 0.2) {
            player.sendMessage(CC.translate("&4&l* BLACKOUT BLOCKED * &7[" + pTarget.getName() + "'s &c&lCrystalline Band&7]"));
            return;
        }
        if (player.hasMetadata("BlackoutCooldown") && System.currentTimeMillis() - player.getMetadata("BlackoutCooldown").get(0).asLong() <= 30000L) {
            return;
        }
        player.setMetadata("BlackoutCooldown", new FixedMetadataValue((Plugin)Enchantments.getInstance(), System.currentTimeMillis()));
        long durationInTicks = level * 20;
        if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.CHRISTMAS_HELMET_STAR)) {
            durationInTicks *= (long)0.66;
        }
        if (AstroItemSkinsAPI.hasSkinEquipped(pTarget, SkinType.BACK_TO_SCHOOL_2021_HELMET)) {
            durationInTicks *= 0.4;

        }
        pTarget.playSound(pTarget.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
        DamageIndicatorUtils.sendDamageIndicator(pTarget, "&5&l* BLACKOUT *", "blackout");
        player.sendMessage(CC.translate("&5&l* BLACKOUT &5[" + (int)(durationInTicks / 20L) + "&5s on &e" + pTarget.getName() + "&5] &5&l*"));
        pTarget.sendMessage(CC.translate("&5&L* BLACKOUT &7[" + durationInTicks/20 + "&7s] &5&l *"));
        pTarget.setMetadata("blackout", new FixedMetadataValue((Plugin)Enchantments.getInstance(), "banana"));
        Enchantments.getInstance().getServer().getScheduler().runTaskLaterAsynchronously((Plugin)Enchantments.getInstance(), () -> pTarget.removeMetadata("blackout", (Plugin)Enchantments.getInstance()), AstroItemSkinsAPI.hasSkinEquipped((Player)player, SkinType.CORRUPT_SWORD) ? (durationInTicks * 2L) : durationInTicks);    }

    private int getLevel(final ItemStack piece, final String name) {
        return EnchantmentAPI.getEnchantments(piece).get(EnchantmentAPI.getEnchantments(piece).keySet().stream().filter(e -> e.name().equals(name)).findFirst().get());
    }
}
