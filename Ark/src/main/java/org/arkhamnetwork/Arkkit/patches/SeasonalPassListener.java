/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.google.common.collect.MapMaker
 *  lilypad.client.connect.api.request.Request
 *  lilypad.client.connect.api.request.impl.MessageRequest
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.MapMaker;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lilypad.client.connect.api.request.Request;
import lilypad.client.connect.api.request.impl.MessageRequest;
import org.arkhamnetwork.Arkkit.patches.SeasonalPasses;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

class SeasonalPassListener
implements Listener {
    private String permNodeName = "nebulaplanet";
    private Map<UUID, Long> redeemCooldown = new MapMaker().expireAfterWrite(60L, TimeUnit.MINUTES).makeMap();

    SeasonalPassListener() {
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            Material type;
            ItemStack item = event.getItem();
            Material material = type = item != null ? item.getType() : null;
            if (item == null || type != Material.PAPER || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                return;
            }
            if (new NBTWrapper(item).getString("cosmicType").equals("entryTicket")) {
                event.setCancelled(true);
                if (player.getWorld().getName().contains("world_duels")) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot do this while in the duel world!");
                    return;
                }
                if (player.hasPermission("cosmic.seasonal." + this.permNodeName) || player.hasPermission("cosmic.seasonal." + this.permNodeName + ".lifetime")) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You have already unlocked access to the Seasonal Galaxy!");
                    return;
                }
                if (this.redeemCooldown.containsKey(player.getUniqueId())) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You have already claimed a Seasonal Galaxy ticket!");
                    return;
                }
                this.redeemCooldown.put(player.getUniqueId(), System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10L));
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else {
                    player.setItemInHand(null);
                }
                player.updateInventory();
                String permissionNode = "pex user " + player.getName() + " add cosmic.seasonal." + this.permNodeName + " global";
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)permissionNode);
                player.sendMessage(CC.GreenB + "(!) " + CC.Green + "You have unlocked access to the Seasonal Galaxy!");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.1f);
                try {
                    ((SeasonalPasses)ArkPlugins.SEASONAL_PASSES.getArkPlugin()).getConnect().request((Request)new MessageRequest("nebulaplanet", "cPermU", permissionNode));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

