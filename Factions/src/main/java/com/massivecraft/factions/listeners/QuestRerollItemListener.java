/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.FactionUtils
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.massivecraft.factions.Faction
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.massivecraft.factions.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.P;
import com.massivecraft.factions.manager.QuestMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class QuestRerollItemListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        NBTWrapper wrapper;
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (event.isCancelled() && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }
        if (event.useItemInHand() == Event.Result.DENY) {
            return;
        }
        Player player = event.getPlayer();
        ItemStack clicked = event.getItem();
        if (clicked != null && clicked.getType() == Material.PAPER && (wrapper = new NBTWrapper(clicked)).getString("cosmicType").equals("F_QUEST_REROLL")) {
            event.setCancelled(true);
            if (!player.getWorld().getName().equals(((World)Bukkit.getWorlds().get(0)).getName())) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must claim the Faction Quest Re-roll in the Overworld!");
                return;
            }
            Faction faction = FactionUtils.getFaction((Player)player, (boolean)false);
            if (faction == null || !FactionUtils.isNormal((Faction)faction)) {
                player.sendMessage(CC.Red + "You must be in a faction to use this item!");
                return;
            }
            if (clicked.getAmount() > 1) {
                player.sendMessage(CC.Red + "You cannot claim stacked Faction Quest re-rolls!");
                return;
            }
            player.setItemInHand(null);
            new QuestMenu(player, true).openGUI(P.getP());
            player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.9f);
            player.updateInventory();
        }
    }
}

