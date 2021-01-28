/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicBossEggs.boss.type.Bosses
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  me.ifamasssxd.cosmicbosses.struct.Boss
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.World
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 */
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

public class Halloween2018Task
implements Runnable {
    private int nextSpawnInSeconds;
    private int maxDelay;
    private int minDelay;
    private long lastPumpkinEventTime;
    private CosmicSpaceChestsPlugin plugin;
    private static UUID lastBossSpawned;

    public Halloween2018Task(CosmicSpaceChestsPlugin plugin) {
        this.plugin = plugin;
        this.maxDelay = plugin.getConfig().getInt("cosmic_pumpkin.max_delay");
        this.minDelay = plugin.getConfig().getInt("cosmic_pumpkin.min_delay");
        this.nextSpawnInSeconds = this.getRandomNextSpawn();
    }

    @Override
    public void run() {
        if (this.nextSpawnInSeconds <= 0) {
            this.nextSpawnInSeconds = this.getRandomNextSpawn();
            this.lastPumpkinEventTime = System.currentTimeMillis();
            Halloween2018Task.spawnCosmicPumpkinBoss();
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

    public static void spawnCosmicPumpkinBoss() {
        Bukkit.broadcastMessage((String)"");
        Bukkit.broadcastMessage((String)(CC.GoldB + "*** " + CC.YellowB + CC.Underline + "HALLOWEEN EVENT" + CC.GoldB + " *** "));
        Bukkit.broadcastMessage((String)((Object)ChatColor.GOLD + "An " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Evil Abomination Boss" + (Object)ChatColor.GOLD + " has spawned in the Unholy Temple at the /battlefield warp!"));
        Bukkit.broadcastMessage((String)(CC.Gray + "Defeat him to spawn a Pumpkin Harvest of loot filled pumpkins all over the /battlefield WarZone!"));
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
        Boss boss = BossType.EVIL_ABOMINATION.createInstance(spawn);
        boss.onBossSpawn();
        if (boss.getEntity() != null) {
            boss.getEntity().setMetadata("pumpkinHarvest", (MetadataValue)new FixedMetadataValue((Plugin)CosmicSpaceChestsPlugin.plugin, (Object)System.currentTimeMillis()));
            lastBossSpawned = boss.getUuid();
        }
    }

    public static void spawnCosmicPumpkin(String summoner) {
        double chests_per_location = 0;
        ArrayList<Location> drop_zones = new ArrayList<Location>();
        boolean factions = Bukkit.getPluginManager().isPluginEnabled("Factions");
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            pl.playSound(pl.getLocation(), Sound.GHAST_DEATH, 1.0f, 0.5f);
            if (!pl.getWorld().getName().equals("world_arena") || MiscUtil.getRegionName(pl.getLocation()).startsWith("spawn_")) continue;
            drop_zones.add(pl.getLocation());
        }
        double d = drop_zones.size() > 0 ? (double)(drop_zones.size() < 10 ? 50L : Math.round(75.0 / (double)drop_zones.size())) : (chests_per_location = 50.0);
        if (chests_per_location < 1.0) {
            chests_per_location = 1.0;
        }
        int pumpkinsDropped = 0;
        for (Location loc : drop_zones) {
            if (pumpkinsDropped > 15) break;
            int i = 0;
            while ((double)i < chests_per_location) {
                Location cloc = Halloween2018Task.getNearbyLocation(loc, 50, 50);
                if (!MiscUtil.getRegionName(cloc).startsWith("spawn_")) {
                    Halloween2018Task.dropChest(cloc);
                    ++pumpkinsDropped;
                }
                ++i;
            }
        }
        CosmicSpaceChestsPlugin.log.debug("Executed DropPumpkinTask() with chests_per_location = " + chests_per_location + "!", Halloween2018Task.class);
        Bukkit.broadcastMessage((String)"");
        Bukkit.broadcastMessage((String)(CC.GoldB + "*** " + CC.YellowB + CC.Underline + "HALLOWEEN EVENT" + CC.GoldB + " *** "));
        Bukkit.broadcastMessage((String)(CC.Gold + "The Evil Abomination has been defeated! Pumpkins stuffed with extremely rare loot can be seen falling all over the /battlefield WarZone!"));
        if (summoner != null) {
            Bukkit.getServer().broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "Cosmic Pumpkin Summoner: " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + summoner + "!");
        }
        Bukkit.getServer().broadcastMessage("");
    }

    public void alert(String time) {
        Bukkit.broadcastMessage((String)"");
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + "*** " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "HALLOWEEN EVENT" + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + " *** ");
        Bukkit.getServer().broadcastMessage((Object)ChatColor.GOLD + "A " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Pumpkin Harvest Boss" + (Object)ChatColor.GOLD + " is spawning at the " + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Battlefield Warp" + (Object)ChatColor.GOLD + " in " + time + ", get ready to TRICK OR GANK!");
        Bukkit.getServer().broadcastMessage((Object)ChatColor.GRAY + "The treasure stuffed pumpkins will spawn around players in the PvP enabled " + (Object)ChatColor.GOLD + "/warp battlefield" + (Object)ChatColor.GRAY + " after the Evil Abomination boss is defeated!");
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
        CosmicSpaceChestsPlugin.log.debug("(DE) Falling pumpkin spawning near " + l.toString(), CosmicSpaceChestsPlugin.class);
        l.getWorld().spawnFallingBlock(l, Material.PUMPKIN.getId(), (byte)0);
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

