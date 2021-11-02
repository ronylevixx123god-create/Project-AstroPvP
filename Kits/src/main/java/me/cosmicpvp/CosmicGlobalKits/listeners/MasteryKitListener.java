/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent
 *  com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.CC
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.me.astropvp.cratekeys.utils.RandomFireworks
 *  org.arkhamnetwork.Arkkit.patches.ItemID
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.me.astropvp.cratekeys.inventory.ItemStack
 */
package me.cosmicpvp.CosmicGlobalKits.listeners;

import com.cosmicpvp.cosmicutils.customevents.CustomCosmicPlayerEvent;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.RandomFireworks;
import me.cosmicpvp.CosmicGlobalKits.CosmicGlobalKitsAPI;
import me.cosmicpvp.CosmicGlobalKits.libs.GlobalKits;
import me.cosmicpvp.CosmicGlobalKits.libs.MasteryKits;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MasteryKitListener
implements Listener {
    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack clicked = event.getItem();
            if (clicked == null || clicked.getType() == Material.AIR) {
                return;
            }
            Player player = event.getPlayer();
            NBTWrapper wrapper = new NBTWrapper(clicked);
            if (wrapper.getString("cosmicType").equalsIgnoreCase("MASTERY_KIT")) {
                event.setCancelled(true);
                NBTTagCompoundBuilder cosmicData = new NBTTagCompoundBuilder(wrapper.getNBTBase("conaxData"));
                String masteryKit = cosmicData.getString("kit");
                if (masteryKit == null || masteryKit.isEmpty()) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "Invalid Mastery Kit item detected!");
                    return;
                }
                MasteryKits kit = MasteryKits.getFromName(masteryKit);
                if (kit == null) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "Invalid Mastery Kit item detected!");
                    return;
                }
                if (player.getWorld().getName().contains("world_duels")) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot redeem this item while in a duel.");
                    return;
                }
                if (kit.hasPermission(player)) {
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You have already unlocked access to this Mastery Kit!");
                    player.sendMessage(CC.Gray + "Use '/mkit' to claim your kit!");
                    return;
                }
                for (GlobalKits kits : kit.getKitsRequired()) {
                    if (kits.hasAccess(player) && CosmicGlobalKitsAPI.getKitTier(player, kits) >= 3) continue;
                    player.sendMessage(CC.RedB + "(!) " + CC.Red + "You do not have the required '" + kits.getItemName() + CC.Red + "' Tier III /gkit unlocked!");
                    player.sendMessage(CC.Gray + "Purchase a Fallen Hero at " + CC.GrayU + "buy.astropvp.net" + CC.Gray + " to unlock and level-up your /gkit!");
                    return;
                }
                if (ArkPlugins.ITEM_ID.getArkPlugin().isRunning()) {
                    long iid = wrapper.getIID();
                    if (wrapper.isBlacklistedIID()) {
                        player.sendMessage(CC.RedB + "(!) " + CC.Red + "You attempted to use an already claimed item!");
                        player.getInventory().removeItem(new ItemStack[]{clicked});
                        player.updateInventory();
                        Bukkit.getLogger().info("Removing duped Mastery Kit Item from " + player.getName() + ": " + (Object)clicked + " IID: " + iid);
                        return;
                    }
                    ItemID.blacklistIID((long)iid, (String)("mastery kit claimed " + player.getName()));
                }
                if (clicked.getAmount() > 1) {
                    clicked.setAmount(clicked.getAmount() - 1);
                } else {
                    player.setItemInHand(null);
                }
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You have lost access to the following Global Kits:");
                for (GlobalKits gkit : kit.getKitsRequired()) {
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("pex user " + player.getName() + " remove " + gkit.getPermissionString()));
                    player.sendMessage(CC.Gold + " - " + gkit.getItemName());
                }
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)("pex user " + player.getName() + " add " + kit.getPermissionString()));
                player.sendMessage(CC.GoldB + "(!) " + CC.Gold + "You UNLOCKED access to the " + kit.getName() + CC.Gold + " Mastery Kit!");
                player.sendMessage(CC.Gray + "Use /mkit to redeem your new kit!");
                Bukkit.getLogger().info("[MKitUnlock] " + player.getName() + " (" + player.getUniqueId() + ") redeemed a " + kit.getName() + " kit item!");
                RandomFireworks.getManager().launchRandomFirework(player.getLocation());
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.1f);


                player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + kit.getName());
                player.sendMessage(ChatColor.YELLOW + "Mastery Kit Unlocked!");

                player.updateInventory();
                Bukkit.getPluginManager().callEvent((Event)new CustomCosmicPlayerEvent(player, "ITEM_USE", new Object[]{"MASTERY_KIT", kit.name()}));
            }
        }
    }
}

