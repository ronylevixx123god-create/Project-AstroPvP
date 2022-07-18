package me.venir.astrocollectors.collector;

import com.cosmicpvp.cosmicutils.utils.EconomyUtils;
import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.google.common.collect.ImmutableList;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import lombok.Getter;
import lombok.Setter;
import me.astropvp.astrospawners.AstroSpawners;
import me.astropvp.astrospawners.data.SpawnerData;
import me.ifamasssxd.cosmichcf.api.HCFOutpostAPI;
import me.ifamasssxd.cosmichcf.struct.OutpostType;
import me.venir.astrocollectors.AstroCollectors;
import me.venir.astrocollectors.language.Language;
import me.venir.astrocollectors.utils.ChunkUtil;
import me.venir.astrocollectors.utils.Utils;
import net.astropvp.core.utils.OutpostUtil;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;


public final class Collector {

    @Getter
    @Setter
    private long lastUpdated;
    @Getter
    private double worth;
    @Getter
    private long tnt;
    @Getter
    private final Map<EntityType, Integer> spawnersInChunk;
    private final Reference<World> reference;
    private final double x, y, z;
    private final Queue<CollectorSellLog> sellLogs;


    public Collector(World world, JSONObject data) {
        this.reference = new WeakReference<>(world);

        Object potentialLocationArray = data.get("Location");
        if (!(potentialLocationArray instanceof JSONArray)) {
            throw new IllegalArgumentException();
        }
        JSONArray uncastedLocationArray = (JSONArray) potentialLocationArray;
        this.x = Double.parseDouble(String.valueOf(uncastedLocationArray.get(0)));
        this.y = Double.parseDouble(String.valueOf(uncastedLocationArray.get(1)));
        this.z = Double.parseDouble(String.valueOf(uncastedLocationArray.get(2)));
        this.sellLogs = new LinkedBlockingQueue<>(10);

        this.lastUpdated = (long) data.get("lastUpdated");

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

        this.worth = (double) data.get("worth");
        ;
        this.tnt = (long) data.get("tnt");
        ;
    }

    public Collector(Location location) {
        this.reference = new WeakReference<>(location.getWorld());
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.sellLogs = new LinkedBlockingQueue<>(10);
        this.lastUpdated = System.currentTimeMillis();

        this.spawnersInChunk = new HashMap<>();
        this.scanSpawners(location);

        this.worth = 0.0;
        this.tnt = 0L;
    }

    public void scanSpawners(Location location) {

        for (BlockState blockState : location.getChunk().getTileEntities()) {
            if (blockState instanceof CreatureSpawner) {
                if (AstroSpawners.getInstance().getSpawnersMap().containsKey(blockState.getLocation())) {
                    SpawnerData data = AstroSpawners.getInstance().getSpawnersMap().get(blockState.getLocation());
                    if (data.getEntityType() == EntityType.BLAZE) continue;
                    this.spawnersInChunk.put(data.getEntityType(), data.getSpawnerAmount());
                }
            }
        }
    }

    public void sellWorth(Player player) {
        if (this.worth <= 0) return;
        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 5, 5);
        EconomyUtils.depositBalance(player, worth);
        Language.COLLECTOR_SELL.send(player, str -> str.replace("%price%", Utils.formatNum((long) this.worth)));
        this.worth = 0.0;
    }

    public void claimTNT(Player player) {
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
        Faction faction = fplayer.getFaction();
        int amount = (int) this.tnt;

        if (faction.isWilderness()) {
            Language.NEED_FACTION.send(player);
            return;
        }

        if (faction.getMaxTNT() <= faction.getTNTBank()) return;

        if (faction.getMaxTNT() < faction.getTNTBank() + amount) {
            amount = faction.getMaxTNT() - faction.getTNTBank();
            faction.setTNTBank(faction.getTNTBank() + amount);
            this.tnt = this.tnt - (amount / 2);
        } else {
            faction.setTNTBank(faction.getTNTBank() + amount);
            this.tnt = 0;
        }

        int finalAmount = amount;
        Language.COLLECTOR_TNT.send(player, str -> str.replace("%tnt%", Utils.formatNum(finalAmount)));

        addTNTSellLog(player, amount);
        player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 5, 5);
    }

    private double calculateWorth() {
        long current = System.currentTimeMillis();
        long secondsPassed = (current - this.getLastUpdated()) / 1000;
        double total = 0;

        for (Map.Entry<EntityType, Integer> entry : getSpawnersInChunk().entrySet()) {
            EntityType type = entry.getKey();
            int amountSpawners = entry.getValue();
            total += AstroCollectors.INSTANCE.getSpawnerUtil().getMoneyPerSecond(type) * secondsPassed * amountSpawners;

        }
        if (HCFOutpostAPI.isControllingOutpost(FactionUtils.getFactionAt(this.getLocation()), OutpostType.THE_GREAT_HALL)) {
            total *= 1.25;
        }

        if (OutpostUtil.hasPlutoOutpost(FactionUtils.getFactionAt(this.getLocation()))) {
            total *= 1.25;
        }
        return total;
    }

    private int calculateTnt() {
        if (!this.getSpawnersInChunk().containsKey(EntityType.CREEPER)) return 0;
        long current = System.currentTimeMillis();
        long secondsPassed = (current - this.getLastUpdated()) / 1000;
        double total = AstroCollectors.INSTANCE.getSpawnerUtil().getTntPerSecond() * secondsPassed * this.getSpawnersInChunk().get(EntityType.CREEPER);
        return (int) total;
    }

    public void update() {
        this.updateWorth();
        this.updateTnt();
        this.setLastUpdated(System.currentTimeMillis());
    }

    private void updateWorth() {
        this.addWorth(this.calculateWorth());
    }

    private void updateTnt() {
        this.addTnt(this.calculateTnt());
    }

    private void addWorth(double amount) {
        this.worth += amount;
    }

    private void addTnt(double amount) {
        this.tnt += amount;
    }

    public void addTNTSellLog(Player seller, int amount) {
        boolean canOffer = this.sellLogs.offer(new CollectorSellLog(seller.getName(), CollectorSellLog.SellLogType.TNT, amount));

        if (!canOffer) {
            this.sellLogs.poll();
            this.sellLogs.offer(new CollectorSellLog(seller.getName(), CollectorSellLog.SellLogType.TNT, amount));
        }
    }

    public void addMobDropSellLog(Player seller, double worth) {
        boolean canOffer = this.sellLogs.offer(new CollectorSellLog(seller.getName(), CollectorSellLog.SellLogType.MOB_DROPS, worth));

        if (!canOffer) {
            this.sellLogs.poll();
            this.sellLogs.offer(new CollectorSellLog(seller.getName(), CollectorSellLog.SellLogType.MOB_DROPS, worth));
        }
    }

    public List<CollectorSellLog> getSellLogs() {
        return ImmutableList.copyOf(sellLogs);
    }

    public void clearSpawners() {
        this.spawnersInChunk.clear();
    }

    public Location getLocation() {
        World world = this.reference.get();
        if (world == null) {
            return null;
        }
        return new Location(world, x, y, z);
    }

    void invalidate() {
        this.reference.clear();
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
        jsonObject.put("lastUpdated", this.lastUpdated);
        jsonObject.put("worth", this.worth);
        jsonObject.put("tnt", this.tnt);
        return jsonObject;
    }
}
