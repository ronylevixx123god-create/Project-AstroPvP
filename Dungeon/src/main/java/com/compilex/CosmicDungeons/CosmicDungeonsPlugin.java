/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.gui.CosmicGUI
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.cosmicpvp.cosmicutils.utils.JSONUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldGuardUtils
 *  com.cosmicpvp.cosmicutils.utils.WorldUtil
 *  com.decimatemc.decimatecommons.inventory.InventoryManager
 *  com.decimatemc.decimatelootboxes.DecimateLootboxes
 *  com.decimatemc.decimatelootboxes.api.Lootbox
 *  com.decimatemc.decimatelootboxes.api.PluginLootboxManager
 *  com.decimatemc.decimateserversync.DecimateServerSync
 *  com.earth2me.essentials.IEssentials
 *  com.google.common.collect.Lists
 *  com.google.gson.reflect.TypeToken
 *  com.massivecraft.factions.FPlayer
 *  com.massivecraft.factions.FPlayers
 *  fr.neatmonster.nocheatplus.checks.CheckType
 *  fr.neatmonster.nocheatplus.hooks.NCPHook
 *  fr.neatmonster.nocheatplus.hooks.NCPHookManager
 *  me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems
 *  me.ifamasssxd.cosmicskycustoms.struct.CosmicItem
 *  net.milkbowl.vault.economy.Economy
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.RegisteredServiceProvider
 *  org.bukkit.plugin.java.JavaPlugin
 */
package com.compilex.CosmicDungeons;

import com.compilex.CosmicDungeons.cmds.CommandAdminDungeon;
import com.compilex.CosmicDungeons.cmds.CommandDungeon;
import com.compilex.CosmicDungeons.cmds.CommandDungeonGive;
import com.compilex.CosmicDungeons.cmds.CommandDungeonMaster;
import com.compilex.CosmicDungeons.cmds.CommandObjective;
import com.compilex.CosmicDungeons.dungeons.libs.DestroyedOutpostDungeon;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonPlayer;
import com.compilex.CosmicDungeons.dungeons.libs.DungeonType;
import com.compilex.CosmicDungeons.dungeons.libs.PlanetNulDungeon;
import com.compilex.CosmicDungeons.items.BossSlayerTrinketItem;
import com.compilex.CosmicDungeons.items.ChunkBusterItem;
import com.compilex.CosmicDungeons.items.CleanseScrollItem;
import com.compilex.CosmicDungeons.items.HeroicDungeonScrollItem;
import com.compilex.CosmicDungeons.items.PortalProtectionTrinketItem;
import com.compilex.CosmicDungeons.items.PortalTimeReductionTrinketItem;
import com.compilex.CosmicDungeons.listeners.BossSlayerTrinketListener;
import com.compilex.CosmicDungeons.listeners.DungeonChallengeChestListener;
import com.compilex.CosmicDungeons.listeners.DungeonInOutListener;
import com.compilex.CosmicDungeons.listeners.DungeonLootbagListener;
import com.compilex.CosmicDungeons.listeners.DungeonMasterNPCListener;
import com.compilex.CosmicDungeons.listeners.DungeonMenuListener;
import com.compilex.CosmicDungeons.listeners.DungeonPlayerDeathListener;
import com.compilex.CosmicDungeons.listeners.DungeonPlayerMoveListener;
import com.compilex.CosmicDungeons.listeners.DungeonPortalCraftListener;
import com.compilex.CosmicDungeons.listeners.DungeonPortalListener;
import com.compilex.CosmicDungeons.listeners.DungeonSanityListener;
import com.compilex.CosmicDungeons.listeners.PlayerJoinQuitListener;
import com.compilex.CosmicDungeons.menus.DungeonMasterKeyMenu;
import com.compilex.CosmicDungeons.ncphooks.DestroyedOutpostNCPHook;
import com.compilex.CosmicDungeons.struct.PortalLog;
import com.compilex.CosmicDungeons.tasks.HolidayPortalTask;
import com.compilex.CosmicDungeons.utils.EntityHider;
import com.cosmicpvp.cosmicutils.gui.CosmicGUI;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.JSONUtils;
import com.cosmicpvp.cosmicutils.utils.WorldGuardUtils;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.earth2me.essentials.IEssentials;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.hooks.NCPHook;
import fr.neatmonster.nocheatplus.hooks.NCPHookManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import net.astropvp.utils.inventory.InventoryManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class CosmicDungeonsPlugin extends JavaPlugin {
    private DungeonsAPI api;
    private static CosmicDungeonsPlugin _;
    private Economy economy;
    private EntityHider entityHider;
    private boolean disableDungeons = false;
    private boolean lowCostKeys = false;
    private boolean fundEnabled = false;
    private boolean disableCalled = false;
    private List<DungeonType> disabledDungeons = Lists.newArrayList();
    private Map<UUID, String> factionsInsideOnEnable = new HashMap<UUID, String>();
    private String serverName;
    private boolean usePhysicalKey = true;
    private List<DungeonType> availableDungeonKeysTracked = Lists.newArrayList();
    private Map<DungeonType, List<PortalLog>> portalLogs = new HashMap<DungeonType, List<PortalLog>>();
    private Type portalLogToken = new TypeToken<HashMap<DungeonType, List<PortalLog>>>(){}.getType();
    private File portalLogFile;
    public IEssentials ess3;
    private DungeonMenuListener dungeonMenuListener;
    private PlayerJoinQuitListener playerJoinQuitListener;
    private DungeonPortalCraftListener dungeonPortalCraftListener;
    private DungeonPortalListener dungeonPortalListener;
    private DungeonMasterNPCListener dungeonMasterNPCListener;
    private DungeonInOutListener dungeonInOutListener;
    private DungeonPlayerDeathListener dungeonPlayerDeathListener;
    private DungeonLootbagListener dungeonLootbagListener;
    private DungeonPlayerMoveListener dungeonPlayerMoveListener;
    private DungeonSanityListener dungeonSanityListener;
    private InventoryManager inventoryManager;

    public DungeonsAPI getApi() {
        return this.api;
    }

    public EntityHider getEntityHider() {
        return this.entityHider;
    }

    public static CosmicDungeonsPlugin get_() {
        return _;
    }

    public Economy getEconomy() {
        return this.economy;
    }

    public boolean isDisableDungeons() {
        return this.disableDungeons;
    }

    public boolean isLowCostKeys() {
        return this.lowCostKeys;
    }

    public void setDisableDungeons(boolean disableDungeons) {
        this.disableDungeons = disableDungeons;
    }

    public void setLowCostKeys(boolean lowCostKeys) {
        this.lowCostKeys = lowCostKeys;
    }

    public boolean isFundEnabled() {
        return this.fundEnabled;
    }

    public boolean isDisableCalled() {
        return this.disableCalled;
    }

    public List<DungeonType> getDisabledDungeons() {
        return this.disabledDungeons;
    }

    public Map<UUID, String> getFactionsInsideOnEnable() {
        return this.factionsInsideOnEnable;
    }

    public static CosmicDungeonsPlugin get() {
        return _;
    }

    public void setUsePhysicalKey(boolean usePhysicalKey) {
        this.usePhysicalKey = usePhysicalKey;
    }

    public boolean isUsePhysicalKey() {
        return this.usePhysicalKey;
    }

    public Map<DungeonType, List<PortalLog>> getPortalLogs() {
        return this.portalLogs;
    }

    public void onEnable() {
        _ = this;
        this.ess3 = (IEssentials)Bukkit.getPluginManager().getPlugin("Essentials");
        this.entityHider = new EntityHider((Plugin)this, EntityHider.Policy.BLACKLIST);
        this.mkdirs();
        this.api = new DungeonsAPI();
        this.setupEconomy();
        this.loadDungeons();
        this.registerCommands();
        this.registerListeners();
        this.registerTasks();
        WorldGuardUtils.hook();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            this.api.addPlayer(p);
        }
        try {
            Class.forName(WorldUtil.class.getName());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (this.getConfig().getBoolean("dungeons.disabled." + DungeonType.DESTROYED_OUTPOST.name(), true) && this.getConfig().getBoolean("dungeons.disabled." + DungeonType.ABANDONED_SHIP.name(), false)) {
            this.disabledDungeons.add(DungeonType.ABANDONED_SHIP);
        }
        this.fundEnabled = new File("plugins/CosmicFund.jar").exists();
        this.lowCostKeys = this.getConfig().getBoolean("lowCostKeys");
        this.usePhysicalKey = this.getConfig().getBoolean("usePhysicalKeys", true);
        if (this.usePhysicalKey) {
            for (DungeonType type : DungeonType.values()) {
                if (DungeonMasterKeyMenu.getResetString(type) != null) continue;
                this.availableDungeonKeysTracked.add(type);
            }
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDungeonsPlugin.get(), () -> {
            for (FPlayer fplayer : FPlayers.i.get()) {
                try {
                    UUID uuid = UUID.fromString(fplayer.getAccountId());
                    this.factionsInsideOnEnable.put(uuid, fplayer.getFactionId());
                }
                catch (Exception e) {
                    Bukkit.getLogger().info("Unable to get UUID from: " + fplayer.getNameAsync() + " ID: " + fplayer.getAccountId());
                    e.printStackTrace();
                }
            }
            Bukkit.getLogger().info("[CosmicDungeons] Registered " + this.factionsInsideOnEnable.size() + " players inside of factions!");
        }, 100L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)CosmicDungeonsPlugin.get(), () -> {
            for (DungeonType type : DungeonType.values()) {
                if (this.availableDungeonKeysTracked.contains((Object)type) || DungeonMasterKeyMenu.getResetString(type) != null) continue;
                this.availableDungeonKeysTracked.add(type);
                Bukkit.broadcastMessage((String)"");
                Bukkit.broadcastMessage((String)(CC.GoldB + "(!) " + CC.GoldU + type.getDungeonName() + CC.Gold + " Dungeon Keys are now available for purchase from the Dungeon Master!"));
                Bukkit.broadcastMessage((String)"");
            }
        }, 200L, 200L);
        this.inventoryManager = new InventoryManager((JavaPlugin)this);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)CosmicDungeonsPlugin.get(), () -> this.inventoryManager.init(), 20L);
        File orCreateFile = null;
        try {
            orCreateFile = JSONUtils.getOrCreateFile("portalLogs.json");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.portalLogFile = orCreateFile;
        try {
            this.portalLogs = (Map)JSONUtils.fromJson((File)orCreateFile, (Type)this.portalLogToken);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (this.portalLogs == null) {
            this.portalLogs = new HashMap<DungeonType, List<PortalLog>>();
        }
        if (this.portalLogs != null) {
            this.portalLogs.forEach((dType, logs) -> logs.removeIf(log -> System.currentTimeMillis() - log.getPlacetime() >= TimeUnit.DAYS.toMillis(10L)));
        }
        this.getLogger().info("Loaded " + this.portalLogs.size() + " Portal logs keys..");
    }

    public boolean isDevplanet() {
        return this.serverName != null && this.serverName.startsWith("devplanet");
    }

    public void onDisable() {
        this.disableCalled = true;
        for (DungeonPlayer player : this.api.getPlayers().values()) {
            player.save();
            if (!player.isOnline() || player.getCurrentDungeon() == null) continue;
            player.leaveDungeon();
        }
        for (Location l : this.dungeonPortalListener.portalBlocks) {
            l.getBlock().setType(Material.AIR);
        }
        if (this.portalLogs != null) {
            try {
                JSONUtils.saveJSONToFile((File)this.portalLogFile, this.portalLogs, (Object)this.portalLogToken);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    public DungeonMenuListener getDungeonMenuListener() {
        return this.dungeonMenuListener;
    }

    public PlayerJoinQuitListener getPlayerJoinQuitListener() {
        return this.playerJoinQuitListener;
    }

    public DungeonPortalCraftListener getDungeonPortalCraftListener() {
        return this.dungeonPortalCraftListener;
    }

    public DungeonPortalListener getDungeonPortalListener() {
        return this.dungeonPortalListener;
    }

    public DungeonMasterNPCListener getDungeonMasterNPCListener() {
        return this.dungeonMasterNPCListener;
    }

    public DungeonInOutListener getDungeonInOutListener() {
        return this.dungeonInOutListener;
    }

    public DungeonPlayerDeathListener getDungeonPlayerDeathListener() {
        return this.dungeonPlayerDeathListener;
    }

    public DungeonLootbagListener getDungeonLootbagListener() {
        return this.dungeonLootbagListener;
    }

    public DungeonPlayerMoveListener getDungeonPlayerMoveListener() {
        return this.dungeonPlayerMoveListener;
    }

    public DungeonSanityListener getDungeonSanityListener() {
        return this.dungeonSanityListener;
    }

    private void registerListeners() {
        this.dungeonMenuListener = new DungeonMenuListener();
        this.playerJoinQuitListener = new PlayerJoinQuitListener();
        this.dungeonMasterNPCListener = new DungeonMasterNPCListener();
        this.dungeonPortalListener = new DungeonPortalListener();
        this.dungeonPortalCraftListener = new DungeonPortalCraftListener();
        this.dungeonInOutListener = new DungeonInOutListener();
        this.dungeonPlayerDeathListener = new DungeonPlayerDeathListener();
        this.dungeonPlayerMoveListener = new DungeonPlayerMoveListener();
        this.dungeonLootbagListener = new DungeonLootbagListener();
        this.dungeonSanityListener = new DungeonSanityListener();
        this.getServer().getPluginManager().registerEvents((Listener)this.dungeonMenuListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.playerJoinQuitListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.dungeonMasterNPCListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.dungeonPortalCraftListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.dungeonPortalListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.dungeonInOutListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.dungeonPlayerDeathListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.dungeonLootbagListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.dungeonPlayerMoveListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)this.dungeonSanityListener, (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new DungeonChallengeChestListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BossSlayerTrinketListener(), (Plugin)this);
        try {
            if (Bukkit.getPluginManager().isPluginEnabled("NoCheatPlus")) {
                NCPHookManager.addHook((CheckType)CheckType.MOVING_SURVIVALFLY, (NCPHook)new DestroyedOutpostNCPHook());
                this.getLogger().info("Registered NoCheatPlus Hook for DestroyedOutpost dungeon!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (Bukkit.getPluginManager().isPluginEnabled("CosmicSkyCustomItems")) {
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)new PortalProtectionTrinketItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)new PortalTimeReductionTrinketItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)new BossSlayerTrinketItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)new HeroicDungeonScrollItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)new ChunkBusterItem());
            CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)new CleanseScrollItem());
        }
    }

    private void registerCommands() {
        this.getCommand("dungeon").setExecutor((CommandExecutor)new CommandDungeon());
        this.getCommand("dungeonmaster").setExecutor((CommandExecutor)new CommandDungeonMaster());
        this.getCommand("adungeon").setExecutor((CommandExecutor)new CommandAdminDungeon());
        this.getCommand("objective").setExecutor((CommandExecutor)new CommandObjective());
        this.getCommand("dungeongive").setExecutor((CommandExecutor)new CommandDungeonGive());
    }

    private void registerTasks() {
        if (this.getConfig().getBoolean("holiday.dailyPortals")) {
            Bukkit.getScheduler().runTaskTimer((Plugin)this, (Runnable)new HolidayPortalTask(), 1200L, 1200L);
        }
        Bukkit.getScheduler().runTaskTimer((Plugin)this, () -> CosmicGUI.activeGUIs.values(), 20L, 20L);
    }

    private void loadDungeons() {
        CosmicDungeonsPlugin.get_().getApi().addDungeon(new Dungeon(Bukkit.getWorld((String)"dungeon_yijki_a"), DungeonType.ABANDONED_SHIP, this.getConfig().getString("dungeons.dungeon_yijki_a.name"), new LinkedList<String>(this.getConfig().getStringList("dungeons.dungeon_yijki_a.description"))));
        CosmicDungeonsPlugin.get_().getApi().addDungeon(new DestroyedOutpostDungeon(Bukkit.getWorld((String)DungeonType.DESTROYED_OUTPOST.getInternalName()), "&4&l&nDestroyed Outpost", new LinkedList<String>(Lists.newArrayList(new String[]{"", "&4&lLORE", CC.Gray + "After investigating the source of the", CC.Gray + "distress signal sent from the Abandoned Spaceship", CC.Gray + "and parsing over the recovered Cosmonaut Ship", CC.Gray + "master log files, a chilling series of events", CC.Gray + "has been uncovered.", "", CC.Gray + "With the teleportation matrix solved, the", CC.Gray + "Mother of Yijki\u2019s source location has been traced.", CC.Gray + "There is a rift into its dimension at the", CC.Gray + "core of this destroyed mining outpost.", "", CC.Gray + "You have been chosen to lead the assault", CC.Gray + "onto the hostile planet to rid the Cosmoverse", CC.Gray + "of this demonic entity of destruction."}))));
        CosmicDungeonsPlugin.get_().getApi().addDungeon(new PlanetNulDungeon(Bukkit.getWorld((String)DungeonType.PLANET_NUL.getInternalName()), "&8&l&nPlanet Nul", new LinkedList<String>(Lists.newArrayList(new String[]{"", "&8&lLORE", CC.Gray + "Located on the edge of the Cosmoverse,", CC.Gray + "Planet Nul is a mysterious and dark planet", CC.Gray + "that, until recently, held no significance.", CC.Gray + "", CC.Gray + "However, new intel suggests one of the", CC.Gray + "Most infamous criminals in the universe,", CC.Gray + "known only as - " + CC.DarkGray + "The Corrupt Cosmonaut,", CC.Gray + "is hiding deep in an abandoned military bunker", CC.Gray + "that can be accessed from the planet's surface.", CC.Gray + "", CC.Gray + "An elite team has been assembled, and you have", CC.Gray + "been chosen, to launch a tactical assault on", CC.Gray + "the military bunker on Planet Nul with one", CC.Gray + "simple objective: " + CC.DarkGray + "Eliminate the Corrupt Cosmonaut."}))));
    }

    private void mkdirs() {
        new File("plugins/CosmicDungeons/playerData").mkdirs();
    }

    public void logPortalPlace(DungeonType type, Player player, Block block) {
        List<PortalLog> logs = this.portalLogs.computeIfAbsent(type, e -> Lists.newLinkedList());
        logs.add(new PortalLog(player.getUniqueId(), player.getWorld().getName(), block.getX(), block.getY(), block.getZ(), System.currentTimeMillis()));
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            this.economy = (Economy)economyProvider.getProvider();
        }
        return this.economy != null;
    }
}

