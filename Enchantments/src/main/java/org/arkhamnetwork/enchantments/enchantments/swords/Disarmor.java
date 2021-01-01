package org.arkhamnetwork.enchantments.enchantments.swords;

import com.rit.sucy.*;
import org.arkhamnetwork.enchantments.util.*;
import org.arkhamnetwork.enchantments.util.GeneralUtil;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import org.bukkit.metadata.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.bukkit.block.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.*;

public class Disarmor extends CustomEnchantment
{
    public Disarmor() {
        super("Disarmor", GeneralUtil.swords, 2);
        this.max = 8;
        this.base = 15.0;
        this.interval = 3.0;
    }
    
    public void applyEffect(final LivingEntity player, final LivingEntity target, final int level, final EntityDamageByEntityEvent e) {
        if (!(target instanceof Player)) {
            return;
        }
        if (target.getHealth() > 10.0) {
            return;
        }
        final double random = Math.random();
        double chance = 0.0025 * level;
        if (target.hasMetadata("stickyEnchant")) {
            final int stickyLevel = target.getMetadata("stickyEnchant").get(0).asInt();
            chance -= stickyLevel * 0.0025;
        }
        if (random < chance) {
            this.unequipRandomArmor((Player)target, (Player)player);
            final Location l = target.getLocation();
            l.getWorld().playSound(l, Sound.ANVIL_BREAK, 1.0f, 0.6f);
            WorldUtil.sendWorldEffect(l.getBlock(), Material.DIAMOND_ORE.getId(), 2001);
            WorldUtil.sendWorldEffect(l.getBlock().getRelative(BlockFace.UP), Material.DIAMOND_ORE.getId(), 2001);
            WorldUtil.sendWorldEffect(l.getBlock().getRelative(BlockFace.UP, 2), Material.DIAMOND_ORE.getId(), 2001);
        }
    }
    
    private void unequipRandomArmor(final Player p, final Player playerDisarming) {
        final int i = new Random().nextInt(4);
        ItemStack armor = null;
        if (i == 1) {
            armor = p.getEquipment().getHelmet();
            p.getEquipment().setHelmet((ItemStack)null);
        }
        else if (i == 2) {
            armor = p.getEquipment().getChestplate();
            p.getEquipment().setChestplate((ItemStack)null);
        }
        else if (i == 3) {
            armor = p.getEquipment().getLeggings();
            p.getEquipment().setLeggings((ItemStack)null);
        }
        else if (i == 4) {
            armor = p.getEquipment().getBoots();
            p.getEquipment().setBoots((ItemStack)null);
        }
        if (armor != null) {
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(new ItemStack[] { armor });
            }
            else {
                p.getWorld().dropItem(p.getTargetBlock((HashSet)null, 1).getLocation(), armor);
            }
        }
        p.sendMessage("");
        p.sendMessage("");
        p.sendMessage("");
        p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "* DISARMORED *" + ChatColor.GRAY + " (by: " + playerDisarming.getName() + ")");
        p.sendMessage(ChatColor.GRAY + "A random piece of your armor has been unequipped. Re-equip it quickly before you get KO'd!");
        p.sendMessage("");
        p.sendMessage("");
        p.sendMessage("");
        playerDisarming.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "* DISARMED " + p.getName() + " *");
    }
}
