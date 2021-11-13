/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  me.ifamasssxd.killtracker.KillTrackerAPI
 *  net.minecraft.server.v1_8_R3.Entity
 *  org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown
 *  org.bukkit.ChatColor
 *  org.bukkit.GameMode
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.craftbukkit.v1_8_R3.CraftWorld
 *  org.bukkit.entity.EnderPearl
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.entity.ProjectileLaunchEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 */
package com.compilex.CosmicDungeons.listeners.items;

import com.compilex.CosmicDungeons.DungeonsAPI;
import com.compilex.CosmicDungeons.listeners.items.EntityEscapistPearl;
import com.compilex.CosmicDungeons.listeners.items.SoulItemListener;
import com.compilex.CosmicDungeons.struct.DungeonDebuff;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.ifamasssxd.killtracker.KillTrackerAPI;
import net.minecraft.server.v1_8_R3.Entity;
import org.arkhamnetwork.Arkkit.patches.EnderpearlCooldown;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class OmniPearlListener
implements Listener {
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        Player player;
        if (event.getEntity().getShooter() instanceof Player && event.getEntity() instanceof EnderPearl && (player = (Player)event.getEntity().getShooter()).getItemInHand() != null && player.getItemInHand().getType() != Material.AIR && new NBTWrapper(player.getItemInHand()).getString("cosmicType").equals("omni_pearl")) {
            event.getEntity().setVelocity(event.getEntity().getVelocity().multiply(1.5f));
        }
    }

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && SoulItemListener.isOmniPearl(event.getItem())) {
            event.setCancelled(true);
            event.setUseItemInHand(Event.Result.DENY);
            if (DungeonsAPI.hasDungeonDebuff(event.getPlayer(), DungeonDebuff.NO_ENDER_PEARLS)) {
                return;
            }
            if (!EnderpearlCooldown.isEnderpearlCooldownOver((Player)event.getPlayer()) || !SoulItemListener.canThrowPearl(event.getItem(), event.getPlayer())) {
                return;
            }
            if (!KillTrackerAPI.hasSoulAmount((Player)event.getPlayer(), (int)5)) {
                event.getPlayer().sendMessage((Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + " *** NOT ENOUGH SOULS " + (Object)ChatColor.RED + "(REQ: 5)" + (Object)ChatColor.BOLD + " ***");
                event.getPlayer().updateInventory();
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.VILLAGER_NO, 1.0f, 1.0f);
                return;
            }
            KillTrackerAPI.removeSoulsFromGems((Player)event.getPlayer(), (int)5);
            event.getPlayer().sendMessage(CC.translate((String)"&c&l- 5 Souls &7(Omni Pearl)"));
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.67f);
            EntityEscapistPearl enderpearl = new EntityEscapistPearl(event.getPlayer());
            ((CraftWorld)event.getPlayer().getLocation().getWorld()).getHandle().addEntity((Entity)enderpearl);
        }
    }
}

