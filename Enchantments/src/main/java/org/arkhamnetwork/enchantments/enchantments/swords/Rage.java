package org.arkhamnetwork.enchantments.enchantments.swords;

import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.RageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Rage extends CustomEnchantment {
    public Rage() {
        super("Rage", GeneralUtil.weapons, 2);
        this.max = 6;
        this.base = 10.0D;
        this.interval = 8.0D;
        Bukkit.getPluginManager().registerEvents(new RageListener(), Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (target instanceof ArmorStand) {
            return;
        }
        if (event.isCancelled()) return;
        if (!WorldGuardUtils.isPvPEnabled(user.getLocation())) return;
        if (!WorldGuardUtils.isPvPEnabled(target.getLocation())) return;

        if (event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0D && user instanceof Player) {
            Player pUser = (Player) user;
            double hitCombo = RageUtils.getComboOfPlayer(pUser, target instanceof Player);

            if (hitCombo > 8D) {
                hitCombo = 8D;
            }

            if (hitCombo > 1.0D) {
                event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0 + (hitCombo/20)));
                playRageEffect(target.getEyeLocation(), enchantLevel);
            }
        }

    }

    private void playRageEffect(Location loc, int level) {
        WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
    }

}
