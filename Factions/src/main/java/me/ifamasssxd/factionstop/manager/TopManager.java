/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.massivecraft.factions.Board
 *  com.massivecraft.factions.FLocation
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.Factions
 *  me.ifamasssxd.cosmicweblink.CosmicWebLink
 *  me.ifamasssxd.cosmicweblink.struct.DataTypeEnum
 *  org.arkhamnetwork.Arkkit.libs.BlockValueLocation
 *  org.arkhamnetwork.Arkkit.libs.SpawnerLocation
 *  org.arkhamnetwork.Arkkit.patches.BlockValue
 *  org.arkhamnetwork.Arkkit.patches.SpawnerFee
 *  org.arkhamnetwork.Arkkit.plugins.ArkPlugins
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Material
 *  org.bukkit.World
 *  org.bukkit.entity.EntityType
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.factionstop.manager;

import com.google.common.collect.Lists;
import com.massivecraft.factions.*;
import me.ifamasssxd.factionstop.struct.LoadCallback;
import me.ifamasssxd.factionstop.struct.StoredFaction;
import me.ifamasssxd.factionstop.struct.TopFaction;
import org.arkhamnetwork.Arkkit.libs.BlockValueLocation;
import org.arkhamnetwork.Arkkit.libs.SpawnerLocation;
import org.arkhamnetwork.Arkkit.patches.BlockValue;
import org.arkhamnetwork.Arkkit.patches.SpawnerFee;
import org.arkhamnetwork.Arkkit.plugins.ArkPlugins;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TopManager {
    private final LinkedHashMap<Integer, TopFaction> topFactions = new LinkedHashMap();
    private final LinkedHashMap<Integer, TopFaction> wealthTopFactions = new LinkedHashMap();
    private final LinkedHashMap<String, TopFaction> fastFactions = new LinkedHashMap();
    private int totalSpawnersFound = 0;
    private final int refreshTimer = 5;
    boolean loadingData = false;
    private long lastUpdate = -1L;
    private long lastWealthUpdate = -1L;
    private final Map<Integer, String> previousFTops = new HashMap<Integer, String>();
    private final Map<Integer, String> previousWealthFTops = new HashMap<Integer, String>();
    private static final DecimalFormat format = new DecimalFormat("#,###");

    public TopFaction getTopFaction(String id) {
        return this.fastFactions.get(id);
    }

    public void loadTopFactions(LoadCallback callback, int delay) {
        this.loadTopFactions(callback, delay, false);
    }

    public void loadTopFactions(final LoadCallback callback, int delay, final boolean forceWealth) {
        long last;
        long l = last = forceWealth ? this.lastWealthUpdate : this.lastUpdate;
        if (delay != -1 && last != -1L && System.currentTimeMillis() - last < TimeUnit.MINUTES.toMillis(this.refreshTimer)) {
            return;
        }
        final boolean webLinkEnabled = Bukkit.getPluginManager().isPluginEnabled("CosmicWebLink");
        /*if (webLinkEnabled) {
            try {
                if (CosmicWebLink.get().getFactionDataManager().factionIdsKothWins.isEmpty()) {
                    CosmicWebLink.get().getFactionDataManager().loadKoth();
                }
            }
            catch (Exception e) {
                Bukkit.getLogger().info("Unable to find CosmicWebLink hook even tho its enabled! Did CosmicWebLink SQL enable properly?");
            }
        }*/
        if (forceWealth) {
            this.lastWealthUpdate = System.currentTimeMillis();
        } else {
            this.lastUpdate = System.currentTimeMillis();
        }
        final HashMap spawners = new HashMap();
        final HashMap containers = new HashMap();
        final HashMap<String, StoredFaction> storedFactions = new HashMap();
        System.out.println("Loading spawners..");
        new BukkitRunnable(){

            /*
             * WARNING - void declaration
             */
            public void run() {
                try {
                    boolean wealth;
                    Field mapField = Board.class.getDeclaredField("flocationIds");
                    mapField.setAccessible(true);
                    ConcurrentHashMap<FLocation, String> factionLocations = (ConcurrentHashMap) mapField.get(Board.getInstance());
                    TopManager.this.loadingData = true;
                    long start = System.currentTimeMillis();
                    long firstStart = System.currentTimeMillis();
                    int chunksChecked = 0;
                    if (ArkPlugins.SPAWNER_FEE.getArkPlugin().isRunning()) {
                        int spawnersChecked = 0;
                        for (Map.Entry spawner : SpawnerFee.playerSpawners.entrySet()) {
                            List<Object> var12_17 = null;
                            ++spawnersChecked;
                            if (((SpawnerLocation)spawner.getValue()).getSpawnerType() == EntityType.UNKNOWN) continue;
                            String chunkCoords = ((SpawnerLocation)spawner.getValue()).getWorldName() + ":" + ((SpawnerLocation)spawner.getValue()).getChunkX() + "," + ((SpawnerLocation)spawner.getValue()).getChunkZ();
                            List list = (List)spawners.get(chunkCoords);
                            if (list == null) {
                                ArrayList arrayList = Lists.newArrayList();
                            }
                            var12_17.add(spawner.getValue());
                            TopManager.this.totalSpawnersFound++;
                            spawners.put(chunkCoords, var12_17);
                        }
                        System.out.println("Loaded " + spawnersChecked + " spawners in " + spawners.size() + " chunks.");
                        AtomicInteger index = new AtomicInteger(0);
                        long trackStart = System.currentTimeMillis();
                        if (BlockValue.trackedBlockValues != null) {
                            new HashMap<String, BlockValueLocation>(BlockValue.trackedBlockValues).forEach((coordString, blockValue) -> {
                                if (blockValue.getBlockType() == Material.HOPPER && !P.getP().isIncludeHoppers()) {
                                    return;
                                }
                                String chunkCoords = blockValue.getWorldName() + ":" + blockValue.getChunkX() + "," + blockValue.getChunkZ();
                                List currentBlocksInChunk = (List) containers.computeIfAbsent(chunkCoords, e -> Lists.newArrayList());
                                currentBlocksInChunk.add(blockValue);
                                index.addAndGet(1);
                            });
                        }
                        System.out.println("Took " + (System.currentTimeMillis() - trackStart) + " ms to load " + index.get() + " trackedBlockValues into chunk map.");
                        System.out.println("Scanning " + factionLocations.size() + " Faction Claims..");
                        for (Map.Entry<FLocation, String> entry : factionLocations.entrySet()) {
                            List locations;
                            World world;
                            FLocation chunkLoc = entry.getKey();
                            String facId = entry.getValue();
                            int factionID = Integer.parseInt(entry.getValue());
                            World world2 = world = chunkLoc != null ? chunkLoc.getWorld() : null;
                            if (factionID <= 0 || world == null) continue;
                            String chunkCoords = world.getName() + ":" + chunkLoc.getX() + "," + chunkLoc.getZ();
                            List chunkSpawners = (List)spawners.get(chunkCoords);
                            if (chunkSpawners != null) {
                                StoredFaction storedFaction = storedFactions.computeIfAbsent(facId, fac -> new StoredFaction(facId, Lists.newArrayList(), Lists.newArrayList()));
                                storedFaction.getFactionSpawners().addAll(chunkSpawners);
                            }
                            if ((locations = (List)containers.get(chunkCoords)) != null) {
                                StoredFaction faction = storedFactions.computeIfAbsent(facId, e -> new StoredFaction(facId, Lists.newArrayList(), Lists.newArrayList()));
                                faction.getFactionContainers().addAll(locations);
                            }
                            if (++chunksChecked % 10000 != 0) continue;
                            Bukkit.getLogger().info("Checked " + chunksChecked + " Chunks..");
                        }
                    }
                    start = System.currentTimeMillis();
                    Bukkit.getLogger().info("Starting faction check..");
                    for (Faction faction : Factions.i.get()) {
                        try {
                            if (!faction.isNormal()) continue;
                            StoredFaction storedFaction = storedFactions.get(faction.getId());
                            if (storedFaction == null) {
                                storedFaction = new StoredFaction(faction.getId(), Lists.newArrayList(), Lists.newArrayList());
                                storedFactions.put(faction.getId(), storedFaction);
                            } else {
                                storedFaction.calculateSpawnerWorth();
                            }
                            storedFaction.calculateTotalBalance();
                        }
                        catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    Bukkit.getLogger().info("Finished faction check in " + (System.currentTimeMillis() - start) + "ms");
                    System.out.println("Scanned " + chunksChecked + " Chunks in " + (System.currentTimeMillis() - firstStart) + "ms found " + TopManager.this.totalSpawnersFound + " spawners.");
                    LinkedHashMap<String, StoredFaction> topSortedFactions = TopManager.this.sortByComparator(storedFactions, false, forceWealth && P.getP().factionPointsEnabled);
                    boolean bl = wealth = forceWealth && P.getP().factionPointsEnabled;
                    if (!wealth) {
                        TopManager.this.fastFactions.clear();
                    }
                    int place = 1;
                    for (Map.Entry<String, StoredFaction> entry : topSortedFactions.entrySet()) {
                        Faction faction = entry.getValue().getFaction();
                        if (faction == null) continue;
                        StoredFaction storedFaction = entry.getValue();
                        /*if (webLinkEnabled) {
                            try {
                                if (CosmicWebLink.get().getFactionDataManager().factionIdsKothWins != null) {
                                    int wins = CosmicWebLink.get().getFactionDataManager().getFactionKothWins(faction.getId());
                                    storedFaction.setKothWins(wins);
                                }
                            }
                            catch (Exception wins) {
                                // empty catch block
                            }
                        }*/
                        if (place < 50) {
                            System.out.println("Top Faction " + faction.getTag() + " is #" + place + ", found " + storedFaction.getMobSpawnerCount() + " Spawners worth " + storedFaction.getTotalSpawnerWorth() + " with top player balance: " + storedFaction.getTotalBalance() + " owner: " + storedFaction.getOwner());
                        }
                        if (place <= 3) {
                            String previousPlace;
                            String string = previousPlace = forceWealth ? TopManager.this.previousWealthFTops.get(place) : TopManager.this.previousFTops.get(place);
                            if (forceWealth) {
                                TopManager.this.previousWealthFTops.put(place, faction.getId());
                            } else {
                                TopManager.this.previousFTops.put(place, faction.getId());
                            }
                            if (previousPlace == null || !previousPlace.equals(faction.getId())) {
                                String worthString = !P.getP().factionPointsEnabled || forceWealth ? "$" + format.format(storedFaction.getTotalWorth()) : storedFaction.getFactionPoints() + " Faction Points";
                                int finalPlace = place;
                                Bukkit.getScheduler().runTask(P.getP(), () -> Bukkit.broadcastMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "(!) " + ChatColor.UNDERLINE.toString() + faction.getTag() + ChatColor.GREEN + " has taken " + ChatColor.GREEN + ChatColor.BOLD.toString() + "#" + finalPlace + ChatColor.GREEN + " in /f " + (P.getP().factionPointsEnabled && forceWealth ? "wealth" : "top") + " with " + worthString));
                            }
                        }
                        TopFaction fac2 = new TopFaction(storedFaction, place);
                        if (wealth) {
                            TopManager.this.wealthTopFactions.put(place++, fac2);
                            continue;
                        }
                        TopManager.this.topFactions.put(place++, fac2);
                        TopManager.this.fastFactions.put(storedFaction.getFactionID(), fac2);
                    }
                    /*if (Bukkit.getPluginManager().isPluginEnabled("CosmicWebLink")) {
                        CosmicWebLink cosmicWebLink = (CosmicWebLink)Bukkit.getPluginManager().getPlugin("CosmicWebLink");
                        cosmicWebLink.getDataManager().updateData(DataTypeEnum.FACTIONS_TOP, new Object[]{TopManager.this.topFactions, TopManager.this.wealthTopFactions});
                    }*/
                    topSortedFactions.clear();
                    TopManager.this.loadingData = false;
                    if (callback != null) {
                        callback.onLoad();
                    }
                }
                catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }.runTaskLaterAsynchronously(P.getP(), delay == -1 ? 0L : (long)delay);
    }

    public LinkedHashMap<String, StoredFaction> sortByComparator(HashMap<String, StoredFaction> unsortMap, boolean order, boolean wealth) {
        if (unsortMap == null) {
            return null;
        }
        LinkedList<Map.Entry<String, StoredFaction>> list = new LinkedList<Map.Entry<String, StoredFaction>>(unsortMap.entrySet());
        Collections.sort(list, (o1, o2) -> {
            if (order) {
                return o1.getValue().compareTo(o2.getValue(), wealth);
            }
            return o2.getValue().compareTo(o1.getValue(), wealth);
        });
        LinkedHashMap<String, StoredFaction> sortedMap = new LinkedHashMap<String, StoredFaction>();
        for (Map.Entry entry : list) {
            sortedMap.put((String)entry.getKey(), (StoredFaction)entry.getValue());
        }
        return sortedMap;
    }

    public LinkedHashMap<Integer, TopFaction> getTopFactions() {
        return this.topFactions;
    }

    public LinkedHashMap<Integer, TopFaction> getWealthTopFactions() {
        return this.wealthTopFactions;
    }

    public LinkedHashMap<String, TopFaction> getFastFactions() {
        return this.fastFactions;
    }

    public int getRefreshTimer() {
        return this.refreshTimer;
    }

    public boolean isLoadingData() {
        return this.loadingData;
    }

    public long getLastUpdate() {
        return this.lastUpdate;
    }

    public long getLastWealthUpdate() {
        return this.lastWealthUpdate;
    }
}

