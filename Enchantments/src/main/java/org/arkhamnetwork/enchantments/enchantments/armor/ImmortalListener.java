package org.arkhamnetwork.enchantments.enchantments.armor;

import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.cosmicpvp.cosmicutils.utils.UpgradeUtils;
import com.rit.sucy.EnchantmentAPI;
import me.ifamasssxd.factionupgrades.FactionUpgradeAPI;
import me.ifamasssxd.factionupgrades.struct.FactionUpgrade;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.ArmorSlot;
import org.arkhamnetwork.enchantments.util.ArmorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class ImmortalListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(final EntityDamageEvent e) throws Exception {
        if (e.getEntity() instanceof Player) {
            final Player p = (Player) e.getEntity();
            if (CosmicOutpostsAPI.getOutpostTierOfWorld((LivingEntity) p) == OutpostTier.COSMONAUT) {
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
                                soulCost *= (int) ((heroic == 1) ? 0.7 : ((heroic == 2) ? 0.55 : 1.0));
                            } else {
                                final int perkLevel = FactionUpgradeAPI.getPerkLevel(p, FactionUpgrade.SOUL_MASTER);
                                if (perkLevel == 1) {
                                    soulCost *= (int) 0.95;
                                }
                                if (perkLevel == 2) {
                                    soulCost *= (int) 0.9;
                                }
                                if (perkLevel == 3) {
                                    soulCost *= (int) 0.75;
                                }
                            }
                        } catch (Exception ex) {
                        }
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
                            final EntityDamageByEntityEvent ed = (EntityDamageByEntityEvent) e;
                            if (ed.getDamager() instanceof Player) {
                                final Player pAttacker = (Player) ed.getDamager();
                                final ArmorSlot slot = ArmorSlot.getArmorType(i);
                                if (slot != null) {
                                    ArmorUtil.modifyPlayerArmor(pAttacker, slot, -1);
                                }
                            }
                        }
                        return;
                    } else if (!p.hasMetadata("outOfSoulsMessage") || p.getMetadata("outOfSoulsMessage").get(0).asLong() < System.currentTimeMillis()) {
                        de.inventivegames.particle.ParticleEffect.LAVA.sendToPlayers(Bukkit.getOnlinePlayers(),p.getEyeLocation(), 0,1,0,0.55f,20);

                        p.playSound(p.getLocation(), Sound.ITEM_BREAK, 0.7f, 0.4f);
                        p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "** OUT OF SOULS **");
                        p.setMetadata("outOfSoulsMessage", (MetadataValue) new FixedMetadataValue((Plugin) Enchantments.getInstance(), (Object) (System.currentTimeMillis() + 15000L)));
                        return;
                    }
                }
            }
        }
    }
}
