// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.listeners;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Material;
import java.util.Random;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import org.bukkit.Bukkit;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.bukkit.Sound;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.inventory.ItemStack;
import com.compilex.CosmicCrate.api.CCAPI;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import com.compilex.CosmicCrate.libs.Version;
import java.util.List;
import org.bukkit.event.Listener;

public class MysteryCrateListener implements Listener
{
    private static List<Version> godly_versions_2016;
    private static List<Version> rare_versions_2016;
    private static List<Version> versions_2016;
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && this.isMysteryCrate(e.getItem())) {
            final ItemStack i = e.getItem();
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getPlayer().getInventory().firstEmpty() == -1) {
                e.getPlayer().sendMessage(ChatColor.RED + "Your inventory is full!");
                return;
            }
            final Version v = this.getRandomCrateVersion();
            final ItemStack cc = CCAPI.getCosmicCrate(v, "Mystery Cosmic Crate");
            if (i.getAmount() > 1) {
                i.setAmount(i.getAmount() - 1);
                e.getPlayer().setItemInHand(i);
            }
            else {
                e.getPlayer().setItemInHand((ItemStack)null);
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                    final long itemID = ItemID.getItemID(e.getItem());
                    if (itemID != -1L) {
                        ItemID.blacklistIID(itemID, "mysteryCCOpen/" + v.toString() + "/" + e.getPlayer().getName());
                    }
                }
            }
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.CHEST_OPEN, 1.0f, 0.7f);
            ParticleEffect.playEffect(ParticleEffect.SPELL, e.getPlayer().getEyeLocation(), 0.8f, 100);
            ParticleEffect.playEffect(ParticleEffect.INSTANT_SPELL, e.getPlayer().getEyeLocation(), 0.6f, 80);
            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + ">> " + ChatColor.translateAlternateColorCodes('&', v.crateName.substring(0, 2)) + e.getPlayer().getName() + ChatColor.GRAY + " unlocked the " + v.getRawName() + " /cc" + ChatColor.GRAY + " from a Mystery Cosmic Crate!");
            e.getPlayer().getInventory().addItem(new ItemStack[] { cc });
            e.getPlayer().updateInventory();
            Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(e.getPlayer(), "CC_USE", new Object[] { "MYSTERY" }));
        }
    }
    
    private Version getRandomCrateVersion() {
        final double d = Math.random();
        if (d < 0.6) {
            return MysteryCrateListener.versions_2016.get(new Random().nextInt(MysteryCrateListener.versions_2016.size()));
        }
        if (d < 0.9) {
            return MysteryCrateListener.rare_versions_2016.get(new Random().nextInt(MysteryCrateListener.rare_versions_2016.size()));
        }
        return MysteryCrateListener.godly_versions_2016.get(new Random().nextInt(MysteryCrateListener.godly_versions_2016.size()));
    }
    
    private boolean isMysteryCrate(final ItemStack i) {
        return i != null && i.getType() == Material.ENDER_CHEST && i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().size() > 2 && i.getItemMeta().getLore().get(2).equals(ChatColor.GRAY + "a random /cc from this list!");
    }
    
    static {
        MysteryCrateListener.godly_versions_2016 = new ArrayList<Version>(Arrays.asList(Version.SCHOOL, Version.BLACK_FRIDAY, Version.HOLIDAY));
        MysteryCrateListener.rare_versions_2016 = new ArrayList<Version>(Arrays.asList(Version.JULY, Version.AUGUST, Version.OCTOBER, Version.HALLOWEEN, Version.NOVEMBER, Version.BLACK_FRIDAY, Version.DECEMBER_2016));
        MysteryCrateListener.versions_2016 = new ArrayList<Version>(Arrays.asList(Version.JANURARY, Version.FEBRUARY, Version.MARCH, Version.APRIL, Version.AUGUST, Version.OCTOBER));
    }
}
