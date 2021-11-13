/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  me.quantiom.util.ItemSkinUtils
 *  net.minecraft.server.v1_8_R3.Entity
 *  org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.craftbukkit.v1_8_R3.CraftWorld
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 */
package com.compilex.CosmicDungeons.listeners.items;

import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.listeners.items.EntityEscapistPearl;
import com.compilex.CosmicDungeons.listeners.items.SoulItemListener;
import com.compilex.CosmicDungeons.struct.DungeonDebuff;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.quantiom.util.ItemSkinUtils;
import net.minecraft.server.v1_8_R3.Entity;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EscapistPearlListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }
        if (event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL && (new NBTWrapper(event.getItem()).getString("cosmicType").equals("escapist_pearl") || ItemSkinUtils.hasItemSkin((Player)event.getPlayer(), (String)"SEXY_BELT")) && EnderpearlCooldown.isEnderpearlCooldownOver((Player)event.getPlayer())) {
            Player player = event.getPlayer();
            if (DungeonsAPI.hasDungeonDebuff(player, DungeonDebuff.NO_ENDER_PEARLS)) {
                return;
            }
            boolean takePearl = true;
            if (ItemSkinUtils.hasItemSkin((Player)event.getPlayer(), (String)"SEXY_BELT") && SoulItemListener.isSoulPearl(player.getInventory().getItemInHand())) {
                takePearl = false;
            }
            if (takePearl) {
                int amount = player.getInventory().getItemInHand().getAmount();
                if (amount < 2) {
                    player.getInventory().setItemInHand(null);
                } else {
                    player.getInventory().getItemInHand().setAmount(amount - 1);
                }
            }
            EntityEscapistPearl enderpearl = new EntityEscapistPearl(player);
            ((CraftWorld)player.getLocation().getWorld()).getHandle().addEntity((Entity)enderpearl);
            event.setCancelled(true);
        }
    }
}

