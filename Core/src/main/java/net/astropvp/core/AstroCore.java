package net.astropvp.core;

import com.cosmicpvp.CosmicSponges.CosmicSpongesAPI;
import com.cosmicpvp.CosmicSponges.libs.SpongeChunk;
import com.cosmicpvp.CosmicSponges.listeners.BlockFlowListener;
import com.cosmicpvp.CosmicSponges.listeners.ChunkListener;
import com.cosmicpvp.CosmicSponges.listeners.SpongeModifyListener;
import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.I18n;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.ifamasssxd.betterpay.PayListener;
import me.ifamasssxd.betterpay.PayToggle;
import me.ifamasssxd.betterpay.commands.CommandTogglePay;
import me.ifamasssxd.cosmicjackpot.listeners.JackpotListener;
import me.ifamasssxd.cosmicjackpot.struct.JackpotManager;
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
import me.matthawkk.tasks.AutoBroadcastTask;
import net.astropvp.core.commands.*;
import net.astropvp.core.listeners.*;
import net.astropvp.core.tasks.LastManStandingCapTask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

@Getter @Setter
public class AstroCore extends JavaPlugin {

    private static AstroCore instance;
    private static Economy econ = null;
    private final String consolePrefix = "[AstroCore]";

    public static AstroCore getInstance() {
        return instance;
    }

    public static Economy getEcon() {
        return econ;
    }

    private CommandHelp commandHelp;
    public static String LORE_SUFFIX = ":  ";
    private File spongeDirectory;

    private JackpotManager jackpotManager;
    public static Economy economy;

    Essentials ess;
    private PayToggle payToggles;

    private FilterManager filterManager;
    private ItemFilterListener itemListener;

    @SneakyThrows
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        setupEconomy();
        commands();
        events();
        new LastManStandingCapTask().runTaskTimer(this, 20L, 20L);
        Bukkit.getScheduler().runTaskTimer(this, new AutoBroadcastTask(), 20L, 6000L);

        this.commandHelp = new CommandHelp();
        if (commandHelp.isEnchantsEnabled()) {
            this.getCommand("helpenchants").setExecutor(new CommandHelpEnchants());
        }

        CosmicSpongesAPI.setCachedSpongeChunks(new HashMap<String, SpongeChunk>());
        this.buildDB();
        for (World w : Bukkit.getWorlds()) {
            for (Chunk c : w.getLoadedChunks()) {
                CosmicSpongesAPI.cacheSpongeChunk(c);
            }
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "arkunload lava_sponge");
        WorldGuardPlugin.inst().getConfig().set("simulation.sponge.enable", false);
        for (World w : Bukkit.getWorlds()) {
            WorldGuardPlugin.inst().getGlobalStateManager().get(w).simulateSponge = false;
        }
        CosmicSpongesAPI.debug = false;

        this.ess = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
        this.payToggles = new PayToggle("toggles.yml");

        this.jackpotManager = new JackpotManager();
        this.jackpotManager.loadFileData(this);
        this.getServer().getPluginManager().registerEvents(new JackpotListener(), this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> this.jackpotManager.onJackpotTick(), 20L, 20L);

        this.filterManager = new FilterManager();
        this.filterManager.loadFilters();
        this.itemListener = new ItemFilterListener();
        this.getServer().getPluginManager().registerEvents(this.itemListener, this);
        this.getCommand("filter").setExecutor(new CommandFilter());

        this.getCommand("wiki").setExecutor(new CommandWiki());
        this.getCommand("spawnmenu").setExecutor(new CommandSpawnMenu());
        this.getCommand("withdrawsouls").setExecutor(new CommandWithdrawSouls());
        this.getCommand("givetracker").setExecutor(new CommandGiveTracker());
        this.getCommand("givesoulgem").setExecutor(new CommandGiveSoulGem());

        this.getServer().getPluginManager().registerEvents(new CommandListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new ItemListener(), this);
        this.getServer().getPluginManager().registerEvents(new SoulGemListener(), this);

        this.getServer().getPluginManager().registerEvents(new ChunkListener(), this);
        this.getServer().getPluginManager().registerEvents(new BlockFlowListener(), this);
        this.getServer().getPluginManager().registerEvents(new SpongeModifyListener(), this);
    }

    @SneakyThrows
    public void onDisable() {
        instance = null;
        this.payToggles.saveToggles();
        this.jackpotManager.saveFileData();
        this.filterManager.saveFilters();
    }


    public void commands() {
        getCommand("bless").setExecutor(new Bless());
        getCommand("dupe").setExecutor(new CommandDupe());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("pots").setExecutor(new PotionsCommand(this));
        getCommand("broadcast").setExecutor(new Broadcast(this));
        getCommand("invsee").setExecutor(new Invsee());
        getCommand("givechunkbuster").setExecutor(new ChunkBuster(this));
        getCommand("trash").setExecutor(new TrashCommand());
        getCommand("lastmanstanding").setExecutor(new LastManStandingCommand());
        getCommand("togglepay").setExecutor(new CommandTogglePay());
    }

    public void events() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new DungeonInventoryEvent(), this);
        pm.registerEvents(new AutoOP(), this);
        pm.registerEvents(new ServerFixes(), this);
        pm.registerEvents(new MOTD(), this);
        pm.registerEvents(new HeroicArmorFix(), this);
        pm.registerEvents(new MobRewards(), this);
        pm.registerEvents(new PotionFix(), this);
        pm.registerEvents(new ChunkBusterListener(), this);
        pm.registerEvents(new OmniGemFix(), this);
        pm.registerEvents(new CommandAliases(), this);
        pm.registerEvents(new FirstJoin(), this);
        pm.registerEvents(new CombatLogBlock(), this);
        pm.registerEvents(new DamageFixes(), this);
        pm.registerEvents(new LastManStandingListener(), this);
        pm.registerEvents(new MobSpawnerFix(), this);
        pm.registerEvents(new PayListener(), this);
    }

    public void showError(Player sender, Throwable exception, String commandLabel) {
        sender.sendMessage(I18n.tl("errorWithMessage", exception.getMessage()));
    }

    public String getConsolePrefix() {
        return consolePrefix;
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

}
