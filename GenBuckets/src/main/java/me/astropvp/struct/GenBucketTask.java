package me.astropvp.struct;

import com.cosmicpvp.cosmicutils.utils.FactionUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import me.astropvp.utils.KrizzUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class GenBucketTask extends BukkitRunnable {
    private static Map<UUID, List<GenBucketTask>> tasks = Maps.newHashMap();
    private Player player;
    private Block block;
    private BlockFace blockFace;
    private Location currentLocation;
    private ActiveBucket activeBucket;
    private int totalBlocks;
    private int maxBlocks;
    private Faction placedFaction;
    private UUID uuid;

    public GenBucketTask(Player player, Block clickedBlock, BlockFace blockFace, BlockFace originalFace, ActiveBucket activeBucket) {
        this.player = player;
        this.block = clickedBlock.getLocation().add((double)originalFace.getModX(), (double)originalFace.getModY(), (double)originalFace.getModZ()).getBlock();
        this.blockFace = blockFace;
        this.currentLocation = this.block.getLocation();
        this.activeBucket = activeBucket;
        this.maxBlocks = activeBucket.getDirection().getMaxBlocks();
        this.placedFaction = FactionUtils.getFPlayer(player).getFaction();
        this.uuid = UUID.randomUUID();
        if (!tasks.containsKey(player.getUniqueId())) {
            tasks.put(player.getUniqueId(), Lists.newArrayList(new GenBucketTask[]{this}));
        } else {
            List<GenBucketTask> genTasks = (List)tasks.get(player.getUniqueId());
            genTasks.add(this);
            tasks.put(player.getUniqueId(), genTasks);
        }

    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void run() {
        try {
            if ((this.currentLocation.getBlock().getType() != Material.AIR || !this.isLocationValid()) && (this.currentLocation.getBlock().getType() != Material.GRASS || !this.isLocationValid()) &&  (this.currentLocation.getBlock().getType() != Material.LONG_GRASS || !this.isLocationValid()) && (this.currentLocation.getBlock().getType() != Material.STATIONARY_WATER || !this.isLocationValid()) && (this.currentLocation.getBlock().getType() != Material.WATER || !this.isLocationValid())) {
                this.endTask();
                return;
            }

            this.currentLocation.getBlock().setType(this.activeBucket.getBucket().getPlacedMaterial());
            ++this.totalBlocks;
            if (this.totalBlocks >= this.maxBlocks || this.currentLocation.getY() >= 256.0D) {
                this.endTask();
                return;
            }

            this.currentLocation.add((double)this.blockFace.getModX(), (double)this.blockFace.getModY(), (double)this.blockFace.getModZ());
        } catch (NullPointerException var2) {
            var2.printStackTrace();
        }

    }

    public ItemStack getItem() {
        Material material = this.activeBucket.getBucket().getPlacedMaterial();
        int x = this.currentLocation.getBlockX();
        int y = this.currentLocation.getBlockY();
        int z = this.currentLocation.getBlockZ();
        NBTWrapper nbtItem = new NBTWrapper(KrizzUtil.createItem(material, "&e&lGenBucket Task &7(" + KrizzUtil.niceName(material) + ")", "", " &2&l* &a&lTotal Blocks &f" + this.totalBlocks + "&7/" + this.maxBlocks, " &2&l* &a&lCurrent Location &fx" + x + " y" + y + " z" + z, " &2&l* &a&lBucket Direction &f" + this.activeBucket.getDirection().niceName() + " &7(" + this.blockFace.name() + ")", "", "&c&l[!] Click here to cancel the bucket."));
        nbtItem.setString("uuid", this.uuid.toString());
        return nbtItem.build();
    }

    public boolean isLocationValid() {
        Board.getInstance();
        Faction locFaction = Board.getFactionAt(new FLocation(this.currentLocation));
        boolean hasChunkAccess = false;
        if (this.currentLocation != null) {
            hasChunkAccess = FactionUtils.getFaction(this.player).isNormal() && FactionUtils.getFaction(this.player).getId().equals(locFaction.getId());
        }

        if (hasChunkAccess) {
            return true;
        } else {
            return locFaction == this.placedFaction;
        }
    }

    public void endTask() {
        this.cancel();
        this.dispose();
    }

    public void dispose() {
        this.block = null;
        this.currentLocation = null;
        this.blockFace = null;
        this.activeBucket = null;
        List<GenBucketTask> genTasks = (List)tasks.get(this.player.getUniqueId());
        genTasks.remove(this);
        tasks.put(this.player.getUniqueId(), genTasks);
    }

    public static List<GenBucketTask> getTasks(Player player) {
        return tasks.getOrDefault(player.getUniqueId(), Lists.newArrayList());
    }

    public static Optional<GenBucketTask> getTask(Player player, UUID uuid) {
        return ((List<GenBucketTask>)tasks.getOrDefault(player.getUniqueId(), Lists.newArrayList())).stream()
                .filter(task -> task.getUuid().equals(uuid))
                .findFirst();
    }


    public Player getPlayer() {
        return this.player;
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockFace getBlockFace() {
        return this.blockFace;
    }

    public Location getCurrentLocation() {
        return this.currentLocation;
    }

    public ActiveBucket getActiveBucket() {
        return this.activeBucket;
    }

    public int getTotalBlocks() {
        return this.totalBlocks;
    }

    public int getMaxBlocks() {
        return this.maxBlocks;
    }

    public Faction getPlacedFaction() {
        return this.placedFaction;
    }

    public static Map<UUID, List<GenBucketTask>> getTasks() {
        return tasks;
    }
}
