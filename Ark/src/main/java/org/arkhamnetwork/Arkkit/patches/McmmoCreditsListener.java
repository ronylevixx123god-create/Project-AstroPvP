/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.gmail.nossr50.api.ExperienceAPI
 *  com.gmail.nossr50.datatypes.skills.SkillType
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

final class McmmoCreditsListener
        implements Listener {
    McmmoCreditsListener() {
    }

    @EventHandler
    public void onPlayerUseMCMMOCredit(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            long itemID;
            int value = this.getMcmmoCreditValue(e.getItem());
            if (value == 0) {
                return;
            }
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            e.getPlayer().setItemInHand(null);
            e.getPlayer().updateInventory();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "addcredits " + e.getPlayer().getName() + " " + value);
        }
    }

    @EventHandler
    public void onPlayerUseSkillCredit(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            int value = this.getSkillCreditValue(e.getItem());
            SkillType type = this.getSkillCreditType(e.getItem());
            if (value == 0) {
                return;
            }
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            if (ExperienceAPI.getLevel(e.getPlayer(), type.name()) + value <= ExperienceAPI.getLevelCap(type.name())) {
                long itemID;
                e.getPlayer().setItemInHand(null);
                e.getPlayer().updateInventory();
                ExperienceAPI.addLevel(e.getPlayer(), type.name(), value);
                e.getPlayer().sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "+" + value + " " + type.name() + " LEVELS!");
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0f, 0.33f);
            } else {
                e.getPlayer().sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "You cannot add that many " + type.name() + " levels, as it would exceed the level cap.");
            }
        }
    }

    private int getMcmmoCreditValue(ItemStack is) {
        if (is.getType() == Material.PAPER && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && (is.getItemMeta().getDisplayName().startsWith(ChatColor.GOLD + "MCMMO Voucher") || is.getItemMeta().getDisplayName().startsWith(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "MCMMO Voucher"))) {
            return Integer.parseInt(ChatColor.stripColor(is.getItemMeta().getLore().get(0).split(" ")[0])) * is.getAmount();
        }
        return 0;
    }

    private int getSkillCreditValue(ItemStack is) {
        if (is.getType() == Material.PAPER && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Skill Voucher")) {
            return Integer.parseInt(ChatColor.stripColor(is.getItemMeta().getLore().get(1).split(" ")[0].replace("+", ""))) * is.getAmount();
        }
        return 0;
    }

    private SkillType getSkillCreditType(ItemStack is) {
        if (is.getType() == Material.PAPER && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().getDisplayName().startsWith(ChatColor.GOLD + ChatColor.BOLD.toString() + "Skill Voucher")) {
            return SkillType.valueOf(ChatColor.stripColor(is.getItemMeta().getLore().get(0).split(" ")[1]).toUpperCase());
        }
        return null;
    }
}

