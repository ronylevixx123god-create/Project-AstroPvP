package org.arkhamnetwork.enchantments.enchantments.armor;

import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.ConcurrentHashMap;

public class Cactus extends CustomEnchantment {
    protected static ConcurrentHashMap<String, Integer> cactus;

    public Cactus() {
        super("Cactus", GeneralUtil.armor, 2);
        this.max = 2;
        this.base = 20.0D;
        this.interval = 5.0D;
        cactus = new ConcurrentHashMap<>();
        Bukkit.getPluginManager().registerEvents(new CactusListener(), (Plugin) Enchantments.getInstance());
    }


    public void applyUnequipEffect(Player player, int enchantLevel) {
        cactus.remove(player.getName());
    }

    public void applyDefenseEffect(LivingEntity player, LivingEntity attacker, int level, EntityDamageEvent e) {
        if (player.hasMetadata("neutralized"))
            return;
        if (player instanceof Player) {
            if (attacker instanceof Player) {
                Player pAttacker = (Player) attacker;
                pAttacker.damage(0.5D * level);
            }
        }
    }
}
