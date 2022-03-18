package me.venir.astrocollectors.collector;

import com.conaxgames.customskulls.util.CC;
import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.earth2me.essentials.Essentials;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AtomicDouble;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.data.SpawnerData;
import me.venir.astrocollectors.AstroCollectors;
import me.venir.astrocollectors.collector.CollectorSellLog;
import me.venir.astrocollectors.language.Language;
import me.venir.astrocollectors.utils.ChunkUtil;
import me.venir.astrocollectors.utils.SpawnerUtil;
import me.venir.astrocollectors.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.arkhamnetwork.Arkkit.utils.EssentialsUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public final class Collector {

    private final Reference<World> reference;
    private final int chunkX, chunkZ;
    private final double x, y, z;
    private final List<CollectorSellLog> sellLogs;
    @Getter
    @Setter
    private long lastSold;
    @Getter
    private Map<EntityType, Integer> spawnersInChunk;
    Essentials essentials;


    public Collector(World world, int chunkX, int chunkZ, JSONObject data) {
        this.essentials = EssentialsUtils.getEssentials();
        reference = new WeakReference<>(world);
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;

        Object potentialLocationArray = data.get("Location");
        if (!(potentialLocationArray instanceof JSONArray)) {
            throw new IllegalArgumentException();
        }
        JSONArray uncastedLocationArray = (JSONArray) potentialLocationArray;
        x = Double.parseDouble(String.valueOf(uncastedLocationArray.get(0)));
        y = Double.parseDouble(String.valueOf(uncastedLocationArray.get(1)));
        z = Double.parseDouble(String.valueOf(uncastedLocationArray.get(2)));
        sellLogs = new ArrayList<>();

        //Load from file
        lastSold = (long) data.get("lastSold");


        //put all spawners from chunk in this list
        this.spawnersInChunk = new HashMap<>();
        Object potentialSpawnerArray = data.get("spawners");
        if (!(potentialSpawnerArray instanceof JSONArray)) {
            throw new IllegalArgumentException();
        }
        JSONArray uncastedSpawnerArray = (JSONArray) potentialSpawnerArray;
        for (Object o : uncastedSpawnerArray) {
            JSONObject jsonObject = ((JSONObject) o);
            EntityType type = EntityType.valueOf(String.valueOf(jsonObject.get("type")));
            int amount = Integer.parseInt(String.valueOf(jsonObject.get("amount")));
            this.spawnersInChunk.put(type, amount);
        }


    }

    public Collector(Location location) {
        this.essentials = EssentialsUtils.getEssentials();
        reference = new WeakReference<>(location.getWorld());
        x = location.getBlockX();
        y = location.getBlockY();
        z = location.getBlockZ();
        Chunk chunk = location.getChunk();
        chunkX = chunk.getX();
        chunkZ = chunk.getZ();
        sellLogs = new ArrayList<>();
        lastSold = -1;

        //put all spawners from chunk in this list
        this.spawnersInChunk = new HashMap<>();
        for (Map.Entry<Location, SpawnerData> entry : AstroSpawners.getInstance().getSpawnersMap().entrySet()) {
            Location locationSpawner = entry.getKey();
            SpawnerData spawnerData = entry.getValue();
            if (spawnerData.getEntityType() == EntityType.BLAZE) continue;

            if (ChunkUtil.isLocationAInSameChunkAsLocationB(locationSpawner, location)) {
                this.spawnersInChunk.put(spawnerData.getEntityType(), spawnerData.getSpawnerAmount());
            }
        }
    }

    public Location getLocation() {
        World world = reference.get();
        if (world == null) {
            return null;
        }
        return new Location(world, x, y, z);
    }

    void invalidate() {
        reference.clear();
    }

    public void sellAll(Player player) {
        double worth = calculateWorth();
        if (worth <= 0) return;
        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 5, 5);
        EconomyUtils.depositBalance(player, worth);
        this.setLastSold(System.currentTimeMillis());
        player.closeInventory();

    }

    public double calculateWorth() {
        long lastSold = getLastSold();
        long current = System.currentTimeMillis();
        long secondsPassed = (current - lastSold)/1000;
        long minutesPassed = secondsPassed/60;
        double total = 0;
        for (Map.Entry<EntityType, Integer> entry : getSpawnersInChunk().entrySet()) {
            EntityType type = entry.getKey();
            int amountSpawners = entry.getValue();
            total += SpawnerUtil.getMoneyPerMinute(type) * amountSpawners * minutesPassed;
        }
        return total;
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();

        JSONArray location = new JSONArray();
        location.add(x);
        location.add(y);
        location.add(z);

        jsonObject.put("Location", location);
        JSONArray spawners = new JSONArray();
        for (Map.Entry<EntityType, Integer> spawnerData : this.spawnersInChunk.entrySet()) {
            JSONObject spawner = new JSONObject();
            spawner.put("type", spawnerData.getKey().name());
            spawner.put("amount", spawnerData.getValue());
            spawners.add(spawner);
        }


        jsonObject.put("spawners", spawners);
        jsonObject.put("lastSold", lastSold);
        return jsonObject;
    }

    public void addTNTSellLog(Player seller, int amount) {
        sellLogs.add(new CollectorSellLog(seller.getName(), CollectorSellLog.SellLogType.TNT, amount));
    }

    public void addMobDropSellLog(Player seller, double worth) {
        sellLogs.add(new CollectorSellLog(seller.getName(), CollectorSellLog.SellLogType.MOB_DROPS, worth));
    }

    public List<CollectorSellLog> getSellLogs() {
        return ImmutableList.copyOf(sellLogs);
    }
}
