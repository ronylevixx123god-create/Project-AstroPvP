package me.kingsavaqe.astrotournaments;

import lombok.Getter;
import me.kingsavaqe.astrotournaments.commands.TournamentCommand;
import me.kingsavaqe.astrotournaments.listeners.PlayerQuitListener;
import me.kingsavaqe.astrotournaments.listeners.TournamentDeathListener;
import me.kingsavaqe.astrotournaments.managers.TournamentManager;
import me.kingsavaqe.astrotournaments.objects.TournamentPlayer;
import me.kingsavaqe.astrotournaments.utils.Queue;
import me.kingsavaqe.astrotournaments.utils.Utils;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public class AstroTournamentsPlugin extends JavaPlugin {

    @Getter
    private TournamentManager tournamentManager;

    @Getter
    private ArenaRegister arenaRegister;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        CommandManager commandManager = new CommandManager(this);
        commandManager.register(new TournamentCommand(this));

        this.tournamentManager = new TournamentManager(this);
        this.tournamentManager.setPlayerQueue(new Queue<>());
        this.tournamentManager.setWaitingRoomLocation(Utils.getLocationFromString(this.getConfig().getString("Tournament.WaitingRoom.Location")));
        this.arenaRegister = new ArenaRegister(this);

        this.arenaRegister.registerArenas();

        this.registerListeners();

    }

    @Override
    public void onDisable() {
        for (Map.Entry<UUID, TournamentPlayer> entry : tournamentManager.getTournamentPlayers().entrySet()) {
            UUID uuid = entry.getKey();
            TournamentPlayer tournamentPlayer = entry.getValue();

            //Save to file for each player

        }
    }


    public void registerListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new TournamentDeathListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(this), this);
    }
}
