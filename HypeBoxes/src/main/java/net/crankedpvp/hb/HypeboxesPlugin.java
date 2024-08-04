package net.crankedpvp.hb;

import lombok.Getter;
import me.mattstudios.mf.base.CommandManager;
import net.crankedpvp.hb.commands.HypeBoxCommand;
import net.crankedpvp.hb.commands.ViewBattleOpenCommand;
import net.crankedpvp.hb.commands.ViewHypeboxOpenCommand;
import net.crankedpvp.hb.listeners.HypeboxOpenListener;
import net.crankedpvp.hb.listeners.HypeboxPreviewListener;
import net.crankedpvp.hb.struct.HypeBoxManager;
import net.crankedpvp.hb.struct.HypeBoxRegister;
import net.crankedpvp.hb.inventory.HypeBoxAnimationInventory;
import net.crankedpvp.hb.inventory.HypeboxBattleInventory;
import net.crankedpvp.hb.language.FileHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HypeboxesPlugin extends JavaPlugin {

    @Getter
    private static HypeboxesPlugin instance;

    @Getter
    private FileHandler fileHandler;

    @Getter
    private HypeBoxRegister hypeBoxRegister;

    @Getter
    private HypeBoxManager hypeBoxManager;

    @Getter
    private CommandManager commandManager;

    @Getter
    private Map<UUID, HypeBoxAnimationInventory> hypeboxesBeingOpened;

    @Getter
    private Map<UUID, HypeboxBattleInventory> hypeboxBattles;


    @Override
    public void onEnable() {

        instance = this;

        this.fileHandler = new FileHandler(this);
        this.fileHandler.load();

        this.hypeBoxRegister = new HypeBoxRegister(this);
        this.hypeBoxManager = new HypeBoxManager(this);

        this.hypeBoxRegister.clearHypeboxMap();
        this.hypeBoxManager.createDefaultConfig("-example");
        this.hypeBoxManager.initializeAll();

        this.commandManager = new CommandManager(this);
        this.registerCommandCompletion();
        this.commandManager.register(new HypeBoxCommand(this));
        this.commandManager.register(new ViewHypeboxOpenCommand(this));
        this.commandManager.register(new ViewBattleOpenCommand(this));

        this.hypeboxesBeingOpened = new HashMap<>();
        this.hypeboxBattles = new HashMap<>();

        this.registerListeners();
    }

    private void registerListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new HypeboxOpenListener(this), this);
        pluginManager.registerEvents(new HypeboxPreviewListener(this), this);
    }

    public void registerCommandCompletion() {
        this.commandManager.getCompletionHandler().register("#hypeboxes", input -> this.hypeBoxRegister.getHypeboxesAsIDs());
    }
}

