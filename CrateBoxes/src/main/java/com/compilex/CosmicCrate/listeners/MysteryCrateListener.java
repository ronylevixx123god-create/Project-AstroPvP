/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MysteryCrateListener
implements Listener {
    private static List<Version> godly_versions_2016 = new ArrayList<Version>(Arrays.asList(new Version[]{Version.SCHOOL, Version.BLACK_FRIDAY, Version.HOLIDAY}));
    private static List<Version> rare_versions_2016 = new ArrayList<Version>(Arrays.asList(new Version[]{Version.JULY, Version.AUGUST, Version.OCTOBER, Version.HALLOWEEN, Version.NOVEMBER, Version.BLACK_FRIDAY, Version.DECEMBER_2016}));
    private static List<Version> versions_2016 = new ArrayList<Version>(Arrays.asList(new Version[]{Version.JANURARY, Version.FEBRUARY, Version.MARCH, Version.APRIL, Version.AUGUST, Version.OCTOBER}));

    @EventHandler(priority=EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && this.isMysteryCrate(e.getItem())) {
            ItemStack i = e.getItem();
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getPlayer().getInventory().firstEmpty() == -1) {
                e.getPlayer().sendMessage((Object)ChatColor.RED + "Your inventory is full!");
                return;
            }
            Version v = this.getRandomCrateVersion();
            ItemStack cc = CCAPI.getCosmicCrate(v, "Mystery Cosmic Crate");
            if (i.getAmount() > 1) {
                i.setAmount(i.getAmount() - 1);
                e.getPlayer().setItemInHand(i);
            } else {
                long itemID;
                e.getPlayer().setItemInHand(null);
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning() && (itemID = ItemID.getItemID((ItemStack)e.getItem())) != -1L) {
                    ItemID.blacklistIID((long)itemID, (String)("mysteryCCOpen/" + v.toString() + "/" + e.getPlayer().getName()));
                }
            }
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1.0f, 0.7f);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.SPELL, (Location)e.getPlayer().getEyeLocation(), (float)0.8f, (int)100);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.INSTANT_SPELL, (Location)e.getPlayer().getEyeLocation(), (float)0.6f, (int)80);
            Bukkit.broadcastMessage((String)((Object)ChatColor.DARK_GRAY + "" + (Object)ChatColor.BOLD + ">> " + ChatColor.translateAlternateColorCodes((char)'&', (String)v.crateName.substring(0, 2)) + e.getPlayer().getName() + (Object)ChatColor.GRAY + " unlocked the " + v.getRawName() + " /cc" + (Object)ChatColor.GRAY + " from a Mystery Cosmic Crate!"));
            e.getPlayer().getInventory().addItem(new ItemStack[]{cc});
            e.getPlayer().updateInventory();
            Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(e.getPlayer(), "CC_USE", new Object[]{"MYSTERY"}));
        }
    }

    private Version getRandomCrateVersion() {
        double d = Math.random();
        if (d < 0.6) {
            return versions_2016.get(new Random().nextInt(versions_2016.size()));
        }
        if (d < 0.9) {
            return rare_versions_2016.get(new Random().nextInt(rare_versions_2016.size()));
        }
        return godly_versions_2016.get(new Random().nextInt(godly_versions_2016.size()));
    }

    private boolean isMysteryCrate(ItemStack i) {
        return i != null && i.getType() == Material.ENDER_CHEST && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().size() > 2 && ((String)i.getItemMeta().getLore().get(2)).equals((Object)ChatColor.GRAY + "a random /cc from this list!");
    }
}

