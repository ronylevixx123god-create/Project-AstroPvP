package org.arkhamnetwork.enchantments.enchantments.heroic;

import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import net.astropvp.damagehandler.AstroDamageHandler;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.arkhamnetwork.enchantments.enchantments.axes.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.inventory.*;
import org.bukkit.*;

public class HeroicArmored extends HeroicCustomEnchantment
{
    public HeroicArmored() {
        super("Paladin Armored", "Armored");
    }

    public void applyDefenseEffect(final LivingEntity player, final LivingEntity attacker, final int level, final EntityDamageEvent e) {
        if (!(player instanceof Player)) {
            return;
        }
        if (player.hasMetadata("neutralized")) {
            return;
        }
        ItemStack i = null;
        if (attacker instanceof Player) {
            final Player pAttacker = (Player)attacker;
            if (AstroItemSkinsAPI.hasSkinEquipped(pAttacker, SkinType.VALENTINES_2021_BELT)) {
                return;
            }
            i = pAttacker.getItemInHand();
            if (Math.random() < level * 0.01) {
                Blessed.blessPlayer((Player)player, false);
                Bukkit.getLogger().info("Blessing " + ((Player)player).getName() + " from level " + level + " heroic armored!");
            }
        }
        final double damage_negation = level * 0.01;
        if (i != null && i.getType().name().contains("_SWORD") && e.getDamage() * (1.0 - damage_negation) > 1.0) {
            AstroDamageHandler.getInstance().getDamageHandler().reduceDamage((double)level, e, "&6&lArmored");
            final Location l = player.getLocation();
            WorldUtil.sendWorldEffect(l.getBlock(), 57, 2001);
        }
    }
}
