/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.bukkit.FireworkEffect
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Firework
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.FireworkMeta
 */
package org.arkhamnetwork.Arkkit.patches;

import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.NewYears;
import org.arkhamnetwork.Arkkit.utils.RandomFireworks;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

final class NewYearsListener
implements Listener {
    NewYearsListener() {
    }

    @EventHandler
    public void onPlayerUseExplosiveCake(PlayerInteractEvent e) {
        if (e.hasItem() && NewYears.isExplosiveCake(e.getItem()) && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setCancelled(true);
            ItemStack i = e.getItem();
            if (i.getAmount() > 1) {
                i.setAmount(i.getAmount() - 1);
                e.getPlayer().setItemInHand(i);
            } else {
                e.getPlayer().setItemInHand(null);
            }
            e.getPlayer().updateInventory();
            Location l = e.getPlayer().getLocation().add(0.0, 1.0, 0.0);
            ParticleEffect.playEffect(ParticleEffect.SPELL, l, 0.1f, 50);
            ParticleEffect.playEffect(ParticleEffect.FIREWORKS_SPARK, l, 0.33f, 40);
            ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, l, 0.2f, 5);
            l.getWorld().playSound(l, Sound.EXPLODE, 0.8f, 0.5f);
            l.getWorld().playSound(l, Sound.EAT, 1.0f, 0.77f);
        }
    }

    @EventHandler
    public void onPlayerUseSpaceFirework(PlayerInteractEvent e) {
        if (e.hasItem() && NewYears.isCosmicCracker(e.getItem()) && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setCancelled(true);
            ItemStack i = e.getItem();
            if (i.getAmount() > 1) {
                i.setAmount(i.getAmount() - 1);
                e.getPlayer().setItemInHand(i);
            } else {
                e.getPlayer().setItemInHand(null);
            }
            e.getPlayer().updateInventory();
            Location l = e.getPlayer().getLocation().add(0.0, 1.0, 0.0);
            Firework fw = (Firework)l.getWorld().spawnEntity(l.clone().add(0.0, 1.0, 0.0), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(RandomFireworks.getManager().getRandomColor()).withFade(RandomFireworks.getManager().getRandomColor()).with(RandomFireworks.getManager().getRandomType()).trail(true).build();
            fwm.addEffect(effect);
            fwm.setPower(0);
            fw.setFireworkMeta(fwm);
        }
    }
}

