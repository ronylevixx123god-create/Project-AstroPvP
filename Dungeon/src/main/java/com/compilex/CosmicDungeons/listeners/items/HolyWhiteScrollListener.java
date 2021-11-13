/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicArmorSets.CosmicArmorSetsAPI
 *  com.compilex.CosmicArmorSets.enums.ArmorSet
 *  com.compilex.CosmicOutposts.CosmicOutpostsAPI
 *  com.cosmicpvp.cosmicutils.CosmicUtils
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.exceptions.CosmicException
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.decimatemc.decimatecustomblocks.enums.BuffType
 *  com.decimatemc.decimatecustomblocks.storage.BuffStorage
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Faction
 *  me.ifamasssxd.cosmichcf.api.HCFOutpostAPI
 *  me.ifamasssxd.cosmichcf.struct.OutpostType
 *  me.ifamasssxd.cosmicmasks.MaskType
 *  me.quantiom.util.ItemSkinUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerRespawnEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicDungeons.listeners.items;

import com.compilex.CosmicArmorSets.CosmicArmorSetsAPI;
import com.compilex.CosmicArmorSets.enums.ArmorSet;
import com.compilex.CosmicDungeons.CosmicDungeonsPlugin;
import com.compilex.CosmicDungeons.utils.HolyWhiteScrollUtils;
import com.compilex.CosmicOutposts.CosmicOutpostsAPI;
import com.cosmicpvp.cosmicutils.CosmicUtils;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.decimatemc.decimatecustomblocks.enums.BuffType;
import com.decimatemc.decimatecustomblocks.storage.BuffStorage;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Faction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import me.ifamasssxd.cosmicmasks.MaskType;
import me.quantiom.util.ItemSkinUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class HolyWhiteScrollListener
implements Listener {
    private HashMap<String, List<ItemStack>> savedItems = new HashMap();

    public HashMap<String, List<ItemStack>> getSavedItems() {
        return this.savedItems;
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onPlayerApplyHolyWhiteScroll(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getWhoClicked().getOpenInventory() == null || e.getWhoClicked().getOpenInventory().getBottomInventory() == null || !e.getClickedInventory().equals((Object)e.getWhoClicked().getOpenInventory().getBottomInventory())) {
            return;
        }
        if (e.getCursor() != null && HolyWhiteScrollUtils.isOldHolyWhiteScroll(e.getCursor())) {
            try {
                NBTWrapper cursor = new NBTWrapper(e.getCursor());
                if (cursor.getCosmicData() != null) {
                    return;
                }
                NBTWrapper wrapper = e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR ? new NBTWrapper(e.getCurrentItem()) : null;
                CosmicDungeonsPlugin.get().getHolyWhitescrollItem().onInventoryClick((Player)e.getWhoClicked(), e.getCursor(), cursor, wrapper, e.getCurrentItem(), e);
            }
            catch (CosmicException e1) {
                ((Player)e.getWhoClicked()).sendMessage(e1.getMessage());
                e.setCancelled(true);
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getEntity().getWorld().getName().contains("duel_") != false) return;
        if (e.getEntity().getName().contains("koth")) {
            return;
        }
        if (e.getEntity().getWorld().getName().toLowerCase().contains("dungeon")) {
            return;
        }
        if (e.getEntity() != null) {
            if (e.getEntity() instanceof Player == false) return;
        }
        if (e.getEntity().getKiller() != null && !(e.getEntity().getKiller() instanceof Player)) {
            return;
        }
        killer = e.getEntity().getKiller();
        wearingMonopolyMask = killer != null && MaskType.MONOPOLY.hasEquipped(killer) != false;
        wearingThanosMask = killer != null && MaskType.THANOS.hasEquipped(killer) != false;
        controlling = killer != null && CosmicUtils.get().isOutpostsEnabled() != false && CosmicOutpostsAPI.controlsCosmonautOutpost((Player)killer) != false;
        savedItems = new ArrayList<ItemStack>();
        drops = e.getDrops();
        for (ItemStack i : drops) {
            block25: {
                if (!HolyWhiteScrollUtils.itemHasHolyWhitescroll(i)) continue;
                mask = false;
                HolyWhiteScrollUtils.removeHolyWhitescroll(i);
                killerIsControllingFrozen = false;
                isBansheed = false;
                isControlling = controlling != false && Math.random() > 0.9;
                isMonopolied = false;
                isThanosed = wearingThanosMask != false && Math.random() <= 0.0666;
                isTravAxe = false;
                isKilledHoldingTravAxe = false;
                if (killer == null) break block25;
                killerIsControllingFrozen = FactionUtils.getFaction((Player)killer).isNormal() != false && HCFOutpostAPI.isControllingOutpost((Faction)FactionUtils.getFaction((Player)killer), (OutpostType)OutpostType.FROZEN) != false;
                isBansheed = e.getEntity().hasMetadata("got_bansheed_now_gonna_negate_holywhitescroll");
                if (!wearingMonopolyMask) ** GOTO lbl-1000
                v0 = Math.random();
                v1 = killerIsControllingFrozen != false ? 0.48 : 0.33;
                if (v0 <= v1) {
                    v2 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v2 = false;
                }
                isMonopolied = v2;
                isKilledHoldingTravAxe = false;
                if (CosmicArmorSetsAPI.getArmorSet((Player)killer) == ArmorSet.DIMENSIONAL_TRAVELER) {
                    flag = false;
                    var19_20 = killer.getInventory().getContents();
                    var20_22 = var19_20.length;
                    for (var21_23 = 0; var21_23 < var20_22; ++var21_23) {
                        item = var19_20[var21_23];
                        if ((item == null || !new NBTWrapper(item).getString("cosmicType").equals("TRAVELER_AXE")) && !CosmicArmorSetsAPI.isOmniWeapon((ItemStack)item)) continue;
                        flag = true;
                    }
                    v3 = isTravAxe = Math.random() < 0.5 && flag != false;
                }
            }
            if (e.getEntity() != null && CosmicArmorSetsAPI.getArmorSet((Player)e.getEntity()) == ArmorSet.DIMENSIONAL_TRAVELER) {
                for (ItemStack item : e.getEntity().getInventory().getContents()) {
                    if (item == null || !new NBTWrapper(item).getString("cosmicType").equals("TRAVELER_AXE") && !CosmicArmorSetsAPI.isOmniWeapon((ItemStack)item)) continue;
                    isKilledHoldingTravAxe = true;
                }
            }
            Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(e.getEntity(), "HOLY_WHITESCROLL_DEATH", new Object[]{Lists.newArrayList((Object[])((Object[])new ItemStack[]{i}))}));
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ITEM_BREAK, 5.0f, 1.2f);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.WITHER_SHOOT, 3.0f, 10.0f);
            if (ItemSkinUtils.hasItemSkin((ItemStack)e.getEntity().getItemInHand(), (String)"SPRING_2020_AXE")) {
                isBansheed = false;
            }
            if (ItemSkinUtils.hasItemSkin((ItemStack)e.getEntity().getItemInHand(), (String)"SPRING_2020_AXE")) {
                isBansheed = false;
            }
            if (ItemSkinUtils.hasItemSkin((Player)Bukkit.getPlayer((String)e.getEntity().getName()), (String)"WITCH_HAT")) {
                isBansheed = false;
            }
            if (killer != null && BuffStorage.tryGetBuffs((Player)e.getEntity(), (BuffType)BuffType.IMMUNE_HOLY_WHITE_SCROLL).size() == 0) {
                if (isMonopolied && !isKilledHoldingTravAxe) {
                    if (HCFOutpostAPI.isControllingOutpost((Player)e.getEntity(), (OutpostType)OutpostType.ABYSSAL) && Math.random() < 0.33) {
                        e.getEntity().sendMessage(CC.GoldB + "* ABYSSAL MONO MASK NEGATION " + CC.Gray + "(" + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")");
                        savedItems.add(i);
                        continue;
                    }
                    if (killerIsControllingFrozen) {
                        e.getEntity().sendMessage(CC.RedB + "* FROZEN STRONGHOLD MONOPOLY [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.RedB + "] *");
                        killer.sendMessage(CC.GreenB + "* FROZEN STRONGHOLD MONOPOLY [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.GreenB + "] *");
                    } else {
                        e.getEntity().sendMessage(CC.RedB + "* MONOPOLY MASK [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.RedB + "] *");
                        killer.sendMessage(CC.GreenB + "* MONOPOLY MASK [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.GreenB + "] *");
                    }
                    Bukkit.getLogger().info("Dropping " + (Object)i + " due to " + killer.getName() + "'s Monopoly Mask procing.");
                    continue;
                }
                if (isThanosed) {
                    e.getEntity().sendMessage(CC.RedB + "* THANOS MASK [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.RedB + "] *");
                    killer.sendMessage(CC.GreenB + "* THANOS MASK [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.GreenB + "] *");
                    continue;
                }
                if (isControlling) {
                    e.getEntity().sendMessage(CC.RedB + "* COSMONAUT OUTPOST PERK [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.RedB + "] *");
                    killer.sendMessage(CC.GreenB + "* COSMONAUT OUTPOST PERK [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.GreenB + "] *");
                    Bukkit.getLogger().info("Dropping " + (Object)i + " due to " + killer.getName() + "'s Cosmonaut procing.");
                    continue;
                }
                if (isBansheed) {
                    e.getEntity().sendMessage(CC.RedB + "* BANSHEE RKO [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.RedB + "] *");
                    killer.sendMessage(CC.GreenB + "* BANSHEE RKO [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.GreenB + "] *");
                    Bukkit.getLogger().info("Dropping " + (Object)i + " due to " + killer.getName() + "'s Banshee RKO procing.");
                    continue;
                }
                if (isTravAxe) {
                    e.getEntity().sendMessage(CC.RedB + "* TIME SPLITTER [" + CC.Gray + killer.getName() + " (- " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.RedB + "] *");
                    killer.sendMessage(CC.GreenB + "* TIME SPLITTER [" + CC.Gray + e.getEntity().getName() + " (+ " + ItemUtils.getDisplayName((ItemStack)i) + CC.Gray + ")" + CC.GreenB + "] *");
                    Bukkit.getLogger().info("Dropping " + (Object)i + " due to " + killer.getName() + "'s Traveler Axe White Scroll Negation procing.");
                    continue;
                }
            }
            savedItems.add(i);
        }
        var8_8 = savedItems.iterator();
        while (true) {
            if (!var8_8.hasNext()) {
                if (savedItems.size() <= 0) return;
                this.savedItems.put(e.getEntity().getUniqueId().toString(), savedItems);
                return;
            }
            i = (ItemStack)var8_8.next();
            e.getDrops().remove((Object)i);
        }
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        List<ItemStack> toReturn = this.savedItems.remove(e.getPlayer().getUniqueId().toString());
        if (toReturn != null) {
            for (ItemStack i : toReturn) {
                Bukkit.getLogger().info("[CosmicDungeons (HolyWhiteScroll)] Player " + e.getPlayer().getName() + " (" + e.getPlayer().getUniqueId() + ") respawned with Holy White Scroll item: " + (Object)i);
                e.getPlayer().getInventory().addItem(new ItemStack[]{HolyWhiteScrollUtils.removeHolyWhitescroll(i)});
            }
            Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(e.getPlayer(), "HOLY_WHITESCROLL_DEATH", new Object[]{toReturn}));
        }
    }
}

