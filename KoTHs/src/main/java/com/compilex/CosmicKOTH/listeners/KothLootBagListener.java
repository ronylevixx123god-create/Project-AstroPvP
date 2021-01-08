/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang.StringUtils
 *  org.arkhamnetwork.Arkkit.libs.ParticleEffect
 *  org.arkhamnetwork.Arkkit.patches.ItemLinking
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package com.compilex.CosmicKOTH.listeners;

import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicKOTH.utils.RewardUtils;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.libs.ParticleEffect;
import org.arkhamnetwork.Arkkit.patches.ItemLinking;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class KothLootBagListener
implements Listener {
    @EventHandler
    public void onPlayerOpenLootBag(PlayerInteractEvent e) {
        if (e.hasItem() && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && RewardUtils.isKOTHLootbag(e.getItem())) {
            Player p = e.getPlayer();
            e.setCancelled(true);
            e.setUseItemInHand(Event.Result.DENY);
            e.setUseInteractedBlock(Event.Result.DENY);
            int emptySlots = this.getEmptyInventorySlots(p);
            if (emptySlots < 10) {
                p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.RED + "You need >=10 empty inventory slots to open this Lootbag.");
                p.sendMessage((Object)ChatColor.GRAY + "You currently have " + (Object)ChatColor.UNDERLINE + emptySlots + (Object)ChatColor.GRAY + " empty slots.");
                return;
            }
            if (e.getItem().getAmount() > 1) {
                ItemStack i = e.getItem();
                i.setAmount(i.getAmount() - 1);
                p.setItemInHand(i);
            } else {
                p.setItemInHand((ItemStack)null);
            }
            List<ItemStack> rewards = CosmicKOTHPlugin.isVanillaGalaxy() ? RewardUtils.getVanillaKOTHLoot(p) : RewardUtils.getKOTHLoot(p);
            for (ItemStack j : rewards) {
                if (j == null || j.getType() == Material.AIR) continue;
                if (p.getInventory().firstEmpty() != -1) {
                    p.getInventory().addItem(new ItemStack[]{j});
                    continue;
                }
                p.getWorld().dropItem(p.getLocation(), j);
            }
            Bukkit.broadcastMessage((String)"");
            Bukkit.broadcastMessage((String)(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + " " + (Object)ChatColor.BOLD + p.getName() + (Object)ChatColor.GREEN + " has opened a " + (Object)ChatColor.UNDERLINE + "KOTH Lootbag!"));
            Bukkit.broadcastMessage((String)(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + (Object)ChatColor.UNDERLINE + "Rewards:"));
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                for (ItemStack k : rewards) {
                    String itemName;
                    String string = itemName = k.hasItemMeta() && k.getItemMeta().hasDisplayName() ? k.getItemMeta().getDisplayName() : StringUtils.capitaliseAllWords((String)k.getType().name().toLowerCase().replace("_", " "));
                    if (k.getType() == Material.ENCHANTED_BOOK) {
                        itemName = "Depth Strider Enchantment Book";
                    }
                    ItemLinking.sendItemViaJSON((String)(ChatColor.GREEN.toString() + (Object)ChatColor.BOLD + "* " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + k.getAmount() + "x " + (Object)ChatColor.RESET + itemName), (ItemStack)k, (Player)pl);
                }
            }
            Bukkit.broadcastMessage((String)"");
            p.updateInventory();
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.HAPPY_VILLAGER, (Location)p.getLocation().add(0.0, 1.0, 0.0), (float)0.7f, (int)30);
            ParticleEffect.playEffect((ParticleEffect)ParticleEffect.FIREWORKS_SPARK, (Location)p.getLocation().add(0.0, 1.0, 0.0), (float)0.8f, (int)50);
            p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0f, 2.0f);
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.2f);
        }
    }

    public final int getEmptyInventorySlots(Player p) {
        int emptySlots = 0;
        for (int i = 0; i < 36; ++i) {
            if (p.getInventory().getItem(i) != null && p.getInventory().getItem(i).getType() != Material.AIR) continue;
            ++emptySlots;
        }
        return emptySlots;
    }
}

