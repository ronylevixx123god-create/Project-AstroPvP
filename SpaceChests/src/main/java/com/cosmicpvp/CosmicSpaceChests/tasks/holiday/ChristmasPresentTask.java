/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  me.ifamasssxd.cosmicbosses.struct.enums.BossType
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Sound
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package com.cosmicpvp.CosmicSpaceChests.tasks.holiday;

import com.cosmicpvp.CosmicSpaceChests.CosmicSpaceChestsPlugin;
import com.cosmicpvp.CosmicSpaceChests.utils.MiscUtil;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicbosses.struct.enums.BossType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ChristmasPresentTask
implements Runnable {
    private static CosmicSpaceChestsPlugin plugin;
    private static long lastChristmasEventTime;
    private static long currentChristmasDelay;
    private boolean firstRun = true;
    Random r;

    public ChristmasPresentTask(CosmicSpaceChestsPlugin fdp) {
        plugin = fdp;
        this.r = new Random();
        currentChristmasDelay = (long)new Random().nextInt(plugin.getConfig().getInt("cosmic_christmas.max_delay") - plugin.getConfig().getInt("cosmic_christmas.min_delay")) * 1200L;
    }

    @Override
    public void run() {
        if (Bukkit.getServer().getOnlinePlayers().size() == 0) {
            return;
        }
        if (!this.firstRun) {
            currentChristmasDelay = (long)new Random().nextInt(plugin.getConfig().getInt("cosmic_christmas.max_delay") - plugin.getConfig().getInt("cosmic_christmas.min_delay")) * 1200L;
        }
        this.firstRun = false;
        Bukkit.getScheduler().runTaskLater((Plugin)CosmicSpaceChestsPlugin.plugin, new Runnable(){

            @Override
            public void run() {
                Bukkit.broadcastMessage((String)"");
                Bukkit.getServer().broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "*** " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "CHRISTMAS EVENT" + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + " *** ");
                Bukkit.getServer().broadcastMessage((Object)ChatColor.GREEN + "A " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Christmas Blizzard" + (Object)ChatColor.GREEN + " is starting at the " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Battlefield Warp" + (Object)ChatColor.GREEN + " in 60 SECONDS, will you be " + ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "NAUGHTY or NICE?");
                Bukkit.getServer().broadcastMessage((Object)ChatColor.GRAY + "The treasure filled chests will spawn around players in the PvP enabled " + (Object)ChatColor.YELLOW + "/warp battlefield" + (Object)ChatColor.GRAY + " area.");
                Bukkit.broadcastMessage((String)"");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 2.0f, 0.4f);
                }
                Bukkit.getScheduler().runTaskLater((Plugin)CosmicSpaceChestsPlugin.plugin, new Runnable(){

                    @Override
                    public void run() {
                        lastChristmasEventTime = System.currentTimeMillis();
                        ChristmasPresentTask.spawnCosmicChristmas(null);
                    }
                }, 1200L);
            }
        }, currentChristmasDelay);
    }

    public static long getNextChristmasSpawn() {
        return lastChristmasEventTime + currentChristmasDelay * 50L + (long)(plugin.getConfig().getInt("cosmic_christmas.min_delay") * 60 * 20 * 50);
    }

    public static long getPreviousChristmasSpawn() {
        return lastChristmasEventTime;
    }

    public static String getPreviousChristmasSpawnString() {
        return ChristmasPresentTask.getTimeString(System.currentTimeMillis() - lastChristmasEventTime);
    }

    public static String getNextChristmasTimeString() {
        return ChristmasPresentTask.getTimeString(ChristmasPresentTask.getNextChristmasSpawn() - System.currentTimeMillis());
    }

    private static String getTimeString(long millis) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    public static void spawnCosmicChristmas(String summoner) {
        double chests_per_location;
        ArrayList<Location> drop_zones = new ArrayList<Location>();
        boolean factions = Bukkit.getPluginManager().isPluginEnabled("Factions");
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            pl.playSound(pl.getLocation(), Sound.BAT_TAKEOFF, 1.0f, 0.5f);
            if (!pl.getWorld().getName().equals("world_arena") || MiscUtil.getRegionName(pl.getLocation()).startsWith("spawn_")) continue;
            drop_zones.add(pl.getLocation());
        }
        double d = drop_zones.size() > 0 ? (double)(drop_zones.size() < 10 ? 50L : Math.round(75.0 / (double)drop_zones.size())) : (chests_per_location = 50.0);
        if (chests_per_location < 1.0) {
            chests_per_location = 1.0;
        }
        int christmassDropped = 0;
        for (Location loc : drop_zones) {
            if (christmassDropped > 15) break;
            int i = 0;
            while ((double)i < chests_per_location) {
                Location cloc = ChristmasPresentTask.getNearbyLocation(loc, 50, 50);
                if (!MiscUtil.getRegionName(cloc).startsWith("spawn_") && cloc.getY() <= 170.0) {
                    ChristmasPresentTask.dropChest(cloc);
                    ++christmassDropped;
                }
                ++i;
            }
        }
        Location spawn = Bukkit.getWorld((String)"world_arena").getBlockAt(337, 83, 68).getLocation();
        BossType.CHRISTMAS_SPIRIT.createInstance(spawn).onBossSpawn();
        CosmicSpaceChestsPlugin.log.debug("Executed DropChristmasTask() with chests_per_location = " + chests_per_location + "!", ChristmasPresentTask.class);
        Bukkit.getServer().broadcastMessage("");
        Bukkit.getServer().broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "*** " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + ChatColor.UNDERLINE.toString() + "CHRISTMAS EVENT" + ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + " *** ");
        Bukkit.getServer().broadcastMessage((Object)ChatColor.GREEN + "A " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Christmas Blizzard" + (Object)ChatColor.GREEN + " has started in the " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + "Battlefield Warp" + (Object)ChatColor.GREEN + " those christmas presents are filled with " + (Object)ChatColor.UNDERLINE + "extremely rare loot!" + (Object)ChatColor.GREEN + " Additionally, a " + (Object)ChatColor.RED + (Object)ChatColor.BOLD + "Holiday Spirit" + (Object)ChatColor.GREEN + " boss has spawned at the center of the Battlefield stronghold!");
        if (summoner != null) {
            Bukkit.getServer().broadcastMessage(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + "Christmas Blizzard Summoner: " + ChatColor.RED.toString() + ChatColor.BOLD.toString() + summoner + "!");
        }
        Bukkit.getServer().broadcastMessage("");
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

    static {
        lastChristmasEventTime = System.currentTimeMillis();
        currentChristmasDelay = 0L;
    }
}

