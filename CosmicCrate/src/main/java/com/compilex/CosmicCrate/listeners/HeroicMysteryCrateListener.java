// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.listeners;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Material;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import org.bukkit.Bukkit;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Sound;
import com.compilex.CosmicCrate.api.CCAPI;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import com.compilex.CosmicCrate.libs.Version;
import java.util.List;
import org.bukkit.event.Listener;

public class HeroicMysteryCrateListener implements Listener
{
    private static List<Version> godly_versions_2017;
    private static List<Version> rare_versions_2017;
    private static List<Version> versions_2017;
    private static List<Version> versions2018;
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            final ItemStack i = e.getItem();
            if (this.isHeroicMysteryCrate(i)) {
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                e.setUseItemInHand(Event.Result.DENY);
                if (e.getPlayer().getInventory().firstEmpty() == -1) {
                    e.getPlayer().sendMessage(ChatColor.RED + "Your inventory is full!");
                    return;
                }
                if (i.getAmount() > 1) {
                    i.setAmount(i.getAmount() - 1);
                    e.getPlayer().setItemInHand(i);
                }
                else {
                    e.getPlayer().setItemInHand((ItemStack)null);
                    if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                        final long itemID = ItemID.getItemID(e.getItem());
                        if (itemID != -1L) {
                            ItemID.blacklistIID(itemID, "mysteryCCOpen");
                        }
                    }
                }
                final Version v = this.getRandomCrateVersion();
                final ItemStack cc = CCAPI.getCosmicCrate(v, "Heroic Mystery Cosmic Crate");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1.0f, 0.7f);
                ParticleEffect.playEffect(ParticleEffect.SPELL, e.getPlayer().getEyeLocation(), 0.8f, 100);
                ParticleEffect.playEffect(ParticleEffect.INSTANT_SPELL, e.getPlayer().getEyeLocation(), 0.6f, 80);
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + ">> " + ChatColor.translateAlternateColorCodes('&', v.crateName.substring(0, 2)) + e.getPlayer().getName() + ChatColor.RED + " unlocked the " + v.getRawName() + " /cc" + ChatColor.RED + " from a " + ChatColor.UNDERLINE + "Heroic" + ChatColor.RED + " Mystery Cosmic Crate!");
                e.getPlayer().getInventory().addItem(new ItemStack[] { cc });
                e.getPlayer().updateInventory();
                Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(e.getPlayer(), "CC_USE", new Object[] { "HEROIC" }));
            }
            else if (this.isGodlyMysteryCrate(i)) {
                e.setUseItemInHand(Event.Result.DENY);
                e.setCancelled(true);
                e.setUseInteractedBlock(Event.Result.DENY);
                final Player pl = e.getPlayer();
                if (pl.getInventory().firstEmpty() == -1) {
                    pl.sendMessage(CC.RedB + "(!) " + CC.Red + "Your inventory is full!");
                    return;
                }
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                    final long itemID2 = ItemID.getItemID(e.getItem());
                    if (itemID2 != -1L) {
                        if (ItemID.blacklist_iid.contains(itemID2)) {
                            pl.sendMessage(CC.RedB + "(!) " + CC.Red + "It seems that Godly Cosmic Mystery Crate has already been claimed! #" + itemID2);
                            pl.setItemInHand((ItemStack)null);
                            pl.updateInventory();
                            Bukkit.getLogger().info("[CosmicCrate] Removing already claimed cosmic crate: " + itemID2 + " from " + pl.getName() + "!");
                            return;
                        }
                        ItemID.blacklistIID(itemID2, "godlyCCOpen " + pl.getName());
                    }
                }
                if (i.getAmount() > 1) {
                    i.setAmount(i.getAmount() - 1);
                    e.getPlayer().setItemInHand(i);
                }
                else {
                    e.getPlayer().setItemInHand((ItemStack)null);
                }
                final Version v2 = this.getRandomGodlyCrateVersion();
                final ItemStack cc2 = CCAPI.getCosmicCrate(v2, "Superior Mystery Cosmic Crate");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1.0f, 0.7f);
                ParticleEffect.playEffect(ParticleEffect.SPELL, e.getPlayer().getEyeLocation(), 0.8f, 100);
                ParticleEffect.playEffect(ParticleEffect.INSTANT_SPELL, e.getPlayer().getEyeLocation(), 0.6f, 80);
                Bukkit.broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + ">> " + ChatColor.translateAlternateColorCodes('&', v2.crateName.substring(0, 2)) + e.getPlayer().getName() + ChatColor.RED + " unlocked the " + v2.getRawName() + " /cc" + ChatColor.RED + " from a " + ChatColor.UNDERLINE + "Superior" + ChatColor.RED + " Cosmic Crate!");
                e.getPlayer().getInventory().addItem(new ItemStack[] { cc2 });
                e.getPlayer().updateInventory();
                Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(e.getPlayer(), "CC_USE", new Object[] { "SUPERIOR" }));
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
        return HeroicMysteryCrateListener.versions2018.get(ThreadLocalRandom.current().nextInt(HeroicMysteryCrateListener.versions2018.size()));
    }
    
    private Version getRandomCrateVersion() {
        final double d = Math.random();
        if (d < 0.6) {
            return HeroicMysteryCrateListener.versions_2017.get(new Random().nextInt(HeroicMysteryCrateListener.versions_2017.size()));
        }
        if (d < 0.9) {
            return HeroicMysteryCrateListener.rare_versions_2017.get(new Random().nextInt(HeroicMysteryCrateListener.rare_versions_2017.size()));
        }
        return HeroicMysteryCrateListener.godly_versions_2017.get(new Random().nextInt(HeroicMysteryCrateListener.godly_versions_2017.size()));
    }
    
    private boolean isHeroicMysteryCrate(final ItemStack i) {
        return i != null && i.getType() == Material.ENDER_CHEST && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().size() > 2 && i.getItemMeta().getLore().get(2).equals(ChatColor.GRAY + "a random 2017 /cc from this list!");
    }
    
    private boolean isGodlyMysteryCrate(final ItemStack i) {
        return i != null && i.getType() == Material.ENDER_CHEST && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().size() > 2 && i.getItemMeta().getLore().get(2).equals(ChatColor.GRAY + "a random 2018 /cc from this list!") && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("Crate");
    }
    
    static {
        HeroicMysteryCrateListener.godly_versions_2017 = new ArrayList<Version>(Arrays.asList(Version.SCHOOL_2017, Version.NOVEMBER_2017, Version.DECEMBER_2017, Version.HOLIDAY_2017));
        HeroicMysteryCrateListener.rare_versions_2017 = new ArrayList<Version>(Arrays.asList(Version.OCTOBER_2017, Version.VALENTINE, Version.HALLOWEEN_2017));
        HeroicMysteryCrateListener.versions_2017 = new ArrayList<Version>(Arrays.asList(Version.MARCH_2017, Version.JANUARY_2017, Version.JULY_2017, Version.APRIL_2017));
        HeroicMysteryCrateListener.versions2018 = (List<Version>)Lists.newArrayList((Object[])new Version[] { Version.JANUARY_2018, Version.FEBUARY_2018, Version.MARCH_2018, Version.APRIL_2018, Version.MAY_2018, Version.JUNE_2018, Version.AUGUST_2018, Version.OCTOBER_2018, Version.NOVEMBER_2018, Version.DECEMBER_2018 });
    }
}
