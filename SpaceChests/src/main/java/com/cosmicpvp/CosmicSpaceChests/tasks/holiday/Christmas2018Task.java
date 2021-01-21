package com.cosmicpvp.CosmicSpaceChests.tasks.holiday;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.CosmicSpaceChests.utils.MiscUtil;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Christmas2018Task
implements Runnable {
    private int nextSpawnInSeconds;
    private int maxDelay;
    private int minDelay;
    private long lastPumpkinEventTime;
    private CosmicSpaceChestsPlugin plugin;
    private static UUID lastBossSpawned;

    public Christmas2018Task(CosmicSpaceChestsPlugin plugin) {
        this.plugin = plugin;
        this.maxDelay = plugin.getConfig().getInt("cosmic_christmas.max_delay");
        this.minDelay = plugin.getConfig().getInt("cosmic_christmas.min_delay");
        this.nextSpawnInSeconds = this.getRandomNextSpawn();
    }

    @Override
    public void run() {
        if (this.nextSpawnInSeconds <= 0) {
            this.nextSpawnInSeconds = this.getRandomNextSpawn();
            this.lastPumpkinEventTime = System.currentTimeMillis();
            Christmas2018Task.spawnCosmicChristmasBoss();
        } else {
            --this.nextSpawnInSeconds;
            if (this.nextSpawnInSeconds == 60) {
                this.alert("60 SECONDS");
            } else if (this.nextSpawnInSeconds == 300) {
                this.alert("5 MINUTES");
            } else if (this.nextSpawnInSeconds == 600) {
                this.alert("10 MINUTES");
            } else if (this.nextSpawnInSeconds == 900) {
                this.alert("15 MINUTES");
            }
        }
    }

    public static void spawnCosmicChristmasBoss() {
        Bukkit.getServer().broadcastMessage("");
        Bukkit.getServer().broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "*** " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "CHRISTMAS EVENT" + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + " *** ");
        Bukkit.getServer().broadcastMessage((Object)ChatColor.GREEN + "A " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Christmas Blizzard" + (Object)ChatColor.GREEN + " has started in the " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Battlefield Warp!" + (Object)ChatColor.GREEN + "An " + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "Evil Holiday Spirit" + (Object)ChatColor.GREEN + " boss has spawned at the center of the Battlefield stronghold! Once defeated christmas presents filled with " + CC.GreenU + "extremely rare loot" + CC.Green + " will drop nearby!");
        Bukkit.broadcastMessage((String)"");
        World world_arena = Bukkit.getWorld((String)"world_arena");
        if (lastBossSpawned != null) {
            for (LivingEntity ent : world_arena.getLivingEntities()) {
                if (ent == null || !ent.getUniqueId().equals(lastBossSpawned)) continue;
                Bukkit.getLogger().info("Cancelling spawn of Evil Abomination since it was still alive!");
                return;
            }
        }
        Location spawn = world_arena.getBlockAt(337, 83, 68).getLocation();
        Boss boss = BossType.CHRISTMAS_SPIRIT.createInstance(spawn);
        boss.onBossSpawn();
        if (boss.getEntity() != null) {
            boss.getEntity().setMetadata("christmasSpirit", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)System.currentTimeMillis()));
            lastBossSpawned = boss.getUuid();
        }
    }

    public static void spawnCosmicPumpkin(String summoner) {
        double chests_per_location = 0;
        ArrayList<Location> drop_zones = new ArrayList<Location>();
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            pl.playSound(pl.getLocation(), Sound.GHAST_DEATH, 1.0f, 0.5f);
            if (!pl.getWorld().getName().equals("world_arena") || MiscUtil.getRegionName(pl.getLocation()).startsWith("spawn_")) continue;
            drop_zones.add(pl.getLocation());
        }
        double d = drop_zones.size() > 0 ? (double)(drop_zones.size() < 10 ? 50L : Math.round(75.0 / (double)drop_zones.size())) : (chests_per_location = 50.0);
        if (chests_per_location < 1.0) {
            chests_per_location = 1.0;
        }
        int presentsDropped = 0;
        for (Location loc : drop_zones) {
            if (presentsDropped > 15) break;
            int i = 0;
            while ((double)i < chests_per_location) {
                Location cloc = Christmas2018Task.getNearbyLocation(loc, 50, 50);
                if (!MiscUtil.getRegionName(cloc).startsWith("spawn_")) {
                    Christmas2018Task.dropChest(cloc);
                    ++presentsDropped;
                }
                ++i;
            }
        }
        CosmicSpaceChestsPlugin.log.debug("Executed DropPumpkinTask() with chests_per_location = " + chests_per_location + "!", Christmas2018Task.class);
        Bukkit.broadcastMessage((String)"");
        Bukkit.getServer().broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "*** " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "CHRISTMAS EVENT" + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + " *** ");
        Bukkit.broadcastMessage((String)(CC.Green + "The " + CC.Red + "Evil Holiday Spirit" + CC.Green + " has been defeated! Presents stuffed with extremely rare loot can be seen falling all over the " + CC.Red + "/battlefield WarZone" + CC.Green + "!"));
        Bukkit.getServer().broadcastMessage("");
        if (summoner != null) {
            Bukkit.getServer().broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Christmas Blizzard Summoner: " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + summoner + "!");
        }
    }

    public void alert(String time) {
        Bukkit.broadcastMessage((String)"");
        Bukkit.getServer().broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "*** " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "CHRISTMAS EVENT" + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + " *** ");
        Bukkit.getServer().broadcastMessage((Object)ChatColor.GREEN + "A " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Christmas Blizzard" + (Object)ChatColor.GREEN + " is starting at the " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Battlefield Warp" + (Object)ChatColor.GREEN + " in " + time + ", will you be " + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "NAUGHTY or NICE?");
        Bukkit.getServer().broadcastMessage((Object)ChatColor.GRAY + "The treasure filled chests will spawn around players in the PvP enabled " + (Object)ChatColor.YELLOW + "/warp battlefield" + (Object)ChatColor.GRAY + " area after the " + CC.RedB + "Evil Holiday Spirit" + CC.Gray + " has been defeated!");
        Bukkit.broadcastMessage((String)"");
    }

    public static Location getNearbyLocation(Location l, int radius, int y_boost) {
        Random rand = new Random();
        Location rand_loc = l.clone();
        rand_loc.add((double)((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 8)), 0.0, (double)((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 8)));
        rand_loc.add(0.5, (double)y_boost, 0.5);
        while ((rand_loc.getBlock().getType() != Material.AIR || rand_loc.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) && rand_loc.getY() < 255.0) {
            rand_loc = rand_loc.add(0.0, 1.0, 0.0);
        }
        return rand_loc;
    }

    public static void dropChest(Location l) {
        CosmicSpaceChestsPlugin.log.debug("(DE) Falling christmas spawning near " + l.toString(), CosmicSpaceChestsPlugin.class);
        l.getWorld().spawnFallingBlock(l, Material.CHEST.getId(), (byte)0);
    }

    public int getRandomNextSpawn() {
        return (ThreadLocalRandom.current().nextInt(this.maxDelay - this.minDelay) + this.minDelay) * 60;
    }

    public int getNextSpawnInSeconds() {
        return this.nextSpawnInSeconds;
    }

    public void setNextSpawnInSeconds(int nextSpawnInSeconds) {
        this.nextSpawnInSeconds = nextSpawnInSeconds;
    }
}

