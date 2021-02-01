/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.compat.ASound
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.Lists
 *  com.google.common.collect.MapMaker
 *  me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.arkhamnetwork.enchantments.MasteryCustomEnchantment
 *  org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent
 *  org.arkhamnetwork.enchantments.events.PlayerAttemptTinkerItem
 *  org.arkhamnetwork.enchantments.libs.TinkeredItem
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.craftbukkit.libs.com.google.gson.internal.Pair
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicArmorSets.listeners;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.CosmicArmorSetsPlugin;
import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicArmorSets.enums.armorsets.DragonSlayerArmorSet;
import com.compilex.CosmicArmorSets.utils.Pair;
import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.enchantments.MasteryCustomEnchantment;
import org.arkhamnetwork.enchantments.events.CustomEnchantProcOnEntityEvent;
import org.arkhamnetwork.enchantments.events.PlayerAttemptTinkerItem;
import org.arkhamnetwork.enchantments.libs.TinkeredItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class ArmorSetCrystalTinkererListener implements Listener {
    private final ConcurrentMap<Object, Object> antiMkitsEquipped = CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.SECONDS).build().asMap();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerAttemptTinkerItem(PlayerAttemptTinkerItem event) {
        TinkeredItem item;
        if (event.getItem() != null && (item = event.getItem()).getOfferForItem() == null) {
            AbilityArmorSet fromArmor;
            ArmorSet set = ArmorSet.getArmorSetFromItem(item.getItem());
            if (set != null) {
                event.setCancelled(false);
                item.setXpValue(0.0);
                item.setOfferForItem(ArmorSet.createCrystal(Lists.newArrayList(set), 20.0));
            } else if (item.getItem() != null && item.getItem().getType() != Material.AIR && (fromArmor = AbilityArmorSet.getSetFromMasteryShard(item.getItem())) != null) {
                event.setCancelled(false);
                item.setXpValue(0.0);
                item.setOfferForItem(fromArmor.createAntiMkitShard(item.getItem().getAmount()));
            }
        }
    }

    @EventHandler
    public void onEnchantProc(CustomEnchantProcOnEntityEvent event) {
        if (event.getEnchant() != null && event.getTarget() instanceof Player) {
            String name = event.getEnchant().name();
            if (!name.equals("Silence") && !name.equals("IceAspect")) {
                Player target;
                Set equipped;
                String armorSetFrom;
                if (event.getEnchant() instanceof MasteryCustomEnchantment && event.getTarget() instanceof Player && (armorSetFrom = ((MasteryCustomEnchantment) event.getEnchant()).getArmorSet()) != null && !armorSetFrom.isEmpty() && (equipped = this.getAntiMkitsEquipped(target = (Player) event.getTarget())) != null && equipped.contains(armorSetFrom)) {
                    if (target.hasMetadata("revoked")) {
                        return;
                    }
                    String customName = event.getPlayer() instanceof Player ? event.getPlayer().getName() : event.getPlayer().getCustomName();
                    event.setCancelled(true);
                    CosmicArmorSetsPlugin.get_().getLogger().info("Cancelling " + event.getEnchant().name() + " against " + target.getName() + " from " + customName + " due to Anti M-Kit!");
                }
            } else {
                Map<ArmorSet, Integer> equipped = CosmicArmorSetsAPI.getBonusCrystalsEquipped((Player) event.getTarget());
                Integer dragonSlayer = equipped.get(ArmorSet.DRAGON_SLAYER);
                if (dragonSlayer != null) {
                    ((DragonSlayerArmorSet) ArmorSet.DRAGON_SLAYER.getArmorSetInfo()).onEnchantImmuneToProced((Player) event.getTarget(), (Player) event.getPlayer(), name, dragonSlayer, event);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClickShardsTogether(InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
        ItemStack cursor = event.getCursor();
        if (event.getWhoClicked().getInventory() != null && event.getInventory() != null && clicked != null && clicked.getType() != Material.AIR && cursor != null && cursor.getType() != Material.AIR && clicked.getType() == cursor.getType() && event.getWhoClicked().getInventory() != null && event.getWhoClicked().getOpenInventory() != null && event.getWhoClicked().getInventory().equals(event.getWhoClicked().getOpenInventory().getBottomInventory())) {
            Pair<Set<AbilityArmorSet>, Integer> cursorSet;
            NBTWrapper clickedWrap = new NBTWrapper(clicked);
            NBTWrapper cursorWrap = new NBTWrapper(cursor);
            Pair<Set<AbilityArmorSet>, Integer> clickSet = AbilityArmorSet.getFromAntiMkitCrystal(clicked);
            if (clickSet != null && (cursorSet = AbilityArmorSet.getFromAntiMkitCrystal(cursor)) != null) {
                Player player = (Player) event.getWhoClicked();
                if (!player.getWorld().equals(Bukkit.getWorlds().get(0))) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must combine these crystals in the Overworld!");
                } else {
                    event.setCancelled(true);
                    if (!(clickSet.getKey().stream().findFirst()).equals(cursorSet.getKey().stream().findFirst())) {
                        player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must combine Anti M-Kit Crystals of the same type!");
                    } else {
                        int newTotal = clickSet.getValue() * clicked.getAmount() + clickSet.getValue() * cursor.getAmount();
                        if (newTotal > 100) {
                            player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot combine an Anti M-Kit Crystal over 100% success rate!");
                        } else {
                            boolean cursorBlack = false;
                            if (!(clickedWrap.getIID() != -1L && ItemID.blacklist_iid.contains(clickedWrap.getIID()) || (cursorBlack = cursorWrap.getIID() != -1L && ItemID.blacklist_iid.contains(cursorWrap.getIID())))) {
                                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                                    if (clickedWrap.getIID() != -1L) {
                                        ItemID.blacklistIID(clickedWrap.getIID(), player.getName() + " Combined ");
                                    }
                                    if (cursorWrap.getIID() != -1L) {
                                        ItemID.blacklistIID(cursorWrap.getIID(), player.getName() + " Combined ");
                                    }
                                }
                                player.setItemOnCursor(null);
                                event.setCurrentItem(new CustomItemBuilder("ANTI_MKIT", newTotal, cursorSet.getValue()).build());
                                ASound.LEVEL_UP.play(player, 10.0f, 1.1f);
                                ASound.ORB_PICKUP.play(player, 10.0f, 1.1f);
                            } else {
                                player.sendMessage(CC.RedB + "(!) " + CC.Red + "Anti M-Kit crystal already redeemed!");
                                if (cursorBlack) {
                                    player.setItemOnCursor(null);
                                } else {
                                    event.setCurrentItem(null);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Set getAntiMkitsEquipped(Player player) {
        HashSet<String> found = (HashSet<String>) this.antiMkitsEquipped.get(player.getUniqueId());
        if (found == null) {
            found = new HashSet<String>();
            for (ItemStack armor : player.getInventory().getArmorContents()) {
                Set<AbilityArmorSet> fromArmor;
                if (armor == null || armor.getType() == Material.AIR || (fromArmor = AbilityArmorSet.getAntiMkitCrystal(armor)) == null)
                    continue;
                for (AbilityArmorSet set : fromArmor) {
                    found.add(set.name());
                }
            }
            this.antiMkitsEquipped.put(player.getUniqueId(), found);
        }
        return found;
    }
}

