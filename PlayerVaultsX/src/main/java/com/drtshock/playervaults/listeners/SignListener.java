package com.drtshock.playervaults.listeners;

import com.drtshock.playervaults.*;
import org.bukkit.event.player.*;
import com.drtshock.playervaults.commands.*;
import com.drtshock.playervaults.translations.*;
import org.bukkit.command.*;
import com.drtshock.playervaults.vaultmanagement.*;
import org.bukkit.entity.*;
import org.bukkit.block.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.block.*;

public class SignListener implements Listener
{
    private PlayerVaults plugin;
    
    public SignListener(final PlayerVaults plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onInteract(final PlayerInteractEvent playerInteractEvent) {
        final Player player = playerInteractEvent.getPlayer();
        if (playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK && PlayerVaults.getInstance().getInVault().containsKey(player.getUniqueId().toString())) {
            final Block clickedBlock = playerInteractEvent.getClickedBlock();
            if (clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.TRAPPED_CHEST || clickedBlock.getType() == Material.ENDER_CHEST || clickedBlock.getType() == Material.FURNACE || clickedBlock.getType() == Material.BURNING_FURNACE || clickedBlock.getType() == Material.BREWING_STAND || clickedBlock.getType() == Material.ENCHANTMENT_TABLE || clickedBlock.getType() == Material.BEACON) {
                playerInteractEvent.setCancelled(true);
            }
        }
        if (PlayerVaults.getInstance().getSetSign().containsKey(player.getName())) {
            final int chest = PlayerVaults.getInstance().getSetSign().get(player.getName()).getChest();
            final boolean self = PlayerVaults.getInstance().getSetSign().get(player.getName()).isSelf();
            final String s = self ? null : PlayerVaults.getInstance().getSetSign().get(player.getName()).getOwner();
            PlayerVaults.getInstance().getSetSign().remove(player.getName());
            playerInteractEvent.setCancelled(true);
            if (playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (playerInteractEvent.getClickedBlock().getType() == Material.WALL_SIGN || playerInteractEvent.getClickedBlock().getType() == Material.SIGN_POST) {
                    final Location location = ((Sign)playerInteractEvent.getClickedBlock().getState()).getLocation();
                    final String name = location.getWorld().getName();
                    final int blockX = location.getBlockX();
                    final int blockY = location.getBlockY();
                    final int blockZ = location.getBlockZ();
                    if (self) {
                        this.plugin.getSigns().set(name + ";;" + blockX + ";;" + blockY + ";;" + blockZ + ".self", (Object)true);
                    }
                    else {
                        this.plugin.getSigns().set(name + ";;" + blockX + ";;" + blockY + ";;" + blockZ + ".owner", (Object)s);
                    }
                    this.plugin.getSigns().set(name + ";;" + blockX + ";;" + blockY + ";;" + blockZ + ".chest", (Object)chest);
                    this.plugin.saveSigns();
                    player.sendMessage(Lang.TITLE.toString() + Lang.SET_SIGN);
                }
                else {
                    player.sendMessage(Lang.TITLE.toString() + Lang.NOT_A_SIGN);
                }
            }
            else {
                player.sendMessage(Lang.TITLE.toString() + Lang.NOT_A_SIGN);
            }
            return;
        }
        final Block clickedBlock2 = playerInteractEvent.getClickedBlock();
        if (playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK && (clickedBlock2.getType() == Material.WALL_SIGN || clickedBlock2.getType() == Material.SIGN_POST)) {
            final Location location2 = clickedBlock2.getLocation();
            final String name2 = location2.getWorld().getName();
            final int blockX2 = location2.getBlockX();
            final int blockY2 = location2.getBlockY();
            final int blockZ2 = location2.getBlockZ();
            if (this.plugin.getSigns().getKeys(false).contains(name2 + ";;" + blockX2 + ";;" + blockY2 + ";;" + blockZ2)) {
                final int int1 = PlayerVaults.getInstance().getSigns().getInt(name2 + ";;" + blockX2 + ";;" + blockY2 + ";;" + blockZ2 + ".chest", 1);
                if (player.hasPermission("playervaults.signs.use") || player.hasPermission("playervaults.signs.bypass")) {
                    final boolean boolean1 = PlayerVaults.getInstance().getSigns().getBoolean(name2 + ";;" + blockX2 + ";;" + blockY2 + ";;" + blockZ2 + ".self", false);
                    final String replacement = boolean1 ? player.getName() : PlayerVaults.getInstance().getSigns().getString(name2 + ";;" + blockX2 + ";;" + blockY2 + ";;" + blockZ2 + ".owner");
                    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer((replacement != null) ? replacement : playerInteractEvent.getPlayer().getName());
                    if (offlinePlayer == null || !offlinePlayer.hasPlayedBefore()) {
                        player.sendMessage(Lang.TITLE.toString() + Lang.VAULT_DOES_NOT_EXIST.toString());
                        return;
                    }
                    if (boolean1) {
                        if (!VaultOperations.checkPerms((CommandSender)player, int1)) {
                            player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS.toString());
                            return;
                        }
                        final Inventory loadOwnVault = VaultManager.getInstance().loadOwnVault(player, int1, VaultOperations.getMaxVaultSize((OfflinePlayer)player));
                        if (loadOwnVault != null) {
                            player.openInventory(loadOwnVault);
                        }
                    }
                    else {
                        final Inventory loadOtherVault = VaultManager.getInstance().loadOtherVault(offlinePlayer.getUniqueId(), int1, VaultOperations.getMaxVaultSize(offlinePlayer));
                        if (loadOtherVault == null) {
                            player.sendMessage(Lang.TITLE.toString() + Lang.VAULT_DOES_NOT_EXIST.toString());
                        }
                        else {
                            player.openInventory(loadOtherVault);
                        }
                    }
                    PlayerVaults.getInstance().getInVault().put(player.getUniqueId().toString(), new VaultViewInfo(boolean1 ? player.getUniqueId() : offlinePlayer.getUniqueId(), int1));
                    playerInteractEvent.setCancelled(true);
                    player.sendMessage(Lang.TITLE.toString() + Lang.OPEN_WITH_SIGN.toString().replace("%v", String.valueOf(int1)).replace("%p", replacement));
                }
                else {
                    player.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPhysics(final BlockPhysicsEvent blockPhysicsEvent) {
        this.blockChangeCheck(blockPhysicsEvent.getBlock().getLocation());
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityChangeBlock(final EntityChangeBlockEvent entityChangeBlockEvent) {
        this.blockChangeCheck(entityChangeBlockEvent.getBlock().getLocation());
    }
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent blockBreakEvent) {
        this.blockChangeCheck(blockBreakEvent.getBlock().getLocation());
    }
    
    public void blockChangeCheck(final Location location) {
        if (this.plugin.getSigns().getKeys(false).isEmpty()) {
            return;
        }
        final String name = location.getWorld().getName();
        final int blockX = location.getBlockX();
        final int blockY = location.getBlockY();
        final int blockZ = location.getBlockZ();
        if (this.plugin.getSigns().getKeys(false).contains(name + ";;" + blockX + ";;" + blockY + ";;" + blockZ)) {
            this.plugin.getSigns().set(name + ";;" + blockX + ";;" + blockY + ";;" + blockZ, (Object)null);
            this.plugin.saveSigns();
        }
    }
}
