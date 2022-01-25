package net.astropvp.core.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.MaskUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class DamageFixes implements Listener {


/*    @EventHandler(priority = EventPriority.LOWEST)
    public void onHitP6(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player phurt = (Player) e.getEntity();

            e.setDamage(e.getDamage() * (1 - getProtectionReduction(phurt)));
        }
    }*/

    @EventHandler(priority = EventPriority.HIGHEST)
    public void damageCap(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            if (e.getFinalDamage() > 22) {
                e.setDamage(22);
            }
        }
    }

    @EventHandler()
    public void elfMaskReHeal(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player phurt = (Player) e.getEntity();
            if (!(phurt.getHealth() <= 10)) return;
            if (MaskUtils.hasMaskEquipped(phurt, "ELF") && Math.random() <= 0.15) {
                e.getEntity().sendMessage(CC.translate("&a&l * ELF MASK HEAL ABILITY &a&l*"));
                e.setCancelled(true);
                ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getMaxHealth());

            }
        }
        
    }


    public double getProtectionReduction(Player p) {
        double decrease = 0;
        for (ItemStack armorPiece : p.getInventory().getArmorContents()) {
            if (armorPiece == null) continue;
            if (armorPiece.getType() == Material.AIR) continue;
            if (armorPiece.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                int protLevel = armorPiece.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                if (protLevel == 6) decrease += 2;
                if (protLevel == 5) decrease += 1;
            }
        }
        return decrease * 0.01;
    }
}
