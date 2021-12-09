package net.astropvp.astroevents;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import net.astropvp.astroevents.commands.AdminEventsCommand;
import net.astropvp.astroevents.commands.EventsCommand;
import net.astropvp.astroevents.commands.ParkourEventCommand;
import net.astropvp.astroevents.commands.SumoEventCommand;
import net.astropvp.astroevents.game.MiniGameRegister;
import net.astropvp.astroevents.utils.configuration.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

@Getter
public class AstroEvents extends JavaPlugin {

    private ConfigManager configManager;
    private MiniGameRegister miniGameRegister;
    private PaperCommandManager commandManager;
    private List<BaseCommand> commands;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager(this);
        this.configManager.setupFiles();

        this.miniGameRegister = new MiniGameRegister(this);
        this.miniGameRegister.initialize();

        this.commandManager = new PaperCommandManager(this);
        this.commandManager.enableUnstableAPI("help");

        this.registerCommands();
    }

    @Override
    public void onDisable() {
        this.commands.clear();
        this.getMiniGameRegister().unregisterAll();
        this.commandManager = null;
        this.configManager = null;
    }

    private void registerCommands() {
        this.initNewCommands();
        this.commands.forEach(command -> this.commandManager.registerCommand(command));
    }

    public void initNewCommands() {
        this.commands = Arrays.asList(
                new AdminEventsCommand(this),
                new ParkourEventCommand(this),
                new SumoEventCommand(this),
                new EventsCommand(this)
        );
    }
}
