/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.compilex.CosmicKOTH.CosmicKOTHAPI
 *  com.compilex.CosmicKOTH.CosmicKOTHPlugin
 *  com.compilex.CosmicKOTH.tasks.DateWatcherTask
 *  com.compilex.CosmicKOTH.tasks.KothEventTask
 *  com.compilex.CosmicKOTH.utils.TimeUtils
 *  com.compilex.CosmicOutposts.CosmicOutpostsPlugin
 *  com.earth2me.essentials.commands.WarpNotFoundException
 *  net.ess3.api.InvalidWorldException
 *  net.minecraft.util.org.apache.commons.lang3.text.WordUtils
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World$Environment
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event$Result
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.inventory.InventoryOpenEvent
 *  org.bukkit.event.player.PlayerCommandPreprocessEvent
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.inventory.meta.ItemMeta
 *  org.bukkit.plugin.Plugin
 */
package org.arkhamnetwork.Arkkit.patches;

import com.compilex.CosmicKOTH.CosmicKOTHAPI;
import com.compilex.CosmicKOTH.CosmicKOTHPlugin;
import com.compilex.CosmicKOTH.tasks.DateWatcherTask;
import com.compilex.CosmicKOTH.tasks.KothEventTask;
import com.compilex.CosmicKOTH.utils.TimeUtils;
import com.compilex.CosmicOutposts.CosmicOutpostsPlugin;
import com.earth2me.essentials.commands.WarpNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.ess3.api.InvalidWorldException;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.EssentialsWarpUI;
import org.arkhamnetwork.Arkkit.patches.WarpData;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.arkhamnetwork.Arkkit.utils.WorldGuardUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

final class EssentialsWarpUIListener
implements Listener {
    private HashMap<String, List<Player>> warpPlayers;
    private HashMap<String, List<Player>> warpPvPPlayers;
    private HashMap<String, Long> warpPlayerCache;
    private Inventory cachedWarpInventory = null;
    private long warpInventoryCacheExpire = 0L;
    private LinkedHashMap<String, WarpData> warps = new LinkedHashMap<String, WarpData>(){
        {
            this.put("hills", new WarpData("hills", (Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + "Hills", Material.getMaterial((int)98), "&aA vanilla forest biome,", "&agood for more casual PvP/PvE."));
            this.put("icyplains", new WarpData("icyplains", (Object)ChatColor.AQUA + "" + (Object)ChatColor.BOLD + "Icy Plains", Material.PACKED_ICE, "&bAn icy biome, freezing cold,", "&bgood for mid-range PvP."));
            this.put("desert", new WarpData("desert", (Object)ChatColor.GOLD + "" + (Object)ChatColor.BOLD + "Desert", Material.SANDSTONE, "&6An expansive flat desert biome,", "&6perfect for intense PvP."));
            this.put("beach", new WarpData("beach", (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + "Beach", Material.SAND, "&eA flat, wet beach biome,", "&eperfect for intense water PvP."));
            this.put("plains", new WarpData("plains", (Object)ChatColor.GREEN + "" + (Object)ChatColor.BOLD + "Plains", Material.GRASS, "&aA vanilla flat plains biome,", "&aperfect for extreme PvP."));
            this.put("snowyhills", new WarpData("snowyhills", (Object)ChatColor.AQUA + "" + (Object)ChatColor.BOLD + "Snowy Hills", Material.PACKED_ICE, "&bAn icy biome, freezing cold,", "&bgood for mid-range PvP."));
            this.put("swamp", new WarpData("swamp", (Object)ChatColor.DARK_GREEN + "" + (Object)ChatColor.BOLD + "Swamp", Material.MOSSY_COBBLESTONE, "&2A deep swamp biome,", "&2good for more casual PvP/PvE."));
        }
    };

    EssentialsWarpUIListener() {
        this.warpPlayers = new HashMap();
        this.warpPvPPlayers = new HashMap();
        this.warpPlayerCache = new HashMap();
    }

    @EventHandler
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().toLowerCase().equals("/warp") || e.getMessage().toLowerCase().equals("/warps") || e.getMessage().toLowerCase().equals("/ewarp") || e.getMessage().toLowerCase().equals("/essentials:warp") || e.getMessage().toLowerCase().equals("/essentials:ewarp")) {
            Player pl = e.getPlayer();
            e.setCancelled(true);
            pl.playSound(pl.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.67f);
            pl.openInventory(this.buildNewWarpInventory());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equals("Cosmic Warps")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {
                final String warp = this.getWarpName(e.getCurrentItem());
                if (warp == null) {
                    return;
                }
                final Player pl = (Player)e.getWhoClicked();
                pl.playSound(pl.getLocation(), Sound.CLICK, 1.0f, 0.75f);
                pl.closeInventory();
                Bukkit.getScheduler().runTaskLater((Plugin)Arkkit.get(), new Runnable(){

                    @Override
                    public void run() {
                        pl.chat("/warp " + warp.toLowerCase());
                    }
                }, 1L);
            }
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory().getTitle().equals("Cosmic Warps")) {
            e.setCancelled(false);
        }
    }

    private String getWarpName(ItemStack warp_item) {
        if (warp_item == null || !warp_item.hasItemMeta() || !warp_item.getItemMeta().hasDisplayName()) {
            return null;
        }
        if (warp_item.getItemMeta().getDisplayName().contains("Icy Plains")) {
            return "icyplains";
        }
        if (warp_item.getItemMeta().getDisplayName().contains("Snowy Hills")) {
            return "snowyhills";
        }
        return ChatColor.stripColor((String)warp_item.getItemMeta().getDisplayName()).split(" ")[0];
    }

    private Inventory buildWarpInventory(List<ItemStack> warp_items) {
        int size = this.getInvSize(warp_items.size());
        Inventory inv = Bukkit.createInventory(null, (int)size, (String)"Cosmic Warps");
        for (ItemStack is : warp_items) {
            inv.addItem(new ItemStack[]{is});
        }
        return inv;
    }

    private Inventory buildNewWarpInventory() {
        if (System.currentTimeMillis() < this.warpInventoryCacheExpire) {
            return this.cachedWarpInventory;
        }
        Inventory inv = Bukkit.createInventory(null, (int)9, (String)"Cosmic Warps");
        for (Map.Entry<String, WarpData> data : this.warps.entrySet()) {
            try {
                if (EssentialsWarpUI.ess.getWarps().getWarp(data.getKey()) == null || inv.firstEmpty() == -1) continue;
                inv.setItem(inv.firstEmpty(), this.getWarpItem(data.getValue()));
            }
            catch (WarpNotFoundException | InvalidWorldException e) {
                Bukkit.getLogger().info("[EssentiaslWarpUI] Invalid /warp: " + data.getKey());
            }
        }
        inv.setItem(inv.firstEmpty(), this.getWarpItem("nether", Material.NETHERRACK, ChatColor.RED, "&cA hellish dimension, good", "&cfor collecting rare resources."));
        if (ArkPlugins.END_GRINDING.getArkPlugin().isRunning()) {
            inv.setItem(inv.firstEmpty(), this.getEndWarpItem("end", Material.EYE_OF_ENDER, ChatColor.LIGHT_PURPLE, "&dA demonic world, inhabited", "&dby rare monsters and very", "&ddangerous PvP masters."));
        }
        try {
            EssentialsWarpUI.ess.getWarps().getWarp("battlefield");
            inv.setItem(6, this.getWarpItem("battlefield", Material.DIAMOND_SWORD, ChatColor.DARK_RED, "&4A dangerous, sealed dome arena.", "&4Events are often held here."));
        }
        catch (WarpNotFoundException | InvalidWorldException err) {
            Bukkit.getLogger().info("[Arkkit (EssentialsWarpUI)] Skipping registration of battlefield /warp due to no /warp battlefield.");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicOutposts")) {
            inv.setItem(7, this.getSpecialWarpItem("outpost", Material.DIAMOND_CHESTPLATE, ChatColor.YELLOW, "&e24/7 running, capturable PvP zones.", "&eUse /outpost for more information.", "", "&c&lVanilla:", (CosmicOutpostsPlugin.get_().getVanillaOutpost().getCapStatus().contains("CONTROLLED") ? CosmicOutpostsPlugin.get_().getVanillaOutpost().getCapStatus() + (Object)ChatColor.GRAY + " (" + (Object)ChatColor.GREEN + CosmicOutpostsPlugin.get_().getVanillaOutpost().getControllingFaction() + (Object)ChatColor.GRAY + ")" : CosmicOutpostsPlugin.get_().getVanillaOutpost().getCapStatus()) + (Object)ChatColor.GRAY + " [" + CosmicOutpostsPlugin.get_().getVanillaOutpost().getCapTask().getCapPercent() * 100.0f + "%]", "", "&8&lTrainee:", (CosmicOutpostsPlugin.get_().getTraineeOutpost().getCapStatus().contains("CONTROLLED") ? CosmicOutpostsPlugin.get_().getTraineeOutpost().getCapStatus() + (Object)ChatColor.GRAY + " (" + (Object)ChatColor.GREEN + CosmicOutpostsPlugin.get_().getTraineeOutpost().getControllingFaction() + (Object)ChatColor.GRAY + ")" : CosmicOutpostsPlugin.get_().getTraineeOutpost().getCapStatus()) + (Object)ChatColor.GRAY + " [" + CosmicOutpostsPlugin.get_().getTraineeOutpost().getCapTask().getCapPercent() * 100.0f + "%]", "", "&b&lHero:", (CosmicOutpostsPlugin.get_().getHeroOutpost().getCapStatus().contains("CONTROLLED") ? CosmicOutpostsPlugin.get_().getHeroOutpost().getCapStatus() + (Object)ChatColor.GRAY + " (" + (Object)ChatColor.GREEN + CosmicOutpostsPlugin.get_().getHeroOutpost().getControllingFaction() + (Object)ChatColor.GRAY + ")" : CosmicOutpostsPlugin.get_().getHeroOutpost().getCapStatus()) + (Object)ChatColor.GRAY + " [" + CosmicOutpostsPlugin.get_().getHeroOutpost().getCapTask().getCapPercent() * 100.0f + "%]", "", "&6&lCosmonaut:", (CosmicOutpostsPlugin.get_().getCosmonautOutpost().getCapStatus().contains("CONTROLLED") ? CosmicOutpostsPlugin.get_().getCosmonautOutpost().getCapStatus() + (Object)ChatColor.GRAY + " (" + (Object)ChatColor.GREEN + CosmicOutpostsPlugin.get_().getCosmonautOutpost().getControllingFaction() + (Object)ChatColor.GRAY + ")" : CosmicOutpostsPlugin.get_().getCosmonautOutpost().getCapStatus()) + (Object)ChatColor.GRAY + " [" + CosmicOutpostsPlugin.get_().getCosmonautOutpost().getCapTask().getCapPercent() * 100.0f + "%]"));
        }
        String status = CosmicKOTHAPI.canTeleportToKOTH() ? ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "OPEN" : ChatColor.RED.toString() + ChatColor.BOLD.toString() + "CLOSED";
        String flagStatus = null;
        if (CosmicKOTHAPI.isKOTHRunning()) {
            KothEventTask KET = CosmicKOTHPlugin.get_().getKoth();
            if (KET.getPlayerCapped() != null) {
                flagStatus = ChatColor.RED.toString() + ChatColor.BOLD.toString() + "CAPTURED " + (Object)ChatColor.GRAY + "(" + (Object)ChatColor.LIGHT_PURPLE + KET.getNameOfFlagCapper() + (Object)ChatColor.GRAY + ")";
            }
            if (KET.getPlayerCapping() != null) {
                flagStatus = ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "CAPTURING " + (Object)ChatColor.GRAY + "(" + (Object)ChatColor.LIGHT_PURPLE + KET.getNameOfFlagCapper() + (Object)ChatColor.GRAY + ") [" + (Object)ChatColor.LIGHT_PURPLE + TimeUtils.getSecondsAsString((long)KET.getSecondsUntilCap()) + (Object)ChatColor.GRAY + " left]";
            }
            if (flagStatus == null) {
                flagStatus = ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "UNCLAIMED";
            }
        }
        inv.setItem(8, this.getSpecialWarpItem("koth", Material.DIAMOND, ChatColor.LIGHT_PURPLE, "&dKing of the  Hill PvP Event.", "&dUse /koth for more information.", "", "&d&lKOTH Status: " + status, CosmicKOTHAPI.isKOTHRunning() ? "&dFlag: " + flagStatus : "&dNext Scheduled KOTH: &c&l" + DateWatcherTask.getTimeUntilNextKOTH()));
        for (int i = 0; i < inv.getSize(); ++i) {
            if (inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR) continue;
            inv.setItem(i, this.buildItem(Material.STAINED_GLASS_PANE, (short)7, (Object)ChatColor.DARK_GRAY + "" + (Object)ChatColor.BOLD + "???", null));
        }
        this.cachedWarpInventory = inv;
        this.warpInventoryCacheExpire = System.currentTimeMillis() + 5000L;
        return inv;
    }

    private ItemStack getWarpItem(String warpName, Material m, ChatColor warpColor, String warpDescription1, String warpDescription2) {
        Material warpMaterial = m;
        try {
            Location warpLoc = EssentialsWarpUI.ess.getWarps().getWarp(warpName);
            String itemWarpName = warpName;
            if (warpName.equals("icyplains") || warpName.equals("iceyplains")) {
                itemWarpName = "Icy Plains";
            }
            return this.buildItem(warpMaterial, (short)0, (Object)warpColor + "" + (Object)ChatColor.BOLD + WordUtils.capitalize((String)itemWarpName) + " /warp", (Object)ChatColor.YELLOW + "" + warpLoc.getBlockX() + (Object)ChatColor.BOLD + "x" + (Object)ChatColor.YELLOW + ", " + warpLoc.getBlockZ() + (Object)ChatColor.BOLD + "z", "", ChatColor.translateAlternateColorCodes((char)'&', (String)warpDescription1), ChatColor.translateAlternateColorCodes((char)'&', (String)warpDescription2), "", (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + this.getPlayersAtWarp(warpName, true).size() + (Object)ChatColor.YELLOW + " nearby players(s)", (Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + this.getPlayersAtWarp(warpName, false).size() + (Object)ChatColor.RED + " PvP-enabled player(s)", "", (Object)ChatColor.GRAY + "Click to teleport to this warp!");
        }
        catch (WarpNotFoundException | InvalidWorldException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ItemStack getWarpItem(WarpData warpData) {
        Material warpMaterial = warpData.getDisplayMaterial();
        try {
            Location warpLoc = EssentialsWarpUI.ess.getWarps().getWarp(warpData.getWarpName());
            String itemWarpName = warpData.getDisplayName();
            String warpDescription1 = warpData.getDescription()[0];
            String warpDescription2 = warpData.getDescription()[1];
            return this.buildItem(warpMaterial, (short)0, warpData.getDisplayName() + " /warp", (Object)ChatColor.YELLOW + "" + warpLoc.getBlockX() + (Object)ChatColor.BOLD + "x" + (Object)ChatColor.YELLOW + ", " + warpLoc.getBlockZ() + (Object)ChatColor.BOLD + "z", "", ChatColor.translateAlternateColorCodes((char)'&', (String)warpDescription1), ChatColor.translateAlternateColorCodes((char)'&', (String)warpDescription2), "", (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + this.getPlayersAtWarp(warpData.getWarpName(), true).size() + (Object)ChatColor.YELLOW + " nearby players(s)", (Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + this.getPlayersAtWarp(warpData.getWarpName(), false).size() + (Object)ChatColor.RED + " PvP-enabled player(s)", "", (Object)ChatColor.GRAY + "Click to teleport to this warp!");
        }
        catch (WarpNotFoundException | InvalidWorldException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ItemStack getEndWarpItem(String warpName, Material m, ChatColor warpColor, String warpDescription1, String warpDescription2, String warpDescription3) {
        Material warpMaterial = m;
        try {
            Location warpLoc = EssentialsWarpUI.ess.getWarps().getWarp(warpName);
            String itemWarpName = warpName;
            return this.buildItem(warpMaterial, (short)0, (Object)warpColor + "" + (Object)ChatColor.BOLD + WordUtils.capitalize((String)itemWarpName) + " /warp", (Object)ChatColor.YELLOW + "" + warpLoc.getBlockX() + (Object)ChatColor.BOLD + "x" + (Object)ChatColor.YELLOW + ", " + warpLoc.getBlockZ() + (Object)ChatColor.BOLD + "z", "", ChatColor.translateAlternateColorCodes((char)'&', (String)warpDescription1), ChatColor.translateAlternateColorCodes((char)'&', (String)warpDescription2), ChatColor.translateAlternateColorCodes((char)'&', (String)warpDescription3), "", (Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + "WARNING:" + (Object)ChatColor.RED + " You " + (Object)ChatColor.UNDERLINE + "cannot" + (Object)ChatColor.RED + " teleport away,", (Object)ChatColor.RED + "the ONLY way out is the Exit Portal.", "", (Object)ChatColor.YELLOW + "" + (Object)ChatColor.BOLD + this.getPlayersAtWarp(warpName, true).size() + (Object)ChatColor.YELLOW + " nearby players(s)", (Object)ChatColor.RED + "" + (Object)ChatColor.BOLD + this.getPlayersAtWarp(warpName, false).size() + (Object)ChatColor.RED + " PvP-enabled player(s)", "", (Object)ChatColor.GRAY + "Click to teleport to this warp!");
        }
        catch (WarpNotFoundException | InvalidWorldException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ItemStack getSpecialWarpItem(String warpName, Material m, ChatColor warpColor, String ... warpDescription) {
        Material warpMaterial = m;
        ItemStack i = this.buildItem(warpMaterial, (short)0, (Object)warpColor + "" + (Object)ChatColor.BOLD + WordUtils.capitalize((String)warpName) + " /warp", "");
        ItemMeta im = i.getItemMeta();
        List lore = im.getLore();
        for (String s : warpDescription) {
            lore.add(ChatColor.translateAlternateColorCodes((char)'&', (String)s));
        }
        lore.add("");
        lore.add((Object)ChatColor.GRAY + "Click to teleport to this warp!");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    private List<Player> getPlayersAtWarp(String warpName, boolean includeSafezone) {
        if (this.warpPlayers.containsKey(warpName) && this.warpPvPPlayers.containsKey(warpName) && System.currentTimeMillis() < this.warpPlayerCache.get(warpName)) {
            return includeSafezone ? this.warpPlayers.get(warpName) : this.warpPvPPlayers.get(warpName);
        }
        ArrayList<Player> players = new ArrayList<Player>();
        ArrayList<Player> pvpPlayers = new ArrayList<Player>();
        try {
            Location warp = EssentialsWarpUI.ess.getWarps().getWarp(warpName);
            for (Player p : warp.getWorld().getPlayers()) {
                String regionName = WorldGuardUtils.getRegionName(p.getLocation());
                if (!regionName.contains(warpName) && warp.getWorld().getEnvironment() == World.Environment.NORMAL) continue;
                if (!WorldGuardUtils.isPvPDisabled(p.getLocation())) {
                    pvpPlayers.add(p);
                }
                players.add(p);
            }
        }
        catch (WarpNotFoundException | InvalidWorldException e) {
            e.printStackTrace();
        }
        this.warpPlayers.put(warpName, players);
        this.warpPvPPlayers.put(warpName, pvpPlayers);
        this.warpPlayerCache.put(warpName, System.currentTimeMillis() + 5000L);
        return includeSafezone ? players : pvpPlayers;
    }

    private int getInvSize(int amount) {
        int inv_size;
        for (inv_size = 9; inv_size < amount && inv_size < 81; inv_size += 9) {
        }
        return inv_size;
    }

    private ItemStack buildItem(Material m, short metaData, String name, String ... description) {
        ItemStack is = new ItemStack(m, 1, metaData);
        ItemMeta im = is.getItemMeta();
        if (name != null) {
            im.setDisplayName(name);
        }
        if (description != null) {
            im.setLore(Arrays.asList(description));
        }
        is.setItemMeta(im);
        return is;
    }

    private String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;
        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }
            titleCase.append(c);
        }
        return titleCase.toString();
    }
}

