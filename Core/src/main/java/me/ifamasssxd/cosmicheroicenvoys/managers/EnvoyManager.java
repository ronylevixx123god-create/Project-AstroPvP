/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity
 *  com.cosmicpvp.CosmicSpaceChests.utils.MiscUtil
 *  io.apice.clientcomms.ClientCommsAPI
 *  io.apice.clientcomms.EventType
 *  me.ifamasssxd.cosmicbosses.struct.Boss
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.entity.FallingBlock
 *  org.bukkit.entity.LivingEntity
 *  org.bukkit.entity.Player
 *  org.bukkit.metadata.FixedMetadataValue
 *  org.bukkit.metadata.MetadataValue
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.scheduler.BukkitRunnable
 */
package me.ifamasssxd.cosmicheroicenvoys.managers;

import com.cosmicpvp.CosmicSpaceChests.libs.ItemRarity;
import com.cosmicpvp.CosmicSpaceChests.utils.MiscUtil;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import me.ifamasssxd.cosmicheroicenvoys.utils.LocationUtils;
import me.ifamasssxd.cosmicheroicenvoys.utils.MiscUtils;
import net.astropvp.core.AstroCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class EnvoyManager {
    /*private Location bossLocation;
    private int spawnTimeInSeconds = 0;
    private int randomDelay = 0;
    private int spawnTimer = 0;
    private final Random random = new Random();
    private UUID bossUUID;
    private BukkitRunnable task;
    public long lastBossSpawn = System.currentTimeMillis();

    public void startTimer() {
        this.bossLocation = LocationUtils.getLocation(AstroCore.getInstance().getConfig().getString("bossSpawn"));
        this.randomDelay = AstroCore.getInstance().getConfig().getInt("randomDelay");
        this.spawnTimeInSeconds = AstroCore.getInstance().getConfig().getInt("spawnTimeInSeconds", 14400);
        this.startEnvoyTimer();
    }

    private ItemRarity getRandomItemRarity() {
        int x = this.random.nextInt(1000);
        if (x <= 500) {
            return ItemRarity.ELITE;
        }
        if (x <= 750) {
            return ItemRarity.ULTIMATE;
        }
        if (x <= 950) {
            return ItemRarity.LEGENDARY;
        }
        return ItemRarity.ELITE;
    }

    public void spawnBoss() {
        this.lastBossSpawn = System.currentTimeMillis();
        Boss boss = BossType.PLAGUE_BLOATER.createInstance(this.bossLocation);
        boss.onBossSpawn();
        LivingEntity entity = boss.getEntity();
        entity.setMetadata("heroicEnvoyBoss", new FixedMetadataValue(AstroCore.getInstance(), ""));
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "** HEROIC ENVOY BOSS SPAWNED AT BATTLEFIELD! **");
        Bukkit.broadcastMessage(ChatColor.GRAY + "Kill the boss at " + ChatColor.GRAY + ChatColor.UNDERLINE.toString() + "/warp battlefield" + ChatColor.GRAY + " to spawn a Heroic Envoy and unlock " + ChatColor.GRAY + ChatColor.UNDERLINE + "faction crystals" + ChatColor.GRAY + "!");
        Bukkit.broadcastMessage("");
        this.bossUUID = boss.getUuid();
    }

    public void startEnvoyTimer() {
        int delay;
        this.spawnTimer = delay = this.spawnTimeInSeconds + (this.random.nextBoolean() ? -1 : 1) * this.random.nextInt(this.randomDelay);
        if (this.task != null) {
            this.task.cancel();
            if (Bukkit.getScheduler().isCurrentlyRunning(this.task.getTaskId())) {
                Bukkit.getLogger().info("Cancelling task to restart..");
                Bukkit.getScheduler().cancelTask(this.task.getTaskId());
            }
        }
        System.out.println("Spawning envoy boss in " + this.spawnTimer + " seconds ( " + this.spawnTimer / 60 + "mins).");
        this.task = new BukkitRunnable(){

            public void run() {
                if (EnvoyManager.this.spawnTimer <= 0) {
                    if (EnvoyManager.this.getBossLocation() == null) {
                        Bukkit.getConsoleSender().sendMessage("****************************************");
                        Bukkit.getConsoleSender().sendMessage("Heroic Envoy Boss location was null.");
                        Bukkit.getConsoleSender().sendMessage("The boss task has not ran.");
                        Bukkit.getConsoleSender().sendMessage("****************************************");
                        return;
                    }
                    this.cancel();
                    if (EnvoyManager.this.bossUUID != null && MiscUtils.getEntity(EnvoyManager.this.bossUUID, EnvoyManager.this.bossLocation.getWorld()) != null) {
                        EnvoyManager.this.startEnvoyTimer();
                        return;
                    }
                    EnvoyManager.this.spawnBoss();
                    EnvoyManager.this.startEnvoyTimer();
                    return;
                }
                if (EnvoyManager.this.spawnTimer % 240 == 0 && EnvoyManager.this.bossUUID != null && MiscUtils.getEntity(EnvoyManager.this.bossUUID, EnvoyManager.this.bossLocation.getWorld()) != null) {
                    Bukkit.broadcastMessage("");
                    Bukkit.broadcastMessage(ChatColor.AQUA + ChatColor.BOLD.toString() + "(!) " + ChatColor.AQUA + ChatColor.BOLD.toString() + "A Heroic Envoy Boss is at /warp battlefield!");
                    Bukkit.broadcastMessage(ChatColor.GRAY + "Defeat it to unlock a heroic envoy and " + ChatColor.GRAY + "faction crystals!");
                    Bukkit.broadcastMessage("");
                }
                if (EnvoyManager.this.spawnTimer == 300) {
                    EnvoyManager.this.notifyPlayers("5 MINUTES");
                    Bukkit.broadcastMessage(ChatColor.GRAY + "Go to " + ChatColor.GRAY + ChatColor.UNDERLINE.toString() + "/warp battlefield" + ChatColor.GRAY + " and prepare to battle!");
                } else if (EnvoyManager.this.spawnTimer == 60) {
                    EnvoyManager.this.notifyPlayers("1 MINUTE");
                    Bukkit.broadcastMessage(ChatColor.GRAY + "Go to " + ChatColor.GRAY + ChatColor.UNDERLINE.toString() + "/warp battlefield" + ChatColor.GRAY + " and prepare to battle!");
                } else if (EnvoyManager.this.spawnTimer == 30 || EnvoyManager.this.spawnTimer <= 5 && EnvoyManager.this.spawnTimer > 0) {
                    EnvoyManager.this.notifyPlayers(EnvoyManager.this.spawnTimer + " SECOND" + (EnvoyManager.this.spawnTimer == 1 ? "" : "S"));
                    if (EnvoyManager.this.spawnTimer == 1) {
                        Bukkit.broadcastMessage(ChatColor.GRAY + "        Use " + ChatColor.GRAY + ChatColor.UNDERLINE.toString() + "/warp battlefield" + ChatColor.GRAY + " to fight the Heroic Boss!");
                    }
                }
                EnvoyManager.this.spawnTimer--;
            }
        };
        this.task.runTaskTimer(AstroCore.getInstance(), 20L, 20L);
    }

    public void notifyPlayers(String timer) {
        Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "*** HEROIC ENVOY BOSS SPAWNING IN " + ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + timer + ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + " ***");
    }

    public void spawnEnvoyBlocks(Location center) {
        this.bossUUID = null;
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "*** THE HEROIC ENVOY BOSS HAS BEEN DEFEATED! ***");
        Bukkit.broadcastMessage(ChatColor.GRAY + "         A Heroic Envoy is now falling near its corpse!");
        Bukkit.broadcastMessage("");
        ArrayList dropLocations = new ArrayList();
        double chests_per_location = 10 + this.random.nextInt(5);
        Bukkit.getLogger().info("BattlefieldEvent: Dropping " + chests_per_location + " chests around " + center.getBlockX() + ", " + center.getBlockY() + ", " + center.getBlockZ());
        int dropped = 0;
        int i = 0;
        while ((double)i < chests_per_location) {
            Location nearby = EnvoyManager.getNearbyLocation(center, 30, 30);
            if (!MiscUtil.getRegionName(nearby).startsWith("spawn_")) {
                this.dropChest(nearby);
                ++dropped;
            }
            ++i;
        }
        Bukkit.getLogger().info("BattlefieldEvent: Managed to find " + dropped + " valid chest spawn locations");
    }

    public void dropChest(Location l) {
        ItemRarity rarity = this.getRandomItemRarity();
        AstroCore.getInstance().getLogger().info("(DE) Falling heroic envoy with rarity " + rarity.name() + " near " + l.toString());
        FallingBlock entity = l.getWorld().spawnFallingBlock(l, Material.CHEST, (byte)0);
        entity.setMetadata("chestRarity", new FixedMetadataValue(AstroCore.getInstance(), rarity.name()));
    }

    public static Location getNearbyLocation(Location l, int radius, int y_boost) {
        Random rand = new Random();
        Location rand_loc = l.clone();
        rand_loc.add((rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 8), 0.0, (rand.nextBoolean() ? 1 : -1) * (rand.nextInt(radius) + 8));
        rand_loc.add(0.5, y_boost, 0.5);
        while ((rand_loc.getBlock().getType() != Material.AIR || rand_loc.getBlock().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR) && rand_loc.getY() < 255.0) {
            rand_loc = rand_loc.add(0.0, 1.0, 0.0);
        }
        return rand_loc;
    }

    public void setBossLocation(Location bossLocation) {
        this.bossLocation = bossLocation;
    }

    public Location getBossLocation() {
        return this.bossLocation;
    }

    public UUID getBossUUID() {
        return this.bossUUID;
    }

    public long getLastBossSpawn() {
        return this.lastBossSpawn;
    }*/
}

