/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

import com.avaje.ebean.config.ServerConfig;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.BanList;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.UnsafeValues;
import org.bukkit.Warning;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.map.MapView;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageRecipient;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.CachedServerIcon;

public interface Server
extends PluginMessageRecipient {
    public static final String BROADCAST_CHANNEL_ADMINISTRATIVE = "bukkit.broadcast.admin";
    public static final String BROADCAST_CHANNEL_USERS = "bukkit.broadcast.user";

    public String getName();

    public String getVersion();

    public String getBukkitVersion();

    public Player[] getOnlinePlayers();

    public int getMaxPlayers();

    public int getPort();

    public int getViewDistance();

    public String getIp();

    public String getServerName();

    public String getServerId();

    public String getWorldType();

    public boolean getGenerateStructures();

    public boolean getAllowEnd();

    public boolean getAllowNether();

    public boolean hasWhitelist();

    public void setWhitelist(boolean var1);

    public Set<OfflinePlayer> getWhitelistedPlayers();

    public void reloadWhitelist();

    public int broadcastMessage(String var1);

    public String getUpdateFolder();

    public File getUpdateFolderFile();

    public long getConnectionThrottle();

    public int getTicksPerAnimalSpawns();

    public int getTicksPerMonsterSpawns();

    public Player getPlayer(String var1);

    public Player getPlayerExact(String var1);

    public List<Player> matchPlayer(String var1);

    public PluginManager getPluginManager();

    public BukkitScheduler getScheduler();

    public ServicesManager getServicesManager();

    public List<World> getWorlds();

    public World createWorld(WorldCreator var1);

    public boolean unloadWorld(String var1, boolean var2);

    public boolean unloadWorld(World var1, boolean var2);

    public World getWorld(String var1);

    public World getWorld(UUID var1);

    @Deprecated
    public MapView getMap(short var1);

    public MapView createMap(World var1);

    public void reload();

    public Logger getLogger();

    public PluginCommand getPluginCommand(String var1);

    public void savePlayers();

    public boolean dispatchCommand(CommandSender var1, String var2) throws CommandException;

    public void configureDbConfig(ServerConfig var1);

    public boolean addRecipe(Recipe var1);

    public List<Recipe> getRecipesFor(ItemStack var1);

    public Iterator<Recipe> recipeIterator();

    public void clearRecipes();

    public void resetRecipes();

    public Map<String, String[]> getCommandAliases();

    public int getSpawnRadius();

    public void setSpawnRadius(int var1);

    public boolean getOnlineMode();

    public boolean getAllowFlight();

    public boolean isHardcore();

    public boolean useExactLoginLocation();

    public void shutdown();

    public int broadcast(String var1, String var2);

    public OfflinePlayer getOfflinePlayer(String var1);

    public Set<String> getIPBans();

    public void banIP(String var1);

    public void unbanIP(String var1);

    public Set<OfflinePlayer> getBannedPlayers();

    public BanList getBanList(BanList.Type var1);

    public Set<OfflinePlayer> getOperators();

    public GameMode getDefaultGameMode();

    public void setDefaultGameMode(GameMode var1);

    public ConsoleCommandSender getConsoleSender();

    public File getWorldContainer();

    public OfflinePlayer[] getOfflinePlayers();

    public Messenger getMessenger();

    public HelpMap getHelpMap();

    public Inventory createInventory(InventoryHolder var1, InventoryType var2);

    public Inventory createInventory(InventoryHolder var1, int var2);

    public Inventory createInventory(InventoryHolder var1, int var2, String var3);

    public int getMonsterSpawnLimit();

    public int getAnimalSpawnLimit();

    public int getWaterAnimalSpawnLimit();

    public int getAmbientSpawnLimit();

    public boolean isPrimaryThread();

    public String getMotd();

    public String getShutdownMessage();

    public Warning.WarningState getWarningState();

    public ItemFactory getItemFactory();

    public ScoreboardManager getScoreboardManager();

    public CachedServerIcon getServerIcon();

    public CachedServerIcon loadServerIcon(File var1) throws IllegalArgumentException, Exception;

    public CachedServerIcon loadServerIcon(BufferedImage var1) throws IllegalArgumentException, Exception;

    public void setIdleTimeout(int var1);

    public int getIdleTimeout();

    @Deprecated
    public UnsafeValues getUnsafe();
}

