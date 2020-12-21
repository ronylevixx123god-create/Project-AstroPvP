/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.sk89q.util.yaml.YAMLFormat
 *  com.sk89q.util.yaml.YAMLProcessor
 */
package com.sk89q.worldguard.bukkit;

import com.sk89q.util.yaml.YAMLFormat;
import com.sk89q.util.yaml.YAMLProcessor;
import com.sk89q.worldguard.blacklist.Blacklist;
import com.sk89q.worldguard.blacklist.BlacklistLogger;
import com.sk89q.worldguard.blacklist.loggers.ConsoleLoggerHandler;
import com.sk89q.worldguard.blacklist.loggers.DatabaseLoggerHandler;
import com.sk89q.worldguard.blacklist.loggers.FileLoggerHandler;
import com.sk89q.worldguard.bukkit.BukkitBlacklist;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.chest.ChestProtection;
import com.sk89q.worldguard.chest.SignChestProtection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class WorldConfiguration {
    public static final String CONFIG_HEADER = "#\r\n# WorldGuard's world configuration file\r\n#\r\n# This is a world configuration file. Anything placed into here will only\r\n# affect this world. If you don't put anything in this file, then the\r\n# settings will be inherited from the main configuration file.\r\n#\r\n# If you see {} below, that means that there are NO entries in this file.\r\n# Remove the {} and add your own entries.\r\n#\r\n";
    private WorldGuardPlugin plugin;
    private String worldName;
    private YAMLProcessor parentConfig;
    private YAMLProcessor config;
    private File blacklistFile;
    private Blacklist blacklist;
    private ChestProtection chestProtection = new SignChestProtection();
    public boolean summaryOnStart;
    public boolean opPermissions;
    public boolean fireSpreadDisableToggle;
    public boolean itemDurability;
    public boolean simulateSponge;
    public int spongeRadius;
    public boolean disableExpDrops;
    public Set<PotionEffectType> blockPotions;
    public boolean blockPotionsAlways;
    public boolean pumpkinScuba;
    public boolean redstoneSponges;
    public boolean noPhysicsGravel;
    public boolean noPhysicsSand;
    public boolean ropeLadders;
    public boolean allowPortalAnywhere;
    public Set<Integer> preventWaterDamage;
    public boolean blockLighter;
    public boolean disableFireSpread;
    public Set<Integer> disableFireSpreadBlocks;
    public boolean preventLavaFire;
    public Set<Integer> allowedLavaSpreadOver;
    public boolean blockTNTExplosions;
    public boolean blockTNTBlockDamage;
    public boolean blockCreeperExplosions;
    public boolean blockCreeperBlockDamage;
    public boolean blockWitherExplosions;
    public boolean blockWitherBlockDamage;
    public boolean blockWitherSkullExplosions;
    public boolean blockWitherSkullBlockDamage;
    public boolean blockEnderDragonBlockDamage;
    public boolean blockEnderDragonPortalCreation;
    public boolean blockFireballExplosions;
    public boolean blockFireballBlockDamage;
    public boolean blockOtherExplosions;
    public boolean blockEntityPaintingDestroy;
    public boolean blockEntityItemFrameDestroy;
    public boolean blockPluginSpawning;
    public boolean blockGroundSlimes;
    public boolean blockZombieDoorDestruction;
    public boolean disableContactDamage;
    public boolean disableFallDamage;
    public boolean disableLavaDamage;
    public boolean disableFireDamage;
    public boolean disableLightningDamage;
    public boolean disableDrowningDamage;
    public boolean disableSuffocationDamage;
    public boolean teleportOnSuffocation;
    public boolean disableVoidDamage;
    public boolean teleportOnVoid;
    public boolean disableExplosionDamage;
    public boolean disableMobDamage;
    public boolean useRegions;
    public boolean highFreqFlags;
    public int regionWand;
    public Set<EntityType> blockCreatureSpawn;
    public boolean allowTamedSpawns;
    public int maxClaimVolume;
    public boolean claimOnlyInsideExistingRegions;
    public int maxRegionCountPerPlayer;
    public boolean antiWolfDumbness;
    public boolean signChestProtection;
    public boolean disableSignChestProtectionCheck;
    public boolean removeInfiniteStacks;
    public boolean disableCreatureCropTrampling;
    public boolean disablePlayerCropTrampling;
    public boolean preventLightningFire;
    public Set<Integer> disallowedLightningBlocks;
    public boolean disableThunder;
    public boolean disableWeather;
    public boolean alwaysRaining;
    public boolean alwaysThundering;
    public boolean disablePigZap;
    public boolean disableCreeperPower;
    public boolean disableHealthRegain;
    public boolean disableMushroomSpread;
    public boolean disableIceMelting;
    public boolean disableSnowMelting;
    public boolean disableSnowFormation;
    public boolean disableIceFormation;
    public boolean disableLeafDecay;
    public boolean disableGrassGrowth;
    public boolean disableMyceliumSpread;
    public boolean disableVineGrowth;
    public boolean disableCropGrowth;
    public boolean disableEndermanGriefing;
    public boolean disableSnowmanTrails;
    public boolean disableSoilDehydration;
    public Set<Integer> allowedSnowFallOver;
    public boolean regionInvinciblityRemovesMobs;
    public boolean explosionFlagCancellation;
    public boolean disableDeathMessages;
    public boolean disableObsidianGenerators;
    private Map<String, Integer> maxRegionCounts;

    public WorldConfiguration(WorldGuardPlugin plugin, String worldName, YAMLProcessor parentConfig) {
        File baseFolder = new File(plugin.getDataFolder(), "worlds/" + worldName);
        File configFile = new File(baseFolder, "config.yml");
        this.blacklistFile = new File(baseFolder, "blacklist.txt");
        this.plugin = plugin;
        this.worldName = worldName;
        this.parentConfig = parentConfig;
        plugin.createDefaultConfiguration(configFile, "config_world.yml");
        plugin.createDefaultConfiguration(this.blacklistFile, "blacklist.txt");
        this.config = new YAMLProcessor(configFile, true, YAMLFormat.EXTENDED);
        this.loadConfiguration();
        if (this.summaryOnStart) {
            plugin.getLogger().info("Loaded configuration for world '" + worldName + "'");
        }
    }

    private boolean getBoolean(String node, boolean def) {
        boolean val = this.parentConfig.getBoolean(node, def);
        if (this.config.getProperty(node) != null) {
            return this.config.getBoolean(node, def);
        }
        return val;
    }

    private String getString(String node, String def) {
        String val = this.parentConfig.getString(node, def);
        if (this.config.getProperty(node) != null) {
            return this.config.getString(node, def);
        }
        return val;
    }

    private int getInt(String node, int def) {
        int val = this.parentConfig.getInt(node, def);
        if (this.config.getProperty(node) != null) {
            return this.config.getInt(node, def);
        }
        return val;
    }

    private double getDouble(String node, double def) {
        double val = this.parentConfig.getDouble(node, def);
        if (this.config.getProperty(node) != null) {
            return this.config.getDouble(node, def);
        }
        return val;
    }

    private List<Integer> getIntList(String node, List<Integer> def) {
        List res = this.parentConfig.getIntList(node, def);
        if (res == null || res.size() == 0) {
            this.parentConfig.setProperty(node, new ArrayList());
        }
        if (this.config.getProperty(node) != null) {
            res = this.config.getIntList(node, def);
        }
        return res;
    }

    private List<String> getStringList(String node, List<String> def) {
        List res = this.parentConfig.getStringList(node, def);
        if (res == null || res.size() == 0) {
            this.parentConfig.setProperty(node, new ArrayList());
        }
        if (this.config.getProperty(node) != null) {
            res = this.config.getStringList(node, def);
        }
        return res;
    }

    private List<String> getKeys(String node) {
        ArrayList res = this.parentConfig.getKeys(node);
        if (res == null || res.size() == 0) {
            res = this.config.getKeys(node);
        }
        if (res == null) {
            res = new ArrayList();
        }
        return res;
    }

    private Object getProperty(String node) {
        Object res = this.parentConfig.getProperty(node);
        if (this.config.getProperty(node) != null) {
            res = this.config.getProperty(node);
        }
        return res;
    }

    private void loadConfiguration() {
        try {
            this.config.load();
        }
        catch (IOException e) {
            this.plugin.getLogger().severe("Error reading configuration for world " + this.worldName + ": ");
            e.printStackTrace();
        }
        this.summaryOnStart = this.getBoolean("summary-on-start", true);
        this.opPermissions = this.getBoolean("op-permissions", true);
        this.itemDurability = this.getBoolean("protection.item-durability", true);
        this.removeInfiniteStacks = this.getBoolean("protection.remove-infinite-stacks", false);
        this.disableExpDrops = this.getBoolean("protection.disable-xp-orb-drops", false);
        this.disableObsidianGenerators = this.getBoolean("protection.disable-obsidian-generators", false);
        this.blockPotions = new HashSet<PotionEffectType>();
        for (String potionName : this.getStringList("gameplay.block-potions", null)) {
            PotionEffectType effect = PotionEffectType.getByName(potionName);
            if (effect == null) {
                this.plugin.getLogger().warning("Unknown potion effect type '" + potionName + "'");
                continue;
            }
            this.blockPotions.add(effect);
        }
        this.blockPotionsAlways = this.getBoolean("gameplay.block-potions-overly-reliably", false);
        this.simulateSponge = this.getBoolean("simulation.sponge.enable", true);
        this.spongeRadius = Math.max(1, this.getInt("simulation.sponge.radius", 3)) - 1;
        this.redstoneSponges = this.getBoolean("simulation.sponge.redstone", false);
        this.pumpkinScuba = this.getBoolean("default.pumpkin-scuba", false);
        this.disableHealthRegain = this.getBoolean("default.disable-health-regain", false);
        this.noPhysicsGravel = this.getBoolean("physics.no-physics-gravel", false);
        this.noPhysicsSand = this.getBoolean("physics.no-physics-sand", false);
        this.ropeLadders = this.getBoolean("physics.vine-like-rope-ladders", false);
        this.allowPortalAnywhere = this.getBoolean("physics.allow-portal-anywhere", false);
        this.preventWaterDamage = new HashSet<Integer>(this.getIntList("physics.disable-water-damage-blocks", null));
        this.blockTNTExplosions = this.getBoolean("ignition.block-tnt", false);
        this.blockTNTBlockDamage = this.getBoolean("ignition.block-tnt-block-damage", false);
        this.blockLighter = this.getBoolean("ignition.block-lighter", false);
        this.preventLavaFire = this.getBoolean("fire.disable-lava-fire-spread", true);
        this.disableFireSpread = this.getBoolean("fire.disable-all-fire-spread", false);
        this.disableFireSpreadBlocks = new HashSet<Integer>(this.getIntList("fire.disable-fire-spread-blocks", null));
        this.allowedLavaSpreadOver = new HashSet<Integer>(this.getIntList("fire.lava-spread-blocks", null));
        this.blockCreeperExplosions = this.getBoolean("mobs.block-creeper-explosions", false);
        this.blockCreeperBlockDamage = this.getBoolean("mobs.block-creeper-block-damage", false);
        this.blockWitherExplosions = this.getBoolean("mobs.block-wither-explosions", false);
        this.blockWitherBlockDamage = this.getBoolean("mobs.block-wither-block-damage", false);
        this.blockWitherSkullExplosions = this.getBoolean("mobs.block-wither-skull-explosions", false);
        this.blockWitherSkullBlockDamage = this.getBoolean("mobs.block-wither-skull-block-damage", false);
        this.blockEnderDragonBlockDamage = this.getBoolean("mobs.block-enderdragon-block-damage", false);
        this.blockEnderDragonPortalCreation = this.getBoolean("mobs.block-enderdragon-portal-creation", false);
        this.blockFireballExplosions = this.getBoolean("mobs.block-fireball-explosions", false);
        this.blockFireballBlockDamage = this.getBoolean("mobs.block-fireball-block-damage", false);
        this.antiWolfDumbness = this.getBoolean("mobs.anti-wolf-dumbness", false);
        this.allowTamedSpawns = this.getBoolean("mobs.allow-tamed-spawns", true);
        this.disableEndermanGriefing = this.getBoolean("mobs.disable-enderman-griefing", false);
        this.disableSnowmanTrails = this.getBoolean("mobs.disable-snowman-trails", false);
        this.blockEntityPaintingDestroy = this.getBoolean("mobs.block-painting-destroy", false);
        this.blockEntityItemFrameDestroy = this.getBoolean("mobs.block-item-frame-destroy", false);
        this.blockPluginSpawning = this.getBoolean("mobs.block-plugin-spawning", true);
        this.blockGroundSlimes = this.getBoolean("mobs.block-above-ground-slimes", false);
        this.blockOtherExplosions = this.getBoolean("mobs.block-other-explosions", false);
        this.blockZombieDoorDestruction = this.getBoolean("mobs.block-zombie-door-destruction", false);
        this.disableFallDamage = this.getBoolean("player-damage.disable-fall-damage", false);
        this.disableLavaDamage = this.getBoolean("player-damage.disable-lava-damage", false);
        this.disableFireDamage = this.getBoolean("player-damage.disable-fire-damage", false);
        this.disableLightningDamage = this.getBoolean("player-damage.disable-lightning-damage", false);
        this.disableDrowningDamage = this.getBoolean("player-damage.disable-drowning-damage", false);
        this.disableSuffocationDamage = this.getBoolean("player-damage.disable-suffocation-damage", false);
        this.disableContactDamage = this.getBoolean("player-damage.disable-contact-damage", false);
        this.teleportOnSuffocation = this.getBoolean("player-damage.teleport-on-suffocation", false);
        this.disableVoidDamage = this.getBoolean("player-damage.disable-void-damage", false);
        this.teleportOnVoid = this.getBoolean("player-damage.teleport-on-void-falling", false);
        this.disableExplosionDamage = this.getBoolean("player-damage.disable-explosion-damage", false);
        this.disableMobDamage = this.getBoolean("player-damage.disable-mob-damage", false);
        this.disableDeathMessages = this.getBoolean("player-damage.disable-death-messages", false);
        this.signChestProtection = this.getBoolean("chest-protection.enable", false);
        this.disableSignChestProtectionCheck = this.getBoolean("chest-protection.disable-off-check", false);
        this.disableCreatureCropTrampling = this.getBoolean("crops.disable-creature-trampling", false);
        this.disablePlayerCropTrampling = this.getBoolean("crops.disable-player-trampling", false);
        this.disallowedLightningBlocks = new HashSet<Integer>(this.getIntList("weather.prevent-lightning-strike-blocks", null));
        this.preventLightningFire = this.getBoolean("weather.disable-lightning-strike-fire", false);
        this.disableThunder = this.getBoolean("weather.disable-thunderstorm", false);
        this.disableWeather = this.getBoolean("weather.disable-weather", false);
        this.disablePigZap = this.getBoolean("weather.disable-pig-zombification", false);
        this.disableCreeperPower = this.getBoolean("weather.disable-powered-creepers", false);
        this.alwaysRaining = this.getBoolean("weather.always-raining", false);
        this.alwaysThundering = this.getBoolean("weather.always-thundering", false);
        this.disableMushroomSpread = this.getBoolean("dynamics.disable-mushroom-spread", false);
        this.disableIceMelting = this.getBoolean("dynamics.disable-ice-melting", false);
        this.disableSnowMelting = this.getBoolean("dynamics.disable-snow-melting", false);
        this.disableSnowFormation = this.getBoolean("dynamics.disable-snow-formation", false);
        this.disableIceFormation = this.getBoolean("dynamics.disable-ice-formation", false);
        this.disableLeafDecay = this.getBoolean("dynamics.disable-leaf-decay", false);
        this.disableGrassGrowth = this.getBoolean("dynamics.disable-grass-growth", false);
        this.disableMyceliumSpread = this.getBoolean("dynamics.disable-mycelium-spread", false);
        this.disableVineGrowth = this.getBoolean("dynamics.disable-vine-growth", false);
        this.disableCropGrowth = this.getBoolean("dynamics.disable-crop-growth", false);
        this.disableSoilDehydration = this.getBoolean("dynamics.disable-soil-dehydration", false);
        this.allowedSnowFallOver = new HashSet<Integer>(this.getIntList("dynamics.snow-fall-blocks", null));
        this.useRegions = this.getBoolean("regions.enable", true);
        this.regionInvinciblityRemovesMobs = this.getBoolean("regions.invincibility-removes-mobs", false);
        this.explosionFlagCancellation = this.getBoolean("regions.explosion-flags-block-entity-damage", true);
        this.highFreqFlags = this.getBoolean("regions.high-frequency-flags", false);
        this.regionWand = this.getInt("regions.wand", 334);
        this.maxClaimVolume = this.getInt("regions.max-claim-volume", 30000);
        this.claimOnlyInsideExistingRegions = this.getBoolean("regions.claim-only-inside-existing-regions", false);
        this.maxRegionCountPerPlayer = this.getInt("regions.max-region-count-per-player.default", 7);
        this.maxRegionCounts = new HashMap<String, Integer>();
        this.maxRegionCounts.put(null, this.maxRegionCountPerPlayer);
        for (String key : this.getKeys("regions.max-region-count-per-player")) {
            Object val;
            if (key.equalsIgnoreCase("default") || (val = this.getProperty("regions.max-region-count-per-player." + key)) == null || !(val instanceof Number)) continue;
            this.maxRegionCounts.put(key, ((Number)val).intValue());
        }
        this.blockCreatureSpawn = new HashSet<EntityType>();
        for (String creatureName : this.getStringList("mobs.block-creature-spawn", null)) {
            EntityType creature = EntityType.fromName(creatureName);
            if (creature == null) {
                this.plugin.getLogger().warning("Unknown mob type '" + creatureName + "'");
                continue;
            }
            if (!creature.isAlive()) {
                this.plugin.getLogger().warning("Entity type '" + creatureName + "' is not a creature");
                continue;
            }
            this.blockCreatureSpawn.add(creature);
        }
        boolean useBlacklistAsWhitelist = this.getBoolean("blacklist.use-as-whitelist", false);
        boolean logConsole = this.getBoolean("blacklist.logging.console.enable", true);
        boolean logDatabase = this.getBoolean("blacklist.logging.database.enable", false);
        String dsn = this.getString("blacklist.logging.database.dsn", "jdbc:mysql://localhost:3306/minecraft");
        String user = this.getString("blacklist.logging.database.user", "root");
        String pass = this.getString("blacklist.logging.database.pass", "");
        String table = this.getString("blacklist.logging.database.table", "blacklist_events");
        boolean logFile = this.getBoolean("blacklist.logging.file.enable", false);
        String logFilePattern = this.getString("blacklist.logging.file.path", "worldguard/logs/%Y-%m-%d.log");
        int logFileCacheSize = Math.max(1, this.getInt("blacklist.logging.file.open-files", 10));
        try {
            if (this.blacklist != null) {
                this.blacklist.getLogger().close();
            }
            BukkitBlacklist blist = new BukkitBlacklist((Boolean)useBlacklistAsWhitelist, this.plugin);
            blist.load(this.blacklistFile);
            if (blist.isEmpty()) {
                this.blacklist = null;
            } else {
                this.blacklist = blist;
                if (this.summaryOnStart) {
                    this.plugin.getLogger().log(Level.INFO, "Blacklist loaded.");
                }
                BlacklistLogger blacklistLogger = blist.getLogger();
                if (logDatabase) {
                    blacklistLogger.addHandler(new DatabaseLoggerHandler(dsn, user, pass, table, this.worldName, this.plugin.getLogger()));
                }
                if (logConsole) {
                    blacklistLogger.addHandler(new ConsoleLoggerHandler(this.worldName, this.plugin.getLogger()));
                }
                if (logFile) {
                    FileLoggerHandler handler = new FileLoggerHandler(logFilePattern, logFileCacheSize, this.worldName, this.plugin.getLogger());
                    blacklistLogger.addHandler(handler);
                }
            }
        }
        catch (FileNotFoundException e) {
            this.plugin.getLogger().log(Level.WARNING, "WorldGuard blacklist does not exist.");
        }
        catch (IOException e) {
            this.plugin.getLogger().log(Level.WARNING, "Could not load WorldGuard blacklist: " + e.getMessage());
        }
        if (this.summaryOnStart) {
            this.plugin.getLogger().log(Level.INFO, this.blockTNTExplosions ? "(" + this.worldName + ") TNT ignition is blocked." : "(" + this.worldName + ") TNT ignition is PERMITTED.");
            this.plugin.getLogger().log(Level.INFO, this.blockLighter ? "(" + this.worldName + ") Lighters are blocked." : "(" + this.worldName + ") Lighters are PERMITTED.");
            this.plugin.getLogger().log(Level.INFO, this.preventLavaFire ? "(" + this.worldName + ") Lava fire is blocked." : "(" + this.worldName + ") Lava fire is PERMITTED.");
            if (this.disableFireSpread) {
                this.plugin.getLogger().log(Level.INFO, "(" + this.worldName + ") All fire spread is disabled.");
            } else if (this.disableFireSpreadBlocks.size() > 0) {
                this.plugin.getLogger().log(Level.INFO, "(" + this.worldName + ") Fire spread is limited to " + this.disableFireSpreadBlocks.size() + " block types.");
            } else {
                this.plugin.getLogger().log(Level.INFO, "(" + this.worldName + ") Fire spread is UNRESTRICTED.");
            }
        }
        this.config.setHeader(CONFIG_HEADER);
        this.config.save();
    }

    public Blacklist getBlacklist() {
        return this.blacklist;
    }

    public String getWorldName() {
        return this.worldName;
    }

    public boolean isChestProtected(Block block, Player player) {
        if (!this.signChestProtection) {
            return false;
        }
        if (this.plugin.hasPermission(player, "worldguard.chest-protection.override") || this.plugin.hasPermission(player, "worldguard.override.chest-protection")) {
            return false;
        }
        return this.chestProtection.isProtected(block, player);
    }

    public boolean isChestProtected(Block block) {
        return this.signChestProtection && this.chestProtection.isProtected(block, null);
    }

    public boolean isChestProtectedPlacement(Block block, Player player) {
        if (!this.signChestProtection) {
            return false;
        }
        if (this.plugin.hasPermission(player, "worldguard.chest-protection.override") || this.plugin.hasPermission(player, "worldguard.override.chest-protection")) {
            return false;
        }
        return this.chestProtection.isProtectedPlacement(block, player);
    }

    public boolean isAdjacentChestProtected(Block block, Player player) {
        if (!this.signChestProtection) {
            return false;
        }
        if (this.plugin.hasPermission(player, "worldguard.chest-protection.override") || this.plugin.hasPermission(player, "worldguard.override.chest-protection")) {
            return false;
        }
        return this.chestProtection.isAdjacentChestProtected(block, player);
    }

    public ChestProtection getChestProtection() {
        return this.chestProtection;
    }

    public int getMaxRegionCount(Player player) {
        int max = -1;
        for (String group : this.plugin.getGroups(player)) {
            int groupMax;
            if (!this.maxRegionCounts.containsKey(group) || max >= (groupMax = this.maxRegionCounts.get(group).intValue())) continue;
            max = groupMax;
        }
        if (max <= -1) {
            max = this.maxRegionCountPerPlayer;
        }
        return max;
    }
}

