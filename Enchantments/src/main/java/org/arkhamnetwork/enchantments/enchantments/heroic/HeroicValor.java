package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import lombok.SneakyThrows;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import org.arkhamnetwork.enchantments.libs.PlayerPotionEffects;
import org.arkhamnetwork.enchantments.libs.SpecificCustomEnchantment;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HeroicValor extends HeroicCustomEnchantment {
    public HeroicValor() {
        super("Martyr Valor", "Valor", GeneralUtil.armor, 2);
        this.max = 5;
    }

    @SneakyThrows
    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(player instanceof Player))
            return;
        if (AstroItemSkinsAPI.hasSkinEquipped((Player) player, SkinType.VALENTINES_2021_SWORD)) {
            level = 5;
        }
        double damage_negation = level * 0.01D;
        double hp_threshold = (level * 5 + 6);
        if (player.getHealth() <= hp_threshold && (((Player) player).getItemInHand().getType() == Material.GOLD_SWORD || ((Player) player).getItemInHand().getType() == Material.DIAMOND_SWORD) && e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0D - damage_negation) > 1.0D) {
            e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0D - damage_negation));
        }
    }
}
