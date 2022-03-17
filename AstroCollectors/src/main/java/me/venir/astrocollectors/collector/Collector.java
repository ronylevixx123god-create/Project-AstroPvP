package me.venir.astrocollectors.collector;

import com.conaxgames.customskulls.util.CC;
import com.earth2me.essentials.Essentials;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.AtomicDouble;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import lombok.Getter;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.data.SpawnerData;
import me.venir.astrocollectors.AstroCollectors;
import me.venir.astrocollectors.collector.CollectorSellLog;
import me.venir.astrocollectors.language.Language;
import me.venir.astrocollectors.utils.ChunkUtil;
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
    private final Map<Material, Integer> lootMap;
    private final List<CollectorSellLog> sellLogs;
    private long lastSold;
    @Getter
    private Map<EntityType, Integer> spawnersInChunk;
    Essentials essentials;


    public Collector(World world, int chunkX, int chunkZ, JSONObject data) {
        this.essentials = EssentialsUtils.getEssentials();
        reference = new WeakReference<>(world);
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        lootMap = new EnumMap<>(Material.class);
        if (data.size() != 2) {
            throw new IllegalArgumentException();
        }
        Object potentialLootMap = data.get("Loot");
        if (!(potentialLootMap instanceof JSONObject)) {
            throw new IllegalArgumentException();
        }
        JSONObject uncastedLootMap = (JSONObject) potentialLootMap;
        for (Object obj : uncastedLootMap.entrySet()) {
            Map.Entry entry = (Map.Entry) obj;
            Material material = Material.matchMaterial(String.valueOf(entry.getKey()));
            int amount = 0;
            try {
                amount = Integer.parseInt(String.valueOf(entry.getValue()));
            } catch (NumberFormatException ignore) {

            }

            lootMap.put(material, amount);
        }
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
        lootMap = new EnumMap<>(Material.class);
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

            if (ChunkUtil.isLocationAInSameChunkAsLocationB(locationSpawner, location)) {
                this.spawnersInChunk.put(spawnerData.getEntityType(), spawnerData.getSpawnerAmount());
            }
        }
    }

    public int getAmount(Material material) {
        return lootMap.getOrDefault(material, 0);
    }

    public double getTntAmount() {
        return lootMap.getOrDefault(Material.TNT, 0);
    }

    public int getTotalMobDropAmount() {
        AtomicInteger totalAmount = new AtomicInteger();
        lootMap.forEach((material, amount) -> {
            if (material == Material.TNT) return;
            totalAmount.addAndGet(amount);
        });
        return totalAmount.get();
    }

    public void setAmount(Material material, int amount) {
        lootMap.put(material, amount);
    }

    public double getMobDropWorth(Material material) {
        return this.getAmount(material) * essentials.getWorth().getPrice(essentials, new ItemStack(material)).doubleValue();
    }

    public double getAllMobDropsWorth() {
        AtomicDouble totalWorth = new AtomicDouble();
        lootMap.forEach((material, amount) -> {
            if (material == Material.TNT) return;
            totalWorth.addAndGet(getMobDropWorth(material));
        });
        return totalWorth.get();
    }

    public void incrementAmount(Material material, int increment) {
        lootMap.compute(material, (key, value) -> {
            if (value == null) {
                return increment;
            }
            return increment + value;
        });
    }

    public void resetAmount(Material material) {
        setAmount(material, 0);
    }

    public Location getLocation() {
        World world = reference.get();
        if (world == null) {
            return null;
        }
        return new Location(world, x, y, z);
    }

    public Map<Material, Integer> getLootMap() {
        return lootMap;
    }

    void invalidate() {
        reference.clear();
        lootMap.clear();
    }

    public void sellTNT(Player player) {
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        Faction faction = fplayer.getFaction();
        Material material = Material.TNT;
        int amount = getAmount(material);

        if (faction.isWilderness()) {
            return;
        }

        if (faction.getMaxTNT() <= faction.getTNTBank()) return;


        if (faction.getMaxTNT() < faction.getTNTBank() + amount) {
            amount = faction.getMaxTNT() - faction.getTNTBank();
            faction.setTNTBank(faction.getTNTBank() + amount);
            setAmount(material, getAmount(material) - (amount / 2));
        } else {
            faction.setTNTBank(faction.getTNTBank() + amount);
            resetAmount(material);
        }
        int finalAmount = amount;

        Language.COLLECTOR_TNT.send(player, str -> str.replace("%tnt%", Utils.formatNum(finalAmount)));

        addTNTSellLog(player, amount);

        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 5, 5);
    }

    public void sellOneTntOrMobDropAndLog(Player player, Material material) {
        if (getAmount(material) == 0) return;
        if (material == Material.TNT) {
            sellTNT(player);
        } else {
            sellOneMobDrop(player, material);
        }
    }

    public void sellAll(Player player) {
        if (lootMap.isEmpty()) return;
        if (getTotalMobDropAmount() > 0) addMobDropSellLog(player, getTotalMobDropAmount(), getAllMobDropsWorth());
        lootMap.forEach((m, i) -> {
            if (getAmount(m) == 0) return;
            sellOneTntOrMobDropAndLog(player, m);
        });
        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 5, 5);
    }

    public void sellOneMobDrop(Player player, Material material) {
        Economy eco = AstroCollectors.getEconomy();
        double price = getMobDropWorth(material);
        eco.depositPlayer(player, price);
        Language.COLLECTOR_SELL.send(player, str -> str.replace("%price%", Utils.formatNum(price)));
        resetAmount(material);
        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 5, 5);
    }

    public void sellAllMobDrops(Player player) {
        if (lootMap.isEmpty()) return;
        lootMap.forEach((m, i) -> {
            if (getAmount(m) == 0) return;
            if (m == Material.TNT) return;
            sellOneMobDrop(player, m);
        });
        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 5, 5);
    }

    @SuppressWarnings("unchecked")
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();

        JSONArray location = new JSONArray();
        location.add(x);
        location.add(y);
        location.add(z);

        jsonObject.put("Location", location);

        JSONObject lootObject = new JSONObject();
        this.lootMap.forEach((key, value) -> lootObject.put(String.valueOf(key), value));

        jsonObject.put("Loot", lootObject);
        JSONArray spawners = new JSONArray();
        for (Map.Entry<EntityType, Integer> spawnerData : this.spawnersInChunk.entrySet()) {
            JSONObject spawner = new JSONObject();
            spawner.put("type", spawnerData.getKey());
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

    public void addMobDropSellLog(Player seller, int amount, double worth) {
        sellLogs.add(new CollectorSellLog(seller.getName(), CollectorSellLog.SellLogType.MOB_DROPS, amount, worth));
    }

    public List<CollectorSellLog> getSellLogs() {
        return ImmutableList.copyOf(sellLogs);
    }
}
