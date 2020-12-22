/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.earth2me.essentials.User
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.struct.Role
 *  markehme.factionsplus.Cmds.CmdWarp
 *  org.apache.commons.lang.StringUtils
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.ClickType
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryCloseEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.earth2me.essentials.User;
import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Role;
import java.util.List;
import markehme.factionsplus.Cmds.CmdWarp;
import org.apache.commons.lang.StringUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.CustomWarps;
import org.arkhamnetwork.Arkkit.patches.WarpGUIManager;
import org.arkhamnetwork.Arkkit.utils.EssentialsUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

class WarpGUIListener
implements Listener {
    private List<String> aliases = Lists.newArrayList(new String[]{"/f warp", "/f warps"});
    private WarpGUIManager manager;

    public WarpGUIListener(WarpGUIManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (this.aliases.contains(event.getMessage().toLowerCase())) {
            event.setCancelled(true);
            event.setMessage("/null");
            Player player = event.getPlayer();
            player.openInventory(this.manager.getWarpInventory(player));
        }
    }

    @EventHandler
    public void onPlayerClose(InventoryCloseEvent event) {
        if (event.getPlayer().hasMetadata("custom_warp")) {
            event.getPlayer().removeMetadata("custom_warp", (Plugin)Arkkit.get());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equals("Faction Warps")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
                return;
            }
            if (event.getRawSlot() < 0 || event.getRawSlot() >= event.getInventory().getSize()) {
                return;
            }
            Player player = (Player)event.getWhoClicked();
            FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
            if (fplayer.getFaction() == null || !fplayer.getFaction().isNormal()) {
                player.closeInventory();
                return;
            }
            String warpName = ChatColor.stripColor((String)item.getItemMeta().getDisplayName());
            if (CmdWarp.getWarp((Faction)fplayer.getFaction(), (String)warpName) == null) {
                player.sendMessage((Object)ChatColor.RED + "That warp is no longer valid!");
                player.closeInventory();
                return;
            }
            if (event.getClick() == ClickType.RIGHT) {
                if (!fplayer.getRole().isAtLeast(Role.MODERATOR)) {
                    player.sendMessage((Object)ChatColor.RED + "You must be atleast Faction Moderator to change warp icons!");
                    return;
                }
                player.openInventory(this.manager.getMaterialSelection());
                player.setMetadata("custom_warp", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)warpName));
                player.playSound(player.getLocation(), Sound.LAVA_POP, 1.0f, 1.0f);
            } else {
                player.closeInventory();
                player.chat("/f warp " + warpName);
            }
        } else if (event.getInventory().getName().equals("Select Warp Icon")) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null || item.getType() == Material.AIR) {
                return;
            }
            if (event.getRawSlot() < 0 || event.getRawSlot() >= event.getInventory().getSize()) {
                return;
            }
            Player player = (Player)event.getWhoClicked();
            FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)player);
            if (fplayer.getFaction() == null || !fplayer.getFaction().isNormal()) {
                player.closeInventory();
                return;
            }
            if (player.hasMetadata("custom_warp")) {
                String warpName = ((MetadataValue)player.getMetadata("custom_warp").get(0)).asString();
                if (CmdWarp.getWarp((Faction)fplayer.getFaction(), (String)warpName) == null) {
                    player.sendMessage((Object)ChatColor.RED + "That warp is no longer valid!");
                    player.closeInventory();
                    return;
                }
                CustomWarps customWarps = this.manager.getCustomWarps().get(fplayer.getFaction().getId());
                if (customWarps == null) {
                    customWarps = new CustomWarps();
                    this.manager.getCustomWarps().put(fplayer.getFaction().getId(), customWarps);
                }
                customWarps.put(warpName, item.getType());
                player.sendMessage((Object)ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + (Object)ChatColor.GREEN + "Saved " + warpName + " faction warp icon to " + (Object)ChatColor.GREEN + ChatColor.UNDERLINE.toString() + StringUtils.capitaliseAllWords((String)item.getType().name().toLowerCase().replace("_", " ")));
                player.playSound(player.getLocation(), Sound.CLICK, 1.0f, 0.8f);
                player.removeMetadata("custom_warp", (Plugin)Arkkit.get());
                player.closeInventory();
                player.chat("/f warp");
            } else {
                player.closeInventory();
            }
        }
    }

    private boolean isValidHome(Player player, String name) {
        User user = EssentialsUtils.getEssentials().getUser(player);
        try {
            return user.getHome(name) != null;
        }
        catch (Exception e) {
            return false;
        }
    }
}

