/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.compat.ParamBuilder
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.net.astropvp.armorsets.utils.ReflectionUtils
 *  com.massivecraft.factions.Faction
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package com.conaxgames.factions.boosters.listeners;

import com.conaxgames.factions.boosters.enums.BoosterType;
import com.conaxgames.factions.boosters.struct.ActiveBooster;
import com.conaxgames.factions.boosters.struct.BoosterItemInfo;
import com.conaxgames.factions.boosters.struct.BoosterManager;
import com.conaxgames.factions.boosters.struct.FactionBoosters;
import com.cosmicpvp.cosmicutils.compat.ParamBuilder;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class BoosterListener implements Listener {
    private BoosterManager manager;

    public BoosterListener(BoosterManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.hasItem()) {
            ItemStack item = event.getItem();
            Player player = event.getPlayer();
            if (this.manager != null) {
            if (this.manager.isBoosterItem(item)) {
                event.setCancelled(true);
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);
                Faction faction = FactionUtils.getFaction(player, false);
                if (faction == null || !FactionUtils.isNormal(faction)) {
                    player.sendMessage(ChatColor.RED + "You must be in a faction to use a Faction Booster!");
                    return;
                }
                FactionBoosters currentBoosters = this.manager.getFactionBooster(faction);
                BoosterItemInfo info = this.manager.getBoosterItemInfo(item);
                if (info != null) {
                    if (player.getWorld().getName().startsWith("world_duels")) {
                        player.sendMessage(ChatColor.RED + "You cannot use this item in the duel world.");
                        return;
                    }
                    if (currentBoosters != null &&
                            currentBoosters.isBoosterActive(info.getType())) {
                        ActiveBooster booster = (ActiveBooster) currentBoosters.get(info.getType());
                        if (booster.getWhoApplied().equals(player.getName())) {
                            if (booster.getMultiplier() >= 10.0D) {
                                player.sendMessage(ChatColor.RED + "You cannot apply more than a 10x multiplier.");
                                return;
                            }
                            if (booster.getMultiplier() != info.getMultiplier()) {
                                player.sendMessage(ChatColor.RED + ChatColor.BOLD
                                        .toString() + "(!) " + ChatColor.RED + "You can only apply the same " + booster
                                        .getMultiplier() + "x multiplier while your booster is active.");
                                return;
                            }
                            booster.setMaxSeconds(info.getDuration() + booster.getMaxSeconds() - booster.getSecondsElapsed());
                            booster.setTimeApplied(System.currentTimeMillis());
                            booster.setSecondsElapsed(0);
                            activateBooster(faction, player, booster, item);
                            return;
                        }
                        player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "(!) " + ChatColor.RED + "Your faction already has this booster activated by " + booster
                                .getWhoApplied() + " for another " + booster
                                .getFormattedTimeLeft() + "!");
                        return;
                    }
                    if (currentBoosters == null) {
                        currentBoosters = new FactionBoosters();
                        this.manager.getFactionBoosters().put(FactionUtils.getFactionId(faction), currentBoosters);
                    }
                    ActiveBooster newBooster = new ActiveBooster(player.getName(), info.getMultiplier(), System.currentTimeMillis(), 0, info.getDuration(), info.getType());
                    currentBoosters.put(info.getType(), newBooster);
                    activateBooster(faction, player, newBooster, item);
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid Booster detected.");
                }
            }
            }
        }
    }

    public void activateBooster(Faction faction, Player player, ActiveBooster booster, ItemStack item) {
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
        } else {
            player.setItemInHand(null);
        }
        if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning())
            ItemID.blacklistIID(ItemID.getItemID(item), "xp booster use");
        player.updateInventory();
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 0.5F);
        ReflectionUtils.callReflectionMethod(faction, Faction.class, "sendMessage", new ParamBuilder(String.class, ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.GREEN + player.getName() + " used a " + ChatColor.GREEN + ChatColor.UNDERLINE
                .toString() + booster.getMultiplier() + "x " + booster.getBoosterType().getItemName() + ChatColor.GREEN + " Faction Booster!" + ChatColor.GRAY + " [" + booster
                .getFormattedTimeLeft() + " left]"));
        Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(player, "BOOSTER_USE", new Object[] { booster.getBoosterType().name(), Double.valueOf(booster.getMultiplier()), Integer.valueOf(booster.getMaxSeconds()) }));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Faction faction = FactionUtils.getFaction(player);
        if (faction == null || !FactionUtils.isNormal(faction))
            return;
        BoosterManager manager = P.getP().getBoosterManager();
        if (manager.getFactionBoosters().containsKey(FactionUtils.getFactionId(faction))) {
            FactionBoosters boosters = manager.getFactionBooster(faction);
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)P.getP(), () -> {
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 0.7F);
                manager.showActiveBoosters(player, boosters);
            },40L);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();
            Faction fac = FactionUtils.getFaction(player);
            if (fac == null || !FactionUtils.isNormal(fac))
                return;
            BoosterManager manager = P.getP().getBoosterManager();
            FactionBoosters boosters = manager.getFactionBooster(fac);
            if (boosters != null)
                if (boosters.isBoosterActive(BoosterType.EXP)) {
                    ActiveBooster booster = (ActiveBooster)boosters.get(BoosterType.EXP);
                    event.setDroppedExp((int)(event.getDroppedExp() * booster.getMultiplier()));
                    int notify = 0;
                    if (player.hasMetadata("expBoosterNotify") && player.getMetadata("expBoosterNotify").size() > 0) {
                        notify = ((MetadataValue)player.getMetadata("expBoosterNotify").get(0)).asInt();
                        if (notify >= 5) {
                            player.sendMessage(ChatColor.GREEN + ChatColor.BOLD
                                    .toString() + "+ " + booster.getMultiplier() + "x EXP " + ChatColor.GREEN + "(" + booster
                                    .getWhoApplied() + "'s Faction Booster " + ChatColor.GRAY + "[" + booster.getFormattedTimeLeft() + "]" + ChatColor.GREEN + ")");
                            notify = 0;
                        }
                    }
                    player.setMetadata("expBoosterNotify", (MetadataValue)new FixedMetadataValue((Plugin)P.getP(), Integer.valueOf(++notify)));
                }
        }
    }
}
