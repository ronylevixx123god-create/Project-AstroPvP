package org.arkhamnetwork.enchantments.enchantments.bow;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.cosmicpvp.cosmicutils.utils.CC;
import me.ifamasssxd.cosmicmasks.MaskType;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.DamageIndicatorUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.Map;

class TeleblockListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent e) {
        if (e.getDamager().hasMetadata("teleblockEnchantment") && e.getDamager() instanceof Projectile) {
            final int teleblockLevel = e.getDamager().getMetadata("teleblockEnchantment").get(0).asInt();
            if (e.getEntity() instanceof Player) {
                final Player pHurt = (Player) e.getEntity();
                if (CosmicArmorSetsAPI.getArmorSet(pHurt) == ArmorSet.RANGER) {
                    return;
                }
                if (MaskType.hasEquipped(pHurt, "GLITCH")) {
                    return;
                }
                final Map<ArmorSet, Integer> equipped = (Map<ArmorSet, Integer>) CosmicArmorSetsAPI.getBonusCrystalsEquipped(pHurt);
                final Integer crystalCount = equipped.get(ArmorSet.RANGER);
                if (crystalCount != null && Math.random() < 0.2 * crystalCount) {
                    pHurt.sendMessage(CC.GreenB + "* RANGER CRYSTAL [" + CC.Gray + "Immune to Teleblock" + CC.GreenB + "] *");
                    return;
                }
                pHurt.setMetadata("teleblock", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + (5 + teleblockLevel * 3000))));
                final PlayerInventory pi = pHurt.getInventory();
                int pearlsToDestroy = teleblockLevel * 3;
                for (int i = 0; i < pi.getSize(); ++i) {
                    if (pi.getItem(i) != null && pi.getItem(i).getType() == Material.ENDER_PEARL && (!pi.getItem(i).hasItemMeta() || !pi.getItem(i).getItemMeta().hasDisplayName())) {
                        final int amount = pi.getItem(i).getAmount();
                        if (amount <= pearlsToDestroy) {
                            pearlsToDestroy -= amount;
                            pi.setItem(i, new ItemStack(Material.AIR));
                        } else {
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
                DamageIndicatorUtils.sendDamageIndicator((Player) e.getEntity(), CC.DarkPurpleB + ChatColor.DARK_PURPLE + "* TELEBLOCK [" + 5 + teleblockLevel * 3 + "s] [-" + pearlsToDestroy + "ep] *", "TELEBLOCK");
                pHurt.sendMessage(ChatColor.DARK_PURPLE + "** TELEBLOCK [" + 5 + teleblockLevel * 3 + "s] [-" + pearlsToDestroy + "ep] **");
                pHurt.playSound(pHurt.getLocation(), Sound.ENDERMAN_HIT, 0.75f, 0.6f);
            }
        }
    }
}
