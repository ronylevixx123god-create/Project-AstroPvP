/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.harry5574.utils.hologram.Hologram
 *  com.sk89q.worldedit.Vector
 *  com.sk89q.worldguard.bukkit.WorldGuardPlugin
 *  net.milkbowl.vault.economy.Economy
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.Inventory
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.RegisteredServiceProvider
 */
package org.arkhamnetwork.Arkkit.patches.player_shops;

import com.harry5574.utils.hologram.Hologram;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.milkbowl.vault.economy.Economy;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.arkhamnetwork.Arkkit.patches.player_shops.CommandShop;
import org.arkhamnetwork.Arkkit.patches.player_shops.PlayerShopListener;
import org.arkhamnetwork.Arkkit.patches.player_shops.PlayerShopMoveTask;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PlayerShops
extends ArkPlugin {
    protected static PlayerShops plugin;
    protected static PlayerShopListener shop_listener;
    protected static String currency_name;
    protected static WorldGuardPlugin wg;
    protected static Economy economy;
    protected static HashMap<Player, Inventory> player_shops;
    protected static HashMap<Inventory, Player> player_shops_inverse;
    protected static HashMap<Player, Location> player_shop_locations;
    protected static HashMap<Player, Hologram> player_holograms;
    protected static HashMap<UUID, Integer> shop_teleport_task_ids;

    public PlayerShops(Plugin p) {
        super(p);
        player_shops = new HashMap();
        player_shops_inverse = new HashMap();
        player_shop_locations = new HashMap();
        player_holograms = new HashMap();
        shop_teleport_task_ids = new HashMap();
    }

    @Override
    public void enable() {
        wg = this.getWorldGuard();
        this.setupEconomy();
        currency_name = this.getBukkitPlugin().getConfig().getString("language.player_shops.currency");
        shop_listener = new PlayerShopListener();
        this.registerCommand(new CommandShop("shop", "/<command>", "Create your own player-owned shop!", new ArrayList<String>(Arrays.asList("playershop", "pshop", "openshop", "makeshop", "createshop"))));
        this.registerListener(shop_listener);
        this.registerTask(new PlayerShopMoveTask().runTaskTimerAsynchronously((Plugin)Arkkit.get(), 20L, 20L), PlayerShops.class);
    }

    @Override
    public void disable() {
        for (Hologram hologram : player_holograms.values()) {
            hologram.destroy();
        }
        for (Map.Entry entry : player_shops.entrySet()) {
            Player pl = (Player)entry.getKey();
            if (pl.getInventory().firstEmpty() == -1) continue;
            pl.getInventory().addItem(new ItemStack[]{shop_listener.getItem((Inventory)entry.getValue(), 0)});
        }
        this.recycleMaps();
    }

    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        return (WorldGuardPlugin)plugin;
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy)economyProvider.getProvider();
        }
        return economy != null;
    }

    protected static boolean isShopNear(Location loc, int radius) {
        radius = (int)Math.pow(radius, 2.0);
        for (Location sloc : player_shop_locations.values()) {
            if (!(sloc.distanceSquared(loc) <= (double)radius)) continue;
            return true;
        }
        return false;
    }

    protected static String getItemName(ItemStack is) {
        return is.hasItemMeta() && is.getItemMeta().hasDisplayName() ? is.getItemMeta().getDisplayName() : is.getType().name();
    }

    protected static Inventory buildShop(String shop_owner, ItemStack is, int price) {
        String title = ChatColor.BLACK.toString() + ChatColor.BOLD.toString() + "Price: " + ChatColor.BLACK.toString() + "$" + price;
        if (title.length() > 32) {
            return null;
        }
        Inventory shop = Bukkit.createInventory(null, (int)9, (String)title);
        shop.setItem(0, is);
        return shop;
    }

    protected static String getRegionName(Location l) {
        if (!Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
            return "";
        }
        try {
            Class<?> bukkitUtil = wg.getClass().getClassLoader().loadClass("com.sk89q.worldguard.bukkit.BukkitUtil");
            Method toVector = bukkitUtil.getMethod("toVector", Block.class);
            Vector blockVector = (Vector)toVector.invoke(null, new Object[]{l.getBlock()});
            List regionSet = wg.getGlobalRegionManager().get(l.getWorld()).getApplicableRegionsIDs(blockVector);
            if (regionSet.size() < 1) {
                return "";
            }
            String return_region = "";
            int return_priority = -1;
            for (String region : regionSet) {
                int region_priority = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getPriority();
                if (return_region.equalsIgnoreCase("")) {
                    return_region = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getId();
                    return_priority = region_priority;
                }
                if (region_priority <= return_priority) continue;
                return_region = wg.getGlobalRegionManager().get(l.getWorld()).getRegion(region).getId();
                return_priority = region_priority;
            }
            if (return_region.contains("global")) {
                return_region = "";
            }
            return return_region;
        }
        catch (Exception exception) {
            return "";
        }
    }

    private void recycleMaps() {
        player_shops = new HashMap();
        player_shops_inverse = new HashMap();
        player_shop_locations = new HashMap();
        player_holograms = new HashMap();
        shop_teleport_task_ids = new HashMap();
    }

    static {
        currency_name = "$";
        wg = null;
        economy = null;
    }
}

