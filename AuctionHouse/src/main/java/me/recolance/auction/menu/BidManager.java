/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.google.common.collect.Lists
 *  net.minecraft.util.com.google.common.reflect.TypeToken
 *  net.minecraft.util.com.google.gson.Gson
 *  net.minecraft.util.com.google.gson.GsonBuilder
 *  net.minecraft.util.com.google.gson.JsonDeserializationContext
 *  net.minecraft.util.com.google.gson.JsonDeserializer
 *  net.minecraft.util.com.google.gson.JsonElement
 *  net.minecraft.util.com.google.gson.JsonParseException
 *  net.minecraft.util.com.google.gson.JsonPrimitive
 *  net.minecraft.util.com.google.gson.JsonSerializationContext
 *  net.minecraft.util.com.google.gson.JsonSerializer
 *  net.minecraft.util.io.netty.util.internal.ConcurrentSet
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.scheduler.BukkitRunnable
 *  org.bukkit.scheduler.BukkitTask
 */
package me.recolance.auction.menu;

import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import io.netty.util.internal.ConcurrentSet;
import me.recolance.auction.Auction;
import me.recolance.auction.data.AuctionBid;
import me.recolance.auction.data.ItemStackDeserializer;
import me.recolance.auction.data.ItemStackSerializer;
import me.recolance.auction.menu.bidmenus.BiddableMenu;
import me.recolance.auction.util.ItemSerialization;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BidManager {
    private Map<UUID, Set<AuctionBid>> currentBids = new HashMap<UUID, Set<AuctionBid>>();
    private ConcurrentHashMap<UUID, AuctionBid> activeAuctionBids = new ConcurrentHashMap();
    private HashMap<UUID, List<ItemStack>> bidCollectionBin = new HashMap();
    private Map<UUID, ConcurrentSet<UUID>> watchlists = new ConcurrentHashMap<UUID, ConcurrentSet<UUID>>();
    private File activeAuctionFile;
    private File bidCollectionBinFile;
    private File watchlistFile;
    private Type activeToken = new TypeToken<HashSet<AuctionBid>>(){}.getType();
    private Type bidBinToken = new TypeToken<HashMap<UUID, List<String>>>(){}.getType();
    private Type watchlistToken = new TypeToken<ConcurrentHashMap<UUID, ConcurrentSet<UUID>>>(){}.getType();
    private Type item = new TypeToken<List<ItemStack>>(){}.getType();
    private BukkitTask task;
    private boolean proccessAuctionHouse = false;
    private List<ItemStack> loaded;
    private Gson gson = new GsonBuilder().registerTypeAdapter(Material.class, (Object)new JsonSerializer<Material>(){

        public JsonElement serialize(Material material, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(material.name());
        }
    }).registerTypeAdapter(Material.class, (Object)new JsonDeserializer<Material>(){

        public Material deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement instanceof JsonPrimitive) {
                return Material.getMaterial((String)((JsonPrimitive)jsonElement).getAsString());
            }
            return null;
        }
    }).registerTypeAdapter(ItemStack.class, (Object)new ItemStackSerializer()).registerTypeAdapter(ItemStack.class, (Object)new ItemStackDeserializer()).enableComplexMapKeySerialization().create();

    public void loadBids(JavaPlugin plugin) throws IOException {
        this.activeAuctionFile = JSONUtils.getOrCreateFile((File)plugin.getDataFolder(), (String)"activeAuctions.json");
        this.bidCollectionBinFile = JSONUtils.getOrCreateFile((File)plugin.getDataFolder(), (String)"bidCollectionBids.json");
        this.watchlistFile = JSONUtils.getOrCreateFile((File)plugin.getDataFolder(), (String)"watchlistFile.json");
        HashMap serializedList = (HashMap)JSONUtils.fromJson((File)this.bidCollectionBinFile, (Type)this.bidBinToken, (Gson)this.gson);
        Set<> auctions = (Set)JSONUtils.fromJson((File)this.activeAuctionFile, (Type)this.activeToken, (Gson)this.gson); // HELP
        if (auctions != null) {
            auctions.forEach(auc -> {
                if (auc == null || auc.isExpired()) {
                    return;
                }
                auc.deserialize();
                this.activeAuctionBids.put(auc.getBidID(), (AuctionBid)auc);
            });
        }
        if (this.activeAuctionBids == null) {
            this.activeAuctionBids = new ConcurrentHashMap();
        }
        if (serializedList != null) {
            serializedList.forEach((id, list) -> this.bidCollectionBin.put((UUID)id, list.stream().map(ItemSerialization::itemFromBase64).collect(Collectors.toList())));
        }
        this.watchlists = (Map)JSONUtils.fromJson((File)this.watchlistFile, (Type)this.watchlistToken);
        if (this.watchlists == null) {
            this.watchlists = new ConcurrentHashMap<UUID, ConcurrentSet<UUID>>();
        }
        this.watchlists.forEach((owner, ids) -> ids.stream().filter(aucID -> !this.activeAuctionBids.containsKey(aucID)).forEach(((ConcurrentSet)ids)::remove));
        this.activeAuctionBids.forEach((auctionId, bid) -> {
            Set bids = this.currentBids.computeIfAbsent(bid.getSellerUUID(), e -> new HashSet());
            bids.add(bid);
        });
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Auction.getPlugin(), () -> {
            this.proccessAuctionHouse = true;
        }, 1200L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)Auction.getPlugin(), () -> this.saveBids(true), 1200L, 6000L);
        if (Bukkit.getOnlinePlayers().size() > 0) {
            this.proccessAuctionHouse = true;
        }
    }

    public void startBidTimer() {
        if (this.task != null) {
            this.task.cancel();
        }
        this.task = new BukkitRunnable(){

            public void run() {
                if (!BidManager.this.proccessAuctionHouse) {
                    return;
                }
                for (Map.Entry bidEntry : BidManager.this.activeAuctionBids.entrySet()) {
                    try {
                        AuctionBid bid = (AuctionBid)bidEntry.getValue();
                        boolean expired = bid.isExpired();
                        bid.onTick();
                        if (!bid.isExpired() || expired) continue;
                        BidManager.this.removeAuctionBid(bid);
                        Bukkit.getLogger().info("Removing expired auction bid " + bid.getSellerName() + " for " + bid.getBidSellDisplayName());
                    }
                    catch (Exception e) {
                        Bukkit.getLogger().info("ERROR onTick inside bid: " + ((AuctionBid)bidEntry.getValue()).getSellerName() + " ( " + ((AuctionBid)bidEntry.getValue()).getBidID() + ") Selling: " + ((AuctionBid)bidEntry.getValue()).getBidSellDisplayName() + " Items: " + ((AuctionBid)bidEntry.getValue()).getSellingItems());
                        if (((AuctionBid)bidEntry.getValue()).isExpired()) {
                            BidManager.this.activeAuctionBids.remove(bidEntry.getKey());
                        }
                        e.printStackTrace();
                    }
                }
                CosmicGUI.activeGUIs.forEach((uuid, gui) -> {
                    BiddableMenu bidMenu;
                    if (gui instanceof BiddableMenu && (bidMenu = (BiddableMenu)((Object)gui)).getPlayer() != null && bidMenu.getPlayer().getOpenInventory() != null && bidMenu.getPlayer().getOpenInventory().getTopInventory() != null && bidMenu.getPlayer().getOpenInventory().getTopInventory().getName().equals(((BiddableMenu)((Object)gui)).getTitle())) {
                        gui.redraw();
                        return;
                    }
                });
            }
        }.runTaskTimer((Plugin)Auction.getPlugin(), 20L, 20L);
    }

    public void removeAuctionBid(AuctionBid bid) {
        this.activeAuctionBids.remove(bid.getBidID());
        Set<AuctionBid> bids = this.currentBids.get(bid.getSellerUUID());
        if (bids != null) {
            bids.remove(bid);
        }
        Bukkit.getLogger().info("Removing bid " + bid.getBidID() + " from " + bid.getSellerName());
    }

    public void saveBids(boolean async) {
        if (async && Bukkit.isPrimaryThread()) {
            ConcurrentHashMap<UUID, AuctionBid> copy = new ConcurrentHashMap<UUID, AuctionBid>(this.activeAuctionBids);
            HashMap<UUID, List<ItemStack>> binCopy = new HashMap<UUID, List<ItemStack>>(this.bidCollectionBin);
            CompletableFuture.runAsync(() -> this.saveBids(copy, binCopy));
            return;
        }
        this.saveBids(this.activeAuctionBids, this.bidCollectionBin);
    }

    public void saveBids(ConcurrentHashMap<UUID, AuctionBid> bids, HashMap<UUID, List<ItemStack>> collectionBins) {
        ArrayList bis = Lists.newArrayList(bids.values());
        HashMap serializedCollectionBins = new HashMap();
        collectionBins.forEach((id, itemList) -> serializedCollectionBins.put(id, itemList.stream().map(ItemSerialization::itemToStringBase64).collect(Collectors.toList())));
        JSONUtils.saveJSONToFile((File)this.bidCollectionBinFile, serializedCollectionBins, (Object)this.bidBinToken, (Gson)this.gson);
        JSONUtils.saveJSONToFile((File)this.activeAuctionFile, (Object)bis, (Object)this.activeToken, (Gson)this.gson);
        JSONUtils.saveJSONToFile((File)this.watchlistFile, this.watchlists, (Object)this.watchlistToken, (Gson)this.gson);
    }

    public Map<UUID, Set<AuctionBid>> getCurrentBids() {
        return this.currentBids;
    }

    public ConcurrentHashMap<UUID, AuctionBid> getActiveAuctionBids() {
        return this.activeAuctionBids;
    }

    public HashMap<UUID, List<ItemStack>> getBidCollectionBin() {
        return this.bidCollectionBin;
    }

    public Map<UUID, ConcurrentSet<UUID>> getWatchlists() {
        return this.watchlists;
    }

    public boolean isProccessAuctionHouse() {
        return this.proccessAuctionHouse;
    }

    public void setProccessAuctionHouse(boolean proccessAuctionHouse) {
        this.proccessAuctionHouse = proccessAuctionHouse;
    }
}

