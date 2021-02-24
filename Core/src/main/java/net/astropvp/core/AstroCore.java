package net.astropvp.core;

import com.cosmicpvp.titles.cmds.CommandTitle;
import com.cosmicpvp.titles.cmds.CommandTitleSet;
import com.cosmicpvp.titles.listeners.ChatListener;
import com.cosmicpvp.titles.listeners.PermissionListener;
import com.cosmicpvp.titles.listeners.PlayerJoinQuitListener;
import com.cosmicpvp.titles.listeners.TitleInventoryListener;
import com.cosmicpvp.titles.sql.SQLConnection;
import com.cosmicpvp.titles.sql.SQLHandler;
import com.cosmicpvp.titles.sql.TitleDatabaseManager;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.I18n;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.astropvp.commands.CommandGiveBackpack;
import me.astropvp.listeners.BackpackListener;
import me.astropvp.listeners.PlayerDamageListener;
import me.astropvp.listeners.PortablePicnicListener;
import me.astropvp.tasks.PortablePicnicTask;
import me.ifamasssxd.betterpay.PayListener;
import me.ifamasssxd.betterpay.PayToggle;
import me.ifamasssxd.betterpay.commands.CommandTogglePay;
import me.ifamasssxd.corechunkfinder.CoreChunkFinderCommand;
import me.ifamasssxd.cosmiccoinflip.commands.CommandCoinFlip;
import me.ifamasssxd.cosmiccoinflip.listeners.InventoryListener;
import me.ifamasssxd.cosmiccoinflip.struct.CoinFlipManager;
import me.ifamasssxd.cosmiccoinflip.struct.CoinFlipMatch;
import me.ifamasssxd.cosmicheroicenvoys.commands.CommandSetHeroicBossSpawn;
import me.ifamasssxd.cosmicheroicenvoys.commands.CommandSpawnHeroicBoss;
import me.ifamasssxd.cosmicheroicenvoys.listeners.EnvoyListener;
import me.ifamasssxd.cosmicheroicenvoys.managers.EnvoyManager;
import me.ifamasssxd.cosmicjackpot.commands.CommandJackpot;
import me.ifamasssxd.cosmicjackpot.listeners.JackpotListener;
import me.ifamasssxd.cosmicjackpot.struct.JackpotManager;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ifamasssxd.cosmictoggles.commands.CommandScoreboard;
import me.ifamasssxd.cosmictoggles.commands.CommandToggles;
import me.ifamasssxd.cosmictoggles.manager.TogglesManager;
import me.ifamasssxd.help.commands.CommandHelp;
import me.ifamasssxd.help.commands.CommandHelpEnchants;
import me.ifamasssxd.help.commands.CommandSpawnMenu;
import me.ifamasssxd.help.commands.CommandWiki;
import me.ifamasssxd.help.listeners.CommandListener;
import me.ifamasssxd.itemfilter.FilterManager;
import me.ifamasssxd.itemfilter.commands.CommandFilter;
import me.ifamasssxd.itemfilter.listeners.ItemFilterListener;
import me.ifamasssxd.killtracker.commands.CommandGiveSoulGem;
import me.ifamasssxd.killtracker.commands.CommandGiveTracker;
import me.ifamasssxd.killtracker.commands.CommandWithdrawSouls;
import me.ifamasssxd.killtracker.listeners.ItemListener;
import me.ifamasssxd.killtracker.listeners.PlayerListener;
import me.ifamasssxd.killtracker.listeners.SoulGemListener;
import me.matthawkk.autobroadcast.tasks.AutoBroadcastTask;
import me.matthawkk.autogiveall.struct.GiveAllManager;
import me.matthawkk.autogiveall.tasks.AutoGiveAllTask;
import net.astropvp.core.commands.*;
import net.astropvp.core.conaxgames.giveall.ManualGiveAllCommand;
import net.astropvp.core.items.ApolloRank;
import net.astropvp.core.items.AstroPlusRank;
import net.astropvp.core.items.HeroicUpgrade;
import net.astropvp.core.listeners.*;
import net.astropvp.core.conaxgames.staff.Freeze;
import net.astropvp.core.conaxgames.staff.FreezeCommand;
import net.astropvp.core.conaxgames.staff.StaffChatCommand;
import net.astropvp.core.conaxgames.staff.StaffListeners;
import net.astropvp.core.tasks.LastManStandingCapTask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

@Getter
@Setter
public class AstroCore extends JavaPlugin {

    private static AstroCore instance;
    private static Economy econ = null;
    private static Field bukkitCommand;
    private final String consolePrefix = "[AstroCore]";
    public static AstroCore getInstance() {
        return instance;
    }

    // Sponges
    private CommandHelp commandHelp;
    public static String LORE_SUFFIX = ":  ";
    private File spongeDirectory;
    // Jackpot
    private JackpotManager jackpotManager;
    public static Economy economy;
    // BetterPay
    Essentials ess;
    private PayToggle payToggles;
    // ItemFilter
    @Getter private FilterManager filterManager;
    @Getter private ItemFilterListener itemListener;
    // PlayerTitles
    @Getter public TitleDatabaseManager dataManager;
    @Getter public static boolean hcf;
    // CoreChunkFinder
    @Getter private boolean tntDebug = false;
    // Heroic Envoys
    @Getter public EnvoyManager envoyManager;
    // Coin Flips
    @Getter private CoinFlipManager coinFlipManager;
    // Cosmic Toggles
    @Getter private TogglesManager toggleManager = new TogglesManager();

    @Getter private GiveAllManager giveAllManager;

    @Getter @Setter public static TitleManagerAPI titleManagerAPI;

    private Freeze freezeCommand;


    @SneakyThrows
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        saveConfig();
        setupEconomy();
        events();
        new LastManStandingCapTask().runTaskTimer(this, 20L, 20L);

        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new HeroicUpgrade());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new ApolloRank());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new AstroPlusRank());
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new AutoBroadcastTask(), 20L, 6000L);

        Bukkit.getScheduler().runTaskLater(this, ()->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ic reload"), 100L);
        Bukkit.getScheduler().runTaskLater(this, ()->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "arkload vault_gui"), 100L);
        Bukkit.getScheduler().runTaskLater(this, ()->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "arkload death_messages"), 100L);

        if (!Bukkit.getPluginManager().isPluginEnabled("TitleManager")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Astro] " + ChatColor.RED + "TitleManager is not loaded.");
        } else {
            titleManagerAPI = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");
        }


        this.commandHelp = new CommandHelp();
        if (commandHelp.isEnchantsEnabled()) {
            this.getCommand("helpenchants").setExecutor(new CommandHelpEnchants());
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "arkunload lava_sponge");
        WorldGuardPlugin.inst().getConfig().set("simulation.sponge.enable", false);
        for (World w : Bukkit.getWorlds()) {
            WorldGuardPlugin.inst().getGlobalStateManager().get(w).simulateSponge = false;
        }


        this.ess = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
        this.payToggles = new PayToggle("toggles.yml");

        this.jackpotManager = new JackpotManager();
        try {
            this.jackpotManager.loadFileData(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.getServer().getPluginManager().registerEvents(new JackpotListener(), this);
        this.getServer().getPluginManager().registerEvents(new GrindingMessagesToggleListener(), this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            try {
                this.jackpotManager.onJackpotTick();
            } catch (UserDoesNotExistException e) {
                e.printStackTrace();
            } catch (NoLoanPermittedException e) {
                e.printStackTrace();
            }
        }, 20L, 20L);

        this.filterManager = new FilterManager();
        this.filterManager.loadFilters();
        this.itemListener = new ItemFilterListener();
        this.getServer().getPluginManager().registerEvents(this.itemListener, this);

        this.setupSQL();
        this.dataManager = new TitleDatabaseManager();
        this.dataManager.cacheAllEquippedTitles();
        hcf = new File("plugins/HCFactions.jar").exists();

        //this.envoyManager = new EnvoyManager();
        //this.envoyManager.startTimer();
        this.getCommand("spawnheroicboss").setExecutor(new CommandSpawnHeroicBoss());
        this.getCommand("setheroicbossspawn").setExecutor(new CommandSetHeroicBossSpawn());

        this.getCommand("armorcolor").setExecutor(new ArmorColorCommand());

        this.getCommand("corechunkfinder").setExecutor(new CoreChunkFinderCommand());

        this.getCommand("coinflip").setExecutor(new CommandCoinFlip());

        this.getCommand("list").setExecutor(new ListCommand());
        this.getCommand("grinding").setExecutor((CommandExecutor)new ToggleGrindingMessages());
        this.getCommand("jackpot").setExecutor(new CommandJackpot());
        this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        this.getServer().getPluginManager().registerEvents(new me.ifamasssxd.cosmiccoinflip.listeners.PlayerListener(), this);

        this.coinFlipManager = new CoinFlipManager();
        try {
            this.coinFlipManager.loadCoinFlips();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.coinFlipManager.loadToggles();
        if (!this.getConfig().getBoolean("resetStats")) {
            this.getConfig().set("resetStats", true);
            this.saveConfig();
            try {
                this.coinFlipManager.clearStats();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new AutoGiveAllTask(), 20L, 20L);

        this.toggleManager.loadToggles();

        this.getServer().getPluginManager().registerEvents(new Freeze(), this);
        this.getServer().getPluginManager().registerEvents(new ArmorColorClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockCommand(), this);
        this.getServer().getPluginManager().registerEvents(new BackpackListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new PortablePicnicListener(), this);
        Bukkit.getScheduler().runTaskTimer(this, new PortablePicnicTask(), 0L, 200L);
        this.getCommand("givebackpack").setExecutor(new CommandGiveBackpack());

        this.getCommand("togglesb").setExecutor(new CommandScoreboard());
        this.getCommand("toggles").setExecutor(new CommandToggles());
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        this.getCommand("filter").setExecutor(new CommandFilter());
        this.getCommand("wiki").setExecutor(new CommandWiki());
        this.getCommand("spawnmenu").setExecutor(new CommandSpawnMenu());
        this.getCommand("withdrawsouls").setExecutor(new CommandWithdrawSouls());
        this.getCommand("givetracker").setExecutor(new CommandGiveTracker());
        this.getCommand("givesoulgem").setExecutor(new CommandGiveSoulGem());
        this.getCommand("title").setExecutor(new CommandTitle());
        this.getCommand("titleset").setExecutor(new CommandTitleSet());
        this.getCommand("claim").setExecutor(new me.matthawkk.autogiveall.commands.CommandClaim());
        this.getCommand("forcegiveall").setExecutor(new me.matthawkk.autogiveall.commands.CommandForceGiveAll());
        this.getCommand("nextgiveall").setExecutor(new me.matthawkk.autogiveall.commands.CommandNextGiveAll());

        this.getCommand("giveenchantmentcrystal").setExecutor(new GiveEnchantmentCrystalCommand());

        this.getCommand("bless").setExecutor(new Bless());
        this.getCommand("dupe").setExecutor(new CommandDupe());
        this.getCommand("heal").setExecutor(new HealCommand());
        this.getCommand("discord").setExecutor(new DiscordCommand());
        this.getCommand("pots").setExecutor(new PotionsCommand(this));

        this.getCommand("invsee").setExecutor(new Invsee());
        this.getCommand("givechunkbuster").setExecutor(new ChunkBuster(this));
        this.getCommand("trash").setExecutor(new TrashCommand());
        this.getCommand("lastmanstanding").setExecutor(new LastManStandingCommand());
        this.getCommand("togglepay").setExecutor(new CommandTogglePay());

        Arrays.asList(new Broadcast(), new FreezeCommand(), new StaffChatCommand(), new ManualGiveAllCommand()).forEach(this:: registerCommand);

        this.getServer().getPluginManager().registerEvents(new StaffListeners(), this);
        this.getServer().getPluginManager().registerEvents(new CommandListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemListener(), this);
        this.getServer().getPluginManager().registerEvents(new SoulGemListener(), this);

        this.getServer().getPluginManager().registerEvents(new ApplyEnchantCrystalListener(), this);

        Bukkit.getServer().getPluginManager().registerEvents(new TitleInventoryListener(this.dataManager), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(this.dataManager), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PermissionListener(this.dataManager), this);

        this.getServer().getPluginManager().registerEvents(new EnvoyListener(), this);

        freezeCommand = new Freeze();

        Bukkit.getConsoleSender().sendMessage(" ******************************************** ");
        Bukkit.getConsoleSender().sendMessage(" ASTRO CORE DEBUG: PLUGIN SHOULD BE LOADED NOW");
        Bukkit.getConsoleSender().sendMessage(" ******************************************** ");
    }

    @SneakyThrows
    public void onDisable() {
        this.payToggles.saveToggles();
        try {
            this.jackpotManager.saveFileData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dataManager.saveAllEquippedTitlesToSQL();
        this.filterManager.saveFilters();

        /*Boss bos;
        if (this.envoyManager.getBossUUID() != null && this.envoyManager.getBossLocation() != null && (bos = Boss.bosses.get(this.envoyManager.getBossUUID())) != null) {
            bos.onDeath(null);
            bos.getEntity().remove();
            Boss.bosses.remove(bos.getEntity().getUniqueId());
            System.out.println("Removing boss!");
        }*/

        for (CoinFlipMatch active : this.coinFlipManager.getCoinFlipMatches()) {
            if (active.getMatchState() == CoinFlipMatch.MatchState.WAITING) {
                if (active.getStarterPlayer() == null) continue;
                economy.depositPlayer(active.getStarterPlayer(), active.getWager());
                Bukkit.getLogger().info("Depositing $" + active.getWager() + " to " + active.getStarterPlayer().getName() + " due to reload...");
                continue;
            }
            if (active.getMatchState() == CoinFlipMatch.MatchState.DONE) continue;
            if (active.getStarterPlayer() != null) {
                economy.depositPlayer(active.getStarterPlayer(), active.getWager());
                Bukkit.getLogger().info("Depositing $" + active.getWager() + " to " + active.getStarterPlayer().getName() + " due to reload...");
            }
            if (active.getSecondPlayer() == null) continue;
            economy.depositPlayer(active.getSecondPlayer(), active.getWager());
            Bukkit.getLogger().info("Depositing $" + active.getWager() + " to " + active.getSecondPlayer().getName() + " due to reload...");
        }
        this.coinFlipManager.getCoinFlipMatches().clear();
        try {
            this.coinFlipManager.saveCoinFlipRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.coinFlipManager.saveToggles();

        this.toggleManager.saveToggles();

        instance = null;
    }

    public void events() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new DungeonInventoryEvent(), this);
        pm.registerEvents(new AutoOP(), this);
        pm.registerEvents(new ServerFixes(), this);
        pm.registerEvents(new MOTD(), this);
        pm.registerEvents(new HeroicArmorFix(), this);
        pm.registerEvents(new PotionFix(), this);
        pm.registerEvents(new ChunkBusterListener(), this);
        pm.registerEvents(new OmniGemFix(), this);
        pm.registerEvents(new CommandAliases(), this);
        pm.registerEvents(new FirstJoin(), this);
        pm.registerEvents(new CombatLogBlock(), this);
        pm.registerEvents(new DamageFixes(), this);
        pm.registerEvents(new LastManStandingListener(), this);
        pm.registerEvents(new PayListener(), this);
    }

    public void showError(Player sender, Throwable exception, String commandLabel) {
        sender.sendMessage(I18n.tl("errorWithMessage", exception.getMessage()));
    }

    private void setupSQL() {
        SQLHandler.initializeSQL(AstroCore.getInstance());
        SQLConnection.executeSQLQuery("CREATE TABLE IF NOT EXISTS `titledata` (`UUID` VARCHAR(36) NOT NULL, `ActiveTitle` VARCHAR(255), primary key(UUID))", false);
        SQLConnection.executeSQLQuery("DELETE FROM `titledata` WHERE ActiveTitle='none'", false);
    }

    public String getConsolePrefix() {
        return consolePrefix;
    }

    public Economy getEcon() {
        return econ;
    }


    static {
        hcf = false;
    }

    public void setTntDebug(boolean tntDebug) {
        this.tntDebug = tntDebug;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        econ = rsp.getProvider();
        return (econ != null);
    }

    public CommandHelp getHelpCommand() {
        return this.commandHelp;
    }

    private void buildDB() {
        this.spongeDirectory = new File("plugins/CosmicSponges/spongeChunks");
        if (!this.spongeDirectory.exists()) {
            this.spongeDirectory.mkdirs();
        }
        for (World w : Bukkit.getWorlds()) {
            File subDir = new File("plugins/CosmicSponges/spongeChunks/" + w.getName());
            subDir.mkdir();
        }
    }

    public File getSpongeDirectory() {
        return this.spongeDirectory;
    }

    private void registerCommand(Command cmd) {
        this.registerCommand(cmd, this.getName());
    }

    public void registerCommand(Command cmd, String fallback) {
        try {
            if (bukkitCommand == null) {
                bukkitCommand = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                bukkitCommand.setAccessible(true);
            }
            CommandMap commandMap = (CommandMap) bukkitCommand.get(Bukkit.getServer());
            commandMap.register(cmd.getName(), fallback, cmd);
        } catch(Exception e){
            e.printStackTrace();

        }
    }

    public TogglesManager getToggleManager() {
        return toggleManager;
    }

    public FilterManager getFilterManager() {
        return filterManager;
    }

    public CoinFlipManager getCoinFlipManager() {
        return coinFlipManager;
    }

    public JackpotManager getJackpotManager() {
        return jackpotManager;
    }

    public PayToggle getPayToggles() {
        return this.payToggles;
    }

}
