/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.ItemUtils
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  com.cosmicpvp.cosmicutils.utils.PlayerUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.block.Action
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.inventory.ItemStack
 */
package org.arkhamnetwork.Arkkit.patches;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import java.util.List;
import org.arkhamnetwork.Arkkit.libs.ArkPluginListener;
import org.arkhamnetwork.Arkkit.libs.JSONMessage;
import org.arkhamnetwork.Arkkit.patches.ItemID;
import org.arkhamnetwork.Arkkit.patches.ResetKit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

final class ResetKitListener
extends ArkPluginListener<ResetKit> {
    ResetKitListener(ResetKit patch) {
        super(patch);
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=false)
    public void onPlayerInteract(PlayerInteractEvent event) {
        NBTWrapper wrapper;
        ItemStack item = event.getItem();
        if (item != null && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && item.getType() == Material.CHEST && (wrapper = new NBTWrapper(item)).getString("cosmicType").equals("RESET_KIT")) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            if (!player.getWorld().getName().equals(((World)Bukkit.getWorlds().get(0)).getName())) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You can only redeem Reset Kits in the Overworld!");
                return;
            }
            if (item.getAmount() > 1) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You cannot open stacked Reset Kits!");
                return;
            }
            int empty = PlayerUtil.getEmptyInventorySlots((Player)player);
            if (empty < 5) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You must have at least 5 empty inventory spaces to open a Reset Kit!");
                return;
            }
            long iid = wrapper.getIID();
            if (wrapper.isBlacklistedIID() && !player.isOp()) {
                player.sendMessage(CC.RedB + "(!) " + CC.Red + "You have attempted to claim an already opened Reset Kit!");
                Bukkit.getLogger().info("[Arkkit (ResetKits)] Removing duplicate IID from " + player.getName() + " IID: " + iid);
                return;
            }
            List<ItemStack> loot = ((ResetKit)this.patch).getResetKitItems(player);
            if (iid != -1L) {
                ItemID.blacklistIID(iid, "resetkit claim " + player.getName());
            }
            player.setItemInHand(null);
            player.sendMessage("");
            player.sendMessage(CC.Gold + "You examine the Reset Kit cache and discover...");
            for (ItemStack is : loot) {
                if (is == null) continue;
                PlayerUtil.giveOrDropProtectedItem((Player)player, (ItemStack)is, (int)-1);
                JSONMessage message = new JSONMessage("");
                message.addText(null, CC.GoldB + "* " + CC.White, ChatColor.WHITE);
                message.addItem(is, CC.White + ItemUtils.getDisplayName((ItemStack)is), ChatColor.WHITE);
                message.sendToPlayer(player);
            }
            player.sendMessage("");
            player.updateInventory();
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.4f);
        }
    }
}

