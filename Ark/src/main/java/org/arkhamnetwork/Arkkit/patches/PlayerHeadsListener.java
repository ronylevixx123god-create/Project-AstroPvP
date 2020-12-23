/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  net.minecraft.util.org.apache.commons.lang3.text.WordUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.inventory.meta.SkullMeta
 */
package org.arkhamnetwork.Arkkit.patches;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

final class PlayerHeadsListener
        implements Listener {
    PlayerHeadsListener() {
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player pKiller;
        Player p_dead = e.getEntity();
        if (p_dead.getKiller() instanceof Player && this.dropHead(pKiller = p_dead.getKiller())) {
            e.getDrops().add(this.getPlayerHead(p_dead.getName(), p_dead.getKiller()));
        }
    }

    private boolean dropHead(Player killer) {
        int chance = 5;
        if (killer.hasPermission("arkkit.playerheads.chance.10")) {
            chance = 10;
        }
        if (killer.hasPermission("arkkit.playerheads.chance.20")) {
            chance = 20;
        }
        if (killer.hasPermission("arkkit.playerheads.chance.30")) {
            chance = 30;
        }
        if (killer.hasPermission("arkkit.playerheads.chance.40")) {
            chance = 40;
        }
        if (killer.hasPermission("arkkit.playerheads.chance.50")) {
            chance = 50;
        }
        return new Random().nextInt(100) < chance;
    }

    private ItemStack getPlayerHead(String p_name, Player pKiller) {
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta) is.getItemMeta();
        sm.setOwner(p_name);
        sm.setDisplayName(ChatColor.WHITE + "Skull of " + p_name);
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String monthName = month_date.format(PlayerHeads.calendar.getTime());
        sm.setLore(new ArrayList<String>(Arrays.asList(ChatColor.GRAY + "Defeated by " + ChatColor.WHITE + pKiller.getName() + ChatColor.GRAY + " on ", ChatColor.WHITE + monthName + " " + PlayerHeads.calendar.get(5) + ", " + PlayerHeads.calendar.get(1) + ChatColor.GRAY + " at ", ChatColor.WHITE + "" + pKiller.getLocation().getBlockX() + ", " + pKiller.getLocation().getBlockY() + ", " + pKiller.getLocation().getBlockZ() + ChatColor.GRAY + " with a(n) ", ChatColor.WHITE + this.getItemName(pKiller.getItemInHand()) + "!")));
        is.setItemMeta(sm);
        return is;
    }

    private String getItemName(ItemStack i) {
        if (i == null || i.getType() == Material.AIR) {
            return "Fists";
        }
        if (i.hasItemMeta() && i.getItemMeta().hasDisplayName()) {
            return i.getItemMeta().getDisplayName();
        }
        return WordUtils.capitalize(i.getType().name());
    }
}

