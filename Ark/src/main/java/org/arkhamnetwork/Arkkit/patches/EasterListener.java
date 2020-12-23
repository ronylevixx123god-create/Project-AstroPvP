/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  org.bukkit.ChatColor
 *  org.bukkit.FireworkEffect
 *  org.bukkit.Sound
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Firework
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerJoinEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.FireworkMeta
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsAPI;
import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import org.arkhamnetwork.Arkkit.utils.RandomFireworks;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

final class EasterListener
        implements Listener {
    private final Easter instance;

    EasterListener(Easter easter) {
        this.instance = easter;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (this.instance.isEaster()) {
            e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_EGG_POP, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onPlayerUseEasterEgg(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && Easter.isEasterEgg(e.getItem())) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            if (e.getPlayer().getInventory().firstEmpty() == -1) {
                e.getPlayer().sendMessage(ChatColor.RED + "You do not have enough inventory slots to open this easter egg.");
                return;
            }
            ItemStack easterEggs = e.getItem().clone();
            if (easterEggs.getAmount() > 1) {
                easterEggs.setAmount(easterEggs.getAmount() - 1);
                e.getPlayer().setItemInHand(easterEggs);
            } else {
                e.getPlayer().setItemInHand(null);
            }
            ItemStack reward = CosmicSpaceChestsAPI.getDropPackageChest(CosmicSpaceChestsAPI.getRandomItemRarity());
            e.getPlayer().getInventory().setItem(e.getPlayer().getInventory().firstEmpty(), reward);
            e.getPlayer().updateInventory();
            e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.CHICKEN_IDLE, 1.0f, 0.75f);
            Firework fw = (Firework) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation().clone().add(0.0, 1.0, 0.0), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(RandomFireworks.getManager().getRandomColor()).withFade(RandomFireworks.getManager().getRandomColor()).with(RandomFireworks.getManager().getRandomType()).trail(true).build();
            fwm.addEffect(effect);
            fwm.setPower(0);
            fw.setFireworkMeta(fwm);
        }
    }
}

