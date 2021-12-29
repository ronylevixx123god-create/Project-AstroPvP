package org.arkhamnetwork.enchantments.enchantments.heroic;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class HeroicValor extends HeroicCustomEnchantment{
    public HeroicValor() {
        super("Martry Valor","Valor", GeneralUtil.armor, 2);
        this.max = 5;
        this.base = 20.0D;
        this.interval = 5.0D;
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (!(player instanceof Player)) {
            return;
        }
        if (player.hasMetadata("noDefenseProcs"))
            return;
        double damage_negation = (double)level * 0.025 + 0.075;
        double hp_threshold = level * 3 + 6;
        if (player.getHealth() <= hp_threshold  && e.getDamage() * (1.0 - damage_negation) > 1.0) {
            e.setDamage(e.getDamage() * (1.0 - damage_negation));
            Location l = player.getLocation();
            WorldUtil.sendWorldEffect((Block)l.getBlock(), (int)41, (int)2001);
        }
    }
}
