/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Chicken
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.EntityDamageEvent
 *  org.bukkit.event.entity.EntityDeathEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.itembuilder.MaskBuilder;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

final class ThanksgivingListener
        implements Listener {
    ThanksgivingListener() {
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (this.isTurkey(e.getEntity())) {
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.CHICKEN_EGG_POP, 1.0f, 0.15f);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (this.isTurkey(e.getEntity())) {
            ItemStack mask;
            e.getDrops().add(this.getThanksgivingTurkey());
            if (Math.random() <= 0.001 && (mask = new MaskBuilder(Math.random() <= 0.5 ? "THANKSGIVING" : "PILGRIM").build()) != null) {
                e.getDrops().add(mask);
            }
        }
    }

    @EventHandler
    public void onPlayerEatTurkey(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && e.getItem().isSimilar(this.getThanksgivingTurkey())) {
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            if (e.getPlayer().getWorld().getName().startsWith("cosmic-station")) {
                e.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "(!) " + ChatColor.RED + "You cannot eat Thanksgiving Turkey at Outposts.");
                return;
            }
            ItemStack i = e.getItem();
            if (i.getAmount() > 1) {
                i.setAmount(i.getAmount() - 1);
                e.getPlayer().setItemInHand(i);
            } else {
                e.getPlayer().setItemInHand(null);
            }
            this.healHP(e.getPlayer(), 16);
            this.healFood(e.getPlayer(), 20);
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.EAT, 0.7f, 0.9f);
            ParticleEffect.playEffect(ParticleEffect.SPELL, e.getPlayer().getLocation().add(0.0, 1.0, 0.0), 0.33f, 35);
        }
    }

    private ItemStack getThanksgivingTurkey() {
        return ItemUtils.buildItem(Material.COOKED_CHICKEN, (short) 0, ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Thanksgiving Turkey", ChatColor.RED + "+16 HP", ChatColor.RED + "+20 Food", ChatColor.GRAY + "A deliciously home-roasted turkey!");
    }

    private boolean isTurkey(Entity ent) {
        return ent instanceof Chicken && ent.getCustomName().contains("Thanksgiving Turkey");
    }

    private void healHP(Player p, int amount) {
        if (p.getHealth() + (double) amount > p.getMaxHealth()) {
            p.setHealth(p.getMaxHealth());
        } else {
            p.setHealth(p.getHealth() + (double) amount);
        }
    }

    private void healFood(Player p, int amount) {
        if (p.getFoodLevel() > 20) {
            p.setFoodLevel(20);
        } else {
            p.setFoodLevel(p.getFoodLevel() + amount);
        }
    }
}

