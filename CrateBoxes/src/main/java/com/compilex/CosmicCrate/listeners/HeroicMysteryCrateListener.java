/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicCrate.listeners;

import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.libs.Version;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HeroicMysteryCrateListener
implements Listener {
    private static List<Version> godly_versions_2017 = new ArrayList<Version>(Arrays.asList(new Version[]{Version.SCHOOL_2017, Version.NOVEMBER_2017, Version.DECEMBER_2017, Version.HOLIDAY_2017}));
    private static List<Version> rare_versions_2017 = new ArrayList<Version>(Arrays.asList(new Version[]{Version.OCTOBER_2017, Version.VALENTINE, Version.HALLOWEEN_2017}));
    private static List<Version> versions_2017 = new ArrayList<Version>(Arrays.asList(new Version[]{Version.MARCH_2017, Version.JANUARY_2017, Version.JULY_2017, Version.APRIL_2017}));
    private static List<Version> versions2018 = Lists.newArrayList((Object[])new Version[]{Version.JANUARY_2018, Version.FEBUARY_2018, Version.MARCH_2018, Version.APRIL_2018, Version.MAY_2018, Version.JUNE_2018, Version.AUGUST_2018, Version.OCTOBER_2018, Version.NOVEMBER_2018, Version.DECEMBER_2018});

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            ItemStack i = e.getItem();
            if (this.isHeroicMysteryCrate(i)) {
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
                if (e.getPlayer().getInventory().firstEmpty() == -1) {
                    e.getPlayer().sendMessage((Object)ChatColor.RED + "Your inventory is full!");
                    return;
                }
                if (i.getAmount() > 1) {
                    i.setAmount(i.getAmount() - 1);
                    e.getPlayer().setItemInHand(i);
                } else {
                    long itemID;
                    e.getPlayer().setItemInHand(null);
                    if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && (itemID = ItemID.getItemID((ItemStack)e.getItem())) != -1L) {
                        ItemID.blacklistIID((long)itemID, (String)"mysteryCCOpen");
                    }
                }
                Version v = this.getRandomCrateVersion();
                ItemStack cc = CCAPI.getCosmicCrate(v, "Heroic Mystery Cosmic Crate");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1.0f, 0.7f);
                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)e.getPlayer().getEyeLocation(), (float)0.8f, (int)100);
                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.INSTANT_SPELL, (Location)e.getPlayer().getEyeLocation(), (float)0.6f, (int)80);
                Bukkit.broadcastMessage((String)((Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + ">> " + ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName.substring(0, 2)) + e.getPlayer().getName() + (Object)ChatColor.RED + " unlocked the " + v.getRawName() + " /cc" + (Object)ChatColor.RED + " from a " + (Object)ChatColor.UNDERLINE + "Heroic" + (Object)ChatColor.RED + " Mystery Cosmic Crate!"));
                e.getPlayer().getInventory().addItem(new ItemStack[]{cc});
                e.getPlayer().updateInventory();
                Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(e.getPlayer(), "CC_USE", new Object[]{"HEROIC"}));
            } else if (this.isGodlyMysteryCrate(i)) {
                long itemID;
                e.setUseItemInHand(Event.Result.DENY);
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                Player pl = e.getPlayer();
                if (pl.getInventory().firstEmpty() == -1) {
                    pl.sendMessage(CC.RedB + "(!) " + CC.Red + "Your inventory is full!");
                    return;
                }
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && (itemID = ItemID.getItemID((ItemStack)e.getItem())) != -1L) {
                    if (ItemID.blacklist_iid.contains(itemID)) {
                        pl.sendMessage(CC.RedB + "(!) " + CC.Red + "It seems that Godly Cosmic Mystery Crate has already been claimed! #" + itemID);
                        pl.setItemInHand(null);
                        pl.updateInventory();
                        Bukkit.getLogger().info("[CosmicCrate] Removing already claimed cosmic crate: " + itemID + " from " + pl.getName() + "!");
                        return;
                    }
                    ItemID.blacklistIID((long)itemID, (String)("godlyCCOpen " + pl.getName()));
                }
                if (i.getAmount() > 1) {
                    i.setAmount(i.getAmount() - 1);
                    e.getPlayer().setItemInHand(i);
                } else {
                    e.getPlayer().setItemInHand(null);
                }
                Version v = this.getRandomGodlyCrateVersion();
                ItemStack cc = CCAPI.getCosmicCrate(v, "Superior Mystery Cosmic Crate");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1.0f, 0.7f);
                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)e.getPlayer().getEyeLocation(), (float)0.8f, (int)100);
                ParticleEffect.playEffect((ParticleEffect)ParticleEffect.INSTANT_SPELL, (Location)e.getPlayer().getEyeLocation(), (float)0.6f, (int)80);
                Bukkit.broadcastMessage((String)((Object)ChatColor.DARK_RED + "" + (Object)ChatColor.BOLD + ">> " + ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName.substring(0, 2)) + e.getPlayer().getName() + (Object)ChatColor.RED + " unlocked the " + v.getRawName() + " /cc" + (Object)ChatColor.RED + " from a " + (Object)ChatColor.UNDERLINE + "Superior" + (Object)ChatColor.RED + " Cosmic Crate!"));
                e.getPlayer().getInventory().addItem(new ItemStack[]{cc});
                e.getPlayer().updateInventory();
                Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(e.getPlayer(), "CC_USE", new Object[]{"SUPERIOR"}));
            }
        }
    }

    private Version getRandomGodlyCrateVersion() {
        if (Math.random() <= 0.1) {
            return Version.JULY_2018;
        }
        if (Math.random() <= 0.025) {
            return Version.VALENTINE_2018;
        }
        if (Math.random() <= 0.05) {
            return Version.HALLOWEEN_2018;
        }
        if (Math.random() <= 0.05) {
            return Version.THANKSGIVING_2018;
        }
        if (Math.random() <= 0.025) {
            return Version.HOLIDAY_2018;
        }
        return versions2018.get(ThreadLocalRandom.current().nextInt(versions2018.size()));
    }

    private Version getRandomCrateVersion() {
        double d = Math.random();
        if (d < 0.6) {
            return versions_2017.get(new Random().nextInt(versions_2017.size()));
        }
        if (d < 0.9) {
            return rare_versions_2017.get(new Random().nextInt(rare_versions_2017.size()));
        }
        return godly_versions_2017.get(new Random().nextInt(godly_versions_2017.size()));
    }

    private boolean isHeroicMysteryCrate(ItemStack i) {
        return i != null && i.getType() == Material.ENDER_CHEST && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().size() > 2 && ((String)i.getItemMeta().getLore().get(2)).equals((Object)ChatColor.GRAY + "a random 2017 /cc from this list!");
    }

    private boolean isGodlyMysteryCrate(ItemStack i) {
        return i != null && i.getType() == Material.ENDER_CHEST && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().size() > 2 && ((String)i.getItemMeta().getLore().get(2)).equals((Object)ChatColor.GRAY + "a random 2018 /cc from this list!") && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("Crate");
    }
}

