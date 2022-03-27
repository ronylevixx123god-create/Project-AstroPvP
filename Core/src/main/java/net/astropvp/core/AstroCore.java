package net.astropvp.core;

import com.astropvp.cc.player.CosmicPlayers;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.FieldAccessException;
import com.cosmicpvp.cosmicutils.utils.CC;
import net.astropvp.core.cosmiccoinflip.listeners.PlayerListener;
import net.astropvp.core.stats.StatsManager;
import net.astropvp.core.titles.cmds.CommandTitle;
import net.astropvp.core.titles.cmds.CommandTitleSet;
import net.astropvp.core.titles.listeners.PermissionListener;
import net.astropvp.core.titles.listeners.PlayerJoinQuitListener;
import net.astropvp.core.titles.listeners.TitleInventoryListener;
import net.astropvp.core.titles.sql.SQLConnection;
import net.astropvp.core.titles.sql.SQLHandler;
import net.astropvp.core.titles.sql.TitleDatabaseManager;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.I18n;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import com.massivecraft.factions.Faction;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.astropvp.core.autogiveall.tasks.AutoGiveAllTask;
import net.astropvp.core.betterpay.PayListener;
import net.astropvp.core.betterpay.PayToggle;
import net.astropvp.core.betterpay.commands.CommandTogglePay;
import net.astropvp.core.cosmiccoinflip.commands.CommandCoinFlip;
import net.astropvp.core.cosmiccoinflip.listeners.InventoryListener;
import net.astropvp.core.cosmiccoinflip.struct.CoinFlipManager;
import net.astropvp.core.cosmiccoinflip.struct.CoinFlipMatch;

import net.astropvp.core.cosmicjackpot.commands.CommandJackpot;
import net.astropvp.core.cosmicjackpot.listeners.JackpotListener;
import net.astropvp.core.cosmicjackpot.struct.JackpotManager;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import net.astropvp.core.cosmictoggles.commands.CommandToggles;
import net.astropvp.core.cosmictoggles.manager.TogglesManager;
import net.astropvp.core.itemfilter.FilterManager;
import net.astropvp.core.itemfilter.commands.CommandFilter;
import net.astropvp.core.itemfilter.listeners.ItemFilterListener;
import net.astropvp.core.killtracker.commands.CommandGiveSoulGem;
import net.astropvp.core.killtracker.commands.CommandWithdrawSouls;
import net.astropvp.core.killtracker.listeners.SoulGemListener;
import net.astropvp.core.autobroadcast.tasks.AutoBroadcastTask;
import net.astropvp.core.autogiveall.struct.GiveAllManager;
import net.astropvp.core.autogiveall.commands.CommandForceGiveAll;
import net.astropvp.core.autogiveall.commands.CommandNextGiveAll;
import net.astropvp.core.commands.*;
import net.astropvp.core.conaxgames.staff.*;
import net.astropvp.core.conaxgames.staff.staffmode.StaffModeCommand;
import net.astropvp.core.conaxgames.staff.staffmode.StaffModeManager;
import net.astropvp.core.conaxgames.staff.staffmode.staffmodules.StaffModeListeners;
import net.astropvp.core.conaxgames.staff.staffmode.staffmodules.StaffModulesRegister;
import net.astropvp.core.items.*;
import net.astropvp.core.items.coder.EnchantmentCoderItem;
import net.astropvp.core.items.coder.HeroicEnchantmentCoderItem;
import net.astropvp.core.listeners.*;
import net.astropvp.core.struct.EnchantmentCoderManager;
import net.astropvp.core.tasks.LastManStandingCapTask;
import net.astropvp.core.utils.CleanseScroll;
import net.astropvp.core.utils.CooldownUtils;
import net.astropvp.core.utils.ItemUtils;
import net.astropvp.core.utils.cache.CacheManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;

@Getter
@Setter
public class AstroCore extends JavaPlugin {
    private static AstroCore instance;
    private CacheManager cacheManager;
    private static Economy econ = null;
    private static Field bukkitCommand;
    private final String consolePrefix = "[AstroCore]";

    public static AstroCore getInstance() {
        return instance;
    }

    private StaffModeManager staffModeManager;
    private StaffModulesRegister staffModulesRegister;
    public static String LORE_SUFFIX = ":  ";
    private File spongeDirectory;
    private HolyWhiteScrollListener holyWhiteScrollListener;
    private HolyWhitescrollItem holyWhitescrollItem;
    private JackpotManager jackpotManager;
    public static Economy economy;
    Essentials ess;
    private PayToggle payToggles;
    ProtocolManager protocolManager;
    public static Map<UUID, Player> grindingMessages;
    private HashMap<UUID, ChatColor> cachedColors;
    public HashMap<UUID, ChatColor> getCachedColors() {
        return this.cachedColors;
    }
    public void addCachedColor(Player player, ChatColor color) {
        cachedColors.put(player.getUniqueId(), color);
    }
    private FilterManager filterManager;
    private ItemFilterListener itemListener;
    public TitleDatabaseManager dataManager;
    public static boolean hcf;
    private SoulItemListener soulItemListener;
    private EnchantmentCoderManager enchantmentCoderManager;
    private CoinFlipManager coinFlipManager;
    private TogglesManager toggleManager = new TogglesManager();
    private GiveAllManager giveAllManager;
    private CooldownUtils cooldownUtils;
    @Getter
    @Setter
    public static TitleManagerAPI titleManagerAPI;
    private Freeze freezeCommand;
    private StatsManager statsManager;

    @SneakyThrows
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        setupEconomy();
        events();
        cachedColors = new HashMap<>();
        this.enchantmentCoderManager = new EnchantmentCoderManager("plugins/AstroCore/enchantmentcoderdata.json");
        this.enchantmentCoderManager.loadData();
        this.staffModeManager = new StaffModeManager();
        this.staffModulesRegister = new StaffModulesRegister(this);
        this.staffModulesRegister.initialize();
        this.cacheManager = new CacheManager();
        grindingMessages = new HashMap<>();
        this.claimsOnInfernal = new HashMap<>();
        this.claimsOnDesert = new HashMap<>();
        this.claimsOnFrozen = new HashMap<>();
        this.filterManager = new FilterManager();
        this.filterManager.loadFilters();
        this.statsManager = new StatsManager(this);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "arkunload lava_sponge");
        WorldGuardPlugin.inst().getConfig().set("simulation.sponge.enable", false);
        for (World w : Bukkit.getWorlds()) {
            WorldGuardPlugin.inst().getGlobalStateManager().get(w).simulateSponge = false;
        }
        this.ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        this.payToggles = new PayToggle("toggles.yml");

        this.jackpotManager = new JackpotManager();
        try {
            this.jackpotManager.loadFileData(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setupSQL();
        this.dataManager = new TitleDatabaseManager();
        this.dataManager.cacheAllEquippedTitles();
        this.coinFlipManager = new CoinFlipManager();
        this.cooldownUtils = new CooldownUtils("astroCore");
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
        this.toggleManager.loadToggles();
        try {
            Class.forName("org.arkhamnetwork.Arkkit.patches.combatlog.CombatLoggerDeathEvent");
            getServer().getPluginManager().registerEvents((Listener) new HolyWhiteScrollCombatLoggerListener(), (Plugin) this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        registerCommands();
        registerListeners();
        registerCustomItems();
        registerTasks();
        protocolFix();

        Bukkit.getConsoleSender().sendMessage(" ******************************************** ");
        Bukkit.getConsoleSender().sendMessage(" ASTRO CORE PLUGIN SHOULD BE LOADED NOW");
        Bukkit.getConsoleSender().sendMessage(" ******************************************** ");

    }

    public HolyWhiteScrollListener getHolyWhiteScrollListener() {
        return this.holyWhiteScrollListener;
    }

    @SneakyThrows
    public void onDisable() {
        this.staffModeManager.toggleOffAllStaffModes();
        this.payToggles.saveToggles();
        try {
            this.jackpotManager.saveFileData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dataManager.saveAllEquippedTitlesToSQL();
        this.filterManager.saveFilters();


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

        this.enchantmentCoderManager.saveData();
        this.enchantmentCoderManager.clearData();

        Set infernalClaims = this.claimsOnInfernal.entrySet();
        for (Object infernalClaim : infernalClaims) {
            Map.Entry me = (Map.Entry) infernalClaim;
            Faction faction = (Faction) me.getKey();
            getConfig().set("infernal-claims." + faction.getTag(), me.getValue());
        }

        Set frozenClaims = this.claimsOnFrozen.entrySet();
        for (Object frozenClaim : frozenClaims) {
            Map.Entry me = (Map.Entry) frozenClaim;
            Faction faction = (Faction) me.getKey();
            getConfig().set("frozen-claims." + faction.getTag(), me.getValue());
        }

        Set desertClaims = this.claimsOnDesert.entrySet();
        for (Object desertClaim : desertClaims) {
            Map.Entry me = (Map.Entry) desertClaim;
            Faction faction = (Faction) me.getKey();
            getConfig().set("desert-claims." + faction.getTag(), me.getValue());
        }

        saveConfig();

        instance = null;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void events() {
        PluginManager pm = getServer().getPluginManager();

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


    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return;
        econ = rsp.getProvider();
    }


    public File getSpongeDirectory() {
        return this.spongeDirectory;
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

    public void registerCommands() {
        getCommand("filter").setExecutor(new CommandFilter());
        getCommand("withdrawsouls").setExecutor(new CommandWithdrawSouls());
        getCommand("givesoulgem").setExecutor(new CommandGiveSoulGem());
        getCommand("title").setExecutor(new CommandTitle());
        getCommand("titleset").setExecutor(new CommandTitleSet());
        getCommand("forcegiveall").setExecutor(new CommandForceGiveAll());
        getCommand("nextgiveall").setExecutor(new CommandNextGiveAll());
        getCommand("giveenchantmentcrystal").setExecutor(new GiveEnchantmentCrystalCommand());
        getCommand("bless").setExecutor(new Bless());
        getCommand("dupe").setExecutor(new CommandDupe());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("pots").setExecutor(new PotionsCommand(this));
        getCommand("invsee").setExecutor(new Invsee());
        getCommand("giveall").setExecutor(new GiveallCommand());
        getCommand("givechunkbuster").setExecutor(new ChunkBuster(this));
        getCommand("trash").setExecutor(new TrashCommand());
        getCommand("lastmanstanding").setExecutor(new LastManStandingCommand());
        getCommand("togglepay").setExecutor(new CommandTogglePay());
        getCommand("toggles").setExecutor(new CommandToggles());
        getCommand("armorcolor").setExecutor(new ArmorColorCommand());
        getCommand("spawnershop").setExecutor(new SpawnerShopCommand());
        getCommand("givebucket").setExecutor(new CommandGiveMagicBucket());
        getServer().getPluginManager().registerEvents(new KillTitleSound(), this);
        getCommand("givesoulpearl").setExecutor(new SoulPearlCommand());
        getCommand("coinflip").setExecutor(new CommandCoinFlip());
        getCommand("list").setExecutor(new ListCommand());
        getCommand("grinding").setExecutor(new ToggleGrindingMessages());
        getCommand("jackpot").setExecutor(new CommandJackpot());
        getCommand("chatcolor").setExecutor(new ChatColorCommand());
        getCommand("helpenchants").setExecutor(new CommandHelpEnchants());
        getCommand("phoenix").setExecutor(new PhoenixCommand());
        getCommand("randomtp").setExecutor(new RandomTpCommand());
        freezeCommand = new Freeze();

        Arrays.asList(new GiveThrowableCreeperEgg(), new Broadcast(), new FreezeCommand(), new StaffChatCommand(), new StaffModeCommand()).forEach(this::registerCommand);
    }

    public void registerTasks() {
        new LastManStandingCapTask().runTaskTimer(this, 20L, 20L);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new AutoGiveAllTask(), 20L, 20L);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            try {
                this.jackpotManager.onJackpotTick();
            } catch (UserDoesNotExistException | NoLoanPermittedException e) {
                e.printStackTrace();
            }
        }, 20L, 20L);

        Bukkit.getScheduler().runTaskTimerAsynchronously(getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!CosmicPlayers.get().getCosmicPlayer(p).isOnCosmicClient() && !p.isOp()) {
                    p.sendMessage("");
                    p.sendMessage(CC.translate("&c&lHey &n" + p.getName() + "&c&l, &cwe have noticed that you are not on the official Suported Astro Version of the &nCosmicClient&c."));
                    p.sendMessage(CC.translate("&7If you wish to download it, follow the steps in the &f#cc-features&7 channel in our &f/discord&7."));
                    p.sendMessage("");
                }
            }
        }, 50L, 7200L);



        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new AutoBroadcastTask(), 20L, 6000L);

        Bukkit.getScheduler().runTaskLater(this, () ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "arkload vault_gui"), 100L);
        Bukkit.getScheduler().runTaskLater(this, () ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "arkload death_messages"), 100L);
        Bukkit.getScheduler().runTaskLater(this, () ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "blockcommand remove ver"), 100L);
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new WarzoneClaimListener(), this);
        getServer().getPluginManager().registerEvents(new StaffModeListeners(this), this);
        getServer().getPluginManager().registerEvents(new EnderpearlFixes(), this);
        getServer().getPluginManager().registerEvents(new DamageFixes(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new TrapBlockListener(), this);
        getServer().getPluginManager().registerEvents(new MagicalLavaBucket(), this);
        getServer().getPluginManager().registerEvents(new MagicalWaterBucket(), this);
        getServer().getPluginManager().registerEvents(new StaffListeners(), this);
        getServer().getPluginManager().registerEvents(new SoulGemListener(), this);
        getServer().getPluginManager().registerEvents(new ApplyEnchantCrystalListener(), this);
        getServer().getPluginManager().registerEvents(new TitleInventoryListener(this.dataManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(this.dataManager), this);
        getServer().getPluginManager().registerEvents(new PermissionListener(this.dataManager), this);
        getServer().getPluginManager().registerEvents(new DungeonInventoryEvent(), this);
        getServer().getPluginManager().registerEvents(new AutoOP(), this);
        getServer().getPluginManager().registerEvents(new ServerFixes(), this);
        getServer().getPluginManager().registerEvents(new MOTD(), this);
        getServer().getPluginManager().registerEvents(new HeroicArmorFix(), this);
        getServer().getPluginManager().registerEvents(new ChunkBusterListener(), this);
        getServer().getPluginManager().registerEvents(new OmniGemFix(), this);
        getServer().getPluginManager().registerEvents(new CommandAliases(), this);
        getServer().getPluginManager().registerEvents(new FirstJoin(), this);
        getServer().getPluginManager().registerEvents(new CombatLogBlock(), this);
        getServer().getPluginManager().registerEvents(new LastManStandingListener(), this);
        getServer().getPluginManager().registerEvents(new PayListener(), this);
        getServer().getPluginManager().registerEvents(new Freeze(), this);
        getServer().getPluginManager().registerEvents(new ArmorColorClickListener(), this);
        getServer().getPluginManager().registerEvents(new BlockCommand(), this);
        getServer().getPluginManager().registerEvents(new SoulItemListener(), this);
        getServer().getPluginManager().registerEvents(new HolyWhiteScrollListener(), this);
        getServer().getPluginManager().registerEvents(new ThrowableCreeperEggs(), this);
        getServer().getPluginManager().registerEvents(new EnchantmentCoderInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new EnchantmentCoderItemListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new WarpCommand(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new WorldBorderPearlFix(), this);
        getServer().getPluginManager().registerEvents(new JackpotListener(), this);
        getServer().getPluginManager().registerEvents(new GrindingMessagesToggleListener(), this);
        getServer().getPluginManager().registerEvents(new DupePatch(), this);
        getServer().getPluginManager().registerEvents( new ItemFilterListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerShopListener(), this);
        this.itemListener = new ItemFilterListener();
    }

    public void registerCustomItems() {
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new HeroicUpgrade());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new RogueScratchCard());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new ApolloRank());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new NebulaRank());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new AstroPlusRank());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new TrapBlockerItem());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new RevokeScratchCard());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new SlotbotScratchCard());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new HolyWhitescrollItem());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new HolyWaterItem());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new CleanseScroll());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new EnchantmentCoderItem());
        CosmicSkyCustomItems.get().getItemManager().registerCustomItem(new HeroicEnchantmentCoderItem());
        this.holyWhitescrollItem = new HolyWhitescrollItem();
    }

    public void protocolFix() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.TAB_COMPLETE) {

            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
                    if (event.getPlayer().isOp()) return;

                    try {
                        PacketContainer packet = event.getPacket();
                        String message = ((String) packet.getSpecificModifier(String.class).read(0)).toLowerCase();
                        if (message.startsWith("/?") && !message.contains("  ") || message.startsWith("/") && !message.contains(" ")
                                || message.startsWith("/help") && !message.contains("  ") || message.startsWith("/ver") && !message.contains("  ")
                                || message.startsWith("/bukkit") && !message.contains("  ")) {
                            event.setCancelled(true);
                        }
                    } catch (FieldAccessException e) {
                        AstroCore.this.getLogger().log(Level.SEVERE, "bruh", e);
                    }
                }
            }
        });
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
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private HashMap<Faction, Integer> claimsOnInfernal;

    public int getClaimsOnInfernal(Faction faction) {
        if (!this.claimsOnInfernal.containsKey(faction)) {
            if (this.getConfig().contains("infernal-claims." + faction.getTag())) {
                this.claimsOnInfernal.put(faction, getConfig().getInt("infernal-claims." + faction));
            }
        }
        return this.claimsOnInfernal.get(faction);
    }

    public void setClaimsOnInfernal(Faction faction, int i) {
        this.claimsOnInfernal.put(faction, i);
    }

    private HashMap<Faction, Integer> claimsOnFrozen;

    public int getClaimsOnFrozen(Faction faction) {
        if (!this.claimsOnFrozen.containsKey(faction)) {
            if (this.getConfig().contains("frozen-claims." + faction.getTag())) {
                this.claimsOnFrozen.put(faction, getConfig().getInt("frozen-claims." + faction));
            }
        }
        return this.claimsOnFrozen.get(faction);
    }

    public void setClaimsOnFrozen(Faction faction, int i) {
        this.claimsOnFrozen.put(faction, i);
    }

    private HashMap<Faction, Integer> claimsOnDesert;

    public int getClaimsOnDesert(Faction faction) {
        if (!this.claimsOnDesert.containsKey(faction)) {
            if (this.getConfig().contains("desert-claims." + faction.getTag())) {
                this.claimsOnDesert.put(faction, getConfig().getInt("desert-claims." + faction));
            }
        }
        return this.claimsOnDesert.get(faction);
    }

    public void setClaimsOnDesert(Faction faction, int i) {
        this.claimsOnDesert.put(faction, i);
    }

    public HolyWhitescrollItem getHolyWhitescrollItem() {
        return this.holyWhitescrollItem;
    }

    public static ItemStack getSoulPearl() {
        return ItemUtils.addGlow(ItemUtils.buildItem(Material.ENDER_PEARL, (short) 0, (Object) ChatColor.DARK_PURPLE + "" + (Object) ChatColor.BOLD + "Soul Pearl", (Object) ChatColor.LIGHT_PURPLE + "A rare /dungeon trinket that", (Object) ChatColor.LIGHT_PURPLE + "functions like a normal enderpearl,", (Object) ChatColor.LIGHT_PURPLE + "but costs souls instead of being,", (Object) ChatColor.LIGHT_PURPLE + "consumed on use.", "", (Object) ChatColor.DARK_PURPLE + "" + (Object) ChatColor.UNDERLINE + "Soul Cost:" + (Object) ChatColor.LIGHT_PURPLE + " 5/pearl"));
    }

    public static ItemStack getHolyWhiteScroll() {
        return AstroCore.getInstance().getHolyWhitescrollItem().createItem(null);
    }

    @Deprecated
    public static ItemStack getOldHolyWhiteScroll() {
        return ItemUtils.addGlow(ItemUtils.buildItem(Material.PAPER, (short) 0, (Object) ChatColor.GOLD + "" + (Object) ChatColor.BOLD + "Holy White Scroll", (Object) ChatColor.YELLOW + "A legendary /dungeon reward that", (Object) ChatColor.YELLOW + "can be applied to armor/weapons,", (Object) ChatColor.YELLOW + "gives a 100% chance of not losing", (Object) ChatColor.YELLOW + "the blessed item on death.", "", (Object) ChatColor.GOLD + "" + (Object) ChatColor.UNDERLINE + "REQ:" + (Object) ChatColor.YELLOW + " White Scroll"));
    }
}
