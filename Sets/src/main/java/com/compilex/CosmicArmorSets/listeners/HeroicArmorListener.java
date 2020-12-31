/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.compilex.CosmicOutposts.outpost.enums.OutpostTier
 *  com.cosmicpvp.cosmicutils.CosmicUtils
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  me.ifamasssxd.cosmichcf.api.HCFOutpostAPI
 *  me.ifamasssxd.cosmichcf.struct.OutpostType
 *  net.minecraft.server.v1_7_R4.NBTBase
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.EntityDamageEvent$DamageModifier
 *  org.bukkit.event.inventory.CraftItemEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 */
package com.compilex.CosmicArmorSets.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.HeroicArmorType;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import net.minecraft.server.v1_8_R3.NBTBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class HeroicArmorListener
        implements Listener {
    @EventHandler
    public void onPlayerCraftItem(CraftItemEvent event) {
        if (event.getInventory() != null && event.getRecipe() != null && event.getRecipe().getResult() != null) {
            Material type = event.getRecipe().getResult().getType();
            if (type != null && (type == Material.GOLD_AXE || type == Material.GOLD_SWORD)) {
                event.setCancelled(true);
                if (event.getWhoClicked() instanceof Player) {
                    ((Player) event.getWhoClicked()).sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot craft this weapon!");
                }
            } else if (type != null && (type == Material.LEATHER_HELMET || type == Material.LEATHER_CHESTPLATE || type == Material.LEATHER_LEGGINGS || type == Material.LEATHER_BOOTS) && event.getWhoClicked().getInventory().contains(Material.INK_SACK) && event.getWhoClicked() instanceof Player && !event.getWhoClicked().isOp()) {
                event.setCancelled(true);
                ((Player) event.getWhoClicked()).sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot manually dye Leather Armor!");
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerClickedHeroic(InventoryClickEvent event) {
        ItemStack current;
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (event.getWhoClicked().getInventory() != null && player.getOpenInventory() != null && event.getWhoClicked().getInventory().equals((Object) player.getOpenInventory().getBottomInventory()) && (current = event.getCurrentItem()) != null) {
            Material type = current.getType();
            NBTWrapper wrapper = null;
            HeroicArmorType typeToCompare = null;
            if (type == Material.LEATHER_HELMET) {
                wrapper = new NBTWrapper(current);
                typeToCompare = HeroicArmorType.HELMET;
            } else if (type == Material.LEATHER_CHESTPLATE) {
                wrapper = new NBTWrapper(current);
                typeToCompare = HeroicArmorType.CHESTPLATE;
            } else if (type == Material.LEATHER_LEGGINGS) {
                wrapper = new NBTWrapper(current);
                typeToCompare = HeroicArmorType.LEGGINGS;
            } else if (type == Material.LEATHER_BOOTS) {
                wrapper = new NBTWrapper(current);
                typeToCompare = HeroicArmorType.BOOTS;
            }
            if (wrapper != null && wrapper.getString("cosmicType").equals("HEROIC_ARMOR")) {
                AbilityArmorSet set;
                NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"));
                int armorModifier = builder.getInt("armorModifier");
                String abilitySet = builder.getString("abilitySet");
                int requiredModifier = typeToCompare.getArmorModifier();
                if (abilitySet != null && !abilitySet.isEmpty() && (set = AbilityArmorSet.fromName(abilitySet)) != null) {
                    requiredModifier = (int) Math.ceil((double) requiredModifier / 2.0);
                }
                if (armorModifier != requiredModifier) {
                    Bukkit.getLogger().info("Updating outdated Heroic Armor with modifier: " + armorModifier + " new: " + typeToCompare.getArmorModifier() + " for " + player.getName());
                    builder.setInt("armorModifier", requiredModifier);
                    wrapper.setNBTBase("cosmicData", builder.get());
                    ItemStack item = wrapper.build();
                    ItemMeta im = item.getItemMeta();
                    List lore = im.getLore();
                    if (lore != null) {
                        for (int i = 0; i < lore.size(); ++i) {
                            String line = (String) lore.get(i);
                            if (!line.startsWith((CC.Gray + "+").intern()) || !line.endsWith("Armor Value")) continue;
                            lore.set(i, CC.Gray + "+" + requiredModifier + " Armor Value");
                            im.setLore(lore);
                            item.setItemMeta(im);
                            break;
                        }
                    }
                    event.setCurrentItem(item);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        Material type;
        if (event.getEntity() instanceof Player) {
            double damageReduction = 0.0;
            OutpostTier tier = CosmicOutpostsAPI.getOutpostTierOfWorld((Entity) event.getEntity());
            if (tier != null && (tier == OutpostTier.VANILLA || tier == OutpostTier.TRAINEE)) {
                return;
            }
            Player player = (Player) event.getEntity();
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item == null) continue;
                type = item.getType();
                if (type == Material.LEATHER_CHESTPLATE) {
                    if (!CosmicArmorSetsAPI.isHeroicArmor(item)) continue;
                    if (this.isMkitArmor(item)) {
                        damageReduction += 0.16;
                        continue;
                    }
                    damageReduction += 0.1;
                    continue;
                }
                if (type == Material.LEATHER_LEGGINGS) {
                    if (!CosmicArmorSetsAPI.isHeroicArmor(item)) continue;
                    if (this.isMkitArmor(item)) {
                        damageReduction += 0.13;
                        continue;
                    }
                    damageReduction += 0.08;
                    continue;
                }
                if (type != Material.LEATHER_BOOTS && type != Material.LEATHER_HELMET || !CosmicArmorSetsAPI.isHeroicArmor(item))
                    continue;
                if (this.isMkitArmor(item)) {
                    damageReduction += 0.08;
                    continue;
                }
                damageReduction += 0.045;
            }
            if (damageReduction > 0.0) {
                double reductionDamage = damageReduction * event.getDamage(EntityDamageEvent.DamageModifier.BASE);
                event.setDamage(Math.max(0.0, event.getDamage(EntityDamageEvent.DamageModifier.BASE) - reductionDamage));
            }
        }
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            double damageMod = 0.0;
            double damageIncrease = 0.0;
            if (CosmicUtils.get().isOutpostsEnabled() && CosmicOutpostsAPI.controlsTraineeOutpost((Player) ((Player) event.getDamager()))) {
                damageMod += 0.025;
            }
            if (HCFOutpostAPI.isControllingOutpost((Player) ((Player) event.getDamager()), (OutpostType) OutpostType.COURTYARD)) {
                damageMod += 0.0625;
            }
            for (ItemStack item : ((Player) event.getEntity()).getInventory().getArmorContents()) {
                if (item == null) continue;
                type = item.getType();
                if (type == Material.LEATHER_CHESTPLATE) {
                    if (!CosmicArmorSetsAPI.isHeroicArmor(item)) continue;
                    damageIncrease += damageMod;
                    continue;
                }
                if (type == Material.LEATHER_LEGGINGS) {
                    if (!CosmicArmorSetsAPI.isHeroicArmor(item)) continue;
                    damageIncrease += damageMod;
                    continue;
                }
                if (type != Material.LEATHER_BOOTS && type != Material.LEATHER_HELMET || !CosmicArmorSetsAPI.isHeroicArmor(item))
                    continue;
                damageIncrease += damageMod;
            }
            if (damageIncrease > 0.0) {
                event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * (1.0 + damageIncrease));
            }
        }
    }

    private boolean isMkitArmor(ItemStack item) {
        NBTTagCompoundBuilder builder = new NBTWrapper(item).getCompound("cosmicData");
        if (builder == null) {
            return false;
        }
        return builder.hasKey("abilitySet");
    }
}

