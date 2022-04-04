/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.earth2me.essentials.Essentials
 *  com.massivecraft.factions.Faction
 *  com.massivecraft.factions.Factions
 *  com.massivecraft.factions.zcore.util.WorldUtil
 *  com.sk89q.worldedit.BlockVector
 *  com.sk89q.worldguard.protection.regions.ProtectedRegion
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.World
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.compilex.CosmicKOTH;

import com.compilex.CosmicKOTH.cmds.CommandAdminKoth;
import com.compilex.CosmicKOTH.cmds.CommandKoth;
import com.compilex.CosmicKOTH.customfiles.CustomConfig;
import com.compilex.CosmicKOTH.listeners.KothInventoryListener;
import com.compilex.CosmicKOTH.listeners.KothLootBagListener;
import com.compilex.CosmicKOTH.listeners.KothPortalListener;
import com.compilex.CosmicKOTH.listeners.KothSanityListener;
import com.compilex.CosmicKOTH.struct.KothWorldType;
import com.compilex.CosmicKOTH.tasks.KothEventTask;
import com.compilex.CosmicKOTH.tasks.LauncherTask;
import com.compilex.CosmicKOTH.utils.LocationUtils;
import com.compilex.CosmicKOTH.utils.RewardUtils;
import com.compilex.CosmicKOTH.utils.WorldGuardUtils;
import com.compilex.CosmicOutposts.cmds.CommandAoutposts;
import com.compilex.CosmicOutposts.cmds.CommandOutposts;
import com.compilex.CosmicOutposts.listeners.OutpostRewardsListener;
import com.compilex.CosmicOutposts.listeners.OutpostSanityListener;
import com.compilex.CosmicOutposts.listeners.OutpostWarpMenuListener;
import com.compilex.CosmicOutposts.outpost.Outpost;
import com.compilex.CosmicOutposts.outpost.enums.OutpostTier;
import com.compilex.CosmicOutposts.outpost.managers.DataManager;
import com.compilex.CosmicOutposts.utils.MonsterUtils;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.earth2me.essentials.Essentials;
import com.massivecraft.factions.Factions;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import lombok.Setter;
import me.ifamasssxd.cosmichcf.commands.CommandOutpost;
import me.ifamasssxd.cosmichcf.listeners.OutpostPerkListener;
import me.ifamasssxd.cosmichcf.listeners.OutpostWorldListener;
import me.ifamasssxd.cosmichcf.managers.OutpostManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class CosmicKOTHPlugin
        extends JavaPlugin {

    @Getter
    private static CosmicKOTHPlugin _;
    @Getter
    private World world;
    @Getter
    @Setter
    private Location spawn;
    @Getter
    @Setter
    private Location kothCenter;
    @Getter
    @Setter
    private KothEventTask koth;
    @Getter
    private Essentials ess3;
    @Getter
    @Setter
    private static boolean isVanillaGalaxy;
    @Getter
    @Setter
    private KothWorldType kothWorldType;
    @Getter
    @Setter
    private Outpost traineeOutpost;
    @Getter
    @Setter
    private Outpost heroOutpost;
    @Getter
    @Setter
    private Outpost cosmonautOutpost;
    @Getter
    @Setter
    private Outpost vanillaOutpost;
    @Getter
    @Setter
    private final List<Outpost> outposts = new ArrayList<Outpost>();
    @Getter
    @Setter
    private Economy economy;
    @Getter
    @Setter
    private DataManager dataManager;
    @Getter
    @Setter
    private File kothBuffsFile;
    @Getter
    @Setter
    private String buffFactionId = "";
    @Getter
    @Setter
    private YamlConfiguration kothBuffsConfig;
    @Getter
    @Setter
    private OutpostManager outpostManager;
    @Getter
    @Setter
    private boolean trophiesEnabled = false;
    @Getter
    @Setter
    private boolean vanillaPlanet = false;
    @Getter
    @Setter
    private boolean cosmicGalaxyPlanet = false;
    @Getter
    @Setter
    private boolean hcfPlanet = false;
    @Getter
    @Setter
    private String baseName = "Outpost";
    @Getter
    @Setter
    private String serverType = "hcf";
    @Getter
    @Setter
    private Map<String, String> cachedMessages = new HashMap<String, String>();
    @Getter
    private Map<String, Integer> kothWins;


    public void onEnable() {
        _ = this;
        WorldGuardUtils.hook();
        this.ess3 = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        isVanillaGalaxy = !new File("plugins/Enchantments.jar").exists();
        this.kothWorldType = KothWorldType.getFromIndex(RewardUtils.getMapIndex());
        this.kothBuffsConfig = new YamlConfiguration();
        this.kothBuffsFile = new File(this.getDataFolder(), "koth_buffs.yml");
        if (!this.kothBuffsFile.exists()) {
            this.kothBuffsFile.getParentFile().mkdirs();
            this.saveResource("koth_buffs.yml", false);
        }
        try {
            this.kothBuffsConfig.load(this.kothBuffsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        this.buffFactionId = this.kothBuffsConfig.getString("factionId");
        this.fixLog();
        this.loadConfig();
        this.registerCommands();
        this.registerListeners();
        this.registerTasks();
        this.saveDefaultConfig();

        this.setupEconomy();

        com.compilex.CosmicOutposts.utils.WorldGuardUtils.hook();
        this.dataManager = new DataManager();
        MonsterUtils.populateLootTables();
        this.loadOutpostConfig();
        this.registerCommands();
        this.registerListeners();

        Bukkit.getPluginManager().registerEvents(new OutpostWarpMenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new OutpostRewardsListener(), this);
        Bukkit.getPluginManager().registerEvents(new OutpostSanityListener(), this);
        Bukkit.getPluginManager().registerEvents(new KothPortalListener(), this);

        this.getCommand("outposts").setExecutor(new CommandOutposts());
        this.getCommand("aoutposts").setExecutor(new CommandAoutposts());

        this.serverType = this.getConfig().getString("type", "hcf");
        this.vanillaPlanet = this.serverType.equalsIgnoreCase("vanilla");
        this.cosmicGalaxyPlanet = this.serverType.equalsIgnoreCase("cosmic");
        this.hcfPlanet = this.serverType.equalsIgnoreCase("hcf");
        this.baseName = this.getConfig().getString("outpost-name", "Stronghold");
        this.outpostManager = new OutpostManager();
        this.outpostManager.loadOutposts(this);
        this.trophiesEnabled = new File("plugins/CosmicHCFTrophies.jar").exists();
        Bukkit.getScheduler().runTaskTimer((Plugin) CosmicKOTHPlugin.get_(), (Runnable) new LauncherTask(14600), 20L, 20L);



        CustomConfig.setup();
        CustomConfig.save();
        kothWins = CustomConfig.load();
    }

    public void onDisable() {
        this.kothBuffsConfig.set("factionId", this.getBuffFactionId());
        try {
            this.kothBuffsConfig.save(this.kothBuffsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (CosmicKOTHPlugin.get_().getKoth() != null) {
            Player[] arrplayer = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
            int n = arrplayer.length;
            for (Player value : arrplayer) {
                Player player;
                Player p = player = value;
                player.removeMetadata("KOTH_CAPPING_START", CosmicKOTHPlugin.get_());
            }
            if (!CosmicKOTHPlugin.get_().getKoth().isCapped()) {
                this.getConfig().set("relaunch", true);
                this.saveConfig();
            }

            CustomConfig.setup();
            CustomConfig.save();
        }

        for (Outpost outpost : this.outposts) {
            if (outpost.getControllingFaction() != null) {
                this.getConfig().set("mainoutposts." + outpost.getOutpostTier().name().toLowerCase() + ".controllingFaction", outpost.getControllingFaction());
                this.getConfig().set("mainoutposts." + outpost.getOutpostTier().name().toLowerCase() + ".capTime", outpost.getCapTime());
                continue;
            }
            this.getConfig().set("mainoutposts." + outpost.getOutpostTier().name().toLowerCase() + ".controllingFaction", "");
        }
        this.getDataManager().saveStoredTimes();
        this.saveConfig();
        if (this.outpostManager != null) {
            this.outpostManager.onDisable(this);
        }
    }

    private void fixLog() {
        String fileName = "plugins/CosmicKOTH/koth.log";
        String line = null;
        ArrayList<String> newLines = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader("plugins/CosmicKOTH/koth.log");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.contains("[")) continue;
                String[] s = line.split("\\[");
                if (s.length > 1) {
                    for (String l : s) {
                        if (l.length() <= 1) continue;
                        newLines.add("[" + l);
                    }
                    continue;
                }
                newLines.add(line);
            }
            bufferedReader.close();
            new File("plugins/CosmicKOTH/koth.log").delete();
            new File("plugins/CosmicKOTH/koth.log").createNewFile();
            for (String entry : newLines) {
                Files.write(Paths.get("plugins/CosmicKOTH/koth.log"), (entry + "\n").getBytes(), StandardOpenOption.APPEND);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("[CosmicKOTH (fixLogs)] Unable to open file 'plugins/CosmicKOTH/koth.log'");
        } catch (IOException ex2) {
            System.out.println("[CosmicKOTH (fixLogs)] Error reading file 'plugins/CosmicKOTH/koth.log'");
        }
    }

    private void loadConfig() {
        this.registerConfigData();
        if (_.getConfig().getBoolean("relaunch")) {
            _.getConfig().set("relaunch", false);
            _.saveConfig();
            Bukkit.getLogger().info("[CosmicKOTH] Server rebooted with an active KOTH, launching a new one.");
        }
        this.registerCenter();
        Bukkit.getScheduler().runTaskLater(CosmicKOTHPlugin.get_(), new Runnable() {

            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv remove world_koth");
                Bukkit.getScheduler().runTaskLater(CosmicKOTHPlugin.get_(), new Runnable() {

                    @Override
                    public void run() {
                        if (CosmicKOTHPlugin.get_().getKothWorldType() == KothWorldType.HALLOWEEN_NIGHTMARE) {
                            Bukkit.getLogger().info("[CosmicKOTH] Updating spawn and WorldBorder variables for world_koth due to worldType = HALLOWEEN_NIGHTMARE...");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv import world_koth NETHER -g VoidGenerator");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb world_koth setcorners 128 142 -135 -143");
                            CosmicKOTHPlugin.this.registerConfigData();
                            CosmicKOTHPlugin.this.registerCenter();
                            CosmicKOTHPlugin.this.spawn = LocationUtils.convertStringToLocation("world_koth,-74.5484,44,95.4336:178.1975$66.75");
                        } else if (CosmicKOTHPlugin.get_().getKothWorldType() == KothWorldType.SANTA_WORKSHOP) {
                            Bukkit.getLogger().info("[CosmicKOTH] Updating playerSpawn variable for world_koth due to worldType = SANTA_WORKSHOP ...");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv import world_koth NORMAL -g VoidGenerator");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb world_koth setcorners -1 -291 -262 -9");
                            CosmicKOTHPlugin.this.registerConfigData();
                            CosmicKOTHPlugin.this.registerCenter();
                            CosmicKOTHPlugin.this.spawn = LocationUtils.convertStringToLocation("world_koth,-177.6923,80.3734,-265.3361:1.2001$12.45");
                        } else {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mv import world_koth NORMAL -g VoidGenerator");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "wb world_koth set 10000 10000 -129.0 -145.5");
                            CosmicKOTHPlugin.this.registerConfigData();
                            CosmicKOTHPlugin.this.registerCenter();
                        }
                        //WorldUtil.changeClaimsInRegionWithFaction(Bukkit.getConsoleSender(), "koth_arena", CosmicKOTHPlugin.this.world, Factions.i.getWarZone(), true);
                    }
                }, 20L);
            }
        }, 10L);
    }


    private void loadOutpostConfig() {
        this.initOutposts();
        for (Outpost outpost : this.outposts) {
            outpost.setWorld(Bukkit.getWorld(this.getConfig().getString("mainoutposts." + outpost.getOutpostTier().name().toLowerCase() + ".world")));
            outpost.setAttackerSpawn(com.compilex.CosmicOutposts.utils.LocationUtils.convertStringToLocation(this.getConfig().getString("mainoutposts." + outpost.getOutpostTier().name().toLowerCase() + ".attackerSpawn")));
            outpost.setDefenderSpawn(com.compilex.CosmicOutposts.utils.LocationUtils.convertStringToLocation(this.getConfig().getString("mainoutposts." + outpost.getOutpostTier().name().toLowerCase() + ".defenderSpawn")));
            outpost.setControllingFaction(this.getConfig().getString("mainoutposts." + outpost.getOutpostTier().name().toLowerCase() + ".controllingFaction").length() > 0 ? this.getConfig().getString("mainoutposts." + outpost.getOutpostTier().name().toLowerCase() + ".controllingFaction") : null);
            if (outpost.getControllingFaction() != null) {
                outpost.getCapTask().setCapPercent(1.0f);
                outpost.getCapTask().setDefendingFaction(Factions.getInstance().getByTag(outpost.getControllingFaction()));
                outpost.setCapTime(this.getConfig().getLong("mainoutposts." + outpost.getOutpostTier().name().toLowerCase() + ".capTime"));
            }
            Bukkit.getLogger().info("[CosmicOutposts] LOADING OUTPOST " + outpost.getOutpostTier().name() + ":");
            Bukkit.getLogger().info(">> attackerSpawn: " + outpost.getAttackerSpawn());
            Bukkit.getLogger().info(">> defenderSpawn: " + outpost.getDefenderSpawn());
            Bukkit.getLogger().info(">> world: " + outpost.getWorld().getName());
            Bukkit.getLogger().info(">> controllingFaction: " + outpost.getControllingFaction());
            if (outpost.getWorld() != null) {
                outpost.startTasks();
                continue;
            }
            Bukkit.getLogger().info(">> skipping startTasks() due to null world.");
        }
    }

    private void initOutposts() {
        this.traineeOutpost = new Outpost(this, OutpostTier.TRAINEE);
        this.heroOutpost = new Outpost(this, OutpostTier.HERO);
        this.cosmonautOutpost = new Outpost(this, OutpostTier.COSMONAUT);
        this.vanillaOutpost = new Outpost(this, OutpostTier.VANILLA);
        this.outposts.addAll(Arrays.asList(this.traineeOutpost, this.heroOutpost, this.cosmonautOutpost, this.vanillaOutpost));
    }

    public void registerConfigData() {
        this.world = Bukkit.getWorld(CosmicKOTHPlugin.get_().getConfig().getString("world"));
        if (_.getConfig().getString("playerSpawn") != null) {
            this.spawn = LocationUtils.convertStringToLocation(_.getConfig().getString("playerSpawn"));
        }
        if (_.getConfig().getString("kothCenter") != null) {
            this.kothCenter = LocationUtils.convertStringToLocation(_.getConfig().getString("kothCenter"));
        }
    }

    public void registerCenter() {
        ProtectedRegion region = WorldGuardUtils.getRegionFromName("world", "koth_center");
        if (region != null) {
            BlockVector maxVector = region.getMaximumPoint();
            BlockVector minVector = region.getMinimumPoint();
            double widthX = maxVector.getX() - minVector.getX();
            double widthZ = maxVector.getZ() - minVector.getZ();
            double height = maxVector.getY() - minVector.getY();
            double middleX = minVector.getX() + widthX / 2.0;
            double middleZ = minVector.getZ() + widthZ / 2.0;
            double middleY = minVector.getY() + height / 2.0;
            this.kothCenter = new Location(this.world, middleX, middleY, middleZ);
            Bukkit.getLogger().info(String.format("[CosmicKOTH] found valid kothCenter at %sx, %sy, %sz, minVector: " + minVector + " maxVector: " + maxVector, "" + middleX, "" + middleY, "" + middleZ));
        } else {
            Bukkit.getLogger().info("unable to find koth_center region inside world");
        }
    }

    private void registerCommands() {
        this.getCommand("koth").setExecutor(new CommandKoth());
        this.getCommand("akoth").setExecutor(new CommandAdminKoth());
        this.getCommand("stronghold").setExecutor((CommandExecutor) new CommandOutpost());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new KothSanityListener(), this);
        Bukkit.getPluginManager().registerEvents(new KothLootBagListener(), this);
        Bukkit.getPluginManager().registerEvents(new KothInventoryListener(), this);

        this.getServer().getPluginManager().registerEvents((Listener) new OutpostWorldListener(), (Plugin) this);

            this.getServer().getPluginManager().registerEvents((Listener) new OutpostPerkListener(), (Plugin) this);

    }

    private void registerTasks() {
        //Bukkit.getScheduler().runTaskTimerAsynchronously(this, new DateWatcherTask(), 1200L, 600L);
    }

    public String getMessage(String msg, String... args) {
        String message = this.cachedMessages.get(msg);
        if (message != null) {
            return String.format(message, args);
        }
        message = this.getConfig().getString("messages." + msg);
        if (message == null) {
            Bukkit.getLogger().info("Cant find message to " + msg + "!");
            return "";
        }
        message = ChatColor.translateAlternateColorCodes((char) '&', (String) message);
        this.cachedMessages.put(msg, message);
        return String.format(message, args);
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            this.economy = (Economy) economyProvider.getProvider();
        }
        return this.economy != null;
    }
}
