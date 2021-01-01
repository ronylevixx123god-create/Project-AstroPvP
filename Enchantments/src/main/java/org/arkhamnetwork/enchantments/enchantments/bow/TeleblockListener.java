package org.arkhamnetwork.enchantments.enchantments.bow;

import me.ifamasssxd.cosmicmasks.MaskType;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import com.compilex.CosmicArmorSets.*;
import com.compilex.CosmicArmorSets.enums.*;
import com.cosmicpvp.cosmicutils.utils.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import org.bukkit.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;

class TeleblockListener implements Listener
{
    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent e) {
        if (e.getDamager().hasMetadata("teleblockEnchantment") && e.getDamager() instanceof Projectile) {
            final int teleblockLevel = e.getDamager().getMetadata("teleblockEnchantment").get(0).asInt();
            if (e.getEntity() instanceof Player) {
                final Player pHurt = (Player)e.getEntity();
                if (CosmicArmorSetsAPI.getArmorSet(pHurt) == ArmorSet.RANGER) {
                    return;
                }
                if (MaskType.hasEquipped(pHurt, "GLITCH")) {
                    return;
                }
                final Map<ArmorSet, Integer> equipped = (Map<ArmorSet, Integer>)CosmicArmorSetsAPI.getBonusCrystalsEquipped(pHurt);
                final Integer crystalCount = equipped.get(ArmorSet.RANGER);
                if (crystalCount != null && Math.random() < 0.2 * crystalCount) {
                    pHurt.sendMessage(CC.GreenB + "* RANGER CRYSTAL [" + CC.Gray + "Immune to Teleblock" + CC.GreenB + "] *");
                    return;
                }
                pHurt.setMetadata("teleblock", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(System.currentTimeMillis() + (5 + teleblockLevel * 3000))));
                final PlayerInventory pi = pHurt.getInventory();
                int pearlsToDestroy = teleblockLevel * 3;
                for (int i = 0; i < pi.getSize(); ++i) {
                    if (pi.getItem(i) != null && pi.getItem(i).getType() == Material.ENDER_PEARL && (!pi.getItem(i).hasItemMeta() || !pi.getItem(i).getItemMeta().hasDisplayName())) {
                        final int amount = pi.getItem(i).getAmount();
                        if (amount <= pearlsToDestroy) {
                            pearlsToDestroy -= amount;
                            pi.setItem(i, new ItemStack(Material.AIR));
                        }
                        else {
                            final ItemStack invPearl = pi.getItem(i);
                            invPearl.setAmount(invPearl.getAmount() - pearlsToDestroy);
                            pi.setItem(i, invPearl);
                            pearlsToDestroy = 0;
                        }
                    }
                    if (pearlsToDestroy <= 0) {
                        break;
                    }
                }
                pHurt.sendMessage(ChatColor.DARK_PURPLE + "** TELEBLOCK [" + 5 + teleblockLevel * 3 + "s] [-" + pearlsToDestroy + "ep] **");
                pHurt.playSound(pHurt.getLocation(), Sound.ENDERMAN_HIT, 0.75f, 0.6f);
            }
        }
    }
}
