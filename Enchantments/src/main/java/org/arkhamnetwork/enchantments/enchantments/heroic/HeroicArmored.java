package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.enchantments.axes.Blessed;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class HeroicArmored extends HeroicCustomEnchantment {
    public HeroicArmored() {
        super("Paladin Armored", "Armored");
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(player instanceof Player))
            return;
        if (player.hasMetadata("neutralized"))
            return;
        ItemStack i = null;
        if (attacker instanceof Player) {
            Player pAttacker = (Player) attacker;

            if (AstroItemSkinsAPI.hasSkinEquipped(pAttacker, SkinType.VALENTINES_2021_BELT)) {
                return;
            }
            i = pAttacker.getItemInHand();
            if (Math.random() < level * 0.01D) {
                Blessed.blessPlayer((Player)player);
            }
        }
        double damage_negation = level * 0.02D;
        if(AstroItemSkinsAPI.hasSkinEquipped((Player) attacker, SkinType.VALENTINES_2021_BELT))
            damage_negation = level * 0.01875D;
        if (i != null && i.getType().name().contains("_SWORD") && e
                .getDamage() * (1.0D - damage_negation) > 1.0D) {
            e.setDamage(e.getDamage() * (1.0D - damage_negation));
            Location l = player.getLocation();
        }
    }
}
