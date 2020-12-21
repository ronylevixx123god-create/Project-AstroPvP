/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CompilexPermissions.CompilexPermissions
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Conf
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.iface.RelationParticipator
 *  com.massivecraft.factions.struct.ChatMode
 *  com.massivecraft.factions.struct.Relation
 *  com.massivecraft.factions.struct.Role
 *  org.arkhamnetwork.ArkhamMoneyNotes.ArkhamMoneyNotesAPI
 *  org.arkhamnetwork.permissions.ArkhamPermissions
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.DyeColor
 *  org.bukkit.Material
 *  org.bukkit.OfflinePlayer
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CompilexPermissions.CompilexPermissions;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.iface.RelationParticipator;
import com.massivecraft.factions.struct.ChatMode;
import com.massivecraft.factions.struct.Relation;
import com.massivecraft.factions.struct.Role;
import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.arkhamnetwork.ArkhamMoneyNotes.ArkhamMoneyNotesAPI;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.ItemLinking;
import org.arkhamnetwork.Arkkit.patches.TimedInventory;
import org.arkhamnetwork.Arkkit.patches.UUIDStorage;
import org.arkhamnetwork.Arkkit.patches.chat_filter.ChatUtils;
import org.arkhamnetwork.Arkkit.utils.ItemUtils;
import org.arkhamnetwork.permissions.ArkhamPermissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

final class ItemLinkingChatListener
implements Listener {
    private ItemLinking itemLinking;

    public ItemLinkingChatListener(ItemLinking linking) {
        this.itemLinking = linking;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().endsWith("'s Inventory")) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        if (event.getMessage() == null) {
            return;
        }
        String msg = event.getMessage().toLowerCase();
        if (msg.startsWith("/brag ")) {
            String id = msg.split("/brag ")[1].trim();
            event.setCancelled(true);
            event.setMessage("/null");
            this.itemLinking.viewBrag.onCommand((CommandSender)event.getPlayer(), null, "/brag", Lists.newArrayList((Object[])new String[]{id}).toArray(new String[1]));
        }
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onAsyncChatEvent(AsyncPlayerChatEvent e) {
        boolean bragShow;
        boolean itemShow = e.getMessage().toUpperCase().contains(ItemLinking.split);
        boolean bl = bragShow = e.getMessage().toUpperCase().contains(ItemLinking.bragSplit) && ItemLinking.bragEnabled;
        if (bragShow || itemShow && e.getPlayer().getItemInHand().getType() != null & e.getPlayer().getItemInHand().getType() != Material.AIR) {
            e.setCancelled(true);
            Object title = null;
            Player pl = e.getPlayer();
            if (!(pl.hasPermission("itemlinking.senditem") || pl.hasPermission("arkhamjsonitem.senditem") || pl.hasPermission("cosmicpvp.senditem"))) {
                pl.sendMessage((Object)ChatColor.RED + "You do not have permission to link items in chat with " + (bragShow ? "[BRAG]" : "[ITEM]") + "!");
                pl.sendMessage((Object)ChatColor.GRAY + "Purchase a rank at " + (Object)ChatColor.UNDERLINE + "buy.cosmicpvp.com" + (Object)ChatColor.GRAY + " and try again!");
                return;
            }
            if (pl.hasMetadata("last_senditem") && System.currentTimeMillis() - ((MetadataValue)pl.getMetadata("last_senditem").get(0)).asLong() <= 5000L) {
                pl.sendMessage((Object)ChatColor.RED + "You cannot send another " + (bragShow ? "[BRAG]" : "[ITEM]") + " link for " + (5000L - (System.currentTimeMillis() - ((MetadataValue)pl.getMetadata("last_senditem").get(0)).asLong())) + "ms!");
                return;
            }
            pl.setMetadata("last_senditem", (MetadataValue)new FixedMetadataValue((Plugin)Arkkit.get(), (Object)System.currentTimeMillis()));
            if (bragShow) {
                UUIDStorage storage = ItemLinking.recentlyAssignedIds.computeIfAbsent(pl.getUniqueId(), sto -> new UUIDStorage());
                UUID newID = storage.generateUUID();
                ItemLinking.inventoryViews.put(newID, new TimedInventory(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10L), this.createCopy(pl)));
            }
            String message = e.getMessage().replaceAll("(?i)" + Pattern.quote(ItemLinking.split.toLowerCase()), ItemLinking.split).replaceAll("(?i)" + Pattern.quote(ItemLinking.bragSplit.toLowerCase()), ItemLinking.bragSplit);
            FPlayer fplayer = (FPlayer)FPlayers.i.get((OfflinePlayer)pl);
            if (fplayer != null) {
                Faction faction = fplayer.getFaction();
                ChatMode chatMode = fplayer.getChatMode();
                if (chatMode == ChatMode.FACTION) {
                    if (pl.hasMetadata("silentMute")) {
                        ItemLinking.sendItemViaJSON(String.format(Conf.factionChatFormat, fplayer.describeToAsync((RelationParticipator)faction).replace(pl.getName(), ""), pl.getName(), ChatUtils.getFilteredMessage(message, pl, true)), e.getPlayer().getItemInHand(), pl, pl);
                        return;
                    }
                    for (FPlayer fp : faction.getFPlayersWhereOnline(true)) {
                        ItemLinking.sendItemViaJSON(String.format(Conf.factionChatFormat, fplayer.describeToAsync((RelationParticipator)faction).replace(pl.getName(), ""), pl.getName(), ChatUtils.getFilteredMessage(message, fp.getPlayer(), true)), e.getPlayer().getItemInHand(), fp.getPlayer(), pl);
                    }
                    Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor((String)("FactionChat: " + String.format(Conf.factionChatFormat, fplayer.describeToAsync((RelationParticipator)faction).replace(pl.getName(), ""), pl.getName(), message))), (Object)pl);
                    return;
                }
                if (chatMode == ChatMode.ALLIANCE) {
                    if (pl.hasMetadata("silentMute")) {
                        ItemLinking.sendItemViaJSON(String.format(Conf.allianceChatFormat, ChatColor.stripColor((String)fplayer.getNameAndTagAsync()), ChatUtils.getFilteredMessage(message, pl, true)), e.getPlayer().getItemInHand(), pl, pl);
                        return;
                    }
                    for (FPlayer fp : FPlayers.i.getOnline()) {
                        if (!faction.getFPlayers().contains((Object)fp) && faction.getRelationTo((RelationParticipator)fp) != Relation.ALLY) continue;
                        ItemLinking.sendItemViaJSON(String.format(Conf.allianceChatFormat, ChatColor.stripColor((String)fplayer.getNameAndTagAsync()), ChatUtils.getFilteredMessage(message, fp.getPlayer(), true)), e.getPlayer().getItemInHand(), fp.getPlayer(), pl);
                    }
                    Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor((String)("AllianceChat: " + String.format(Conf.allianceChatFormat, ChatColor.stripColor((String)fplayer.getNameAndTagAsync()), message))));
                    return;
                }
                if (chatMode == ChatMode.MOD) {
                    if (!fplayer.getRole().isAtLeast(Role.MODERATOR)) {
                        fplayer.getPlayer().sendMessage((Object)ChatColor.RED + "You must be a faction moderator to speak in /f c m");
                        return;
                    }
                    if (pl.hasMetadata("silentMute")) {
                        ItemLinking.sendItemViaJSON(String.format(Conf.modChatFormat, ChatColor.stripColor((String)fplayer.describeToAsync((RelationParticipator)faction).replace(pl.getName(), "")), pl.getName(), ChatUtils.getFilteredMessage(message, pl, true)), e.getPlayer().getItemInHand(), pl, pl);
                        return;
                    }
                    for (FPlayer fp : faction.getFPlayersWhereOnline(true)) {
                        if (!fp.getRole().isAtLeast(Role.MODERATOR)) continue;
                        ItemLinking.sendItemViaJSON(String.format(Conf.modChatFormat, ChatColor.stripColor((String)fplayer.describeToAsync((RelationParticipator)faction).replace(pl.getName(), "")), pl.getName(), ChatUtils.getFilteredMessage(message, fp.getPlayer(), true)), e.getPlayer().getItemInHand(), fp.getPlayer(), pl);
                    }
                    Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor((String)("ModChat: " + String.format(Conf.modChatFormat, ChatColor.stripColor((String)fplayer.describeToAsync((RelationParticipator)faction).replace(pl.getName(), "")), pl.getName(), message))));
                    return;
                }
                if (chatMode == ChatMode.TRUCE) {
                    if (pl.hasMetadata("silentMute")) {
                        ItemLinking.sendItemViaJSON(String.format(Conf.truceChatFormat, ChatColor.stripColor((String)fplayer.getNameAndTagAsync()), ChatUtils.getFilteredMessage(message, pl, true)), e.getPlayer().getItemInHand(), pl, pl);
                        return;
                    }
                    for (FPlayer fp : FPlayers.i.getOnline()) {
                        if (!faction.getFPlayers().contains((Object)fp) && faction.getRelationTo((RelationParticipator)fp) != Relation.TRUCE) continue;
                        ItemLinking.sendItemViaJSON(String.format(Conf.truceChatFormat, ChatColor.stripColor((String)fplayer.getNameAndTagAsync()), ChatUtils.getFilteredMessage(message, fp.getPlayer(), true)), e.getPlayer().getItemInHand(), fp.getPlayer(), pl);
                    }
                    Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor((String)("TruceChat: " + String.format(Conf.truceChatFormat, ChatColor.stripColor((String)fplayer.getNameAndTagAsync()), message))));
                    return;
                }
            }
            String pg = "";
            if (new File("plugins/CompilexPermissions.jar").exists()) {
                pg = CompilexPermissions.get().getPermissionManager().getUser(pl.getUniqueId()).getPrefix();
            } else if (new File("plugins/ArkhamPermissions.jar").exists()) {
                pg = ArkhamPermissions.getInstance().getPermissionManager().getUser(pl.getUniqueId()).getPrefix();
            }
            ChatColor cc = ChatColor.WHITE;
            String msg = ChatColor.translateAlternateColorCodes((char)'&', (String)ItemLinking.FORMAT.replace("[PREFIX]", pg).replace("[TITLE]", title == null ? "" : title).replace("[NAME]", pl.getName()).replace("[COLOR]", cc == null ? "" : cc.toString())).trim();
            msg = msg.replace((Object)ChatColor.DARK_GRAY + "[" + (Object)ChatColor.DARK_GRAY + "]", "");
            msg = msg.replace((Object)ChatColor.DARK_GRAY + "[" + (Object)ChatColor.RED + "P" + (Object)ChatColor.DARK_GRAY + "]", "");
            msg = msg.replaceAll("  ", " ");
            if (pl.hasPermission("arkhamnetwork.joker_perks")) {
                Bukkit.broadcastMessage((String)"");
            }
            if (pl.hasMetadata("silentMute")) {
                ItemLinking.sendItemViaJSON(msg.replace("[MESSAGE]", ChatUtils.getFilteredMessage(ChatColor.stripColor((String)message), pl, true)), e.getPlayer().getItemInHand(), pl, pl);
                return;
            }
            for (Player p : e.getRecipients()) {
                ItemLinking.sendItemViaJSON(msg.replace("[MESSAGE]", ChatUtils.getFilteredMessage(ChatColor.stripColor((String)message), p, true)), e.getPlayer().getItemInHand(), p, pl);
            }
            if (pl.hasPermission("arkhamnetwork.joker_perks")) {
                Bukkit.broadcastMessage((String)"");
            }
        }
    }

    public void createNewBragInventory(Player player) {
        UUIDStorage storage = ItemLinking.recentlyAssignedIds.computeIfAbsent(player.getUniqueId(), sto -> new UUIDStorage());
        UUID newID = storage.generateUUID();
        ItemLinking.inventoryViews.put(newID, new TimedInventory(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10L), this.createCopy(player)));
    }

    public Inventory createCopy(Player player) {
        ItemStack item;
        Inventory inventory = Bukkit.createInventory(null, (int)(player.getInventory().getSize() + 9), (String)(player.getName() + "'s Inventory"));
        ItemStack[] armor = player.getEquipment().getArmorContents();
        ItemStack[] items = player.getInventory().getContents();
        for (int i = 0; i < items.length; ++i) {
            item = items[i];
            if (item != null && item.getType() != Material.AIR) {
                item = item.clone();
            }
            inventory.setItem(i, item);
        }
        for (int slot = inventory.getSize() - 9; slot < inventory.getSize(); ++slot) {
            item = inventory.getItem(slot);
            if (item != null && item.getType() != Material.AIR) continue;
            inventory.setItem(slot, ItemUtils.buildItem(Material.STAINED_GLASS_PANE, DyeColor.GRAY.getWoolData(), " ", ""));
        }
        inventory.setItem(inventory.getSize() - 7, armor[3]);
        inventory.setItem(inventory.getSize() - 6, armor[2]);
        inventory.setItem(inventory.getSize() - 4, armor[1]);
        inventory.setItem(inventory.getSize() - 3, armor[0]);
        return inventory;
    }

    private long getMoney(Player p) {
        long bal = 0L;
        if (Bukkit.getPluginManager().isPluginEnabled("ArkhamMoneyNotes")) {
            bal += ArkhamMoneyNotesAPI.getAllMoneyNoteValues((Player)p);
        }
        bal = (long)((double)bal + ItemLinking.economy.getBalance((OfflinePlayer)p));
        return bal;
    }

    private boolean hasRealNickname(Player p) {
        return p.getDisplayName() != null && p.getDisplayName().contains("~");
    }

    private String getActualNickname(Player p) {
        if (this.hasRealNickname(p)) {
            String nick = p.getDisplayName().split("~")[1];
            return "~" + nick;
        }
        return p.getName();
    }
}

