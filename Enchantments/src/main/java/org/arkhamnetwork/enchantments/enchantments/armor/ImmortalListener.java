package org.arkhamnetwork.enchantments.enchantments.armor;

import com.compilex.CosmicOutposts.*;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.bukkit.entity.*;
import com.compilex.CosmicOutposts.outpost.enums.*;
import com.rit.sucy.*;
import com.cosmicpvp.cosmicutils.utils.*;
import me.ifamasssxd.factionupgrades.struct.*;
import me.ifamasssxd.factionupgrades.*;
import me.ifamasssxd.killtracker.*;
import org.arkhamnetwork.enchantments.util.*;
import org.bukkit.event.entity.*;
import de.inventivegames.particle.*;
import org.bukkit.*;
import org.arkhamnetwork.enchantments.*;
import org.bukkit.metadata.*;
import org.bukkit.plugin.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;

final class ImmortalListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(final EntityDamageEvent e) throws Exception {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player)e.getEntity();
            if (CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity)p) == OutpostTier.COSMONAUT) {
                return;
            }
            for (final ItemStack i : p.getInventory().getArmorContents()) {
                if (i != null && EnchantmentAPI.itemHasEnchantment(i, "Immortal")) {
                    final int enchLevel = EnchantmentAPI.getEnchantments(i).get(EnchantmentAPI.getEnchantment("Immortal"));
                    int soulCost = 5 - enchLevel;
                    if (Immortal.cosmicOutposts && CosmicOutpostsAPI.controlsCosmonautOutpost(p)) {
                        soulCost /= 2;
                    }
                    if (Immortal.factionUpgrades) {
                        try {
                            final int heroic = UpgradeUtils.getUpgradeLevel(p, "HEROIC_SOUL_MASTERY");
                            if (heroic > 0) {
                                soulCost *= (int)((heroic == 1) ? 0.7 : ((heroic == 2) ? 0.55 : 1.0));
                            }
                            else {
                                final int perkLevel = FactionUpgradeAPI.getPerkLevel(p, FactionUpgrade.SOUL_MASTER);
                                if (perkLevel == 1) {
                                    soulCost *= 0;
                                }
                                if (perkLevel == 2) {
                                    soulCost *= 0;
                                }
                                if (perkLevel == 3) {
                                    soulCost *= 0;
                                }
                            }
                        }
                        catch (Exception ex) {}
                    }
                    soulCost = Math.max(1, soulCost);
                    if (KillTrackerAPI.hasSoulAmount(p, soulCost)) {
                        if (p.hasMetadata("soulTrap") && p.getMetadata("soulTrap").get(0).asLong() > System.currentTimeMillis()) {
                            return;
                        }
                        KillTrackerAPI.removeSoulsFromGems(p, soulCost);
                        if (KillTrackerAPI.getAllSouls(p) % 20 == 0) {
                            p.sendMessage("");
                            p.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "** IMMORTAL **");
                            p.sendMessage(ChatColor.GRAY + "You have " + ChatColor.UNDERLINE + KillTrackerAPI.getAllSouls(p) + ChatColor.GRAY + " souls left.");
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.65f);
                            p.sendMessage("");
                        }
                        ArmorUtil.modifyPlayerArmor(p, ArmorSlot.HELMET, 2);
                        ArmorUtil.modifyPlayerArmor(p, ArmorSlot.CHESTPLATE, 2);
                        ArmorUtil.modifyPlayerArmor(p, ArmorSlot.LEGGINGS, 2);
                        ArmorUtil.modifyPlayerArmor(p, ArmorSlot.BOOTS, 2);
                        if (e instanceof EntityDamageByEntityEvent) {
                            final EntityDamageByEntityEvent ed = (EntityDamageByEntityEvent)e;
                            if (ed.getDamager() instanceof Player) {
                                final Player pAttacker = (Player)ed.getDamager();
                                final ArmorSlot slot = ArmorSlot.getArmorType(i);
                                if (slot != null) {
                                    ArmorUtil.modifyPlayerArmor(pAttacker, slot, -1);
                                }
                            }
                        }
                        return;
                    }
                    else if (!p.hasMetadata("outOfSoulsMessage") || p.getMetadata("outOfSoulsMessage").get(0).asLong() < System.currentTimeMillis()) {
                        p.playSound(p.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.4f);
                        p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** OUT OF SOULS **");
                        p.setMetadata("outOfSoulsMessage", (MetadataValue)new FixedMetadataValue((Plugin)Enchantments.getInstance(), (Object)(System.currentTimeMillis() + 15000L)));
                        return;
                    }
                }
            }
        }
    }
}
